package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_contentsafe")
public class QCloudContentSafe extends BasicEntity {
    private String BizType;

    private String DataContentType;

    private Long UsedCount;

    private Long TotalCount;

    private String CreateTime;

}
