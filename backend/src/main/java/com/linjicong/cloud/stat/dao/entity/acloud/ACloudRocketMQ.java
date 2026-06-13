package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_rocket_mq")
public class ACloudRocketMQ extends BasicEntity {
    private String InstanceId;
    private String InstanceName;
    private String RegionId;
    private Boolean IndependentNaming;
    private String CreateTime;
}
