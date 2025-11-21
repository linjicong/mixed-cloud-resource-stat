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

import com.baomidou.mybatisplus.annotation.TableField;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import com.linjicong.cloud.stat.dao.typehandle.impl.StringJsonTypeHandle;
import com.linjicong.cloud.stat.dao.typehandle.impl.qcloud.dns.TagItemJsonTypeHandle;
import com.tencentcloudapi.cvm.v20170312.models.Instance;
import com.tencentcloudapi.dnspod.v20210323.models.TagItem;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Type;

/**
 * 腾讯云-DNS域名
 *
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 * @see Instance
 */
@Data
@Entity
@Table(name = "q_cloud_dns_domain")

public class QCloudDnsDomain extends BasicEntity {

    /**
     * 域名ID
     */
    private Long DomainId;

    /**
     * 域名名称
     */
    private String Name;

    /**
     * 域名状态
     */
    private String Status;

    /**
     * TTL时间
     */
    @TableField(value = "ttl")
    @Column(name = "ttl")
    private Long TTL;

    /**
     * CNAME加速状态
     */
    @TableField(value = "cname_speedup")
    @Column(name = "cname_speedup")
    private String CNAMESpeedup;

    /**
     * DNS状态
     */
    @TableField(value = "dns_status")
    @Column(name = "dns_status")
    private String DNSStatus;

    /**
     * 域名等级
     */
    private String Grade;

    /**
     * 分组ID
     */
    private Long GroupId;

    /**
     * 搜索引擎推送状态
     */
    private String SearchEnginePush;

    /**
     * 备注信息
     */
    private String Remark;

    /**
     * Punycode编码域名
     */
    private String Punycode;

    /**
     * 有效DNS服务器列表
     */
    @Column(columnDefinition="json",name = "effective_dns")
    @Type(JsonStringType.class)
    @TableField(value = "effective_dns",typeHandler = StringJsonTypeHandle.class)
    private String[] EffectiveDNS;

    /**
     * 等级级别
     */
    private Long GradeLevel;

    /**
     * 等级标题
     */
    private String GradeTitle;

    /**
     * 是否为VIP域名
     */
    private String IsVip;

    /**
     * VIP开始时间
     */
    private String VipStartAt;

    /**
     * VIP结束时间
     */
    private String VipEndAt;

    /**
     * VIP自动续费状态
     */
    private String VipAutoRenew;

    /**
     * 记录数量
     */
    private Long RecordCount;

    /**
     * 创建时间
     */
    private String CreatedOn;

    /**
     * 更新时间
     */
    private String UpdatedOn;

    /**
     * 域名所有者
     */
    private String Owner;

    /**
     * 标签列表
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    @TableField(typeHandler = TagItemJsonTypeHandle.class)
    private TagItem[] TagList;
}