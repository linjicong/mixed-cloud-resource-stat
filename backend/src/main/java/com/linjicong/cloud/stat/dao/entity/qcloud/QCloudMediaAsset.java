package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_mediaasset")
public class QCloudMediaAsset extends BasicEntity {
    private String Bucket;

    private String MediaType;

    private Long FileNum;

    private Long TotalSize;

    private String CreateTime;

}
