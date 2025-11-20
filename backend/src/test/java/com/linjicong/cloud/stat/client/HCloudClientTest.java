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
import com.obs.services.model.ObsObject;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.obs.services.model.AccessControlList.REST_CANNED_BUCKET_OWNER_FULL_CONTROL;

/**
 * @author linjicong
 * @version 1.0.0
 * @date 2022-08-03-15:42
 */
@SpringBootTest
class HCloudClientTest {

    private HCloudClient hCloudClient;

    private  CloudConf cloudConf;

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
    private HCloudBillsMonthlyBreakDownMapper hCloudBillsMonthlyBreakDownMapper;
    @Resource
    private HCloudDnsPrivateMapper hCloudDnsPrivateMapper;
    @Resource
    private HCloudDnsPrivateRecordSetsMapper hCloudDnsPrivateRecordSetsMapper;

    @Resource
    private HCloudCceMapper hCloudCceMapper;

    @Resource
    private HCloudUserMapper hCloudUserMapper;

    @Resource
    private HCloudPermanentAccessKeyMapper hCloudPermanentAccessKeyMapper;
    @Resource
    private HCloudAuthDomainMapper hCloudAuthDomainMapper;

    @Resource
    private HCloudResourceRecordDetailMapper hCloudResourceRecordDetailMapper;

    @Resource
    private HCloudResourceMapper hCloudResourceMapper;
    @BeforeEach
    public void beforeEach(){
        cloudConf = cloudConfMapper.selectById(11);
        hCloudClient = new HCloudClient(cloudConf);
    }

    @Test
    void syncEcs() {
        List<HCloudEcs> hCloudEcs = hCloudClient.listEcs();
        hCloudEcsMapper.insertBatchSomeColumn(hCloudEcs);
    }

    @Test
    void syncRds() {
        List<HCloudRds> hCloudRds = hCloudClient.listRds();
        hCloudRdsMapper.insertBatch(hCloudRds);
    }

    @Test
    void syncDcs() {
        List<HCloudDcs> hCloudDcs = hCloudClient.listDcs();
        hCloudDcsMapper.insertBatch(hCloudDcs);
    }

    @Test
    void syncDds() {
        List<HCloudDds> hCloudDds = hCloudClient.listDds();
        hCloudDdsMapper.insertBatch(hCloudDds);
    }

    @Test
    void syncObs() {
        List<HCloudObs> hCloudObs = hCloudClient.listObs();
        hCloudObs.forEach(e->{
            BucketStorageInfo info = hCloudClient.listObsInfo(e.getBucketName());
            e.setObjectNum(info.getObjectNumber());
            e.setSize(info.getSize());
        });
        hCloudObsMapper.insertBatch(hCloudObs);
    }

    @Test
    void syncObsInfo() {
        BucketStorageInfo info = hCloudClient.listObsInfo("velero-ph-k8s19-sz");
        System.out.println(info);
    }

    @Test
    void syncSfs() {
        List<HCloudSfs> hCloudSfs = hCloudClient.listSfs();
        hCloudSfsMapper.insertBatch(hCloudSfs);
    }

    @Test
    void syncElb() {
        List<HCloudElb> hCloudElb = hCloudClient.listElb();
        hCloudElbMapper.insertBatch(hCloudElb);
    }

    @Test
    void syncVpc() {
        List<HCloudVpc> hCloudVpcs = hCloudClient.listVpc();
        hCloudVpcMapper.insertBatch(hCloudVpcs);
    }

    @Test
    void syncEvs() {
        List<HCloudEvs> hCloudEvs = hCloudClient.listEvs();
        hCloudEvsMapper.insertBatch(hCloudEvs);
    }

    @Test
    void syncCesMetric() {
        List<HCloudCesMetric> hCloudCesMetrics = hCloudClient.listCesMetric();
        hCloudCesMetricMapper.insertBatch(hCloudCesMetrics);
    }

    @Test
    void syncCesMetricData() {
        List<HCloudCesMetric> hCloudCesMetricList = hCloudCesMetricMapper.selectByStatDateAndConfName(DateUtil.today(),cloudConf.getName());
        // 接口一次只能传500个指标进行查询
        List<List<HCloudCesMetric>> splitHCloudCesMetrics = ListUtil.split(hCloudCesMetricList, 500);
        Date date = new Date();

        for (List<HCloudCesMetric> hCloudCesMetrics : splitHCloudCesMetrics) {
            List<MetricInfo> metricInfos = BeanUtils.cgLibCopyList(hCloudCesMetrics, MetricInfo::new);
            List<HCloudCesMetricData> hCloudCesMetricData = hCloudClient.listCesMetricData(metricInfos,DateUtil.offsetDay(DateUtil.beginOfDay(date),-1),DateUtil.offsetDay(DateUtil.endOfDay(date),-1));
            hCloudCesMetricDataMapper.insertBatch(hCloudCesMetricData);
        }
    }

    /**
     * 同步CES监控数据到ECS
     * 该方法用于从HCloud CES（云监控服务）中同步特定指标数据到ECS（云服务器）中
     * 主要步骤包括：
     * 1. 根据当前日期和云配置名称查询待同步的监控指标列表
     * 2. 将指标列表分割成多个子列表，以满足接口查询限制
     * 3. 遍历每个子列表，过滤出需要的指标和命名空间
     * 4. 将过滤后的指标信息转换格式，并调用接口获取监控数据
     * 5. 将获取到的监控数据插入数据库
     */
    @Test
    void syncCesMetricDataEcs() {
        // 查询当天需要同步的监控指标列表
        List<HCloudCesMetric> hCloudCesMetricList = hCloudCesMetricMapper.selectByStatDateAndConfName(DateUtil.today(),cloudConf.getName());

        // 接口一次只能传500个指标进行查询，因此需要分割列表
        List<List<HCloudCesMetric>> splitHCloudCesMetrics = ListUtil.split(hCloudCesMetricList, 500);

        // 获取当前日期的前一个月日期，用于查询上个月的监控数据
        Date date = new Date();
        date=DateUtil.offsetMonth(date, -1);

        // 定义需要查询的命名空间和指标名称列表
        List<String> namespaces = Arrays.asList("SYS.ECS","AGT.ECS");
        List<String> metrics = Arrays.asList("mem_usedPercent","cpu_usage");

        // 遍历分割后的监控指标列表
        for (List<HCloudCesMetric> hCloudCesMetrics : splitHCloudCesMetrics) {
            // 过滤出需要的指标和命名空间
            hCloudCesMetrics = hCloudCesMetrics.stream().filter(m -> metrics.contains(m.getMetricName()) && namespaces.contains(m.getNamespace())).collect(Collectors.toList());

            // 如果过滤后的指标列表不为空，则获取监控数据并插入数据库
            if(hCloudCesMetrics.size() > 0) {
                // 将过滤后的指标信息转换格式
                List<MetricInfo> metricInfos = BeanUtils.cgLibCopyList(hCloudCesMetrics, MetricInfo::new);

                // 调用接口获取监控数据
                List<HCloudCesMetricData> hCloudCesMetricData = hCloudClient.listCesMetricData(metricInfos,DateUtil.offsetDay(DateUtil.beginOfDay(date),-1),DateUtil.offsetDay(DateUtil.endOfDay(date),-1));

                // 将获取到的监控数据插入数据库
                hCloudCesMetricDataMapper.insertBatch(hCloudCesMetricData);
            }
        }
    }

    @Test
    void syncBillsFeeRecords() {
        List<HCloudBillsFeeRecords> hCloudBillsFeeRecords = hCloudClient.listBillsFeeRecords(DateUtil.format(new Date(), "yyyy-MM"));
        hCloudBillsFeeRecordsMapper.insertBatch(hCloudBillsFeeRecords);
    }

    @Test
    void syncBillsMonthlyBreakDown() {
        List<HCloudBillsMonthlyBreakDown> hCloudBillsMonthlyBreakDowns = hCloudClient.listBillsMonthlyBreakDown("2023-03");
        hCloudBillsMonthlyBreakDownMapper.insertBatch(hCloudBillsMonthlyBreakDowns);
    }

    @Test
    void listDnsPrivate() {
        List<HCloudDnsPrivate> hCloudDnsPrivates = hCloudClient.listDnsPrivate();
        hCloudDnsPrivateMapper.insertBatch(hCloudDnsPrivates);
    }

    @Test
    void listDnsPrivateRecordSets() {
        List<HCloudDnsPrivateRecordSets> hCloudDnsPrivateRecordSets = hCloudClient.listDnsPrivateRecordSets();
        hCloudDnsPrivateRecordSetsMapper.insertBatch(hCloudDnsPrivateRecordSets);
    }

    @Test
    void listClusters() {
        List<HCloudCce> hCloudCces = hCloudClient.listClusters();
        hCloudCceMapper.insertBatch(hCloudCces);
    }

    @Test
    void listUsers() {
        List<HCloudUser> hCloudUsers = hCloudClient.listUsers();
        hCloudUserMapper.insertBatch(hCloudUsers);
    }
    @Test
    void selectRds() {
        HCloudEcs hCloudEcs = hCloudEcsMapper.selectById(1);
        ServerDetail serverDetail = new ServerDetail();
        BeanUtil.copyProperties(hCloudEcs, serverDetail);
        System.out.println(serverDetail);
    }
    /**
     * 根据统计日期选择ECS实例进行测试
     * 此测试用例的目的是验证根据当前日期和云配置名称查询HCloudEcs记录的功能
     * 它首先调用selectByStatDateAndConfName方法来获取当天的ECS实例列表，然后打印列表的大小
     */
    @Test
    void selectEcsByStatDate() {
        // 查询当天的ECS实例列表，并根据云配置名称进行筛选
        List<HCloudEcs> hCloudEcs = hCloudEcsMapper.selectByStatDateAndConfName(DateUtil.today(),cloudConf.getName());
        // 打印查询结果的大小
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
        hCloudEcsMapper.deleteByStatDateAndConfName(DateUtil.today(),cloudConf.getName());
    }

    @Test
    void deleteDcs() {
        hCloudDcsMapper.deleteByStatDateAndConfName(DateUtil.today(),cloudConf.getName());
    }

    @Test
    void listPermanentAccessKeys(String userId) {
        List<HCloudPermanentAccessKey> hCloudPermanentAccessKeys = hCloudClient.listPermanentAccessKeys(userId);
        hCloudPermanentAccessKeyMapper.insertBatch(hCloudPermanentAccessKeys);
    }

    @Test
    void listAllPermanentAccessKeys() {
        List<HCloudPermanentAccessKey> hCloudPermanentAccessKeys = new ArrayList<>();
        List<HCloudUser> hCloudUsers = hCloudUserMapper.selectByStatDateAndConfName("2024-12-24", cloudConf.getName());
        hCloudUsers.forEach(user->{
            hCloudPermanentAccessKeys.addAll(hCloudClient.listPermanentAccessKeys(user.getId()));
        });
        hCloudPermanentAccessKeyMapper.insertBatch(hCloudPermanentAccessKeys);
    }


    @Test
    void listAuthDomains() {
        List<HCloudAuthDomain> hCloudAuthDomains = hCloudClient.listAuthDomains();
        hCloudAuthDomainMapper.insertBatch(hCloudAuthDomains);
    }

    @Test
    void listEcs() {
    }

    @Test
    void listRds() {
    }

    @Test
    void listDcs() {
    }

    @Test
    void listDds() {
    }

    @Test
    void listObs() {
    }

    @Test
    void listObsInfo() {
    }

    @Test
    void listSfs() {
    }

    @Test
    void listElb() {
    }

    @Test
    void listVpc() {
    }

    @Test
    void listEvs() {
    }

    @Test
    void listCesMetric() {
    }

    @Test
    void listCesMetricData() {
    }

    @Test
    void listBillsFeeRecords() {
    }

    /**
     * 测试方法，用于列出资源记录详情并更新数据库
     * 此方法首先删除数据库中与当前日期和特定配置名称相关的记录，
     * 然后从hCloud客户端获取指定月份的资源记录详情，
     * 最后将获取到的数据插入数据库中
     */
    @Test
    void listResourceRecordsDetails() {
        // 删除数据库中与当前日期和特定配置名称相关的记录，避免数据重复
        hCloudResourceRecordDetailMapper.deleteByStatDateAndConfName(DateUtil.today(),cloudConf.getName());

        // 从hCloud客户端获取指定月份（这里为"2023-03"）的资源记录详情
        List<HCloudResourceRecordDetail> hCloudResourceRecordDetails = hCloudClient.listResourceRecordsDetails("2023-03");

        // 将获取到的资源记录详情批量插入数据库中
        hCloudResourceRecordDetailMapper.insertBatch(hCloudResourceRecordDetails);
    }

    @Test
    void listBillsMonthlyBreakDown() {
    }

    @Test
    void listResources() {
    }

    @Test
    void listAllResources() {
        hCloudResourceMapper.deleteByStatDateAndConfName(DateUtil.today(),cloudConf.getName());
        List<HCloudResource> HCloudResource = hCloudClient.listAllResources();
        hCloudResourceMapper.insertBatch(HCloudResource);
    }

    @Test
    void listObsObjects() {
        List<ObsObject> obsObjectList = hCloudClient.listObsObjects("xxx");
    }

    @Test
    void getObsObjectAcl() {
    }

    @Test
    void setObsObjectAcl() {
        hCloudClient.setObsObjectAcl("xxx","xxx",REST_CANNED_BUCKET_OWNER_FULL_CONTROL);
    }
}
