package com.linjicong.cloud.stat.dao.mapper.qcloud;

import com.linjicong.cloud.stat.dao.entity.qcloud.QCloudUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author linjicong
 * @version 1.0.0
 * @date 2024-05-24-9:32
 */
@SpringBootTest
class QCloudUserMapperTest {
    @Autowired
    private QCloudUserMapper qCloudUserMapper;

    @Test
    void selectByStatDate() {
        List<QCloudUser> qCloudUsers = qCloudUserMapper.selectByStatDate("2023-02-27");
        System.out.println(qCloudUsers);
    }
}
