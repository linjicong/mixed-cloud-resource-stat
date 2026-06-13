package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_oss")
public class ACloudOss extends BasicEntity {
    private String Name;
    private String Location;
    private String CreationDate;
    private String StorageClass;
    private String ExtranetEndpoint;
    private String IntranetEndpoint;
    private String RegionId;
}
