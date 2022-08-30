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

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.huaweicloud.sdk.bss.v2.BssClient;
import com.huaweicloud.sdk.bss.v2.model.*;
import com.huaweicloud.sdk.bss.v2.region.BssRegion;
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
import com.huaweicloud.sdk.ecs.v2.EcsClient;
import com.huaweicloud.sdk.ecs.v2.model.ListServersDetailsRequest;
import com.huaweicloud.sdk.ecs.v2.region.EcsRegion;
import com.huaweicloud.sdk.elb.v3.ElbClient;
import com.huaweicloud.sdk.elb.v3.model.ListLoadBalancersRequest;
import com.huaweicloud.sdk.elb.v3.region.ElbRegion;
import com.huaweicloud.sdk.evs.v2.EvsClient;
import com.huaweicloud.sdk.evs.v2.model.ListVolumesRequest;
import com.huaweicloud.sdk.evs.v2.region.EvsRegion;
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
import com.linjicong.cloud.stat.dao.entity.CloudConf;
import com.linjicong.cloud.stat.dao.entity.hcloud.*;
import com.linjicong.cloud.stat.util.BeanUtils;
import com.obs.services.ObsClient;
import com.obs.services.model.ListBucketsRequest;

import java.util.Date;
import java.util.List;

/**
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 */
public class HCloudClient{

    private final ICredential auth;

    private final ICredential globalAuth;

    private final ObsClient obsClient;

    private final String region;

    public HCloudClient(CloudConf cloudConf) {
        String accessKey = cloudConf.getAccessKey();
        String secretKey = cloudConf.getSecretKey();
        this.region = cloudConf.getRegion();
        this.auth = new BasicCredentials()
                .withAk(accessKey)
                .withSk(secretKey);
        this.globalAuth = new GlobalCredentials()
                .withAk(accessKey)
                .withSk(secretKey);
        this.obsClient = new ObsClient(accessKey, secretKey, ObsEndpoint.CN_SOUTH_1);
    }

    /**
     * 查询华为云-弹性云服务器列表
     * @return 弹性云服务器列表
     */
    public List<HCloudEcs> listEcs() {
        EcsClient client = EcsClient.newBuilder()
                .withCredential(auth)
                .withRegion(EcsRegion.valueOf(region))
                .build();

        return BeanUtils.cgLibCopyList(client.listServersDetails(new ListServersDetailsRequest().withLimit(1000)).getServers(), HCloudEcs::new);
    }

    public List<HCloudRds> listRds() {
        RdsClient client = RdsClient.newBuilder()
                .withCredential(auth)
                .withRegion(RdsRegion.valueOf(region))
                .build();

        return BeanUtils.cgLibCopyList(client.listInstances(new ListInstancesRequest().withLimit(100)).getInstances(),HCloudRds::new);
    }

    public List<HCloudDcs> listDcs() {
        DcsClient client = DcsClient.newBuilder()
                .withCredential(auth)
                .withRegion(DcsRegion.valueOf(region))
                .build();

        return BeanUtils.cgLibCopyList(client.listInstances(new com.huaweicloud.sdk.dcs.v2.model.ListInstancesRequest().withLimit(1000)).getInstances(),HCloudDcs::new);

    }

    public List<HCloudDds> listDds() {
        DdsClient client = DdsClient.newBuilder()
                .withCredential(auth)
                .withRegion(DdsRegion.valueOf(region))
                .build();

        return BeanUtils.cgLibCopyList(client.listInstances(new com.huaweicloud.sdk.dds.v3.model.ListInstancesRequest().withLimit(100)).getInstances(),HCloudDds::new);
    }

    public List<HCloudObs> listObs() {
        return BeanUtils.cgLibCopyList(obsClient.listBucketsV2(new ListBucketsRequest()).getBuckets(),HCloudObs::new);
    }

    public List<HCloudSfs> listSfs() {
        SFSTurboClient client = SFSTurboClient.newBuilder()
                .withCredential(auth)
                .withRegion(SFSTurboRegion.valueOf(region))
                .build();

        return BeanUtils.cgLibCopyList(client.listShares(new ListSharesRequest().withLimit(200)).getShares(),HCloudSfs::new);
    }

    public List<HCloudElb> listElb() {
        ElbClient client = ElbClient.newBuilder()
                .withCredential(auth)
                .withRegion(ElbRegion.valueOf(region))
                .build();

        return BeanUtils.cgLibCopyList(client.listLoadBalancers(new ListLoadBalancersRequest().withLimit(200)).getLoadbalancers(), HCloudElb::new);
    }

    public List<HCloudVpc> listVpc() {
        VpcClient client = VpcClient.newBuilder()
                .withCredential(auth)
                .withRegion(VpcRegion.valueOf(region))
                .build();

        return BeanUtils.cgLibCopyList(client.listVpcs(new ListVpcsRequest().withLimit(200)).getVpcs(), HCloudVpc::new);
    }

    public List<HCloudEvs> listEvs() {
        EvsClient client = EvsClient.newBuilder()
                .withCredential(auth)
                .withRegion(EvsRegion.valueOf(region))
                .build();

        return BeanUtils.cgLibCopyList(client.listVolumes(new ListVolumesRequest().withLimit(200)).getVolumes(), HCloudEvs::new);
    }

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

    public List<HCloudResources> listResources() {
        RmsClient client = RmsClient.newBuilder()
                .withCredential(globalAuth)
                .withRegion(RmsRegion.CN_NORTH_4)
                .build();
        ListAllResourcesRequest listAllResourcesRequest = new ListAllResourcesRequest();
        listAllResourcesRequest.withLimit(200);

        ListAllResourcesResponse listAllResourcesResponse = client.listAllResources(listAllResourcesRequest.withLimit(200));
        List<ResourceEntity> resources = listAllResourcesResponse.getResources();
        String nextMarker = listAllResourcesResponse.getPageInfo().getNextMarker();

        while (StrUtil.isNotBlank(nextMarker)){
            ListAllResourcesResponse listAllResourcesResponseNext = client.listAllResources(listAllResourcesRequest.withMarker(nextMarker));
            nextMarker = listAllResourcesResponseNext.getPageInfo().getNextMarker();
            resources.addAll(listAllResourcesResponseNext.getResources());
        }
        return BeanUtils.cgLibCopyList(resources, HCloudResources::new);
    }

    public List<HCloudDnsPrivate> listDnsPrivate() {
        DnsClient client = DnsClient.newBuilder()
                .withCredential(auth)
                .withRegion(DnsRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listPrivateZones(new ListPrivateZonesRequest().withLimit(500).withType("private")).getZones(), HCloudDnsPrivate::new);
    }

    public List<HCloudDnsPrivateRecordSets> listDnsPrivateRecordSets() {
        DnsClient client = DnsClient.newBuilder()
                .withCredential(auth)
                .withRegion(DnsRegion.valueOf(region))
                .build();
        return BeanUtils.cgLibCopyList(client.listRecordSets(new ListRecordSetsRequest().withLimit(500).withZoneType("private")).getRecordsets(), HCloudDnsPrivateRecordSets::new);
    }
}
