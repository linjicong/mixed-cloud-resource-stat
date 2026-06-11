package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_nativebuild")
public class QCloudNativeBuild extends BasicEntity {
    private String BuildId;

    private String BuildName;

    private Long Status;

    private String CreateTime;

}
