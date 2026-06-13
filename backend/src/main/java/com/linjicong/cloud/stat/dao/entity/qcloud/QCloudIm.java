package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 腾讯云-IM 即时通信
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Data
@Table(name = "q_cloud_im")
@Entity
public class QCloudIm extends BasicEntity {

    private String appId;
    private String appName;
    private String status;
    private String createTime;
    private String updateTime;
}
