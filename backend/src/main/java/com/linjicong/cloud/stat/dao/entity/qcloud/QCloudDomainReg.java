package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_domain_reg")
public class QCloudDomainReg extends BasicEntity {
    private String DomainName;

    private String CreationDate;

    private String ExpirationDate;

    private String StatusCode;

    private Long IsPremium;

    private String DomainId;

}
