package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_vod")
public class QCloudVOD extends BasicEntity {
    private Long SubAppId;

    private String Name;

    private String Description;

    private String CreateTime;

    private String UpdateTime;

    private String Status;

}
