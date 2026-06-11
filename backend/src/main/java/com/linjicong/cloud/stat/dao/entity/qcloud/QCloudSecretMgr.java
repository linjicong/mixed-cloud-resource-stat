package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_secretmgr")
public class QCloudSecretMgr extends BasicEntity {
    private String SecretName;

    private String Description;

    private String Status;

    private String CreateTime;

    private String KmsKeyId;

}
