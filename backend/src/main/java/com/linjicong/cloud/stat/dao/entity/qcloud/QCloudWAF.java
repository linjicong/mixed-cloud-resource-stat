package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_waf")
public class QCloudWAF extends BasicEntity {
    private String Domain;

    private String InstanceName;

    private String InstanceID;

    private Long FlowMode;

    private Long Status;

    private String Edition;

    private String CreateTime;

    private String Engine;

    private Long IsCdn;

}