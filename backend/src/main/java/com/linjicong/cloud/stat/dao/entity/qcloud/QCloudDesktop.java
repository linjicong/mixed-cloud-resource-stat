package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 腾讯云-云桌面
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Data
@Table(name = "q_cloud_desktop")
@Entity
public class QCloudDesktop extends BasicEntity {

    private String desktopId;
    private String desktopName;
    private String status;
    private String region;
    private String zone;
    private String createTime;
    private String updateTime;
    private String appId;
    private String uin;
}
