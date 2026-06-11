package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_tcb")
public class QCloudTCB extends BasicEntity {
    private String EnvId;

    private String PackageName;

    private Long Source;

    private String CreateTime;

    private String UpdateTime;

    private Long Status;

    private String CustomDomain;

    private String Region;

}
