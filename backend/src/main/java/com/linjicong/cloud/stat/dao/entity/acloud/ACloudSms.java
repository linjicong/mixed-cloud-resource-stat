package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_sms")
public class ACloudSms extends BasicEntity {
    private String SignName;
    private String AuditStatus;
    private String CreateDate;
}
