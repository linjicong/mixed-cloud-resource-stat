package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_faceswap")
public class QCloudFaceSwap extends BasicEntity {
    private Long ProjectId;

    private String TemplateName;

    private Long Status;

    private Long UsedCount;

}
