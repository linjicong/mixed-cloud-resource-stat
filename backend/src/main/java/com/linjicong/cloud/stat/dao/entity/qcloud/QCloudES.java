package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_es")
public class QCloudES extends BasicEntity {
    private String InstanceName;

    private String InstanceId;

    private String Region;

    private String Zone;

    private Long AppId;

    private String Uin;

    private String VpcUid;

    private String SubnetUid;

    private String InstanceVersion;

    private String LicenseType;

    private Long MemSize;

    private Long DiskSize;

    private Long NodeNum;

    private String NodeType;

    private Long Status;

    private Long RenewFlag;

    private String ChargeType;

    private String DeadlineTime;

    private String CreateTime;

    private String WebNodeType;

    private String EsPublicAcl;

}