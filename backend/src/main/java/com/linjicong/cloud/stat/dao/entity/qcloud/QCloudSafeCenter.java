package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_safecenter")
public class QCloudSafeCenter extends BasicEntity {
    private String AssetType;

    private String AssetId;

    private Long RiskLevel;

    private Long Status;

    private String CreateTime;

}
