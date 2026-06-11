package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_ti")
public class QCloudTI extends BasicEntity {
    private String ModelId;

    private String ModelName;

    private String Framework;

    private Long Status;

    private String CreateTime;

}
