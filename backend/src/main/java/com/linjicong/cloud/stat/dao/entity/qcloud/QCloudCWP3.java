package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_cwp3")
public class QCloudCWP3 extends BasicEntity {
    private String MachineId;

    private String MachineName;

    private String MachineOs;

    private String MachineStatus;

    private String Uuid;

    private String Quuid;

    private String PrivateIp;

    private String Region;

}
