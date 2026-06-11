package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_cloudhsm")
public class QCloudCloudHSM extends BasicEntity {
    private String HsmId;

    private String HsmName;

    private Long Status;

    private String Zone;

    private String CreateTime;

}
