package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_healthomics")
public class QCloudHealthOmics extends BasicEntity {
    private String DatasetId;

    private String DatasetName;

    private Long Status;

    private String CreateTime;

}
