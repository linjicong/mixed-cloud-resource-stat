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
import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.util.ArrayUtil;
import com.huaweicloud.sdk.core.auth.BasicCredentials;
import com.huaweicloud.sdk.core.auth.ICredential;
import com.linjicong.cloud.stat.dao.entity.CloudConf;
import com.linjicong.cloud.stat.dao.entity.hcloud.HCloudEcs;
import com.linjicong.cloud.stat.dao.entity.qcloud.QCloudBillResourceSummary;
import com.linjicong.cloud.stat.dao.entity.qcloud.QCloudCdb;
import com.linjicong.cloud.stat.dao.entity.qcloud.QCloudCvm;
import com.linjicong.cloud.stat.exception.ClientException;
import com.linjicong.cloud.stat.util.BeanUtils;
import com.tencentcloudapi.billing.v20180709.BillingClient;
import com.tencentcloudapi.billing.v20180709.models.BillResourceSummary;
import com.tencentcloudapi.billing.v20180709.models.DescribeBillResourceSummaryRequest;
import com.tencentcloudapi.billing.v20180709.models.DescribeBillResourceSummaryResponse;
import com.tencentcloudapi.cdb.v20170320.CdbClient;
import com.tencentcloudapi.cdb.v20170320.models.DescribeDBInstancesRequest;
import com.tencentcloudapi.cdb.v20170320.models.DescribeDBInstancesResponse;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.Region;
import com.tencentcloudapi.cvm.v20170312.CvmClient;
import com.tencentcloudapi.cvm.v20170312.models.DescribeInstancesRequest;
import com.tencentcloudapi.cvm.v20170312.models.Instance;

import java.util.ArrayList;
import java.util.List;

/**
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 */
public class QCloudClient{
    private final Credential credential;
    private final String region;

    public QCloudClient(CloudConf cloudConf) {
        this.credential = new Credential(cloudConf.getAccessKey(),cloudConf.getSecretKey());
        this.region=cloudConf.getRegion();
    }


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
}
