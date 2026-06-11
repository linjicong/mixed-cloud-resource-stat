package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_apigw")
public class QCloudAPIGW extends BasicEntity {
    private String ServiceName;

    private String ServiceId;

    private String Protocol;

    private String NetTypes;

    private String InnerSubDomain;

    private String OuterSubDomain;

    private String CreateTime;

    private String Region;

    private String InstanceId;

}
