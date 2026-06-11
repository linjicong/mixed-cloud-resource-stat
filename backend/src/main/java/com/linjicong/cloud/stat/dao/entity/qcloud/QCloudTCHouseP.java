package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_tchousep")
public class QCloudTCHouseP extends BasicEntity {
    private String InstanceId;

    private String InstanceName;

    private Long Status;

    private String Region;

    private String Zone;

    private String VpcId;

    private String SubnetId;

    private String CreateTime;

    private String ExpireTime;

    private Long RegionId;

}
