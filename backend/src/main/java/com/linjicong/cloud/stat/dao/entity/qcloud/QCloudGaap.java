package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_gaap")
public class QCloudGaap extends BasicEntity {
    private String ProxyName;

    private String ProxyId;

    private Long ProjectId;

    private String Region;

    private String AccessRegion;

    private String Protocol;

    private Long Bandwidth;

    private String Status;

    private String CreateTime;

    private Long ConcurrentConnections;

}
