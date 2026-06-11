package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_iotdevice")
public class QCloudIoTDevice extends BasicEntity {
    private String ProductId;

    private String DeviceName;

    private String DeviceId;

    private Long Status;

    private String OnlineTime;

    private String CreateTime;

}
