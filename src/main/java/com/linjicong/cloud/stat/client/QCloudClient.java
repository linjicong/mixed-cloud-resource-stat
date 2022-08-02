package com.linjicong.cloud.stat.client;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.huaweicloud.sdk.core.auth.BasicCredentials;
import com.huaweicloud.sdk.core.auth.ICredential;
import com.huaweicloud.sdk.dcs.v2.DcsClient;
import com.huaweicloud.sdk.dcs.v2.region.DcsRegion;
import com.huaweicloud.sdk.ecs.v2.EcsClient;
import com.huaweicloud.sdk.ecs.v2.model.*;
import com.huaweicloud.sdk.ecs.v2.region.EcsRegion;
import com.huaweicloud.sdk.rds.v3.RdsClient;
import com.huaweicloud.sdk.rds.v3.model.*;
import com.huaweicloud.sdk.rds.v3.region.RdsRegion;

import com.linjicong.cloud.stat.dao.entity.CloudConf;
import com.linjicong.cloud.stat.dao.entity.hcloud.HCloudEcs;
import com.linjicong.cloud.stat.dao.entity.hcloud.HCloudRds;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 */
public class QCloudClient{
    private static ICredential auth;

    public QCloudClient(CloudConf cloudConf) {
        auth = new BasicCredentials()
                .withAk(cloudConf.getAccessKey())
                .withSk(cloudConf.getSecretKey());
    }


    public void syncEcs() {
    }

    public void syncRds() {

    }

    public void syncDcs() {

    }
}
