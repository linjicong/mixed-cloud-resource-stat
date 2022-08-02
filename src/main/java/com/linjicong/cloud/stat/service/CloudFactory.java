package com.linjicong.cloud.stat.service;

import cn.hutool.extra.spring.SpringUtil;
import com.linjicong.cloud.stat.client.HCloudClient;
import com.linjicong.cloud.stat.client.QCloudClient;
import com.linjicong.cloud.stat.dao.constant.CloudConstant;
import com.linjicong.cloud.stat.dao.entity.CloudConf;
import org.springframework.beans.factory.FactoryBean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 */
public class CloudFactory {

    public static final Map<Integer, CloudService> cachedServiceMap = new HashMap<>();

    public static CloudService getService(CloudConf cloudConf) {
        switch (cloudConf.getProvider()) {
            case CloudConstant.H_CLOUD:
                return SpringUtil.getBean("HCloudService");
            case CloudConstant.Q_CLOUD:
                return SpringUtil.getBean("QCloudService");
            default:
                throw new RuntimeException("供应商配置错误");
        }
    }
}
