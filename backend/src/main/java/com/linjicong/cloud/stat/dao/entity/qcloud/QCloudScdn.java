package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 腾讯云-SCDN 安全加速
 * 共享 CDN 资源模型
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Data
@Table(name = "q_cloud_scdn")
@Entity
public class QCloudScdn extends BasicEntity {

    private String domain;
    private String status;
    private String origin;
    private String createTime;
    private String updateTime;
}
