package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_cspgateway")
public class QCloudCSPGateway extends BasicEntity {
    private String GatewayId;

    private String GatewayName;

    private Long Status;

    private String VpcId;

    private String CreateTime;

}
