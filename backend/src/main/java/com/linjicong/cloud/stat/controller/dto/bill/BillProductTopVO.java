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
 * 产品 TOP 响应 VO（{@code GET /bill/products/top}）。
 *
 * <p>闭环校验：{@code Σ rows.payAmount + others.payAmount == totalPayAmount}。</p>
 *
 * @author linjicong
 * @version 1.0.0
 * @date 2026-01-01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillProductTopVO {

    /** 查询区间 */
    private BillKpiVO.Range range;

    /** 维度：provider / account */
    private String dimension;

    /** 区间实付总额 */
    private BigDecimal totalPayAmount;

    /** TOP 行，按实付降序，长度 ≤ topN */
    @Builder.Default
    private List<Row> rows = new ArrayList<>();

    /** topN 之外的产品合计，保证 rows + others = total */
    private Others others;

    /**
     * TOP 产品行。
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Row {
        /** 排名，从 1 开始 */
        private Integer rank;
        /** 产品大类 */
        private String productMain;
        /** 厂商标识 */
        private String provider;
        /** 厂商中文名 */
        private String providerLabel;
        /** 账号名（dimension=account 时有值） */
        private String account;
        /** 实付金额 */
        private BigDecimal payAmount;
        /** 占比 */
        private BigDecimal ratio;
        /** 涉及账号数 */
        private Long accountCount;
        /** 涉及资源数；华为费用记录无资源 ID 时为 null（前端显示 —） */
        private Long resourceCount;
    }

    /**
     * TOP 之外的产品汇总。
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Others {
        /** 合计金额 */
        private BigDecimal payAmount;
        /** 合计占比 */
        private BigDecimal ratio;
        /** 产品数 */
        private Integer productCount;
    }
}
