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
package com.linjicong.cloud.stat.service.bill.bo;

/**
 * 统一计费模式枚举（架构决策 D8）。
 *
 * <p>华为云 {@code charging_mode} 为 Integer 编码，腾讯云 {@code pay_mode_name} 已是文本。
 * 归一化层在此收敛映射，前端只消费 {@link #label()} 文本，不再写厂商分支。
 * 未识别的编码一律归入「其他」，保证不丢数据。</p>
 *
 * @author linjicong
 * @version 1.0.0
 * @date 2026-01-01
 */
public enum ChargingModeEnum {

    /** 包年包月（华为编码 1） */
    PREPAID("包年包月"),

    /** 按需（华为编码 0） */
    POSTPAID("按需"),

    /** 预留实例（华为编码 3） */
    RESERVED("预留实例"),

    /** 资源包（华为编码 4） */
    RESOURCE_PACK("资源包"),

    /** 节省计划（华为编码 10） */
    SAVING_PLAN("节省计划"),

    /** 其他（未识别编码 / 空值） */
    OTHER("其他");

    /** 统一中文标签 */
    private final String label;

    ChargingModeEnum(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }

    /**
     * 华为云 {@code charging_mode} Integer 编码 → 统一计费模式。
     *
     * <p>编码映射：0=按需、1=包年包月、3=预留实例、4=资源包、10=节省计划，其余归「其他」。</p>
     *
     * @param code 原始编码，允许为 null
     * @return 对应枚举，绝不返回 null
     */
    public static ChargingModeEnum fromHuaweiCode(Integer code) {
        if (code == null) {
            return OTHER;
        }
        return switch (code) {
            case 0 -> POSTPAID;
            case 1 -> PREPAID;
            case 3 -> RESERVED;
            case 4 -> RESOURCE_PACK;
            case 10 -> SAVING_PLAN;
            default -> OTHER;
        };
    }

    /**
     * 华为云编码的字符串形式 → 统一计费模式（XML 投影回来的是字符串）。
     *
     * @param raw 编码字符串，允许为 null / 空白 / 非数字
     * @return 对应枚举，绝不返回 null
     */
    public static ChargingModeEnum fromHuaweiCodeText(String raw) {
        if (raw == null || raw.isBlank()) {
            return OTHER;
        }
        try {
            return fromHuaweiCode(Integer.valueOf(raw.trim()));
        } catch (NumberFormatException e) {
            return OTHER;
        }
    }

    /**
     * 腾讯云 {@code PayModeName} 文本 → 统一计费模式。
     *
     * @param text 原始文本，允许为 null
     * @return 对应枚举，绝不返回 null
     */
    public static ChargingModeEnum fromTencentText(String text) {
        if (text == null || text.isBlank()) {
            return OTHER;
        }
        String s = text.trim();
        String lower = s.toLowerCase();
        // 顺序重要：先判定更具体的「节省计划 / 资源包 / 预留实例」，最后才是包年包月与按需
        if (s.contains("节省计划") || lower.contains("savingsplan") || lower.contains("savings plan")
                || "sp".equals(lower)) {
            return SAVING_PLAN;
        }
        if (s.contains("资源包") || lower.contains("resourcepack") || lower.contains("resource pack")
                || "rp".equals(lower)) {
            return RESOURCE_PACK;
        }
        if (s.contains("预留") || lower.contains("ri") || lower.contains("reserved")) {
            return RESERVED;
        }
        if (s.contains("包年包月") || s.contains("预付费") || lower.contains("prepaid")) {
            return PREPAID;
        }
        if (s.contains("按量计费") || s.contains("后付费") || s.contains("按需") || lower.contains("postpaid")) {
            return POSTPAID;
        }
        return OTHER;
    }

    /**
     * 统一标签文本 → 枚举（用于明细筛选参数归一）。
     *
     * @param label 统一中文标签，允许为 null
     * @return 匹配枚举；无法匹配返回 null（表示「不过滤」而非「其他」）
     */
    public static ChargingModeEnum fromLabel(String label) {
        if (label == null || label.isBlank()) {
            return null;
        }
        String s = label.trim();
        for (ChargingModeEnum value : values()) {
            if (value.label.equals(s)) {
                return value;
            }
        }
        return null;
    }
}
