package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_tcr")
public class QCloudTCR extends BasicEntity {
    private String RegistryName;

    private String RegistryId;

    private String RegistryType;

    private String Status;

    private String PublicDomain;

    private String RegionName;

    private String CreatedAt;

    private String UpdatedAt;

    private Boolean DeleteFlag;

}