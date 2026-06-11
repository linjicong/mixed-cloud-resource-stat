package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_bastion")
public class QCloudBastion extends BasicEntity {
    private String ResourceId;

    private String ResourceName;

    private Long Status;

    private String CreateTime;

}
