package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_ioa")
public class QCloudIOA extends BasicEntity {
    private String DeviceId;

    private String DeviceName;

    private String UserName;

    private Long Status;

    private String CreateTime;

}
