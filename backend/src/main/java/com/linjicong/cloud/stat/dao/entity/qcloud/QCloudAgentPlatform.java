package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_agentplatform")
public class QCloudAgentPlatform extends BasicEntity {
    private String AgentId;

    private String AgentName;

    private Long Status;

    private String ModelName;

    private String CreateTime;

}
