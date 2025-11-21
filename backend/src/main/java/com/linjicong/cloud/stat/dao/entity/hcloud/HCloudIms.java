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

import com.huaweicloud.sdk.ims.v2.model.ImageInfo;
import com.huaweicloud.sdk.ims.v2.model.Links;
import com.huaweicloud.sdk.ims.v2.model.TagKeyValue;
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
 * 华为云-镜像服务（IMS）
 * @author linjicong
 * @version 1.0.0
 * @date 2024-12-24
 * @see ImageInfo
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "h_cloud_ims")
public class HCloudIms extends BasicEntity {
    /**
     * 镜像ID
     */
    private String id;
    
    /**
     * 镜像名称
     */
    private String name;
    
    /**
     * 镜像状态
     */
    private String status;
    
    /**
     * 镜像类型
     */
    private String imageType;
    
    /**
     * 镜像格式
     */
    private String diskFormat;
    
    /**
     * 镜像大小（字节）
     */
    private Long size;
    
    /**
     * 镜像所属租户ID
     */
    private String owner;
    
    /**
     * 镜像是否可用
     */
    private Boolean isPublic;
    
    /**
     * 镜像是否受保护
     */
    @Column(name = "protected")
    private Boolean isProtected;
    
    /**
     * 镜像可见性
     */
    private String visibility;
    
    /**
     * 镜像架构类型
     */
    private String architecture;
    
    /**
     * 镜像虚拟化类型
     */
    private String virtualEnvType;
    
    /**
     * 镜像操作系统类型
     */
    private String osType;
    
    /**
     * 镜像平台类型
     */
    private String platform;
    
    /**
     * 镜像操作系统版本
     */
    private String osVersion;
    
    /**
     * 镜像描述
     */
    private String description;
    
    /**
     * 镜像创建时间
     */
    private String createdAt;
    
    /**
     * 镜像更新时间
     */
    private String updatedAt;
    
    /**
     * 镜像删除时间
     */
    private String deletedAt;
    
    /**
     * 镜像删除标记（注意：与BasicEntity的deleted字段不同，这是镜像本身的删除标记）
     */
    @Column(name = "image_deleted")
    private Boolean imageDeleted;
    
    /**
     * 镜像最小磁盘大小（GB）
     */
    private Integer minDisk;
    
    /**
     * 镜像最小内存大小（MB）
     */
    private Integer minRam;
    
    /**
     * 镜像校验和
     */
    private String checksum;
    
    /**
     * 镜像文件下载地址
     */
    private String file;
    
    /**
     * 镜像后端存储类型
     */
    private String backend;
    
    /**
     * 镜像来源
     */
    private String source;
    
    /**
     * 镜像来源类型
     */
    private String imageSourceType;
    
    /**
     * 镜像标签列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private List<TagKeyValue> tags;
    
    /**
     * 镜像链接列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private List<Links> links;
    
    /**
     * 镜像所属企业项目ID
     */
    private String enterpriseProjectId;
    
    /**
     * 镜像产品编码
     */
    private String productCode;
    
    /**
     * 镜像源ID
     */
    private String imageSourceId;
    
    /**
     * 镜像源名称
     */
    private String imageSourceName;
    
    /**
     * 镜像源区域
     */
    private String imageSourceRegion;
    
    /**
     * 镜像源项目ID
     */
    private String imageSourceProjectId;
}

