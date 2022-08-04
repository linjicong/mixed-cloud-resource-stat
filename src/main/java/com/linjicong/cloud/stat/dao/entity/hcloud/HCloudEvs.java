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
import com.huaweicloud.sdk.evs.v2.model.VolumeMetadata;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import java.util.Map;

/**
 * @author linjicong
 * @version 1.0.0
 * @date 2022-08-03-10:17
 */
@Data
@Table(name = "h_cloud_evs")
@Entity
@TypeDef(name = "json",typeClass = JsonStringType.class)
public class HCloudEvs extends BasicEntity {

    private String id;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private List<Link> links = null;

    private String name;

    private String status;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private List<Attachment> attachments = null;

    private String availabilityZone;

    private String osVolHostAttrHost;

    private String sourceVolid;

    private String snapshotId;

    private String description;

    private String createdAt;

    private String osVolTenantAttrTenantId;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private Map<String, Object> volumeImageMetadata = null;

    private String volumeType;

    private Integer size;

    private String consistencygroupId;

    private String bootable;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private VolumeMetadata metadata;

    private String updatedAt;

    private Boolean encrypted;

    private String replicationStatus;

    private String osVolumeReplicationExtendedStatus;

    private String osVolMigStatusAttrMigstat;

    private String osVolMigStatusAttrNameId;

    private String shareable;

    private String userId;

    private String serviceType;

    private Boolean multiattach;

    private String dedicatedStorageId;

    private String dedicatedStorageName;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private Map<String, String> tags = null;

    private String wwn;

    private String enterpriseProjectId;

    private String serialNumber;
}
