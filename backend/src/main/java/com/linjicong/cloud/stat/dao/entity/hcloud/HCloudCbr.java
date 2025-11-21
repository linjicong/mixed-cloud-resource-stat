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

import com.huaweicloud.sdk.cbr.v1.model.BackupExtendInfo;
import com.huaweicloud.sdk.cbr.v1.model.ResourceExtraInfo;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.List;

/**
 * 华为云-云备份服务（CBR）备份实体类
 * 用于存储华为云CBR备份资源信息
 * 对应华为云CBR API的Backup对象
 * 
 * @author linjicong
 * @version 1.0.0
 * @date 2024-12-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "h_cloud_cbr")
public class HCloudCbr extends BasicEntity {
    
    /**
     * 备份ID
     */
    private String id;
    
    /**
     * 备份名称
     */
    private String name;
    
    /**
     * 备份描述
     */
    private String description;
    
    /**
     * 备份状态
     * 取值范围：available, protecting, deleting, restore-error, error
     */
    private String status;
    
    /**
     * 备份创建时间
     */
    private String createdAt;
    
    /**
     * 备份更新时间
     */
    private String updatedAt;
    
    /**
     * 备份类型
     * 取值范围：manual, auto
     */
    private String backupType;
    
    /**
     * 备份方式
     * 取值范围：full, incremental
     */
    private String backupMethod;
    
    /**
     * 备份大小（字节）
     */
    private Long size;
    
    /**
     * 资源ID
     */
    private String resourceId;
    
    /**
     * 资源名称
     */
    private String resourceName;
    
    /**
     * 资源类型
     * 取值范围：OS::Nova::Server, OS::Cinder::Volume, OS::Sfs::Turbo
     */
    private String resourceType;
    
    /**
     * 备份记录ID
     */
    private String checkpointId;
    
    /**
     * 项目ID
     */
    private String projectId;
    
    /**
     * 企业项目ID
     */
    private String enterpriseProjectId;
    
    /**
     * 备份提供商ID
     */
    private String providerId;
    
    /**
     * 父备份ID
     */
    private String parentId;
    
    /**
     * 子备份列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private List<String> children;
    
    /**
     * 扩展信息
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private BackupExtendInfo extendInfo;
    
    /**
     * 资源额外信息
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private ResourceExtraInfo resourceExtraInfo;
    
    /**
     * 备份标签列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private List<Object> tags;
    
    /**
     * 是否支持恢复
     */
    private Boolean supportedRestoreMode;
    
    /**
     * 是否包含系统盘
     */
    private Boolean containSystemDisk;
    
    /**
     * 是否支持lazyloading
     */
    private Boolean supportedLazyLoading;
    
    /**
     * 是否支持重删
     */
    private Boolean supportedDedup;
    
    /**
     * 是否支持快速恢复
     */
    private Boolean supportedFastRestore;
    
    /**
     * 是否支持备份共享
     */
    private Boolean supportedSharedBackup;
    
    /**
     * 是否支持备份复制
     */
    private Boolean supportedReplication;
    
    /**
     * 是否支持备份迁移
     */
    private Boolean supportedMigration;
    
    /**
     * 是否支持备份加密
     */
    private Boolean supportedEncryption;
    
    /**
     * 是否支持备份压缩
     */
    private Boolean supportedCompression;
    
    /**
     * 是否支持备份去重
     */
    private Boolean supportedDeduplication;
    
    /**
     * 是否支持备份验证
     */
    private Boolean supportedVerification;
    
    /**
     * 是否支持备份清理
     */
    private Boolean supportedCleanup;
    
    /**
     * 是否支持备份归档
     */
    private Boolean supportedArchive;
    
    /**
     * 是否支持备份恢复
     */
    private Boolean supportedRestore;
}
