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

import com.huaweicloud.sdk.elb.v3.model.*;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.Type;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.List;

/**
 * @author linjicong
 * @version 1.0.0
 * @date 2022-08-03-10:17
 * @see LoadBalancer
 */
@Data
@Table(name = "h_cloud_elb")
@Entity

public class HCloudElb extends BasicEntity {

    /**
     * 负载均衡器ID
     */
    private String id;

    /**
     * 负载均衡器描述
     */
    private String description;

    /**
     * 配置状态
     */
    private String provisioningStatus;

    /**
     * 管理状态
     */
    private Boolean adminStateUp;

    /**
     * 提供商
     */
    private String provider;

    /**
     * 后端服务器组列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private List<PoolRef> pools;

    /**
     * 监听器列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private List<ListenerRef> listeners;

    /**
     * 操作状态
     */
    private String operatingStatus;

    /**
     * 负载均衡器名称
     */
    private String name;

    /**
     * 项目ID
     */
    private String projectId;

    /**
     * VIP子网CIDR ID
     */
    private String vipSubnetCidrId;

    /**
     * VIP地址
     */
    private String vipAddress;

    /**
     * VIP端口ID
     */
    private String vipPortId;

    /**
     * 标签列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private List<Tag> tags;

    /**
     * 创建时间
     */
    private String createdAt;

    /**
     * 更新时间
     */
    private String updatedAt;

    /**
     * 是否保证型
     */
    private Boolean guaranteed;

    /**
     * 虚拟私有云ID
     */
    private String vpcId;

    /**
     * 弹性公网IP列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private List<EipInfo> eips;

    /**
     * IPv6 VIP地址
     */
    @Column(name= "ipv6_vip_address")
    private String ipv6VipAddress;

    /**
     * IPv6 VIP子网ID
     */
    @Column(name= "ipv6_vip_virsubnet_id")
    private String ipv6VipVirsubnetId;

    /**
     * IPv6 VIP端口ID
     */
    @Column(name= "ipv6_vip_port_id")
    private String ipv6VipPortId;

    /**
     * 可用区列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private List<String> availabilityZoneList;

    /**
     * 企业项目ID
     */
    private String enterpriseProjectId;

    /**
     * 计费信息
     */
    private String billingInfo;

    /**
     * 四层规格ID
     */
    @Column(name= "l4_flavor_id")
    private String l4FlavorId;

    /**
     * 四层弹性规格ID
     */
    @Column(name= "l4_scale_flavor_id")
    private String l4ScaleFlavorId;

    /**
     * 七层规格ID
     */
    @Column(name= "l7_flavor_id")
    private String l7FlavorId;

    /**
     * 七层弹性规格ID
     */
    @Column(name= "l7_scale_flavor_id")
    private String l7ScaleFlavorId;

    /**
     * 公网IP信息列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private List<PublicIpInfo> publicips;

    /**
     * 负载均衡器所在子网ID列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private List<String> elbVirsubnetIds;

    /**
     * 负载均衡器所在子网类型
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private LoadBalancer.ElbVirsubnetTypeEnum elbVirsubnetType;

    /**
     * 是否启用IP目标
     */
    private Boolean ipTargetEnable;

    /**
     * 冻结场景
     */
    private String frozenScene;

    /**
     * IPv6带宽信息
     */
    @Column(name="ipv6_bandwidth",columnDefinition="json")
    @Type(JsonStringType.class)
    private BandwidthRef ipv6Bandwidth;

    /**
     * 是否启用删除保护
     */
    private Boolean deletionProtectionEnable;

    /**
     * 弹性扩缩容配置
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private AutoscalingRef autoscaling;
}
