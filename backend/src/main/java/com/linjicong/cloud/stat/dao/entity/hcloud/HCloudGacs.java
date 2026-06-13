package com.linjicong.cloud.stat.dao.entity.hcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 华为云-GPU 加速云服务器 GACS
 * 共享 ECS 资源模型
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Data
@Table(name = "h_cloud_gacs")
@Entity
public class HCloudGacs extends BasicEntity {

    private String id;
    private String name;
    private String status;
    private String updated;
    private String created;
    private String flavorId;
    private String imageId;
    private String availabilityZone;
    private String tenantId;
    private String userId;
    private String enterpriseProjectId;
    private String description;
    private String hostId;
    private String keyName;
    private String accessIPv4;
    private String accessIPv6;
    private Integer progress;
    private Integer powerState;
    private Boolean locked;
}
