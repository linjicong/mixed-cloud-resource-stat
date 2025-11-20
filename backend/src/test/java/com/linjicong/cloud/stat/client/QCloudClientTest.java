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
        // 根据统计日期和配置名称查询访问密钥
        List<QCloudAccessKey> qCloudAccessKeys = qCloudAccessKeyMapper.selectByStatDateAndConfName("2024-12-23",cloudConf.getName());
        // 初始化用于存储访问密钥最后使用情况的列表
        List<QCloudAccessKeyLastUsed> qCloudAccessKeyLastUsedList = new ArrayList<>();
        // 提取访问密钥的SecretId
        List<String> secretIdList = qCloudAccessKeys.stream().map(QCloudAccessKey::getAccessKeyId).toList();
        // 将SecretId列表分割为多个子列表，每个子列表包含10个SecretId
        List<List<String>> splitList = CollUtil.split(secretIdList, 10);
        // 遍历每个子列表，查询并合并访问密钥的最后使用情况
        for (List<String> strings : splitList) {
            qCloudAccessKeyLastUsedList.addAll(qCloudClient.listAccessKeyLastUsed(strings.toArray(String[]::new)));
        }
        // 批量插入访问密钥的最后使用情况到数据库
        qCloudAccessKeyLastUsedMapper.insertBatch(qCloudAccessKeyLastUsedList);
    }

    /**
     * 测试方法：获取所有访问密钥并关联用户
     * 该方法首先根据统计日期和配置名称查询用户，然后遍历每个用户获取其访问密钥，
     * 并建立用户与访问密钥之间的关联关系，最后批量插入访问密钥和用户-密钥关联信息
     */
    @Test
    void listAccessKeysAll() {
        // 根据统计日期和配置名称查询QCloud用户列表
        List<QCloudUser> qCloudUsers = qCloudUserMapper.selectByStatDateAndConfName("2024-12-23",cloudConf.getName());
        // 初始化存储所有访问密钥的列表
        List<QCloudAccessKey> qCloudAccessKeysAll = new ArrayList<>();
        // 初始化存储所有用户与访问密钥关联关系的列表
        List<QCloudUserToAccessKey>  userToAccessKeysAll= new ArrayList<>();

        // 遍历每个用户，获取其访问密钥信息
        for (QCloudUser qCloudUser : qCloudUsers) {
            // 获取当前用户的唯一标识uin
            Long uin = qCloudUser.getUin();
            // 查询并获取当前用户的访问密钥列表
            List<QCloudAccessKey> qCloudAccessKeys = qCloudClient.listAccessKeys(uin);
            // 将当前用户的访问密钥添加到所有访问密钥列表中
            qCloudAccessKeysAll.addAll(qCloudAccessKeys);

            // 遍历当前用户的每个访问密钥，创建用户与密钥的关联关系
            for (QCloudAccessKey qCloudAccessKey : qCloudAccessKeys) {
                QCloudUserToAccessKey qCloudUserToAccessKey = new QCloudUserToAccessKey();
                // 设置关联关系中的用户标识
                qCloudUserToAccessKey.setUin(uin);
                // 设置关联关系中的访问密钥ID
                qCloudUserToAccessKey.setAccessKeyId(qCloudAccessKey.getAccessKeyId());
                // 将用户与访问密钥的关联关系添加到列表中
                userToAccessKeysAll.add(qCloudUserToAccessKey);
            }
        }

        // 如果存在访问密钥，则批量插入到数据库中
        if(!qCloudAccessKeysAll.isEmpty()) {
            qCloudAccessKeyMapper.insertBatch(qCloudAccessKeysAll);
        }
        // 如果存在用户与访问密钥的关联关系，则批量插入到数据库中
        if(!userToAccessKeysAll.isEmpty()) {
            qCloudUserToAccessKeyMapper.insertBatch(userToAccessKeysAll);
        }
    }

    @Test
    void deleteData(){

    }
}
