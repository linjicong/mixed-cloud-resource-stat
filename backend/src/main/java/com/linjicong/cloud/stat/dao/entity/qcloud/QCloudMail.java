package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_mail")
public class QCloudMail extends BasicEntity {
    private String EmailAddress;

    private String EmailIdentity;

    private Long Status;

    private String CreateTime;

}
