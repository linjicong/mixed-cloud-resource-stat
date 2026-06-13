package com.linjicong.cloud.stat.dao.entity.qcloud;

import com.linjicong.cloud.stat.dao.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 腾讯云-云联网
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Data
@Table(name = "q_cloud_cc")
@Entity
public class QCloudCc extends BasicEntity {

    private String ccnId;
    private String ccnName;
    private String status;
    private String region;
    private String createTime;
    private String updateTime;
    private String description;
}
