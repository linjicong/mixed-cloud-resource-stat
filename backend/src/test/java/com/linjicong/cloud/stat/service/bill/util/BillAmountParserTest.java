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
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link BillAmountParser} 单元测试。
 *
 * <p>覆盖腾讯云 varchar 金额列的安全转数值口径：null / 空串 / '-' / '--' / 千分位逗号 /
 * 负数（退款）/ 真正非法串。重点验证「合法缺失不计数、解析失败才计数」的语义区分。</p>
 *
 * @author QA 严过关
 */
class BillAmountParserTest {

    @Test
    @DisplayName("合法缺失（null/空串/-/--）应返回 null 且不计入异常")
    void legalMissingReturnsNullNoAnomaly() {
        BillAmountParser.resetAnomalyCount();
        assertNull(BillAmountParser.toDecimal((String) null));
        assertNull(BillAmountParser.toDecimal(""));
        assertNull(BillAmountParser.toDecimal("   "));
        assertNull(BillAmountParser.toDecimal("-"));
        assertNull(BillAmountParser.toDecimal("--"));
        assertEquals(0, BillAmountParser.anomalyCount(), "合法缺失不应增加异常计数");
    }

    @Test
    @DisplayName("千分位逗号应被去除后正确解析")
    void thousandSeparatorParsed() {
        BillAmountParser.resetAnomalyCount();
        BigDecimal v = BillAmountParser.toDecimal("1,234.56");
        assertNotNull(v);
        assertEquals(0, new BigDecimal("1234.56").compareTo(v));
        assertEquals(0, BillAmountParser.anomalyCount());
    }

    @Test
    @DisplayName("负金额（退款）应保留符号，不得取绝对值")
    void negativeKeepsSign() {
        BillAmountParser.resetAnomalyCount();
        BigDecimal v = BillAmountParser.toDecimal("-55.20");
        assertNotNull(v);
        assertEquals(0, new BigDecimal("-55.20").compareTo(v));
        assertTrue(v.signum() < 0, "退款负数必须保留负号");
    }

    @Test
    @DisplayName("真正无法解析的串应计异常并返回 null")
    void garbageCountedAsAnomaly() {
        BillAmountParser.resetAnomalyCount();
        assertNull(BillAmountParser.toDecimal("abc"));
        assertEquals(1, BillAmountParser.anomalyCount());
        assertNull(BillAmountParser.toDecimal("12.3.4"));
        assertEquals(2, BillAmountParser.anomalyCount(), "两次失败累计");
    }

    @Test
    @DisplayName("Object 重载：Double 走 toString 路径避免二进制误差")
    void objectDoublePath() {
        BillAmountParser.resetAnomalyCount();
        BigDecimal v = BillAmountParser.toDecimal((Object) 123.456D);
        assertNotNull(v);
        // 123.456D 的 double 字面量转字符串后应能解析
        assertEquals(0, new BigDecimal("123.456").compareTo(v));
        BigDecimal bd = BillAmountParser.toDecimal((Object) new BigDecimal("7.00"));
        assertEquals(0, new BigDecimal("7.00").compareTo(bd));
    }

    @Nested
    @DisplayName("scale / nullToZero / isZero 兜底")
    class Helpers {

        @Test
        @DisplayName("scale 统一 2 位 HALF_UP，null 归零")
        void scaleHalfUp() {
            assertEquals(new BigDecimal("1.24"), BillAmountParser.scale(new BigDecimal("1.235")));
            assertEquals(new BigDecimal("0.00"), BillAmountParser.scale(null));
        }

        @Test
        @DisplayName("isZero 对 null 与 0.00 均视为零")
        void isZeroCovers() {
            assertTrue(BillAmountParser.isZero(null));
            assertTrue(BillAmountParser.isZero(new BigDecimal("0.00")));
            assertFalse(BillAmountParser.isZero(new BigDecimal("-0.01")));
        }
    }
}
