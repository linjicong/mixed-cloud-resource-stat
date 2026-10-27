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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 统一账单模型（架构决策：三层归一的核心数据结构）。
 *
 * <p>三张物理表（华为云费用记录 / 华为云月度摊销明细 / 腾讯云账单资源摘要）在此结构上归一。
 * 全链路金额字段统一为 {@link BigDecimal}（scale=2，HALF_UP），禁止 Double/Float。</p>
 *
 * <p><b>约束</b>：除 Provider 实现类与 Mapper XML 外，任何地方不得出现厂商原始字段名
 * （如 {@code cash_pay_amount}），Service 层只认识本 BO。</p>
 *
 * @author linjicong
 * @version 1.0.0
 * @date 2026-01-01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnifiedBillRecord {

    // ---------- 维度 ----------

    /** 账号名，取 {@code conf_name} */
    private String account;

    /** 厂商标识：huawei / tencent / aliyun（全小写） */
    private String provider;

    /** 厂商中文名：华为云 / 腾讯云 / 阿里云 */
    private String providerLabel;

    /** 地域，取 {@code conf_region}，为空时回退 {@code region_name} */
    private String region;

    /** 账期，统一 {@code YYYY-MM} */
    private String billMonth;

    /** 产品大类（华为 service_type_name / 腾讯 business_code_name） */
    private String productMain;

    /** 产品子类（华为 resource_type_name 或 sub_resource_type_name / 腾讯 product_code_name） */
    private String productSub;

    /** 计费模式统一文本，见 {@link ChargingModeEnum} */
    private String chargingMode;

    /** 计费模式原始值（华为 Integer 的字符串形式 / 腾讯原文），便于排查 */
    private String chargingModeRaw;

    /** 资源 ID（华为费用记录表无此列，恒为 null，决策 D12） */
    private String resourceId;

    /** 资源名称 */
    private String resourceName;

    /** 交易/扣费时间，原样字符串透传，不做时区转换 */
    private String tradeTime;

    // ---------- 金额（全部 BigDecimal，scale=2） ----------

    /** 实付金额（本模块唯一主口径） */
    private BigDecimal payAmount;

    /** 原价 */
    private BigDecimal officialAmount;

    /** 优惠后应付 */
    private BigDecimal consumeAmount;

    /** 优惠额 = 原价 - 优惠后应付，负数按 0 兜底 */
    private BigDecimal discountAmount;

    /** 现金部分（构成拆解用，不参与再次求和） */
    private BigDecimal cashPay;

    /** 代金券部分 */
    private BigDecimal voucherPay;

    /** 信用/赠送账户部分 */
    private BigDecimal creditPay;

    /** 欠费（不计入实付，仅展示） */
    private BigDecimal debtAmount;

    /** 抹零（不计入实付，仅展示） */
    private BigDecimal eraseAmount;

    /** 核销（不计入实付，仅展示） */
    private BigDecimal writeoffAmount;

    // ---------- 元信息 ----------

    /** 金额来源表 */
    private AmountSourceEnum amountSource;

    /** 该行是否存在金额转换失败（前端打「数据异常」角标） */
    private Boolean amountAnomaly;
}
