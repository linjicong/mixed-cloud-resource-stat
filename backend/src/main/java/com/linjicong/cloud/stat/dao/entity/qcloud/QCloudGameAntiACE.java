package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_gameantiace")
public class QCloudGameAntiACE extends BasicEntity {
    private String GameId;

    private String GameName;

    private Long Status;

    private String CreateTime;

}
