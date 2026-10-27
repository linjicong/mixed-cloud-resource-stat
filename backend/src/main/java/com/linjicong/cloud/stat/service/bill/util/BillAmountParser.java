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

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 金额安全解析工具（架构决策 D3）。
 *
 * <p>腾讯云账单表的金额列全是 {@code varchar}，可能包含空串、{@code "-"}、千分位逗号、
 * 甚至脏数据。本工具是 Java 侧唯一权威口径，必须与 XML 里的
 * {@code CAST(NULLIF(REPLACE(NULLIF(TRIM(col),''),',',''),'-') AS DECIMAL(18,4))}
 * 表达式行为一致。</p>
 *
 * <p><b>语义区分</b>：空串、{@code "-"}、{@code "--"}、null 属于「合法缺失」，返回 null
 * 且<b>不计入</b>异常计数；只有真正解析失败（如 {@code "abc"}）才计入
 * {@link #anomalyCount()}，用于 dataQuality.amountAnomalyCount。</p>
 *
 * <p>行为契约：</p>
 * <ol>
 *   <li>{@code null} / 空串 / {@code "-"} / {@code "--"} → 返回 null，不计数；</li>
 *   <li>含千分位逗号 → 去掉后解析；</li>
 *   <li>无法解析 → 计入异常计数并返回 null。</li>
 * </ol>
 *
 * @author linjicong
 * @version 1.0.0
 * @date 2026-01-01
 */
public final class BillAmountParser {

    /** 金额统一精度 */
    public static final int SCALE = 2;

    /** 金额统一舍入模式 */
    public static final RoundingMode ROUNDING = RoundingMode.HALF_UP;

    /**
     * 金额转换失败计数。
     *
     * <p>使用 {@link ThreadLocal} 而非全局静态计数器：后者在并发请求下会互相串扰
     * （A 请求 reset 会把 B 请求累计的计数清零，导致 dataQuality 数字失真）。
     * 每个请求线程独立计数，{@link #resetAnomalyCount()} 会移除变量避免线程池复用泄漏。</p>
     */
    private static final ThreadLocal<Integer> ANOMALY_COUNTER = ThreadLocal.withInitial(() -> 0);

    private BillAmountParser() {
        // 工具类禁止实例化
    }

    /**
     * 安全解析字符串金额。
     *
     * @param raw 原始字符串
     * @return 解析结果（保留原始精度，未做 scale 处理）；缺失或解析失败返回 null
     */
    public static BigDecimal toDecimal(String raw) {
        if (raw == null) {
            return null;
        }
        String s = raw.trim();
        if (s.isEmpty() || "-".equals(s) || "--".equals(s)) {
            // 合法缺失，不计入异常
            return null;
        }
        s = s.replace(",", "");
        try {
            return new BigDecimal(s);
        } catch (NumberFormatException e) {
            incrementAnomaly();
            return null;
        }
    }

    /**
     * 安全解析任意对象金额（主要服务于华为云 double 列与 MyBatis 数值映射）。
     *
     * @param raw 原始对象，支持 Number / String / null
     * @return 解析结果；无法解析返回 null
     */
    public static BigDecimal toDecimal(Object raw) {
        if (raw == null) {
            return null;
        }
        if (raw instanceof BigDecimal bigDecimal) {
            return bigDecimal;
        }
        if (raw instanceof Number number) {
            // 华为云 double 列：先转字符串再转 BigDecimal，避免 double 二进制误差直接进 BigDecimal
            return toDecimal(number.toString());
        }
        if (raw instanceof String str) {
            return toDecimal(str);
        }
        incrementAnomaly();
        return null;
    }

    /**
     * 安全解析并保证非 null（缺失按 0 处理）。
     *
     * @param raw 原始字符串
     * @return 金额，永不为 null
     */
    public static BigDecimal toDecimalOrZero(String raw) {
        BigDecimal value = toDecimal(raw);
        return value == null ? BigDecimal.ZERO : value;
    }

    /**
     * 安全解析并保证非 null（缺失按 0 处理）。
     *
     * @param raw 原始对象
     * @return 金额，永不为 null
     */
    public static BigDecimal toDecimalOrZero(Object raw) {
        BigDecimal value = toDecimal(raw);
        return value == null ? BigDecimal.ZERO : value;
    }

    /**
     * 空值兜底为 0。
     *
     * @param value 金额
     * @return 原值或 {@link BigDecimal#ZERO}
     */
    public static BigDecimal nullToZero(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    /**
     * 统一 scale=2 HALF_UP。
     *
     * @param value 金额，允许为 null
     * @return 归一后的金额，永不为 null
     */
    public static BigDecimal scale(BigDecimal value) {
        return nullToZero(value).setScale(SCALE, ROUNDING);
    }

    /**
     * 求和两个金额并统一精度。
     *
     * @param a 金额 a
     * @param b 金额 b
     * @return a + b（scale=2）
     */
    public static BigDecimal add(BigDecimal a, BigDecimal b) {
        return scale(nullToZero(a).add(nullToZero(b)));
    }

    /**
     * 相减两个金额（a - b）并统一精度。
     *
     * @param a 被减数
     * @param b 减数
     * @return a - b（scale=2）
     */
    public static BigDecimal subtract(BigDecimal a, BigDecimal b) {
        return scale(nullToZero(a).subtract(nullToZero(b)));
    }

    /**
     * 累加一次异常计数（线程内）。
     */
    private static void incrementAnomaly() {
        Integer current = ANOMALY_COUNTER.get();
        ANOMALY_COUNTER.set((current == null ? 0 : current) + 1);
    }

    /**
     * 当前线程累计的金额解析异常行数。
     *
     * @return 异常计数
     */
    public static int anomalyCount() {
        Integer count = ANOMALY_COUNTER.get();
        return count == null ? 0 : count;
    }

    /**
     * 重置并移除当前线程的异常计数（每次请求入口调用，避免跨请求累计与线程池泄漏）。
     */
    public static void resetAnomalyCount() {
        ANOMALY_COUNTER.remove();
    }

    /**
     * 判断金额是否为 0（null 视为 0）。
     *
     * @param value 金额
     * @return true 表示零或空
     */
    public static boolean isZero(BigDecimal value) {
        return nullToZero(value).compareTo(BigDecimal.ZERO) == 0;
    }
}
