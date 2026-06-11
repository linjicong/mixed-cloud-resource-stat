package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_ssl")
public class QCloudSSL extends BasicEntity {
    private String Alias;

    private String SubjectAltName;

    private String Status;

    private String TypeName;

    private Long ProjectId;

    private String From;

    private String CertificateBeginTime;

    private String CertificateEndTime;

    private Long InsertTime;

    private String Issuer;

    private String Domain;

    private Long DomainCount;

}