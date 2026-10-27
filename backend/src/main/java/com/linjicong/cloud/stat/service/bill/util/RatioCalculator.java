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
package com.linjicong.cloud.stat.service.bill.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 占比与环比计算器（架构决策 D5）。
 *
 * <p>环比返回 {@code {rate, comparable}} 双字段：上期 ≤ 0 时 {@code rate = null} 且
 * {@code comparable = false}，<b>绝不返回 0</b>，因为前端必须能区分「持平」与「不可比」。</p>
 *
 * @author linjicong
 * @version 1.0.0
 * @date 2026-01-01
 */
public final class RatioCalculator {

    /** 百分比精度 */
    private static final int SCALE = 2;

    /** 百分数基数 */
    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);

    private RatioCalculator() {
        // 工具类禁止实例化
    }

    /**
     * 环比计算结果。
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RatioResult {
        /** 环比百分比数值（如 12.34 表示 +12.34%）；不可比时为 null */
        private BigDecimal rate;

        /** 是否可比（上期 &gt; 0 才可比） */
        private boolean comparable;

        /**
         * 构造「不可比」结果。
         *
         * @return 不可比结果
         */
        public static RatioResult notComparable() {
            return new RatioResult(null, false);
        }

        /**
         * 构造可比结果。
         *
         * @param rate 百分比数值
         * @return 可比结果
         */
        public static RatioResult comparable(BigDecimal rate) {
            return new RatioResult(rate, true);
        }
    }

    /**
     * 计算环比。
     *
     * <p>公式：{@code (本期 - 上期) / 上期 × 100}，保留 2 位 HALF_UP。
     * 上期为 null 或 ≤ 0 时判定为不可比。</p>
     *
     * @param current  本期金额
     * @param previous 上期金额
     * @return 环比结果，永不为 null
     */
    public static RatioResult chainRatio(BigDecimal current, BigDecimal previous) {
        BigDecimal cur = BillAmountParser.nullToZero(current);
        BigDecimal prev = BillAmountParser.nullToZero(previous);
        if (prev.compareTo(BigDecimal.ZERO) <= 0) {
            return RatioResult.notComparable();
        }
        BigDecimal rate = cur.subtract(prev)
                .divide(prev, 6, BillAmountParser.ROUNDING)
                .multiply(HUNDRED)
                .setScale(SCALE, BillAmountParser.ROUNDING);
        return RatioResult.comparable(rate);
    }

    /**
     * 计算占比。
     *
     * @param part  分子
     * @param total 分母
     * @return 百分比数值（保留 2 位）；分母为 0 时返回 0.00
     */
    public static BigDecimal ratio(BigDecimal part, BigDecimal total) {
        BigDecimal p = BillAmountParser.nullToZero(part);
        BigDecimal t = BillAmountParser.nullToZero(total);
        if (t.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO.setScale(SCALE, BillAmountParser.ROUNDING);
        }
        return p.divide(t, 6, BillAmountParser.ROUNDING)
                .multiply(HUNDRED)
                .setScale(SCALE, BillAmountParser.ROUNDING);
    }

    /**
     * 计算折扣率（优惠额 / 原价 × 100）。
     *
     * @param discountAmount 优惠额
     * @param officialAmount 原价
     * @return 折扣率；原价为 0 时返回 null（表示无法计算，前端显示 --）
     */
    public static BigDecimal discountRate(BigDecimal discountAmount, BigDecimal officialAmount) {
        BigDecimal official = BillAmountParser.nullToZero(officialAmount);
        if (official.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }
        return BillAmountParser.nullToZero(discountAmount)
                .divide(official, 6, BillAmountParser.ROUNDING)
                .multiply(HUNDRED)
                .setScale(SCALE, BillAmountParser.ROUNDING);
    }
}
