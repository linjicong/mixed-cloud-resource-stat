package com.linjicong.cloud.stat.dao.entity.acloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Type;

@Data
@Entity
@Table(name = "a_cloud_ecs")
public class ACloudEcs extends BasicEntity {
    private String InstanceId;
    private String InstanceName;
    private String InstanceType;
    private Integer CPU;
    private Integer Memory;
    private String Status;
    private String RegionId;
    private String ZoneId;
    private String ImageId;
    private String OSName;
    private String OSType;
    private String InstanceChargeType;
    private String CreationTime;
    private String ExpiredTime;
    private String InternetChargeType;
    private Integer InternetMaxBandwidthOut;
    private String NetworkType;
    private String KeyPairName;
    private String ResourceGroupId;
    @Column(columnDefinition = "json")
    @Type(JsonStringType.class)
    private String[] SecurityGroupIds;
    @Column(columnDefinition = "json")
    @Type(JsonStringType.class)
    private Object PublicIpAddress;
    @Column(columnDefinition = "json")
    @Type(JsonStringType.class)
    private Object InnerIpAddress;
    @Column(columnDefinition = "json")
    @Type(JsonStringType.class)
    private Object VpcAttributes;
    @Column(columnDefinition = "json")
    @Type(JsonStringType.class)
    private Object Tags;
}
