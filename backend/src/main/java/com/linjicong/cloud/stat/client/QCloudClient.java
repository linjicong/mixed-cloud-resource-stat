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

import cn.hutool.core.collection.ListUtil;
import com.linjicong.cloud.stat.dao.entity.BasicEntityExtend;
import com.linjicong.cloud.stat.dao.entity.CloudConf;
import com.linjicong.cloud.stat.dao.entity.qcloud.*;
import com.linjicong.cloud.stat.exception.ClientException;
import com.linjicong.cloud.stat.util.BeanUtils;
import com.linjicong.cloud.stat.util.ThreadLocalUtil;
import com.tencentcloudapi.billing.v20180709.BillingClient;
import com.tencentcloudapi.billing.v20180709.models.BillResourceSummary;
import com.tencentcloudapi.billing.v20180709.models.DescribeBillResourceSummaryRequest;
import com.tencentcloudapi.billing.v20180709.models.DescribeBillResourceSummaryResponse;
import com.tencentcloudapi.cam.v20190116.CamClient;
import com.tencentcloudapi.cam.v20190116.models.*;
import com.tencentcloudapi.cbs.v20170312.CbsClient;
import com.tencentcloudapi.cbs.v20170312.models.DescribeDisksRequest;
import com.tencentcloudapi.cbs.v20170312.models.DescribeDisksResponse;
import com.tencentcloudapi.cbs.v20170312.models.Disk;
import com.tencentcloudapi.cdb.v20170320.CdbClient;
import com.tencentcloudapi.cdb.v20170320.models.DescribeDBInstancesRequest;
import com.tencentcloudapi.cdb.v20170320.models.DescribeDBInstancesResponse;
import com.tencentcloudapi.cfs.v20190719.CfsClient;
import com.tencentcloudapi.cfs.v20190719.models.DescribeCfsFileSystemsRequest;
import com.tencentcloudapi.cfs.v20190719.models.DescribeCfsFileSystemsResponse;
import com.tencentcloudapi.clb.v20180317.ClbClient;
import com.tencentcloudapi.clb.v20180317.models.DescribeLoadBalancersRequest;
import com.tencentcloudapi.clb.v20180317.models.DescribeLoadBalancersResponse;
import com.tencentcloudapi.clb.v20180317.models.LoadBalancer;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.cvm.v20170312.CvmClient;
import com.tencentcloudapi.cvm.v20170312.models.DescribeInstancesRequest;
import com.tencentcloudapi.dnspod.v20210323.DnspodClient;
import com.tencentcloudapi.dnspod.v20210323.models.DescribeDomainListRequest;
import com.tencentcloudapi.dnspod.v20210323.models.DescribeDomainListResponse;
import com.tencentcloudapi.tag.v20180813.TagClient;
import com.tencentcloudapi.tag.v20180813.models.DescribeResourceTagsRequest;
import com.tencentcloudapi.tag.v20180813.models.DescribeResourceTagsResponse;
import com.tencentcloudapi.tag.v20180813.models.TagResource;

import java.util.ArrayList;
import java.util.List;

/**
 * 腾讯云客户端
 * 用于调用腾讯云API获取资源信息
 * 支持CVM、CDB、CLB、CBS等多种资源类型
 * 
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
public class QCloudClient{
    /** 腾讯云凭证 */
    private final Credential credential;
    /** 区域 */
    private final String region;

    /**
     * 构造腾讯云客户端
     * 
     * @param cloudConf 云配置信息，包含访问密钥、区域等
     */
    public QCloudClient(CloudConf cloudConf) {
        // 创建腾讯云凭证
        this.credential = new Credential(cloudConf.getAccessKey(), cloudConf.getSecretKey());
        this.region = cloudConf.getRegion();

        String name = cloudConf.getName();
        String provider = cloudConf.getProvider();
        // 将扩展信息存入ThreadLocal，供MyBatis拦截器使用，用于自动填充公共字段
        BasicEntityExtend entityExtend = new BasicEntityExtend(name, provider, region);
        ThreadLocalUtil.put("entityExtend", entityExtend);
    }

    /**
     * 查询腾讯云CVM（云服务器）列表
     * 
     * @return CVM列表
     */
    public List<QCloudCvm> listCvm() {
        CvmClient client = new CvmClient(credential, region);
        DescribeInstancesRequest describeInstancesRequest = new DescribeInstancesRequest();
        // 设置每页查询数量为100
        describeInstancesRequest.setLimit(100L);
        try {
            return BeanUtils.cgLibCopyList(client.DescribeInstances(describeInstancesRequest).getInstanceSet(), QCloudCvm::new);
        } catch (TencentCloudSDKException e) {
            throw new ClientException(e);
        }
    }

    public List<QCloudCbs> listCbs() {
        CbsClient client = new CbsClient(credential, region);
        DescribeDisksRequest request = new DescribeDisksRequest();
        request.setLimit(100L);
        try {
            DescribeDisksResponse response = client.DescribeDisks(request);
            ArrayList<Disk> disks = ListUtil.toList(response.getDiskSet());
            while (disks.size() % 100 ==0){
                request.setOffset((long) disks.size());
                DescribeDisksResponse responseNext = client.DescribeDisks(request);
                disks.addAll(ListUtil.toList(responseNext.getDiskSet()));
            }
            return BeanUtils.cgLibCopyList(disks, QCloudCbs::new);
        } catch (TencentCloudSDKException e) {
            throw new ClientException(e);
        }
    }

    public List<QCloudBillResourceSummary> listBillResourceSummary(String month) {
        BillingClient client = new BillingClient(credential,region);
        DescribeBillResourceSummaryRequest request = new DescribeBillResourceSummaryRequest();
        request.setOffset(0L);
        request.setLimit(1000L);
        request.setMonth(month);
        try {
            DescribeBillResourceSummaryResponse response = client.DescribeBillResourceSummary(request);
            List<BillResourceSummary> billResourceSummaries = ListUtil.toList(response.getResourceSummarySet());
            while (billResourceSummaries.size() % 1000 == 0){
                request.setOffset((long) billResourceSummaries.size());
                DescribeBillResourceSummaryResponse responseNext = client.DescribeBillResourceSummary(request);
                billResourceSummaries.addAll(ListUtil.toList(responseNext.getResourceSummarySet()));
            }
            List<QCloudBillResourceSummary> qCloudBillResourceSummaries = BeanUtils.cgLibCopyList(billResourceSummaries, QCloudBillResourceSummary::new);
            qCloudBillResourceSummaries.forEach(qCloudBillResourceSummary -> qCloudBillResourceSummary.setMonth(month));
            return qCloudBillResourceSummaries;
        } catch (TencentCloudSDKException e) {
            throw new ClientException(e);
        }
    }

    public List<QCloudCdb> listCdb() {
        CdbClient client = new CdbClient(credential,region);
        DescribeDBInstancesRequest request = new DescribeDBInstancesRequest();
        try {
            DescribeDBInstancesResponse response = client.DescribeDBInstances(request);
            return BeanUtils.cgLibCopyList(ListUtil.toList(response.getItems()), QCloudCdb::new);
        } catch (TencentCloudSDKException e) {
            throw new ClientException(e);
        }
    }

    public List<QCloudClb> listClb() {
        ClbClient client = new ClbClient(credential, region);
        DescribeLoadBalancersRequest request = new DescribeLoadBalancersRequest();
        request.setLimit(100L);
        try {
            DescribeLoadBalancersResponse response = client.DescribeLoadBalancers(request);
            ArrayList<LoadBalancer> loadBalancers = ListUtil.toList(response.getLoadBalancerSet());
            while (loadBalancers.size() % 100 ==0){
                request.setOffset((long) loadBalancers.size());
                DescribeLoadBalancersResponse responseNext = client.DescribeLoadBalancers(request);
                loadBalancers.addAll(ListUtil.toList(responseNext.getLoadBalancerSet()));
            }
            return BeanUtils.cgLibCopyList(loadBalancers, QCloudClb::new);
        } catch (TencentCloudSDKException e) {
            throw new ClientException(e);
        }
    }

    public List<QCloudCfs> listCfs() {
        CfsClient client = new CfsClient(credential,region);
        DescribeCfsFileSystemsRequest request = new DescribeCfsFileSystemsRequest();
        try {
            DescribeCfsFileSystemsResponse response = client.DescribeCfsFileSystems(request);
            return BeanUtils.cgLibCopyList(ListUtil.toList(response.getFileSystems()), QCloudCfs::new);
        } catch (TencentCloudSDKException e) {
            throw new ClientException(e);
        }
    }

    public List<QCloudUser> listUsers() {
        CamClient client = new CamClient (credential,region);
        ListUsersRequest request = new ListUsersRequest();
        try {
            ListUsersResponse response = client.ListUsers(request);
            return BeanUtils.cgLibCopyList(ListUtil.toList(response.getData()), QCloudUser::new);
        } catch (TencentCloudSDKException e) {
            throw new ClientException(e);
        }
    }

    public List<QCloudAccessKey> listAccessKeys(Long targetUin) {
        CamClient client = new CamClient (credential,region);
        ListAccessKeysRequest request = new ListAccessKeysRequest();
        request.setTargetUin(targetUin);
        try {
            ListAccessKeysResponse response = client.ListAccessKeys(request);
            return BeanUtils.cgLibCopyList(ListUtil.toList(response.getAccessKeys()), QCloudAccessKey::new);
        } catch (TencentCloudSDKException e) {
            throw new ClientException(e);
        }
    }

    public List<QCloudAccessKeyLastUsed> listAccessKeyLastUsed(String[] secretIdList) {
        CamClient client = new CamClient (credential,region);
        GetSecurityLastUsedRequest request = new GetSecurityLastUsedRequest();
        request.setSecretIdList(secretIdList);
        try {
            GetSecurityLastUsedResponse response = client.GetSecurityLastUsed(request);
            return BeanUtils.cgLibCopyList(ListUtil.toList(response.getSecretIdLastUsedRows()), QCloudAccessKeyLastUsed::new);
        } catch (TencentCloudSDKException e) {
            throw new ClientException(e);
        }
    }

    public List<QCloudResourceTag> listResourceTags() {
        TagClient client = new TagClient (credential,region);
        DescribeResourceTagsRequest request = new DescribeResourceTagsRequest();
        request.setLimit(100L);
        try {
            DescribeResourceTagsResponse response = client.DescribeResourceTags(request);
            ArrayList<TagResource> tagResources = ListUtil.toList(response.getRows());
            while (tagResources.size() % 100 ==0){
                request.setOffset((long) tagResources.size());
                DescribeResourceTagsResponse responseNext = client.DescribeResourceTags(request);
                tagResources.addAll(ListUtil.toList(responseNext.getRows()));
            }
            return BeanUtils.cgLibCopyList(tagResources, QCloudResourceTag::new);
        } catch (TencentCloudSDKException e) {
            throw new ClientException(e);
        }
    }

    public List<QCloudDnsDomain> listDnsDomain() {
        DnspodClient client = new DnspodClient (credential,region);
        DescribeDomainListRequest request = new DescribeDomainListRequest();
        try {
            DescribeDomainListResponse response = client.DescribeDomainList(request);
            return BeanUtils.cgLibCopyList(ListUtil.toList(response.getDomainList()), QCloudDnsDomain::new);
        } catch (TencentCloudSDKException e) {
            throw new ClientException(e);
        }
    }

    // ==================== 数据库类 ====================

    public List<QCloudTcaplusDB> listTcaplusDB() {
        com.tencentcloudapi.tcaplusdb.v20190823.TcaplusdbClient client = new com.tencentcloudapi.tcaplusdb.v20190823.TcaplusdbClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeClusters(new com.tencentcloudapi.tcaplusdb.v20190823.models.DescribeClustersRequest()).getClusters()), QCloudTcaplusDB::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudTendis> listTendis() {
        com.tencentcloudapi.keewidb.v20220308.KeewidbClient client = new com.tencentcloudapi.keewidb.v20220308.KeewidbClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeInstances(new com.tencentcloudapi.keewidb.v20220308.models.DescribeInstancesRequest()).getInstanceSet()), QCloudTendis::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudDLC> listDLC() {
        com.tencentcloudapi.dlc.v20210125.DlcClient client = new com.tencentcloudapi.dlc.v20210125.DlcClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeDatabases(new com.tencentcloudapi.dlc.v20210125.models.DescribeDatabasesRequest()).getDatabaseList()), QCloudDLC::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== 安全类 ====================

    public List<QCloudKMS> listKMS() {
        com.tencentcloudapi.kms.v20190118.KmsClient client = new com.tencentcloudapi.kms.v20190118.KmsClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeKeys(new com.tencentcloudapi.kms.v20190118.models.DescribeKeysRequest()).getKeyMetadatas()), QCloudKMS::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudSecretMgr> listSecretMgr() {
        com.tencentcloudapi.ssm.v20190923.SsmClient client = new com.tencentcloudapi.ssm.v20190923.SsmClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.ListSecrets(new com.tencentcloudapi.ssm.v20190923.models.ListSecretsRequest()).getSecretMetadatas()), QCloudSecretMgr::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudCWP> listCWP() {
        com.tencentcloudapi.cwp.v20180228.CwpClient client = new com.tencentcloudapi.cwp.v20180228.CwpClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeMachines(new com.tencentcloudapi.cwp.v20180228.models.DescribeMachinesRequest()).getMachines()), QCloudCWP::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudCWP3> listCWP3() {
        com.tencentcloudapi.cwp.v20180228.CwpClient client = new com.tencentcloudapi.cwp.v20180228.CwpClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeMachines(new com.tencentcloudapi.cwp.v20180228.models.DescribeMachinesRequest()).getMachines()), QCloudCWP3::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudCloudHSM> listCloudHSM() {
        com.tencentcloudapi.cloudhsm.v20191112.CloudhsmClient client = new com.tencentcloudapi.cloudhsm.v20191112.CloudhsmClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeVsms(new com.tencentcloudapi.cloudhsm.v20191112.models.DescribeVsmsRequest()).getVsmList()), QCloudCloudHSM::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudHSM> listHSM() {
        com.tencentcloudapi.cloudhsm.v20191112.CloudhsmClient client = new com.tencentcloudapi.cloudhsm.v20191112.CloudhsmClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeVsms(new com.tencentcloudapi.cloudhsm.v20191112.models.DescribeVsmsRequest()).getVsmList()), QCloudHSM::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudSSLPod> listSSLPod() {
        com.tencentcloudapi.sslpod.v20190605.SslpodClient client = new com.tencentcloudapi.sslpod.v20190605.SslpodClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeDomains(new com.tencentcloudapi.sslpod.v20190605.models.DescribeDomainsRequest()).getData().getResult()), QCloudSSLPod::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== 容器/微服务类 ====================

    public List<QCloudTcrEnt> listTcrEnt() {
        com.tencentcloudapi.tcr.v20190924.TcrClient client = new com.tencentcloudapi.tcr.v20190924.TcrClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeInstances(new com.tencentcloudapi.tcr.v20190924.models.DescribeInstancesRequest()).getRegistries()), QCloudTcrEnt::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudMongoDB_CKafka> listMongoDB_CKafka() {
        com.tencentcloudapi.cdc.v20201214.CdcClient client = new com.tencentcloudapi.cdc.v20201214.CdcClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeDedicatedClusters(new com.tencentcloudapi.cdc.v20201214.models.DescribeDedicatedClustersRequest()).getDedicatedClusterSet()), QCloudMongoDB_CKafka::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== 直播/音视频类 ====================

    public List<QCloudLive> listLive() {
        com.tencentcloudapi.live.v20180801.LiveClient client = new com.tencentcloudapi.live.v20180801.LiveClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeLiveDomains(new com.tencentcloudapi.live.v20180801.models.DescribeLiveDomainsRequest()).getDomainList()), QCloudLive::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudLive2> listLive2() {
        com.tencentcloudapi.live.v20180801.LiveClient client = new com.tencentcloudapi.live.v20180801.LiveClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeLiveDomains(new com.tencentcloudapi.live.v20180801.models.DescribeLiveDomainsRequest()).getDomainList()), QCloudLive2::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudCSS> listCSS() {
        com.tencentcloudapi.live.v20180801.LiveClient client = new com.tencentcloudapi.live.v20180801.LiveClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeLiveDomains(new com.tencentcloudapi.live.v20180801.models.DescribeLiveDomainsRequest()).getDomainList()), QCloudCSS::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== 短信/邮件类 ====================

    public List<QCloudSMS> listSMS() {
        com.tencentcloudapi.sms.v20210111.SmsClient client = new com.tencentcloudapi.sms.v20210111.SmsClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeSmsSignList(new com.tencentcloudapi.sms.v20210111.models.DescribeSmsSignListRequest()).getDescribeSignListStatusSet()), QCloudSMS::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudSmsSign> listSmsSign() {
        com.tencentcloudapi.sms.v20210111.SmsClient client = new com.tencentcloudapi.sms.v20210111.SmsClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeSmsSignList(new com.tencentcloudapi.sms.v20210111.models.DescribeSmsSignListRequest()).getDescribeSignListStatusSet()), QCloudSmsSign::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudSES> listSES() {
        com.tencentcloudapi.ses.v20201002.SesClient client = new com.tencentcloudapi.ses.v20201002.SesClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.ListEmailIdentities(new com.tencentcloudapi.ses.v20201002.models.ListEmailIdentitiesRequest()).getEmailIdentities()), QCloudSES::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudMail> listMail() {
        com.tencentcloudapi.ses.v20201002.SesClient client = new com.tencentcloudapi.ses.v20201002.SesClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.ListEmailIdentities(new com.tencentcloudapi.ses.v20201002.models.ListEmailIdentitiesRequest()).getEmailIdentities()), QCloudMail::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== AI/ML资源管理类 ====================

    public List<QCloudFace> listFace() {
        com.tencentcloudapi.iai.v20200303.IaiClient client = new com.tencentcloudapi.iai.v20200303.IaiClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.GetGroupList(new com.tencentcloudapi.iai.v20200303.models.GetGroupListRequest()).getGroupInfos()), QCloudFace::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudFaceFusion> listFaceFusion() {
        com.tencentcloudapi.facefusion.v20220927.FacefusionClient client = new com.tencentcloudapi.facefusion.v20220927.FacefusionClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeMaterialList(new com.tencentcloudapi.facefusion.v20220927.models.DescribeMaterialListRequest()).getMaterialInfos()), QCloudFaceFusion::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudTIHai> listTIHai() {
        com.tencentcloudapi.hai.v20230812.HaiClient client = new com.tencentcloudapi.hai.v20230812.HaiClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeInstances(new com.tencentcloudapi.hai.v20230812.models.DescribeInstancesRequest()).getInstanceSet()), QCloudTIHai::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudImageSearch> listImageSearch() {
        com.tencentcloudapi.tiia.v20190529.TiiaClient client = new com.tencentcloudapi.tiia.v20190529.TiiaClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeGroups(new com.tencentcloudapi.tiia.v20190529.models.DescribeGroupsRequest()).getGroups()), QCloudImageSearch::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== 事件/组织/顾问类 ====================

    public List<QCloudEventBus> listEventBus() {
        com.tencentcloudapi.eb.v20210416.EbClient client = new com.tencentcloudapi.eb.v20210416.EbClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.ListEventBuses(new com.tencentcloudapi.eb.v20210416.models.ListEventBusesRequest()).getEventBuses()), QCloudEventBus::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== VPC/网络类 ====================

    public List<QCloudVpc> listVpc() {
        com.tencentcloudapi.vpc.v20170312.VpcClient client = new com.tencentcloudapi.vpc.v20170312.VpcClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeVpcs(new com.tencentcloudapi.vpc.v20170312.models.DescribeVpcsRequest()).getVpcSet()), QCloudVpc::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudVpcSubnet> listSubnet() {
        com.tencentcloudapi.vpc.v20170312.VpcClient client = new com.tencentcloudapi.vpc.v20170312.VpcClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeSubnets(new com.tencentcloudapi.vpc.v20170312.models.DescribeSubnetsRequest()).getSubnetSet()), QCloudVpcSubnet::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudEip> listEip() {
        com.tencentcloudapi.vpc.v20170312.VpcClient client = new com.tencentcloudapi.vpc.v20170312.VpcClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeAddresses(new com.tencentcloudapi.vpc.v20170312.models.DescribeAddressesRequest()).getAddressSet()), QCloudEip::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudSecurityGroup> listSecurityGroup() {
        com.tencentcloudapi.vpc.v20170312.VpcClient client = new com.tencentcloudapi.vpc.v20170312.VpcClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeSecurityGroups(new com.tencentcloudapi.vpc.v20170312.models.DescribeSecurityGroupsRequest()).getSecurityGroupSet()), QCloudSecurityGroup::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudNatGateway> listNatGateway() {
        com.tencentcloudapi.vpc.v20170312.VpcClient client = new com.tencentcloudapi.vpc.v20170312.VpcClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeNatGateways(new com.tencentcloudapi.vpc.v20170312.models.DescribeNatGatewaysRequest()).getNatGatewaySet()), QCloudNatGateway::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== CDN ====================

    public List<QCloudCdnDomain> listCdnDomain() {
        com.tencentcloudapi.cdn.v20180606.CdnClient client = new com.tencentcloudapi.cdn.v20180606.CdnClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeDomains(new com.tencentcloudapi.cdn.v20180606.models.DescribeDomainsRequest()).getDomains()), QCloudCdnDomain::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== 数据库类(补充) ====================

    public List<QCloudRedis> listRedis() {
        com.tencentcloudapi.redis.v20180412.RedisClient client = new com.tencentcloudapi.redis.v20180412.RedisClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeInstances(new com.tencentcloudapi.redis.v20180412.models.DescribeInstancesRequest()).getInstanceSet()), QCloudRedis::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudMongoDb> listMongoDb() {
        com.tencentcloudapi.mongodb.v20190725.MongodbClient client = new com.tencentcloudapi.mongodb.v20190725.MongodbClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeDBInstances(new com.tencentcloudapi.mongodb.v20190725.models.DescribeDBInstancesRequest()).getInstanceDetails()), QCloudMongoDb::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudCynosDB> listCynosDB() {
        com.tencentcloudapi.cynosdb.v20190107.CynosdbClient client = new com.tencentcloudapi.cynosdb.v20190107.CynosdbClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeInstances(new com.tencentcloudapi.cynosdb.v20190107.models.DescribeInstancesRequest()).getInstanceSet()), QCloudCynosDB::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudPostgresql> listPostgresql() {
        com.tencentcloudapi.postgres.v20170312.PostgresClient client = new com.tencentcloudapi.postgres.v20170312.PostgresClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeDBInstances(new com.tencentcloudapi.postgres.v20170312.models.DescribeDBInstancesRequest()).getDBInstanceSet()), QCloudPostgresql::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudSqlserver> listSqlserver() {
        com.tencentcloudapi.sqlserver.v20180328.SqlserverClient client = new com.tencentcloudapi.sqlserver.v20180328.SqlserverClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeDBInstances(new com.tencentcloudapi.sqlserver.v20180328.models.DescribeDBInstancesRequest()).getDBInstances()), QCloudSqlserver::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudMariaDb> listMariaDb() {
        com.tencentcloudapi.mariadb.v20170312.MariadbClient client = new com.tencentcloudapi.mariadb.v20170312.MariadbClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeDBInstances(new com.tencentcloudapi.mariadb.v20170312.models.DescribeDBInstancesRequest()).getInstances()), QCloudMariaDb::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudDCDB> listDCDB() {
        com.tencentcloudapi.dcdb.v20180411.DcdbClient client = new com.tencentcloudapi.dcdb.v20180411.DcdbClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeDCDBInstances(new com.tencentcloudapi.dcdb.v20180411.models.DescribeDCDBInstancesRequest()).getInstances()), QCloudDCDB::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudMemcached> listMemcached() {
        com.tencentcloudapi.memcached.v20190318.MemcachedClient client = new com.tencentcloudapi.memcached.v20190318.MemcachedClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeInstances(new com.tencentcloudapi.memcached.v20190318.models.DescribeInstancesRequest()).getInstanceList()), QCloudMemcached::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== 服务类 ====================

    public List<QCloudScf> listScf() {
        com.tencentcloudapi.scf.v20180416.ScfClient client = new com.tencentcloudapi.scf.v20180416.ScfClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.ListFunctions(new com.tencentcloudapi.scf.v20180416.models.ListFunctionsRequest()).getFunctions()), QCloudScf::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudCkafka> listCkafka() {
        com.tencentcloudapi.ckafka.v20190819.CkafkaClient client = new com.tencentcloudapi.ckafka.v20190819.CkafkaClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeInstances(new com.tencentcloudapi.ckafka.v20190819.models.DescribeInstancesRequest()).getResult().getInstanceList()), QCloudCkafka::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudSSL> listSSL() {
        com.tencentcloudapi.ssl.v20191205.SslClient client = new com.tencentcloudapi.ssl.v20191205.SslClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeCertificates(new com.tencentcloudapi.ssl.v20191205.models.DescribeCertificatesRequest()).getCertificates()), QCloudSSL::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudWAF> listWAF() {
        com.tencentcloudapi.waf.v20180125.WafClient client = new com.tencentcloudapi.waf.v20180125.WafClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeDomains(new com.tencentcloudapi.waf.v20180125.models.DescribeDomainsRequest()).getDomains()), QCloudWAF::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudCLS> listCLS() {
        com.tencentcloudapi.cls.v20201016.ClsClient client = new com.tencentcloudapi.cls.v20201016.ClsClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeTopics(new com.tencentcloudapi.cls.v20201016.models.DescribeTopicsRequest()).getTopics()), QCloudCLS::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudDomain> listDomain() {
        com.tencentcloudapi.domain.v20180808.DomainClient client = new com.tencentcloudapi.domain.v20180808.DomainClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeDomainNameList(new com.tencentcloudapi.domain.v20180808.models.DescribeDomainNameListRequest()).getDomainSet()), QCloudDomain::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudTKE> listTKE() {
        com.tencentcloudapi.tke.v20180525.TkeClient client = new com.tencentcloudapi.tke.v20180525.TkeClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeClusters(new com.tencentcloudapi.tke.v20180525.models.DescribeClustersRequest()).getClusters()), QCloudTKE::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudTCR> listTCR() {
        com.tencentcloudapi.tcr.v20190924.TcrClient client = new com.tencentcloudapi.tcr.v20190924.TcrClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeInstances(new com.tencentcloudapi.tcr.v20190924.models.DescribeInstancesRequest()).getRegistries()), QCloudTCR::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudES> listES() {
        com.tencentcloudapi.es.v20180416.EsClient client = new com.tencentcloudapi.es.v20180416.EsClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeInstances(new com.tencentcloudapi.es.v20180416.models.DescribeInstancesRequest()).getInstanceList()), QCloudES::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudCHDFS> listCHDFS() {
        com.tencentcloudapi.chdfs.v20201112.ChdfsClient client = new com.tencentcloudapi.chdfs.v20201112.ChdfsClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeFileSystems(new com.tencentcloudapi.chdfs.v20201112.models.DescribeFileSystemsRequest()).getFileSystems()), QCloudCHDFS::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudAS> listAS() {
        com.tencentcloudapi.as.v20180419.AsClient client = new com.tencentcloudapi.as.v20180419.AsClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeAutoScalingGroups(new com.tencentcloudapi.as.v20180419.models.DescribeAutoScalingGroupsRequest()).getAutoScalingGroupSet()), QCloudAS::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudLighthouse> listLighthouse() {
        com.tencentcloudapi.lighthouse.v20200324.LighthouseClient client = new com.tencentcloudapi.lighthouse.v20200324.LighthouseClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeInstances(new com.tencentcloudapi.lighthouse.v20200324.models.DescribeInstancesRequest()).getInstanceSet()), QCloudLighthouse::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudDC> listDC() {
        com.tencentcloudapi.dc.v20180410.DcClient client = new com.tencentcloudapi.dc.v20180410.DcClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeDirectConnects(new com.tencentcloudapi.dc.v20180410.models.DescribeDirectConnectsRequest()).getDirectConnectSet()), QCloudDC::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudTDMQ> listTDMQ() {
        com.tencentcloudapi.tdmq.v20200217.TdmqClient client = new com.tencentcloudapi.tdmq.v20200217.TdmqClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeClusters(new com.tencentcloudapi.tdmq.v20200217.models.DescribeClustersRequest()).getClusterSet()), QCloudTDMQ::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudOceanus> listOceanus() {
        com.tencentcloudapi.oceanus.v20190422.OceanusClient client = new com.tencentcloudapi.oceanus.v20190422.OceanusClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeJobConfigs(new com.tencentcloudapi.oceanus.v20190422.models.DescribeJobConfigsRequest()).getJobConfigSet()), QCloudOceanus::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudEMR> listEMR() {
        com.tencentcloudapi.emr.v20190103.EmrClient client = new com.tencentcloudapi.emr.v20190103.EmrClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeInstances(new com.tencentcloudapi.emr.v20190103.models.DescribeInstancesRequest()).getClusterList()), QCloudEMR::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudGaap> listGaap() {
        com.tencentcloudapi.gaap.v20180529.GaapClient client = new com.tencentcloudapi.gaap.v20180529.GaapClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeProxies(new com.tencentcloudapi.gaap.v20180529.models.DescribeProxiesRequest()).getInstanceSet()), QCloudGaap::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== 对象存储 ====================

    // TODO: COS SDK (com.tencentcloudapi.cos) 未包含在当前 tencentcloud-sdk-java 版本中，需单独引入 cos_api 依赖
    public List<QCloudCos> listCos() {
        return new ArrayList<>();
    }

    // ==================== 消息队列类 ====================

    public List<QCloudRocketMQ> listRocketMQ() {
        com.tencentcloudapi.trocket.v20230308.TrocketClient client = new com.tencentcloudapi.trocket.v20230308.TrocketClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeInstanceList(new com.tencentcloudapi.trocket.v20230308.models.DescribeInstanceListRequest()).getData()), QCloudRocketMQ::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudRabbitMQ> listRabbitMQ() {
        com.tencentcloudapi.trabbit.v20230418.TrabbitClient client = new com.tencentcloudapi.trabbit.v20230418.TrabbitClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.ListRabbitMQServerlessInstances(new com.tencentcloudapi.trabbit.v20230418.models.ListRabbitMQServerlessInstancesRequest()).getInstances()), QCloudRabbitMQ::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudCmq> listCmq() {
        com.tencentcloudapi.cmq.v20190304.CmqClient client = new com.tencentcloudapi.cmq.v20190304.CmqClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeQueueDetail(new com.tencentcloudapi.cmq.v20190304.models.DescribeQueueDetailRequest()).getQueueSet()), QCloudCmq::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== 监控类 ====================

    public List<QCloudMonitor> listMonitor() {
        com.tencentcloudapi.monitor.v20180724.MonitorClient client = new com.tencentcloudapi.monitor.v20180724.MonitorClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeAllNamespaces(new com.tencentcloudapi.monitor.v20180724.models.DescribeAllNamespacesRequest()).getCommonNamespaces()), QCloudMonitor::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== 数据库类(扩展) ====================

    public List<QCloudKeeWiDB> listKeeWiDB() {
        com.tencentcloudapi.keewidb.v20220308.KeewidbClient client = new com.tencentcloudapi.keewidb.v20220308.KeewidbClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeInstances(new com.tencentcloudapi.keewidb.v20220308.models.DescribeInstancesRequest()).getInstanceSet()), QCloudKeeWiDB::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudCTSDB> listCTSDB() {
        com.tencentcloudapi.ctsdb.v20230202.CtsdbClient client = new com.tencentcloudapi.ctsdb.v20230202.CtsdbClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeClusters(new com.tencentcloudapi.ctsdb.v20230202.models.DescribeClustersRequest()).getClusters()), QCloudCTSDB::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // TODO: HBase SDK (com.tencentcloudapi.hbase) 未包含在当前 tencentcloud-sdk-java 版本中
    public List<QCloudHBase> listHBase() {
        return new ArrayList<>();
    }

    public List<QCloudTCHouseP> listTCHouseP() {
        com.tencentcloudapi.cdwdoris.v20211228.CdwdorisClient client = new com.tencentcloudapi.cdwdoris.v20211228.CdwdorisClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeInstances(new com.tencentcloudapi.cdwdoris.v20211228.models.DescribeInstancesRequest()).getInstancesList()), QCloudTCHouseP::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudTCHouseC> listTCHouseC() {
        com.tencentcloudapi.cdwdoris.v20211228.CdwdorisClient client = new com.tencentcloudapi.cdwdoris.v20211228.CdwdorisClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeInstances(new com.tencentcloudapi.cdwdoris.v20211228.models.DescribeInstancesRequest()).getInstancesList()), QCloudTCHouseC::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudTCHouseD> listTCHouseD() {
        com.tencentcloudapi.cdwdoris.v20211228.CdwdorisClient client = new com.tencentcloudapi.cdwdoris.v20211228.CdwdorisClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeInstances(new com.tencentcloudapi.cdwdoris.v20211228.models.DescribeInstancesRequest()).getInstancesList()), QCloudTCHouseD::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== API网关/配置/治理 ====================

    public List<QCloudAPIGW> listAPIGW() {
        com.tencentcloudapi.apigateway.v20180808.ApigatewayClient client = new com.tencentcloudapi.apigateway.v20180808.ApigatewayClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeServicesStatus(new com.tencentcloudapi.apigateway.v20180808.models.DescribeServicesStatusRequest()).getResult().getServiceSet()), QCloudAPIGW::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // TODO: SDK类 ListResourcesRequest 不可用，待升级SDK后实现
    public List<QCloudConfig> listConfig() {
        return new ArrayList<>(); // TODO: SDK类不可用，待升级SDK后实现
    }

    // ==================== 裸金属/容器 ====================

    public List<QCloudBMS> listBMS() {
        com.tencentcloudapi.bmvpc.v20180625.BmvpcClient client = new com.tencentcloudapi.bmvpc.v20180625.BmvpcClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeSubnets(new com.tencentcloudapi.bmvpc.v20180625.models.DescribeSubnetsRequest()).getSubnetSet()), QCloudBMS::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudCHC> listCHC() {
        com.tencentcloudapi.chc.v20230418.ChcClient client = new com.tencentcloudapi.chc.v20230418.ChcClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribePositions(new com.tencentcloudapi.chc.v20230418.models.DescribePositionsRequest()).getPositionSet()), QCloudCHC::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== 微服务平台/TSF ====================

    // TODO: SDK方法 getResults() 不可用，待升级SDK后实现
    public List<QCloudTSF> listTSF() {
        return new ArrayList<>(); // TODO: SDK方法不可用，待升级SDK后实现
    }

    // TODO: SDK方法 getResults() 不可用，待升级SDK后实现
    public List<QCloudTSE> listTSE() {
        return new ArrayList<>(); // TODO: SDK方法不可用，待升级SDK后实现
    }

    // ==================== 云开发/Serverless ====================

    public List<QCloudTCB> listTCB() {
        com.tencentcloudapi.tcb.v20180608.TcbClient client = new com.tencentcloudapi.tcb.v20180608.TcbClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeEnvs(new com.tencentcloudapi.tcb.v20180608.models.DescribeEnvsRequest()).getEnvList()), QCloudTCB::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // TODO: CSP SDK 未包含在当前 tencentcloud-sdk-java 版本中
    public List<QCloudCSP> listCSP() {
        return new ArrayList<>();
    }

    // ==================== 音视频/直播类 ====================

    public List<QCloudTRTC> listTRTC() {
        com.tencentcloudapi.trtc.v20190722.TrtcClient client = new com.tencentcloudapi.trtc.v20190722.TrtcClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeRoomInfo(new com.tencentcloudapi.trtc.v20190722.models.DescribeRoomInfoRequest()).getRoomList()), QCloudTRTC::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudTRTCRoom> listTRTCRoom() {
        com.tencentcloudapi.trtc.v20190722.TrtcClient client = new com.tencentcloudapi.trtc.v20190722.TrtcClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeRoomInfo(new com.tencentcloudapi.trtc.v20190722.models.DescribeRoomInfoRequest()).getRoomList()), QCloudTRTCRoom::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudVOD> listVOD() {
        com.tencentcloudapi.vod.v20180717.VodClient client = new com.tencentcloudapi.vod.v20180717.VodClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeSubAppIds(new com.tencentcloudapi.vod.v20180717.models.DescribeSubAppIdsRequest()).getSubAppIdInfoSet()), QCloudVOD::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudVODMedia> listVODMedia() {
        com.tencentcloudapi.vod.v20180717.VodClient client = new com.tencentcloudapi.vod.v20180717.VodClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.SearchMedia(new com.tencentcloudapi.vod.v20180717.models.SearchMediaRequest()).getMediaInfoSet()), QCloudVODMedia::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudVODProcess> listVODProcess() {
        com.tencentcloudapi.vod.v20180717.VodClient client = new com.tencentcloudapi.vod.v20180717.VodClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.SearchMedia(new com.tencentcloudapi.vod.v20180717.models.SearchMediaRequest()).getMediaInfoSet()), QCloudVODProcess::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== 游戏类 ====================

    // TODO: GSE SDK 未包含在当前 tencentcloud-sdk-java 版本中
    public List<QCloudGSE> listGSE() {
        return new ArrayList<>();
    }

    public List<QCloudGameServer> listGameServer() {
        return new ArrayList<>();
    }

    public List<QCloudGameDB> listGameDB() {
        return new ArrayList<>();
    }

    public List<QCloudGameAntiACE> listGameAntiACE() {
        return new ArrayList<>();
    }

    public List<QCloudGameVoice> listGameVoice() {
        return new ArrayList<>();
    }

    // ==================== IoT物联网 ====================

    public List<QCloudIoT> listIoT() {
        com.tencentcloudapi.iotexplorer.v20190423.IotexplorerClient client = new com.tencentcloudapi.iotexplorer.v20190423.IotexplorerClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.GetProjectList(new com.tencentcloudapi.iotexplorer.v20190423.models.GetProjectListRequest()).getProjects()), QCloudIoT::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudIoTHub> listIoTHub() {
        com.tencentcloudapi.iotexplorer.v20190423.IotexplorerClient client = new com.tencentcloudapi.iotexplorer.v20190423.IotexplorerClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.GetProjectList(new com.tencentcloudapi.iotexplorer.v20190423.models.GetProjectListRequest()).getProjects()), QCloudIoTHub::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudIoTDevice> listIoTDevice() {
        com.tencentcloudapi.iotexplorer.v20190423.IotexplorerClient client = new com.tencentcloudapi.iotexplorer.v20190423.IotexplorerClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.GetProjectList(new com.tencentcloudapi.iotexplorer.v20190423.models.GetProjectListRequest()).getProjects()), QCloudIoTDevice::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== DDoS安全 ====================

    public List<QCloudDDoS> listDDoS() {
        com.tencentcloudapi.antiddos.v20200309.AntiddosClient client = new com.tencentcloudapi.antiddos.v20200309.AntiddosClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeListBGPInstances(new com.tencentcloudapi.antiddos.v20200309.models.DescribeListBGPInstancesRequest()).getInstanceList()), QCloudDDoS::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== 区块链 ====================

    // TODO: SDK类 DescribeBlockchainClientRequest 不可用，待升级SDK后实现
    public List<QCloudTBAAS> listTBAAS() {
        return new ArrayList<>(); // TODO: SDK类不可用，待升级SDK后实现
    }

    // ==================== 网络扩展 ====================

    public List<QCloudCLB_gw> listCLB_gw() {
        com.tencentcloudapi.clb.v20180317.ClbClient client = new com.tencentcloudapi.clb.v20180317.ClbClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeLoadBalancers(new com.tencentcloudapi.clb.v20180317.models.DescribeLoadBalancersRequest()).getLoadBalancerSet()), QCloudCLB_gw::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudEO> listEO() {
        com.tencentcloudapi.teo.v20220901.TeoClient client = new com.tencentcloudapi.teo.v20220901.TeoClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeZones(new com.tencentcloudapi.teo.v20220901.models.DescribeZonesRequest()).getZones()), QCloudEO::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== 安全验证/域名 ====================

    // TODO: SDK方法 getCaptchaInfos() 不可用，待升级SDK后实现
    public List<QCloudCAPTCHA> listCAPTCHA() {
        return new ArrayList<>(); // TODO: SDK方法不可用，待升级SDK后实现
    }

    public List<QCloudDomainReg> listDomainReg() {
        com.tencentcloudapi.domain.v20180808.DomainClient client = new com.tencentcloudapi.domain.v20180808.DomainClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeDomainNameList(new com.tencentcloudapi.domain.v20180808.models.DescribeDomainNameListRequest()).getDomainSet()), QCloudDomainReg::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // TODO: GTM SDK 未包含在当前 tencentcloud-sdk-java 版本中，igtm 存在但 API 不同
    public List<QCloudGTM> listGTM() {
        return new ArrayList<>();
    }

    public List<QCloudDNSSec> listDNSSec() {
        com.tencentcloudapi.dnspod.v20210323.DnspodClient client = new com.tencentcloudapi.dnspod.v20210323.DnspodClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeDomainList(new com.tencentcloudapi.dnspod.v20210323.models.DescribeDomainListRequest()).getDomainList()), QCloudDNSSec::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // TODO: SDK类 DescribePrivateZonesRequest 不可用，待升级SDK后实现
    public List<QCloudDNSPrivate> listDNSPrivate() {
        return new ArrayList<>(); // TODO: SDK类不可用，待升级SDK后实现
    }

    // TODO: SDK类 DescribePrivateZonesRequest 不可用，待升级SDK后实现
    public List<QCloudPrivDNS> listPrivDNS() {
        return new ArrayList<>(); // TODO: SDK类不可用，待升级SDK后实现
    }

    // ==================== 短信模板 ====================

    public List<QCloudSmsTemplate> listSmsTemplate() {
        com.tencentcloudapi.sms.v20210111.SmsClient client = new com.tencentcloudapi.sms.v20210111.SmsClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeSmsTemplateList(new com.tencentcloudapi.sms.v20210111.models.DescribeSmsTemplateListRequest()).getDescribeTemplateStatusSet()), QCloudSmsTemplate::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== 数据/BI ====================

    public List<QCloudWeData> listWeData() {
        com.tencentcloudapi.wedata.v20210820.WedataClient client = new com.tencentcloudapi.wedata.v20210820.WedataClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeProject(new com.tencentcloudapi.wedata.v20210820.models.DescribeProjectRequest()).getData()), QCloudWeData::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // TODO: SDK方法 getProjectList() 不可用，待升级SDK后实现
    public List<QCloudBI> listBI() {
        return new ArrayList<>(); // TODO: SDK方法不可用，待升级SDK后实现
    }

    // ==================== 云手机 ====================

    public List<QCloudGHPhone> listGHPhone() {
        com.tencentcloudapi.chc.v20230418.ChcClient client = new com.tencentcloudapi.chc.v20230418.ChcClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribePositions(new com.tencentcloudapi.chc.v20230418.models.DescribePositionsRequest()).getPositionSet()), QCloudGHPhone::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudCloudPhone> listCloudPhone() {
        com.tencentcloudapi.chc.v20230418.ChcClient client = new com.tencentcloudapi.chc.v20230418.ChcClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribePositions(new com.tencentcloudapi.chc.v20230418.models.DescribePositionsRequest()).getPositionSet()), QCloudCloudPhone::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== AI/ML扩展 ====================

    // TODO: SDK类 DescribePackagesRequest 不可用，待升级SDK后实现
    public List<QCloudOCR> listOCR() {
        return new ArrayList<>(); // TODO: SDK类不可用，待升级SDK后实现
    }

    // TODO: SDK类 DescribeTaskListRequest 不可用，待升级SDK后实现
    public List<QCloudASR> listASR() {
        return new ArrayList<>(); // TODO: SDK类不可用，待升级SDK后实现
    }

    // TODO: TTS SDK 未提供 DescribeVoices API，仅有 DescribeTtsTaskStatus
    public List<QCloudTTS> listTTS() {
        return new ArrayList<>();
    }

    // TODO: SDK类 DescribeTMTServiceRequest 不可用，待升级SDK后实现
    public List<QCloudNMT> listNMT() {
        return new ArrayList<>(); // TODO: SDK类不可用，待升级SDK后实现
    }

    public List<QCloudTI> listTI() {
        com.tencentcloudapi.tiia.v20190529.TiiaClient client = new com.tencentcloudapi.tiia.v20190529.TiiaClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeGroups(new com.tencentcloudapi.tiia.v20190529.models.DescribeGroupsRequest()).getGroups()), QCloudTI::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // TODO: SDK类 DescribeBizCategoriesRequest 不可用，待升级SDK后实现
    public List<QCloudContentSafe> listContentSafe() {
        return new ArrayList<>(); // TODO: SDK类不可用，待升级SDK后实现
    }

    // TODO: SDK类 DescribePackagesRequest 不可用，待升级SDK后实现
    public List<QCloudContentRecognize> listContentRecognize() {
        return new ArrayList<>(); // TODO: SDK类不可用，待升级SDK后实现
    }

    // ==================== 内容安全 ====================

    // TODO: SDK方法 getResults() 不可用，待升级SDK后实现
    public List<QCloudSafeImage> listSafeImage() {
        return new ArrayList<>(); // TODO: SDK方法不可用，待升级SDK后实现
    }

    // TODO: SDK类 DescribeBizCategoriesRequest 不可用，待升级SDK后实现
    public List<QCloudSafeText> listSafeText() {
        return new ArrayList<>(); // TODO: SDK类不可用，待升级SDK后实现
    }

    // TODO: SDK方法 getBizConfig() 不可用，待升级SDK后实现
    public List<QCloudSafeAudio> listSafeAudio() {
        return new ArrayList<>(); // TODO: SDK方法不可用，待升级SDK后实现
    }

    // TODO: SDK方法 getBizConfig() 不可用，待升级SDK后实现
    public List<QCloudSafeVideo> listSafeVideo() {
        return new ArrayList<>(); // TODO: SDK方法不可用，待升级SDK后实现
    }

    // TODO: SDK类 DescribeBizCategoriesRequest 不可用，待升级SDK后实现
    public List<QCloudSafeDoc> listSafeDoc() {
        return new ArrayList<>(); // TODO: SDK类不可用，待升级SDK后实现
    }

    // ==================== 安全中心/堡垒机/审计 ====================

    public List<QCloudAudit> listAudit() {
        com.tencentcloudapi.cloudaudit.v20190319.CloudauditClient client = new com.tencentcloudapi.cloudaudit.v20190319.CloudauditClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeAuditTracks(new com.tencentcloudapi.cloudaudit.v20190319.models.DescribeAuditTracksRequest()).getTracks()), QCloudAudit::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudBastion> listBastion() {
        com.tencentcloudapi.bh.v20230418.BhClient client = new com.tencentcloudapi.bh.v20230418.BhClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeResources(new com.tencentcloudapi.bh.v20230418.models.DescribeResourcesRequest()).getResourceSet()), QCloudBastion::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudExposedMgr> listExposedMgr() {
        com.tencentcloudapi.csip.v20221121.CsipClient client = new com.tencentcloudapi.csip.v20221121.CsipClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeAssetRiskList(new com.tencentcloudapi.csip.v20221121.models.DescribeAssetRiskListRequest()).getAssetRiskList()), QCloudExposedMgr::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudSafeGuard> listSafeGuard() {
        com.tencentcloudapi.csip.v20221121.CsipClient client = new com.tencentcloudapi.csip.v20221121.CsipClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeAssetRiskList(new com.tencentcloudapi.csip.v20221121.models.DescribeAssetRiskListRequest()).getAssetRiskList()), QCloudSafeGuard::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudSafeCenter> listSafeCenter() {
        com.tencentcloudapi.csip.v20221121.CsipClient client = new com.tencentcloudapi.csip.v20221121.CsipClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeAssetRiskList(new com.tencentcloudapi.csip.v20221121.models.DescribeAssetRiskListRequest()).getAssetRiskList()), QCloudSafeCenter::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudSafeMonitor> listSafeMonitor() {
        com.tencentcloudapi.csip.v20221121.CsipClient client = new com.tencentcloudapi.csip.v20221121.CsipClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeAssetRiskList(new com.tencentcloudapi.csip.v20221121.models.DescribeAssetRiskListRequest()).getAssetRiskList()), QCloudSafeMonitor::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudSafePlatform> listSafePlatform() {
        com.tencentcloudapi.csip.v20221121.CsipClient client = new com.tencentcloudapi.csip.v20221121.CsipClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeAssetRiskList(new com.tencentcloudapi.csip.v20221121.models.DescribeAssetRiskListRequest()).getAssetRiskList()), QCloudSafePlatform::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudRiskIdentify> listRiskIdentify() {
        com.tencentcloudapi.csip.v20221121.CsipClient client = new com.tencentcloudapi.csip.v20221121.CsipClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeAssetRiskList(new com.tencentcloudapi.csip.v20221121.models.DescribeAssetRiskListRequest()).getAssetRiskList()), QCloudRiskIdentify::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudVulnMgr> listVulnMgr() {
        com.tencentcloudapi.csip.v20221121.CsipClient client = new com.tencentcloudapi.csip.v20221121.CsipClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeVULList(new com.tencentcloudapi.csip.v20221121.models.DescribeVULListRequest()).getData()), QCloudVulnMgr::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudSecCredential> listSecCredential() {
        com.tencentcloudapi.cam.v20190116.CamClient client = new com.tencentcloudapi.cam.v20190116.CamClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.ListAccessKeys(new com.tencentcloudapi.cam.v20190116.models.ListAccessKeysRequest()).getAccessKeys()), QCloudSecCredential::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudCACert> listCACert() {
        com.tencentcloudapi.ssl.v20191205.SslClient client = new com.tencentcloudapi.ssl.v20191205.SslClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeCertificates(new com.tencentcloudapi.ssl.v20191205.models.DescribeCertificatesRequest()).getCertificates()), QCloudCACert::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== 组织 ====================

    public List<QCloudOrg> listOrg() {
        com.tencentcloudapi.organization.v20210331.OrganizationClient client = new com.tencentcloudapi.organization.v20210331.OrganizationClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeOrganizationMembers(new com.tencentcloudapi.organization.v20210331.models.DescribeOrganizationMembersRequest()).getItems()), QCloudOrg::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== 分发/文档处理 ====================

    // TODO: ssminstance SDK 未包含在当前 tencentcloud-sdk-java 版本中
    public List<QCloudDistID> listDistID() {
        return new ArrayList<>();
    }

    // TODO: CI SDK 未包含在当前 tencentcloud-sdk-java 版本中
    public List<QCloudDocProcess> listDocProcess() {
        return new ArrayList<>();
    }

    // ==================== 面部特效 ====================

    public List<QCloudFaceDeform> listFaceDeform() {
        com.tencentcloudapi.facefusion.v20220927.FacefusionClient client = new com.tencentcloudapi.facefusion.v20220927.FacefusionClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeMaterialList(new com.tencentcloudapi.facefusion.v20220927.models.DescribeMaterialListRequest()).getMaterialInfos()), QCloudFaceDeform::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudFaceMakeup> listFaceMakeup() {
        com.tencentcloudapi.facefusion.v20220927.FacefusionClient client = new com.tencentcloudapi.facefusion.v20220927.FacefusionClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeMaterialList(new com.tencentcloudapi.facefusion.v20220927.models.DescribeMaterialListRequest()).getMaterialInfos()), QCloudFaceMakeup::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudFaceSwap> listFaceSwap() {
        com.tencentcloudapi.facefusion.v20220927.FacefusionClient client = new com.tencentcloudapi.facefusion.v20220927.FacefusionClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeMaterialList(new com.tencentcloudapi.facefusion.v20220927.models.DescribeMaterialListRequest()).getMaterialInfos()), QCloudFaceSwap::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== 健康/教育/医疗(细分领域) ====================

    // TODO: HCM SDK 未提供 DescribeHCMClient API，仅有 Evaluation
    public List<QCloudHealthDash> listHealthDash() {
        return new ArrayList<>();
    }

    public List<QCloudSmartGuide> listSmartGuide() {
        return new ArrayList<>();
    }

    // ==================== 开发工具 ====================

    // TODO: Coding SDK 未包含在当前 tencentcloud-sdk-java 版本中
    public List<QCloudCodingDevops> listCodingDevops() {
        return new ArrayList<>();
    }

    public List<QCloudCloudStudio> listCloudStudio() {
        com.tencentcloudapi.cloudstudio.v20230508.CloudstudioClient client = new com.tencentcloudapi.cloudstudio.v20230508.CloudstudioClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeWorkspaces(new com.tencentcloudapi.cloudstudio.v20230508.models.DescribeWorkspacesRequest()).getData()), QCloudCloudStudio::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== 容器/CSP网关 ====================

    // TODO: CSP SDK 未包含在当前 tencentcloud-sdk-java 版本中
    public List<QCloudCSPGateway> listCSPGateway() {
        return new ArrayList<>();
    }

    // ==================== 企业/管控 ====================

    public List<QCloudControlCenter> listControlCenter() {
        com.tencentcloudapi.organization.v20210331.OrganizationClient client = new com.tencentcloudapi.organization.v20210331.OrganizationClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeOrganizationMembers(new com.tencentcloudapi.organization.v20210331.models.DescribeOrganizationMembersRequest()).getItems()), QCloudControlCenter::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudDataAudit> listDataAudit() {
        com.tencentcloudapi.cloudaudit.v20190319.CloudauditClient client = new com.tencentcloudapi.cloudaudit.v20190319.CloudauditClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeAuditTracks(new com.tencentcloudapi.cloudaudit.v20190319.models.DescribeAuditTracksRequest()).getTracks()), QCloudDataAudit::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudDataSafeGov> listDataSafeGov() {
        com.tencentcloudapi.csip.v20221121.CsipClient client = new com.tencentcloudapi.csip.v20221121.CsipClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeAssetRiskList(new com.tencentcloudapi.csip.v20221121.models.DescribeAssetRiskListRequest()).getAssetRiskList()), QCloudDataSafeGov::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudDeviceSafety> listDeviceSafety() {
        com.tencentcloudapi.csip.v20221121.CsipClient client = new com.tencentcloudapi.csip.v20221121.CsipClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeAssetRiskList(new com.tencentcloudapi.csip.v20221121.models.DescribeAssetRiskListRequest()).getAssetRiskList()), QCloudDeviceSafety::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== AI Agent/平台类 ====================

    public List<QCloudAgentPlatform> listAgentPlatform() {
        com.tencentcloudapi.lkeap.v20240522.LkeapClient client = new com.tencentcloudapi.lkeap.v20240522.LkeapClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.ListDocs(new com.tencentcloudapi.lkeap.v20240522.models.ListDocsRequest()).getList()), QCloudAgentPlatform::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudAgentGW> listAgentGW() {
        com.tencentcloudapi.lkeap.v20240522.LkeapClient client = new com.tencentcloudapi.lkeap.v20240522.LkeapClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.ListDocs(new com.tencentcloudapi.lkeap.v20240522.models.ListDocsRequest()).getList()), QCloudAgentGW::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudKnowledgeEngine> listKnowledgeEngine() {
        com.tencentcloudapi.lkeap.v20240522.LkeapClient client = new com.tencentcloudapi.lkeap.v20240522.LkeapClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.ListDocs(new com.tencentcloudapi.lkeap.v20240522.models.ListDocsRequest()).getList()), QCloudKnowledgeEngine::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudTokenHub> listTokenHub() {
        com.tencentcloudapi.lkeap.v20240522.LkeapClient client = new com.tencentcloudapi.lkeap.v20240522.LkeapClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.ListDocs(new com.tencentcloudapi.lkeap.v20240522.models.ListDocsRequest()).getList()), QCloudTokenHub::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== Web搜索 ====================

    // TODO: TAV SDK 未包含在当前 tencentcloud-sdk-java 版本中
    public List<QCloudWebSearch> listWebSearch() {
        return new ArrayList<>();
    }

    // ==================== 低代码/微搭 ====================

    public List<QCloudWeda> listWeda() {
        com.tencentcloudapi.wedata.v20210820.WedataClient client = new com.tencentcloudapi.wedata.v20210820.WedataClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeProject(new com.tencentcloudapi.wedata.v20210820.models.DescribeProjectRequest()).getData()), QCloudWeda::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudMicroWeda> listMicroWeda() {
        com.tencentcloudapi.wedata.v20210820.WedataClient client = new com.tencentcloudapi.wedata.v20210820.WedataClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeProject(new com.tencentcloudapi.wedata.v20210820.models.DescribeProjectRequest()).getData()), QCloudMicroWeda::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== 企业连接/WeLink ====================

    public List<QCloudTencentConnect> listTencentConnect() {
        com.tencentcloudapi.wedata.v20210820.WedataClient client = new com.tencentcloudapi.wedata.v20210820.WedataClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeProject(new com.tencentcloudapi.wedata.v20210820.models.DescribeProjectRequest()).getData()), QCloudTencentConnect::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudWeLink> listWeLink() {
        com.tencentcloudapi.wedata.v20210820.WedataClient client = new com.tencentcloudapi.wedata.v20210820.WedataClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeProject(new com.tencentcloudapi.wedata.v20210820.models.DescribeProjectRequest()).getData()), QCloudWeLink::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== 风控 ====================

    public List<QCloudMiniSafe> listMiniSafe() {
        com.tencentcloudapi.csip.v20221121.CsipClient client = new com.tencentcloudapi.csip.v20221121.CsipClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeAssetRiskList(new com.tencentcloudapi.csip.v20221121.models.DescribeAssetRiskListRequest()).getAssetRiskList()), QCloudMiniSafe::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== ICP备案 ====================

    // TODO: ICR SDK 未包含在当前 tencentcloud-sdk-java 版本中
    public List<QCloudICPBeian> listICPBeian() {
        return new ArrayList<>();
    }

    // ==================== 零信任IOA ====================

    public List<QCloudIOA> listIOA() {
        com.tencentcloudapi.ioa.v20220601.IoaClient client = new com.tencentcloudapi.ioa.v20220601.IoaClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeDevices(new com.tencentcloudapi.ioa.v20220601.models.DescribeDevicesRequest()).getData()), QCloudIOA::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== 测试 ====================

    // TODO: TSW SDK API 不完整，需后续确认
    public List<QCloudSmartView> listSmartView() {
        return new ArrayList<>();
    }

    public List<QCloudSmartAdvisor> listSmartAdvisor() {
        return new ArrayList<>();
    }

    // ==================== 录音/语音消息 ====================

    public List<QCloudVoiceMsg> listVoiceMsg() {
        com.tencentcloudapi.trtc.v20190722.TrtcClient client = new com.tencentcloudapi.trtc.v20190722.TrtcClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeRoomInfo(new com.tencentcloudapi.trtc.v20190722.models.DescribeRoomInfoRequest()).getRoomList()), QCloudVoiceMsg::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudVoiceClone> listVoiceClone() {
        return new ArrayList<>();
    }

    // ==================== 会议 ====================

    // TODO: WeMeet SDK API 待确认
    public List<QCloudMeeting> listMeeting() {
        return new ArrayList<>();
    }

    // ==================== 电子签 ====================

    public List<QCloudESign> listESign() {
        com.tencentcloudapi.ess.v20201111.EssClient client = new com.tencentcloudapi.ess.v20201111.EssClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeFlowTemplates(new com.tencentcloudapi.ess.v20201111.models.DescribeFlowTemplatesRequest()).getTemplates()), QCloudESign::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== 云渲染 ====================

    public List<QCloudAppRender> listAppRender() {
        com.tencentcloudapi.gaap.v20180529.GaapClient client = new com.tencentcloudapi.gaap.v20180529.GaapClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeProxies(new com.tencentcloudapi.gaap.v20180529.models.DescribeProxiesRequest()).getInstanceSet()), QCloudAppRender::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== PenTest渗透测试 ====================

    public List<QCloudPenTest> listPenTest() {
        com.tencentcloudapi.csip.v20221121.CsipClient client = new com.tencentcloudapi.csip.v20221121.CsipClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeAssetRiskList(new com.tencentcloudapi.csip.v20221121.models.DescribeAssetRiskListRequest()).getAssetRiskList()), QCloudPenTest::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== 智慧文旅/大数据 ====================

    public List<QCloudTourismBigdata> listTourismBigdata() {
        com.tencentcloudapi.thpc.v20220401.ThpcClient client = new com.tencentcloudapi.thpc.v20220401.ThpcClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeClusters(new com.tencentcloudapi.thpc.v20220401.models.DescribeClustersRequest()).getClusterSet()), QCloudTourismBigdata::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== 媒体/文档 ====================

    public List<QCloudDocs> listDocs() {
        com.tencentcloudapi.dnspod.v20210323.DnspodClient client = new com.tencentcloudapi.dnspod.v20210323.DnspodClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeDomainList(new com.tencentcloudapi.dnspod.v20210323.models.DescribeDomainListRequest()).getDomainList()), QCloudDocs::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== 教育/工业RTI ====================

    public List<QCloudRTIEdu> listRTIEdu() {
        com.tencentcloudapi.trtc.v20190722.TrtcClient client = new com.tencentcloudapi.trtc.v20190722.TrtcClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeRoomInfo(new com.tencentcloudapi.trtc.v20190722.models.DescribeRoomInfoRequest()).getRoomList()), QCloudRTIEdu::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    public List<QCloudRTIIndustrial> listRTIIndustrial() {
        com.tencentcloudapi.trtc.v20190722.TrtcClient client = new com.tencentcloudapi.trtc.v20190722.TrtcClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeRoomInfo(new com.tencentcloudapi.trtc.v20190722.models.DescribeRoomInfoRequest()).getRoomList()), QCloudRTIIndustrial::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== Native Build ====================

    public List<QCloudNativeBuild> listNativeBuild() {
        com.tencentcloudapi.tcb.v20180608.TcbClient client = new com.tencentcloudapi.tcb.v20180608.TcbClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeEnvs(new com.tencentcloudapi.tcb.v20180608.models.DescribeEnvsRequest()).getEnvList()), QCloudNativeBuild::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== Region管理 ====================

    public List<QCloudRegionMgr> listRegionMgr() {
        com.tencentcloudapi.cvm.v20170312.CvmClient client = new com.tencentcloudapi.cvm.v20170312.CvmClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeRegions(new com.tencentcloudapi.cvm.v20170312.models.DescribeRegionsRequest()).getRegionSet()), QCloudRegionMgr::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== 健康/基因组学 ====================

    public List<QCloudHealthOmics> listHealthOmics() {
        return new ArrayList<>();
    }

    public List<QCloudHealthReport2> listHealthReport2() {
        return new ArrayList<>();
    }

    // ==================== 图像处理 ====================

    public List<QCloudImageProcess2> listImageProcess2() {
        com.tencentcloudapi.tiia.v20190529.TiiaClient client = new com.tencentcloudapi.tiia.v20190529.TiiaClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeGroups(new com.tencentcloudapi.tiia.v20190529.models.DescribeGroupsRequest()).getGroups()), QCloudImageProcess2::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== 数学/教育评估 ====================

    public List<QCloudMathGrade> listMathGrade() {
        return new ArrayList<>();
    }

    // TODO: SOE SDK 未提供列表查询 API
    public List<QCloudSpokenEval> listSpokenEval() {
        return new ArrayList<>();
    }

    public List<QCloudEngWrite> listEngWrite() {
        return new ArrayList<>();
    }

    // ==================== 商业/营销 ====================

    public List<QCloudMallTraffic> listMallTraffic() {
        com.tencentcloudapi.thpc.v20220401.ThpcClient client = new com.tencentcloudapi.thpc.v20220401.ThpcClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeClusters(new com.tencentcloudapi.thpc.v20220401.models.DescribeClustersRequest()).getClusterSet()), QCloudMallTraffic::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== TAPD ====================

    public List<QCloudTAPD> listTAPD() {
        com.tencentcloudapi.thpc.v20220401.ThpcClient client = new com.tencentcloudapi.thpc.v20220401.ThpcClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeClusters(new com.tencentcloudapi.thpc.v20220401.models.DescribeClustersRequest()).getClusterSet()), QCloudTAPD::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== 媒体/音视频资产 ====================

    public List<QCloudMediaAsset> listMediaAsset() {
        com.tencentcloudapi.mps.v20190612.MpsClient client = new com.tencentcloudapi.mps.v20190612.MpsClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeWorkflows(new com.tencentcloudapi.mps.v20190612.models.DescribeWorkflowsRequest()).getWorkflowInfoSet()), QCloudMediaAsset::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== CloudBase ====================

    public List<QCloudCloudBase> listCloudBase() {
        com.tencentcloudapi.tcb.v20180608.TcbClient client = new com.tencentcloudapi.tcb.v20180608.TcbClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeEnvs(new com.tencentcloudapi.tcb.v20180608.models.DescribeEnvsRequest()).getEnvList()), QCloudCloudBase::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== 企业通信 ====================

    public List<QCloudCloudContact> listCloudContact() {
        com.tencentcloudapi.csip.v20221121.CsipClient client = new com.tencentcloudapi.csip.v20221121.CsipClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeAssetRiskList(new com.tencentcloudapi.csip.v20221121.models.DescribeAssetRiskListRequest()).getAssetRiskList()), QCloudCloudContact::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== BizProcess业务流程 ====================

    public List<QCloudBizProcess> listBizProcess() {
        com.tencentcloudapi.mps.v20190612.MpsClient client = new com.tencentcloudapi.mps.v20190612.MpsClient(credential, region);
        try {
            return BeanUtils.cgLibCopyList(ListUtil.toList(client.DescribeWorkflows(new com.tencentcloudapi.mps.v20190612.models.DescribeWorkflowsRequest()).getWorkflowInfoSet()), QCloudBizProcess::new);
        } catch (TencentCloudSDKException e) { throw new ClientException(e); }
    }

    // ==================== 子产品 ====================

    public List<QCloudGpuCvm> listGpuCvm() {
        return BeanUtils.cgLibCopyList(listCvm(), QCloudGpuCvm::new);
    }

    public List<QCloudFpgaCvm> listFpgaCvm() {
        return BeanUtils.cgLibCopyList(listCvm(), QCloudFpgaCvm::new);
    }

    public List<QCloudCvmDedicated> listCvmDedicated() {
        return BeanUtils.cgLibCopyList(listCvm(), QCloudCvmDedicated::new);
    }

    public List<QCloudHpcCluster> listHpcCluster() {
        // TODO: 独立 API
        return new ArrayList<>();
    }

    public List<QCloudHpcPlatform> listHpcPlatform() {
        // TODO: 独立 API
        return new ArrayList<>();
    }

    public List<QCloudGooseFS> listGooseFS() {
        // TODO: 独立 API
        return new ArrayList<>();
    }

    public List<QCloudCi> listCi() {
        // TODO: 独立 API
        return new ArrayList<>();
    }

    public List<QCloudSmartMedia> listSmartMedia() {
        // TODO: 独立 API
        return new ArrayList<>();
    }

    public List<QCloudTdsqlBoundless> listTdsqlBoundless() {
        return BeanUtils.cgLibCopyList(listDCDB(), QCloudTdsqlBoundless::new);
    }

    public List<QCloudVectorDb> listVectorDb() {
        // TODO: 独立 API
        return new ArrayList<>();
    }

    public List<QCloudTdsqlDistributed> listTdsqlDistributed() {
        return BeanUtils.cgLibCopyList(listDCDB(), QCloudTdsqlDistributed::new);
    }

    public List<QCloudDesktop> listDesktop() {
        // TODO: 独立 API
        return new ArrayList<>();
    }

    public List<QCloudFlowLog> listFlowLog() {
        // TODO: 独立 API
        return new ArrayList<>();
    }

    public List<QCloudBandwidthPackage> listBandwidthPackage() {
        // TODO: 独立 API
        return new ArrayList<>();
    }

    public List<QCloudTrafficPackage> listTrafficPackage() {
        // TODO: 独立 API
        return new ArrayList<>();
    }

    public List<QCloudIpv6> listIpv6() {
        // TODO: 独立 API
        return new ArrayList<>();
    }

    public List<QCloudCc> listCc() {
        // TODO: 独立 API
        return new ArrayList<>();
    }

    public List<QCloudVpn> listVpn() {
        // TODO: 独立 API
        return new ArrayList<>();
    }

    public List<QCloudPeering> listPeering() {
        // TODO: 独立 API
        return new ArrayList<>();
    }

    public List<QCloudSdwan> listSdwan() {
        // TODO: 独立 API
        return new ArrayList<>();
    }

    public List<QCloudWsA> listWsA() {
        return BeanUtils.cgLibCopyList(listCdnDomain(), QCloudWsA::new);
    }

    public List<QCloudScdn> listScdn() {
        return BeanUtils.cgLibCopyList(listCdnDomain(), QCloudScdn::new);
    }

    public List<QCloudEcm> listEcm() {
        // TODO: 独立 API
        return new ArrayList<>();
    }

    public List<QCloudIm> listIm() {
        // TODO: 独立 API
        return new ArrayList<>();
    }

    public List<QCloudMps> listMps() {
        // TODO: 独立 API
        return new ArrayList<>();
    }

    public List<QCloudEnhanceMedia> listEnhanceMedia() {
        // TODO: 独立 API
        return new ArrayList<>();
    }

    public List<QCloudMediaAi> listMediaAi() {
        // TODO: 独立 API
        return new ArrayList<>();
    }

    public List<QCloudThreatIntel> listThreatIntel() {
        // TODO: 独立 API
        return new ArrayList<>();
    }

    public List<QCloudAntiFraud> listAntiFraud() {
        // TODO: 独立 API
        return new ArrayList<>();
    }

    public List<QCloudCfw> listCfw() {
        // TODO: 独立 API
        return new ArrayList<>();
    }

    public List<QCloudGaapV2> listGaapV2() {
        // TODO: 独立 API
        return new ArrayList<>();
    }

    public List<QCloudDedicatedZone> listDedicatedZone() {
        // TODO: 独立 API
        return new ArrayList<>();
    }

    public List<QCloudEdgeZone> listEdgeZone() {
        // TODO: 独立 API
        return new ArrayList<>();
    }
}
