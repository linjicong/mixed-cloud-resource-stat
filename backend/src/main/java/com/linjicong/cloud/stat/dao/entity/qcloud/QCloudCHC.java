package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_chc")
public class QCloudCHC extends BasicEntity {
    private String InstanceId;

    private String DeviceClass;

    private String Zone;

    private String Status;

    private String VpcId;

    private String SubnetId;

    private String CreateTime;

    private String SerialNumber;

}
