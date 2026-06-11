package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_cmq")
public class QCloudCmq extends BasicEntity {
    private String QueueId;

    private String QueueName;

    private Long Qps;

    private Long Bandwidth;

    private Long MaxMsgHeapNum;

    private Long MsgRetentionSeconds;

    private String CreateTime;

    private String LastModifyTime;

    private Long ActiveMsgNum;

    private Long InactiveMsgNum;

}
