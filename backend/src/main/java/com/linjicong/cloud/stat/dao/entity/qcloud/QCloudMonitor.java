package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_monitor")
public class QCloudMonitor extends BasicEntity {
    private String NamespaceShowName;

    private String NamespaceValue;

    private String CreateDate;

    private String Description;

    private Long IsHidden;

    private String DashboardId;

}