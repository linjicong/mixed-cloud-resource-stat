package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_hbase")
public class QCloudHBase extends BasicEntity {
    private String ClusterId;

    private String ClusterName;

    private Long Status;

    private String Region;

    private String Zone;

    private String VpcId;

    private String SubnetId;

    private String InstanceId;

    private Long MasterDiskSize;

    private Long CoreDiskSize;

    private String CreateTime;

}
