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

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.huaweicloud.sdk.bss.v2.model.CostUnitPair;
import com.huaweicloud.sdk.bss.v2.model.NvlCostAnalysedBillDetail;
import com.huaweicloud.sdk.bss.v2.model.TagPair;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.Type;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.List;

/**
 * 华为云-月度账单明细
 * @author linjicong
 * @version 1.0.0
 * @date 2022-08-11-9:33
 * @see NvlCostAnalysedBillDetail
 */
@Data
@Table(name = "h_cloud_bills_monthly_break_down")
@Entity

public class HCloudBillsMonthlyBreakDown extends BasicEntity {

    /**
     * 分摊月份
     */
    private String sharedMonth;

    /**
     * 账单周期
     */
    private String billCycle;

    /**
     * 账单类型
     */
    private Integer billType;

    /**
     * 客户ID
     */
    private String customerId;

    /**
     * 区域编码
     */
    private String regionCode;

    /**
     * 区域名称
     */
    private String regionName;

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
     * 生效时间
     */
    private String effectiveTime;

    /**
     * 失效时间
     */
    private String expireTime;

    /**
     * 资源ID
     */
    private String resourceId;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 资源标签
     */
    private String resourceTag;

    /**
     * 产品规格描述
     */
    private String productSpecDesc;

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
     * 订单ID
     */
    private String orderId;

    /**
     * 周期类型
     */
    private Integer periodType;

    /**
     * 使用类型
     */
    private String usageType;

    /**
     * 使用量
     */
    @Column(name = "`usage`")
    @TableField(value = "`usage`")
    private Double usage;

    /**
     * 使用量单位ID
     */
    private Integer usageMeasureId;

    /**
     * 免费资源使用量
     */
    private Double freeResourceUsage;

    /**
     * 免费资源使用量单位ID
     */
    private Integer freeResourceMeasureId;

    /**
     * 预留实例使用量
     */
    private Double riUsage;

    /**
     * 预留实例使用量单位ID
     */
    private Integer riUsageMeasureId;

    /**
     * 消费金额
     */
    private Double consumeAmount;

    /**
     * 过去月份摊销金额
     */
    private Double pastMonthsAmortizedAmount;

    /**
     * 当前月份摊销金额
     */
    private Double currentMonthAmortizedAmount;

    /**
     * 未来月份摊销金额
     */
    private Double futureMonthsAmortizedAmount;

    /**
     * 摊销现金金额
     */
    private Double amortizedCashAmount;

    /**
     * 摊销信用金额
     */
    private Double amortizedCreditAmount;

    /**
     * 摊销优惠券金额
     */
    private Double amortizedCouponAmount;

    /**
     * 摊销灵活购买券金额
     */
    private Double amortizedFlexipurchaseCouponAmount;

    /**
     * 摊销储值卡金额
     */
    private Double amortizedStoredValueCardAmount;

    /**
     * 摊销奖励金额
     */
    private Double amortizedBonusAmount;

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

    /**
     * 有效标签对列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<TagPair> effectiveTagPairs;

    /**
     * 成本单元对列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<CostUnitPair> costUnitPairs;
}