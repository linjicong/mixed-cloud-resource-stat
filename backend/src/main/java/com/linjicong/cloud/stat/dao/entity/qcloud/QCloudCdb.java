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
import com.tencentcloudapi.cdb.v20170320.models.*;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.Type;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * 腾讯云-云数据库MySQL
 *
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 * @see InstanceInfo
 */
@Data
@Entity
@Table(name = "q_cloud_cdb")

public class QCloudCdb extends BasicEntity {

    /**
     * 外网状态，可能的返回值为：0-未开通外网；1-已开通外网；2-已关闭外网
     */
    private Long WanStatus;

    /**
     * 可用区信息
     */
    private String Zone;

    /**
     * 初始化标志，可能的返回值为：0-未初始化；1-已初始化
     */
    private Long InitFlag;

    /**
     * 只读vip信息。单独开通只读实例访问的只读实例才有该字段
     注意：此字段可能返回 null，表示取不到有效值。
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private RoVipInfo RoVipInfo;

    /**
     * 内存容量，单位为 MB
     */
    private Long Memory;

    /**
     * 实例状态，可能的返回值：0-创建中；1-运行中；4-隔离中；5-已隔离
     */
    private Long Status;

    /**
     * 私有网络 ID，例如：51102
     */
    private Long VpcId;

    /**
     * 备机信息
     注意：此字段可能返回 null，表示取不到有效值。
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private SlaveInfo SlaveInfo;

    /**
     * 实例 ID
     */
    private String InstanceId;

    /**
     * 硬盘容量，单位为 GB
     */
    private Long Volume;

    /**
     * 自动续费标志，可能的返回值：0-未开通自动续费；1-已开通自动续费；2-已关闭自动续费
     */
    private Long AutoRenew;

    /**
     * 数据复制方式。0 - 异步复制；1 - 半同步复制；2 - 强同步复制
     */
    private Long ProtectMode;

    /**
     * 只读组详细信息
     注意：此字段可能返回 null，表示取不到有效值。
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private RoGroup[] RoGroups;

    /**
     * 子网 ID，例如：2333
     */
    private Long SubnetId;

    /**
     * 实例类型，可能的返回值：1-主实例；2-灾备实例；3-只读实例
     */
    private Long InstanceType;

    /**
     * 项目 ID
     */
    private Long ProjectId;

    /**
     * 地域信息
     */
    private String Region;

    /**
     * 实例到期时间
     */
    private String DeadlineTime;

    /**
     * 可用区部署方式。可能的值为：0 - 单可用区；1 - 多可用区
     */
    private Long DeployMode;

    /**
     * 实例任务状态。0 - 没有任务 ,1 - 升级中,2 - 数据导入中,3 - 开放Slave中,4 - 外网访问开通中,5 - 批量操作执行中,6 - 回档中,7 - 外网访问关闭中,8 - 密码修改中,9 - 实例名修改中,10 - 重启中,12 - 自建迁移中,13 - 删除库表中,14 - 灾备实例创建同步中,15 - 升级待切换,16 - 升级切换中,17 - 升级切换完成
     */
    private Long TaskStatus;

    /**
     * 主实例详细信息
     注意：此字段可能返回 null，表示取不到有效值。
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private MasterInfo MasterInfo;

    /**
     * 实例类型
     */
    private String DeviceType;

    /**
     * 内核版本
     */
    private String EngineVersion;

    /**
     * 实例名称
     */
    private String InstanceName;

    /**
     * 灾备实例详细信息
     注意：此字段可能返回 null，表示取不到有效值。
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private DrInfo[] DrInfo;

    /**
     * 外网域名
     */
    private String WanDomain;

    /**
     * 外网端口号
     */
    private Long WanPort;

    /**
     * 付费类型，可能的返回值：0-包年包月；1-按量计费
     */
    private Long PayType;

    /**
     * 实例创建时间
     */
    private String CreateTime;

    /**
     * 实例 IP
     */
    private String Vip;

    /**
     * 端口号
     */
    private Long Vport;

    /**
     * 磁盘写入是否被锁定（实例数据写入量已经超过磁盘配额）。0 -未被锁定 1 -已被锁定
     */
    private Long CdbError;

    /**
     * 私有网络描述符，例如："vpc-5v8wn9mg"
     */
    private String UniqVpcId;

    /**
     * 子网描述符，例如："subnet-1typ0s7d"
     */
    private String UniqSubnetId;

    /**
     * 物理 ID
     */
    private String PhysicalId;

    /**
     * 核心数
     */
    private Long Cpu;

    /**
     * 每秒查询数量
     */
    private Long Qps;

    /**
     * 可用区中文名称
     */
    private String ZoneName;

    /**
     * 物理机型
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private String DeviceClass;

    /**
     * 置放群组 ID
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private String DeployGroupId;

    /**
     * 可用区 ID
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private Long ZoneId;

    /**
     * 节点数
     */
    private Long InstanceNodes;

    /**
     * 标签列表
     注意：此字段可能返回 null，表示取不到有效值。
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private TagInfoItem[] TagList;
}