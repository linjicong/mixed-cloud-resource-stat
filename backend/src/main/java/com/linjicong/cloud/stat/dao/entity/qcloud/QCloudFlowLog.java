package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 腾讯云-流日志
 * 共享 VPC 资源模型
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Data
@Table(name = "q_cloud_flow_log")
@Entity
public class QCloudFlowLog extends BasicEntity {

    private String flowLogId;
    private String flowLogName;
    private String status;
    private String vpcId;
    private String subnetId;
    private String region;
    private String createTime;
    private String updateTime;
}
