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
package com.linjicong.cloud.stat.controller.dto.bill;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 账号维度响应 VO（{@code GET /bill/accounts}）。
 *
 * <p>{@code rows} 按实付降序；{@code hasBillData=false} 的账号（如阿里云）永远排最后、
 * 占比计 0 且<b>不进占比分母</b>。</p>
 *
 * @author linjicong
 * @version 1.0.0
 * @date 2026-01-01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillAccountVO {

    /** 查询区间（含前置区间） */
    private Range range;

    /** 产生费用的账号实付合计（占比分母） */
    private BigDecimal totalPayAmount;

    /** 账号行 */
    @Builder.Default
    private List<Row> rows = new ArrayList<>();

    /** 按厂商汇总，供占比环形图的「按厂商」维度直接消费 */
    @Builder.Default
    private List<ProviderShare> byProvider = new ArrayList<>();

    /**
     * 查询区间（含前置区间，用于环比说明）。
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Range {
        /** 起 */
        private String startMonth;
        /** 止 */
        private String endMonth;
        /** 月份数 */
        private Integer monthCount;
        /** 等长前置区间 */
        private BillKpiVO.Range previousRange;
    }

    /**
     * 账号行。
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Row {
        /** 账号名 */
        private String account;
        /** 厂商标识 */
        private String provider;
        /** 厂商中文名 */
        private String providerLabel;
        /** 地域 */
        private String region;
        /** 账号是否启用（来自 cloud_conf.enable） */
        private Boolean enabled;
        /** 区间实付 */
        private BigDecimal payAmount;
        /** 区间原价 */
        private BigDecimal officialAmount;
        /** 区间优惠额 */
        private BigDecimal discountAmount;
        /** 占比（分母只含 hasBillData=true 的账号） */
        private BigDecimal ratio;
        /** 环比信息 */
        private ChainRatio chainRatio;
        /** 是否有账单数据（false 表示该厂商账单未采集） */
        private Boolean hasBillData;
        /** 近 6 个月实付，无数据为空数组 */
        @Builder.Default
        private List<BigDecimal> sparkline = new ArrayList<>();
        /** 近 6 个月月份骨架，与 sparkline 一一对应 */
        @Builder.Default
        private List<String> sparklineMonths = new ArrayList<>();
    }

    /**
     * 账号行环比信息。
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChainRatio {
        /** 环比百分比；不可比为 null */
        private BigDecimal rate;
        /** 是否可比 */
        private Boolean comparable;
        /** 上期实付 */
        private BigDecimal previousPayAmount;
    }

    /**
     * 按厂商汇总行。
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProviderShare {
        /** 厂商标识 */
        private String provider;
        /** 厂商中文名 */
        private String label;
        /** 实付合计 */
        private BigDecimal payAmount;
        /** 占比 */
        private BigDecimal ratio;
    }
}
