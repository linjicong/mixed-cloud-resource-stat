package com.linjicong.cloud.stat.service;

import com.linjicong.cloud.stat.dao.entity.CloudConf;
import org.springframework.stereotype.Service;

/**
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 */
@Service
public class QCloudService implements CloudService{
    @Override
    public int syncEcs(CloudConf cloudConf) {
        return 0;
    }
}
