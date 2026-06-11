package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_oceanus")
public class QCloudOceanus extends BasicEntity {
    private String JobName;

    private String JobId;

    private Long JobType;

    private Long Status;

    private String Region;

    private String CreateTime;

    private String UpdateTime;

}
