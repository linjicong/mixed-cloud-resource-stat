package com.linjicong.cloud.stat.controller;

import com.linjicong.cloud.stat.dao.mapper.acloud.*;
import com.linjicong.cloud.stat.dao.mapper.hcloud.*;
import com.linjicong.cloud.stat.dao.mapper.qcloud.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/statistics")
@CrossOrigin(origins = "*", maxAge = 3600)
public class StatisticsController {

    @Autowired private HCloudEcsMapper hCloudEcsMapper;
    @Autowired private HCloudRdsMapper hCloudRdsMapper;
    @Autowired private HCloudElbMapper hCloudElbMapper;
    @Autowired private HCloudEvsMapper hCloudEvsMapper;
    @Autowired private HCloudVpcMapper hCloudVpcMapper;

    @Autowired private QCloudCvmMapper qCloudCvmMapper;
    @Autowired private QCloudCdbMapper qCloudCdbMapper;
    @Autowired private QCloudClbMapper qCloudClbMapper;
    @Autowired private QCloudCbsMapper qCloudCbsMapper;
    @Autowired private QCloudVpcMapper qCloudVpcMapper;
    @Autowired private QCloudVpcSubnetMapper qCloudVpcSubnetMapper;
    @Autowired private QCloudEipMapper qCloudEipMapper;
    @Autowired private QCloudSecurityGroupMapper qCloudSecurityGroupMapper;
    @Autowired private QCloudCdnDomainMapper qCloudCdnDomainMapper;
    @Autowired private QCloudRedisMapper qCloudRedisMapper;
    @Autowired private QCloudMongoDbMapper qCloudMongoDbMapper;
    @Autowired private QCloudCynosDBMapper qCloudCynosDBMapper;
    @Autowired private QCloudPostgresqlMapper qCloudPostgresqlMapper;
    @Autowired private QCloudSqlserverMapper qCloudSqlserverMapper;
    @Autowired private QCloudNatGatewayMapper qCloudNatGatewayMapper;
    @Autowired private QCloudCfsMapper qCloudCfsMapper;
    @Autowired private QCloudDnsDomainMapper qCloudDnsDomainMapper;
    @Autowired private QCloudCosMapper qCloudCosMapper;
    @Autowired private QCloudScfMapper qCloudScfMapper;
    @Autowired private QCloudMariaDbMapper qCloudMariaDbMapper;
    @Autowired private QCloudDCDBMapper qCloudDCDBMapper;
    @Autowired private QCloudCkafkaMapper qCloudCkafkaMapper;
    @Autowired private QCloudRocketMQMapper qCloudRocketMQMapper;
    @Autowired private QCloudSSLMapper qCloudSSLMapper;
    @Autowired private QCloudWAFMapper qCloudWAFMapper;
    @Autowired private QCloudCLSMapper qCloudCLSMapper;
    @Autowired private QCloudMonitorMapper qCloudMonitorMapper;
    @Autowired private QCloudDomainMapper qCloudDomainMapper;
    @Autowired private QCloudTKEMapper qCloudTKEMapper;
    @Autowired private QCloudTCRMapper qCloudTCRMapper;
    @Autowired private QCloudESMapper qCloudESMapper;
    @Autowired private QCloudMemcachedMapper qCloudMemcachedMapper;
    @Autowired private QCloudKeeWiDBMapper qCloudKeeWiDBMapper;
    @Autowired private QCloudCTSDBMapper qCloudCTSDBMapper;
    @Autowired private QCloudCHDFSMapper qCloudCHDFSMapper;
    @Autowired private QCloudASMapper qCloudASMapper;
    @Autowired private QCloudLighthouseMapper qCloudLighthouseMapper;
    @Autowired private QCloudDCMapper qCloudDCMapper;
    @Autowired private QCloudRabbitMQMapper qCloudRabbitMQMapper;
    @Autowired private QCloudAPIGWMapper qCloudAPIGWMapper;
    @Autowired private QCloudBMSMapper qCloudBMSMapper;
    @Autowired private QCloudTDMQMapper qCloudTDMQMapper;
    @Autowired private QCloudOceanusMapper qCloudOceanusMapper;
    @Autowired private QCloudEMRMapper qCloudEMRMapper;
    @Autowired private QCloudGaapMapper qCloudGaapMapper;

    @Autowired private ACloudDnsDomainMapper aCloudDnsDomainMapper;
    @Autowired private ACloudEcsMapper aCloudEcsMapper;
    @Autowired private ACloudRdsMapper aCloudRdsMapper;
    @Autowired private ACloudRedisMapper aCloudRedisMapper;
    @Autowired private ACloudVpcMapper aCloudVpcMapper;
    @Autowired private ACloudSlbMapper aCloudSlbMapper;
    @Autowired private ACloudOssMapper aCloudOssMapper;
    @Autowired private ACloudNatGatewayMapper aCloudNatGatewayMapper;
    @Autowired private ACloudSecurityGroupMapper aCloudSecurityGroupMapper;
    @Autowired private ACloudEipMapper aCloudEipMapper;
    @Autowired private ACloudCdnMapper aCloudCdnMapper;
    @Autowired private ACloudWafMapper aCloudWafMapper;
    @Autowired private ACloudKmsMapper aCloudKmsMapper;
    @Autowired private ACloudAckMapper aCloudAckMapper;
    @Autowired private ACloudSlsMapper aCloudSlsMapper;
    @Autowired private ACloudSmsMapper aCloudSmsMapper;
    @Autowired private ACloudMongoDbMapper aCloudMongoDbMapper;
    @Autowired private ACloudKafkaMapper aCloudKafkaMapper;
    @Autowired private ACloudRocketMQMapper aCloudRocketMQMapper;
    @Autowired private ACloudDiskMapper aCloudDiskMapper;
    @Autowired private ACloudElasticsearchMapper aCloudElasticsearchMapper;
    @Autowired private ACloudFcMapper aCloudFcMapper;
    @Autowired private ACloudCmsMapper aCloudCmsMapper;
    @Autowired private ACloudNasMapper aCloudNasMapper;
    @Autowired private ACloudEssMapper aCloudEssMapper;
    @Autowired private ACloudHssMapper aCloudHssMapper;
    @Autowired private ACloudActionTrailMapper aCloudActionTrailMapper;
    @Autowired private ACloudApiGatewayMapper aCloudApiGatewayMapper;
    @Autowired private ACloudIoTMapper aCloudIoTMapper;
    @Autowired private ACloudLiveMapper aCloudLiveMapper;
    @Autowired private ACloudEmrMapper aCloudEmrMapper;
    @Autowired private ACloudVodMapper aCloudVodMapper;
    @Autowired private ACloudAcrMapper aCloudAcrMapper;
    @Autowired private ACloudDdosMapper aCloudDdosMapper;
    @Autowired private ACloudSslMapper aCloudSslMapper;
    @Autowired private ACloudCloudFirewallMapper aCloudCloudFirewallMapper;
    @Autowired private ACloudDscMapper aCloudDscMapper;
    @Autowired private ACloudPolarDbMapper aCloudPolarDbMapper;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getStatistics() {
        try {
            Map<String, Object> statistics = new HashMap<>();

            Map<String, Integer> huaweiStats = new HashMap<>();
            huaweiStats.put("ecs", hCloudEcsMapper.selectList(null).size());
            huaweiStats.put("rds", hCloudRdsMapper.selectList(null).size());
            huaweiStats.put("elb", hCloudElbMapper.selectList(null).size());
            huaweiStats.put("evs", hCloudEvsMapper.selectList(null).size());
            huaweiStats.put("vpc", hCloudVpcMapper.selectList(null).size());
            statistics.put("huawei", huaweiStats);

            Map<String, Integer> tencentStats = new HashMap<>();
            tencentStats.put("cvm", qCloudCvmMapper.selectList(null).size());
            tencentStats.put("cdb", qCloudCdbMapper.selectList(null).size());
            tencentStats.put("clb", qCloudClbMapper.selectList(null).size());
            tencentStats.put("cbs", qCloudCbsMapper.selectList(null).size());
            tencentStats.put("vpc", qCloudVpcMapper.selectList(null).size());
            tencentStats.put("subnet", qCloudVpcSubnetMapper.selectList(null).size());
            tencentStats.put("eip", qCloudEipMapper.selectList(null).size());
            tencentStats.put("sg", qCloudSecurityGroupMapper.selectList(null).size());
            tencentStats.put("cdn", qCloudCdnDomainMapper.selectList(null).size());
            tencentStats.put("redis", qCloudRedisMapper.selectList(null).size());
            tencentStats.put("mongodb", qCloudMongoDbMapper.selectList(null).size());
            tencentStats.put("cynosdb", qCloudCynosDBMapper.selectList(null).size());
            tencentStats.put("postgresql", qCloudPostgresqlMapper.selectList(null).size());
            tencentStats.put("sqlserver", qCloudSqlserverMapper.selectList(null).size());
            tencentStats.put("nat", qCloudNatGatewayMapper.selectList(null).size());
            tencentStats.put("cfs", qCloudCfsMapper.selectList(null).size());
            tencentStats.put("dns", qCloudDnsDomainMapper.selectList(null).size());
            tencentStats.put("cos", qCloudCosMapper.selectList(null).size());
            tencentStats.put("scf", qCloudScfMapper.selectList(null).size());
            tencentStats.put("mariadb", qCloudMariaDbMapper.selectList(null).size());
            tencentStats.put("dcdb", qCloudDCDBMapper.selectList(null).size());
            tencentStats.put("ckafka", qCloudCkafkaMapper.selectList(null).size());
            tencentStats.put("rocketmq", qCloudRocketMQMapper.selectList(null).size());
            tencentStats.put("ssl", qCloudSSLMapper.selectList(null).size());
            tencentStats.put("waf", qCloudWAFMapper.selectList(null).size());
            tencentStats.put("cls", qCloudCLSMapper.selectList(null).size());
            tencentStats.put("monitor", qCloudMonitorMapper.selectList(null).size());
            tencentStats.put("domain", qCloudDomainMapper.selectList(null).size());
            tencentStats.put("tke", qCloudTKEMapper.selectList(null).size());
            tencentStats.put("tcr", qCloudTCRMapper.selectList(null).size());
            tencentStats.put("es", qCloudESMapper.selectList(null).size());
            tencentStats.put("memcached", qCloudMemcachedMapper.selectList(null).size());
            tencentStats.put("keewidb", qCloudKeeWiDBMapper.selectList(null).size());
            tencentStats.put("ctsdb", qCloudCTSDBMapper.selectList(null).size());
            tencentStats.put("chdfs", qCloudCHDFSMapper.selectList(null).size());
            tencentStats.put("as", qCloudASMapper.selectList(null).size());
            tencentStats.put("lighthouse", qCloudLighthouseMapper.selectList(null).size());
            tencentStats.put("dc", qCloudDCMapper.selectList(null).size());
            tencentStats.put("rabbitmq", qCloudRabbitMQMapper.selectList(null).size());
            tencentStats.put("apigw", qCloudAPIGWMapper.selectList(null).size());
            tencentStats.put("bms", qCloudBMSMapper.selectList(null).size());
            tencentStats.put("tdmq", qCloudTDMQMapper.selectList(null).size());
            tencentStats.put("oceanus", qCloudOceanusMapper.selectList(null).size());
            tencentStats.put("emr", qCloudEMRMapper.selectList(null).size());
            tencentStats.put("gaap", qCloudGaapMapper.selectList(null).size());
            statistics.put("tencent", tencentStats);

            Map<String, Integer> aliyunStats = new HashMap<>();
            aliyunStats.put("dns", aCloudDnsDomainMapper.selectList(null).size());
            aliyunStats.put("ecs", aCloudEcsMapper.selectList(null).size());
            aliyunStats.put("rds", aCloudRdsMapper.selectList(null).size());
            aliyunStats.put("redis", aCloudRedisMapper.selectList(null).size());
            aliyunStats.put("vpc", aCloudVpcMapper.selectList(null).size());
            aliyunStats.put("slb", aCloudSlbMapper.selectList(null).size());
            aliyunStats.put("oss", aCloudOssMapper.selectList(null).size());
            aliyunStats.put("nat", aCloudNatGatewayMapper.selectList(null).size());
            aliyunStats.put("sg", aCloudSecurityGroupMapper.selectList(null).size());
            aliyunStats.put("eip", aCloudEipMapper.selectList(null).size());
            aliyunStats.put("cdn", aCloudCdnMapper.selectList(null).size());
            aliyunStats.put("waf", aCloudWafMapper.selectList(null).size());
            aliyunStats.put("kms", aCloudKmsMapper.selectList(null).size());
            aliyunStats.put("ack", aCloudAckMapper.selectList(null).size());
            aliyunStats.put("sls", aCloudSlsMapper.selectList(null).size());
            aliyunStats.put("sms", aCloudSmsMapper.selectList(null).size());
            aliyunStats.put("mongodb", aCloudMongoDbMapper.selectList(null).size());
            aliyunStats.put("kafka", aCloudKafkaMapper.selectList(null).size());
            aliyunStats.put("rocketmq", aCloudRocketMQMapper.selectList(null).size());
            aliyunStats.put("disk", aCloudDiskMapper.selectList(null).size());
            aliyunStats.put("elasticsearch", aCloudElasticsearchMapper.selectList(null).size());
            aliyunStats.put("fc", aCloudFcMapper.selectList(null).size());
            aliyunStats.put("cms", aCloudCmsMapper.selectList(null).size());
            aliyunStats.put("nas", aCloudNasMapper.selectList(null).size());
            aliyunStats.put("ess", aCloudEssMapper.selectList(null).size());
            aliyunStats.put("hss", aCloudHssMapper.selectList(null).size());
            aliyunStats.put("actiontrail", aCloudActionTrailMapper.selectList(null).size());
            aliyunStats.put("apigateway", aCloudApiGatewayMapper.selectList(null).size());
            aliyunStats.put("iot", aCloudIoTMapper.selectList(null).size());
            aliyunStats.put("live", aCloudLiveMapper.selectList(null).size());
            aliyunStats.put("emr", aCloudEmrMapper.selectList(null).size());
            aliyunStats.put("vod", aCloudVodMapper.selectList(null).size());
            aliyunStats.put("acr", aCloudAcrMapper.selectList(null).size());
            aliyunStats.put("ddos", aCloudDdosMapper.selectList(null).size());
            aliyunStats.put("ssl", aCloudSslMapper.selectList(null).size());
            aliyunStats.put("cloudfw", aCloudCloudFirewallMapper.selectList(null).size());
            aliyunStats.put("dsc", aCloudDscMapper.selectList(null).size());
            aliyunStats.put("polardb", aCloudPolarDbMapper.selectList(null).size());
            statistics.put("aliyun", aliyunStats);

            int totalHuawei = huaweiStats.values().stream().mapToInt(Integer::intValue).sum();
            int totalTencent = tencentStats.values().stream().mapToInt(Integer::intValue).sum();
            int totalAliyun = aliyunStats.values().stream().mapToInt(Integer::intValue).sum();

            Map<String, Integer> totals = new HashMap<>();
            totals.put("huawei", totalHuawei);
            totals.put("tencent", totalTencent);
            totals.put("aliyun", totalAliyun);
            totals.put("all", totalHuawei + totalTencent + totalAliyun);
            statistics.put("totals", totals);

            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "statistics failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
