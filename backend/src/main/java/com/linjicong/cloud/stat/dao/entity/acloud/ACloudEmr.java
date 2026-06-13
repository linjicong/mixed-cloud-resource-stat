package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_emr")
public class ACloudEmr extends BasicEntity {
    private String Id;
    private String Name;
    private String RegionId;
    private String Status;
    private String ChargeType;
    private String CreateTime;
    private String ClusterType;
    private String SoftwareInfo;
}
