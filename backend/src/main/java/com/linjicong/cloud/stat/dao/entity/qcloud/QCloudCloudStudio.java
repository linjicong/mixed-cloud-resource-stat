package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_cloudstudio")
public class QCloudCloudStudio extends BasicEntity {
    private String SpaceId;

    private String SpaceName;

    private Long Status;

    private String CreateTime;

    private String Region;

}
