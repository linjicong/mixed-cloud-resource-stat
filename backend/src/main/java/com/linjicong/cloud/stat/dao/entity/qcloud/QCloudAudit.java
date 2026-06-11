package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_audit")
public class QCloudAudit extends BasicEntity {
    private String AuditName;

    private String ResourceId;

    private String ResourceName;

    private String ActionType;

    private String EventTime;

    private String EventRegion;

}
