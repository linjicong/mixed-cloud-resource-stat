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

import com.huaweicloud.sdk.bss.v2.model.MonthlyBillRecord;
import com.huaweicloud.sdk.iam.v3.model.LinksSelf;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.Type;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

/**
 * 华为云-认证域
 * @author linjicong
 * @version 1.0.0
 * @date 2022-08-09-17:28
 * @see MonthlyBillRecord
 */
@Data
@Table(name = "h_cloud_auth_domain",indexes = {@Index(columnList = "statDate")})
@Entity

public class HCloudAuthDomain extends BasicEntity {

    /**
     * 是否启用
     */
    private Boolean enabled;

    /**
     * 域ID
     */
    private String id;

    /**
     * 域名称
     */
    private String name;

    /**
     * 链接信息
     */
    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private LinksSelf links;

    /**
     * 描述信息
     */
    private String description;

}