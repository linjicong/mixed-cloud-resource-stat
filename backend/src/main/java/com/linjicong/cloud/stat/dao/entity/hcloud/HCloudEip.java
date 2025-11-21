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

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * 华为云弹性公网IP实体类
 * 用于存储华为云弹性公网IP（EIP）资源信息
 * 
 * @author linjicong
 * @version 1.0.0
 * @date 2022-08-03-10:17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "h_cloud_eip")
@Entity
public class HCloudEip extends BasicEntity {

    /**
     * 弹性公网IP的ID
     */
    private String id;

    /**
     * 功能说明：弹性公网IP的状态
     * 取值范围：FREEZED，DOWN，ACTIVE，ERROR
     */
    private String status;

    /**
     * 功能说明：弹性公网IP的类型
     * 取值范围：5_bgp，5_sbgp
     */
    private String type;

    /**
     * 功能说明：弹性公网IP的版本
     * 取值范围：4，6
     */
    private Integer ipVersion;

    /**
     * 功能说明：弹性公网IP的地址
     */
    @Column(name = "public_ip_address")
    private String publicIpAddress;

    /**
     * 功能说明：弹性公网IP的IPv6地址
     */
    @Column(name = "public_ipv6_address")
    private String publicIpv6Address;

    /**
     * 功能说明：弹性公网IP的租户ID
     */
    @Column(name = "tenant_id")
    private String tenantId;

    /**
     * 功能说明：弹性公网IP的创建时间
     */
    @Column(name = "create_time")
    private OffsetDateTime createTime;

    /**
     * 功能说明：弹性公网IP的带宽ID
     */
    @Column(name = "bandwidth_id")
    private String bandwidthId;

    /**
     * 功能说明：弹性公网IP的带宽名称
     */
    @Column(name = "bandwidth_name")
    private String bandwidthName;

    /**
     * 功能说明：弹性公网IP的带宽大小
     */
    @Column(name = "bandwidth_size")
    private Integer bandwidthSize;

    /**
     * 功能说明：弹性公网IP的带宽共享类型
     * 取值范围：PER，WHOLE
     */
    @Column(name = "bandwidth_share_type")
    private String bandwidthShareType;

    /**
     * 功能说明：弹性公网IP的企业项目ID
     */
    @Column(name = "enterprise_project_id")
    private String enterpriseProjectId;

    /**
     * 功能说明：弹性公网IP绑定的实例类型
     * 取值范围：PORT，NATGW，ELB，VPN，ELBV1
     */
    @Column(name = "associate_instance_type")
    private String associateInstanceType;

    /**
     * 功能说明：弹性公网IP绑定的实例ID
     */
    @Column(name = "associate_instance_id")
    private String associateInstanceId;

    /**
     * 功能说明：弹性公网IP绑定的实例名称
     */
    @Column(name = "associate_instance_name")
    private String associateInstanceName;

    /**
     * 功能说明：弹性公网IP绑定的实例类型（详细）
     */
    @Column(name = "associate_instance_type_detail")
    private String associateInstanceTypeDetail;

    /**
     * 功能说明：弹性公网IP的端口ID
     */
    @Column(name = "port_id")
    private String portId;

    /**
     * 功能说明：弹性公网IP的端口信息
     */
    @Column(name = "port_info", columnDefinition = "json")
    @Type(JsonStringType.class)
    private Object portInfo;

    /**
     * 功能说明：弹性公网IP的标签列表
     */
    @Column(columnDefinition = "json")
    @Type(JsonStringType.class)
    private List<Object> tags;

    /**
     * 功能说明：弹性公网IP的别名
     */
    private String alias;

    /**
     * 功能说明：弹性公网IP的描述
     */
    private String description;

    /**
     * 功能说明：弹性公网IP的更新时间
     */
    @Column(name = "update_time")
    private OffsetDateTime updateTime;

    /**
     * 功能说明：弹性公网IP的订单ID
     */
    @Column(name = "order_id")
    private String orderId;

    /**
     * 功能说明：弹性公网IP的计费模式
     * 取值范围：prePaid，postPaid
     */
    @Column(name = "charge_mode")
    private String chargeMode;

    /**
     * 功能说明：弹性公网IP的带宽类型
     */
    @Column(name = "bandwidth_type")
    private String bandwidthType;

    /**
     * 功能说明：弹性公网IP的带宽计费类型
     */
    @Column(name = "bandwidth_charge_mode")
    private String bandwidthChargeMode;

    /**
     * 功能说明：弹性公网IP的带宽名称（详细）
     */
    @Column(name = "bandwidth_name_detail")
    private String bandwidthNameDetail;

    /**
     * 功能说明：弹性公网IP的带宽大小（详细）
     */
    @Column(name = "bandwidth_size_detail")
    private Integer bandwidthSizeDetail;

    /**
     * 功能说明：弹性公网IP的带宽共享类型（详细）
     */
    @Column(name = "bandwidth_share_type_detail")
    private String bandwidthShareTypeDetail;

    /**
     * 功能说明：弹性公网IP的带宽类型（详细）
     */
    @Column(name = "bandwidth_type_detail")
    private String bandwidthTypeDetail;

    /**
     * 功能说明：弹性公网IP的带宽计费类型（详细）
     */
    @Column(name = "bandwidth_charge_mode_detail")
    private String bandwidthChargeModeDetail;

    /**
     * 功能说明：弹性公网IP的冻结场景
     */
    @Column(name = "frozen_scene")
    private String frozenScene;
}

