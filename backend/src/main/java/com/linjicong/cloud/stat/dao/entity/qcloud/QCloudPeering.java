package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 腾讯云-对等连接
 * 共享 VPC 资源模型
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Data
@Table(name = "q_cloud_peering")
@Entity
public class QCloudPeering extends BasicEntity {

    private String peeringConnectionId;
    private String peeringConnectionName;
    private String status;
    private String vpcId;
    private String peerVpcId;
    private String peerUin;
    private String region;
    private String createTime;
}
