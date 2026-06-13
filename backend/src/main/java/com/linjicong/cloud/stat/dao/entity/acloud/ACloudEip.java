package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_eip")
public class ACloudEip extends BasicEntity {
    private String AllocationId;
    private String Name;
    private String RegionId;
    private String IpAddress;
    private String Status;
    private String InstanceId;
    private String InstanceType;
    private Integer Bandwidth;
    private String CreationTime;
    private String ChargeType;
    private String ResourceGroupId;
}
