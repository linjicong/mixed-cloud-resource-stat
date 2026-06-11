package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_cacert")
public class QCloudCACert extends BasicEntity {
    private String CertId;

    private String CertName;

    private Long Status;

    private String CreateTime;

}
