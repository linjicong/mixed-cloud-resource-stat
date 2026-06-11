package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_css")
public class QCloudCSS extends BasicEntity {
    private String DomainName;

    private Long DomainType;

    private Long Status;

    private String CreateTime;

    private String UpdateTime;

    private Long PlayType;

    private String Type;

}
