package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_ocr")
public class QCloudOCR extends BasicEntity {
    private String Type;

    private Long UsedCount;

    private Long TotalCount;

    private String CreateTime;

}
