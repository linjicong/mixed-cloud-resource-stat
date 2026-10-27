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
package com.linjicong.cloud.stat.service.bill.bo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 聚合查询结果 BO 集合。
 *
 * <p>六类结果行集中在一个文件中，各自对应 {@code BillAggregateMapper} 的一个查询方法。
 * 全部金额字段为 {@link BigDecimal}（SQL 侧为 DECIMAL，禁止映射为 Double 以免丢精度）。</p>
 *
 * @author linjicong
 * @version 1.0.0
 * @date 2026-01-01
 */
public final class RowBO {

    private RowBO() {
        // 工具类禁止实例化
    }

    /**
     * 区间汇总行：{@code sumByRange} / {@code sumDetail} 的返回。
     */
    @Data
    public static class AggregateRow {
        /** 实付合计 */
        private BigDecimal payAmount;
        /** 原价合计 */
        private BigDecimal officialAmount;
        /** 优惠额合计 */
        private BigDecimal discountAmount;
        /** 行数 */
        private Long rowCount;
    }

    /**
     * 按月的汇总行：{@code sumByMonth} 的返回。
     */
    @Data
    public static class MonthSumRow {
        /** 账期 YYYY-MM */
        private String billMonth;
        /** 实付合计 */
        private BigDecimal payAmount;
        /** 原价合计 */
        private BigDecimal officialAmount;
        /** 优惠额合计 */
        private BigDecimal discountAmount;
    }

    /**
     * 厂商 × 月份 汇总行：{@code sumByProviderAndMonth} 的返回。
     */
    @Data
    public static class ProviderMonthRow {
        /** 厂商标识 */
        private String provider;
        /** 账期 YYYY-MM */
        private String billMonth;
        /** 实付合计 */
        private BigDecimal payAmount;
    }

    /**
     * 账号维度汇总行：{@code sumByAccount} 的返回。
     */
    @Data
    public static class AccountSumRow {
        /** 账号名 */
        private String account;
        /** 厂商标识 */
        private String provider;
        /** 地域 */
        private String region;
        /** 实付合计 */
        private BigDecimal payAmount;
        /** 原价合计 */
        private BigDecimal officialAmount;
        /** 优惠额合计 */
        private BigDecimal discountAmount;
        /** 行数 */
        private Long rowCount;
    }

    /**
     * 产品维度汇总行：{@code sumByProduct} 的返回。
     */
    @Data
    public static class ProductSumRow {
        /** 产品大类 */
        private String productMain;
        /** 厂商标识 */
        private String provider;
        /** 账号名（dimension=account 时有值，按厂商维度聚合时为 null） */
        private String account;
        /** 实付合计 */
        private BigDecimal payAmount;
        /** 涉及账号数（COUNT(DISTINCT account)） */
        private Long accountCount;
        /** 涉及资源数（COUNT(DISTINCT resource_id)，全为空时为 null） */
        private Long resourceCount;
    }

    /**
     * 明细行：{@code pageDetail} 的返回，字段形状与 UNION ALL 投影列一一对应。
     */
    @Data
    public static class DetailRow {
        /** 来源表主键（用于拼装明细 id） */
        private Long srcPk;
        /** 账号名 */
        private String account;
        /** 厂商标识 */
        private String provider;
        /** 地域 */
        private String region;
        /** 账期 YYYY-MM */
        private String billMonth;
        /** 产品大类 */
        private String productMain;
        /** 产品子类 */
        private String productSub;
        /** 计费模式原始值（华为编码字符串 / 腾讯文本） */
        private String chargingModeRaw;
        /** 资源 ID */
        private String resourceId;
        /** 资源名称 */
        private String resourceName;
        /** 交易时间 */
        private String tradeTime;
        /** 实付 */
        private BigDecimal payAmount;
        /** 原价 */
        private BigDecimal officialAmount;
        /** 优惠后应付 */
        private BigDecimal consumeAmount;
        /** 优惠额 */
        private BigDecimal discountAmount;
        /** 现金 */
        private BigDecimal cashPay;
        /** 代金券 */
        private BigDecimal voucherPay;
        /** 信用/赠送账户 */
        private BigDecimal creditPay;
        /** 欠费 */
        private BigDecimal debtAmount;
        /** 抹零 */
        private BigDecimal eraseAmount;
        /** 核销 */
        private BigDecimal writeoffAmount;
        /** 金额来源（FEE_RECORD / BREAK_DOWN / RESOURCE_SUMMARY） */
        private String amountSource;
    }

    /**
     * cloud_conf 账号主数据行。
     */
    @Data
    public static class CloudConfRow {
        /** 账号名（cloud_conf.name） */
        private String name;
        /** 厂商原始值（cloud_conf.provider，可能是 HCloud / QCloud / ACloud） */
        private String provider;
        /** 地域 */
        private String region;
    }
}
