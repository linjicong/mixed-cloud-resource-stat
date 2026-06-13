package com.linjicong.cloud.stat.dao.entity.hcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.OffsetDateTime;

/**
 * 华为云-SFS Turbo（高性能文件存储）
 * 共享 SFS 资源模型，过滤 turbo 类型
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Data
@Table(name = "h_cloud_sfs_turbo")
@Entity
public class HCloudSfsTurbo extends BasicEntity {

    private String version;
    private String availCapacity;
    private String availabilityZone;
    private String azName;
    private OffsetDateTime createdAt;
    private String cryptKeyId;
    private String expandType;
    private String exportLocation;
    private String id;
    private String name;
    private String payModel;
    private String region;
    private String securityGroupId;
    private String shareProto;
    private String shareType;
    private String size;
    private String status;
    private String subStatus;
    private String subnetId;
    private String vpcId;
}
