package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 腾讯云-边缘计算机器
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Data
@Table(name = "q_cloud_ecm")
@Entity
public class QCloudEcm extends BasicEntity {

    private String instanceId;
    private String instanceName;
    private String status;
    private String region;
    private String zone;
    private String createTime;
    private String updateTime;
}
