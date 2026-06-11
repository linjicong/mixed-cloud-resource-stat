package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_seccredential")
public class QCloudSecCredential extends BasicEntity {
    private String SecretId;

    private Long Status;

    private String CreateTime;

    private String LastUsedTime;

}
