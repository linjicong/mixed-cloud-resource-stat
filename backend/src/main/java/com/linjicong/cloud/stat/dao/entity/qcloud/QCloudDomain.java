package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_domain")
public class QCloudDomain extends BasicEntity {
    private String DomainName;

    private String DomainId;

    private String CreatedOn;

    private String ExpiresOn;

    private String Status;

    private Long AutoRenew;

    private String DnsStatus;

    private String Owner;

}