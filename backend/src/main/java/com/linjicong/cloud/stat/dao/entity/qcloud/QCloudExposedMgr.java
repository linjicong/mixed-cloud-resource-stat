package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_exposedmgr")
public class QCloudExposedMgr extends BasicEntity {
    private String AssetId;

    private String AssetType;

    private Long RiskLevel;

    private String CreateTime;

}
