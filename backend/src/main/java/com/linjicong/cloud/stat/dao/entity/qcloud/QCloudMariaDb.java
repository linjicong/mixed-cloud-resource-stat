package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_mariadb")
public class QCloudMariaDb extends BasicEntity {
    private String InstanceName;

    private Long ProjectId;

    private String Region;

    private String Zone;

    private Long Memory;

    private Long Storage;

    private String VpcId;

    private String SubnetId;

    private Long Status;

    private String Vip;

    private Long Vport;

    private String PayMode;

    private String CreateTime;

    private String DeadlineTime;

    private Long AutoRenewFlag;

    private String DbVersion;

    private Long Cpu;

}