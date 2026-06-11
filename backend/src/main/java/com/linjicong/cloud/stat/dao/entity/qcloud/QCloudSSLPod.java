package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_sslpod")
public class QCloudSSLPod extends BasicEntity {
    private String Domain;

    private String CertId;

    private Long Status;

    private String ExpireTime;

    private String Issuer;

    private String CreateTime;

}
