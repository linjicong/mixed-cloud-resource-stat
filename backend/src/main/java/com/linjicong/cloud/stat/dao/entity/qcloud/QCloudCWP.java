package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_cwp")
public class QCloudCWP extends BasicEntity {
    private String MachineId;

    private String MachineName;

    private String MachineOs;

    private String MachineStatus;

    private String Uuid;

    private String Quuid;

    private String PrivateIp;

    private String PublicIp;

    private Long ProjectId;

    private String Region;

}
