package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_acr")
public class ACloudAcr extends BasicEntity {
    private String InstanceId;
    private String InstanceName;
    private String RegionId;
    private String InstanceSpecification;
    private String InstanceType;
    private String CreateTime;
    private String ModifiedTime;
    private String ResourceGroupId;
}
