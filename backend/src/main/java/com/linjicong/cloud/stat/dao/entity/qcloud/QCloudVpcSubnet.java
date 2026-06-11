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
import com.tencentcloudapi.vpc.v20170312.models.Tag;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.Type;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * 腾讯云-子网
 *
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 * @see com.tencentcloudapi.vpc.v20170312.models.Subnet
 */
@Data
@Entity
@Table(name = "q_cloud_vpc_subnet")
public class QCloudVpcSubnet extends BasicEntity {

    /**
     * VPC实例ID
     */
    private String VpcId;

    /**
     * 子网实例ID
     */
    private String SubnetId;

    /**
     * 子网名称
     */
    private String SubnetName;

    /**
     * 子网的CIDR
     */
    private String CidrBlock;

    /**
     * 是否默认子网
     */
    private Boolean IsDefault;

    /**
     * 是否开启广播
     */
    private Boolean EnableBroadcast;

    /**
     * 可用区
     */
    private String Zone;

    /**
     * 路由表实例ID
     */
    private String RouteTableId;

    /**
     * 创建时间
     */
    private String CreatedTime;

    /**
     * 可用IPv4数
     */
    private Long AvailableIpAddressCount;

    /**
     * 子网的IPv6 CIDR
     */
    private String Ipv6CidrBlock;

    /**
     * 网络ACL ID
     */
    private String NetworkAclId;

    /**
     * 标签
     */
    @Column(columnDefinition = "json")
    @Type(JsonStringType.class)
    private Tag[] Tags;
}
