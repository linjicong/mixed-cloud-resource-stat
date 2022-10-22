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

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import com.tencentcloudapi.clb.v20180317.models.*;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 腾讯-云数据库
 *
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 * @see
 */
@Data
@Entity
@Table(name = "q_cloud_clb")
@TypeDef(name = "json",typeClass = JsonStringType.class)
public class QCloudClb extends BasicEntity {

    /**
     * 负载均衡实例 ID。
     */
    private String LoadBalancerId;

    /**
     * 负载均衡实例的名称。
     */
    private String LoadBalancerName;

    /**
     * 负载均衡实例的网络类型：
     OPEN：公网属性， INTERNAL：内网属性。
     */
    private String LoadBalancerType;

    /**
     * 负载均衡类型标识，1：负载均衡，0：传统型负载均衡。
     */
    private Long Forward;

    /**
     * 负载均衡实例的域名，仅公网传统型负载均衡实例才提供该字段
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private String Domain;

    /**
     * 负载均衡实例的 VIP 列表。
     注意：此字段可能返回 null，表示取不到有效值。
     */
    @Column(columnDefinition="json")
    @Type(type = "json")
    private String [] LoadBalancerVips;

    /**
     * 负载均衡实例的状态，包括
     0：创建中，1：正常运行。
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private Long Status;

    /**
     * 负载均衡实例的创建时间。
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private String CreateTime;

    /**
     * 负载均衡实例的上次状态转换时间。
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private String StatusTime;

    /**
     * 负载均衡实例所属的项目 ID， 0 表示默认项目。
     */
    private Long ProjectId;

    /**
     * 私有网络的 ID
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private String VpcId;

    /**
     * 高防 LB 的标识，1：高防负载均衡 0：非高防负载均衡。
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private Long OpenBgp;

    /**
     * 在 2016 年 12 月份之前的传统型内网负载均衡都是开启了 snat 的。
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private Boolean Snat;

    /**
     * 0：表示未被隔离，1：表示被隔离。
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private Long Isolation;

    /**
     * 用户开启日志的信息，日志只有公网属性创建了 HTTP 、HTTPS 监听器的负载均衡才会有日志。
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private String Log;

    /**
     * 负载均衡实例所在的子网（仅对内网VPC型LB有意义）
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private String SubnetId;

    /**
     * 负载均衡实例的标签信息
     注意：此字段可能返回 null，表示取不到有效值。
     */
    @Column(columnDefinition="json")
    @Type(type = "json")
    private TagInfo [] Tags;

    /**
     * 负载均衡实例的安全组
     注意：此字段可能返回 null，表示取不到有效值。
     */
    @Column(columnDefinition="json")
    @Type(type = "json")
    private String [] SecureGroups;

    /**
     * 负载均衡实例绑定的后端设备的基本信息
     注意：此字段可能返回 null，表示取不到有效值。
     */
    @Column(columnDefinition="json")
    @Type(type = "json")
    private TargetRegionInfo TargetRegionInfo;

    /**
     * anycast负载均衡的发布域，对于非anycast的负载均衡，此字段返回为空字符串
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private String AnycastZone;

    /**
     * IP版本，ipv4 | ipv6
     注意：此字段可能返回 null，表示取不到有效值。
     */
    @Column(name = "addresses_ip_version")
    private String AddressIPVersion;

    /**
     * 数值形式的私有网络 ID
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private Long NumericalVpcId;

    /**
     * 负载均衡IP地址所属的ISP
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private String VipIsp;

    /**
     * 主可用区
     注意：此字段可能返回 null，表示取不到有效值。
     */
    @Column(columnDefinition="json")
    @Type(type = "json")
    private ZoneInfo MasterZone;

    /**
     * 备可用区
     注意：此字段可能返回 null，表示取不到有效值。
     */
    @Column(columnDefinition="json")
    @Type(type = "json")
    private ZoneInfo [] BackupZoneSet;

    /**
     * 负载均衡实例被隔离的时间
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private String IsolatedTime;

    /**
     * 负载均衡实例的过期时间，仅对预付费负载均衡生效
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private String ExpireTime;

    /**
     * 负载均衡实例的计费类型，PREPAID：包年包月，POSTPAID_BY_HOUR：按量计费
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private String ChargeType;

    /**
     * 负载均衡实例的网络属性
     注意：此字段可能返回 null，表示取不到有效值。
     */
    @Column(columnDefinition="json")
    @Type(type = "json")
    private InternetAccessible NetworkAttributes;

    /**
     * 负载均衡实例的预付费相关属性
     注意：此字段可能返回 null，表示取不到有效值。
     */
    @Column(columnDefinition="json")
    @Type(type = "json")
    private LBChargePrepaid PrepaidAttributes;

    /**
     * 负载均衡日志服务(CLS)的日志集ID
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private String LogSetId;

    /**
     * 负载均衡日志服务(CLS)的日志主题ID
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private String LogTopicId;

    /**
     * 负载均衡实例的IPv6地址
     注意：此字段可能返回 null，表示取不到有效值。
     */
    @Column(name = "address_ipv6")
    private String AddressIPv6;

    /**
     * 暂做保留，一般用户无需关注。
     注意：此字段可能返回 null，表示取不到有效值。
     */
    @Column(columnDefinition="json")
    @Type(type = "json")
    private ExtraInfo ExtraInfo;

    /**
     * 是否可绑定高防包
     注意：此字段可能返回 null，表示取不到有效值。
     */
    @Column(name = "is_ddos")
    private Boolean IsDDos;

    /**
     * 负载均衡维度的个性化配置ID
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private String ConfigId;

    /**
     * 后端服务是否放通来自LB的流量
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private Boolean LoadBalancerPassToTarget;

    /**
     * 内网独占集群
     注意：此字段可能返回 null，表示取不到有效值。
     */
    @Column(columnDefinition="json")
    @Type(type = "json")
    private ExclusiveCluster ExclusiveCluster;

    /**
     * IP地址版本为ipv6时此字段有意义， IPv6Nat64 | IPv6FullChain
     注意：此字段可能返回 null，表示取不到有效值。
     */
    @Column(name = "ipv6_mode")
    private String IPv6Mode;

    /**
     * 是否开启SnatPro。
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private Boolean SnatPro;

    /**
     * 开启SnatPro负载均衡后，SnatIp列表。
     注意：此字段可能返回 null，表示取不到有效值。
     */
    @Column(columnDefinition="json")
    @Type(type = "json")
    private SnatIp[] SnatIps;

    /**
     * 性能保障规格
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private String SlaType;

    /**
     * vip是否被封堵
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private Boolean IsBlock;

    /**
     * 封堵或解封时间
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private String IsBlockTime;

    /**
     * IP类型是否是本地BGP
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private Boolean LocalBgp;

    /**
     * 7层独占标签。
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private String ClusterTag;

    /**
     * 开启IPv6FullChain负载均衡7层监听器支持混绑IPv4/IPv6目标功能。
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private Boolean MixIpTarget;

    /**
     * 私有网络内网负载均衡，就近接入模式下规则所落在的可用区
     注意：此字段可能返回 null，表示取不到有效值。
     */
    @Column(columnDefinition="json")
    @Type(type = "json")
    private String [] Zones;

    /**
     * CLB是否为NFV，空：不是，l7nfv：七层是NFV。
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private String NfvInfo;

    /**
     * 负载均衡日志服务(CLS)的健康检查日志集ID
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private String HealthLogSetId;

    /**
     * 负载均衡日志服务(CLS)的健康检查日志主题ID
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private String HealthLogTopicId;

    /**
     * 集群ID.
     注意：此字段可能返回 null，表示取不到有效值。
     */
    @Column(columnDefinition="json")
    @Type(type = "json")
    private String [] ClusterIds;

    /**
     * 负载均衡的属性
     注意：此字段可能返回 null，表示取不到有效值。
     */
    @Column(columnDefinition="json")
    @Type(type = "json")
    private String [] AttributeFlags;
}
