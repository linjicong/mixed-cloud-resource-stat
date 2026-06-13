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
import com.linjicong.cloud.stat.dao.entity.acloud.*;
import com.linjicong.cloud.stat.util.BeanUtils;
import com.linjicong.cloud.stat.util.ThreadLocalUtil;

import java.util.ArrayList;
import java.util.Collections;
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

    /** 默认分页大小 */
    private static final int DEFAULT_PAGE_SIZE = 50;

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

    /**
     * 查询阿里云ECS实例列表
     *
     * @return ECS实例列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudEcs> listEcs() {
        try {
            com.aliyun.ecs20140526.Client client = new com.aliyun.ecs20140526.Client(generateConfig("ecs"));
            List<com.aliyun.ecs20140526.models.DescribeInstancesResponseBody.DescribeInstancesResponseBodyInstancesInstance> allItems = new ArrayList<>();
            int pageNumber = 1;
            while (true) {
                com.aliyun.ecs20140526.models.DescribeInstancesRequest request = new com.aliyun.ecs20140526.models.DescribeInstancesRequest()
                        .setPageNumber(pageNumber)
                        .setPageSize(DEFAULT_PAGE_SIZE);
                List<com.aliyun.ecs20140526.models.DescribeInstancesResponseBody.DescribeInstancesResponseBodyInstancesInstance> items =
                        client.describeInstances(request).getBody().getInstances().getInstance();
                if (items == null || items.isEmpty()) break;
                allItems.addAll(items);
                if (items.size() < DEFAULT_PAGE_SIZE) break;
                pageNumber++;
            }
            return BeanUtils.cgLibCopyList(allItems, ACloudEcs::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云ECS实例列表失败", e);
        }
    }

    /**
     * 查询阿里云RDS实例列表
     *
     * @return RDS实例列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudRds> listRds() {
        try {
            com.aliyun.rds20140815.Client client = new com.aliyun.rds20140815.Client(generateConfig("rds"));
            List<com.aliyun.rds20140815.models.DescribeDBInstancesResponseBody.DescribeDBInstancesResponseBodyItemsDBInstance> allItems = new ArrayList<>();
            int pageNumber = 1;
            while (true) {
                com.aliyun.rds20140815.models.DescribeDBInstancesRequest request = new com.aliyun.rds20140815.models.DescribeDBInstancesRequest()
                        .setPageNumber(pageNumber)
                        .setPageSize(DEFAULT_PAGE_SIZE);
                List<com.aliyun.rds20140815.models.DescribeDBInstancesResponseBody.DescribeDBInstancesResponseBodyItemsDBInstance> items =
                        client.describeDBInstances(request).getBody().getItems().getDBInstance();
                if (items == null || items.isEmpty()) break;
                allItems.addAll(items);
                if (items.size() < DEFAULT_PAGE_SIZE) break;
                pageNumber++;
            }
            return BeanUtils.cgLibCopyList(allItems, ACloudRds::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云RDS实例列表失败", e);
        }
    }

    /**
     * 查询阿里云Redis实例列表
     *
     * @return Redis实例列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudRedis> listRedis() {
        try {
            com.aliyun.r_kvstore20150101.Client client = new com.aliyun.r_kvstore20150101.Client(generateConfig("r-kvstore"));
            List<com.aliyun.r_kvstore20150101.models.DescribeInstancesResponseBody.DescribeInstancesResponseBodyInstancesKVStoreInstance> allItems = new ArrayList<>();
            int pageNumber = 1;
            while (true) {
                com.aliyun.r_kvstore20150101.models.DescribeInstancesRequest request =
                        new com.aliyun.r_kvstore20150101.models.DescribeInstancesRequest()
                                .setPageNumber(pageNumber)
                                .setPageSize(DEFAULT_PAGE_SIZE);
                List<com.aliyun.r_kvstore20150101.models.DescribeInstancesResponseBody.DescribeInstancesResponseBodyInstancesKVStoreInstance> items =
                        client.describeInstances(request).getBody().getInstances().getKVStoreInstance();
                if (items == null || items.isEmpty()) break;
                allItems.addAll(items);
                if (items.size() < DEFAULT_PAGE_SIZE) break;
                pageNumber++;
            }
            return BeanUtils.cgLibCopyList(allItems, ACloudRedis::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云Redis实例列表失败", e);
        }
    }

    /**
     * 查询阿里云VPC列表
     *
     * @return VPC列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudVpc> listVpc() {
        try {
            com.aliyun.vpc20160428.Client client = new com.aliyun.vpc20160428.Client(generateConfig("vpc"));
            List<com.aliyun.vpc20160428.models.DescribeVpcsResponseBody.DescribeVpcsResponseBodyVpcsVpc> allItems = new ArrayList<>();
            int pageNumber = 1;
            while (true) {
                com.aliyun.vpc20160428.models.DescribeVpcsRequest request = new com.aliyun.vpc20160428.models.DescribeVpcsRequest()
                        .setPageNumber(pageNumber)
                        .setPageSize(DEFAULT_PAGE_SIZE);
                List<com.aliyun.vpc20160428.models.DescribeVpcsResponseBody.DescribeVpcsResponseBodyVpcsVpc> items =
                        client.describeVpcs(request).getBody().getVpcs().getVpc();
                if (items == null || items.isEmpty()) break;
                allItems.addAll(items);
                if (items.size() < DEFAULT_PAGE_SIZE) break;
                pageNumber++;
            }
            return BeanUtils.cgLibCopyList(allItems, ACloudVpc::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云VPC列表失败", e);
        }
    }

    /**
     * 查询阿里云SLB负载均衡列表
     *
     * @return SLB实例列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudSlb> listSlb() {
        try {
            com.aliyun.slb20140515.Client client = new com.aliyun.slb20140515.Client(generateConfig("slb"));
            List<com.aliyun.slb20140515.models.DescribeLoadBalancersResponseBody.DescribeLoadBalancersResponseBodyLoadBalancersLoadBalancer> allItems = new ArrayList<>();
            int pageNumber = 1;
            while (true) {
                com.aliyun.slb20140515.models.DescribeLoadBalancersRequest request = new com.aliyun.slb20140515.models.DescribeLoadBalancersRequest()
                        .setPageNumber(pageNumber)
                        .setPageSize(DEFAULT_PAGE_SIZE);
                List<com.aliyun.slb20140515.models.DescribeLoadBalancersResponseBody.DescribeLoadBalancersResponseBodyLoadBalancersLoadBalancer> items =
                        client.describeLoadBalancers(request).getBody().getLoadBalancers().getLoadBalancer();
                if (items == null || items.isEmpty()) break;
                allItems.addAll(items);
                if (items.size() < DEFAULT_PAGE_SIZE) break;
                pageNumber++;
            }
            return BeanUtils.cgLibCopyList(allItems, ACloudSlb::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云SLB实例列表失败", e);
        }
    }

    /**
     * 查询阿里云OSS存储桶列表
     *
     * @return OSS存储桶列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudOss> listOss() {
        try {
            com.aliyun.oss20190517.Client client = new com.aliyun.oss20190517.Client(generateConfig("oss"));
            com.aliyun.oss20190517.models.ListBucketsRequest listBucketsRequest = new com.aliyun.oss20190517.models.ListBucketsRequest();
            List<com.aliyun.oss20190517.models.Bucket> items = client.listBuckets(listBucketsRequest).getBody().getBuckets();
            if (items == null || items.isEmpty()) return Collections.emptyList();
            return BeanUtils.cgLibCopyList(items, ACloudOss::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云OSS存储桶列表失败", e);
        }
    }

    /**
     * 查询阿里云NAT网关列表
     *
     * @return NAT网关列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudNatGateway> listNatGateway() {
        try {
            com.aliyun.vpc20160428.Client client = new com.aliyun.vpc20160428.Client(generateConfig("vpc"));
            List<com.aliyun.vpc20160428.models.DescribeNatGatewaysResponseBody.DescribeNatGatewaysResponseBodyNatGatewaysNatGateway> allItems = new ArrayList<>();
            int pageNumber = 1;
            while (true) {
                com.aliyun.vpc20160428.models.DescribeNatGatewaysRequest request = new com.aliyun.vpc20160428.models.DescribeNatGatewaysRequest()
                        .setPageNumber(pageNumber)
                        .setPageSize(DEFAULT_PAGE_SIZE);
                List<com.aliyun.vpc20160428.models.DescribeNatGatewaysResponseBody.DescribeNatGatewaysResponseBodyNatGatewaysNatGateway> items =
                        client.describeNatGateways(request).getBody().getNatGateways().getNatGateway();
                if (items == null || items.isEmpty()) break;
                allItems.addAll(items);
                if (items.size() < DEFAULT_PAGE_SIZE) break;
                pageNumber++;
            }
            return BeanUtils.cgLibCopyList(allItems, ACloudNatGateway::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云NAT网关列表失败", e);
        }
    }

    /**
     * 查询阿里云安全组列表
     *
     * @return 安全组列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudSecurityGroup> listSecurityGroup() {
        try {
            com.aliyun.ecs20140526.Client client = new com.aliyun.ecs20140526.Client(generateConfig("ecs"));
            List<com.aliyun.ecs20140526.models.DescribeSecurityGroupsResponseBody.DescribeSecurityGroupsResponseBodySecurityGroupsSecurityGroup> allItems = new ArrayList<>();
            int pageNumber = 1;
            while (true) {
                com.aliyun.ecs20140526.models.DescribeSecurityGroupsRequest request = new com.aliyun.ecs20140526.models.DescribeSecurityGroupsRequest()
                        .setPageNumber(pageNumber)
                        .setPageSize(DEFAULT_PAGE_SIZE);
                List<com.aliyun.ecs20140526.models.DescribeSecurityGroupsResponseBody.DescribeSecurityGroupsResponseBodySecurityGroupsSecurityGroup> items =
                        client.describeSecurityGroups(request).getBody().getSecurityGroups().getSecurityGroup();
                if (items == null || items.isEmpty()) break;
                allItems.addAll(items);
                if (items.size() < DEFAULT_PAGE_SIZE) break;
                pageNumber++;
            }
            return BeanUtils.cgLibCopyList(allItems, ACloudSecurityGroup::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云安全组列表失败", e);
        }
    }

    /**
     * 查询阿里云EIP弹性公网IP列表
     *
     * @return EIP列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudEip> listEip() {
        try {
            com.aliyun.vpc20160428.Client client = new com.aliyun.vpc20160428.Client(generateConfig("vpc"));
            List<com.aliyun.vpc20160428.models.DescribeEipAddressesResponseBody.DescribeEipAddressesResponseBodyEipAddressesEipAddress> allItems = new ArrayList<>();
            int pageNumber = 1;
            while (true) {
                com.aliyun.vpc20160428.models.DescribeEipAddressesRequest request = new com.aliyun.vpc20160428.models.DescribeEipAddressesRequest()
                        .setPageNumber(pageNumber)
                        .setPageSize(DEFAULT_PAGE_SIZE);
                List<com.aliyun.vpc20160428.models.DescribeEipAddressesResponseBody.DescribeEipAddressesResponseBodyEipAddressesEipAddress> items =
                        client.describeEipAddresses(request).getBody().getEipAddresses().getEipAddress();
                if (items == null || items.isEmpty()) break;
                allItems.addAll(items);
                if (items.size() < DEFAULT_PAGE_SIZE) break;
                pageNumber++;
            }
            return BeanUtils.cgLibCopyList(allItems, ACloudEip::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云EIP列表失败", e);
        }
    }

    /**
     * 查询阿里云CDN域名列表
     *
     * @return CDN域名列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudCdn> listCdn() {
        try {
            com.aliyun.cdn20180510.Client client = new com.aliyun.cdn20180510.Client(generateConfig("cdn"));
            List<com.aliyun.cdn20180510.models.DescribeUserDomainsResponseBody.DescribeUserDomainsResponseBodyDomainsPageData> allItems = new ArrayList<>();
            int pageNumber = 1;
            while (true) {
                com.aliyun.cdn20180510.models.DescribeUserDomainsRequest request = new com.aliyun.cdn20180510.models.DescribeUserDomainsRequest()
                        .setPageNumber(pageNumber)
                        .setPageSize(DEFAULT_PAGE_SIZE);
                List<com.aliyun.cdn20180510.models.DescribeUserDomainsResponseBody.DescribeUserDomainsResponseBodyDomainsPageData> items =
                        client.describeUserDomains(request).getBody().getDomains().getPageData();
                if (items == null || items.isEmpty()) break;
                allItems.addAll(items);
                if (items.size() < DEFAULT_PAGE_SIZE) break;
                pageNumber++;
            }
            return BeanUtils.cgLibCopyList(allItems, ACloudCdn::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云CDN域名列表失败", e);
        }
    }

    /**
     * 查询阿里云WAF实例信息
     *
     * @return WAF实例列表（单实例模式）
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudWaf> listWaf() {
        try {
            com.aliyun.waf_openapi20190910.Client client = new com.aliyun.waf_openapi20190910.Client(generateConfig("wafopenapi"));
            com.aliyun.waf_openapi20190910.models.DescribeInstanceInfoRequest request = new com.aliyun.waf_openapi20190910.models.DescribeInstanceInfoRequest();
            com.aliyun.waf_openapi20190910.models.DescribeInstanceInfoResponseBody body = client.describeInstanceInfo(request).getBody();
            com.aliyun.waf_openapi20190910.models.DescribeInstanceInfoResponseBody.DescribeInstanceInfoResponseBodyInstanceInfo instanceInfo = body.getInstanceInfo();
            if (instanceInfo == null) return Collections.emptyList();
            ACloudWaf waf = BeanUtils.cgLibCopyBean(instanceInfo, ACloudWaf::new);
            return Collections.singletonList(waf);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云WAF实例信息失败", e);
        }
    }

    /**
     * 查询阿里云KMS密钥列表
     *
     * @return KMS密钥列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudKms> listKms() {
        try {
            com.aliyun.kms20160120.Client client = new com.aliyun.kms20160120.Client(generateConfig("kms"));
            List<com.aliyun.kms20160120.models.ListKeysResponseBody.ListKeysResponseBodyKeysKey> allItems = new ArrayList<>();
            int pageNumber = 1;
            while (true) {
                com.aliyun.kms20160120.models.ListKeysRequest request = new com.aliyun.kms20160120.models.ListKeysRequest()
                        .setPageNumber(pageNumber)
                        .setPageSize(DEFAULT_PAGE_SIZE);
                List<com.aliyun.kms20160120.models.ListKeysResponseBody.ListKeysResponseBodyKeysKey> items =
                        client.listKeys(request).getBody().getKeys().getKey();
                if (items == null || items.isEmpty()) break;
                allItems.addAll(items);
                if (items.size() < DEFAULT_PAGE_SIZE) break;
                pageNumber++;
            }
            return BeanUtils.cgLibCopyList(allItems, ACloudKms::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云KMS密钥列表失败", e);
        }
    }

    /**
     * 查询阿里云ACK容器集群列表
     *
     * @return ACK集群列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudAck> listAck() {
        try {
            com.aliyun.cs20151215.Client client = new com.aliyun.cs20151215.Client(generateConfig("cs"));
            com.aliyun.cs20151215.models.DescribeClustersV1Request request = new com.aliyun.cs20151215.models.DescribeClustersV1Request();
            List<com.aliyun.cs20151215.models.DescribeClustersV1ResponseBody.DescribeClustersV1ResponseBodyClusters> items =
                    client.describeClustersV1(request).getBody().getClusters();
            if (items == null || items.isEmpty()) return Collections.emptyList();
            return BeanUtils.cgLibCopyList(items, ACloudAck::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云ACK集群列表失败", e);
        }
    }

    /**
     * 查询阿里云SLS日志服务项目列表
     *
     * @return SLS项目列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudSls> listSls() {
        try {
            com.aliyun.sls20201230.Client client = new com.aliyun.sls20201230.Client(generateConfig("cn-shanghai.sls"));
            List<com.aliyun.sls20201230.models.Project> allItems = new ArrayList<>();
            int offset = 0;
            while (true) {
                com.aliyun.sls20201230.models.ListProjectRequest request = new com.aliyun.sls20201230.models.ListProjectRequest()
                        .setOffset(offset)
                        .setSize(DEFAULT_PAGE_SIZE);
                List<com.aliyun.sls20201230.models.Project> items =
                        client.listProject(request).getBody().getProjects();
                if (items == null || items.isEmpty()) break;
                allItems.addAll(items);
                if (items.size() < DEFAULT_PAGE_SIZE) break;
                offset += DEFAULT_PAGE_SIZE;
            }
            return BeanUtils.cgLibCopyList(allItems, ACloudSls::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云SLS日志服务项目列表失败", e);
        }
    }

    /**
     * 查询阿里云短信签名列表
     *
     * @return 短信签名列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudSms> listSms() {
        try {
            com.aliyun.dysmsapi20170525.Client client = new com.aliyun.dysmsapi20170525.Client(generateConfig("dysmsapi"));
            com.aliyun.dysmsapi20170525.models.QuerySmsSignListRequest request = new com.aliyun.dysmsapi20170525.models.QuerySmsSignListRequest()
                    .setPageIndex(1);
            List<com.aliyun.dysmsapi20170525.models.QuerySmsSignListResponseBody.QuerySmsSignListResponseBodySmsSignList> items =
                    client.querySmsSignList(request).getBody().getSmsSignList();
            if (items == null || items.isEmpty()) return Collections.emptyList();
            return BeanUtils.cgLibCopyList(items, ACloudSms::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云短信签名列表失败", e);
        }
    }

    /**
     * 查询阿里云DDS文档数据库实例列表
     *
     * @return MongoDB文档数据库实例列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudMongoDb> listMongoDb() {
        try {
            com.aliyun.dds20151201.Client client = new com.aliyun.dds20151201.Client(generateConfig("dds"));
            List<com.aliyun.dds20151201.models.DescribeDBInstancesResponseBody.DescribeDBInstancesResponseBodyDBInstancesDBInstance> allItems = new ArrayList<>();
            int pageNumber = 1;
            while (true) {
                com.aliyun.dds20151201.models.DescribeDBInstancesRequest request = new com.aliyun.dds20151201.models.DescribeDBInstancesRequest()
                        .setPageNumber(pageNumber)
                        .setPageSize(DEFAULT_PAGE_SIZE);
                List<com.aliyun.dds20151201.models.DescribeDBInstancesResponseBody.DescribeDBInstancesResponseBodyDBInstancesDBInstance> items =
                        client.describeDBInstances(request).getBody().getDBInstances().getDBInstance();
                if (items == null || items.isEmpty()) break;
                allItems.addAll(items);
                if (items.size() < DEFAULT_PAGE_SIZE) break;
                pageNumber++;
            }
            return BeanUtils.cgLibCopyList(allItems, ACloudMongoDb::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云DDS文档数据库实例列表失败", e);
        }
    }

    /**
     * 查询阿里云消息队列Kafka实例列表
     *
     * @return Kafka实例列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudKafka> listKafka() {
        try {
            com.aliyun.alikafka20190916.Client client = new com.aliyun.alikafka20190916.Client(generateConfig("alikafka"));
            com.aliyun.alikafka20190916.models.GetInstanceListRequest request = new com.aliyun.alikafka20190916.models.GetInstanceListRequest();
            com.aliyun.alikafka20190916.models.GetInstanceListResponseBody.GetInstanceListResponseBodyInstanceList instanceList =
                    client.getInstanceList(request).getBody().getInstanceList();
            if (instanceList == null) return Collections.emptyList();
            List<com.aliyun.alikafka20190916.models.GetInstanceListResponseBody.GetInstanceListResponseBodyInstanceListInstanceVO> items = instanceList.getInstanceVO();
            if (items == null || items.isEmpty()) return Collections.emptyList();
            return BeanUtils.cgLibCopyList(items, ACloudKafka::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云Kafka实例列表失败", e);
        }
    }

    /**
     * 查询阿里云消息队列RocketMQ实例列表
     *
     * @return RocketMQ实例列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudRocketMQ> listRocketMQ() {
        try {
            com.aliyun.ons20190214.Client client = new com.aliyun.ons20190214.Client(generateConfig("ons"));
            // TODO: ons SDK API 结构可能不同，当前使用保守写法
            com.aliyun.ons20190214.models.OnsInstanceInServiceListRequest request = new com.aliyun.ons20190214.models.OnsInstanceInServiceListRequest();
            List<?> items = client.onsInstanceInServiceList(request).getBody().getData().getInstanceVO();
            if (items == null || items.isEmpty()) return Collections.emptyList();
            return BeanUtils.cgLibCopyList((List) items, ACloudRocketMQ::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云消息队列RocketMQ实例列表失败", e);
        }
    }

    /**
     * 查询阿里云云硬盘(Disk)列表
     * 复用ECS SDK
     *
     * @return 云硬盘列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudDisk> listDisk() {
        try {
            com.aliyun.ecs20140526.Client client = new com.aliyun.ecs20140526.Client(generateConfig("ecs"));
            List<com.aliyun.ecs20140526.models.DescribeDisksResponseBody.DescribeDisksResponseBodyDisksDisk> allItems = new ArrayList<>();
            int pageNumber = 1;
            while (true) {
                com.aliyun.ecs20140526.models.DescribeDisksRequest request = new com.aliyun.ecs20140526.models.DescribeDisksRequest()
                        .setPageNumber(pageNumber)
                        .setPageSize(DEFAULT_PAGE_SIZE);
                List<com.aliyun.ecs20140526.models.DescribeDisksResponseBody.DescribeDisksResponseBodyDisksDisk> items =
                        client.describeDisks(request).getBody().getDisks().getDisk();
                if (items == null || items.isEmpty()) break;
                allItems.addAll(items);
                if (items.size() < DEFAULT_PAGE_SIZE) break;
                pageNumber++;
            }
            return BeanUtils.cgLibCopyList(allItems, ACloudDisk::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云云硬盘列表失败", e);
        }
    }

    /**
     * 查询阿里云Elasticsearch实例列表
     *
     * @return Elasticsearch实例列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudElasticsearch> listElasticsearch() {
        try {
            com.aliyun.elasticsearch20170613.Client client = new com.aliyun.elasticsearch20170613.Client(generateConfig("elasticsearch"));
            List<com.aliyun.elasticsearch20170613.models.ListInstanceResponseBody.ListInstanceResponseBodyResult> allItems = new ArrayList<>();
            int pageNumber = 1;
            while (true) {
                com.aliyun.elasticsearch20170613.models.ListInstanceRequest request = new com.aliyun.elasticsearch20170613.models.ListInstanceRequest()
                        .setPage(pageNumber)
                        .setSize(DEFAULT_PAGE_SIZE);
                List<com.aliyun.elasticsearch20170613.models.ListInstanceResponseBody.ListInstanceResponseBodyResult> items =
                        client.listInstance(request).getBody().getResult();
                if (items == null || items.isEmpty()) break;
                allItems.addAll(items);
                if (items.size() < DEFAULT_PAGE_SIZE) break;
                pageNumber++;
            }
            return BeanUtils.cgLibCopyList(allItems, ACloudElasticsearch::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云Elasticsearch实例列表失败", e);
        }
    }

    /**
     * 查询阿里云函数计算(FC)服务列表
     *
     * @return 函数计算服务列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudFc> listFc() {
        try {
            // TODO: FC v3 SDK 结构可能不同，当前使用保守写法
            com.aliyun.fc20230330.Client client = new com.aliyun.fc20230330.Client(generateConfig("fc"));
            com.aliyun.fc20230330.models.ListFunctionsRequest request = new com.aliyun.fc20230330.models.ListFunctionsRequest();
            List<?> items = client.listFunctions(request).getBody().getFunctions();
            if (items == null || items.isEmpty()) return Collections.emptyList();
            return BeanUtils.cgLibCopyList((List) items, ACloudFc::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云函数计算服务列表失败", e);
        }
    }

    /**
     * 查询阿里云云监控应用分组列表
     *
     * @return 云监控应用分组列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudCms> listCms() {
        try {
            com.aliyun.cms20190101.Client client = new com.aliyun.cms20190101.Client(generateConfig("cms"));
            List<com.aliyun.cms20190101.models.DescribeMonitorGroupsResponseBody.DescribeMonitorGroupsResponseBodyResourcesResource> allItems = new ArrayList<>();
            int pageNumber = 1;
            while (true) {
                com.aliyun.cms20190101.models.DescribeMonitorGroupsRequest request = new com.aliyun.cms20190101.models.DescribeMonitorGroupsRequest()
                        .setPageNumber(pageNumber)
                        .setPageSize(DEFAULT_PAGE_SIZE);
                List<com.aliyun.cms20190101.models.DescribeMonitorGroupsResponseBody.DescribeMonitorGroupsResponseBodyResourcesResource> items =
                        client.describeMonitorGroups(request).getBody().getResources().getResource();
                if (items == null || items.isEmpty()) break;
                allItems.addAll(items);
                if (items.size() < DEFAULT_PAGE_SIZE) break;
                pageNumber++;
            }
            return BeanUtils.cgLibCopyList(allItems, ACloudCms::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云云监控应用分组列表失败", e);
        }
    }

    /**
     * 查询阿里云文件存储NAS列表
     *
     * @return NAS文件系统列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudNas> listNas() {
        try {
            com.aliyun.nas20170626.Client client = new com.aliyun.nas20170626.Client(generateConfig("nas"));
            com.aliyun.nas20170626.models.DescribeFileSystemsRequest request = new com.aliyun.nas20170626.models.DescribeFileSystemsRequest();
            List<com.aliyun.nas20170626.models.DescribeFileSystemsResponseBody.DescribeFileSystemsResponseBodyFileSystemsFileSystem> items =
                    client.describeFileSystems(request).getBody().getFileSystems().getFileSystem();
            if (items == null || items.isEmpty()) return Collections.emptyList();
            return BeanUtils.cgLibCopyList(items, ACloudNas::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云文件存储NAS列表失败", e);
        }
    }

    /**
     * 查询阿里云弹性伸缩(ESS)伸缩组列表
     *
     * @return 伸缩组列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudEss> listEss() {
        try {
            com.aliyun.ess20220222.Client client = new com.aliyun.ess20220222.Client(generateConfig("ess"));
            List<com.aliyun.ess20220222.models.DescribeScalingGroupsResponseBody.DescribeScalingGroupsResponseBodyScalingGroups> allItems = new ArrayList<>();
            int pageNumber = 1;
            while (true) {
                com.aliyun.ess20220222.models.DescribeScalingGroupsRequest request = new com.aliyun.ess20220222.models.DescribeScalingGroupsRequest()
                        .setPageNumber(pageNumber)
                        .setPageSize(DEFAULT_PAGE_SIZE);
                List<com.aliyun.ess20220222.models.DescribeScalingGroupsResponseBody.DescribeScalingGroupsResponseBodyScalingGroups> items =
                        client.describeScalingGroups(request).getBody().getScalingGroups();
                if (items == null || items.isEmpty()) break;
                allItems.addAll(items);
                if (items.size() < DEFAULT_PAGE_SIZE) break;
                pageNumber++;
            }
            return BeanUtils.cgLibCopyList(allItems, ACloudEss::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云弹性伸缩组列表失败", e);
        }
    }

    /**
     * 查询阿里云主机安全(HSS)资产列表
     *
     * @return 主机安全资产列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudHss> listHss() {
        try {
            com.aliyun.sas20181203.Client client = new com.aliyun.sas20181203.Client(generateConfig("sas"));
            List<com.aliyun.sas20181203.models.DescribeCloudCenterInstancesResponseBody.DescribeCloudCenterInstancesResponseBodyInstances> allItems = new ArrayList<>();
            int currentPage = 1;
            while (true) {
                com.aliyun.sas20181203.models.DescribeCloudCenterInstancesRequest request = new com.aliyun.sas20181203.models.DescribeCloudCenterInstancesRequest()
                        .setCurrentPage(currentPage)
                        .setPageSize(DEFAULT_PAGE_SIZE)
                        .setMachineTypes("ecs");
                List<com.aliyun.sas20181203.models.DescribeCloudCenterInstancesResponseBody.DescribeCloudCenterInstancesResponseBodyInstances> items =
                        client.describeCloudCenterInstances(request).getBody().getInstances();
                if (items == null || items.isEmpty()) break;
                allItems.addAll(items);
                if (items.size() < DEFAULT_PAGE_SIZE) break;
                currentPage++;
            }
            return BeanUtils.cgLibCopyList(allItems, ACloudHss::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云主机安全资产列表失败", e);
        }
    }

    /**
     * 查询阿里云云审计(ActionTrail)跟踪列表
     *
     * @return 审计跟踪列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudActionTrail> listActionTrail() {
        try {
            com.aliyun.actiontrail20171204.Client client = new com.aliyun.actiontrail20171204.Client(generateConfig("actiontrail"));
            com.aliyun.actiontrail20171204.models.DescribeTrailsRequest request = new com.aliyun.actiontrail20171204.models.DescribeTrailsRequest();
            List<com.aliyun.actiontrail20171204.models.DescribeTrailsResponseBody.DescribeTrailsResponseBodyTrailList> items =
                    client.describeTrails(request).getBody().getTrailList();
            if (items == null || items.isEmpty()) return Collections.emptyList();
            return BeanUtils.cgLibCopyList(items, ACloudActionTrail::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云云审计跟踪列表失败", e);
        }
    }

    /**
     * 查询阿里云API网关分组列表
     *
     * @return API网关分组列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudApiGateway> listApiGateway() {
        try {
            com.aliyun.cloudapi20160714.Client client = new com.aliyun.cloudapi20160714.Client(generateConfig("apigateway"));
            List<com.aliyun.cloudapi20160714.models.DescribeApiGroupsResponseBody.DescribeApiGroupsResponseBodyApiGroupAttributesApiGroupAttribute> allItems = new ArrayList<>();
            int pageNumber = 1;
            while (true) {
                com.aliyun.cloudapi20160714.models.DescribeApiGroupsRequest request = new com.aliyun.cloudapi20160714.models.DescribeApiGroupsRequest()
                        .setPageNumber(pageNumber)
                        .setPageSize(DEFAULT_PAGE_SIZE);
                List<com.aliyun.cloudapi20160714.models.DescribeApiGroupsResponseBody.DescribeApiGroupsResponseBodyApiGroupAttributesApiGroupAttribute> items =
                        client.describeApiGroups(request).getBody().getApiGroupAttributes().getApiGroupAttribute();
                if (items == null || items.isEmpty()) break;
                allItems.addAll(items);
                if (items.size() < DEFAULT_PAGE_SIZE) break;
                pageNumber++;
            }
            return BeanUtils.cgLibCopyList(allItems, ACloudApiGateway::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云API网关分组列表失败", e);
        }
    }

    /**
     * 查询阿里云物联网(IoT)产品列表
     *
     * @return IoT产品列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudIoT> listIoT() {
        try {
            com.aliyun.iot20180120.Client client = new com.aliyun.iot20180120.Client(generateConfig("iot"));
            List<com.aliyun.iot20180120.models.QueryProductListResponseBody.QueryProductListResponseBodyDataListProductInfo> allItems = new ArrayList<>();
            int currentPage = 1;
            while (true) {
                com.aliyun.iot20180120.models.QueryProductListRequest request = new com.aliyun.iot20180120.models.QueryProductListRequest()
                        .setCurrentPage(currentPage)
                        .setPageSize(DEFAULT_PAGE_SIZE);
                com.aliyun.iot20180120.models.QueryProductListResponseBody.QueryProductListResponseBodyDataList dataList =
                        client.queryProductList(request).getBody().getData().getList();
                if (dataList == null) break;
                List<com.aliyun.iot20180120.models.QueryProductListResponseBody.QueryProductListResponseBodyDataListProductInfo> items = dataList.getProductInfo();
                if (items == null || items.isEmpty()) break;
                allItems.addAll(items);
                if (items.size() < DEFAULT_PAGE_SIZE) break;
                currentPage++;
            }
            return BeanUtils.cgLibCopyList(allItems, ACloudIoT::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云物联网产品列表失败", e);
        }
    }

    /**
     * 查询阿里云视频直播(Live)域名列表
     *
     * @return 直播域名列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudLive> listLive() {
        try {
            com.aliyun.live20161101.Client client = new com.aliyun.live20161101.Client(generateConfig("live"));
            com.aliyun.live20161101.models.DescribeLiveUserDomainsRequest request = new com.aliyun.live20161101.models.DescribeLiveUserDomainsRequest();
            List<com.aliyun.live20161101.models.DescribeLiveUserDomainsResponseBody.DescribeLiveUserDomainsResponseBodyDomainsPageData> items =
                    client.describeLiveUserDomains(request).getBody().getDomains().getPageData();
            if (items == null || items.isEmpty()) return Collections.emptyList();
            return BeanUtils.cgLibCopyList(items, ACloudLive::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云视频直播域名列表失败", e);
        }
    }

    /**
     * 查询阿里云大数据EMR集群列表
     *
     * @return EMR集群列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudEmr> listEmr() {
        try {
            com.aliyun.emr20160408.Client client = new com.aliyun.emr20160408.Client(generateConfig("emr"));
            List<com.aliyun.emr20160408.models.ListClustersResponseBody.ListClustersResponseBodyClustersClusterInfo> allItems = new ArrayList<>();
            int pageNumber = 1;
            while (true) {
                com.aliyun.emr20160408.models.ListClustersRequest request = new com.aliyun.emr20160408.models.ListClustersRequest()
                        .setPageNumber(pageNumber)
                        .setPageSize(DEFAULT_PAGE_SIZE);
                com.aliyun.emr20160408.models.ListClustersResponseBody.ListClustersResponseBodyClusters clusters =
                        client.listClusters(request).getBody().getClusters();
                if (clusters == null) break;
                List<com.aliyun.emr20160408.models.ListClustersResponseBody.ListClustersResponseBodyClustersClusterInfo> items = clusters.getClusterInfo();
                if (items == null || items.isEmpty()) break;
                allItems.addAll(items);
                if (items.size() < DEFAULT_PAGE_SIZE) break;
                pageNumber++;
            }
            return BeanUtils.cgLibCopyList(allItems, ACloudEmr::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云EMR集群列表失败", e);
        }
    }

    /**
     * 查询阿里云视频点播(VOD)视频列表
     *
     * @return 视频列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudVod> listVod() {
        try {
            com.aliyun.vod20170321.Client client = new com.aliyun.vod20170321.Client(generateConfig("vod"));
            List<com.aliyun.vod20170321.models.GetVideoListResponseBody.GetVideoListResponseBodyVideoListVideo> allItems = new ArrayList<>();
            int pageNo = 1;
            while (true) {
                com.aliyun.vod20170321.models.GetVideoListRequest request = new com.aliyun.vod20170321.models.GetVideoListRequest()
                        .setPageNo(pageNo)
                        .setPageSize(DEFAULT_PAGE_SIZE);
                com.aliyun.vod20170321.models.GetVideoListResponseBody.GetVideoListResponseBodyVideoList videoList =
                        client.getVideoList(request).getBody().getVideoList();
                if (videoList == null) break;
                List<com.aliyun.vod20170321.models.GetVideoListResponseBody.GetVideoListResponseBodyVideoListVideo> items = videoList.getVideo();
                if (items == null || items.isEmpty()) break;
                allItems.addAll(items);
                if (items.size() < DEFAULT_PAGE_SIZE) break;
                pageNo++;
            }
            return BeanUtils.cgLibCopyList(allItems, ACloudVod::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云视频点播视频列表失败", e);
        }
    }

    /**
     * 查询阿里云容器镜像服务(ACR)实例列表
     *
     * @return ACR实例列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudAcr> listAcr() {
        try {
            com.aliyun.cr20181201.Client client = new com.aliyun.cr20181201.Client(generateConfig("cr"));
            List<com.aliyun.cr20181201.models.ListInstanceResponseBody.ListInstanceResponseBodyInstances> allItems = new ArrayList<>();
            int pageNo = 1;
            while (true) {
                com.aliyun.cr20181201.models.ListInstanceRequest request = new com.aliyun.cr20181201.models.ListInstanceRequest()
                        .setPageNo(pageNo)
                        .setPageSize(DEFAULT_PAGE_SIZE);
                List<com.aliyun.cr20181201.models.ListInstanceResponseBody.ListInstanceResponseBodyInstances> items =
                        client.listInstance(request).getBody().getInstances();
                if (items == null || items.isEmpty()) break;
                allItems.addAll(items);
                if (items.size() < DEFAULT_PAGE_SIZE) break;
                pageNo++;
            }
            return BeanUtils.cgLibCopyList(allItems, ACloudAcr::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云容器镜像服务实例列表失败", e);
        }
    }

    /**
     * 查询阿里云DDoS高防实例列表
     *
     * @return DDoS高防实例列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudDdos> listDdos() {
        try {
            com.aliyun.ddosbgp20171120.Client client = new com.aliyun.ddosbgp20171120.Client(generateConfig("ddosbgp"));
            List<com.aliyun.ddosbgp20171120.models.DescribeInstanceListResponseBody.DescribeInstanceListResponseBodyInstanceList> allItems = new ArrayList<>();
            int pageNo = 1;
            while (true) {
                com.aliyun.ddosbgp20171120.models.DescribeInstanceListRequest request = new com.aliyun.ddosbgp20171120.models.DescribeInstanceListRequest()
                        .setPageNo(pageNo)
                        .setPageSize(DEFAULT_PAGE_SIZE);
                List<com.aliyun.ddosbgp20171120.models.DescribeInstanceListResponseBody.DescribeInstanceListResponseBodyInstanceList> items =
                        client.describeInstanceList(request).getBody().getInstanceList();
                if (items == null || items.isEmpty()) break;
                allItems.addAll(items);
                if (items.size() < DEFAULT_PAGE_SIZE) break;
                pageNo++;
            }
            return BeanUtils.cgLibCopyList(allItems, ACloudDdos::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云DDoS高防实例列表失败", e);
        }
    }

    /**
     * 查询阿里云SSL证书列表
     *
     * @return SSL证书列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudSsl> listSsl() {
        try {
            com.aliyun.cas20180713.Client client = new com.aliyun.cas20180713.Client(generateConfig("cas"));
            List<com.aliyun.cas20180713.models.DescribeUserCertificateListResponseBody.DescribeUserCertificateListResponseBodyCertificateList> allItems = new ArrayList<>();
            int currentPage = 1;
            while (true) {
                com.aliyun.cas20180713.models.DescribeUserCertificateListRequest request = new com.aliyun.cas20180713.models.DescribeUserCertificateListRequest()
                        .setCurrentPage(currentPage)
                        .setShowSize(DEFAULT_PAGE_SIZE);
                List<com.aliyun.cas20180713.models.DescribeUserCertificateListResponseBody.DescribeUserCertificateListResponseBodyCertificateList> items =
                        client.describeUserCertificateList(request).getBody().getCertificateList();
                if (items == null || items.isEmpty()) break;
                allItems.addAll(items);
                if (items.size() < DEFAULT_PAGE_SIZE) break;
                currentPage++;
            }
            return BeanUtils.cgLibCopyList(allItems, ACloudSsl::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云SSL证书列表失败", e);
        }
    }

    /**
     * 查询阿里云云防火墙实例信息
     *
     * @return 云防火墙实例列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudCloudFirewall> listCloudFirewall() {
        try {
            com.aliyun.cloudfw20171207.Client client = new com.aliyun.cloudfw20171207.Client(generateConfig("cloudfw"));
            List<com.aliyun.cloudfw20171207.models.DescribeAssetListResponseBody.DescribeAssetListResponseBodyAssets> allItems = new ArrayList<>();
            int currentPage = 1;
            while (true) {
                com.aliyun.cloudfw20171207.models.DescribeAssetListRequest request = new com.aliyun.cloudfw20171207.models.DescribeAssetListRequest()
                        .setCurrentPage(String.valueOf(currentPage))
                        .setPageSize(String.valueOf(DEFAULT_PAGE_SIZE));
                List<com.aliyun.cloudfw20171207.models.DescribeAssetListResponseBody.DescribeAssetListResponseBodyAssets> items =
                        client.describeAssetList(request).getBody().getAssets();
                if (items == null || items.isEmpty()) break;
                allItems.addAll(items);
                if (items.size() < DEFAULT_PAGE_SIZE) break;
                currentPage++;
            }
            return BeanUtils.cgLibCopyList(allItems, ACloudCloudFirewall::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云云防火墙资产列表失败", e);
        }
    }

    /**
     * 查询阿里云数据安全中心(DSC)备份计划列表
     *
     * @return 数据安全中心实例列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudDsc> listDsc() {
        try {
            com.aliyun.dbs20190306.Client client = new com.aliyun.dbs20190306.Client(generateConfig("dbs"));
            List<com.aliyun.dbs20190306.models.DescribeBackupPlanListResponseBody.DescribeBackupPlanListResponseBodyItemsBackupPlanDetail> allItems = new ArrayList<>();
            int pageNum = 1;
            while (true) {
                com.aliyun.dbs20190306.models.DescribeBackupPlanListRequest request = new com.aliyun.dbs20190306.models.DescribeBackupPlanListRequest()
                        .setPageNum(pageNum)
                        .setPageSize(DEFAULT_PAGE_SIZE);
                com.aliyun.dbs20190306.models.DescribeBackupPlanListResponseBody.DescribeBackupPlanListResponseBodyItems itemsObj =
                        client.describeBackupPlanList(request).getBody().getItems();
                if (itemsObj == null) break;
                List<com.aliyun.dbs20190306.models.DescribeBackupPlanListResponseBody.DescribeBackupPlanListResponseBodyItemsBackupPlanDetail> items =
                        itemsObj.getBackupPlanDetail();
                if (items == null || items.isEmpty()) break;
                allItems.addAll(items);
                if (items.size() < DEFAULT_PAGE_SIZE) break;
                pageNum++;
            }
            return BeanUtils.cgLibCopyList(allItems, ACloudDsc::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云数据安全中心备份计划列表失败", e);
        }
    }

    /**
     * 查询阿里云PolarDB集群列表
     *
     * @return PolarDB集群列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudPolarDb> listPolarDb() {
        try {
            com.aliyun.polardb20170801.Client client = new com.aliyun.polardb20170801.Client(generateConfig("polardb"));
            List<com.aliyun.polardb20170801.models.DescribeDBClustersResponseBody.DescribeDBClustersResponseBodyItemsDBCluster> allItems = new ArrayList<>();
            int pageNumber = 1;
            while (true) {
                com.aliyun.polardb20170801.models.DescribeDBClustersRequest request = new com.aliyun.polardb20170801.models.DescribeDBClustersRequest()
                        .setPageNumber(pageNumber)
                        .setPageSize(DEFAULT_PAGE_SIZE);
                com.aliyun.polardb20170801.models.DescribeDBClustersResponseBody.DescribeDBClustersResponseBodyItems itemsObj =
                        client.describeDBClusters(request).getBody().getItems();
                if (itemsObj == null) break;
                List<com.aliyun.polardb20170801.models.DescribeDBClustersResponseBody.DescribeDBClustersResponseBodyItemsDBCluster> items =
                        itemsObj.getDBCluster();
                if (items == null || items.isEmpty()) break;
                allItems.addAll(items);
                if (items.size() < DEFAULT_PAGE_SIZE) break;
                pageNumber++;
            }
            return BeanUtils.cgLibCopyList(allItems, ACloudPolarDb::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云PolarDB集群列表失败", e);
        }
    }

    /**
     * 查询阿里云堡垒机实例列表
     * TODO: CBH SDK (cbh20210625) 暂不可用，使用空实现
     *
     * @return 堡垒机实例列表
     */
    public List<ACloudCbh> listCbh() {
        return Collections.emptyList();
    }

    /**
     * 查询阿里云RabbitMQ实例列表
     * TODO: RabbitMQ SDK (amqp-open20191212) 暂不可用，使用空实现
     *
     * @return RabbitMQ实例列表
     */
    public List<ACloudRabbitMq> listRabbitMq() {
        return Collections.emptyList();
    }

    /**
     * 查询阿里云微服务引擎MSE集群列表
     * TODO: MSE SDK (mse20190531) 暂不可用，使用空实现
     *
     * @return MSE集群列表
     */
    public List<ACloudMse> listMse() {
        return Collections.emptyList();
    }

    /**
     * 查询阿里云MaxCompute项目列表
     * TODO: MaxCompute SDK (maxcompute20220104) 暂不可用，使用空实现
     *
     * @return MaxCompute项目列表
     */
    public List<ACloudMaxCompute> listMaxCompute() {
        return Collections.emptyList();
    }

    /**
     * 查询阿里云AnalyticDB PostgreSQL实例列表
     *
     * @return AnalyticDB实例列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudAnalyticDb> listAnalyticDb() {
        try {
            com.aliyun.gpdb20160503.Client client = new com.aliyun.gpdb20160503.Client(generateConfig("gpdb"));
            List<com.aliyun.gpdb20160503.models.DescribeDBInstancesResponseBody.DescribeDBInstancesResponseBodyItemsDBInstance> allItems = new ArrayList<>();
            int pageNumber = 1;
            while (true) {
                com.aliyun.gpdb20160503.models.DescribeDBInstancesRequest request = new com.aliyun.gpdb20160503.models.DescribeDBInstancesRequest()
                        .setPageNumber(pageNumber)
                        .setPageSize(DEFAULT_PAGE_SIZE);
                List<com.aliyun.gpdb20160503.models.DescribeDBInstancesResponseBody.DescribeDBInstancesResponseBodyItemsDBInstance> items =
                        client.describeDBInstances(request).getBody().getItems().getDBInstance();
                if (items == null || items.isEmpty()) break;
                allItems.addAll(items);
                if (items.size() < DEFAULT_PAGE_SIZE) break;
                pageNumber++;
            }
            return BeanUtils.cgLibCopyList(allItems, ACloudAnalyticDb::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云AnalyticDB实例列表失败", e);
        }
    }

    /**
     * 查询阿里云ClickHouse集群列表
     * TODO: ClickHouse SDK (clickhouse20191111) 暂不可用，使用空实现
     *
     * @return ClickHouse集群列表
     */
    public List<ACloudClickHouse> listClickHouse() {
        return Collections.emptyList();
    }

    /**
     * 查询阿里云Hologres实例列表
     *
     * @return Hologres实例列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudHologres> listHologres() {
        try {
            com.aliyun.hologram20220601.Client client = new com.aliyun.hologram20220601.Client(generateConfig("hologram"));
            List<com.aliyun.hologram20220601.models.ListInstancesResponseBody.ListInstancesResponseBodyInstanceList> items =
                    client.listInstances(new com.aliyun.hologram20220601.models.ListInstancesRequest()).getBody().getInstanceList();
            if (items == null || items.isEmpty()) return Collections.emptyList();
            return BeanUtils.cgLibCopyList(items, ACloudHologres::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云Hologres实例列表失败", e);
        }
    }

    /**
     * 查询阿里云VPN网关列表
     * 复用VPC SDK
     *
     * @return VPN网关列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudVpnGateway> listVpnGateway() {
        try {
            com.aliyun.vpc20160428.Client client = new com.aliyun.vpc20160428.Client(generateConfig("vpc"));
            List<com.aliyun.vpc20160428.models.DescribeVpnGatewaysResponseBody.DescribeVpnGatewaysResponseBodyVpnGatewaysVpnGateway> allItems = new ArrayList<>();
            int pageNumber = 1;
            while (true) {
                com.aliyun.vpc20160428.models.DescribeVpnGatewaysRequest request = new com.aliyun.vpc20160428.models.DescribeVpnGatewaysRequest()
                        .setPageNumber(pageNumber)
                        .setPageSize(DEFAULT_PAGE_SIZE);
                List<com.aliyun.vpc20160428.models.DescribeVpnGatewaysResponseBody.DescribeVpnGatewaysResponseBodyVpnGatewaysVpnGateway> items =
                        client.describeVpnGateways(request).getBody().getVpnGateways().getVpnGateway();
                if (items == null || items.isEmpty()) break;
                allItems.addAll(items);
                if (items.size() < DEFAULT_PAGE_SIZE) break;
                pageNumber++;
            }
            return BeanUtils.cgLibCopyList(allItems, ACloudVpnGateway::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询阿里云VPN网关列表失败", e);
        }
    }

    /**
     * 查询阿里云云企业网CEN实例列表
     * TODO: CEN SDK (cbn20170912) 暂不可用，使用空实现
     *
     * @return CEN实例列表
     */
    public List<ACloudCen> listCen() {
        return Collections.emptyList();
    }

    /**
     * 查询阿里云全球加速GA实例列表
     * TODO: GA SDK (ga20191120) 暂不可用，使用空实现
     *
     * @return GA实例列表
     */
    public List<ACloudGa> listGa() {
        return Collections.emptyList();
    }

    /**
     * 查询阿里云VPC终端节点列表
     * TODO: VPC Endpoint API 结构需要确认，使用空实现
     *
     * @return VPC终端节点列表
     */
    public List<ACloudVpcEndpoint> listVpcEndpoint() {
        return Collections.emptyList();
    }

    /**
     * 查询阿里云SAE应用列表
     * TODO: SAE SDK (sae20190506) 暂不可用，使用空实现
     *
     * @return SAE应用列表
     */
    public List<ACloudSae> listSae() {
        return Collections.emptyList();
    }

    /**
     * 查询阿里云ALB应用型负载均衡列表
     * TODO: ALB SDK API 分页方式需要确认，使用空实现
     *
     * @return ALB实例列表
     */
    public List<ACloudAlb> listAlb() {
        return Collections.emptyList();
    }

    /**
     * 查询阿里云NLB网络型负载均衡列表
     * TODO: NLB SDK API 分页方式需要确认，使用空实现
     *
     * @return NLB实例列表
     */
    public List<ACloudNlb> listNlb() {
        return Collections.emptyList();
    }

    /**
     * 查询阿里云配额中心配额列表
     * TODO: 配额中心 API 结构需要确认，使用空实现
     *
     * @return 配额列表
     */
    public List<ACloudQuota> listQuota() {
        return Collections.emptyList();
    }

    /**
     * 查询阿里云配置审计资源列表
     * TODO: Config SDK (config20200907) 暂不可用，使用空实现
     *
     * @return 资源列表
     */
    public List<ACloudConfig> listConfigResource() {
        return Collections.emptyList();
    }

    /**
     * 查询阿里云OOS模板列表
     * TODO: OOS SDK (oos20190601) 暂不可用，使用空实现
     *
     * @return OOS模板列表
     */
    public List<ACloudOos> listOos() {
        return Collections.emptyList();
    }

    /**
     * 查询阿里云DTS实例列表
     * TODO: DTS SDK (dts20200101) 暂不可用，使用空实现
     *
     * @return DTS实例列表
     */
    public List<ACloudDts> listDts() {
        return Collections.emptyList();
    }

    /**
     * 查询阿里云云存储网关列表
     * TODO: StorageGateway SDK (sgw20180511) 暂不可用，使用空实现
     *
     * @return 存储网关列表
     */
    public List<ACloudStorageGateway> listStorageGateway() {
        return Collections.emptyList();
    }

    /**
     * 查询阿里云智能接入网关列表
     * TODO: SAG SDK (smartag20180313) 暂不可用，使用空实现
     *
     * @return 智能接入网关列表
     */
    public List<ACloudSag> listSag() {
        return Collections.emptyList();
    }

    /**
     * 查询阿里云SelectDB实例列表
     * TODO: SelectDB SDK 可能需要特殊配置
     *
     * @return SelectDB实例列表
     * @throws RuntimeException 当API调用失败时抛出异常
     */
    public List<ACloudSelectDb> listSelectDb() {
        // TODO: SelectDB 阿里云SDK暂无公开Maven依赖，使用空实现
        return Collections.emptyList();
    }

    /**
     * 查询阿里云Lindorm实例列表
     * TODO: Lindorm SDK (hitsdb20200615) 暂不可用，使用空实现
     *
     * @return Lindorm实例列表
     */
    public List<ACloudLindorm> listLindorm() {
        return Collections.emptyList();
    }
}
