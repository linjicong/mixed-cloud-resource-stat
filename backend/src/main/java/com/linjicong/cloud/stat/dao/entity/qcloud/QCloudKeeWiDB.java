package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_keewidb")
public class QCloudKeeWiDB extends BasicEntity {
    private String InstanceName;

    private String InstanceId;

    private Long ProjectId;

    private String Region;

    private String Zone;

    private String VpcId;

    private String SubnetId;

    private String Vip;

    private Long Vport;

    private Long MemSize;

    private Long Status;

    private String DeadlineTime;

    private String CreateTime;

    private String Type;

}
