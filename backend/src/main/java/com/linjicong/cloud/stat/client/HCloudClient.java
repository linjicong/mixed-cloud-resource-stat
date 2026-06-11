/*
 * MIT License
 *
 * Copyright (c) 2022 linjicong
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.linjicong.cloud.stat.client;

import cn.hutool.core.util.StrUtil;
import com.huaweicloud.sdk.bss.v2.BssClient;
import com.huaweicloud.sdk.bss.v2.model.*;
import com.huaweicloud.sdk.bss.v2.region.BssRegion;
import com.huaweicloud.sdk.cce.v3.CceClient;
import com.huaweicloud.sdk.cce.v3.model.ListClustersRequest;
import com.huaweicloud.sdk.cce.v3.region.CceRegion;
import com.huaweicloud.sdk.ces.v1.CesClient;
import com.huaweicloud.sdk.ces.v1.model.*;
import com.huaweicloud.sdk.ces.v1.region.CesRegion;
import com.huaweicloud.sdk.core.auth.BasicCredentials;
import com.huaweicloud.sdk.core.auth.GlobalCredentials;
import com.huaweicloud.sdk.core.auth.ICredential;
import com.huaweicloud.sdk.dcs.v2.DcsClient;
import com.huaweicloud.sdk.dcs.v2.region.DcsRegion;
import com.huaweicloud.sdk.dds.v3.DdsClient;
import com.huaweicloud.sdk.dds.v3.region.DdsRegion;
import com.huaweicloud.sdk.dns.v2.DnsClient;
import com.huaweicloud.sdk.dns.v2.model.ListPrivateZonesRequest;
import com.huaweicloud.sdk.dns.v2.model.ListRecordSetsRequest;
import com.huaweicloud.sdk.dns.v2.region.DnsRegion;
import com.huaweicloud.sdk.eip.v2.EipClient;
import com.huaweicloud.sdk.eip.v2.model.ListPublicipsRequest;
import com.huaweicloud.sdk.eip.v2.region.EipRegion;
import com.huaweicloud.sdk.ecs.v2.EcsClient;
import com.huaweicloud.sdk.ecs.v2.model.ListServersDetailsRequest;
import com.huaweicloud.sdk.ecs.v2.region.EcsRegion;
import com.huaweicloud.sdk.elb.v3.ElbClient;
import com.huaweicloud.sdk.elb.v3.model.ListLoadBalancersRequest;
import com.huaweicloud.sdk.elb.v3.region.ElbRegion;
import com.huaweicloud.sdk.evs.v2.EvsClient;
import com.huaweicloud.sdk.evs.v2.model.ListVolumesRequest;
import com.huaweicloud.sdk.evs.v2.region.EvsRegion;
import com.huaweicloud.sdk.iam.v3.IamClient;
import com.huaweicloud.sdk.iam.v3.model.*;
import com.huaweicloud.sdk.iam.v3.region.IamRegion;
import com.huaweicloud.sdk.ims.v2.ImsClient;
import com.huaweicloud.sdk.ims.v2.model.ListImagesRequest;
import com.huaweicloud.sdk.ims.v2.region.ImsRegion;
import com.huaweicloud.sdk.cbr.v1.CbrClient;
import com.huaweicloud.sdk.cbr.v1.model.ListBackupsRequest;
import com.huaweicloud.sdk.cbr.v1.region.CbrRegion;
import com.huaweicloud.sdk.antiddos.v2.AntiDDoSClient;
import com.huaweicloud.sdk.antiddos.v2.region.AntiDDoSRegion;
import com.huaweicloud.sdk.aom.v2.AomClient;
import com.huaweicloud.sdk.aom.v2.region.AomRegion;
import com.huaweicloud.sdk.apig.v2.ApigClient;
import com.huaweicloud.sdk.apig.v2.region.ApigRegion;
import com.huaweicloud.sdk.as.v1.AsClient;
import com.huaweicloud.sdk.as.v1.region.AsRegion;
import com.huaweicloud.sdk.bcs.v2.BcsClient;
import com.huaweicloud.sdk.bcs.v2.region.BcsRegion;
import com.huaweicloud.sdk.bms.v1.BmsClient;
import com.huaweicloud.sdk.bms.v1.region.BmsRegion;
import com.huaweicloud.sdk.ccm.v1.CcmClient;
import com.huaweicloud.sdk.ccm.v1.region.CcmRegion;
import com.huaweicloud.sdk.cdn.v2.CdnClient;
import com.huaweicloud.sdk.cdn.v2.region.CdnRegion;
import com.huaweicloud.sdk.cfw.v1.CfwClient;
import com.huaweicloud.sdk.cfw.v1.region.CfwRegion;
import com.huaweicloud.sdk.cph.v1.CphClient;
import com.huaweicloud.sdk.cph.v1.region.CphRegion;
import com.huaweicloud.sdk.cse.v1.CseClient;
import com.huaweicloud.sdk.cse.v1.region.CseRegion;
import com.huaweicloud.sdk.css.v1.CssClient;
import com.huaweicloud.sdk.css.v1.region.CssRegion;
import com.huaweicloud.sdk.cts.v3.CtsClient;
import com.huaweicloud.sdk.cts.v3.region.CtsRegion;
import com.huaweicloud.sdk.dbss.v1.DbssClient;
import com.huaweicloud.sdk.dbss.v1.region.DbssRegion;
import com.huaweicloud.sdk.ddm.v1.DdmClient;
import com.huaweicloud.sdk.ddm.v1.region.DdmRegion;
import com.huaweicloud.sdk.deh.v1.DeHClient;
import com.huaweicloud.sdk.deh.v1.region.DeHRegion;
import com.huaweicloud.sdk.dli.v1.DliClient;
import com.huaweicloud.sdk.dli.v1.region.DliRegion;
import com.huaweicloud.sdk.drs.v5.DrsClient;
import com.huaweicloud.sdk.drs.v5.region.DrsRegion;
import com.huaweicloud.sdk.dsc.v1.DscClient;
import com.huaweicloud.sdk.dsc.v1.region.DscRegion;
import com.huaweicloud.sdk.dws.v2.DwsClient;
import com.huaweicloud.sdk.dws.v2.region.DwsRegion;
import com.huaweicloud.sdk.er.v3.ErClient;
import com.huaweicloud.sdk.er.v3.region.ErRegion;
import com.huaweicloud.sdk.functiongraph.v2.FunctionGraphClient;
import com.huaweicloud.sdk.functiongraph.v2.region.FunctionGraphRegion;
import com.huaweicloud.sdk.gaussdb.v3.GaussDBClient;
import com.huaweicloud.sdk.gaussdb.v3.region.GaussDBRegion;
import com.huaweicloud.sdk.gaussdbfornosql.v3.GaussDBforNoSQLClient;
import com.huaweicloud.sdk.gaussdbfornosql.v3.region.GaussDBforNoSQLRegion;
import com.huaweicloud.sdk.gaussdbforopengauss.v3.GaussDBforopenGaussClient;
import com.huaweicloud.sdk.gaussdbforopengauss.v3.region.GaussDBforopenGaussRegion;
import com.huaweicloud.sdk.hss.v5.HssClient;
import com.huaweicloud.sdk.hss.v5.region.HssRegion;
import com.huaweicloud.sdk.ief.v1.IefClient;
import com.huaweicloud.sdk.ief.v1.region.IefRegion;
import com.huaweicloud.sdk.iotda.v5.IoTDAClient;
import com.huaweicloud.sdk.iotda.v5.region.IoTDARegion;
import com.huaweicloud.sdk.kafka.v2.KafkaClient;
import com.huaweicloud.sdk.kafka.v2.region.KafkaRegion;
import com.huaweicloud.sdk.kms.v2.KmsClient;
import com.huaweicloud.sdk.kms.v2.region.KmsRegion;
import com.huaweicloud.sdk.live.v1.LiveClient;
import com.huaweicloud.sdk.live.v1.region.LiveRegion;
import com.huaweicloud.sdk.lts.v2.LtsClient;
import com.huaweicloud.sdk.lts.v2.region.LtsRegion;
import com.huaweicloud.sdk.mrs.v1.MrsClient;
import com.huaweicloud.sdk.mrs.v1.region.MrsRegion;
import com.huaweicloud.sdk.nat.v2.NatClient;
import com.huaweicloud.sdk.nat.v2.region.NatRegion;
import com.huaweicloud.sdk.oms.v2.OmsClient;
import com.huaweicloud.sdk.oms.v2.region.OmsRegion;
import com.huaweicloud.sdk.rabbitmq.v2.RabbitMQClient;
import com.huaweicloud.sdk.rabbitmq.v2.region.RabbitMQRegion;
import com.huaweicloud.sdk.rocketmq.v2.RocketMQClient;
import com.huaweicloud.sdk.rocketmq.v2.region.RocketMQRegion;
import com.huaweicloud.sdk.roma.v2.RomaClient;
import com.huaweicloud.sdk.roma.v2.region.RomaRegion;
import com.huaweicloud.sdk.sdrs.v1.SdrsClient;
import com.huaweicloud.sdk.sdrs.v1.region.SdrsRegion;
import com.huaweicloud.sdk.servicestage.v2.ServiceStageClient;
import com.huaweicloud.sdk.servicestage.v2.region.ServiceStageRegion;
import com.huaweicloud.sdk.smn.v2.SmnClient;
import com.huaweicloud.sdk.smn.v2.region.SmnRegion;
import com.huaweicloud.sdk.sms.v3.SmsClient;
import com.huaweicloud.sdk.sms.v3.region.SmsRegion;
import com.huaweicloud.sdk.swr.v2.SwrClient;
import com.huaweicloud.sdk.swr.v2.region.SwrRegion;
import com.huaweicloud.sdk.vod.v1.VodClient;
import com.huaweicloud.sdk.vod.v1.region.VodRegion;
import com.huaweicloud.sdk.vpcep.v1.VpcepClient;
import com.huaweicloud.sdk.vpcep.v1.region.VpcepRegion;
import com.huaweicloud.sdk.vpn.v5.VpnClient;
import com.huaweicloud.sdk.vpn.v5.region.VpnRegion;
import com.huaweicloud.sdk.waf.v1.WafClient;
import com.huaweicloud.sdk.waf.v1.region.WafRegion;
import com.huaweicloud.sdk.workspace.v2.WorkspaceClient;
import com.huaweicloud.sdk.workspace.v2.region.WorkspaceRegion;
import com.huaweicloud.sdk.rds.v3.RdsClient;
import com.huaweicloud.sdk.rds.v3.model.ListInstancesRequest;
import com.huaweicloud.sdk.rds.v3.region.RdsRegion;
import com.huaweicloud.sdk.rms.v1.RmsClient;
import com.huaweicloud.sdk.rms.v1.model.ListAllResourcesRequest;
import com.huaweicloud.sdk.rms.v1.model.ListAllResourcesResponse;
import com.huaweicloud.sdk.rms.v1.model.ResourceEntity;
import com.huaweicloud.sdk.rms.v1.region.RmsRegion;
import com.huaweicloud.sdk.sfsturbo.v1.SFSTurboClient;
import com.huaweicloud.sdk.sfsturbo.v1.model.ListSharesRequest;
import com.huaweicloud.sdk.sfsturbo.v1.region.SFSTurboRegion;
import com.huaweicloud.sdk.vpc.v3.VpcClient;
import com.huaweicloud.sdk.vpc.v3.model.ListVpcsRequest;
import com.huaweicloud.sdk.vpc.v3.region.VpcRegion;
import com.linjicong.cloud.stat.dao.constant.hcloud.ObsEndpoint;
import com.linjicong.cloud.stat.dao.entity.BasicEntityExtend;
import com.linjicong.cloud.stat.dao.entity.CloudConf;
import com.linjicong.cloud.stat.dao.entity.hcloud.*;
import com.linjicong.cloud.stat.util.BeanUtils;
import com.linjicong.cloud.stat.util.ThreadLocalUtil;
import com.obs.services.ObsClient;
import com.obs.services.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 华为云客户端
 * 用于调用华为云API获取资源信息
 * 支持ECS、RDS、ELB、EVS、VPC、OBS等多种资源类型
 * 
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
public class HCloudClient{

    /** 区域级凭证，用于区域级服务 */
    private final ICredential auth;

    /** 全局凭证，用于全局服务（如IAM） */
    private final ICredential globalAuth;

    /** OBS客户端，用于对象存储服务 */
    private final ObsClient obsClient;

    /** 区域 */
    private final String region;

    /**
     * 构造华为云客户端
     * 初始化凭证、OBS客户端等
     * 
     * @param cloudConf 云配置信息，包含访问密钥、区域等
     */
    public HCloudClient(CloudConf cloudConf) {
        String accessKey = cloudConf.getAccessKey();
        String secretKey = cloudConf.getSecretKey();
        String name = cloudConf.getName();
        String provider = cloudConf.getProvider();
        this.region = cloudConf.getRegion();
        // 将扩展信息存入ThreadLocal，供MyBatis拦截器使用，用于自动填充公共字段
        BasicEntityExtend entityExtend = new BasicEntityExtend(name, provider, region);
        ThreadLocalUtil.put("entityExtend", entityExtend);

        this.auth = new BasicCredentials()
                .withAk(accessKey)
                .withSk(secretKey);
        this.globalAuth = new GlobalCredentials()
                .withAk(accessKey)
                .withSk(secretKey);
        this.obsClient = new ObsClient(accessKey, secretKey, ObsEndpoint.CN_SOUTH_1);
    }

    /**
     * 华为云-弹性云服务器列表
     */
    public List<HCloudEcs> listEcs() {
        EcsClient client = EcsClient.newBuilder()
                .withCredential(auth)
                .withRegion(EcsRegion.valueOf(region))
                .build();

        return BeanUtils.cgLibCopyList(client.listServersDetails(new ListServersDetailsRequest().withLimit(1000)).getServers(), HCloudEcs::new);
    }

    /**
     * 华为云-云数据库列表
     */
    public List<HCloudRds> listRds() {
        RdsClient client = RdsClient.newBuilder()
                .withCredential(auth)
                .withRegion(RdsRegion.valueOf(region))
                .build();

        return BeanUtils.cgLibCopyList(client.listInstances(new ListInstancesRequest().withLimit(100)).getInstances(),HCloudRds::new);
    }

    /**
     * 华为云-分布式缓存数据库列表
     */
    public List<HCloudDcs> listDcs() {
        DcsClient client = DcsClient.newBuilder()
                .withCredential(auth)
                .withRegion(DcsRegion.valueOf(region))
                .build();

        return BeanUtils.cgLibCopyList(client.listInstances(new com.huaweicloud.sdk.dcs.v2.model.ListInstancesRequest().withLimit(1000)).getInstances(),HCloudDcs::new);

    }

    /**
     * 华为云-文档数据库列表
     */
    public List<HCloudDds> listDds() {
        DdsClient client = DdsClient.newBuilder()
                .withCredential(auth)
                .withRegion(DdsRegion.valueOf(region))
                .build();

        return BeanUtils.cgLibCopyList(client.listInstances(new com.huaweicloud.sdk.dds.v3.model.ListInstancesRequest().withLimit(100)).getInstances(),HCloudDds::new);
    }

    /**
     * 华为云-对象存储列表
     */
    public List<HCloudObs> listObs() {
        return BeanUtils.cgLibCopyList(obsClient.listBucketsV2(new ListBucketsRequest()).getBuckets(),HCloudObs::new);
    }

    /**
     * 华为云-对象存储详情
     */
    public BucketStorageInfo listObsInfo(String bucketName) {
        return obsClient.getBucketStorageInfo(bucketName);
    }

    public List<ObsObject> listObsObjects(String bucketName) {
        ListObjectsRequest request = new ListObjectsRequest();
        request.setBucketName(bucketName);
        ObjectListing objectListing = obsClient.listObjects(request);
        List<ObsObject> obsObjects = new ArrayList<>(objectListing.getObjects());
        while (objectListing.isTruncated()) {
            request.setMarker(objectListing.getNextMarker());
            objectListing = obsClient.listObjects(request);
            obsObjects.addAll(objectListing.getObjects());
        }
        return obsObjects;
    }

    public AccessControlList getObsObjectAcl(String bucketName,String objectKey) {
        return obsClient.getObjectAcl(bucketName,objectKey);
    }

    public void setObsObjectAcl(String bucketName,String objectKey,AccessControlList acl) {
        SetObjectAclRequest request =new SetObjectAclRequest(bucketName,objectKey,acl);
        obsClient.setObjectAcl(request);
    }
    /**
     * 华为云-文件存储
     */
    public List<HCloudSfs> listSfs() {
        SFSTurboClient client = SFSTurboClient.newBuilder()
                .withCredential(auth)
                .withRegion(SFSTurboRegion.valueOf(region))
                .build();

        return BeanUtils.cgLibCopyList(client.listShares(new ListSharesRequest().withLimit(200)).getShares(),HCloudSfs::new);
    }

    /**
     * 华为云-负载均衡
     */
    public List<HCloudElb> listElb() {
        ElbClient client = ElbClient.newBuilder()
                .withCredential(auth)
                .withRegion(ElbRegion.valueOf(region))
                .build();

        return BeanUtils.cgLibCopyList(client.listLoadBalancers(new ListLoadBalancersRequest().withLimit(200)).getLoadbalancers(), HCloudElb::new);
    }

    /**
     * 华为云-虚拟私有云
     */
    public List<HCloudVpc> listVpc() {
        VpcClient client = VpcClient.newBuilder()
                .withCredential(auth)
                .withRegion(VpcRegion.valueOf(region))
                .build();

        return BeanUtils.cgLibCopyList(client.listVpcs(new ListVpcsRequest().withLimit(200)).getVpcs(), HCloudVpc::new);
    }

    /**
     * 华为云-弹性公网IP
     */
    public List<HCloudEip> listEip() {
        EipClient client = EipClient.newBuilder()
                .withCredential(auth)
                .withRegion(EipRegion.valueOf(region))
                .build();

        return BeanUtils.cgLibCopyList(client.listPublicips(new ListPublicipsRequest().withLimit(200)).getPublicips(), HCloudEip::new);
    }

    /**
     * 华为云-云硬盘
     */
    public List<HCloudEvs> listEvs() {
        EvsClient client = EvsClient.newBuilder()
                .withCredential(auth)
                .withRegion(EvsRegion.valueOf(region))
                .build();

        return BeanUtils.cgLibCopyList(client.listVolumes(new ListVolumesRequest().withLimit(200)).getVolumes(), HCloudEvs::new);
    }

    /**
     * 华为云-监控指标
     */
    public List<HCloudCesMetric> listCesMetric() {
        CesClient client = CesClient.newBuilder()
                .withCredential(auth)
                .withRegion(CesRegion.valueOf(region))
                .build();

        ListMetricsResponse listMetricsResponse = client.listMetrics(new ListMetricsRequest());
        String start = listMetricsResponse.getMetaData().getMarker();
        List<MetricInfoList> metrics = listMetricsResponse.getMetrics();
        while (StrUtil.isNotBlank(start)){
            ListMetricsResponse listMetricsResponse1 = client.listMetrics(new ListMetricsRequest().withStart(start));
            start = listMetricsResponse1.getMetaData().getMarker();
            metrics.addAll(listMetricsResponse1.getMetrics());
        }
        return BeanUtils.cgLibCopyList(metrics, HCloudCesMetric::new);
    }

    /**
     * 华为云-监控数据
     */
    public List<HCloudCesMetricData> listCesMetricData(List<MetricInfo> metricInfos,Date start,Date end) {
        CesClient client = CesClient.newBuilder()
                .withCredential(auth)
                .withRegion(CesRegion.valueOf(region))
                .build();

        BatchListMetricDataRequest batchListMetricDataRequest = new BatchListMetricDataRequest();
        BatchListMetricDataRequestBody batchListMetricDataRequestBody = new BatchListMetricDataRequestBody();
        batchListMetricDataRequestBody.setMetrics(metricInfos);
        batchListMetricDataRequestBody.setPeriod("14400"); //查询1天的数据:500个指标最多设置间隔4小时,10个指标间隔5分钟
        batchListMetricDataRequestBody.setFilter("average");
        batchListMetricDataRequestBody.setFrom(start.getTime());
        batchListMetricDataRequestBody.setTo(end.getTime());
        batchListMetricDataRequest.setBody(batchListMetricDataRequestBody);
        return BeanUtils.cgLibCopyList(client.batchListMetricData(batchListMetricDataRequest).getMetrics(), HCloudCesMetricData::new);
    }

    /**
     * 华为云-查询流水账单
     */
    public List<HCloudBillsFeeRecords> listBillsFeeRecords(String billCycle) {
        BssClient client = BssClient.newBuilder()
                .withCredential(globalAuth)
                .withRegion(BssRegion.CN_NORTH_1)
                .build();
        ListCustomerBillsFeeRecordsRequest request = new ListCustomerBillsFeeRecordsRequest().withLimit(1000).withBillCycle(billCycle);
        ListCustomerBillsFeeRecordsResponse response = client.listCustomerBillsFeeRecords(request);
        List<MonthlyBillRecord> records = response.getRecords();
        while (records.size() % 1000 == 0){
            ListCustomerBillsFeeRecordsResponse responseNext = client.listCustomerBillsFeeRecords(request.withOffset(records.size()));
            records.addAll(responseNext.getRecords());
        }
        return BeanUtils.cgLibCopyList(records, HCloudBillsFeeRecords::new);
    }

    /**
     * 华为云-查询流水账单
     */
    public List<HCloudResourceRecordDetail> listResourceRecordsDetails(String cycle) {
        BssClient client = BssClient.newBuilder()
                .withCredential(globalAuth)
                .withRegion(BssRegion.CN_NORTH_1)
                .build();
        ListCustomerselfResourceRecordDetailsRequest request = new ListCustomerselfResourceRecordDetailsRequest().withBody(b->b.withLimit(1000).withCycle(cycle));
        ListCustomerselfResourceRecordDetailsResponse response = client.listCustomerselfResourceRecordDetails(request);
        List<MonthlyBillRes> records = response.getMonthlyRecords();
        while (records.size() % 1000 == 0){
            ListCustomerselfResourceRecordDetailsResponse responseNext = client.listCustomerselfResourceRecordDetails(request.withBody(request.getBody().withOffset(records.size())));
            records.addAll(responseNext.getMonthlyRecords());
        }
        return BeanUtils.cgLibCopyList(records, HCloudResourceRecordDetail::new);
    }

    /**
     * 华为云-查询月度成本
     */
    public List<HCloudBillsMonthlyBreakDown> listBillsMonthlyBreakDown(String shardMonth) {
        BssClient client = BssClient.newBuilder()
                .withCredential(globalAuth)
                .withRegion(BssRegion.CN_NORTH_1)
                .build();

        ListCustomerBillsMonthlyBreakDownRequest request = new ListCustomerBillsMonthlyBreakDownRequest().withLimit(1000).withSharedMonth(shardMonth);

        ListCustomerBillsMonthlyBreakDownResponse response = client.listCustomerBillsMonthlyBreakDown(request);
        List<NvlCostAnalysedBillDetail> details = response.getDetails();
        while (details.size() % 1000 ==0){
            ListCustomerBillsMonthlyBreakDownResponse responseNext = client.listCustomerBillsMonthlyBreakDown(request.withOffset(details.size()));
            details.addAll(responseNext.getDetails());
        }
        return BeanUtils.cgLibCopyList(details, HCloudBillsMonthlyBreakDown::new);
    }

    /**
     * 华为云-列举所有资源
     * @see RmsClient#listAllResources(com.huaweicloud.sdk.rms.v1.model.ListAllResourcesRequest)
     */
    public List<HCloudResource> listAllResources() {
        RmsClient client = RmsClient.newBuilder()
                .withCredential(globalAuth)
                .withRegion(RmsRegion.CN_NORTH_4)
                .build();
        ListAllResourcesRequest listAllResourcesRequest = new ListAllResourcesRequest();
        listAllResourcesRequest.withLimit(200);

        ListAllResourcesResponse listAllResourcesResponse = client.listAllResources(listAllResourcesRequest);
        List<ResourceEntity> resources = listAllResourcesResponse.getResources();
        String nextMarker = listAllResourcesResponse.getPageInfo().getNextMarker();

        while (StrUtil.isNotBlank(nextMarker)){
            ListAllResourcesResponse listAllResourcesResponseNext = client.listAllResources(listAllResourcesRequest.withMarker(nextMarker));
            nextMarker = listAllResourcesResponseNext.getPageInfo().getNextMarker();
            resources.addAll(listAllResourcesResponseNext.getResources());
        }
        return BeanUtils.cgLibCopyList(resources, HCloudResource::new);
    }

    /**
     * 华为云-查询内网Zone的列表
     */
    public List<HCloudDnsPrivate> listDnsPrivate() {
        DnsClient client = DnsClient.newBuilder()
                .withCredential(auth)
                .withRegion(DnsRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listPrivateZones(new ListPrivateZonesRequest().withLimit(500).withType("private")).getZones(), HCloudDnsPrivate::new);
    }

    /**
     * 华为云-查询租户Record Set资源列表
     */
    public List<HCloudDnsPrivateRecordSets> listDnsPrivateRecordSets() {
        DnsClient client = DnsClient.newBuilder()
                .withCredential(auth)
                .withRegion(DnsRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listRecordSets(new ListRecordSetsRequest().withLimit(500).withZoneType("private")).getRecordsets(), HCloudDnsPrivateRecordSets::new);
    }

    /**
     * 华为云-查询CCE列表
     */
    public List<HCloudCce> listClusters() {
        CceClient client = CceClient.newBuilder()
                .withCredential(auth)
                .withRegion(CceRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listClusters(new ListClustersRequest()).getItems(), HCloudCce::new);
    }

    /**
     * 华为云-查询用户列表
     */
    public List<HCloudAuthDomain> listAuthDomains() {
        IamClient client = IamClient.newBuilder()
                .withCredential(globalAuth)
                .withRegion(IamRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.keystoneListAuthDomains(new KeystoneListAuthDomainsRequest()).getDomains(), HCloudAuthDomain::new);
    }

    /**
     * 华为云-查询用户列表
     */
    public List<HCloudUser> listUsers() {
        IamClient client = IamClient.newBuilder()
                .withCredential(globalAuth)
                .withRegion(IamRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.keystoneListUsers(new KeystoneListUsersRequest()).getUsers(), HCloudUser::new);
    }

    /**
     * 华为云-查询永久访问密钥列表
     */
    public List<HCloudPermanentAccessKey> listPermanentAccessKeys(String userId) {
        IamClient client = IamClient.newBuilder()
                .withCredential(globalAuth)
                .withRegion(IamRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listPermanentAccessKeys(new ListPermanentAccessKeysRequest().withUserId(userId)).getCredentials(), HCloudPermanentAccessKey::new);
    }

    /**
     * 华为云-新增永久访问密钥列表
     */
    public List<HCloudPermanentAccessKey> createPermanentAccessKeys(String userId) {
        IamClient client = IamClient.newBuilder()
                .withCredential(globalAuth)
                .withRegion(IamRegion.valueOf(region))
                .build();
        CreatePermanentAccessKeyRequest createPermanentAccessKeyRequest = new CreatePermanentAccessKeyRequest();
        createPermanentAccessKeyRequest.setBody(new CreatePermanentAccessKeyRequestBody().withCredential(new CreateCredentialOption().withUserId("access_key")));
        CreatePermanentAccessKeyResponse permanentAccessKey = client.createPermanentAccessKey(createPermanentAccessKeyRequest);
        return BeanUtils.cgLibCopyList(client.listPermanentAccessKeys(new ListPermanentAccessKeysRequest()).getCredentials(), HCloudPermanentAccessKey::new);
    }

    /**
     * 华为云-查询镜像列表
     */
    public List<HCloudIms> listImages() {
        ImsClient client = ImsClient.newBuilder()
                .withCredential(auth)
                .withRegion(ImsRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listImages(new ListImagesRequest().withLimit(1000)).getImages(), HCloudIms::new);
    }

    /**
     * 华为云-查询备份列表
     */
    public List<HCloudCbr> listBackups() {
        CbrClient client = CbrClient.newBuilder()
                .withCredential(auth)
                .withRegion(CbrRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listBackups(new ListBackupsRequest().withLimit(1000)).getBackups(), HCloudCbr::new);
    }

    /**
     * 华为云-nat
     */
    public List<HCloudNat> listNat() {
        NatClient client = NatClient.newBuilder()
                .withCredential(auth)
                .withRegion(NatRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listNatGateways(new com.huaweicloud.sdk.nat.v2.model.ListNatGatewaysRequest()).getNatGateways(), HCloudNat::new);
    }

    /**
     * 华为云-functiongraph
     */
    public List<HCloudFunctionGraph> listFunctionGraph() {
        FunctionGraphClient client = FunctionGraphClient.newBuilder()
                .withCredential(auth)
                .withRegion(FunctionGraphRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listFunctions(new com.huaweicloud.sdk.functiongraph.v2.model.ListFunctionsRequest().withMaxitems("1000")).getFunctions(), HCloudFunctionGraph::new);
    }

    /**
     * 华为云-vpn
     */
    public List<HCloudVpn> listVpn() {
        VpnClient client = VpnClient.newBuilder()
                .withCredential(auth)
                .withRegion(VpnRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listVgws(new com.huaweicloud.sdk.vpn.v5.model.ListVgwsRequest()).getVpnGateways(), HCloudVpn::new);
    }

    /**
     * 华为云-gaussdb
     */
    public List<HCloudGaussDb> listGaussDb() {
        GaussDBClient client = GaussDBClient.newBuilder()
                .withCredential(auth)
                .withRegion(GaussDBRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listGaussMySqlInstances(new com.huaweicloud.sdk.gaussdb.v3.model.ListGaussMySqlInstancesRequest().withLimit(100)).getInstances(), HCloudGaussDb::new);
    }

    /**
     * 华为云-kms
     */
    public List<HCloudKms> listKms() {
        KmsClient client = KmsClient.newBuilder()
                .withCredential(auth)
                .withRegion(KmsRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listKeys(new com.huaweicloud.sdk.kms.v2.model.ListKeysRequest()).getKeyDetails(), HCloudKms::new);
    }

    /**
     * 华为云-waf
     */
    public List<HCloudWaf> listWaf() {
        WafClient client = WafClient.newBuilder()
                .withCredential(auth)
                .withRegion(WafRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listHost(new com.huaweicloud.sdk.waf.v1.model.ListHostRequest()).getItems(), HCloudWaf::new);
    }

    /**
     * 华为云-cts
     */
    public List<HCloudCts> listCts() {
        CtsClient client = CtsClient.newBuilder()
                .withCredential(auth)
                .withRegion(CtsRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listTrackers(new com.huaweicloud.sdk.cts.v3.model.ListTrackersRequest()).getTrackers(), HCloudCts::new);
    }

    /**
     * 华为云-kafka
     */
    public List<HCloudKafka> listKafka() {
        KafkaClient client = KafkaClient.newBuilder()
                .withCredential(auth)
                .withRegion(KafkaRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listInstances(new com.huaweicloud.sdk.kafka.v2.model.ListInstancesRequest().withLimit("100")).getInstances(), HCloudKafka::new);
    }

    /**
     * 华为云-rocketmq
     */
    public List<HCloudRocketMq> listRocketMq() {
        RocketMQClient client = RocketMQClient.newBuilder()
                .withCredential(auth)
                .withRegion(RocketMQRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listInstances(new com.huaweicloud.sdk.rocketmq.v2.model.ListInstancesRequest().withLimit(100)).getInstances(), HCloudRocketMq::new);
    }

    /**
     * 华为云-rabbitmq
     */
    public List<HCloudRabbitMq> listRabbitMq() {
        RabbitMQClient client = RabbitMQClient.newBuilder()
                .withCredential(auth)
                .withRegion(RabbitMQRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listInstancesDetails(new com.huaweicloud.sdk.rabbitmq.v2.model.ListInstancesDetailsRequest()).getInstances(), HCloudRabbitMq::new);
    }

    /**
     * 华为云-lts
     */
    public List<HCloudLts> listLts() {
        LtsClient client = LtsClient.newBuilder()
                .withCredential(auth)
                .withRegion(LtsRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listLogGroups(new com.huaweicloud.sdk.lts.v2.model.ListLogGroupsRequest()).getLogGroups(), HCloudLts::new);
    }

    /**
     * 华为云-cdn
     */
    public List<HCloudCdn> listCdn() {
        CdnClient client = CdnClient.newBuilder()
                .withCredential(auth)
                .withRegion(CdnRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listDomains(new com.huaweicloud.sdk.cdn.v2.model.ListDomainsRequest()).getDomains(), HCloudCdn::new);
    }

    /**
     * 华为云-antiddos
     */
    public List<HCloudAntiDdos> listAntiDdos() {
        AntiDDoSClient client = AntiDDoSClient.newBuilder()
                .withCredential(auth)
                .withRegion(AntiDDoSRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listDDosStatus(new com.huaweicloud.sdk.antiddos.v2.model.ListDDosStatusRequest()).getDdosStatus(), HCloudAntiDdos::new);
    }

    /**
     * 华为云-hss
     */
    public List<HCloudHss> listHss() {
        HssClient client = HssClient.newBuilder()
                .withCredential(auth)
                .withRegion(HssRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listHostStatus(new com.huaweicloud.sdk.hss.v5.model.ListHostStatusRequest().withLimit(200)).getDataList(), HCloudHss::new);
    }

    /**
     * 华为云-swr
     */
    public List<HCloudSwr> listSwr() {
        SwrClient client = SwrClient.newBuilder()
                .withCredential(auth)
                .withRegion(SwrRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listNamespaces(new com.huaweicloud.sdk.swr.v2.model.ListNamespacesRequest()).getNamespaces(), HCloudSwr::new);
    }

    /**
     * 华为云-smn
     */
    public List<HCloudSmn> listSmn() {
        SmnClient client = SmnClient.newBuilder()
                .withCredential(auth)
                .withRegion(SmnRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listTopics(new com.huaweicloud.sdk.smn.v2.model.ListTopicsRequest()).getTopics(), HCloudSmn::new);
    }

    /**
     * 华为云-apig
     */
    public List<HCloudApig> listApig() {
        ApigClient client = ApigClient.newBuilder()
                .withCredential(auth)
                .withRegion(ApigRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listInstancesV2(new com.huaweicloud.sdk.apig.v2.model.ListInstancesV2Request()).getInstances(), HCloudApig::new);
    }

    /**
     * 华为云-aom
     */
    public List<HCloudAom> listAom() {
        AomClient client = AomClient.newBuilder()
                .withCredential(auth)
                .withRegion(AomRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listAlarmRule(new com.huaweicloud.sdk.aom.v2.model.ListAlarmRuleRequest()).getThresholds(), HCloudAom::new);
    }

    /**
     * 华为云-css
     */
    public List<HCloudCss> listCss() {
        CssClient client = CssClient.newBuilder()
                .withCredential(auth)
                .withRegion(CssRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listClustersDetails(new com.huaweicloud.sdk.css.v1.model.ListClustersDetailsRequest()).getClusters(), HCloudCss::new);
    }

    /**
     * 华为云-cfw
     */
    public List<HCloudCfw> listCfw() {
        CfwClient client = CfwClient.newBuilder()
                .withCredential(auth)
                .withRegion(CfwRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listFirewallList(new com.huaweicloud.sdk.cfw.v1.model.ListFirewallListRequest()).getData().getRecords(), HCloudCfw::new);
    }

    /**
     * 华为云-ccm
     */
    public List<HCloudCcm> listCcm() {
        CcmClient client = CcmClient.newBuilder()
                .withCredential(auth)
                .withRegion(CcmRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listCertificate(new com.huaweicloud.sdk.ccm.v1.model.ListCertificateRequest()).getCertificates(), HCloudCcm::new);
    }

    /**
     * 华为云-drs
     */
    public List<HCloudDrs> listDrs() {
        DrsClient client = DrsClient.newBuilder()
                .withCredential(auth)
                .withRegion(DrsRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listJobs(new com.huaweicloud.sdk.drs.v5.model.ListJobsRequest().withLimit(100)).getJobs(), HCloudDrs::new);
    }

    /**
     * 华为云-mrs
     */
    public List<HCloudMrs> listMrs() {
        MrsClient client = MrsClient.newBuilder()
                .withCredential(auth)
                .withRegion(MrsRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listClusters(new com.huaweicloud.sdk.mrs.v1.model.ListClustersRequest()).getClusters(), HCloudMrs::new);
    }

    /**
     * 华为云-as
     */
    public List<HCloudAs> listAs() {
        AsClient client = AsClient.newBuilder()
                .withCredential(auth)
                .withRegion(AsRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listScalingGroups(new com.huaweicloud.sdk.as.v1.model.ListScalingGroupsRequest().withLimit(100)).getScalingGroups(), HCloudAs::new);
    }

    /**
     * 华为云-bms
     */
    public List<HCloudBms> listBms() {
        BmsClient client = BmsClient.newBuilder()
                .withCredential(auth)
                .withRegion(BmsRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listBareMetalServers(new com.huaweicloud.sdk.bms.v1.model.ListBareMetalServersRequest()).getServers(), HCloudBms::new);
    }

    /**
     * 华为云-workspace
     */
    public List<HCloudWorkspace> listWorkspace() {
        WorkspaceClient client = WorkspaceClient.newBuilder()
                .withCredential(auth)
                .withRegion(WorkspaceRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listDesktopsDetail(new com.huaweicloud.sdk.workspace.v2.model.ListDesktopsDetailRequest().withLimit(100)).getDesktops(), HCloudWorkspace::new);
    }

    /**
     * 华为云-dli
     */
    public List<HCloudDli> listDli() {
        DliClient client = DliClient.newBuilder()
                .withCredential(auth)
                .withRegion(DliRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listQueues(new com.huaweicloud.sdk.dli.v1.model.ListQueuesRequest()).getQueues(), HCloudDli::new);
    }

    /**
     * 华为云-dws
     */
    public List<HCloudDws> listDws() {
        DwsClient client = DwsClient.newBuilder()
                .withCredential(auth)
                .withRegion(DwsRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listClusters(new com.huaweicloud.sdk.dws.v2.model.ListClustersRequest()).getClusters(), HCloudDws::new);
    }

    /**
     * 华为云-gaussdbfornosql
     */
    public List<HCloudGaussDbNoSql> listGaussDbNoSql() {
        GaussDBforNoSQLClient client = GaussDBforNoSQLClient.newBuilder()
                .withCredential(auth)
                .withRegion(GaussDBforNoSQLRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listInstances(new com.huaweicloud.sdk.gaussdbfornosql.v3.model.ListInstancesRequest().withLimit(100)).getInstances(), HCloudGaussDbNoSql::new);
    }

    /**
     * 华为云-gaussdbforopengauss
     */
    public List<HCloudGaussDbOpenGauss> listGaussDbOpenGauss() {
        GaussDBforopenGaussClient client = GaussDBforopenGaussClient.newBuilder()
                .withCredential(auth)
                .withRegion(GaussDBforopenGaussRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listInstances(new com.huaweicloud.sdk.gaussdbforopengauss.v3.model.ListInstancesRequest().withLimit(100)).getInstances(), HCloudGaussDbOpenGauss::new);
    }

    /**
     * 华为云-ddm
     */
    public List<HCloudDdm> listDdm() {
        DdmClient client = DdmClient.newBuilder()
                .withCredential(auth)
                .withRegion(DdmRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listInstances(new com.huaweicloud.sdk.ddm.v1.model.ListInstancesRequest().withLimit(100)).getInstances(), HCloudDdm::new);
    }

    /**
     * 华为云-cse
     */
    public List<HCloudCse> listCse() {
        CseClient client = CseClient.newBuilder()
                .withCredential(auth)
                .withRegion(CseRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listEngines(new com.huaweicloud.sdk.cse.v1.model.ListEnginesRequest()).getData(), HCloudCse::new);
    }

    /**
     * 华为云-servicestage
     */
    public List<HCloudServiceStage> listServiceStage() {
        ServiceStageClient client = ServiceStageClient.newBuilder()
                .withCredential(auth)
                .withRegion(ServiceStageRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listApplications(new com.huaweicloud.sdk.servicestage.v2.model.ListApplicationsRequest()).getApplications(), HCloudServiceStage::new);
    }

    /**
     * 华为云-cbh
     */
    public List<HCloudCbh> listCbh() {
        com.huaweicloud.sdk.cbh.v2.CbhClient client = com.huaweicloud.sdk.cbh.v2.CbhClient.newBuilder()
                .withCredential(auth)
                .withRegion(com.huaweicloud.sdk.cbh.v2.region.CbhRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listInstances(new com.huaweicloud.sdk.cbh.v2.model.ListInstancesRequest()).getInstance(), HCloudCbh::new);
    }

    /**
     * 华为云-dbss
     */
    public List<HCloudDbss> listDbss() {
        DbssClient client = DbssClient.newBuilder()
                .withCredential(auth)
                .withRegion(DbssRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listAuditInstances(new com.huaweicloud.sdk.dbss.v1.model.ListAuditInstancesRequest()).getServers(), HCloudDbss::new);
    }

    /**
     * 华为云-vod
     */
    public List<HCloudVod> listVod() {
        VodClient client = VodClient.newBuilder()
                .withCredential(auth)
                .withRegion(VodRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listAssetList(new com.huaweicloud.sdk.vod.v1.model.ListAssetListRequest()).getAssets(), HCloudVod::new);
    }

    /**
     * 华为云-live
     */
    public List<HCloudLive> listLive() {
        LiveClient client = LiveClient.newBuilder()
                .withCredential(auth)
                .withRegion(LiveRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listLiveStreamsOnline(new com.huaweicloud.sdk.live.v1.model.ListLiveStreamsOnlineRequest()).getStreams(), HCloudLive::new);
    }

    /**
     * 华为云-oms
     */
    public List<HCloudOms> listOms() {
        OmsClient client = OmsClient.newBuilder()
                .withCredential(auth)
                .withRegion(OmsRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listTasks(new com.huaweicloud.sdk.oms.v2.model.ListTasksRequest()).getTasks(), HCloudOms::new);
    }

    /**
     * 华为云-sdrs
     */
    public List<HCloudSdrs> listSdrs() {
        SdrsClient client = SdrsClient.newBuilder()
                .withCredential(auth)
                .withRegion(SdrsRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listProtectedInstances(new com.huaweicloud.sdk.sdrs.v1.model.ListProtectedInstancesRequest()).getProtectedInstances(), HCloudSdrs::new);
    }

    /**
     * 华为云-sms
     */
    public List<HCloudSms> listSms() {
        SmsClient client = SmsClient.newBuilder()
                .withCredential(auth)
                .withRegion(SmsRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listServers(new com.huaweicloud.sdk.sms.v3.model.ListServersRequest()).getSourceServers(), HCloudSms::new);
    }

    /**
     * 华为云-dsc
     */
    public List<HCloudDsc> listDsc() {
        DscClient client = DscClient.newBuilder()
                .withCredential(auth)
                .withRegion(DscRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listBuckets(new com.huaweicloud.sdk.dsc.v1.model.ListBucketsRequest()).getBuckets(), HCloudDsc::new);
    }

    /**
     * 华为云-roma
     */
    public List<HCloudRoma> listRoma() {
        RomaClient client = RomaClient.newBuilder()
                .withCredential(auth)
                .withRegion(RomaRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listMqsInstance(new com.huaweicloud.sdk.roma.v2.model.ListMqsInstanceRequest()).getInstances(), HCloudRoma::new);
    }

    /**
     * 华为云-cph
     */
    public List<HCloudCph> listCph() {
        CphClient client = CphClient.newBuilder()
                .withCredential(auth)
                .withRegion(CphRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listCloudPhones(new com.huaweicloud.sdk.cph.v1.model.ListCloudPhonesRequest()).getPhones(), HCloudCph::new);
    }

    /**
     * 华为云-er
     */
    public List<HCloudEr> listEr() {
        ErClient client = ErClient.newBuilder()
                .withCredential(auth)
                .withRegion(ErRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listEnterpriseRouters(new com.huaweicloud.sdk.er.v3.model.ListEnterpriseRoutersRequest()).getInstances(), HCloudEr::new);
    }

    /**
     * 华为云-vpcep
     */
    public List<HCloudVpcep> listVpcep() {
        VpcepClient client = VpcepClient.newBuilder()
                .withCredential(auth)
                .withRegion(VpcepRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listEndpoints(new com.huaweicloud.sdk.vpcep.v1.model.ListEndpointsRequest()).getEndpoints(), HCloudVpcep::new);
    }

    /**
     * 华为云-ief
     */
    public List<HCloudIef> listIef() {
        IefClient client = IefClient.newBuilder()
                .withCredential(auth)
                .withRegion(IefRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listEdgeNodes(new com.huaweicloud.sdk.ief.v1.model.ListEdgeNodesRequest()).getNodes(), HCloudIef::new);
    }

    /**
     * 华为云-iotda
     */
    public List<HCloudIotDa> listIotDa() {
        IoTDAClient client = IoTDAClient.newBuilder()
                .withCredential(auth)
                .withRegion(IoTDARegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listDevices(new com.huaweicloud.sdk.iotda.v5.model.ListDevicesRequest().withLimit(100)).getDevices(), HCloudIotDa::new);
    }

    /**
     * 华为云-deh
     */
    public List<HCloudDeh> listDeh() {
        DeHClient client = DeHClient.newBuilder()
                .withCredential(auth)
                .withRegion(DeHRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listDedicatedHosts(new com.huaweicloud.sdk.deh.v1.model.ListDedicatedHostsRequest()).getDedicatedHosts(), HCloudDeh::new);
    }

    /**
     * 华为云-bcs
     */
    public List<HCloudBcs> listBcs() {
        BcsClient client = BcsClient.newBuilder()
                .withCredential(auth)
                .withRegion(BcsRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listBlockchains(new com.huaweicloud.sdk.bcs.v2.model.ListBlockchainsRequest()).getBlockchains(), HCloudBcs::new);
    }
}
