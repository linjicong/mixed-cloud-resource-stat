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
 * 明细分页响应 VO（{@code GET /bill/details}）。
 *
 * <p>{@code summary} 是<b>当前筛选条件下全量</b>的 SUM（不是当前页），供明细分页区顶部展示。</p>
 *
 * @author linjicong
 * @version 1.0.0
 * @date 2026-01-01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillDetailPageVO {

    /** 当前页码，从 1 开始 */
    private Integer pageNum;

    /** 每页条数（服务端已钳制 ≤200） */
    private Integer pageSize;

    /** 当前筛选条件下的总条数 */
    private Long total;

    /** 总页数 */
    private Integer pages;

    /** 当前页数据 */
    @Builder.Default
    private List<Record> records = new ArrayList<>();

    /** 当前筛选条件下的全量合计 */
    private Summary summary;

    /** 数据质量 */
    private DataQuality dataQuality;

    /**
     * 明细行。
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Record {
        /** 唯一 id，格式 {FEE|BD|QCL}:{pk}，供前端 row-key */
        private String id;
        /** 账期 YYYY-MM */
        private String billMonth;
        /** 账号名 */
        private String account;
        /** 厂商标识 */
        private String provider;
        /** 厂商中文名 */
        private String providerLabel;
        /** 产品大类 */
        private String productMain;
        /** 产品子类 */
        private String productSub;
        /** 计费模式统一文本 */
        private String chargingMode;
        /** 原价 */
        private BigDecimal officialAmount;
        /** 优惠后应付 */
        private BigDecimal consumeAmount;
        /** 优惠额 */
        private BigDecimal discountAmount;
        /** 实付 */
        private BigDecimal payAmount;
        /** 现金 */
        private BigDecimal cashPay;
        /** 代金券 */
        private BigDecimal voucherPay;
        /** 信用/赠送账户 */
        private BigDecimal creditPay;
        /** 欠费（不计入实付） */
        private BigDecimal debtAmount;
        /** 抹零（不计入实付） */
        private BigDecimal eraseAmount;
        /** 核销（不计入实付） */
        private BigDecimal writeoffAmount;
        /** 地域 */
        private String region;
        /** 资源 ID（华为费用记录恒为 null，前端显示 —） */
        private String resourceId;
        /** 资源名称 */
        private String resourceName;
        /** 交易时间 */
        private String tradeTime;
        /** 金额来源枚举名 */
        private String amountSource;
        /** 金额来源中文标签（后端直接下发） */
        private String amountSourceLabel;
        /** 是否金额异常 */
        private Boolean amountAnomaly;
    }

    /**
     * 全量合计。
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Summary {
        /** 实付合计 */
        private BigDecimal payAmountSum;
        /** 原价合计 */
        private BigDecimal officialAmountSum;
        /** 优惠额合计 */
        private BigDecimal discountAmountSum;
    }

    /**
     * 数据质量。
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DataQuality {
        /** 金额解析异常行数 */
        private Integer amountAnomalyCount;
    }
}
