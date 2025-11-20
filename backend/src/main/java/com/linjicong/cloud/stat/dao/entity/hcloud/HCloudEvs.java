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

import com.huaweicloud.sdk.evs.v2.model.Attachment;
import com.huaweicloud.sdk.evs.v2.model.Link;
import com.huaweicloud.sdk.evs.v2.model.VolumeDetail;
import com.huaweicloud.sdk.evs.v2.model.VolumeMetadata;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.Type;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Map;

/**
 * @author linjicong
 * @version 1.0.0
 * @date 2022-08-03-10:17
 * @see VolumeDetail
 */
@Data
@Table(name = "h_cloud_evs")
@Entity

public class HCloudEvs extends BasicEntity {

    /**
     * 云硬盘ID
     */
    private String id;

    /**
     * 云硬盘链接信息
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private List<Link> links = null;

    /**
     * 云硬盘名称
     */
    private String name;

    /**
     * 云硬盘状态
     */
    private String status;

    /**
     * 云硬盘挂载信息列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private List<Attachment> attachments = null;

    /**
     * 可用区
     */
    private String availabilityZone;

    /**
     * 云硬盘所在的主机
     */
    private String osVolHostAttrHost;

    /**
     * 源云硬盘ID
     */
    private String sourceVolid;

    /**
     * 快照ID
     */
    private String snapshotId;

    /**
     * 云硬盘描述
     */
    private String description;

    /**
     * 创建时间
     */
    private String createdAt;

    /**
     * 租户ID
     */
    private String osVolTenantAttrTenantId;

    /**
     * 云硬盘镜像元数据
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private Map<String, Object> volumeImageMetadata = null;

    /**
     * 云硬盘类型
     */
    private String volumeType;

    /**
     * 云硬盘大小（GB）
     */
    private Integer size;

    /**
     * 一致性组ID
     */
    private String consistencygroupId;

    /**
     * 是否可启动
     */
    private String bootable;

    /**
     * 云硬盘元数据
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private VolumeMetadata metadata;

    /**
     * 更新时间
     */
    private String updatedAt;

    /**
     * 是否加密
     */
    private Boolean encrypted;

    /**
     * 复制状态
     */
    private String replicationStatus;

    /**
     * 复制扩展状态
     */
    private String osVolumeReplicationExtendedStatus;

    /**
     * 迁移状态
     */
    private String osVolMigStatusAttrMigstat;

    /**
     * 迁移名称ID
     */
    private String osVolMigStatusAttrNameId;

    /**
     * 是否可共享
     */
    private String shareable;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 服务类型
     */
    private String serviceType;

    /**
     * 是否支持多挂载
     */
    private Boolean multiattach;

    /**
     * 专属存储ID
     */
    private String dedicatedStorageId;

    /**
     * 专属存储名称
     */
    private String dedicatedStorageName;

    /**
     * 标签列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private Map<String, String> tags = null;

    /**
     * 全球唯一标识符
     */
    private String wwn;

    /**
     * 企业项目ID
     */
    private String enterpriseProjectId;

    /**
     * 序列号
     */
    private String serialNumber;
}
