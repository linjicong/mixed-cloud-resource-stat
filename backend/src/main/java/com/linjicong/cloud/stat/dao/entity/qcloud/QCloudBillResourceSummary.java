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
package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.baomidou.mybatisplus.annotation.TableField;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import com.linjicong.cloud.stat.dao.typehandle.impl.qcloud.billing.BillTagInfoJsonTypeHandle;
import com.tencentcloudapi.billing.v20180709.models.BillResourceSummary;
import com.tencentcloudapi.billing.v20180709.models.BillTagInfo;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.Type;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author linjicong
 * @version 1.0.0
 * @date 2022-08-16-17:02
 * @see BillResourceSummary
 */
@Data
@Entity
@Table(name = "q_cloud_bill_resource_summary")

public class QCloudBillResourceSummary extends BasicEntity {

    private String month;

    /**
     * 产品名称：云产品大类，如云服务器CVM、云数据库MySQL
     */
    private String BusinessCodeName;

    /**
     * 子产品名称：云产品子类，如云服务器CVM-标准型S1， 当没有获取到子产品名称时，返回"-"
     */
    private String ProductCodeName;

    /**
     * 计费模式：包年包月和按量计费
     */
    private String PayModeName;

    /**
     * 项目
     */
    private String ProjectName;

    /**
     * 地域
     */
    private String RegionName;

    /**
     * 可用区
     */
    private String ZoneName;

    /**
     * 资源实例ID
     */
    private String ResourceId;

    /**
     * 资源实例名称
     */
    private String ResourceName;

    /**
     * 交易类型：包年包月新购/续费/升降配/退款、按量计费扣费、调账补偿/扣费等类型
     */
    private String ActionTypeName;

    /**
     * 订单ID
     */
    private String OrderId;

    /**
     * 扣费时间
     */
    private String PayTime;

    /**
     * 开始使用时间
     */
    private String FeeBeginTime;

    /**
     * 结束使用时间
     */
    private String FeeEndTime;

    /**
     * 配置描述
     */
    private String ConfigDesc;

    /**
     * 扩展字段1
     */
    private String ExtendField1;

    /**
     * 扩展字段2
     */
    private String ExtendField2;

    /**
     * 原价，单位为元
     */
    private String TotalCost;

    /**
     * 折扣率
     当聚合之后折扣不唯一或者合同价的情况下，返回“-”
     */
    private String Discount;

    /**
     * 优惠类型
     */
    private String ReduceType;

    /**
     * 优惠后总价，单位为元
     */
    private String RealTotalCost;

    /**
     * 代金券支付金额，单位为元
     */
    private String VoucherPayAmount;

    /**
     * 现金账户支付金额，单位为元
     */
    private String CashPayAmount;

    /**
     * 赠送账户支付金额，单位为元
     */
    private String IncentivePayAmount;

    /**
     * 扩展字段3
     */
    private String ExtendField3;

    /**
     * 扩展字段4
     */
    private String ExtendField4;

    /**
     * 扩展字段5
     */
    private String ExtendField5;

    /**
     * Tag 信息
     注意：此字段可能返回 null，表示取不到有效值。
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    @TableField(typeHandler = BillTagInfoJsonTypeHandle.class)
    private BillTagInfo[] Tags;

    /**
     * 付款方uin
     */
    private String PayerUin;

    /**
     * 资源所有者uin,无值则返回"-"
     */
    private String OwnerUin;

    /**
     * 操作者uin,无值则返回"-"
     */
    private String OperateUin;

    /**
     * 产品名称代码
     */
    private String BusinessCode;

    /**
     * 子产品名称代码
     */
    private String ProductCode;

    /**
     * 区域ID
     */
    private Long RegionId;

    /**
     * 资源包、预留实例、节省计划、竞价实例这四类特殊实例本身的扣费行为，此字段体现对应的实例类型。枚举值如下：

     ri=Standard RI

     svp=Savings Plan

     si=Spot Instances

     rp=Resource Pack
     */
    private String InstanceType;

    /**
     * 按组件原价的口径换算的预留实例抵扣金额
     */
    @Column(name = "original_cost_with_ri")
    @TableField(value = "original_cost_with_ri")
    private String OriginalCostWithRI;

    /**
     * 节省计划抵扣的SP包面值
     */
    @Column(name = "sp_deduction")
    @TableField(value = "sp_deduction")
    private String SPDeduction;

    /**
     * 按组件原价的口径换算的节省计划抵扣金额
     */
    @Column(name = "original_cost_with_sp")
    @TableField(value = "original_cost_with_sp")
    private String OriginalCostWithSP;
}
