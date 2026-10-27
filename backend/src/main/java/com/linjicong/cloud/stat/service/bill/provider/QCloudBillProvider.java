/*
 * MIT License
 *
 * Copyright (c) 2022 linjicong
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.linjicong.cloud.stat.service.bill.provider;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.linjicong.cloud.stat.dao.entity.qcloud.QCloudBillResourceSummary;
import com.linjicong.cloud.stat.dao.mapper.qcloud.QCloudBillResourceSummaryMapper;
import com.linjicong.cloud.stat.service.bill.bo.AmountSourceEnum;
import com.linjicong.cloud.stat.service.bill.bo.BillQueryCondition;
import com.linjicong.cloud.stat.service.bill.bo.ChargingModeEnum;
import com.linjicong.cloud.stat.service.bill.bo.UnifiedBillRecord;
import com.linjicong.cloud.stat.service.bill.util.BillAmountParser;
import com.linjicong.cloud.stat.service.bill.util.BillMonthUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 腾讯云账单资源摘要归一化 Provider。
 *
 * <p>关键点：
 * <ol>
 *   <li>金额列为 {@code varchar}，必须走 {@link BillAmountParser}，且解析异常要计入
 *       {@code amountAnomaly}（对应 PRD 验收项 7）；</li>
 *   <li>该表<b>没有 {@code month} 列</b>，账期从 {@code pay_time} 归一
 *       （{@link BillMonthUtil#normalize(String)} 支持 {@code 2025-03-15 ...} 形式）；</li>
 *   <li>计费模式由 {@code PayModeName} 文本映射。</li>
 * </ol>
 *
 * @author linjicong
 * @version 1.0.0
 * @date 2026-01-01
 */
@Slf4j
@Component
public class QCloudBillProvider implements BillRecordProvider {

    /** 厂商标识 */
    public static final String PROVIDER = "tencent";

    private final QCloudBillResourceSummaryMapper resourceSummaryMapper;

    public QCloudBillProvider(QCloudBillResourceSummaryMapper resourceSummaryMapper) {
        this.resourceSummaryMapper = resourceSummaryMapper;
    }

    @Override
    public boolean supports(String provider) {
        return PROVIDER.equalsIgnoreCase(provider);
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public List<UnifiedBillRecord> toUnified(BillQueryCondition condition) {
        List<UnifiedBillRecord> result = new ArrayList<>();
        if (condition == null) {
            return result;
        }

        QueryWrapper<QCloudBillResourceSummary> wrapper = new QueryWrapper<>();
        // 该表 pay_time 存在大量零值，不能用 pay_time 做过前置过滤（会误杀 month 有效的行）；
        // 账期筛选在内存侧按 resolveBillMonth 归一后精确过滤
        if (condition.getAccounts() != null && !condition.getAccounts().isEmpty()) {
            wrapper.in("conf_name", condition.getAccounts());
        }

        List<QCloudBillResourceSummary> entities = resourceSummaryMapper.selectList(wrapper);
        int dropped = 0;
        for (QCloudBillResourceSummary entity : entities) {
            try {
                String billMonth = resolveBillMonth(entity);
                if (billMonth == null) {
                    // 账期无法归一，丢弃并计入 droppedRowCount
                    dropped++;
                    continue;
                }
                if (condition.getStartMonth() != null && !condition.getStartMonth().isBlank()
                        && billMonth.compareTo(condition.getStartMonth()) < 0) {
                    continue;
                }
                if (condition.getEndMonth() != null && !condition.getEndMonth().isBlank()
                        && billMonth.compareTo(condition.getEndMonth()) > 0) {
                    continue;
                }
                result.add(toUnified(entity, billMonth));
            } catch (RuntimeException e) {
                log.warn("腾讯云账单归一行失败，已跳过，pk={}", entity.getPk(), e);
            }
        }
        // 把账期无法归一的丢弃行数写回条件对象，供 dataQuality.droppedRowCount 使用
        condition.addDroppedRowCount(dropped);
        return result;
    }

    /**
     * 解析腾讯云账单账期。
     *
     * <p>实测核对结论（与 Mapper XML 的 qcBillMonth 片段保持完全一致的口径）：
     * 该表<b>实际存在 {@code month} 列</b>（形如 {@code 2023-03}），且 {@code pay_time}
     * 存在大量 {@code 0000-00-00 00:00:00} 零值脏数据，不能从 pay_time 推导账期。
     * 因此：优先取 {@code month}，仅当 {@code month} 非法/为空时回退到 {@code pay_time}
     * 前 7 位，并排除 {@code 0000-00} 这类非法账期。</p>
     *
     * @param entity 原始实体
     * @return {@code YYYY-MM}；无法解析返回 null
     */
    private String resolveBillMonth(QCloudBillResourceSummary entity) {
        String month = entity.getMonth();
        if (month != null && month.trim().matches("\\d{4}-\\d{2}")) {
            String normalized = BillMonthUtil.normalize(month.trim());
            if (normalized != null) {
                return normalized;
            }
        }
        String payTime = entity.getPayTime();
        if (payTime != null) {
            String trimmed = payTime.trim();
            if (trimmed.matches("^\\d{4}-\\d{2}.*")) {
                String candidate = trimmed.substring(0, 7);
                if (!"0000-00".equals(candidate)) {
                    return BillMonthUtil.normalize(candidate);
                }
            }
        }
        return null;
    }

    /**
     * 单行归一化。
     *
     * @param entity    原始实体
     * @param billMonth 已归一的账期
     * @return 统一账单记录
     */
    private UnifiedBillRecord toUnified(QCloudBillResourceSummary entity, String billMonth) {
        int anomalyBefore = BillAmountParser.anomalyCount();

        BigDecimal cash = BillAmountParser.toDecimalOrZero(entity.getCashPayAmount());
        BigDecimal voucher = BillAmountParser.toDecimalOrZero(entity.getVoucherPayAmount());
        BigDecimal incentive = BillAmountParser.toDecimalOrZero(entity.getIncentivePayAmount());
        BigDecimal totalCost = BillAmountParser.toDecimalOrZero(entity.getTotalCost());
        BigDecimal realTotalCost = BillAmountParser.toDecimalOrZero(entity.getRealTotalCost());

        // 优惠额 = 原价 - 优惠后总价，负数按 0 兜底
        BigDecimal discount = totalCost.subtract(realTotalCost);
        if (discount.compareTo(BigDecimal.ZERO) < 0) {
            discount = BigDecimal.ZERO;
        }

        boolean anomaly = BillAmountParser.anomalyCount() > anomalyBefore;

        String region = entity.getConfRegion();
        if (region == null || region.isBlank()) {
            region = entity.getRegionName();
        }

        ChargingModeEnum mode = ChargingModeEnum.fromTencentText(entity.getPayModeName());

        String productSub = entity.getProductCodeName();
        if (productSub != null && "-".equals(productSub.trim())) {
            // 腾讯云约定「没有获取到子产品名称时返回 -」
            productSub = null;
        }

        return UnifiedBillRecord.builder()
                .account(entity.getConfName())
                .provider(PROVIDER)
                .providerLabel("腾讯云")
                .region(region)
                .billMonth(billMonth)
                .productMain(entity.getBusinessCodeName())
                .productSub(productSub)
                .chargingMode(mode.label())
                .chargingModeRaw(entity.getPayModeName())
                .resourceId(entity.getResourceId())
                .resourceName(entity.getResourceName())
                .tradeTime(entity.getPayTime())
                .payAmount(BillAmountParser.add(cash, BillAmountParser.add(voucher, incentive)))
                .officialAmount(BillAmountParser.scale(totalCost))
                .consumeAmount(BillAmountParser.scale(realTotalCost))
                .discountAmount(BillAmountParser.scale(discount))
                .cashPay(BillAmountParser.scale(cash))
                .voucherPay(BillAmountParser.scale(voucher))
                .creditPay(BillAmountParser.scale(incentive))
                .debtAmount(BigDecimal.ZERO.setScale(BillAmountParser.SCALE, BillAmountParser.ROUNDING))
                .eraseAmount(BigDecimal.ZERO.setScale(BillAmountParser.SCALE, BillAmountParser.ROUNDING))
                .writeoffAmount(BigDecimal.ZERO.setScale(BillAmountParser.SCALE, BillAmountParser.ROUNDING))
                .amountSource(AmountSourceEnum.RESOURCE_SUMMARY)
                .amountAnomaly(anomaly)
                .build();
    }
}
