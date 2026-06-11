package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_dc")
public class QCloudDC extends BasicEntity {
    private String DirectConnectName;

    private String DirectConnectId;

    private String AccessPointId;

    private String State;

    private String Location;

    private Long Bandwidth;

    private String CreateTime;

    private String OperatorName;

    private String PortType;

}
