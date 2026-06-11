package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_dns_private")
public class QCloudDNSPrivate extends BasicEntity {
    private String ZoneId;

    private String Domain;

    private Long RecordCount;

    private String Status;

    private String Remark;

    private String CreateTime;

    private String Tags;

}
