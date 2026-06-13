package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_action_trail")
public class ACloudActionTrail extends BasicEntity {
    private String TrailName;
    private String RegionId;
    private String HomeRegion;
    private String SlsProjectArn;
    private String SlsWriteRoleArn;
    private String OssBucketName;
    private String Status;
    private String CreateTime;
}
