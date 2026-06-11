package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_healthdash")
public class QCloudHealthDash extends BasicEntity {
    private String DashboardId;

    private String DashboardName;

    private Long Status;

    private String CreateTime;

}
