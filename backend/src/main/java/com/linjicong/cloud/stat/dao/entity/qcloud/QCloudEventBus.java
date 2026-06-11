package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_eventbus")
public class QCloudEventBus extends BasicEntity {
    private String EventBusId;

    private String EventBusName;

    private Long Status;

    private String CreateTime;

    private String Type;

}
