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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link RatioCalculator} 单元测试。
 *
 * <p>重点验证硬约束：环比上期为 0 或负数时 {@code comparable=false 且 rate=null}，
 * 绝不能返回 0（前端要区分「持平」与「不可比」）；占比分母为 0 时返回 0.00。</p>
 *
 * @author QA 严过关
 */
class RatioCalculatorTest {

    @Test
    @DisplayName("环比：(本期-上期)/上期×100，正常可比")
    void chainRatioNormal() {
        RatioCalculator.RatioResult r =
                RatioCalculator.chainRatio(new BigDecimal("110"), new BigDecimal("100"));
        assertTrue(r.isComparable());
        assertEquals(new BigDecimal("10.00"), r.getRate());
    }

    @Test
    @DisplayName("环比：上期为 0 → comparable=false 且 rate=null（不得为 0）")
    void chainRatioPrevZero() {
        RatioCalculator.RatioResult r =
                RatioCalculator.chainRatio(new BigDecimal("100"), BigDecimal.ZERO);
        assertFalse(r.isComparable());
        assertNull(r.getRate(), "不可比时 rate 必须为 null，不能是 0.00");
    }

    @Test
    @DisplayName("环比：上期为负数 → comparable=false 且 rate=null")
    void chainRatioPrevNegative() {
        RatioCalculator.RatioResult r =
                RatioCalculator.chainRatio(new BigDecimal("100"), new BigDecimal("-5"));
        assertFalse(r.isComparable());
        assertNull(r.getRate());
    }

    @Test
    @DisplayName("环比：上期为 null → comparable=false 且 rate=null")
    void chainRatioPrevNull() {
        RatioCalculator.RatioResult r = RatioCalculator.chainRatio(new BigDecimal("100"), null);
        assertFalse(r.isComparable());
        assertNull(r.getRate());
    }

    @Test
    @DisplayName("环比：本期为负（退款月）、上期为正 → 可比且为负百分比")
    void chainRatioCurrentNegative() {
        RatioCalculator.RatioResult r =
                RatioCalculator.chainRatio(new BigDecimal("-10"), new BigDecimal("100"));
        assertTrue(r.isComparable());
        assertEquals(new BigDecimal("-110.00"), r.getRate());
    }

    @Test
    @DisplayName("占比：分母为 0 返回 0.00")
    void ratioZeroDenominator() {
        BigDecimal v = RatioCalculator.ratio(new BigDecimal("5"), BigDecimal.ZERO);
        assertEquals(new BigDecimal("0.00"), v);
    }

    @Test
    @DisplayName("占比：正常 part/total×100")
    void ratioNormal() {
        BigDecimal v = RatioCalculator.ratio(new BigDecimal("25"), new BigDecimal("100"));
        assertEquals(new BigDecimal("25.00"), v);
    }

    @Test
    @DisplayName("占比：负 part（退款）允许为负占比")
    void ratioNegativePart() {
        BigDecimal v = RatioCalculator.ratio(new BigDecimal("-25"), new BigDecimal("100"));
        assertEquals(new BigDecimal("-25.00"), v);
    }

    @Test
    @DisplayName("折扣率：原价为 0 返回 null，正常返回 discount/official×100")
    void discountRateCases() {
        assertNull(RatioCalculator.discountRate(new BigDecimal("10"), BigDecimal.ZERO));
        BigDecimal v = RatioCalculator.discountRate(new BigDecimal("30"), new BigDecimal("100"));
        assertNotNull(v);
        assertEquals(new BigDecimal("30.00"), v);
    }
}
