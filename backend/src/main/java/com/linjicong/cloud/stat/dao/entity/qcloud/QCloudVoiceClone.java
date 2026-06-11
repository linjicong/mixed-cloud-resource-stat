package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_voiceclone")
public class QCloudVoiceClone extends BasicEntity {
    private String VoiceId;

    private String VoiceName;

    private Long Status;

    private String CreateTime;

}
