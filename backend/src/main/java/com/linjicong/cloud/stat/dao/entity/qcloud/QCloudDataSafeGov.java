package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_datasafegov")
public class QCloudDataSafeGov extends BasicEntity {
    private String PolicyId;

    private String PolicyName;

    private Long Status;

    private String CreateTime;

}
