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
import com.tencentcloudapi.cbs.v20170312.models.Placement;
import com.tencentcloudapi.cbs.v20170312.models.Tag;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.Type;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * 腾讯云-云硬盘
 *
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 * @see
 */
@Data
@Entity
@Table(name = "q_cloud_cbs")

public class QCloudCbs extends BasicEntity {

    /**
     * 云盘是否与挂载的实例一起销毁。<br><li>true:销毁实例时会同时销毁云盘，只支持按小时后付费云盘。<br><li>false：销毁实例时不销毁云盘。
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private Boolean DeleteWithInstance;

    /**
     * 自动续费标识。取值范围：<br><li>NOTIFY_AND_AUTO_RENEW：通知过期且自动续费<br><li>NOTIFY_AND_MANUAL_RENEW：通知过期不自动续费<br><li>DISABLE_NOTIFY_AND_MANUAL_RENEW：不通知过期不自动续费。
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private String RenewFlag;

    /**
     * 硬盘介质类型。取值范围：<br><li>CLOUD_BASIC：表示普通云硬盘<br><li>CLOUD_PREMIUM：表示高性能云硬盘<br><li>CLOUD_BSSD：表示通用型SSD云硬盘<br><li>CLOUD_SSD：表示SSD云硬盘<br><li>CLOUD_HSSD：表示增强型SSD云硬盘<br><li>CLOUD_TSSD：表示极速型SSD云硬盘。
     */
    private String DiskType;

    /**
     * 云盘状态。取值范围：<br><li>UNATTACHED：未挂载<br><li>ATTACHING：挂载中<br><li>ATTACHED：已挂载<br><li>DETACHING：解挂中<br><li>EXPANDING：扩容中<br><li>ROLLBACKING：回滚中<br><li>TORECYCLE：待回收<br><li>DUMPING：拷贝硬盘中。
     */
    private String DiskState;

    /**
     * 云盘拥有的快照总数。
     */
    private Long SnapshotCount;

    /**
     * 云盘已挂载到子机，且子机与云盘都是包年包月。<br><li>true：子机设置了自动续费标识，但云盘未设置<br><li>false：云盘自动续费标识正常。
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private Boolean AutoRenewFlagError;

    /**
     * 云盘是否处于快照回滚状态。取值范围：<br><li>false:表示不处于快照回滚状态<br><li>true:表示处于快照回滚状态。
     */
    private Boolean Rollbacking;

    /**
     * 对于非共享型云盘，该参数为空数组。对于共享型云盘，则表示该云盘当前被挂载到的CVM实例InstanceId
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private String [] InstanceIdList;

    /**
     * 云盘是否为加密盘。取值范围：<br><li>false:表示非加密盘<br><li>true:表示加密盘。
     */
    private Boolean Encrypt;

    /**
     * 云硬盘名称。
     */
    private String DiskName;

    /**
     * 云硬盘因欠费销毁或者到期销毁时， 是否使用快照备份数据的标识。true表示销毁时创建快照进行数据备份。false表示直接销毁，不进行数据备份。
     */
    private Boolean BackupDisk;

    /**
     * 与云盘绑定的标签，云盘未绑定标签则取值为空。
     注意：此字段可能返回 null，表示取不到有效值。
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private Tag[] Tags;

    /**
     * 云硬盘挂载的云主机ID。
     */
    private String InstanceId;

    /**
     * 云盘的挂载类型。取值范围：<br><li>PF: PF挂载<br><li>VF: VF挂载
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private String AttachMode;

    /**
     * 云盘关联的定期快照ID。只有在调用DescribeDisks接口时，入参ReturnBindAutoSnapshotPolicy取值为TRUE才会返回该参数。
     注意：此字段可能返回 null，表示取不到有效值。
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private String [] AutoSnapshotPolicyIds;

    /**
     * 云硬盘额外性能值，单位MB/s。
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private Long ThroughputPerformance;

    /**
     * 云盘是否处于类型变更中。取值范围：<br><li>false:表示云盘不处于类型变更中<br><li>true:表示云盘已发起类型变更，正处于迁移中。
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private Boolean Migrating;

    /**
     * 云硬盘ID。
     */
    private String DiskId;

    /**
     * 云盘拥有的快照总容量，单位为MB。
     */
    private Long SnapshotSize;

    /**
     * 云硬盘所在的位置。
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private Placement Placement;

    /**
     * 判断预付费的云盘是否支持主动退还。<br><li>true:支持主动退还<br><li>false:不支持主动退还。
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private Boolean IsReturnable;

    /**
     * 云硬盘的到期时间。
     */
    private String DeadlineTime;

    /**
     * 云盘是否挂载到云主机上。取值范围：<br><li>false:表示未挂载<br><li>true:表示已挂载。
     */
    private Boolean Attached;

    /**
     * 云硬盘大小，单位GB。
     */
    private Long DiskSize;

    /**
     * 云盘类型变更的迁移进度，取值0到100。
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private Long MigratePercent;

    /**
     * 云硬盘类型。取值范围：<br><li>SYSTEM_DISK：系统盘<br><li>DATA_DISK：数据盘。
     */
    private String DiskUsage;

    /**
     * 付费模式。取值范围：<br><li>PREPAID：预付费，即包年包月<br><li>POSTPAID_BY_HOUR：后付费，即按量计费。
     */
    private String DiskChargeType;

    /**
     * 是否为弹性云盘，false表示非弹性云盘，true表示弹性云盘。
     */
    private Boolean Portable;

    /**
     * 云盘是否具备创建快照的能力。取值范围：<br><li>false表示不具备<br><li>true表示具备。
     */
    private Boolean SnapshotAbility;

    /**
     * 在云盘已挂载到实例，且实例与云盘都是包年包月的条件下，此字段才有意义。<br><li>true:云盘到期时间早于实例。<br><li>false：云盘到期时间晚于实例。
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private Boolean DeadlineError;

    /**
     * 云盘快照回滚的进度。
     */
    private Long RollbackPercent;

    /**
     * 当前时间距离盘到期的天数（仅对预付费盘有意义）。
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private Long DifferDaysOfDeadline;

    /**
     * 预付费云盘在不支持主动退还的情况下，该参数表明不支持主动退还的具体原因。取值范围：<br><li>1：云硬盘已经退还<br><li>2：云硬盘已过期<br><li>3：云盘不支持退还<br><li>8：超过可退还数量的限制。
     注意：此字段可能返回 null，表示取不到有效值。
     */
    private Long ReturnFailCode;

    /**
     * 云盘是否为共享型云盘。
     */
    private Boolean Shareable;

    /**
     * 云硬盘的创建时间。
     */
    private String CreateTime;

    /**
     * 销毁云盘时删除关联的非永久保留快照。0 表示非永久快照不随云盘销毁而销毁，1表示非永久快照随云盘销毁而销毁，默认取0。快照是否永久保留可以通过DescribeSnapshots接口返回的快照详情的IsPermanent字段来判断，true表示永久快照，false表示非永久快照。
     */
    private Long DeleteSnapshot;

    /**
     * 云硬盘备份点已使用的数量。
     */
    private Long DiskBackupCount;

    /**
     * 云硬盘挂载实例的类型。取值范围：<br><li>CVM<br><li>EKS
     */
    private String InstanceType;
}