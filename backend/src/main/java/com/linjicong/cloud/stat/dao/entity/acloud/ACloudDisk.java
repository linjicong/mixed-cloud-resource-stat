package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_disk")
public class ACloudDisk extends BasicEntity {
    private String DiskId;
    private String DiskName;
    private String RegionId;
    private String ZoneId;
    private String Status;
    private String Type;
    private Integer Size;
    private String Category;
    private String InstanceId;
    private String CreationTime;
    private String ExpiredTime;
    private String ChargeType;
    private String ResourceGroupId;
}
