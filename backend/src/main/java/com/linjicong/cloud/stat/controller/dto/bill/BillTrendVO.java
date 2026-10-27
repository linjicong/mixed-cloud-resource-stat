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
 * 历史趋势响应 VO（{@code GET /bill/trend}）。
 *
 * <p>{@code months} 数组长度固定等于区间月份数，某月无数据补 {@code 0.00} 而非 null，
 * 前端不做补齐；{@code chainRatios[0]} 恒为 null。</p>
 *
 * @author linjicong
 * @version 1.0.0
 * @date 2026-01-01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillTrendVO {

    /** 查询区间 */
    private BillKpiVO.Range range;

    /** 月份骨架，长度 = 区间月份数 */
    @Builder.Default
    private List<String> months = new ArrayList<>();

    /** 按厂商的金额序列 */
    @Builder.Default
    private List<ProviderSeries> series = new ArrayList<>();

    /** 各月合计金额 */
    @Builder.Default
    private List<BigDecimal> totalAmounts = new ArrayList<>();

    /** 各月环比，第一个恒为 null；上期为 0 也返回 null */
    @Builder.Default
    private List<BigDecimal> chainRatios = new ArrayList<>();

    /** 各厂商账单可用性 */
    @Builder.Default
    private List<BillProviderAvailabilityVO> providerAvailability = new ArrayList<>();

    /** 区间内是否有任何数据（全 0 视为无数据，前端画占位图） */
    private boolean hasData;

    /**
     * 单个厂商的金额序列。
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProviderSeries {
        /** 厂商标识 */
        private String provider;
        /** 厂商中文名 */
        private String label;
        /** 与 {@code months} 一一对应的金额，无数据为 0.00 */
        @Builder.Default
        private List<BigDecimal> amounts = new ArrayList<>();
    }
}
