package com.linjicong.cloud.stat.controller;

import com.linjicong.cloud.stat.dao.mapper.acloud.*;
import com.linjicong.cloud.stat.dao.mapper.hcloud.*;
import com.linjicong.cloud.stat.dao.mapper.qcloud.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 汇总统计控制器
 * 提供按云厂商分组的资源统计和跨云对比数据
 */
@RestController
@RequestMapping("/summary")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SummaryController {

    // ==================== 华为云 Mapper ====================
    @Autowired private HCloudEcsMapper hCloudEcsMapper;
    @Autowired private HCloudRdsMapper hCloudRdsMapper;
    @Autowired private HCloudElbMapper hCloudElbMapper;
    @Autowired private HCloudEvsMapper hCloudEvsMapper;
    @Autowired private HCloudVpcMapper hCloudVpcMapper;
    @Autowired private HCloudEipMapper hCloudEipMapper;
    @Autowired private HCloudNatMapper hCloudNatMapper;
    @Autowired private HCloudObsMapper hCloudObsMapper;
    @Autowired private HCloudDcsMapper hCloudDcsMapper;
    @Autowired private HCloudDdsMapper hCloudDdsMapper;
    @Autowired private HCloudCceMapper hCloudCceMapper;
    @Autowired private HCloudKafkaMapper hCloudKafkaMapper;
    @Autowired private HCloudRocketMqMapper hCloudRocketMqMapper;
    @Autowired private HCloudWafMapper hCloudWafMapper;
    @Autowired private HCloudAntiDdosMapper hCloudAntiDdosMapper;
    @Autowired private HCloudKmsMapper hCloudKmsMapper;
    @Autowired private HCloudCdnMapper hCloudCdnMapper;
    @Autowired private HCloudCssMapper hCloudCssMapper;
    @Autowired private HCloudMrsMapper hCloudMrsMapper;
    @Autowired private HCloudIotDaMapper hCloudIotDaMapper;
    @Autowired private HCloudFunctionGraphMapper hCloudFunctionGraphMapper;
    @Autowired private HCloudSfsMapper hCloudSfsMapper;
    @Autowired private HCloudSwrMapper hCloudSwrMapper;
    @Autowired private HCloudCesMetricMapper hCloudCesMetricMapper;
    @Autowired private HCloudCtsMapper hCloudCtsMapper;
    @Autowired private HCloudLtsMapper hCloudLtsMapper;

    // ==================== 腾讯云 Mapper ====================
    @Autowired private QCloudCvmMapper qCloudCvmMapper;
    @Autowired private QCloudCdbMapper qCloudCdbMapper;
    @Autowired private QCloudClbMapper qCloudClbMapper;
    @Autowired private QCloudCbsMapper qCloudCbsMapper;
    @Autowired private QCloudVpcMapper qCloudVpcMapper;
    @Autowired private QCloudEipMapper qCloudEipMapper;
    @Autowired private QCloudNatGatewayMapper qCloudNatGatewayMapper;
    @Autowired private QCloudCosMapper qCloudCosMapper;
    @Autowired private QCloudRedisMapper qCloudRedisMapper;
    @Autowired private QCloudMongoDbMapper qCloudMongoDbMapper;
    @Autowired private QCloudTKEMapper qCloudTKEMapper;
    @Autowired private QCloudCkafkaMapper qCloudCkafkaMapper;
    @Autowired private QCloudRocketMQMapper qCloudRocketMQMapper;
    @Autowired private QCloudWAFMapper qCloudWAFMapper;
    @Autowired private QCloudDDoSMapper qCloudDDoSMapper;
    @Autowired private QCloudKMSMapper qCloudKMSMapper;
    @Autowired private QCloudCdnDomainMapper qCloudCdnDomainMapper;
    @Autowired private QCloudESMapper qCloudESMapper;
    @Autowired private QCloudEMRMapper qCloudEMRMapper;
    @Autowired private QCloudIoTMapper qCloudIoTMapper;
    @Autowired private QCloudScfMapper qCloudScfMapper;
    @Autowired private QCloudCfsMapper qCloudCfsMapper;
    @Autowired private QCloudTCRMapper qCloudTCRMapper;
    @Autowired private QCloudMonitorMapper qCloudMonitorMapper;
    @Autowired private QCloudAuditMapper qCloudAuditMapper;
    @Autowired private QCloudCLSMapper qCloudCLSMapper;

    // ==================== 阿里云 Mapper ====================
    @Autowired private ACloudEcsMapper aCloudEcsMapper;
    @Autowired private ACloudRdsMapper aCloudRdsMapper;
    @Autowired private ACloudSlbMapper aCloudSlbMapper;
    @Autowired private ACloudDiskMapper aCloudDiskMapper;
    @Autowired private ACloudVpcMapper aCloudVpcMapper;
    @Autowired private ACloudEipMapper aCloudEipMapper;
    @Autowired private ACloudNatGatewayMapper aCloudNatGatewayMapper;
    @Autowired private ACloudOssMapper aCloudOssMapper;
    @Autowired private ACloudRedisMapper aCloudRedisMapper;
    @Autowired private ACloudMongoDbMapper aCloudMongoDbMapper;
    @Autowired private ACloudAckMapper aCloudAckMapper;
    @Autowired private ACloudKafkaMapper aCloudKafkaMapper;
    @Autowired private ACloudRocketMQMapper aCloudRocketMQMapper;
    @Autowired private ACloudWafMapper aCloudWafMapper;
    @Autowired private ACloudDdosMapper aCloudDdosMapper;
    @Autowired private ACloudKmsMapper aCloudKmsMapper;
    @Autowired private ACloudCdnMapper aCloudCdnMapper;
    @Autowired private ACloudElasticsearchMapper aCloudElasticsearchMapper;
    @Autowired private ACloudEmrMapper aCloudEmrMapper;
    @Autowired private ACloudIoTMapper aCloudIoTMapper;
    @Autowired private ACloudFcMapper aCloudFcMapper;
    @Autowired private ACloudNasMapper aCloudNasMapper;
    @Autowired private ACloudAcrMapper aCloudAcrMapper;
    @Autowired private ACloudCmsMapper aCloudCmsMapper;
    @Autowired private ACloudActionTrailMapper aCloudActionTrailMapper;
    @Autowired private ACloudSlsMapper aCloudSlsMapper;

    /**
     * 获取按云厂商分组的资源统计
     */
    @GetMapping("/provider-stats")
    public ResponseEntity<Map<String, Object>> getProviderStats(
            @RequestParam(required = false) String confName) {
        Map<String, Object> result = new HashMap<>();

        // 华为云统计
        Map<String, Integer> huawei = new LinkedHashMap<>();
        huawei.put("ecs", count(hCloudEcsMapper, confName));
        huawei.put("rds", count(hCloudRdsMapper, confName));
        huawei.put("elb", count(hCloudElbMapper, confName));
        huawei.put("evs", count(hCloudEvsMapper, confName));
        huawei.put("vpc", count(hCloudVpcMapper, confName));
        huawei.put("eip", count(hCloudEipMapper, confName));
        huawei.put("nat", count(hCloudNatMapper, confName));
        huawei.put("obs", count(hCloudObsMapper, confName));
        huawei.put("dcs", count(hCloudDcsMapper, confName));
        huawei.put("dds", count(hCloudDdsMapper, confName));
        huawei.put("cce", count(hCloudCceMapper, confName));
        huawei.put("kafka", count(hCloudKafkaMapper, confName));
        huawei.put("rocketmq", count(hCloudRocketMqMapper, confName));
        huawei.put("waf", count(hCloudWafMapper, confName));
        huawei.put("antiddos", count(hCloudAntiDdosMapper, confName));
        huawei.put("kms", count(hCloudKmsMapper, confName));
        huawei.put("cdn", count(hCloudCdnMapper, confName));
        huawei.put("es", count(hCloudCssMapper, confName));
        huawei.put("emr", count(hCloudMrsMapper, confName));
        huawei.put("iot", count(hCloudIotDaMapper, confName));
        huawei.put("faas", count(hCloudFunctionGraphMapper, confName));
        huawei.put("sfs", count(hCloudSfsMapper, confName));
        huawei.put("swr", count(hCloudSwrMapper, confName));
        huawei.put("ces", count(hCloudCesMetricMapper, confName));
        huawei.put("cts", count(hCloudCtsMapper, confName));
        huawei.put("lts", count(hCloudLtsMapper, confName));

        // 腾讯云统计
        Map<String, Integer> tencent = new LinkedHashMap<>();
        tencent.put("cvm", count(qCloudCvmMapper, confName));
        tencent.put("cdb", count(qCloudCdbMapper, confName));
        tencent.put("clb", count(qCloudClbMapper, confName));
        tencent.put("cbs", count(qCloudCbsMapper, confName));
        tencent.put("vpc", count(qCloudVpcMapper, confName));
        tencent.put("eip", count(qCloudEipMapper, confName));
        tencent.put("nat", count(qCloudNatGatewayMapper, confName));
        tencent.put("cos", count(qCloudCosMapper, confName));
        tencent.put("redis", count(qCloudRedisMapper, confName));
        tencent.put("mongodb", count(qCloudMongoDbMapper, confName));
        tencent.put("tke", count(qCloudTKEMapper, confName));
        tencent.put("ckafka", count(qCloudCkafkaMapper, confName));
        tencent.put("rocketmq", count(qCloudRocketMQMapper, confName));
        tencent.put("waf", count(qCloudWAFMapper, confName));
        tencent.put("ddos", count(qCloudDDoSMapper, confName));
        tencent.put("kms", count(qCloudKMSMapper, confName));
        tencent.put("cdn", count(qCloudCdnDomainMapper, confName));
        tencent.put("es", count(qCloudESMapper, confName));
        tencent.put("emr", count(qCloudEMRMapper, confName));
        tencent.put("iot", count(qCloudIoTMapper, confName));
        tencent.put("scf", count(qCloudScfMapper, confName));
        tencent.put("cfs", count(qCloudCfsMapper, confName));
        tencent.put("tcr", count(qCloudTCRMapper, confName));
        tencent.put("monitor", count(qCloudMonitorMapper, confName));
        tencent.put("audit", count(qCloudAuditMapper, confName));
        tencent.put("cls", count(qCloudCLSMapper, confName));

        // 阿里云统计
        Map<String, Integer> aliyun = new LinkedHashMap<>();
        aliyun.put("ecs", count(aCloudEcsMapper, confName));
        aliyun.put("rds", count(aCloudRdsMapper, confName));
        aliyun.put("slb", count(aCloudSlbMapper, confName));
        aliyun.put("disk", count(aCloudDiskMapper, confName));
        aliyun.put("vpc", count(aCloudVpcMapper, confName));
        aliyun.put("eip", count(aCloudEipMapper, confName));
        aliyun.put("nat", count(aCloudNatGatewayMapper, confName));
        aliyun.put("oss", count(aCloudOssMapper, confName));
        aliyun.put("redis", count(aCloudRedisMapper, confName));
        aliyun.put("mongodb", count(aCloudMongoDbMapper, confName));
        aliyun.put("ack", count(aCloudAckMapper, confName));
        aliyun.put("kafka", count(aCloudKafkaMapper, confName));
        aliyun.put("rocketmq", count(aCloudRocketMQMapper, confName));
        aliyun.put("waf", count(aCloudWafMapper, confName));
        aliyun.put("ddos", count(aCloudDdosMapper, confName));
        aliyun.put("kms", count(aCloudKmsMapper, confName));
        aliyun.put("cdn", count(aCloudCdnMapper, confName));
        aliyun.put("es", count(aCloudElasticsearchMapper, confName));
        aliyun.put("emr", count(aCloudEmrMapper, confName));
        aliyun.put("iot", count(aCloudIoTMapper, confName));
        aliyun.put("fc", count(aCloudFcMapper, confName));
        aliyun.put("nas", count(aCloudNasMapper, confName));
        aliyun.put("acr", count(aCloudAcrMapper, confName));
        aliyun.put("cms", count(aCloudCmsMapper, confName));
        aliyun.put("actiontrail", count(aCloudActionTrailMapper, confName));
        aliyun.put("sls", count(aCloudSlsMapper, confName));

        // 汇总
        int totalH = huawei.values().stream().mapToInt(Integer::intValue).sum();
        int totalT = tencent.values().stream().mapToInt(Integer::intValue).sum();
        int totalA = aliyun.values().stream().mapToInt(Integer::intValue).sum();

        Map<String, Integer> totals = new LinkedHashMap<>();
        totals.put("huawei", totalH);
        totals.put("tencent", totalT);
        totals.put("aliyun", totalA);
        totals.put("all", totalH + totalT + totalA);

        result.put("huawei", huawei);
        result.put("tencent", tencent);
        result.put("aliyun", aliyun);
        result.put("totals", totals);

        return ResponseEntity.ok(result);
    }

    /**
     * 获取跨云对比数据（按业务域分组）
     */
    @GetMapping("/cross-cloud")
    public ResponseEntity<Map<String, Object>> getCrossCloudStats(
            @RequestParam(required = false) String confName) {
        Map<String, Object> result = new LinkedHashMap<>();

        // 计算域
        Map<String, Object> compute = new LinkedHashMap<>();
        compute.put("label", "计算");
        compute.put("huawei", Map.of("ecs", count(hCloudEcsMapper, confName), "cce", count(hCloudCceMapper, confName), "faas", count(hCloudFunctionGraphMapper, confName)));
        compute.put("tencent", Map.of("cvm", count(qCloudCvmMapper, confName), "tke", count(qCloudTKEMapper, confName), "scf", count(qCloudScfMapper, confName)));
        compute.put("aliyun", Map.of("ecs", count(aCloudEcsMapper, confName), "ack", count(aCloudAckMapper, confName), "fc", count(aCloudFcMapper, confName)));
        result.put("compute", compute);

        // 存储域
        Map<String, Object> storage = new LinkedHashMap<>();
        storage.put("label", "存储");
        storage.put("huawei", Map.of("obs", count(hCloudObsMapper, confName), "evs", count(hCloudEvsMapper, confName), "sfs", count(hCloudSfsMapper, confName)));
        storage.put("tencent", Map.of("cos", count(qCloudCosMapper, confName), "cbs", count(qCloudCbsMapper, confName), "cfs", count(qCloudCfsMapper, confName)));
        storage.put("aliyun", Map.of("oss", count(aCloudOssMapper, confName), "disk", count(aCloudDiskMapper, confName), "nas", count(aCloudNasMapper, confName)));
        result.put("storage", storage);

        // 数据库域
        Map<String, Object> database = new LinkedHashMap<>();
        database.put("label", "数据库");
        database.put("huawei", Map.of("rds", count(hCloudRdsMapper, confName), "dcs", count(hCloudDcsMapper, confName), "dds", count(hCloudDdsMapper, confName)));
        database.put("tencent", Map.of("cdb", count(qCloudCdbMapper, confName), "redis", count(qCloudRedisMapper, confName), "mongodb", count(qCloudMongoDbMapper, confName)));
        database.put("aliyun", Map.of("rds", count(aCloudRdsMapper, confName), "redis", count(aCloudRedisMapper, confName), "mongodb", count(aCloudMongoDbMapper, confName)));
        result.put("database", database);

        // 网络域
        Map<String, Object> network = new LinkedHashMap<>();
        network.put("label", "网络");
        network.put("huawei", Map.of("vpc", count(hCloudVpcMapper, confName), "elb", count(hCloudElbMapper, confName), "eip", count(hCloudEipMapper, confName), "nat", count(hCloudNatMapper, confName)));
        network.put("tencent", Map.of("vpc", count(qCloudVpcMapper, confName), "clb", count(qCloudClbMapper, confName), "eip", count(qCloudEipMapper, confName), "nat", count(qCloudNatGatewayMapper, confName)));
        network.put("aliyun", Map.of("vpc", count(aCloudVpcMapper, confName), "slb", count(aCloudSlbMapper, confName), "eip", count(aCloudEipMapper, confName), "nat", count(aCloudNatGatewayMapper, confName)));
        result.put("network", network);

        // 安全域
        Map<String, Object> security = new LinkedHashMap<>();
        security.put("label", "安全");
        security.put("huawei", Map.of("waf", count(hCloudWafMapper, confName), "antiddos", count(hCloudAntiDdosMapper, confName), "kms", count(hCloudKmsMapper, confName)));
        security.put("tencent", Map.of("waf", count(qCloudWAFMapper, confName), "ddos", count(qCloudDDoSMapper, confName), "kms", count(qCloudKMSMapper, confName)));
        security.put("aliyun", Map.of("waf", count(aCloudWafMapper, confName), "ddos", count(aCloudDdosMapper, confName), "kms", count(aCloudKmsMapper, confName)));
        result.put("security", security);

        // 中间件域
        Map<String, Object> middleware = new LinkedHashMap<>();
        middleware.put("label", "中间件");
        middleware.put("huawei", Map.of("kafka", count(hCloudKafkaMapper, confName), "rocketmq", count(hCloudRocketMqMapper, confName)));
        middleware.put("tencent", Map.of("ckafka", count(qCloudCkafkaMapper, confName), "rocketmq", count(qCloudRocketMQMapper, confName)));
        middleware.put("aliyun", Map.of("kafka", count(aCloudKafkaMapper, confName), "rocketmq", count(aCloudRocketMQMapper, confName)));
        result.put("middleware", middleware);

        // CDN 域
        Map<String, Object> cdn = new LinkedHashMap<>();
        cdn.put("label", "CDN");
        cdn.put("huawei", Map.of("cdn", count(hCloudCdnMapper, confName)));
        cdn.put("tencent", Map.of("cdn", count(qCloudCdnDomainMapper, confName)));
        cdn.put("aliyun", Map.of("cdn", count(aCloudCdnMapper, confName)));
        result.put("cdn", cdn);

        // 大数据域
        Map<String, Object> bigdata = new LinkedHashMap<>();
        bigdata.put("label", "大数据");
        bigdata.put("huawei", Map.of("es", count(hCloudCssMapper, confName), "emr", count(hCloudMrsMapper, confName)));
        bigdata.put("tencent", Map.of("es", count(qCloudESMapper, confName), "emr", count(qCloudEMRMapper, confName)));
        bigdata.put("aliyun", Map.of("es", count(aCloudElasticsearchMapper, confName), "emr", count(aCloudEmrMapper, confName)));
        result.put("bigdata", bigdata);

        // 运维域
        Map<String, Object> ops = new LinkedHashMap<>();
        ops.put("label", "运维管理");
        ops.put("huawei", Map.of("ces", count(hCloudCesMetricMapper, confName), "cts", count(hCloudCtsMapper, confName), "lts", count(hCloudLtsMapper, confName)));
        ops.put("tencent", Map.of("monitor", count(qCloudMonitorMapper, confName), "audit", count(qCloudAuditMapper, confName), "cls", count(qCloudCLSMapper, confName)));
        ops.put("aliyun", Map.of("cms", count(aCloudCmsMapper, confName), "actiontrail", count(aCloudActionTrailMapper, confName), "sls", count(aCloudSlsMapper, confName)));
        result.put("ops", ops);

        // IoT 域
        Map<String, Object> iot = new LinkedHashMap<>();
        iot.put("label", "物联网");
        iot.put("huawei", Map.of("iot", count(hCloudIotDaMapper, confName)));
        iot.put("tencent", Map.of("iot", count(qCloudIoTMapper, confName)));
        iot.put("aliyun", Map.of("iot", count(aCloudIoTMapper, confName)));
        result.put("iot", iot);

        return ResponseEntity.ok(result);
    }

    /**
     * 通用计数方法
     */
    private <T> int count(CommonMapper<T> mapper, String confName) {
        if (confName != null && !confName.isEmpty()) {
            return mapper.selectByConfName(confName).size();
        }
        return mapper.selectCount(null).intValue();
    }
}
