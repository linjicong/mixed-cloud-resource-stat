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

import com.huaweicloud.sdk.dcs.v2.model.Features;
import com.huaweicloud.sdk.dcs.v2.model.InstanceListInfo;
import com.huaweicloud.sdk.dcs.v2.model.ResourceTag;
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
 * 分布式缓存 Redis
 *
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 * @see InstanceListInfo
 */
@Data
@Table(name = "h_cloud_dcs")
@Entity
@TypeDef(name = "json",typeClass = JsonStringType.class)
public class HCloudDcs extends BasicEntity {

    private String publicipId;

    private String vpcName;

    private Integer chargingMode;

    private String vpcId;

    private String subnetId;

    private String securityGroupId;

    private String createdAt;

    private Boolean enableSsl;

    private Integer maxMemory;

    private Integer usedMemory;

    private String publicipAddress;

    private Integer capacity;

    private String capacityMinor;

    private String orderId;

    private String maintainBegin;

    private String maintainEnd;

    private String engine;

    private String engineVersion;

    private Boolean serviceUpgrade;

    private String noPasswordAccess;

    private String serviceTaskId;

    private String ip;

    private String accessUser;

    private String instanceId;

    private Boolean enablePublicip;

    private Integer port;

    private String userId;

    private String userName;

    private String domainName;

    private String readonlyDomainName;

    private String name;

    private String specCode;

    private String status;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private List<ResourceTag> tags = null;

    private String enterpriseProjectId;

    private String description;

    private String cpuType;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private List<String> azCodes = null;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private Features features;

    private String subStatus;
}
