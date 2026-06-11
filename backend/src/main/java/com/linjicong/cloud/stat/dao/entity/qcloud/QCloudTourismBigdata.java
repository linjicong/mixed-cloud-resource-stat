package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_tourism")
public class QCloudTourismBigdata extends BasicEntity {
    private String SceneId;

    private String SceneName;

    private Long Status;

    private String CreateTime;

}
