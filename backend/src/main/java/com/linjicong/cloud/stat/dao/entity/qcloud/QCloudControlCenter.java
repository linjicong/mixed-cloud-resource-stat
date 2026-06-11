package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_controlcenter")
public class QCloudControlCenter extends BasicEntity {
    private Long AppId;

    private String Name;

    private Long Status;

    private String CreateTime;

}
