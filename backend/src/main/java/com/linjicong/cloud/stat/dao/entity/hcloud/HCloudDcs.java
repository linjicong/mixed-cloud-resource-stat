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


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.List;

/**
 * 华为云-分布式缓存服务 Redis
 *
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 * @see InstanceListInfo
 */
@Data
@Table(name = "h_cloud_dcs")
@Entity

public class HCloudDcs extends BasicEntity {

    /**
     * 弹性公网IP的ID
     */
    private String publicipId;

    /**
     * 虚拟私有云名称
     */
    private String vpcName;

    /**
     * 计费模式
     */
    private Integer chargingMode;

    /**
     * 虚拟私有云ID
     */
    private String vpcId;

    /**
     * 子网ID
     */
    private String subnetId;

    /**
     * 安全组ID
     */
    private String securityGroupId;

    /**
     * 创建时间
     */
    private String createdAt;

    /**
     * 是否启用SSL
     */
    private Boolean enableSsl;

    /**
     * 最大内存
     */
    private Integer maxMemory;

    /**
     * 已使用内存
     */
    private Integer usedMemory;

    /**
     * 弹性公网IP地址
     */
    private String publicipAddress;

    /**
     * 缓存容量
     */
    private Integer capacity;

    /**
     * 缓存容量小数部分
     */
    private String capacityMinor;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 维护时间开始
     */
    private String maintainBegin;

    /**
     * 维护时间结束
     */
    private String maintainEnd;

    /**
     * 缓存引擎
     */
    private String engine;

    /**
     * 缓存引擎版本
     */
    private String engineVersion;

    /**
     * 是否服务升级
     */
    private Boolean serviceUpgrade;

    /**
     * 无密码访问
     */
    private String noPasswordAccess;

    /**
     * 服务任务ID
     */
    private String serviceTaskId;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 访问用户
     */
    private String accessUser;

    /**
     * 实例ID
     */
    private String instanceId;

    /**
     * 是否启用公网IP
     */
    private Boolean enablePublicip;

    /**
     * 端口号
     */
    private Integer port;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 域名
     */
    private String domainName;

    /**
     * 只读域名
     */
    private String readonlyDomainName;

    /**
     * 实例名称
     */
    private String name;

    /**
     * 规格编码
     */
    private String specCode;

    /**
     * 实例状态
     */
    private String status;

    /**
     * 标签列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private List<ResourceTag> tags = null;

    /**
     * 企业项目ID
     */
    private String enterpriseProjectId;

    /**
     * 描述信息
     */
    private String description;

    /**
     * CPU类型
     */
    private String cpuType;

    /**
     * 可用区编码列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private List<String> azCodes = null;

    /**
     * 特性信息
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private Features features;

    /**
     * 子状态
     */
    private String subStatus;
}