package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 腾讯云-高性能计算集群
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Data
@Table(name = "q_cloud_hpc_cluster")
@Entity
public class QCloudHpcCluster extends BasicEntity {

    private String clusterId;
    private String clusterName;
    private String status;
    private String zone;
    private String createTime;
    private String updateTime;
    private String description;
}
