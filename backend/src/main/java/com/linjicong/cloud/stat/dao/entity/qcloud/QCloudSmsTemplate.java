package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_sms_template")
public class QCloudSmsTemplate extends BasicEntity {
    private String TemplateName;

    private Long TemplateId;

    private Long Status;

    private String ReviewReply;

    private Long International;

    private String CreateTime;

}
