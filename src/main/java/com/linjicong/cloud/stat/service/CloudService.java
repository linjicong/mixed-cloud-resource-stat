package com.linjicong.cloud.stat.service;

import com.linjicong.cloud.stat.dao.entity.CloudConf;

/**
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 */
public interface CloudService {
    int syncEcs(CloudConf cloudConf);
}
