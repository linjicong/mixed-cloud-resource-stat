package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_cloudbase")
public class QCloudCloudBase extends BasicEntity {
    private String EnvId;

    private String EnvName;

    private Long Status;

    private String Source;

    private String CreateTime;

    private String Region;

}
