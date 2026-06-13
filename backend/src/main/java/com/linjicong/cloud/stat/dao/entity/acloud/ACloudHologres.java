package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_hologres")
public class ACloudHologres extends BasicEntity {
    private String InstanceId;
    private String InstanceName;
    private String RegionId;
    private String ZoneId;
    private String Status;
    private String PayType;
    private String CreateTime;
    private String ExpireTime;
    private String ResourceGroupId;
    private String InstanceType;
    private String StorageType;
}
