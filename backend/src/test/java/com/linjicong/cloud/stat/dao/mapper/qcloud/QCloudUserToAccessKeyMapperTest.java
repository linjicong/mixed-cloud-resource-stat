package com.linjicong.cloud.stat.dao.mapper.qcloud;

import com.linjicong.cloud.stat.dao.entity.qcloud.QCloudUserToAccessKey;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author linjicong
 * @version 1.0.0
 * @date 2024-05-24-17:32
 */
@SpringBootTest
class QCloudUserToAccessKeyMapperTest {
    @Autowired
    private QCloudUserToAccessKeyMapper qCloudUserToAccessKeyMapper;

    /**
     * 批量插入测试
     * 此测试方法旨在验证QCloudUserToAccessKeyMapper的insertBatch功能
     * 它通过创建一个包含单个QCloudUserToAccessKey对象的列表来模拟批量插入场景
     * 这个方法有助于评估在处理多个对象时，插入操作的性能和正确性
     */
    @Test
    void insertBatch() {
        // 创建一个空的QCloudUserToAccessKey列表，用于批量插入
        List<QCloudUserToAccessKey> qCloudUserToAccessKeyList = new ArrayList<>();

        // 实例化一个QCloudUserToAccessKey对象，并设置其属性
        QCloudUserToAccessKey qCloudUserToAccessKey = new QCloudUserToAccessKey();
        // 设置用户识别号（UIN）
        qCloudUserToAccessKey.setUin(123L);
        // 设置访问密钥ID
        qCloudUserToAccessKey.setAccessKeyId("123456");

        // 将配置好的QCloudUserToAccessKey对象添加到列表中
        qCloudUserToAccessKeyList.add(qCloudUserToAccessKey);

        // 调用mapper的批量插入方法，将列表中的对象插入数据库
        qCloudUserToAccessKeyMapper.insertBatch(qCloudUserToAccessKeyList);
    }
}
