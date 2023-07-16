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
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private Placement Placement;

    private String InstanceId;

    private String InstanceType;

    @Column(name = "cpu")
    private Long CPU;

    private Long Memory;

    private String RestrictState;

    private String InstanceName;

    private String InstanceChargeType;

    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private SystemDisk SystemDisk;

    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private DataDisk[] DataDisks;

    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private String[] PrivateIpAddresses;

    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private String[] PublicIpAddresses;

    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private InternetAccessible InternetAccessible;

    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private VirtualPrivateCloud VirtualPrivateCloud;

    private String ImageId;

    private String RenewFlag;

    private String CreatedTime;

    private String ExpiredTime;

    private String OsName;

    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private String[] SecurityGroupIds;

    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private LoginSettings LoginSettings;

    private String InstanceState;

    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private Tag[] Tags;

    private String StopChargingMode;

    private String Uuid;

    private String LatestOperation;

    private String LatestOperationState;

    private String LatestOperationRequestId;

    private String DisasterRecoverGroupId;

    @Column(name="ipv6_addresses", columnDefinition="json")
    @Type(JsonStringType.class)
    private String[] IPv6Addresses;

    private String CamRoleName;

    private String HpcClusterId;

    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private String[] RdmaIpAddresses;

    private String IsolatedSource;

    @Column(name="gpu_info",columnDefinition="json")
    @Type(JsonStringType.class)
    private GPUInfo GPUInfo;

    private String LicenseType;
}
