package com.linjicong.cloud.stat.client;

import com.linjicong.cloud.stat.dao.entity.CloudConf;
import com.linjicong.cloud.stat.dao.entity.acloud.ACloudDnsDomain;
import com.linjicong.cloud.stat.dao.entity.acloud.ACloudDnsDomainRecords;
import com.linjicong.cloud.stat.dao.mapper.CloudConfMapper;
import com.linjicong.cloud.stat.dao.mapper.acloud.ACloudDnsDomainMapper;
import com.linjicong.cloud.stat.dao.mapper.acloud.ACloudDnsDomainRecordsMapper;
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
class ACloudClientTest {

    private ACloudClient aCloudClient;

    @Resource
    private CloudConfMapper cloudConfMapper;

    @Resource
    private ACloudDnsDomainRecordsMapper aCloudDnsDomainRecordsMapper;

    @Resource
    private ACloudDnsDomainMapper aCloudDnsDomainMapper;

    @BeforeEach
    public void init(){
        CloudConf cloudConf = cloudConfMapper.selectById(4);
        aCloudClient = new ACloudClient(cloudConf);
    }

    @Test
    void listDnsDomain() {
        List<ACloudDnsDomain> aCloudDnsDomain = aCloudClient.listDnsDomain();
        aCloudDnsDomainMapper.insertBatch(aCloudDnsDomain);
    }

    /**
     * 测试方法，用于列出DNS域名记录
     * 该方法从数据库中选择符合条件的域名列表，然后调用云服务API获取每个域名的DNS记录，并将这些记录批量插入数据库
     */
    @Test
    void listDnsDomainRecords() {
        // 根据配置名称从数据库中选择域名列表
        List<ACloudDnsDomain> aCloudDnsDomains = aCloudDnsDomainMapper.selectByConfName("aliyun-lc");

        // 创建一个列表，用于存储所有域名的DNS记录
        List<ACloudDnsDomainRecords> aCloudDnsDomainRecords =new ArrayList<>();

        // 遍历每个域名，调用云服务API获取DNS记录，并添加到列表中
        for (ACloudDnsDomain aCloudDnsDomain : aCloudDnsDomains) {
            aCloudDnsDomainRecords.addAll(aCloudClient.listDnsDomainRecords(aCloudDnsDomain.getDomainName()));
        }

        // 将收集到的所有DNS记录批量插入数据库
        aCloudDnsDomainRecordsMapper.insertBatch(aCloudDnsDomainRecords);
    }
}
