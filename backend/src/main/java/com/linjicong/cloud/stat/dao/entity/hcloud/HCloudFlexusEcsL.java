package com.linjicong.cloud.stat.dao.entity.hcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 华为云-Flexus 云服务器 L 实例
 * 共享 ECS 资源模型
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Data
@Table(name = "h_cloud_flexus_ecs_l")
@Entity
public class HCloudFlexusEcsL extends BasicEntity {

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
