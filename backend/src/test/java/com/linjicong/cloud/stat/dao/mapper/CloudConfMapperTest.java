package com.linjicong.cloud.stat.dao.mapper;

import com.linjicong.cloud.stat.dao.entity.CloudConf;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @author linjicong
 * @version 1.0.0
 * @date 2024-05-21-10:55
 */
@SpringBootTest
class CloudConfMapperTest {

    @Autowired
    private CloudConfMapper cloudConfMapper;

    /**
     * 测试插入云配置信息
     * 此测试用例用于验证CloudConf实体及其相关操作的正确性
     */
    @Test
    void insertCloudConf() {
        // 创建一个新的云配置对象
        CloudConf cloudConf = new CloudConf();

        // 设置云配置的名称
        cloudConf.setName("xxx");
        // 设置云服务提供商
        cloudConf.setProvider("xxx");
        // 设置云服务的区域
        cloudConf.setRegion("xxx");
        // 设置访问云服务的密钥ID
        cloudConf.setAccessKey("xxx ");
        // 设置访问云服务的密钥内容
        cloudConf.setSecretKey("xxx");

        // 执行插入操作，将云配置信息保存到数据库
        cloudConfMapper.insert(cloudConf);
    }

    @Test
    void selectCloudConf() {
        CloudConf cloudConf = cloudConfMapper.selectById(2);
        System.out.println(cloudConf);
    }

    /**
     * 更新云配置信息的测试方法
     * 此方法演示了如何从数据库中选择一条云配置记录，修改其访问密钥和秘密密钥，
     * 更新最后修改时间，然后将更改保存回数据库
     */
    @Test
    void updateCloudConf() {
        // 选择数据库中ID为11的云配置记录
        CloudConf cloudConf = cloudConfMapper.selectById(11);
        // 设置新的访问密钥
        cloudConf.setAccessKey("xxx");
        // 设置新的秘密密钥
        cloudConf.setSecretKey("xxx");
        // 更新最后修改时间为当前时间
        cloudConf.setUpdateTime(new Date());
        // 更新数据库中的云配置记录，并返回受影响的行数
        int i = cloudConfMapper.updateById(cloudConf);
        // 打印受影响的行数
        System.out.println(i);
    }
}
