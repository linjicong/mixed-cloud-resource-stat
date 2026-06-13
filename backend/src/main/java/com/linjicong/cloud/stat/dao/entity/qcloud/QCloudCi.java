package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 腾讯云-数据万象
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Data
@Table(name = "q_cloud_ci")
@Entity
public class QCloudCi extends BasicEntity {

    private String bucketId;
    private String bucketName;
    private String region;
    private String createTime;
    private String updateTime;
    private String description;
}
