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
 * 总览 KPI 响应 VO（{@code GET /bill/kpi}）。
 *
 * <p>金额字段全部 {@link BigDecimal}，缺失统一 {@code 0.00}，<b>永不返回 null</b>；
 * 唯一允许 null 的是 {@code chainRatio.rate} 与 {@code discountRate}（表示不可比 / 无法计算）。</p>
 *
 * @author linjicong
 * @version 1.0.0
 * @date 2026-01-01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillKpiVO {

    /** 查询区间 */
    private Range range;

    /** 区间实付合计 */
    private BigDecimal totalPayAmount;

    /** 区间原价合计 */
    private BigDecimal totalOfficialAmount;

    /** 区间优惠额合计 */
    private BigDecimal totalDiscountAmount;

    /** 折扣率（优惠 / 原价 × 100），原价为 0 时为 null */
    private BigDecimal discountRate;

    /** 环比信息 */
    private ChainRatio chainRatio;

    /** 筛选范围内账号总数（含停用、含无账单的阿里云账号） */
    private Integer accountTotalCount;

    /** 区间内产生费用的账号数 */
    private Integer accountWithBillCount;

    /** 区间内无费用的账号数 */
    private Integer accountWithoutBillCount;

    /** TOP 产品 */
    private TopProduct topProduct;

    /** 各厂商账单可用性 */
    @Builder.Default
    private List<BillProviderAvailabilityVO> providerAvailability = new ArrayList<>();

    /** 数据质量信息 */
    private DataQuality dataQuality;

    /** 结束账期是否为服务器当前月（当月为进行中账期） */
    private boolean currentMonthInProgress;

    /**
     * 查询区间。
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
    }

    /**
     * 环比信息。
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChainRatio {
        /** 环比百分比；不可比时为 null（前端显示「不可比」） */
        private BigDecimal rate;
        /** 是否可比 */
        private Boolean comparable;
        /** 对比的前置区间 */
        private Range previousRange;
        /** 上期实付合计 */
        private BigDecimal previousPayAmount;
    }

    /**
     * TOP 产品摘要。
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TopProduct {
        /** 产品大类名称 */
        private String productMain;
        /** 厂商标识 */
        private String provider;
        /** 厂商中文名 */
        private String providerLabel;
        /** 该产品实付金额 */
        private BigDecimal payAmount;
        /** 占总额的百分比 */
        private BigDecimal ratio;
    }

    /**
     * 数据质量信息。
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DataQuality {
        /** 腾讯云字符串金额解析失败的行数 */
        private Integer amountAnomalyCount;
        /** 账期无法归一被丢弃的行数 */
        private Integer droppedRowCount;
    }
}
