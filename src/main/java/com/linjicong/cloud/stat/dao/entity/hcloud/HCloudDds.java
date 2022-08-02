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
