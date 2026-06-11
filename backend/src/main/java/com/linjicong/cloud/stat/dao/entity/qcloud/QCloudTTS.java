package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_tts")
public class QCloudTTS extends BasicEntity {
    private String EngineType;

    private Long UsedChars;

    private Long TotalChars;

    private String CreateTime;

}
