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

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import com.obs.services.model.*;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.Type;


import jakarta.persistence.*;
import java.util.Date;
import java.util.Map;

/**
 * 华为云-对象存储服务
 *
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 * @see ObsBucket
 */
@Data
@Table(name = "h_cloud_obs")
@Entity

public class HCloudObs extends BasicEntity {

    /**
     * 桶名称
     */
    private String bucketName;

    /**
     * 桶所有者信息
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private Owner owner;

    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 桶所在区域
     */
    private String location;

    /**
     * 存储类别
     */
    @Enumerated(EnumType.STRING)
    private StorageClassEnum storageClass;

    /**
     * 桶元数据
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private Map<String, Object> metadata;

    /**
     * 访问控制列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private AccessControlList acl;

    /**
     * 桶类型枚举
     */
    @Column(name="bucket_type_enum")
    @Enumerated(EnumType.STRING)
    private BucketTypeEnum bucketTypeEnum;

    /**
     * 桶大小（字节）
     * @see BucketStorageInfo
     */
    private Long size;

    /**
     * 对象数量
     * @see BucketStorageInfo
     */
    private Long objectNum;
}