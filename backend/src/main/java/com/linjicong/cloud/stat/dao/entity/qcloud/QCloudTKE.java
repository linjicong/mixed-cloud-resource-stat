package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_tke")
public class QCloudTKE extends BasicEntity {
    private String ClusterName;

    private String ClusterId;

    private String ClusterDesc;

    private String ClusterVersion;

    private String ClusterOs;

    private String ClusterType;

    private String ClusterNetworkSettings;

    private Long ClusterNodeNum;

    private Long ProjectId;

    private String Status;

    private Long ClusterMaterNodeNum;

    private String InstanceAdvancedSettings;

    private String Tags;

}