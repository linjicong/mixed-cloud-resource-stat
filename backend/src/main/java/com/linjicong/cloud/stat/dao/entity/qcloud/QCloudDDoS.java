package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_ddos")
public class QCloudDDoS extends BasicEntity {
    private String InstanceId;

    private String Name;

    private String Level;

    private String Status;

    private String BoundIPList;

    private String CreateTime;

    private String ExpireTime;

    private String Region;

}
