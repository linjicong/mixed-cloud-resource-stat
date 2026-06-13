package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_hss")
public class ACloudHss extends BasicEntity {
    private String Uuid;
    private String InstanceId;
    private String InstanceName;
    private String RegionId;
    private String Ip;
    private String OsType;
    private String OsName;
    private String Status;
    private String AuthVersion;
    private String CreateTime;
}
