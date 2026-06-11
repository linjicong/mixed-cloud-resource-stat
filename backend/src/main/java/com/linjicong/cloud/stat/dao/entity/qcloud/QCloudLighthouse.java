package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_lighthouse")
public class QCloudLighthouse extends BasicEntity {
    private String InstanceName;

    private String InstanceId;

    private String BundleId;

    private String Zone;

    private String Region;

    private String Status;

    private String CreatedTime;

    private String ExpiredTime;

    private String RenewFlag;

    private Long DiskSize;

    private Long Memory;

}
