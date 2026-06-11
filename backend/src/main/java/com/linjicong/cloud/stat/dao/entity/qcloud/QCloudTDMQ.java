package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_tdmq")
public class QCloudTDMQ extends BasicEntity {
    private String ClusterName;

    private String ClusterId;

    private Long Status;

    private String CreateTime;

    private String Remark;

    private String Version;

    private String PublicEndPoint;

    private String InternalEndPoint;

}
