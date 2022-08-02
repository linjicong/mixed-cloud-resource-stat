package com.linjicong.cloud.stat.dao.entity;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

/**
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 */
@Data
public class CloudConf {
    @Id
    private Integer pk;

    private String name;

    private String provider;

    private String region;

    private String accessKey;

    private String secretKey;

    private Boolean enable;

    private Date createTime;

    private Date updateTime;
}
