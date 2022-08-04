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
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * @author linjicong
 * @version 1.0.0
 * @date 2022-08-03-10:17
 */
@Data
@Table(name = "h_cloud_elb")
@Entity
@TypeDef(name = "json",typeClass = JsonStringType.class)
public class HCloudElb extends BasicEntity {

    private String id;

    private String description;

    private String provisioningStatus;

    private Boolean adminStateUp;

    private String provider;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private List<PoolRef> pools;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private List<ListenerRef> listeners;

    private String operatingStatus;

    private String name;

    private String projectId;

    private String vipSubnetCidrId;

    private String vipAddress;

    private String vipPortId;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private List<Tag> tags;

    private String createdAt;

    private String updatedAt;

    private Boolean guaranteed;

    private String vpcId;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private List<EipInfo> eips;

    @Column(name= "ipv6_vip_address")
    private String ipv6VipAddress;

    @Column(name= "ipv6_vip_virsubnet_id")
    private String ipv6VipVirsubnetId;

    @Column(name= "ipv6_vip_port_id")
    private String ipv6VipPortId;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private List<String> availabilityZoneList;

    private String enterpriseProjectId;

    private String billingInfo;

    @Column(name= "l4_flavor_id")
    private String l4FlavorId;

    @Column(name= "l4_scale_flavor_id")
    private String l4ScaleFlavorId;

    @Column(name= "l7_flavor_id")
    private String l7FlavorId;

    @Column(name= "l7_scale_flavor_id")
    private String l7ScaleFlavorId;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private List<PublicIpInfo> publicips;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private List<String> elbVirsubnetIds;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private LoadBalancer.ElbVirsubnetTypeEnum elbVirsubnetType;

    private Boolean ipTargetEnable;

    private String frozenScene;

    @Column(name="ipv6_bandwidth",columnDefinition="json")
    @Type(type = "json")
    private BandwidthRef ipv6Bandwidth;

    private Boolean deletionProtectionEnable;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private AutoscalingRef autoscaling;
}
