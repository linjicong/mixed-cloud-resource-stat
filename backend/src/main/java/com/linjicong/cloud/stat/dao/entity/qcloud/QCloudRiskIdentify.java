package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_riskidentify")
public class QCloudRiskIdentify extends BasicEntity {
    private Long AppId;

    private String SceneCode;

    private Long Status;

    private String CreateTime;

}
