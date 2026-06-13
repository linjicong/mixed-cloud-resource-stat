package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_max_compute")
public class ACloudMaxCompute extends BasicEntity {
    private String Name;
    private String DisplayName;
    private String RegionId;
    private String Status;
    private String Owner;
    private String CreatedTime;
    private String Comment;
    private String DefaultQuota;
}
