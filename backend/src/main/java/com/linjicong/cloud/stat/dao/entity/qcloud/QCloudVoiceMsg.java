package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_voicemsg")
public class QCloudVoiceMsg extends BasicEntity {
    private Long SdkAppId;

    private Long Status;

    private String CreateTime;

}
