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
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

/**
 * 对象存储
 *
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 * @see ObsBucket
 */
@Data
@Table(name = "h_cloud_obs")
@Entity
@TypeDef(name = "json",typeClass = JsonStringType.class)
public class HCloudObs extends BasicEntity {

    private String bucketName;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private Owner owner;

    private Date creationDate;

    private String location;

    @Enumerated(EnumType.STRING)
    private StorageClassEnum storageClass;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private Map<String, Object> metadata;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private AccessControlList acl;

    @Column(name="bucket_type_enum")
    private BucketTypeEnum bucketTypeEnum;

    /**
     * @see BucketStorageInfo
     */
    private Long size;

    /**
     * @see BucketStorageInfo
     */
    private Long objectNum;
}
