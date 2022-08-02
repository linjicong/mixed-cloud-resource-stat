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

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.huaweicloud.sdk.dcs.v2.model.InstanceListInfo;
import com.huaweicloud.sdk.dds.v3.model.*;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * 文档数据库 Mongo
 *
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 */
@Data
@Table(name = "h_cloud_dds")
@Entity
@TypeDef(name = "json",typeClass = JsonStringType.class)
public class HCloudDds extends BasicEntity {

    private String id;

    private String name;

    private String remark;

    private String status;

    private String port;

    private String mode;

    private String region;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private DatastoreItem datastore;

    private String engine;

    private String created;

    private String updated;

    private String dbUserName;

    @Column(name="`ssl`")
    private Integer ssl;

    private String vpcId;

    private String subnetId;

    private String securityGroupId;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private BackupStrategyForItemResponse backupStrategy;

    private String payMode;

    private String maintenanceWindow;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private List<GroupResponseItem> groups ;

    private String diskEncryptionId;

    private String enterpriseProjectId;

    private String timeZone;

    private String dssPoolId;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private List<String> actions ;

    private String orderId;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private List<TagResponse> tags ;

    /**
     * 接口数据转换
     *
     */
    public static HCloudDds fromQueryInstanceResponse(QueryInstanceResponse queryInstanceResponse) {
        HCloudDds hCloudDds = new HCloudDds();
        BeanUtil.copyProperties(queryInstanceResponse, hCloudDds);
        hCloudDds.setStatDate(DateUtil.today());
        return hCloudDds;
    }
}
