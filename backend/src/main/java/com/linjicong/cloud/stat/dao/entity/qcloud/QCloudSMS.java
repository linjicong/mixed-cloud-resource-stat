package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_sms")
public class QCloudSMS extends BasicEntity {
    private String SignName;

    private Long SignId;

    private Long Status;

    private Long International;

    private String CreateTime;

    private String ReviewReply;

}
