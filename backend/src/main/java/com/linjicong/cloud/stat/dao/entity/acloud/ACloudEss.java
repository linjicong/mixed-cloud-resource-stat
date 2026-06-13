package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_ess")
public class ACloudEss extends BasicEntity {
    private String ScalingGroupId;
    private String ScalingGroupName;
    private String RegionId;
    private String ActiveCapacity;
    private String MinSize;
    private String MaxSize;
    private String CreationTime;
    private String Status;
    private String LifecycleState;
    private String ResourceGroupId;
}
