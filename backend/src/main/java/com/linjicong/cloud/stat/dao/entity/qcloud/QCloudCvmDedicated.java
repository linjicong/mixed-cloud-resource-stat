package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 腾讯云-专用宿主机
 * 共享 CVM 资源模型
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Data
@Table(name = "q_cloud_cvm_dedicated")
@Entity
public class QCloudCvmDedicated extends BasicEntity {

    private String instanceId;
    private String instanceName;
    private String instanceType;
    private String cpu;
    private String memory;
    private String status;
    private String zone;
    private String createdTime;
    private String expiredTime;
    private String imageId;
    private String osName;
    private String privateIpAddresses;
    private String publicIpAddresses;
    private String vpcId;
    private String subnetId;
}
