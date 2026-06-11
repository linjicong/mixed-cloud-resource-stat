package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_healthreport2")
public class QCloudHealthReport2 extends BasicEntity {
    private String ReportId;

    private String ReportType;

    private Long Status;

    private String CreateTime;

}
