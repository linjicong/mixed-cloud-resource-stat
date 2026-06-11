package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_ghphone")
public class QCloudGHPhone extends BasicEntity {
    private String InstanceId;

    private String InstanceName;

    private String Status;

    private String Zone;

    private String Region;

    private Long AppId;

    private String CreateTime;

    private String ExpiredTime;

    private String AndroidVersion;

}
