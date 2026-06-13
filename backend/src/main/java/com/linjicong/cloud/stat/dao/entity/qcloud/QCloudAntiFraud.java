package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 腾讯云-流量反欺诈
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Data
@Table(name = "q_cloud_anti_fraud")
@Entity
public class QCloudAntiFraud extends BasicEntity {

    private String taskId;
    private String taskName;
    private String status;
    private String region;
    private String createTime;
    private String updateTime;
}
