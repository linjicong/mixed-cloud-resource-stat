package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_kafka")
public class ACloudKafka extends BasicEntity {
    private String InstanceId;
    private String Name;
    private String RegionId;
    private String ServiceStatus;
    private String DeployType;
    private String CreateTime;
    private String ExpireTime;
    private String ResourceGroupId;
}
