package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_gamevoice")
public class QCloudGameVoice extends BasicEntity {
    private String AppId;

    private String AppName;

    private Long Status;

    private String CreateTime;

}
