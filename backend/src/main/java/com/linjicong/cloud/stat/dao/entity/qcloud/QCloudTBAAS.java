package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_tbaas")
public class QCloudTBAAS extends BasicEntity {
    private String ClusterId;

    private String ClusterName;

    private Long ChainType;

    private Long Status;

    private String NetworkType;

    private String CreateTime;

}
