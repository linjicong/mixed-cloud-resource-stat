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

import com.huaweicloud.sdk.sfsturbo.v1.model.ActionProgress;
import com.huaweicloud.sdk.sfsturbo.v1.model.ShareInfo;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.time.OffsetDateTime;

/**
 * 华为云-文件存储服务
 *
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 * @see ShareInfo
 */
@Data
@Table(name = "h_cloud_sfs")
@Entity

public class HCloudSfs extends BasicEntity {

    /**
     * 操作进度信息
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private ActionProgress actionProgress;

    /**
     * 版本
     */
    private String version;

    /**
     * 可用容量
     */
    private String availCapacity;

    /**
     * 可用区
     */
    private String availabilityZone;

    /**
     * 可用区名称
     */
    private String azName;

    /**
     * 创建时间
     */
    private OffsetDateTime createdAt;

    /**
     * 加密密钥ID
     */
    private String cryptKeyId;

    /**
     * 扩展类型
     */
    private String expandType;

    /**
     * 导出位置
     */
    private String exportLocation;

    /**
     * 文件系统ID
     */
    private String id;

    /**
     * 文件系统名称
     */
    private String name;

    /**
     * 付费模式
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private ShareInfo.PayModelEnum payModel;

    /**
     * 区域
     */
    private String region;

    /**
     * 安全组ID
     */
    private String securityGroupId;

    /**
     * 共享协议
     */
    private String shareProto;

    /**
     * 共享类型
     */
    private String shareType;

    /**
     * 文件系统大小
     */
    private String size;

    /**
     * 状态
     */
    private String status;

    /**
     * 子状态
     */
    private String subStatus;

    /**
     * 子网ID
     */
    private String subnetId;

    /**
     * 虚拟私有云ID
     */
    private String vpcId;
}