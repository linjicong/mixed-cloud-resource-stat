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
 * 阿里云客户端
 * 用于调用阿里云API获取资源信息
 * 主要支持DNS域名相关操作
 * 
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
public class ACloudClient {

    /** 区域 */
    private final String region;
    /** 访问密钥ID */
    private final String accessKey;
    /** 访问密钥Secret */
    private final String secretKey;

    /** 阿里云DNS服务名称 */
    public static final String ALIDNS = "alidns";

    /**
     * 构造阿里云客户端
     * 
     * @param cloudConf 云配置信息，包含访问密钥、区域等
     */
    public ACloudClient(CloudConf cloudConf) {
        String name = cloudConf.getName();
        String provider = cloudConf.getProvider();
        this.accessKey = cloudConf.getAccessKey();
        this.secretKey = cloudConf.getSecretKey();
        this.region = cloudConf.getRegion();

        // 将扩展信息存入ThreadLocal，供MyBatis拦截器使用，用于自动填充公共字段
        BasicEntityExtend entityExtend = new BasicEntityExtend(name, provider, region);
        ThreadLocalUtil.put("entityExtend", entityExtend);
    }

    /**
     * 生成阿里云服务配置
     * 
     * @param serviceName 服务名称（如：alidns）
     * @return 配置对象
     */
    private Config generateConfig(String serviceName) {
        Config config = new Config()
                .setAccessKeyId(accessKey)
                .setAccessKeySecret(secretKey);
        // 设置服务端点：服务名.区域.aliyuncs.com
        config.setEndpoint(serviceName + "." + region + ".aliyuncs.com");
        return config;
    }

    /**
     * 查询阿里云域名列表
     * 
     * @return 域名列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudDnsDomain> listDnsDomain() {
        try {
            Client client = new Client(generateConfig(ALIDNS));
            // 调用API获取域名列表，每页100条
            return BeanUtils.cgLibCopyList(
                    client.describeDomainsWithOptions(
                            new DescribeDomainsRequest().setPageSize(100L),
                            new RuntimeOptions()
                    ).getBody().getDomains().getDomain(),
                    ACloudDnsDomain::new
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云域名列表失败", e);
        }
    }

    /**
     * 获取指定域名的解析记录列表
     * 
     * @param domainName 域名
     * @return 解析记录列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudDnsDomainRecords> listDnsDomainRecords(String domainName) {
        try {
            Client client = new Client(generateConfig(ALIDNS));
            // 调用API获取域名解析记录，每页500条
            return BeanUtils.cgLibCopyList(
                    client.describeDomainRecords(
                            new DescribeDomainRecordsRequest()
                                    .setPageSize(500L)
                                    .setDomainName(domainName)
                    ).getBody().getDomainRecords().getRecord(),
                    ACloudDnsDomainRecords::new
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云域名解析记录失败: " + domainName, e);
        }
    }
}
