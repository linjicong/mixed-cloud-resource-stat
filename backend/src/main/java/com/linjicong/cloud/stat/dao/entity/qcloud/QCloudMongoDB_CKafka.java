package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_cdc")
public class QCloudMongoDB_CKafka extends BasicEntity {
    private String DedicatedClusterId;

    private String DedicatedClusterName;

    private String InstanceId;

    private String Zone;

    private String Status;

    private String CreateTime;

    private String Type;

}
