package com.linjicong.cloud.stat.dao.entity.hcloud;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.huaweicloud.sdk.rds.v3.model.*;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 关系型数据
 *
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 */
@Data
@Table(name = "h_cloud_rds")
@Entity
@TypeDef(name = "json",typeClass = JsonStringType.class)
public class HCloudRds extends BasicEntity {

    private String id;

    private String status;

    private Boolean enableSsl;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private List<String> privateIps ;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private List<String> privateDnsNames ;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private List<String> publicIps ;

    private String type;

    private String created;

    private String updated;

    private String dbUserName;

    private String switchStrategy;

    private String maintenanceWindow;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private List<NodeResponse> nodes ;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private List<RelatedInstance> relatedInstance ;

    private String name;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private Datastore datastore;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private HaResponse ha;

    private Integer port;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private BackupStrategyForResponse backupStrategy;

    private String enterpriseProjectId;

    private String diskEncryptionId;

    private String flavorRef;

    private String cpu;

    private String mem;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private Volume volume;

    private String region;

    private String vpcId;

    private String subnetId;

    private String securityGroupId;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private ChargeInfoResponse chargeInfo;

    private String timeZone;

    @Column(columnDefinition="json")
    @Type(type = "json")
    private List<TagResponse> tags ;

    private Double backupUsedSpace;

    private Double storageUsedSpace;

    private String orderId;

    private Boolean associatedWithDdm;

    private String alias;

    private Long maxIops;

    private String expirationTime;

    /**
     * 接口数据转换
     * @param instanceResponse instanceResponse
     * @return HCloudEcs
     */
    public static HCloudRds fromInstanceResponse(InstanceResponse instanceResponse) {
        HCloudRds hCloudRds = new HCloudRds();
        BeanUtil.copyProperties(instanceResponse, hCloudRds);
        hCloudRds.setStatDate(DateUtil.today());
        return hCloudRds;
    }
}
