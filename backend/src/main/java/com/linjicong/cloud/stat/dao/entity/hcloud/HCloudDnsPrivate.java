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

import com.huaweicloud.sdk.dns.v2.model.PageLink;
import com.huaweicloud.sdk.dns.v2.model.PrivateZoneResp;
import com.huaweicloud.sdk.dns.v2.model.RouterWithStatus;
import com.huaweicloud.sdk.dns.v2.model.Tag;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.Type;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.List;

/**
 * 华为云-DNS私有解析
 * @author linjicong
 * @version 1.0.0
 * @date 2022-08-24-9:51
 * @see PrivateZoneResp
 */
@Data
@Entity
@Table(name = "h_cloud_dns_private")

public class HCloudDnsPrivate extends BasicEntity {
    /**
     * 域名ID
     */
    private String id;
    
    /**
     * 域名名称
     */
    private String name;
    
    /**
     * 描述信息
     */
    private String description;
    
    /**
     * 邮箱地址
     */
    private String email;
    
    /**
     * 域名类型
     */
    private String zoneType;
    
    /**
     * TTL时间
     */
    private Integer ttl;
    
    /**
     * 序列号
     */
    private Integer serial;
    
    /**
     * 状态
     */
    private String status;
    
    /**
     * 记录数量
     */
    private Integer recordNum;
    
    /**
     * 资源池ID
     */
    private String poolId;
    
    /**
     * 项目ID
     */
    private String projectId;
    
    /**
     * 创建时间
     */
    private String createdAt;
    
    /**
     * 更新时间
     */
    private String updatedAt;
    
    /**
     * 链接信息
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private PageLink links;
    
    /**
     * 标签列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private List<Tag> tags;
    
    /**
     * 主DNS服务器列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private List<String> masters;
    
    /**
     * 路由信息列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private List<RouterWithStatus> routers;
    
    /**
     * 企业项目ID
     */
    private String enterpriseProjectId;
}