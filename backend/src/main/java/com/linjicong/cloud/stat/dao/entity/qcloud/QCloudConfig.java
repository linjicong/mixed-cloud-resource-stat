package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_config")
public class QCloudConfig extends BasicEntity {
    private String ResourceId;

    private String ResourceName;

    private String ResourceType;

    private String Region;

    private String AvailabilityZone;

    private String ConfigurationState;

    private String CreateTime;

}
