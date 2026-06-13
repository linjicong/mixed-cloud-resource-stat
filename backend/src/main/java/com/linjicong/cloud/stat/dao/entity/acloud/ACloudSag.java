package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_sag")
public class ACloudSag extends BasicEntity {
    private String SmartAGId;
    private String Name;
    private String RegionId;
    private String CcnId;
    private String Status;
    private Integer MaxBandwidth;
    private String SerialNumber;
    private Long CreateTime;
    private Long EndTime;
    private String SecurityLock;
}
