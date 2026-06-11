package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_cls")
public class QCloudCLS extends BasicEntity {
    private String TopicName;

    private String TopicId;

    private String LogsetName;

    private String LogsetId;

    private String CreateTime;

    private Long Status;

    private Long AutoSplit;

    private Long MaxSplitPartitions;

    private Long PartitionCount;

    private Long Period;

    private String Describe;

}