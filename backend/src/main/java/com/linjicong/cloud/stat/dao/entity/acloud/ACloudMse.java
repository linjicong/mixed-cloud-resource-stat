package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_mse")
public class ACloudMse extends BasicEntity {
    private String InstanceId;
    private String ClusterName;
    private String ClusterAliasName;
    private String RegionId;
    private String ClusterType;
    private String ClusterVersion;
    private String Status;
    private String PayInfo;
    private Long CreateTime;
}
