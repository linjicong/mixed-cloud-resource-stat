package com.linjicong.cloud.stat.dao.mapper.qcloud;

import com.linjicong.cloud.stat.client.QCloudClient;
import com.linjicong.cloud.stat.dao.entity.CloudConf;
import com.linjicong.cloud.stat.dao.entity.qcloud.QCloudAccessKey;
import com.linjicong.cloud.stat.dao.entity.qcloud.QCloudUserToAccessKey;
import com.linjicong.cloud.stat.dao.mapper.CloudConfMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.BeforeEach;
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

    @Test
    void insertBatch() {
        List<QCloudUserToAccessKey> qCloudUserToAccessKeyList = new ArrayList<>();
        QCloudUserToAccessKey qCloudUserToAccessKey = new QCloudUserToAccessKey();
        qCloudUserToAccessKey.setUin(123L);
        qCloudUserToAccessKey.setAccessKeyId("123456");
        qCloudUserToAccessKeyList.add(qCloudUserToAccessKey);
        qCloudUserToAccessKeyMapper.insertBatch(qCloudUserToAccessKeyList);
    }
}
