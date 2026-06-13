package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_fc")
public class ACloudFc extends BasicEntity {
    private String FunctionName;
    private String Description;
    private String RegionId;
    private String Runtime;
    private String Handler;
    private Long Timeout;
    private Long MemorySize;
    private String CreatedTime;
    private String LastModifiedTime;
}
