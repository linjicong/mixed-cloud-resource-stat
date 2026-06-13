package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_ddos")
public class ACloudDdos extends BasicEntity {
    private String InstanceId;
    private String InstanceName;
    private String InstanceType;
    private String RegionId;
    private String Status;
    private String ExpireTime;
    private String CreateTime;
    private String IpType;
    private String Bandwidth;
}
