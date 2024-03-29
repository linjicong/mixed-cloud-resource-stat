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

import com.aliyun.alidns20150109.models.DescribeDomainsResponseBody;
import com.baomidou.mybatisplus.annotation.TableField;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 阿里云-域名
 * @author linjicong
 * @version 1.0.0
 * @date 2022-08-24-9:51
 * @see DescribeDomainsResponseBody.DescribeDomainsResponseBodyDomainsDomain
 */
@Data
@Entity
@Table(name = "a_cloud_dns_domain")
public class ACloudDnsDomain extends BasicEntity {

    private String domainName;

    private String line;

    private Boolean locked;

    private Long priority;

    /**
     * 主机记录
     */
    @TableField(value = "rr")
    private String RR;

    private String recordId;

    private String remark;

    private String status;

    @TableField(value = "ttl")
    private Long TTL;

    private String type;

    private String value;

    private Integer weight;
}
