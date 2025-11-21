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
package com.linjicong.cloud.stat.dao.entity.hcloud;

import com.huaweicloud.sdk.bss.v2.model.MonthlyBillRecord;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 华为云-账单费用记录
 * @author linjicong
 * @version 1.0.0
 * @date 2022-08-09-17:28
 * @see MonthlyBillRecord
 */
@Data
@Table(name = "h_cloud_bills_fee_records")
@Entity

public class HCloudBillsFeeRecords extends BasicEntity {

    /**
     * 账单周期
     */
    private String billCycle;

    /**
     * 客户ID
     */
    private String customerId;

    /**
     * 服务类型编码
     */
    private String serviceTypeCode;

    /**
     * 资源类型编码
     */
    private String resourceTypeCode;

    /**
     * 服务类型名称
     */
    private String serviceTypeName;

    /**
     * 资源类型名称
     */
    private String resourceTypeName;

    /**
     * 区域编码
     */
    private String regionCode;

    /**
     * 企业项目ID
     */
    private String enterpriseProjectId;

    /**
     * 企业项目名称
     */
    private String enterpriseProjectName;

    /**
     * 计费模式
     */
    private Integer chargingMode;

    /**
     * 消费时间
     */
    private String consumeTime;

    /**
     * 交易时间
     */
    private String tradeTime;

    /**
     * 提供商类型
     */
    private Integer providerType;

    /**
     * 交易ID
     */
    private String tradeId;

    /**
     * 账单类型
     */
    private Integer billType;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 官方金额
     */
    private Double officialAmount;

    /**
     * 官方折扣金额
     */
    private Double officialDiscountAmount;

    /**
     * 抹零金额
     */
    private Double eraseAmount;

    /**
     * 消费金额
     */
    private Double consumeAmount;

    /**
     * 现金金额
     */
    private Double cashAmount;

    /**
     * 信用金额
     */
    private Double creditAmount;

    /**
     * 优惠券金额
     */
    private Double couponAmount;

    /**
     * 灵活购买券金额
     */
    private Double flexipurchaseCouponAmount;

    /**
     * 储值卡金额
     */
    private Double storedValueCardAmount;

    /**
     * 奖励金额
     */
    private Double bonusAmount;

    /**
     * 欠费金额
     */
    private Double debtAmount;

    /**
     * 核销金额
     */
    private Double writeoffAmount;

    /**
     * 区域名称
     */
    private String regionName;

}