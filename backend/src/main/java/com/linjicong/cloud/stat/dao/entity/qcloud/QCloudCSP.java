package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_csp")
public class QCloudCSP extends BasicEntity {
    private Long AppId;

    private String Name;

    private Long Status;

    private String Description;

    private String CreateTime;

    private String UpdateTime;

}
