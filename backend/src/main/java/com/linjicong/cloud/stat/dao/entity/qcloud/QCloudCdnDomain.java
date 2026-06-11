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
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.Type;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * 腾讯云-CDN域名
 *
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 * @see com.tencentcloudapi.cdn.v20180606.models.DetailDomain
 */
@Data
@Entity
@Table(name = "q_cloud_cdn_domain")
public class QCloudCdnDomain extends BasicEntity {

    /**
     * 域名ID
     */
    private String ResourceId;

    /**
     * 域名
     */
    private String Domain;

    /**
     * Cname
     */
    private String Cname;

    /**
     * 加速服务状态
     */
    private String Status;

    /**
     * 项目ID
     */
    private Long ProjectId;

    /**
     * 加速区域
     */
    private String Area;

    /**
     * 加速域名业务类型
     */
    private String ServiceType;

    /**
     * 域名创建时间
     */
    private String CreateTime;

    /**
     * 域名更新时间
     */
    private String UpdateTime;

    /**
     * 源站配置
     */
    @Column(columnDefinition = "json")
    @Type(JsonStringType.class)
    private String[] Origin;
}
