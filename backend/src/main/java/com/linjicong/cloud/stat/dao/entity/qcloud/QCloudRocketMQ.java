package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_rocketmq")
public class QCloudRocketMQ extends BasicEntity {
    private String InstanceName;

    private Long InstanceType;

    private Long Status;

    private Long TopicNum;

    private Long GroupNum;

    private Long MaxTopicNum;

    private Long MaxGroupNum;

    private Long MaxTps;

    private String CreateTime;

    private String ExpireTime;

    private String Region;

}