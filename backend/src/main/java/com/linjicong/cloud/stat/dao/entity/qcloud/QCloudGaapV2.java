package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 腾讯云-全球加速 2.0
 * 共享 GAAP 资源模型
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Data
@Table(name = "q_cloud_gaap_v2")
@Entity
public class QCloudGaapV2 extends BasicEntity {

    private String proxyId;
    private String proxyName;
    private String status;
    private String region;
    private String createTime;
    private String updateTime;
}
