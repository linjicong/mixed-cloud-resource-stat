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
import com.huaweicloud.sdk.sfsturbo.v1.model.ActionProgress;
import com.huaweicloud.sdk.sfsturbo.v1.model.Shares;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.OffsetDateTime;

/**
 * 华为云-文件存储
 *
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 */
@Data
@Table(name = "h_cloud_sfs")
@Entity
@TypeDef(name = "json",typeClass = JsonStringType.class)
public class HCloudSfs extends BasicEntity {

    @Column(columnDefinition="json")
    @Type(type = "json")
    private ActionProgress actionProgress;

    private String version;

    private String availCapacity;

    private String availabilityZone;

    private String azName;

    private OffsetDateTime createdAt;

    private String cryptKeyId;

    private String expandType;

    private String exportLocation;

    private String id;

    private String name;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private Shares.PayModelEnum payModel;

    private String region;

    private String securityGroupId;

    private String shareProto;

    private String shareType;

    private String size;

    private String status;

    private String subStatus;

    private String subnetId;

    private String vpcId;

    /**
     * 接口数据转换
     *
     */
    public static HCloudSfs fromShares(Shares shares) {
        HCloudSfs hCloudSfs = new HCloudSfs();
        BeanUtil.copyProperties(shares, hCloudSfs);
        hCloudSfs.setStatDate(DateUtil.today());
        return hCloudSfs;
    }
}
