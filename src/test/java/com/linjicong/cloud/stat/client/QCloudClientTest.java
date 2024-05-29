package com.linjicong.cloud.stat.client;

import cn.hutool.core.collection.CollUtil;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author linjicong
 * @version 1.0.0
 * @date 2022-08-03-15:42
 */
@SpringBootTest
class QCloudClientTest {


    @Resource
    private CloudConfMapper cloudConfMapper;

    private CloudConf cloudConf;

    private QCloudClient qCloudClient;

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

    @Resource
    private QCloudDnsDomainMapper qCloudDnsDomainMapper;

    @Resource
    private QCloudAccessKeyMapper qCloudAccessKeyMapper;
    @Resource
    private QCloudUserToAccessKeyMapper qCloudUserToAccessKeyMapper;
    @Resource
    private QCloudAccessKeyLastUsedMapper qCloudAccessKeyLastUsedMapper;

    @BeforeEach
    public void init(){
        cloudConf = cloudConfMapper.selectById(3);
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
        qCloudBillResourceSummaryMapper.deleteByStatDateAndConfName(DateUtil.today(),cloudConf.getName());
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
        qCloudUserMapper.insertBatch(qCloudUsers);
    }

    @Test
    void listResourceTags() {
        qCloudResourceTagMapper.insertBatch(qCloudClient.listResourceTags());
    }

    @Test
    void listDnsDomain() {
        qCloudDnsDomainMapper.insertBatch(qCloudClient.listDnsDomain());
    }

    @Test
    void listAccessKeys() {
        List<QCloudAccessKey> qCloudAccessKeys = qCloudClient.listAccessKeys(null);
        qCloudAccessKeyMapper.insertBatch(qCloudAccessKeys);
    }

    @Test
    void listAccessKeysLastUsed() {
        List<QCloudAccessKey> qCloudAccessKeys = qCloudAccessKeyMapper.selectByStatDateAndConfName("2024-05-29",cloudConf.getName());
        List<QCloudAccessKeyLastUsed> qCloudAccessKeyLastUsedList = new ArrayList<>();
        List<String> secretIdList = qCloudAccessKeys.stream().map(QCloudAccessKey::getAccessKeyId).toList();
        List<List<String>> splitList = CollUtil.split(secretIdList, 10);
        for (List<String> strings : splitList) {
            qCloudAccessKeyLastUsedList.addAll(qCloudClient.listAccessKeyLastUsed(strings.toArray(String[]::new)));
        }
        qCloudAccessKeyLastUsedMapper.insertBatch(qCloudAccessKeyLastUsedList);
    }

    @Test
    void listAccessKeysAll() {
        List<QCloudUser> qCloudUsers = qCloudUserMapper.selectByStatDateAndConfName("2024-05-29",cloudConf.getName());
        List<QCloudAccessKey> qCloudAccessKeysAll = new ArrayList<>();
        List<QCloudUserToAccessKey>  userToAccessKeysAll= new ArrayList<>();
        for (QCloudUser qCloudUser : qCloudUsers) {
            Long uin = qCloudUser.getUin();
            List<QCloudAccessKey> qCloudAccessKeys = qCloudClient.listAccessKeys(uin);
            qCloudAccessKeysAll.addAll(qCloudAccessKeys);
            for (QCloudAccessKey qCloudAccessKey : qCloudAccessKeys) {
                QCloudUserToAccessKey qCloudUserToAccessKey = new QCloudUserToAccessKey();
                qCloudUserToAccessKey.setUin(uin);
                qCloudUserToAccessKey.setAccessKeyId(qCloudAccessKey.getAccessKeyId());
                userToAccessKeysAll.add(qCloudUserToAccessKey);
            }
        }
        if(!qCloudAccessKeysAll.isEmpty()) {
            qCloudAccessKeyMapper.insertBatch(qCloudAccessKeysAll);
        }
        if(!userToAccessKeysAll.isEmpty()) {
            qCloudUserToAccessKeyMapper.insertBatch(userToAccessKeysAll);
        }
    }

    @Test
    void deleteData(){

    }
}
