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


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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

public class HCloudRds extends BasicEntity {

    /**
     * 实例ID
     */
    private String id;

    /**
     * 实例状态
     */
    private String status;

    /**
     * 是否启用SSL
     */
    private Boolean enableSsl;

    /**
     * 内网IP地址列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private List<String> privateIps ;

    /**
     * 内网域名列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private List<String> privateDnsNames ;

    /**
     * 公网IP地址列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private List<String> publicIps ;

    /**
     * 实例类型
     */
    private String type;

    /**
     * 创建时间
     */
    private String created;

    /**
     * 更新时间
     */
    private String updated;

    /**
     * 数据库用户名
     */
    private String dbUserName;

    /**
     * 切换策略
     */
    private String switchStrategy;

    /**
     * 可维护时间窗
     */
    private String maintenanceWindow;

    /**
     * 节点信息列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private List<NodeResponse> nodes ;

    /**
     * 关联实例列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private List<RelatedInstance> relatedInstance ;

    /**
     * 实例名称
     */
    private String name;

    /**
     * 数据库信息
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private Datastore datastore;

    /**
     * 主备信息
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private HaResponse ha;

    /**
     * 数据库端口
     */
    private Integer port;

    /**
     * 备份策略
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private BackupStrategyForResponse backupStrategy;

    /**
     * 企业项目ID
     */
    private String enterpriseProjectId;

    /**
     * 磁盘加密ID
     */
    private String diskEncryptionId;

    /**
     * 规格ID
     */
    private String flavorRef;

    /**
     * CPU核数
     */
    private String cpu;

    /**
     * 内存大小（GB）
     */
    private String mem;

    /**
     * 磁盘信息
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private Volume volume;

    /**
     * 区域
     */
    private String region;

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
     * 计费信息
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private ChargeInfoResponse chargeInfo;

    /**
     * 时区
     */
    private String timeZone;

    /**
     * 标签列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private List<TagResponse> tags ;

    /**
     * 备份已使用空间（GB）
     */
    private Double backupUsedSpace;

    /**
     * 存储已使用空间（GB）
     */
    private Double storageUsedSpace;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 是否关联DDM
     */
    private Boolean associatedWithDdm;

    /**
     * 实例别名
     */
    private String alias;

    /**
     * 最大IOPS
     */
    private Long maxIops;

    /**
     * 到期时间
     */
    private String expirationTime;
}
