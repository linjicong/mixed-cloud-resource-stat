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
import com.tencentcloudapi.cvm.v20170312.models.*;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.Type;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * 腾讯-弹性云服务器
 *
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 * @see Instance
 */
@Data
@Entity
@Table(name = "q_cloud_cvm")

public class QCloudCvm extends BasicEntity {
    /**
     * 实例所在的位置信息
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private Placement Placement;

    /**
     * 实例ID
     */
    private String InstanceId;

    /**
     * 实例类型
     */
    private String InstanceType;

    /**
     * CPU核数
     */
    @Column(name = "cpu")
    private Long CPU;

    /**
     * 内存大小（GB）
     */
    private Long Memory;

    /**
     * 实例限制状态
     */
    private String RestrictState;

    /**
     * 实例名称
     */
    private String InstanceName;

    /**
     * 实例计费类型
     */
    private String InstanceChargeType;

    /**
     * 系统盘信息
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private SystemDisk SystemDisk;

    /**
     * 数据盘信息列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private DataDisk[] DataDisks;

    /**
     * 内网IP地址列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private String[] PrivateIpAddresses;

    /**
     * 公网IP地址列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private String[] PublicIpAddresses;

    /**
     * 公网带宽相关信息
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private InternetAccessible InternetAccessible;

    /**
     * 私有网络相关信息
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private VirtualPrivateCloud VirtualPrivateCloud;

    /**
     * 镜像ID
     */
    private String ImageId;

    /**
     * 自动续费标识
     */
    private String RenewFlag;

    /**
     * 创建时间
     */
    private String CreatedTime;

    /**
     * 到期时间
     */
    private String ExpiredTime;

    /**
     * 操作系统名称
     */
    private String OsName;

    /**
     * 安全组ID列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private String[] SecurityGroupIds;

    /**
     * 实例登录设置
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private LoginSettings LoginSettings;

    /**
     * 实例状态
     */
    private String InstanceState;

    /**
     * 标签列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private Tag[] Tags;

    /**
     * 实例的停止计费模式
     */
    private String StopChargingMode;

    /**
     * 实例UUID
     */
    private String Uuid;

    /**
     * 实例最新操作
     */
    private String LatestOperation;

    /**
     * 实例最新操作状态
     */
    private String LatestOperationState;

    /**
     * 实例最新操作请求ID
     */
    private String LatestOperationRequestId;

    /**
     * 分散置放群组ID
     */
    private String DisasterRecoverGroupId;

    /**
     * IPv6地址列表
     */
    @Column(name="ipv6_addresses", columnDefinition="json")
    @Type(JsonStringType.class)
    private String[] IPv6Addresses;

    /**
     * CAM角色名称
     */
    private String CamRoleName;

    /**
     * 高性能计算集群ID
     */
    private String HpcClusterId;

    /**
     * RDMA网络IP地址列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private String[] RdmaIpAddresses;

    /**
     * 隔离来源
     */
    private String IsolatedSource;

    /**
     * GPU信息
     */
    @Column(name="gpu_info",columnDefinition="json")
    @Type(JsonStringType.class)
    private GPUInfo GPUInfo;

    /**
     * 许可证类型
     */
    private String LicenseType;
}
