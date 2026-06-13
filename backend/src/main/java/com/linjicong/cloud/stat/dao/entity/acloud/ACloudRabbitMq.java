package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_rabbit_mq")
public class ACloudRabbitMq extends BasicEntity {
    private String InstanceId;
    private String InstanceName;
    private String RegionId;
    private String InstanceType;
    private String Status;
    private String PayType;
    private Long CreateTime;
    private Long ExpireTime;
}
