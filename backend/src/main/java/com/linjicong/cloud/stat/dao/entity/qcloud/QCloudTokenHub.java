package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_tokenhub")
public class QCloudTokenHub extends BasicEntity {
    private String ModelName;

    private Long UsedTokens;

    private Long TotalTokens;

    private String CreateTime;

}
