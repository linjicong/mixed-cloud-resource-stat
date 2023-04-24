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
import com.tencentcloudapi.cam.v20190116.models.ListUsersRequest;
import com.tencentcloudapi.cam.v20190116.models.ListUsersResponse;
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

import java.util.ArrayList;
import java.util.List;

/**
 * 腾讯云-客户端
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 */
public class QCloudClient{
    private final Credential credential;
    private final String region;

    /**
     * 腾讯云客户端
     * @param cloudConf
     */
    public QCloudClient(CloudConf cloudConf) {
        this.credential = new Credential(cloudConf.getAccessKey(),cloudConf.getSecretKey());
        this.region=cloudConf.getRegion();

        String name = cloudConf.getName();
        String provider = cloudConf.getProvider();
        // 先存入共享变量,后面mybatis拦截器要使用,插入公共字段
        BasicEntityExtend entityExtend=new BasicEntityExtend(name,provider,region);
        ThreadLocalUtil.put("entityExtend",entityExtend);
    }

    /**
     * 腾讯云-虚拟机
     * @return
     */
    public List<QCloudCvm> listCvm() {
        CvmClient client = new CvmClient(credential, region);
        DescribeInstancesRequest describeInstancesRequest = new DescribeInstancesRequest();
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
            return BeanUtils.cgLibCopyList(billResourceSummaries, QCloudBillResourceSummary::new);
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
}
