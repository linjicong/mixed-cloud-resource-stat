package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_captcha")
public class QCloudCAPTCHA extends BasicEntity {
    private Long CaptchaAppId;

    private String CaptchaName;

    private Long SceneType;

    private Long Status;

    private String CreateTime;

}
