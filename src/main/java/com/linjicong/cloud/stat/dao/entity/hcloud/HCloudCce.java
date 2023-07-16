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

import com.huaweicloud.sdk.cce.v3.model.ClusterMetadata;
import com.huaweicloud.sdk.cce.v3.model.ClusterSpec;
import com.huaweicloud.sdk.cce.v3.model.ClusterStatus;
import com.huaweicloud.sdk.ces.v1.model.MetricInfoList;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.Type;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * 华为云-云监控-指标
 * @author linjicong
 * @version 1.0.0
 * @date 2022-08-03-10:17
 * @see MetricInfoList
 */
@Data
@Table(name = "h_cloud_cce")
@Entity

public class HCloudCce extends BasicEntity {

    private String kind;

    private String apiVersion;

    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private ClusterMetadata metadata;

    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private ClusterSpec spec;

    @Column(columnDefinition="json")
    @Type(JsonStringType.class)
    private ClusterStatus status;
}
