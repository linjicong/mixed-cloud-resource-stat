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

import com.huaweicloud.sdk.rds.v3.model.*;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * 关系型数据
 *
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 * @see InstanceResponse
 */
@Data
@Table(name = "h_cloud_rds")
@Entity
@TypeDef(name = "json",typeClass = JsonStringType.class)
public class HCloudRds extends BasicEntity {

    private String id;

    private String status;

    private Boolean enableSsl;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private List<String> privateIps ;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private List<String> privateDnsNames ;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private List<String> publicIps ;

    private String type;

    private String created;

    private String updated;

    private String dbUserName;

    private String switchStrategy;

    private String maintenanceWindow;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private List<NodeResponse> nodes ;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private List<RelatedInstance> relatedInstance ;

    private String name;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private Datastore datastore;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private HaResponse ha;

    private Integer port;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private BackupStrategyForResponse backupStrategy;

    private String enterpriseProjectId;

    private String diskEncryptionId;

    private String flavorRef;

    private String cpu;

    private String mem;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private Volume volume;

    private String region;

    private String vpcId;

    private String subnetId;

    private String securityGroupId;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private ChargeInfoResponse chargeInfo;

    private String timeZone;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private List<TagResponse> tags ;

    private Double backupUsedSpace;

    private Double storageUsedSpace;

    private String orderId;

    private Boolean associatedWithDdm;

    private String alias;

    private Long maxIops;

    private String expirationTime;
}
