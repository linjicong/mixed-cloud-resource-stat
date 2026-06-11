package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_safeguard")
public class QCloudSafeGuard extends BasicEntity {
    private String ServiceId;

    private String ServiceName;

    private Long Status;

    private String CreateTime;

}
