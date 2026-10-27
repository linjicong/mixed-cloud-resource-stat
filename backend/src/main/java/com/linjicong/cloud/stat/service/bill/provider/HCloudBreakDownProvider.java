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
import com.linjicong.cloud.stat.dao.entity.hcloud.HCloudBillsMonthlyBreakDown;
import com.linjicong.cloud.stat.dao.mapper.hcloud.HCloudBillsMonthlyBreakDownMapper;
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
 * 华为云月度账单摊销明细归一化 Provider（<b>仅作明细补充源</b>）。
 *
 * <p><b>重要约束（决策 D6）</b>：本表的摊销金额<b>完全不参与</b> KPI / 趋势 / 占比 / TOP
 * 四类聚合。它只在明细列表中作为补充来源出现，并打 {@code BREAK_DOWN} 角标。
 * 原因：费用记录是「现金流视角」，摊销是「成本归属视角」，两者混算必然导致总额翻倍。</p>
 *
 * <p>摊销实付 = 摊销现金 + 摊销代金券 + 摊销信用。本表无原价字段，
 * 因此 {@code officialAmount} 用 {@code consumeAmount} 占位且不参与优惠计算。</p>
 *
 * @author linjicong
 * @version 1.0.0
 * @date 2026-01-01
 */
@Slf4j
@Component
public class HCloudBreakDownProvider implements BillRecordProvider {

    /** 厂商标识 */
    public static final String PROVIDER = "huawei";

    private final HCloudBillsMonthlyBreakDownMapper breakDownMapper;

    public HCloudBreakDownProvider(HCloudBillsMonthlyBreakDownMapper breakDownMapper) {
        this.breakDownMapper = breakDownMapper;
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

        QueryWrapper<HCloudBillsMonthlyBreakDown> wrapper = new QueryWrapper<>();
        if (condition.getStartMonth() != null && !condition.getStartMonth().isBlank()) {
            wrapper.ge("LEFT(TRIM(bill_cycle), 7)", condition.getStartMonth());
        }
        if (condition.getEndMonth() != null && !condition.getEndMonth().isBlank()) {
            wrapper.le("LEFT(TRIM(bill_cycle), 7)", condition.getEndMonth());
        }
        if (condition.getAccounts() != null && !condition.getAccounts().isEmpty()) {
            wrapper.in("conf_name", condition.getAccounts());
        }

        List<HCloudBillsMonthlyBreakDown> entities = breakDownMapper.selectList(wrapper);
        for (HCloudBillsMonthlyBreakDown entity : entities) {
            try {
                String billMonth = BillMonthUtil.normalize(entity.getBillCycle());
                if (billMonth == null) {
                    continue;
                }
                result.add(toUnified(entity, billMonth));
            } catch (RuntimeException e) {
                log.warn("华为云月度明细归一行失败，已跳过，pk={}", entity.getPk(), e);
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
    private UnifiedBillRecord toUnified(HCloudBillsMonthlyBreakDown entity, String billMonth) {
        BigDecimal cash = BillAmountParser.scale(BillAmountParser.toDecimal(entity.getAmortizedCashAmount()));
        BigDecimal coupon = BillAmountParser.scale(BillAmountParser.toDecimal(entity.getAmortizedCouponAmount()));
        BigDecimal credit = BillAmountParser.scale(BillAmountParser.toDecimal(entity.getAmortizedCreditAmount()));
        BigDecimal consume = BillAmountParser.scale(BillAmountParser.toDecimal(entity.getConsumeAmount()));

        String region = entity.getConfRegion();
        if (region == null || region.isBlank()) {
            region = entity.getRegionName();
        }

        // 本表无计费模式编码字段，统一归「其他」
        ChargingModeEnum mode = ChargingModeEnum.OTHER;

        String productSub = entity.getSubResourceTypeName();
        if (productSub == null || productSub.isBlank()) {
            productSub = entity.getResourceTypeName();
        }

        return UnifiedBillRecord.builder()
                .account(entity.getConfName())
                .provider(PROVIDER)
                .providerLabel("华为云")
                .region(region)
                .billMonth(billMonth)
                .productMain(entity.getServiceTypeName())
                .productSub(productSub)
                .chargingMode(mode.label())
                .chargingModeRaw(null)
                .resourceId(entity.getResourceId())
                .resourceName(entity.getResourceName())
                .tradeTime(entity.getEffectiveTime())
                .payAmount(BillAmountParser.add(cash, BillAmountParser.add(coupon, credit)))
                .officialAmount(consume)
                .consumeAmount(consume)
                .discountAmount(BigDecimal.ZERO.setScale(BillAmountParser.SCALE, BillAmountParser.ROUNDING))
                .cashPay(cash)
                .voucherPay(coupon)
                .creditPay(credit)
                .debtAmount(BigDecimal.ZERO.setScale(BillAmountParser.SCALE, BillAmountParser.ROUNDING))
                .eraseAmount(BigDecimal.ZERO.setScale(BillAmountParser.SCALE, BillAmountParser.ROUNDING))
                .writeoffAmount(BigDecimal.ZERO.setScale(BillAmountParser.SCALE, BillAmountParser.ROUNDING))
                .amountSource(AmountSourceEnum.BREAK_DOWN)
                .amountAnomaly(Boolean.FALSE)
                .build();
    }
}
