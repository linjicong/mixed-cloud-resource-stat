package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_tsf")
public class QCloudTSF extends BasicEntity {
    private String ApplicationId;

    private String ApplicationName;

    private String ApplicationType;

    private String MicroserviceType;

    private String CreateTime;

    private String UpdateTime;

    private String ApplicationRuntimeType;

}
