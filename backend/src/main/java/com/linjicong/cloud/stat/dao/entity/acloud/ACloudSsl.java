package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_ssl")
public class ACloudSsl extends BasicEntity {
    private String CertId;
    private String CertName;
    private String Domain;
    private String Issuer;
    private String StartDate;
    private String EndDate;
    private String Status;
    private String SourceType;
}
