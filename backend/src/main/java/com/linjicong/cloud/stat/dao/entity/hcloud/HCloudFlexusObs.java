package com.linjicong.cloud.stat.dao.entity.hcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

/**
 * 华为云-Flexus 对象存储
 * 共享 OBS 资源模型
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Data
@Table(name = "h_cloud_flexus_obs")
@Entity
public class HCloudFlexusObs extends BasicEntity {

    private String bucketName;
    private Date creationDate;
    private String location;
    private String ownerId;
    private String ownerName;
    private String storageClass;
    private String enterpriseProjectId;
}
