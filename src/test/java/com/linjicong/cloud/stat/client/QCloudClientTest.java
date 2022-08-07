package com.linjicong.cloud.stat.client;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import com.huaweicloud.sdk.ces.v1.model.MetricInfo;
import com.huaweicloud.sdk.ecs.v2.model.ServerDetail;
import com.linjicong.cloud.stat.dao.entity.CloudConf;
import com.linjicong.cloud.stat.dao.entity.hcloud.*;
import com.linjicong.cloud.stat.dao.entity.qcloud.QCloudCvm;
import com.linjicong.cloud.stat.dao.mapper.CloudConfMapper;
import com.linjicong.cloud.stat.dao.mapper.hcloud.*;
import com.linjicong.cloud.stat.dao.mapper.qcloud.QCloudCvmMapper;
import com.linjicong.cloud.stat.util.BeanUtils;
import com.obs.services.model.BucketTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author linjicong
 * @version 1.0.0
 * @date 2022-08-03-15:42
 */
@SpringBootTest
class QCloudClientTest {

    private QCloudClient qCloudClient;

    @Resource
    private CloudConfMapper cloudConfMapper;

    @Resource
    private QCloudCvmMapper qCloudCvmMapper;

    @BeforeEach
    public void init(){
        CloudConf cloudConf = cloudConfMapper.selectByPrimaryKey(2);
        qCloudClient = new QCloudClient(cloudConf);
    }

    @Test
    void syncCvm() {
        List<QCloudCvm> qCloudCvms = qCloudClient.listCvm();
        qCloudCvmMapper.insertList(qCloudCvms);
    }


}