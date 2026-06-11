package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_tcr_ent")
public class QCloudTcrEnt extends BasicEntity {
    private String RegistryId;

    private String RegistryName;

    private String RegistryType;

    private String Status;

    private String RegionName;

    private String PublicDomain;

    private String CreatedAt;

}
