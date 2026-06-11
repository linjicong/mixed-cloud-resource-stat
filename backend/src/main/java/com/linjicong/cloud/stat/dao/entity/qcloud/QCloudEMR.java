package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_emr")
public class QCloudEMR extends BasicEntity {
    private String ClusterName;

    private String ClusterId;

    private Long Status;

    private String Region;

    private String Zone;

    private String VpcId;

    private String SubnetId;

    private String MasterIp;

    private Long SlaveNodeNum;

    private String CreateTime;

    private String ExpiredTime;

    private String SoftwareVersion;

}
