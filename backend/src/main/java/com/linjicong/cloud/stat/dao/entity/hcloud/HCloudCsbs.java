package com.linjicong.cloud.stat.dao.entity.hcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 华为云-云服务器备份 CSBS
 * 共享 CBR 资源模型，过滤 server backup 类型
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Data
@Table(name = "h_cloud_csbs")
@Entity
public class HCloudCsbs extends BasicEntity {

    private String id;
    private String name;
    private String status;
    private String description;
    private String createdAt;
    private String updatedAt;
    private String projectId;
    private String vaultId;
    private String resourceId;
    private String resourceType;
    private String resourceSize;
    private String backupSize;
    private String incrementalSize;
    private String enterpriseProjectId;
}
