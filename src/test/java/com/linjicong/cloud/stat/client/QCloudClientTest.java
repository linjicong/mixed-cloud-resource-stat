package com.linjicong.cloud.stat.client;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import com.huaweicloud.sdk.ces.v1.model.MetricInfo;
import com.huaweicloud.sdk.ecs.v2.model.ServerDetail;
import com.linjicong.cloud.stat.dao.entity.CloudConf;
import com.linjicong.cloud.stat.dao.entity.hcloud.*;
import com.linjicong.cloud.stat.dao.entity.qcloud.*;
import com.linjicong.cloud.stat.dao.mapper.CloudConfMapper;
import com.linjicong.cloud.stat.dao.mapper.hcloud.*;
import com.linjicong.cloud.stat.dao.mapper.qcloud.*;
import com.linjicong.cloud.stat.util.BeanUtils;
import com.obs.services.model.BucketTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;
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
    @Resource
    private QCloudCbsMapper qCloudCbsMapper;
    @Resource
    private QCloudClbMapper qCloudClbMapper;
    @Resource
    private QCloudBillResourceSummaryMapper qCloudBillResourceSummaryMapper;
    @Resource
    private QCloudCdbMapper qCloudCdbMapper;
    @Resource
    private QCloudCfsMapper qCloudCfsMapper;

    @BeforeEach
    public void init(){
        CloudConf cloudConf = cloudConfMapper.selectByPrimaryKey(2);
        qCloudClient = new QCloudClient(cloudConf);
    }

    @Test
    void listCvm() {
        List<QCloudCvm> qCloudCvms = qCloudClient.listCvm();
        qCloudCvmMapper.insertList(qCloudCvms);
    }

    @Test
    void listCbs() {
        List<QCloudCbs> qCloudCbs = qCloudClient.listCbs();
        qCloudCbsMapper.insertList(qCloudCbs);
    }

    @Test
    void listClb() {
        List<QCloudClb> qCloudClbs = qCloudClient.listClb();
        qCloudClbMapper.insertList(qCloudClbs);
    }

    @Test
    void listBillResourceSummary() {
        List<QCloudBillResourceSummary> qCloudBillResourceSummaries = qCloudClient.listBillResourceSummary(DateUtil.format(new Date(), "yyyy-MM"));
        qCloudBillResourceSummaryMapper.insertList(qCloudBillResourceSummaries);
    }

    @Test
    void listCdb() {
        List<QCloudCdb> qCloudCdbs = qCloudClient.listCdb();
        qCloudCdbMapper.insertList(qCloudCdbs);
    }

    @Test
    void listCfs() {
        List<QCloudCfs> qCloudCfs = qCloudClient.listCfs();
        qCloudCfsMapper.insertList(qCloudCfs);
    }
}
