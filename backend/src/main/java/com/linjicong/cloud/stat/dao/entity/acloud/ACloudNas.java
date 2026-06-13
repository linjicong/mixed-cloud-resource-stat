package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_nas")
public class ACloudNas extends BasicEntity {
    private String FileSystemId;
    private String Description;
    private String RegionId;
    private String ZoneId;
    private String StorageType;
    private Long Capacity;
    private String EncryptType;
    private String MountTargetCount;
    private String CreateTime;
    private String ResourceGroupId;
}
