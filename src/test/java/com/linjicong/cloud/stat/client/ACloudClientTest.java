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

import javax.annotation.Resource;
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
        CloudConf cloudConf = cloudConfMapper.selectByPrimaryKey(5);
        aCloudClient = new ACloudClient(cloudConf);
    }

    @Test
    void listDnsDomain() {
        List<ACloudDnsDomain> aCloudDnsDomain = aCloudClient.listDnsDomain();
        aCloudDnsDomainMapper.insertList(aCloudDnsDomain);
    }

    @Test
    void listDnsDomainRecords() {
        List<ACloudDnsDomain> aCloudDnsDomains = aCloudDnsDomainMapper.selectAll();
        List<ACloudDnsDomainRecords> aCloudDnsDomainRecords =new ArrayList<>();
        for (ACloudDnsDomain aCloudDnsDomain : aCloudDnsDomains) {
            aCloudDnsDomainRecords.addAll(aCloudClient.listDnsDomainRecords(aCloudDnsDomain.getDomainName()));
        }
        aCloudDnsDomainRecordsMapper.insertList(aCloudDnsDomainRecords);
    }
}
