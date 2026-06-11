package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_gameserver")
public class QCloudGameServer extends BasicEntity {
    private String FleetId;

    private String FleetName;

    private String Status;

    private String InstanceType;

    private Long MaxSize;

    private Long MinSize;

    private String CreateTime;

}
