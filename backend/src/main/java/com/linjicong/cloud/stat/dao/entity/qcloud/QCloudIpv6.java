package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 腾讯云-IPv6
 * 共享 VPC 资源模型
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Data
@Table(name = "q_cloud_ipv6")
@Entity
public class QCloudIpv6 extends BasicEntity {

    private String ipv6Address;
    private String vpcId;
    private String subnetId;
    private String instanceId;
    private String instanceType;
    private String region;
    private String createTime;
}
