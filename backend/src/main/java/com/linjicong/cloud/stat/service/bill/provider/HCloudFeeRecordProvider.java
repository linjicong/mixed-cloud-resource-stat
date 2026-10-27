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
import com.linjicong.cloud.stat.dao.entity.hcloud.HCloudBillsFeeRecords;
import com.linjicong.cloud.stat.dao.mapper.hcloud.HCloudBillsFeeRecordsMapper;
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
 * 华为云费用记录归一化 Provider（现金流口径，架构决策 D6）。
 *
 * <p>实付 = 现金 + 代金券 + 信用。该表<b>无</b> {@code resource_id} / {@code resource_name} 列，
 * 因此明细的资源字段恒为 null（决策 D12，不用 trade_id 冒充）。</p>
 *
 * <p>说明：KPI / 趋势 / 占比 / TOP 全部走 SQL 聚合（{@code BillAggregateMapper}），
 * 本 Provider 的 {@link #toUnified(BillQueryCondition)} 用于需要逐行归一化的场景
 * （如后续按行导出、账号可用性判断），复用同一套字段映射与金额口径，保证与 SQL 一致。</p>
 *
 * @author linjicong
 * @version 1.0.0
 * @date 2026-01-01
 */
@Slf4j
@Component
public class HCloudFeeRecordProvider implements BillRecordProvider {

    /** 厂商标识 */
    public static final String PROVIDER = "huawei";

    private final HCloudBillsFeeRecordsMapper feeRecordsMapper;

    public HCloudFeeRecordProvider(HCloudBillsFeeRecordsMapper feeRecordsMapper) {
        this.feeRecordsMapper = feeRecordsMapper;
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

        QueryWrapper<HCloudBillsFeeRecords> wrapper = new QueryWrapper<>();
        if (condition.getStartMonth() != null && !condition.getStartMonth().isBlank()) {
            wrapper.ge("LEFT(TRIM(bill_cycle), 7)", condition.getStartMonth());
        }
        if (condition.getEndMonth() != null && !condition.getEndMonth().isBlank()) {
            wrapper.le("LEFT(TRIM(bill_cycle), 7)", condition.getEndMonth());
        }
        if (condition.getAccounts() != null && !condition.getAccounts().isEmpty()) {
            wrapper.in("conf_name", condition.getAccounts());
        }

        List<HCloudBillsFeeRecords> entities = feeRecordsMapper.selectList(wrapper);
        for (HCloudBillsFeeRecords entity : entities) {
            try {
                String billMonth = BillMonthUtil.normalize(entity.getBillCycle());
                if (billMonth == null) {
                    // 账期无法归一：丢弃该行，由 Service 汇总 droppedRowCount
                    continue;
                }
                result.add(toUnified(entity, billMonth));
            } catch (RuntimeException e) {
                log.warn("华为云费用记录归一行失败，已跳过，pk={}", entity.getPk(), e);
            }
        }
        return result;
    }

    /**
     * 单行归一化。
     *
     * @param entity    原始实体
     * @param billMonth 已归一的账期
     * @return 统一账单记录
     */
    private UnifiedBillRecord toUnified(HCloudBillsFeeRecords entity, String billMonth) {
        BigDecimal cash = BillAmountParser.scale(BillAmountParser.toDecimal(entity.getCashAmount()));
        BigDecimal coupon = BillAmountParser.scale(BillAmountParser.toDecimal(entity.getCouponAmount()));
        BigDecimal credit = BillAmountParser.scale(BillAmountParser.toDecimal(entity.getCreditAmount()));
        BigDecimal official = BillAmountParser.scale(BillAmountParser.toDecimal(entity.getOfficialAmount()));
        BigDecimal consume = BillAmountParser.scale(BillAmountParser.toDecimal(entity.getConsumeAmount()));
        BigDecimal discount = BillAmountParser.scale(BillAmountParser.toDecimal(entity.getOfficialDiscountAmount()));

        String region = entity.getConfRegion();
        if (region == null || region.isBlank()) {
            region = entity.getRegionName();
        }

        ChargingModeEnum mode = ChargingModeEnum.fromHuaweiCode(entity.getChargingMode());

        return UnifiedBillRecord.builder()
                .account(entity.getConfName())
                .provider(PROVIDER)
                .providerLabel("华为云")
                .region(region)
                .billMonth(billMonth)
                .productMain(entity.getServiceTypeName())
                .productSub(entity.getResourceTypeName())
                .chargingMode(mode.label())
                .chargingModeRaw(entity.getChargingMode() == null ? null : String.valueOf(entity.getChargingMode()))
                .resourceId(null)
                .resourceName(null)
                .tradeTime(entity.getTradeTime())
                .payAmount(BillAmountParser.add(cash, BillAmountParser.add(coupon, credit)))
                .officialAmount(official)
                .consumeAmount(consume)
                .discountAmount(discount)
                .cashPay(cash)
                .voucherPay(coupon)
                .creditPay(credit)
                .debtAmount(BillAmountParser.scale(BillAmountParser.toDecimal(entity.getDebtAmount())))
                .eraseAmount(BillAmountParser.scale(BillAmountParser.toDecimal(entity.getEraseAmount())))
                .writeoffAmount(BillAmountParser.scale(BillAmountParser.toDecimal(entity.getWriteoffAmount())))
                .amountSource(AmountSourceEnum.FEE_RECORD)
                .amountAnomaly(Boolean.FALSE)
                .build();
    }
}
