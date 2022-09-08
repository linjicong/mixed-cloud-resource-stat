package com.linjicong.cloud.stat.client;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import com.huaweicloud.sdk.ces.v1.model.MetricInfo;
import com.huaweicloud.sdk.ecs.v2.model.ServerDetail;
import com.linjicong.cloud.stat.dao.entity.CloudConf;
import com.linjicong.cloud.stat.dao.entity.hcloud.*;
import com.linjicong.cloud.stat.dao.mapper.CloudConfMapper;
import com.linjicong.cloud.stat.dao.mapper.hcloud.*;
import com.linjicong.cloud.stat.util.BeanUtils;
import com.obs.services.model.BucketStorageInfo;
import com.obs.services.model.BucketTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author linjicong
 * @version 1.0.0
 * @date 2022-08-03-15:42
 */
@SpringBootTest
class HCloudClientTest {

    private HCloudClient hCloudClient;

    @Resource
    private CloudConfMapper cloudConfMapper;
    @Resource
    private HCloudEcsMapper hCloudEcsMapper;
    @Resource
    private HCloudRdsMapper hCloudRdsMapper;
    @Resource
    private HCloudDcsMapper hCloudDcsMapper;
    @Resource
    private HCloudDdsMapper hCloudDdsMapper;
    @Resource
    private HCloudObsMapper hCloudObsMapper;
    @Resource
    private HCloudSfsMapper hCloudSfsMapper;
    @Resource
    private HCloudElbMapper hCloudElbMapper;
    @Resource
    private HCloudVpcMapper hCloudVpcMapper;
    @Resource
    private HCloudEvsMapper hCloudEvsMapper;
    @Resource
    private HCloudCesMetricMapper hCloudCesMetricMapper;
    @Resource
    private HCloudCesMetricDataMapper hCloudCesMetricDataMapper;
    @Resource
    private HCloudBillsFeeRecordsMapper hCloudBillsFeeRecordsMapper;
    @Resource
    private HCloudResourcesMapper hCloudResourcesMapper;
    @Resource
    private HCloudBillsMonthlyBreakDownMapper hCloudBillsMonthlyBreakDownMapper;
    @Resource
    private HCloudDnsPrivateMapper hCloudDnsPrivateMapper;
    @Resource
    private HCloudDnsPrivateRecordSetsMapper hCloudDnsPrivateRecordSetsMapper;

    @BeforeEach
    public void init(){
        CloudConf cloudConf = cloudConfMapper.selectByPrimaryKey(1);
        hCloudClient = new HCloudClient(cloudConf);
    }

    @Test
    void syncEcs() {
        List<HCloudEcs> hCloudEcs = hCloudClient.listEcs();
        hCloudEcsMapper.insertList(hCloudEcs);
    }

    @Test
    void syncRds() {
        List<HCloudRds> hCloudRds = hCloudClient.listRds();
        hCloudRdsMapper.insertList(hCloudRds);
    }

    @Test
    void syncDcs() {
        List<HCloudDcs> hCloudDcs = hCloudClient.listDcs();
        hCloudDcsMapper.insertList(hCloudDcs);
    }

    @Test
    void syncDds() {
        List<HCloudDds> hCloudDds = hCloudClient.listDds();
        hCloudDdsMapper.insertList(hCloudDds);
    }

    @Test
    void syncObs() {
        List<HCloudObs> hCloudObs = hCloudClient.listObs();
        hCloudObs.forEach(e->{
            BucketStorageInfo info = hCloudClient.listObsInfo(e.getBucketName());
            e.setObjectNum(info.getObjectNumber());
            e.setSize(info.getSize());
        });
        hCloudObsMapper.insertList(hCloudObs);
    }

    @Test
    void syncObsInfo() {
        BucketStorageInfo info = hCloudClient.listObsInfo("velero-ph-k8s19-sz");
        System.out.println(info);
    }

    @Test
    void syncSfs() {
        List<HCloudSfs> hCloudSfs = hCloudClient.listSfs();
        hCloudSfsMapper.insertList(hCloudSfs);
    }

    @Test
    void syncElb() {
        List<HCloudElb> hCloudElb = hCloudClient.listElb();
        hCloudElbMapper.insertList(hCloudElb);
    }

    @Test
    void syncVpc() {
        List<HCloudVpc> hCloudVpcs = hCloudClient.listVpc();
        hCloudVpcMapper.insertList(hCloudVpcs);
    }

    @Test
    void syncEvs() {
        List<HCloudEvs> hCloudEvs = hCloudClient.listEvs();
        hCloudEvsMapper.insertList(hCloudEvs);
    }

    @Test
    void syncCesMetric() {
        List<HCloudCesMetric> hCloudCesMetrics = hCloudClient.listCesMetric();
        hCloudCesMetricMapper.insertList(hCloudCesMetrics);
    }

    @Test
    void syncCesMetricData() {
        List<HCloudCesMetric> hCloudCesMetricList = hCloudCesMetricMapper.selectByStatDate(DateUtil.today());
        // 接口一次只能传500个指标进行查询
        List<List<HCloudCesMetric>> splitHCloudCesMetrics = ListUtil.split(hCloudCesMetricList, 500);
        Date date = new Date();

        for (List<HCloudCesMetric> hCloudCesMetrics : splitHCloudCesMetrics) {
            List<MetricInfo> metricInfos = BeanUtils.cgLibCopyList(hCloudCesMetrics, MetricInfo::new);
            List<HCloudCesMetricData> hCloudCesMetricData = hCloudClient.listCesMetricData(metricInfos,DateUtil.offsetDay(DateUtil.beginOfDay(date),-1),DateUtil.offsetDay(DateUtil.endOfDay(date),-1));
            hCloudCesMetricDataMapper.insertList(hCloudCesMetricData);
        }
    }

    @Test
    void syncCesMetricDataEcs() {
        List<HCloudCesMetric> hCloudCesMetricList = hCloudCesMetricMapper.selectByStatDate(DateUtil.today());
        // 接口一次只能传500个指标进行查询
        List<List<HCloudCesMetric>> splitHCloudCesMetrics = ListUtil.split(hCloudCesMetricList, 500);
        Date date = new Date();
        date=DateUtil.offsetMonth(date, -1);
        List<String> namespaces = Arrays.asList("SYS.ECS","AGT.ECS");
        List<String> metrics = Arrays.asList("mem_usedPercent","cpu_usage");
        for (List<HCloudCesMetric> hCloudCesMetrics : splitHCloudCesMetrics) {
            hCloudCesMetrics = hCloudCesMetrics.stream().filter(m -> metrics.contains(m.getMetricName()) && namespaces.contains(m.getNamespace())).collect(Collectors.toList());
            if(hCloudCesMetrics.size() > 0) {
                List<MetricInfo> metricInfos = BeanUtils.cgLibCopyList(hCloudCesMetrics, MetricInfo::new);
                List<HCloudCesMetricData> hCloudCesMetricData = hCloudClient.listCesMetricData(metricInfos,DateUtil.offsetDay(DateUtil.beginOfDay(date),-1),DateUtil.offsetDay(DateUtil.endOfDay(date),-1));
                hCloudCesMetricDataMapper.insertList(hCloudCesMetricData);
            }
        }
    }

    @Test
    void syncBillsFeeRecords() {
        List<HCloudBillsFeeRecords> hCloudBillsFeeRecords = hCloudClient.listBillsFeeRecords(DateUtil.format(new Date(), "yyyy-MM"));
        hCloudBillsFeeRecordsMapper.insertList(hCloudBillsFeeRecords);
    }

    @Test
    void syncBillsMonthlyBreakDown() {
        List<HCloudBillsMonthlyBreakDown> hCloudBillsMonthlyBreakDowns = hCloudClient.listBillsMonthlyBreakDown(DateUtil.format(new Date(), "yyyy-MM"));
        hCloudBillsMonthlyBreakDownMapper.insertList(hCloudBillsMonthlyBreakDowns);
    }

    @Test
    void syncResources() {
        List<HCloudResources> hCloudResources = hCloudClient.listResources();
        hCloudResourcesMapper.insertList(hCloudResources);
    }

    @Test
    void listDnsPrivate() {
        List<HCloudDnsPrivate> hCloudDnsPrivates = hCloudClient.listDnsPrivate();
        hCloudDnsPrivateMapper.insertList(hCloudDnsPrivates);
    }

    @Test
    void listDnsPrivateRecordSets() {
        List<HCloudDnsPrivateRecordSets> hCloudDnsPrivateRecordSets = hCloudClient.listDnsPrivateRecordSets();
        hCloudDnsPrivateRecordSetsMapper.insertList(hCloudDnsPrivateRecordSets);
    }

    @Test
    void selectRds() {
        HCloudEcs hCloudEcs = hCloudEcsMapper.selectByPrimaryKey(1);
        ServerDetail serverDetail = new ServerDetail();
        BeanUtil.copyProperties(hCloudEcs, serverDetail);
        System.out.println(serverDetail);
    }
    @Test
    void selectEcsByStatDate() {
        List<HCloudEcs> hCloudEcs = hCloudEcsMapper.selectByStatDate(DateUtil.today());
        System.out.println(hCloudEcs.size());
    }

    @Test
    void insertObs() {
        HCloudObs hCloudObs = new HCloudObs();
        hCloudObs.setBucketTypeEnum(BucketTypeEnum.OBJECT);
        hCloudObsMapper.insert(hCloudObs);
    }

    @Test
    void deleteEcs() {
        hCloudEcsMapper.deleteByStatDate(DateUtil.today());
    }

    @Test
    void deleteDcs() {
        hCloudDcsMapper.deleteByStatDate(DateUtil.today());
    }
}
