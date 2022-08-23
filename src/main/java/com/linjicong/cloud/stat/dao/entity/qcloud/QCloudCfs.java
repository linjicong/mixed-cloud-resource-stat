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

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import com.tencentcloudapi.cfs.v20190719.models.PGroup;
import com.tencentcloudapi.cfs.v20190719.models.TagInfo;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 腾讯-文件存储
 *
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 */
@Data
@Entity
@Table(name = "q_cloud_cfs")
@TypeDef(name = "json",typeClass = JsonStringType.class)
public class QCloudCfs extends BasicEntity {

    /**
     * 创建时间
     */
    private String CreationTime;

    /**
     * 用户自定义名称
     */
    private String CreationToken;

    /**
     * 文件系统 ID
     */
    private String FileSystemId;

    /**
     * 文件系统状态
     */
    private String LifeCycleState;

    /**
     * 文件系统已使用容量
     */
    private Long SizeByte;

    /**
     * 文件系统最大空间限制
     */
    private Long SizeLimit;

    /**
     * 区域 ID
     */
    private Long ZoneId;

    /**
     * 区域名称
     */
    private String Zone;

    /**
     * 文件系统协议类型
     */
    private String Protocol;

    /**
     * 文件系统存储类型
     */
    private String StorageType;

    /**
     * 文件系统绑定的预付费存储包
     */
    private String StorageResourcePkg;

    /**
     * 文件系统绑定的预付费带宽包（暂未支持）
     */
    private String BandwidthResourcePkg;

    /**
     * 文件系统绑定权限组信息
     */
    @Column(name="p_group", columnDefinition="json")
    @Type(type = "json")
    private PGroup PGroup;

    /**
     * 用户自定义名称
     */
    private String FsName;

    /**
     * 文件系统是否加密
     */
    private Boolean Encrypted;

    /**
     * 加密所使用的密钥，可以为密钥的 ID 或者 ARN
     */
    private String KmsKeyId;

    /**
     * 应用ID
     */
    private Long AppId;

    /**
     * 文件系统吞吐上限，吞吐上限是根据文件系统当前已使用存储量、绑定的存储资源包以及吞吐资源包一同确定
     */
    private Float BandwidthLimit;

    /**
     * 文件系统总容量
     */
    private Long Capacity;

    /**
     * 文件系统标签列表
     */
    @Column(columnDefinition="json")
    @Type(type = "json")
    private TagInfo[] Tags;
}
