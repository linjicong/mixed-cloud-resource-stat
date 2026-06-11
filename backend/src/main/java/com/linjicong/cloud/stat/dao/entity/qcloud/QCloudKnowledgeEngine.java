package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_knowledgeengine")
public class QCloudKnowledgeEngine extends BasicEntity {
    private String KnowledgeBaseId;

    private String KnowledgeBaseName;

    private Long DocCount;

    private Long Status;

    private String CreateTime;

}
