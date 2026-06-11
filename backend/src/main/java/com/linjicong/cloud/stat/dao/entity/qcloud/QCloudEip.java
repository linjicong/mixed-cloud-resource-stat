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
 * 腾讯云-EIP弹性公网IP
 *
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 * @see com.tencentcloudapi.vpc.v20170312.models.Address
 */
@Data
@Entity
@Table(name = "q_cloud_eip")
public class QCloudEip extends BasicEntity {

    /**
     * EIP的ID
     */
    private String AddressId;

    /**
     * EIP名称
     */
    private String AddressName;

    /**
     * EIP状态
     */
    private String AddressStatus;

    /**
     * 外网IP地址
     */
    private String AddressIp;

    /**
     * 绑定的资源实例ID
     */
    private String InstanceId;

    /**
     * 绑定资源类型
     */
    private String InstanceType;

    /**
     * 创建时间
     */
    private String CreatedTime;

    /**
     * 计费模式
     */
    private String InternetChargeType;

    /**
     * 带宽上限
     */
    private Long InternetMaxBandwidthOut;

    /**
     * 供应商
     */
    private String AddressType;

    /**
     * 绑定的资源内网IP
     */
    private String PrivateAddressIp;

    /**
     * 资源到期时间
     */
    private String DeadlineDate;

    /**
     * 标签
     */
    @Column(columnDefinition = "json")
    @Type(JsonStringType.class)
    private Tag[] Tags;
}
