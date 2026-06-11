package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_mathgrade")
public class QCloudMathGrade extends BasicEntity {
    private String TaskId;

    private String TaskName;

    private Long Status;

    private Long UsedCount;

    private String CreateTime;

}
