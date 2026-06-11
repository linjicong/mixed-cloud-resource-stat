package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_tcaplusdb")
public class QCloudTcaplusDB extends BasicEntity {
    private String InstanceId;

    private String InstanceName;

    private Long AppId;

    private String Region;

    private String Zone;

    private String VpcId;

    private String SubnetId;

    private Long Status;

    private String CreateTime;

    private String ExpireTime;

    private String TableType;

}
