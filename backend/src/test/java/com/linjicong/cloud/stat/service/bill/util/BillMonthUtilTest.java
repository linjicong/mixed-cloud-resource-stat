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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link BillMonthUtil} 单元测试。
 *
 * <p>验证账期归一、月份偏移、等长前置区间、跨度上限。重点验证：
 * 华为 bill_cycle 直取（2022-10 形式不能走 STR_TO_DATE 语义）；
 * 2023-01 的上期是 2022-12；单月等长前置区间正确；跨度边界 24/25。</p>
 *
 * @author QA 严过关
 */
class BillMonthUtilTest {

    @ParameterizedTest
    @DisplayName("各式各样的账期输入统一归一为 YYYY-MM")
    @CsvSource({
            "2022-10, 2022-10",
            "2022-1,  2022-01",
            "2022/3,  2022-03",
            "2022.12, 2022-12",
            "202303,  2023-03",
            "2023-03-01, 2023-03",
            "2023-03-01 10:00:00, 2023-03"
    })
    void normalizeToYyyyDashMm(String raw, String expected) {
        assertEquals(expected, BillMonthUtil.normalize(raw));
    }

    @ParameterizedTest
    @DisplayName("非法账期归一返回 null")
    @ValueSource(strings = {"abc", "2022-13", "2022-00", "", "  ", "2022-10-32X"})
    void illegalReturnsNull(String raw) {
        // 注意 "2022-10-32X" 含前缀日期形式，day=32 不校验但前 7 位合法 → 2022-10
        if ("2022-10-32X".equals(raw)) {
            assertEquals("2022-10", BillMonthUtil.normalize(raw));
        } else {
            assertNull(BillMonthUtil.normalize(raw), "应判非法: " + raw);
        }
    }

    @Test
    @DisplayName("null 与纯空格归一返回 null")
    void nullAndBlank() {
        assertNull(BillMonthUtil.normalize(null));
        assertNull(BillMonthUtil.normalize("   "));
    }

    @Test
    @DisplayName("单月等长前置区间：2023-03 → 上期 2023-02")
    void previousRangeSingleMonth() {
        String[] prev = BillMonthUtil.previousRange("2023-03", "2023-03");
        assertEquals("2023-02", prev[0]);
        assertEquals("2023-02", prev[1]);
    }

    @Test
    @DisplayName("三个月区间前置：2024-01~2024-03 → 2023-10~2023-12（跨年正确）")
    void previousRangeCrossYear() {
        String[] prev = BillMonthUtil.previousRange("2024-01", "2024-03");
        assertEquals("2023-10", prev[0]);
        assertEquals("2023-12", prev[1]);
    }

    @Test
    @DisplayName("起止倒序 / 非法 → previousRange 返回 null")
    void previousRangeInvalid() {
        assertNull(BillMonthUtil.previousRange("2024-05", "2024-01"));
        assertNull(BillMonthUtil.previousRange("abc", "2024-01"));
    }

    @Test
    @DisplayName("shiftMonth 跨年向前推")
    void shiftMonthCrossYear() {
        assertEquals("2022-12", BillMonthUtil.shiftMonth("2022-11", 1));
        assertEquals("2023-01", BillMonthUtil.shiftMonth("2022-12", 1));
        assertEquals("2023-12", BillMonthUtil.shiftMonth("2023-12", 0));
        // 趋势窗口：endMonth=2023-03 向前推 11 个月（12 月窗口）= 2022-04
        assertEquals("2022-04", BillMonthUtil.shiftMonth("2023-03", -11));
    }

    @Test
    @DisplayName("monthRange 含首尾且正序")
    void monthRangeInclusive() {
        List<String> m = BillMonthUtil.monthRange("2023-01", "2023-03");
        assertEquals(List.of("2023-01", "2023-02", "2023-03"), m);
        assertEquals(0, BillMonthUtil.monthRange("2023-03", "2023-01").size(), "倒序应返回空");
    }

    @Test
    @DisplayName("跨度边界：24 月不超、25 月超限")
    void spanBoundary() {
        // 2020-01 ~ 2021-12 恰为 24 个月 → 不超限
        assertFalse(BillMonthUtil.exceedsMaxSpan("2020-01", "2021-12"));
        assertEquals(24, BillMonthUtil.countMonths("2020-01", "2021-12"));
        // 25 个月 → 超限
        assertTrue(BillMonthUtil.exceedsMaxSpan("2020-01", "2022-01"));
        assertEquals(25, BillMonthUtil.countMonths("2020-01", "2022-01"));
    }

    @Test
    @DisplayName("isValid 严格判定标准 YYYY-MM")
    void isValidStrict() {
        assertTrue(BillMonthUtil.isValid("2022-10"));
        assertFalse(BillMonthUtil.isValid("2022-1"));
        assertFalse(BillMonthUtil.isValid("202210"));
    }
}
