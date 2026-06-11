package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "q_cloud_clb_gw")
public class QCloudCLB_gw extends BasicEntity {
    private String LoadBalancerId;

    private String LoadBalancerName;

    private Long Status;

    private String VpcId;

    private String SubnetId;

    private String CreateTime;

    private String LoadBalancerType;

    private String AddressIPVersion;

}
