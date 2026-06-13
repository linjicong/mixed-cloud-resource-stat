package com.linjicong.cloud.stat.dao.entity.acloud;
import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "a_cloud_waf")
public class ACloudWaf extends BasicEntity {
    private String InstanceId;
    private String RegionId;
    private String Status;
    private String PayType;
    private String ExpireTime;
    private Integer InDebt;
}
