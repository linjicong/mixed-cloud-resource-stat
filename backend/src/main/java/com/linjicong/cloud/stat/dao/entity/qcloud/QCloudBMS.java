package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_bms")
public class QCloudBMS extends BasicEntity {
    private String InstanceName;

    private String InstanceId;

    private String TypeId;

    private String VpcId;

    private String SubnetId;

    private String Status;

    private String Zone;

    private String PrivateIpAddresses;

    private String CreateTime;

    private String ExpiredTime;

}
