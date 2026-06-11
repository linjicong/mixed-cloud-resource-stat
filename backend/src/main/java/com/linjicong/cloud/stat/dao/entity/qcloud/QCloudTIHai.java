package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_tihai")
public class QCloudTIHai extends BasicEntity {
    private String InstanceId;

    private String InstanceName;

    private String Status;

    private String GPUType;

    private String CreateTime;

}
