package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 腾讯云-流量包
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Data
@Table(name = "q_cloud_traffic_package")
@Entity
public class QCloudTrafficPackage extends BasicEntity {

    private String trafficPackageId;
    private String trafficPackageName;
    private String status;
    private String region;
    private String amount;
    private String usedAmount;
    private String createTime;
    private String updateTime;
}
