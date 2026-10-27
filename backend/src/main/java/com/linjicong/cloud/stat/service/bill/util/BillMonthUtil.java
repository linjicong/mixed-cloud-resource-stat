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

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 账期工具类。
 *
 * <p>账期在全链路统一为 {@code YYYY-MM} 字符串。三张表的原始账期格式由各云厂商 SDK 决定，
 * 不一定一致，因此所有入参都必须先过 {@link #normalize(String)}。实现全部基于
 * {@link YearMonth}，不使用 {@code java.util.Date}。</p>
 *
 * @author linjicong
 * @version 1.0.0
 * @date 2026-01-01
 */
public final class BillMonthUtil {

    /** 统一输出格式 */
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM");

    /** 单月最大跨度保护（24 个月），与前端账期选择器上限一致 */
    public static final int MAX_MONTH_SPAN = 24;

    /** 匹配 2025-03 / 2025-3 / 2025/03 / 2025/3 / 2025.03 */
    private static final Pattern SEPARATED = Pattern.compile("^(\\d{4})[-/.](\\d{1,2})$");

    /** 匹配 2025-03-01 / 2025/03/01 等带日期的形式 */
    private static final Pattern WITH_DAY = Pattern.compile("^(\\d{4})[-/.](\\d{1,2})[-/.](\\d{1,2})");

    /** 匹配 202503 */
    private static final Pattern COMPACT = Pattern.compile("^(\\d{4})(\\d{2})$");

    private BillMonthUtil() {
        // 工具类禁止实例化
    }

    /**
     * 账期归一：把各式各样的原始账期统一为 {@code YYYY-MM}。
     *
     * <p>可识别：{@code 2025-03}、{@code 2025-3}、{@code 202503}、{@code 2025/03}、
     * {@code 2025-03-01}、{@code 2025-03-01 10:00:00}。无法解析返回 {@code null}，
     * 调用方应将该行丢弃并计入 droppedRowCount。</p>
     *
     * @param raw 原始账期字符串，允许为 null
     * @return {@code YYYY-MM} 或 null
     */
    public static String normalize(String raw) {
        if (raw == null) {
            return null;
        }
        String s = raw.trim();
        if (s.isEmpty()) {
            return null;
        }

        // 1) 2025-03-01 形式：直接取前 7 位
        Matcher withDay = WITH_DAY.matcher(s);
        if (withDay.find()) {
            return build(withDay.group(1), withDay.group(2));
        }

        // 2) 2025-03 / 2025-3 / 2025/03 / 2025.03
        Matcher separated = SEPARATED.matcher(s);
        if (separated.matches()) {
            return build(separated.group(1), separated.group(2));
        }

        // 3) 202503
        Matcher compact = COMPACT.matcher(s);
        if (compact.matches()) {
            return build(compact.group(1), compact.group(2));
        }

        return null;
    }

    /**
     * 构造合法账期字符串，并对月份做 1..12 校验。
     *
     * @param year  四位年份字符串
     * @param month 月份字符串（可 1 位）
     * @return {@code YYYY-MM}；非法返回 null
     */
    private static String build(String year, String month) {
        try {
            int y = Integer.parseInt(year);
            int m = Integer.parseInt(month);
            if (m < 1 || m > 12) {
                return null;
            }
            return YearMonth.of(y, m).format(OUTPUT_FORMAT);
        } catch (NumberFormatException | DateTimeParseException e) {
            return null;
        }
    }

    /**
     * 判断账期字符串是否已是合法 {@code YYYY-MM}。
     *
     * @param month 账期
     * @return true 表示合法
     */
    public static boolean isValid(String month) {
        if (month == null || month.length() != 7) {
            return false;
        }
        return normalize(month) != null && normalize(month).equals(month);
    }

    /**
     * 按月偏移。
     *
     * @param month 起始账期 {@code YYYY-MM}
     * @param delta 偏移月数，负数为向前推
     * @return 偏移后的 {@code YYYY-MM}；入参非法返回 null
     */
    public static String shiftMonth(String month, int delta) {
        String normalized = normalize(month);
        if (normalized == null) {
            return null;
        }
        YearMonth ym = YearMonth.parse(normalized, OUTPUT_FORMAT);
        return ym.plusMonths(delta).format(OUTPUT_FORMAT);
    }

    /**
     * 生成含首尾的月份区间列表。
     *
     * @param start 起始账期 {@code YYYY-MM}
     * @param end   结束账期 {@code YYYY-MM}，含
     * @return 月份列表（按时间正序）；入参非法或 start &gt; end 时返回空列表
     */
    public static List<String> monthRange(String start, String end) {
        List<String> months = new ArrayList<>();
        String s = normalize(start);
        String e = normalize(end);
        if (s == null || e == null) {
            return months;
        }
        YearMonth from = YearMonth.parse(s, OUTPUT_FORMAT);
        YearMonth to = YearMonth.parse(e, OUTPUT_FORMAT);
        if (from.isAfter(to)) {
            return months;
        }
        YearMonth cursor = from;
        while (!cursor.isAfter(to)) {
            months.add(cursor.format(OUTPUT_FORMAT));
            cursor = cursor.plusMonths(1);
        }
        return months;
    }

    /**
     * 计算等长前置区间。
     *
     * <p>语义：区间长度 N 个月，{@code prevEnd = start 前一个月}，
     * {@code prevStart = prevEnd 前推 N-1 个月}。例如 {@code 2025-01 ~ 2025-03}
     * 的前置区间为 {@code 2024-10 ~ 2024-12}。</p>
     *
     * @param start 本期起
     * @param end   本期止
     * @return 长度为 2 的数组 {@code [prevStart, prevEnd]}；入参非法返回 null
     */
    public static String[] previousRange(String start, String end) {
        String s = normalize(start);
        String e = normalize(end);
        if (s == null || e == null) {
            return null;
        }
        YearMonth from = YearMonth.parse(s, OUTPUT_FORMAT);
        YearMonth to = YearMonth.parse(e, OUTPUT_FORMAT);
        if (from.isAfter(to)) {
            return null;
        }
        int monthCount = (int) (to.getYear() * 12L + to.getMonthValue()
                - from.getYear() * 12L - from.getMonthValue()) + 1;
        YearMonth prevEnd = from.minusMonths(1);
        YearMonth prevStart = prevEnd.minusMonths(monthCount - 1L);
        return new String[]{prevStart.format(OUTPUT_FORMAT), prevEnd.format(OUTPUT_FORMAT)};
    }

    /**
     * 统计区间包含的月份数。
     *
     * @param start 起
     * @param end   止
     * @return 月份数；入参非法或 start &gt; end 时返回 0
     */
    public static int countMonths(String start, String end) {
        return monthRange(start, end).size();
    }

    /**
     * 判断账期跨度是否超过 {@link #MAX_MONTH_SPAN}。
     *
     * @param start 起
     * @param end   止
     * @return true 表示超出上限
     */
    public static boolean exceedsMaxSpan(String start, String end) {
        int count = countMonths(start, end);
        return count > MAX_MONTH_SPAN;
    }

    /**
     * 取当前月账期（服务器时区）。
     *
     * @return {@code YYYY-MM}
     */
    public static String currentMonth() {
        return YearMonth.now().format(OUTPUT_FORMAT);
    }
}
