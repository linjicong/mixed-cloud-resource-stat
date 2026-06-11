package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_eo")
public class QCloudEO extends BasicEntity {
    private String ZoneId;

    private String ZoneName;

    private String OriginalNameServers;

    private String Status;

    private String Type;

    private String NameServers;

    private String CreateTime;

    private String UpdateTime;

}
