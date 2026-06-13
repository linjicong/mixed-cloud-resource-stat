package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 腾讯云-共享带宽
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Data
@Table(name = "q_cloud_bandwidth_package")
@Entity
public class QCloudBandwidthPackage extends BasicEntity {

    private String bandwidthPackageId;
    private String bandwidthPackageName;
    private String status;
    private String region;
    private String bandwidth;
    private String networkType;
    private String createTime;
    private String updateTime;
}
