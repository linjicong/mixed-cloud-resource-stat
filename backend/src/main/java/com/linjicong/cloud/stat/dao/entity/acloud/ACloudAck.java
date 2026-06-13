package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_ack")
public class ACloudAck extends BasicEntity {
    private String ClusterId;
    private String Name;
    private String RegionId;
    private String State;
    private String ClusterType;
    private String ClusterSpec;
    private String CurrentVersion;
    private String CreateTime;
    private String ResourceGroupId;
}
