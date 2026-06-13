package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_cloud_firewall")
public class ACloudCloudFirewall extends BasicEntity {
    private String InstanceId;
    private String RegionId;
    private String Name;
    private String Type;
    private String Status;
    private String IpAddress;
    private String ResourceType;
}
