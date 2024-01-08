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
 * 腾讯-弹性云服务器
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

    private Long DomainId;

    private String Name;

    private String Status;

    @TableField(value = "ttl")
    @Column(name = "ttl")
    private Long TTL;

    @TableField(value = "cname_speedup")
    @Column(name = "cname_speedup")
    private String CNAMESpeedup;

    @TableField(value = "dns_status")
    @Column(name = "dns_status")
    private String DNSStatus;

    private String Grade;

    private Long GroupId;

    private String SearchEnginePush;

    private String Remark;

    private String Punycode;

    @Column(columnDefinition="json",name = "effective_dns")
    @Type(JsonStringType.class)
    @TableField(value = "effective_dns",typeHandler = StringJsonTypeHandle.class)
    private String[] EffectiveDNS;

    private Long GradeLevel;

    private String GradeTitle;

    private String IsVip;

    private String VipStartAt;

    private String VipEndAt;

    private String VipAutoRenew;

    private Long RecordCount;

    private String CreatedOn;

    private String UpdatedOn;

    private String Owner;

    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    @TableField(typeHandler = TagItemJsonTypeHandle.class)
    private TagItem[] TagList;
}
