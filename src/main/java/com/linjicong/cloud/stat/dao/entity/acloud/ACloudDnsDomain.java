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
package com.linjicong.cloud.stat.dao.entity.acloud;

import com.huaweicloud.sdk.dns.v2.model.PrivateZoneResp;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * DNS 解析
 * @author linjicong
 * @version 1.0.0
 * @date 2022-08-24-9:51
 * @see DescribeDomainsResponseBodyDomainsDomain
 */
@Data
@Entity
@Table(name = "a_cloud_dns_domain")
@TypeDef(name = "json",typeClass = JsonStringType.class)
public class ACloudDnsDomain extends BasicEntity {

    public String domainName;

    public String line;

    public Boolean locked;

    public Long priority;

    /**
     * 主机记录
     */
    @Column(name = "rr")
    public String RR;

    public String recordId;

    public String remark;

    public String status;

    @Column(name = "ttl")
    public Long TTL;

    public String type;

    public String value;

    public Integer weight;
}
