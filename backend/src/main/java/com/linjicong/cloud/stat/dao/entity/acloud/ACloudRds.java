package com.linjicong.cloud.stat.dao.entity.acloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_rds")
public class ACloudRds extends BasicEntity {
    private String DBInstanceId;
    private String DBInstanceDescription;
    private String RegionId;
    private String ZoneId;
    private String DBInstanceClass;
    private String DBInstanceType;
    private String DBInstanceStatus;
    private String Engine;
    private String EngineVersion;
    private String PayType;
    private String CreationTime;
    private String ExpireTime;
    private String ResourceGroupId;
}
