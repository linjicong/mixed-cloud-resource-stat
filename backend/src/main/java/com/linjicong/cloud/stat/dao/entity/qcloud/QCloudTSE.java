package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_tse")
public class QCloudTSE extends BasicEntity {
    private String InstanceId;

    private String InstanceName;

    private String Type;

    private Long Status;

    private String Region;

    private String VpcId;

    private String SubnetId;

    private String CreateTime;

    private String InternetAddress;

    private String InternalAddress;

}
