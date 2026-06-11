package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_docs")
public class QCloudDocs extends BasicEntity {
    private String DocId;

    private String DocName;

    private String DocType;

    private String OwnerId;

    private String CreateTime;

    private String UpdateTime;

}
