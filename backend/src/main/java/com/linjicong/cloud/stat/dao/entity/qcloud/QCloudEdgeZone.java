package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 腾讯云-边缘可用区
 * 共享计算资源模型
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Data
@Table(name = "q_cloud_edge_zone")
@Entity
public class QCloudEdgeZone extends BasicEntity {

    private String zoneId;
    private String zoneName;
    private String status;
    private String region;
    private String createTime;
    private String updateTime;
}
