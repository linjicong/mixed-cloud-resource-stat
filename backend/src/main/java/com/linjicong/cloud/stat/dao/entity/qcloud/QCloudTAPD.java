package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_tapd")
public class QCloudTAPD extends BasicEntity {
    private String ProjectId;

    private String ProjectName;

    private String Owner;

    private String CreateTime;

    private Long Status;

}
