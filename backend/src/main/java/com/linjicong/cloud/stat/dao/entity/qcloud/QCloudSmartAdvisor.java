package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_smartadvisor")
public class QCloudSmartAdvisor extends BasicEntity {
    private String SuggestionId;

    private String SuggestionType;

    private Long Severity;

    private String CreateTime;

}
