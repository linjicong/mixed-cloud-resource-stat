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

import com.huaweicloud.sdk.dds.v3.model.*;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.Type;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.List;

/**
 * 华为云-文档数据库服务 Mongo
 *
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 * @see QueryInstanceResponse
 */
@Data
@Table(name = "h_cloud_dds")
@Entity

public class HCloudDds extends BasicEntity {

    /**
     * 实例ID
     */
    private String id;

    /**
     * 实例名称
     */
    private String name;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 实例状态
     */
    private String status;

    /**
     * 端口号
     */
    private String port;

    /**
     * 实例模式
     */
    private String mode;

    /**
     * 区域
     */
    private String region;

    /**
     * 数据库信息
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private DatastoreItem datastore;

    /**
     * 数据库引擎
     */
    private String engine;

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
     * SSL状态
     */
    @Column(name="`ssl`")
    private Integer ssl;

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
     * 备份策略
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private BackupStrategyForItemResponse backupStrategy;

    /**
     * 付费模式
     */
    private String payMode;

    /**
     * 维护时间窗口
     */
    private String maintenanceWindow;

    /**
     * 实例组信息
     */
    @Column(name = "`groups`",columnDefinition="json")
    @Type(JsonStringType.class)
    private List<GroupResponseItem> groups ;

    /**
     * 磁盘加密ID
     */
    private String diskEncryptionId;

    /**
     * 企业项目ID
     */
    private String enterpriseProjectId;

    /**
     * 时区
     */
    private String timeZone;

    /**
     * 专属存储池ID
     */
    private String dssPoolId;

    /**
     * 正在执行的操作列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private List<String> actions ;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 标签列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private List<TagResponse> tags ;
}