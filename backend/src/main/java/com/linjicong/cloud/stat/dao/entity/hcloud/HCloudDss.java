package com.linjicong.cloud.stat.dao.entity.hcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 华为云-专属分布式存储 DSS
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Data
@Table(name = "h_cloud_dss")
@Entity
public class HCloudDss extends BasicEntity {

    private String id;
    private String name;
    private String status;
    private String size;
    private String volumeType;
    private String availabilityZone;
    private String createdAt;
    private String updatedAt;
    private String description;
    private String attachment;
    private String bootable;
    private String encrypted;
    private String kmsKeyId;
    private String throughput;
    private String iops;
    private String enterpriseProjectId;
}
