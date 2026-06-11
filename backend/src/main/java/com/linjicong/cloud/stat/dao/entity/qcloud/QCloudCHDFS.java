package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_chdfs")
public class QCloudCHDFS extends BasicEntity {
    private String FileSystemName;

    private String FileSystemId;

    private String Region;

    private String CreateTime;

    private Long CapacityQuota;

    private Long Status;

}
