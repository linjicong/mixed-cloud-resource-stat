package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_config")
public class ACloudConfig extends BasicEntity {
    private String ResourceId;
    private String ResourceName;
    private String RegionId;
    private String ResourceType;
    private String ResourceCreationTime;
    private String AccountId;
    private String ResourceGroupId;
    private String ComplianceType;
    private String Configuration;
}
