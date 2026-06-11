package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_vulnmgr")
public class QCloudVulnMgr extends BasicEntity {
    private String VulnId;

    private String VulnName;

    private Long Severity;

    private Long Status;

    private String CreateTime;

}
