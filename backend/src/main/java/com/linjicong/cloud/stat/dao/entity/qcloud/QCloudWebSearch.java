package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_websearch")
public class QCloudWebSearch extends BasicEntity {
    private String Query;

    private Long ResultCount;

    private Long UsedCount;

}
