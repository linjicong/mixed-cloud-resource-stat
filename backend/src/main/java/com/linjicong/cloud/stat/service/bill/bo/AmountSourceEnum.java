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
 * 金额来源表枚举。
 *
 * <p>用于标记明细行金额来自哪张物理表，前端据此渲染来源角标。
 * 三个取值分别对应：华为云费用记录表、华为云月度摊销明细表、腾讯云账单资源摘要表。</p>
 *
 * @author linjicong
 * @version 1.0.0
 * @date 2026-01-01
 */
public enum AmountSourceEnum {

    /** 华为云费用记录（现金流口径，本模块唯一参与聚合的华为侧来源） */
    FEE_RECORD("FEE", "费用记录"),

    /** 华为云月度账单摊销明细（仅作明细补充源，不参与任何聚合） */
    BREAK_DOWN("BD", "月度明细"),

    /** 腾讯云账单资源摘要 */
    RESOURCE_SUMMARY("QCL", "资源账单");

    /** 明细行 id 前缀，用于前端 row-key 去重（三表 pk 会重复） */
    private final String idPrefix;

    /** 中文标签，由后端直接下发给前端，避免前端维护厂商分支 */
    private final String label;

    AmountSourceEnum(String idPrefix, String label) {
        this.idPrefix = idPrefix;
        this.label = label;
    }

    public String getIdPrefix() {
        return idPrefix;
    }

    public String getLabel() {
        return label;
    }

    /**
     * 按名称安全解析来源枚举。
     *
     * @param name 枚举名称，允许为 null
     * @return 匹配的枚举；无法匹配时返回 {@link #FEE_RECORD} 作为兜底，绝不返回 null
     */
    public static AmountSourceEnum parseOrDefault(String name) {
        if (name == null) {
            return FEE_RECORD;
        }
        String trimmed = name.trim();
        for (AmountSourceEnum value : values()) {
            if (value.name().equalsIgnoreCase(trimmed)) {
                return value;
            }
        }
        return FEE_RECORD;
    }
}
