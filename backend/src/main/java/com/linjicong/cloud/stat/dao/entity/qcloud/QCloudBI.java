package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_bi")
public class QCloudBI extends BasicEntity {
    private String PageId;

    private String PageName;

    private String ProjectId;

    private Long Status;

    private String CreateTime;

}
