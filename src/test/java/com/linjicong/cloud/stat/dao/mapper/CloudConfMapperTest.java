package com.linjicong.cloud.stat.dao.mapper;

import com.linjicong.cloud.stat.dao.entity.CloudConf;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author linjicong
 * @version 1.0.0
 * @date 2024-05-21-10:55
 */
@SpringBootTest
class CloudConfMapperTest {

    @Autowired
    private CloudConfMapper cloudConfMapper;

    @Test
    void insertCloudConf() {
        CloudConf cloudConf = new CloudConf();
        cloudConf.setName("xxx");
        cloudConf.setProvider("xxx");
        cloudConf.setRegion("xxx");
        cloudConf.setAccessKey("xxx ");
        cloudConf.setSecretKey("xxx");
        cloudConfMapper.insert(cloudConf);
    }

    @Test
    void selectCloudConf() {
        CloudConf cloudConf = cloudConfMapper.selectById(6);
        System.out.println(cloudConf);
    }
}
