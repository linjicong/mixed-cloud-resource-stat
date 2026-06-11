package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_live2")
public class QCloudLive2 extends BasicEntity {
    private String DomainName;

    private Long DomainType;

    private Long Status;

    private String CreateTime;

}
