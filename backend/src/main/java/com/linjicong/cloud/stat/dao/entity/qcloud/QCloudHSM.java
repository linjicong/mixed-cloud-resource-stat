package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_hsm")
public class QCloudHSM extends BasicEntity {
    private String HsmId;

    private String HsmName;

    private String Type;

    private String VpcId;

    private String SubnetId;

    private String Status;

    private String Zone;

    private String ExpireTime;

    private String Vip;

}
