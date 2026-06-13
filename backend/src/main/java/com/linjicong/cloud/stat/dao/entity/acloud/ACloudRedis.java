package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_redis")
public class ACloudRedis extends BasicEntity {
    private String InstanceId;
    private String InstanceName;
    private String RegionId;
    private Long Capacity;
    private String InstanceClass;
    private String InstanceType;
    private String Status;
    private String EngineVersion;
    private String PayType;
    private String CreateTime;
    private String EndTime;
    private String ResourceGroupId;
}
