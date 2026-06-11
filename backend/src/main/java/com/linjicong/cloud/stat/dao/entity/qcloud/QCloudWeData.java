package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_wedata")
public class QCloudWeData extends BasicEntity {
    private String ProjectId;

    private String ProjectName;

    private String Description;

    private Long Status;

    private String CreateTime;

    private String Creator;

}
