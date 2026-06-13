package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_dsc")
public class ACloudDsc extends BasicEntity {
    private String InstanceId;
    private String RegionId;
    private String BackupPlanName;
    private String BackupMethod;
    private String BackupObjects;
    private String Status;
    private String CreateTime;
    private String PayType;
}
