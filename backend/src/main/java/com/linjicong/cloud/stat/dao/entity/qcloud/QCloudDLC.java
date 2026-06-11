package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_dlc")
public class QCloudDLC extends BasicEntity {
    private String DatabaseName;

    private String Description;

    private String Location;

    private String CreateTime;

    private String ModifiedTime;

    private String AssociatedRole;

}
