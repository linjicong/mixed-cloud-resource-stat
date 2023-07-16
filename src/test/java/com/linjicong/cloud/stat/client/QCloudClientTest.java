package com.linjicong.cloud.stat.client;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linjicong.cloud.stat.dao.entity.CloudConf;
import com.linjicong.cloud.stat.dao.entity.qcloud.*;
import com.linjicong.cloud.stat.dao.mapper.CloudConfMapper;
import com.linjicong.cloud.stat.dao.mapper.qcloud.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.annotation.Resource;
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

    @Resource
    private QCloudUserMapper qCloudUserMapper;

    @Resource
    private QCloudResourceTagMapper qCloudResourceTagMapper;

    @BeforeEach
    public void init(){
        CloudConf cloudConf = cloudConfMapper.selectById(3);
        qCloudClient = new QCloudClient(cloudConf);
    }

    @Test
    void listCvm() {
        List<QCloudCvm> qCloudCvms = qCloudClient.listCvm();
        qCloudCvmMapper.insertBatch(qCloudCvms);
    }

    @Test
    void listCbs() {
        List<QCloudCbs> qCloudCbs = qCloudClient.listCbs();
        qCloudCbsMapper.insertBatch(qCloudCbs);
    }

    @Test
    void listClb() {
        List<QCloudClb> qCloudClbs = qCloudClient.listClb();
        qCloudClbMapper.insertBatch(qCloudClbs);
    }

    @Test
    void listBillResourceSummary() {
        qCloudBillResourceSummaryMapper.deleteByStatDate(DateUtil.today());
        List<QCloudBillResourceSummary> qCloudBillResourceSummaries = qCloudClient.listBillResourceSummary("2023-03");
        qCloudBillResourceSummaryMapper.insertBatch(qCloudBillResourceSummaries);
    }

    @Test
    void listCdb() {
        List<QCloudCdb> qCloudCdbs = qCloudClient.listCdb();
        qCloudCdbMapper.insertBatch(qCloudCdbs);
    }

    @Test
    void listCfs() {
        List<QCloudCfs> qCloudCfs = qCloudClient.listCfs();
        qCloudCfsMapper.selectPage(new Page<>(1, 10), null);
        qCloudCfsMapper.insertBatch(qCloudCfs);
    }

    @Test
    void listUser() {
        List<QCloudUser> qCloudUsers = qCloudClient.listUsers();
        //for (QCloudUser qCloudUser : qCloudUsers) {
        //    qCloudUserMapper.insert(qCloudUser);
        //}
        qCloudUserMapper.insertBatch(qCloudUsers);
        //qCloudUserMapper.insertBatchSomeColumn(qCloudUsers);
    }

    @Test
    void listUsers() {
    }

    @Test
    void listResourceTags() {
        qCloudResourceTagMapper.insertBatch(qCloudClient.listResourceTags());
    }
}
