package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_vodprocess")
public class QCloudVODProcess extends BasicEntity {
    private String FileId;

    private String FileName;

    private String MediaProcessType;

    private Long Status;

    private String CreateTime;

}
