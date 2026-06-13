package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 腾讯云-威胁情报
 * 共享安全资源模型
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Data
@Table(name = "q_cloud_threat_intel")
@Entity
public class QCloudThreatIntel extends BasicEntity {

    private String indicatorId;
    private String indicatorType;
    private String threatType;
    private String severity;
    private String confidence;
    private String source;
    private String createTime;
    private String updateTime;
}
