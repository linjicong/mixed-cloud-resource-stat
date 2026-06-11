package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_vodmedia")
public class QCloudVODMedia extends BasicEntity {
    private String FileId;

    private String Name;

    private String Type;

    private Long Duration;

    private Long Size;

    private String CreateTime;

    private String Status;

}
