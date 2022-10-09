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

import com.aliyun.alidns20150109.Client;
import com.aliyun.alidns20150109.models.DescribeDnsGtmInstancesRequest;
import com.aliyun.alidns20150109.models.DescribeDomainRecordsRequest;
import com.aliyun.alidns20150109.models.DescribeDomainsRequest;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.linjicong.cloud.stat.dao.entity.BasicEntityExtend;
import com.linjicong.cloud.stat.dao.entity.CloudConf;
import com.linjicong.cloud.stat.dao.entity.acloud.ACloudDnsDomain;
import com.linjicong.cloud.stat.dao.entity.acloud.ACloudDnsDomainRecords;
import com.linjicong.cloud.stat.util.BeanUtils;
import com.linjicong.cloud.stat.util.ThreadLocalUtil;

import java.util.List;


/**
 * @author linjicong
 * @date 2022-07-28-14:36
 * @version 1.0.0
 */
public class ACloudClient {

    private final String region;
    private final String accessKey;
    private final String secretKey;

    public static final String ALIDNS = "alidns";

    public ACloudClient(CloudConf cloudConf) {
        String name = cloudConf.getName();
        String provider = cloudConf.getProvider();
        this.accessKey = cloudConf.getAccessKey();
        this.secretKey = cloudConf.getSecretKey();
        this.region = cloudConf.getRegion();

        // 先存入共享变量,后面mybatis拦截器要使用,插入公共字段
        BasicEntityExtend entityExtend=new BasicEntityExtend(name,provider,region);
        ThreadLocalUtil.put("entityExtend",entityExtend);
    }

    /**
     * 构造配置
     */
    private Config generateConfig(String serviceName) {
        Config config=new Config().setAccessKeyId(accessKey).setAccessKeySecret(secretKey);
        config.setEndpoint(serviceName + "."+region + ".aliyuncs.com");
        return config;
    }

    public List<ACloudDnsDomain> listDnsDomain() {
        try {
            Client client = new Client(generateConfig(ALIDNS));
            return BeanUtils.cgLibCopyList(client.describeDomainsWithOptions(new DescribeDomainsRequest().setPageSize(100L),new RuntimeOptions()).getBody().getDomains().getDomain(), ACloudDnsDomain::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<ACloudDnsDomainRecords> listDnsDomainRecords(String domainName) {
        try {
            Client client = new Client(generateConfig(ALIDNS));
            return BeanUtils.cgLibCopyList(client.describeDomainRecords(new DescribeDomainRecordsRequest().setPageSize(500L).setDomainName(domainName)).getBody().getDomainRecords().getRecord(), ACloudDnsDomainRecords::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
