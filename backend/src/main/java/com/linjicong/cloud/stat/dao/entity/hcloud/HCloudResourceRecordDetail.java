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

import com.huaweicloud.sdk.bss.v2.model.MonthlyBillRes;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * 华为云-资源记录详情
 * @author linjicong
 * @version 1.0.0
 * @date 2022-08-11-9:33
 * @see MonthlyBillRes
 */
@Data
@Table(name = "h_cloud_resource_record_detail")
@Entity

public class HCloudResourceRecordDetail extends BasicEntity {
    /**
     * 周期
     */
    private String cycle;
    
    /**
     * 账单日期
     */
    private String billDate;
    
    /**
     * 账单类型
     */
    private Integer billType;
    
    /**
     * 客户ID
     */
    private String customerId;
    
    /**
     * 区域
     */
    private String region;
    
    /**
     * 区域名称
     */
    private String regionName;
    
    /**
     * 云服务类型
     */
    private String cloudServiceType;
    
    /**
     * 资源类型编码
     */
    private String resourceTypeCode;
    
    /**
     * 云服务类型名称
     */
    private String cloudServiceTypeName;
    
    /**
     * 资源类型名称
     */
    private String resourceTypeName;
    
    /**
     * 资源实例ID
     */
    private String resInstanceId;
    
    /**
     * 资源名称
     */
    private String resourceName;
    
    /**
     * 资源标签
     */
    private String resourceTag;
    
    /**
     * SKU编码
     */
    private String skuCode;
    
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
    private Integer chargeMode;
    
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
    private Double storedCardAmount;
    
    /**
     * 奖励金额
     */
    private Double bonusAmount;
    
    /**
     * 欠费金额
     */
    private Double debtAmount;
    
    /**
     * 调整金额
     */
    private Double adjustmentAmount;
    
    /**
     * 官方金额
     */
    private Double officialAmount;
    
    /**
     * 折扣金额
     */
    private Double discountAmount;
    
    /**
     * 度量单位ID
     */
    private Integer measureId;
    
    /**
     * 周期类型
     */
    private Integer periodType;
    
    /**
     * 根资源ID
     */
    private String rootResourceId;
    
    /**
     * 父资源ID
     */
    private String parentResourceId;
    
    /**
     * 交易ID
     */
    private String tradeId;
    
    /**
     * 产品规格描述
     */
    private String productSpecDesc;
    
    /**
     * 子服务类型编码
     */
    private String subServiceTypeCode;
    
    /**
     * 子服务类型名称
     */
    private String subServiceTypeName;
    
    /**
     * 子资源类型编码
     */
    private String subResourceTypeCode;
    
    /**
     * 子资源类型名称
     */
    private String subResourceTypeName;
    
    /**
     * 子资源ID
     */
    private String subResourceId;
    
    /**
     * 子资源名称
     */
    private String subResourceName;
}