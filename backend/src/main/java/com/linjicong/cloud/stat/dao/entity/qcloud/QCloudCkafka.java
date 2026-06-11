package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_ckafka")
public class QCloudCkafka extends BasicEntity {
    private String InstanceName;

    private Long Status;

    private String VpcId;

    private String SubnetId;

    private String Vip;

    private Long Vport;

    private Long Bandwidth;

    private Long DiskSize;

    private String ZoneId;

    private String Version;

    private String CreateTime;

    private String ExpireTime;

    private String Region;

}