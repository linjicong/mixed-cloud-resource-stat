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
package com.linjicong.cloud.stat.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.linjicong.cloud.stat.client.ACloudClient;
import com.linjicong.cloud.stat.dao.entity.CloudConf;
import com.linjicong.cloud.stat.dao.entity.acloud.*;
import com.linjicong.cloud.stat.dao.mapper.acloud.*;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 阿里云服务实现类
 * 实现阿里云资源的同步功能
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Service
public class ACloudService implements CloudService {

    @Resource
    private ACloudEcsMapper aCloudEcsMapper;
    @Resource
    private ACloudRdsMapper aCloudRdsMapper;
    @Resource
    private ACloudRedisMapper aCloudRedisMapper;
    @Resource
    private ACloudVpcMapper aCloudVpcMapper;
    @Resource
    private ACloudSlbMapper aCloudSlbMapper;
    @Resource
    private ACloudOssMapper aCloudOssMapper;
    @Resource
    private ACloudNatGatewayMapper aCloudNatGatewayMapper;
    @Resource
    private ACloudSecurityGroupMapper aCloudSecurityGroupMapper;
    @Resource
    private ACloudEipMapper aCloudEipMapper;
    @Resource
    private ACloudCdnMapper aCloudCdnMapper;
    @Resource
    private ACloudWafMapper aCloudWafMapper;
    @Resource
    private ACloudKmsMapper aCloudKmsMapper;
    @Resource
    private ACloudAckMapper aCloudAckMapper;
    @Resource
    private ACloudSlsMapper aCloudSlsMapper;
    @Resource
    private ACloudSmsMapper aCloudSmsMapper;
    @Resource
    private ACloudDnsDomainMapper aCloudDnsDomainMapper;
    @Resource
    private ACloudDnsDomainRecordsMapper aCloudDnsDomainRecordsMapper;
    @Resource
    private ACloudMongoDbMapper aCloudMongoDbMapper;
    @Resource
    private ACloudKafkaMapper aCloudKafkaMapper;
    @Resource
    private ACloudRocketMQMapper aCloudRocketMQMapper;
    @Resource
    private ACloudDiskMapper aCloudDiskMapper;
    @Resource
    private ACloudElasticsearchMapper aCloudElasticsearchMapper;
    @Resource
    private ACloudFcMapper aCloudFcMapper;
    @Resource
    private ACloudCmsMapper aCloudCmsMapper;
    @Resource
    private ACloudNasMapper aCloudNasMapper;
    @Resource
    private ACloudEssMapper aCloudEssMapper;
    @Resource
    private ACloudHssMapper aCloudHssMapper;
    @Resource
    private ACloudActionTrailMapper aCloudActionTrailMapper;
    @Resource
    private ACloudApiGatewayMapper aCloudApiGatewayMapper;
    @Resource
    private ACloudIoTMapper aCloudIoTMapper;
    @Resource
    private ACloudLiveMapper aCloudLiveMapper;
    @Resource
    private ACloudEmrMapper aCloudEmrMapper;
    @Resource
    private ACloudVodMapper aCloudVodMapper;
    @Resource
    private ACloudAcrMapper aCloudAcrMapper;
    @Resource
    private ACloudDdosMapper aCloudDdosMapper;
    @Resource
    private ACloudSslMapper aCloudSslMapper;
    @Resource
    private ACloudCloudFirewallMapper aCloudCloudFirewallMapper;
    @Resource
    private ACloudDscMapper aCloudDscMapper;
    @Resource
    private ACloudPolarDbMapper aCloudPolarDbMapper;
    @Resource
    private ACloudCbhMapper aCloudCbhMapper;
    @Resource
    private ACloudRabbitMqMapper aCloudRabbitMqMapper;
    @Resource
    private ACloudMseMapper aCloudMseMapper;
    @Resource
    private ACloudMaxComputeMapper aCloudMaxComputeMapper;
    @Resource
    private ACloudAnalyticDbMapper aCloudAnalyticDbMapper;
    @Resource
    private ACloudClickHouseMapper aCloudClickHouseMapper;
    @Resource
    private ACloudHologresMapper aCloudHologresMapper;
    @Resource
    private ACloudVpnGatewayMapper aCloudVpnGatewayMapper;
    @Resource
    private ACloudCenMapper aCloudCenMapper;
    @Resource
    private ACloudGaMapper aCloudGaMapper;
    @Resource
    private ACloudVpcEndpointMapper aCloudVpcEndpointMapper;
    @Resource
    private ACloudSaeMapper aCloudSaeMapper;
    @Resource
    private ACloudAlbMapper aCloudAlbMapper;
    @Resource
    private ACloudNlbMapper aCloudNlbMapper;
    @Resource
    private ACloudQuotaMapper aCloudQuotaMapper;
    @Resource
    private ACloudConfigMapper aCloudConfigMapper;
    @Resource
    private ACloudOosMapper aCloudOosMapper;
    @Resource
    private ACloudDtsMapper aCloudDtsMapper;
    @Resource
    private ACloudStorageGatewayMapper aCloudStorageGatewayMapper;
    @Resource
    private ACloudSagMapper aCloudSagMapper;
    @Resource
    private ACloudSelectDbMapper aCloudSelectDbMapper;
    @Resource
    private ACloudLindormMapper aCloudLindormMapper;

    /**
     * 同步所有阿里云资源
     *
     * @param cloudConf 云配置信息
     * @return 同步的资源总数
     */
    @Override
    public int syncEcs(CloudConf cloudConf) {
        ACloudClient aCloudClient = new ACloudClient(cloudConf);
        int total = 0;
        total += syncEcs(aCloudClient, cloudConf);
        total += syncRds(aCloudClient, cloudConf);
        total += syncRedis(aCloudClient, cloudConf);
        total += syncVpc(aCloudClient, cloudConf);
        total += syncSlb(aCloudClient, cloudConf);
        total += syncOss(aCloudClient, cloudConf);
        total += syncNatGateway(aCloudClient, cloudConf);
        total += syncSecurityGroup(aCloudClient, cloudConf);
        total += syncEip(aCloudClient, cloudConf);
        total += syncCdn(aCloudClient, cloudConf);
        total += syncWaf(aCloudClient, cloudConf);
        total += syncKms(aCloudClient, cloudConf);
        total += syncAck(aCloudClient, cloudConf);
        total += syncSls(aCloudClient, cloudConf);
        total += syncSms(aCloudClient, cloudConf);
        total += syncDnsDomain(aCloudClient, cloudConf);
        total += syncMongoDb(aCloudClient, cloudConf);
        total += syncKafka(aCloudClient, cloudConf);
        total += syncRocketMQ(aCloudClient, cloudConf);
        total += syncDisk(aCloudClient, cloudConf);
        total += syncElasticsearch(aCloudClient, cloudConf);
        total += syncFc(aCloudClient, cloudConf);
        total += syncCms(aCloudClient, cloudConf);
        total += syncNas(aCloudClient, cloudConf);
        total += syncEss(aCloudClient, cloudConf);
        total += syncHss(aCloudClient, cloudConf);
        total += syncActionTrail(aCloudClient, cloudConf);
        total += syncApiGateway(aCloudClient, cloudConf);
        total += syncIoT(aCloudClient, cloudConf);
        total += syncLive(aCloudClient, cloudConf);
        total += syncEmr(aCloudClient, cloudConf);
        total += syncVod(aCloudClient, cloudConf);
        total += syncAcr(aCloudClient, cloudConf);
        total += syncDdos(aCloudClient, cloudConf);
        total += syncSsl(aCloudClient, cloudConf);
        total += syncCloudFirewall(aCloudClient, cloudConf);
        total += syncDsc(aCloudClient, cloudConf);
        total += syncPolarDb(aCloudClient, cloudConf);
        total += syncCbh(aCloudClient, cloudConf);
        total += syncRabbitMq(aCloudClient, cloudConf);
        total += syncMse(aCloudClient, cloudConf);
        total += syncMaxCompute(aCloudClient, cloudConf);
        total += syncAnalyticDb(aCloudClient, cloudConf);
        total += syncClickHouse(aCloudClient, cloudConf);
        total += syncHologres(aCloudClient, cloudConf);
        total += syncVpnGateway(aCloudClient, cloudConf);
        total += syncCen(aCloudClient, cloudConf);
        total += syncGa(aCloudClient, cloudConf);
        total += syncVpcEndpoint(aCloudClient, cloudConf);
        total += syncSae(aCloudClient, cloudConf);
        total += syncAlb(aCloudClient, cloudConf);
        total += syncNlb(aCloudClient, cloudConf);
        total += syncQuota(aCloudClient, cloudConf);
        total += syncConfigResource(aCloudClient, cloudConf);
        total += syncOos(aCloudClient, cloudConf);
        total += syncDts(aCloudClient, cloudConf);
        total += syncStorageGateway(aCloudClient, cloudConf);
        total += syncSag(aCloudClient, cloudConf);
        total += syncSelectDb(aCloudClient, cloudConf);
        total += syncLindorm(aCloudClient, cloudConf);
        return total;
    }

    // ==================== ECS ====================

    private int syncEcs(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudEcs> apiList = aCloudClient.listEcs();
        List<ACloudEcs> dbList = aCloudEcsMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudEcs> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(ACloudEcs::getInstanceId, e -> e, (a, b) -> a));
        Map<String, ACloudEcs> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(ACloudEcs::getInstanceId, e -> e, (a, b) -> a));

        List<ACloudEcs> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudEcsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudEcs> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudEcs::getConfName, cloudConf.getName())
                    .in(ACloudEcs::getInstanceId, toDeleteIds)
                    .set(ACloudEcs::getDeleted, 1);
            aCloudEcsMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== RDS ====================

    private int syncRds(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudRds> apiList = aCloudClient.listRds();
        List<ACloudRds> dbList = aCloudRdsMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudRds> apiMap = apiList.stream()
                .filter(e -> e.getDBInstanceId() != null)
                .collect(Collectors.toMap(ACloudRds::getDBInstanceId, e -> e, (a, b) -> a));
        Map<String, ACloudRds> dbMap = dbList.stream()
                .filter(e -> e.getDBInstanceId() != null)
                .collect(Collectors.toMap(ACloudRds::getDBInstanceId, e -> e, (a, b) -> a));

        List<ACloudRds> toInsert = apiList.stream()
                .filter(e -> e.getDBInstanceId() != null && !dbMap.containsKey(e.getDBInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudRdsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudRds> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudRds::getConfName, cloudConf.getName())
                    .in(ACloudRds::getDBInstanceId, toDeleteIds)
                    .set(ACloudRds::getDeleted, 1);
            aCloudRdsMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== Redis ====================

    private int syncRedis(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudRedis> apiList = aCloudClient.listRedis();
        List<ACloudRedis> dbList = aCloudRedisMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudRedis> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(ACloudRedis::getInstanceId, e -> e, (a, b) -> a));
        Map<String, ACloudRedis> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(ACloudRedis::getInstanceId, e -> e, (a, b) -> a));

        List<ACloudRedis> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudRedisMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudRedis> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudRedis::getConfName, cloudConf.getName())
                    .in(ACloudRedis::getInstanceId, toDeleteIds)
                    .set(ACloudRedis::getDeleted, 1);
            aCloudRedisMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== VPC ====================

    private int syncVpc(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudVpc> apiList = aCloudClient.listVpc();
        List<ACloudVpc> dbList = aCloudVpcMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudVpc> apiMap = apiList.stream()
                .filter(e -> e.getVpcId() != null)
                .collect(Collectors.toMap(ACloudVpc::getVpcId, e -> e, (a, b) -> a));
        Map<String, ACloudVpc> dbMap = dbList.stream()
                .filter(e -> e.getVpcId() != null)
                .collect(Collectors.toMap(ACloudVpc::getVpcId, e -> e, (a, b) -> a));

        List<ACloudVpc> toInsert = apiList.stream()
                .filter(e -> e.getVpcId() != null && !dbMap.containsKey(e.getVpcId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudVpcMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudVpc> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudVpc::getConfName, cloudConf.getName())
                    .in(ACloudVpc::getVpcId, toDeleteIds)
                    .set(ACloudVpc::getDeleted, 1);
            aCloudVpcMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== SLB ====================

    private int syncSlb(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudSlb> apiList = aCloudClient.listSlb();
        List<ACloudSlb> dbList = aCloudSlbMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudSlb> apiMap = apiList.stream()
                .filter(e -> e.getLoadBalancerId() != null)
                .collect(Collectors.toMap(ACloudSlb::getLoadBalancerId, e -> e, (a, b) -> a));
        Map<String, ACloudSlb> dbMap = dbList.stream()
                .filter(e -> e.getLoadBalancerId() != null)
                .collect(Collectors.toMap(ACloudSlb::getLoadBalancerId, e -> e, (a, b) -> a));

        List<ACloudSlb> toInsert = apiList.stream()
                .filter(e -> e.getLoadBalancerId() != null && !dbMap.containsKey(e.getLoadBalancerId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudSlbMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudSlb> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudSlb::getConfName, cloudConf.getName())
                    .in(ACloudSlb::getLoadBalancerId, toDeleteIds)
                    .set(ACloudSlb::getDeleted, 1);
            aCloudSlbMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== OSS ====================

    private int syncOss(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudOss> apiList = aCloudClient.listOss();
        List<ACloudOss> dbList = aCloudOssMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudOss> apiMap = apiList.stream()
                .filter(e -> e.getName() != null)
                .collect(Collectors.toMap(ACloudOss::getName, e -> e, (a, b) -> a));
        Map<String, ACloudOss> dbMap = dbList.stream()
                .filter(e -> e.getName() != null)
                .collect(Collectors.toMap(ACloudOss::getName, e -> e, (a, b) -> a));

        List<ACloudOss> toInsert = apiList.stream()
                .filter(e -> e.getName() != null && !dbMap.containsKey(e.getName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudOssMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudOss> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudOss::getConfName, cloudConf.getName())
                    .in(ACloudOss::getName, toDeleteIds)
                    .set(ACloudOss::getDeleted, 1);
            aCloudOssMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== NAT Gateway ====================

    private int syncNatGateway(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudNatGateway> apiList = aCloudClient.listNatGateway();
        List<ACloudNatGateway> dbList = aCloudNatGatewayMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudNatGateway> apiMap = apiList.stream()
                .filter(e -> e.getNatGatewayId() != null)
                .collect(Collectors.toMap(ACloudNatGateway::getNatGatewayId, e -> e, (a, b) -> a));
        Map<String, ACloudNatGateway> dbMap = dbList.stream()
                .filter(e -> e.getNatGatewayId() != null)
                .collect(Collectors.toMap(ACloudNatGateway::getNatGatewayId, e -> e, (a, b) -> a));

        List<ACloudNatGateway> toInsert = apiList.stream()
                .filter(e -> e.getNatGatewayId() != null && !dbMap.containsKey(e.getNatGatewayId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudNatGatewayMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudNatGateway> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudNatGateway::getConfName, cloudConf.getName())
                    .in(ACloudNatGateway::getNatGatewayId, toDeleteIds)
                    .set(ACloudNatGateway::getDeleted, 1);
            aCloudNatGatewayMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== Security Group ====================

    private int syncSecurityGroup(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudSecurityGroup> apiList = aCloudClient.listSecurityGroup();
        List<ACloudSecurityGroup> dbList = aCloudSecurityGroupMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudSecurityGroup> apiMap = apiList.stream()
                .filter(e -> e.getSecurityGroupId() != null)
                .collect(Collectors.toMap(ACloudSecurityGroup::getSecurityGroupId, e -> e, (a, b) -> a));
        Map<String, ACloudSecurityGroup> dbMap = dbList.stream()
                .filter(e -> e.getSecurityGroupId() != null)
                .collect(Collectors.toMap(ACloudSecurityGroup::getSecurityGroupId, e -> e, (a, b) -> a));

        List<ACloudSecurityGroup> toInsert = apiList.stream()
                .filter(e -> e.getSecurityGroupId() != null && !dbMap.containsKey(e.getSecurityGroupId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudSecurityGroupMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudSecurityGroup> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudSecurityGroup::getConfName, cloudConf.getName())
                    .in(ACloudSecurityGroup::getSecurityGroupId, toDeleteIds)
                    .set(ACloudSecurityGroup::getDeleted, 1);
            aCloudSecurityGroupMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== EIP ====================

    private int syncEip(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudEip> apiList = aCloudClient.listEip();
        List<ACloudEip> dbList = aCloudEipMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudEip> apiMap = apiList.stream()
                .filter(e -> e.getAllocationId() != null)
                .collect(Collectors.toMap(ACloudEip::getAllocationId, e -> e, (a, b) -> a));
        Map<String, ACloudEip> dbMap = dbList.stream()
                .filter(e -> e.getAllocationId() != null)
                .collect(Collectors.toMap(ACloudEip::getAllocationId, e -> e, (a, b) -> a));

        List<ACloudEip> toInsert = apiList.stream()
                .filter(e -> e.getAllocationId() != null && !dbMap.containsKey(e.getAllocationId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudEipMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudEip> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudEip::getConfName, cloudConf.getName())
                    .in(ACloudEip::getAllocationId, toDeleteIds)
                    .set(ACloudEip::getDeleted, 1);
            aCloudEipMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CDN ====================

    private int syncCdn(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudCdn> apiList = aCloudClient.listCdn();
        List<ACloudCdn> dbList = aCloudCdnMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudCdn> apiMap = apiList.stream()
                .filter(e -> e.getDomainName() != null)
                .collect(Collectors.toMap(ACloudCdn::getDomainName, e -> e, (a, b) -> a));
        Map<String, ACloudCdn> dbMap = dbList.stream()
                .filter(e -> e.getDomainName() != null)
                .collect(Collectors.toMap(ACloudCdn::getDomainName, e -> e, (a, b) -> a));

        List<ACloudCdn> toInsert = apiList.stream()
                .filter(e -> e.getDomainName() != null && !dbMap.containsKey(e.getDomainName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudCdnMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudCdn> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudCdn::getConfName, cloudConf.getName())
                    .in(ACloudCdn::getDomainName, toDeleteIds)
                    .set(ACloudCdn::getDeleted, 1);
            aCloudCdnMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== WAF ====================

    private int syncWaf(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudWaf> apiList = aCloudClient.listWaf();
        List<ACloudWaf> dbList = aCloudWafMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudWaf> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(ACloudWaf::getInstanceId, e -> e, (a, b) -> a));
        Map<String, ACloudWaf> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(ACloudWaf::getInstanceId, e -> e, (a, b) -> a));

        List<ACloudWaf> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudWafMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudWaf> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudWaf::getConfName, cloudConf.getName())
                    .in(ACloudWaf::getInstanceId, toDeleteIds)
                    .set(ACloudWaf::getDeleted, 1);
            aCloudWafMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== KMS ====================

    private int syncKms(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudKms> apiList = aCloudClient.listKms();
        List<ACloudKms> dbList = aCloudKmsMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudKms> apiMap = apiList.stream()
                .filter(e -> e.getKeyId() != null)
                .collect(Collectors.toMap(ACloudKms::getKeyId, e -> e, (a, b) -> a));
        Map<String, ACloudKms> dbMap = dbList.stream()
                .filter(e -> e.getKeyId() != null)
                .collect(Collectors.toMap(ACloudKms::getKeyId, e -> e, (a, b) -> a));

        List<ACloudKms> toInsert = apiList.stream()
                .filter(e -> e.getKeyId() != null && !dbMap.containsKey(e.getKeyId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudKmsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudKms> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudKms::getConfName, cloudConf.getName())
                    .in(ACloudKms::getKeyId, toDeleteIds)
                    .set(ACloudKms::getDeleted, 1);
            aCloudKmsMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== ACK ====================

    private int syncAck(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudAck> apiList = aCloudClient.listAck();
        List<ACloudAck> dbList = aCloudAckMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudAck> apiMap = apiList.stream()
                .filter(e -> e.getClusterId() != null)
                .collect(Collectors.toMap(ACloudAck::getClusterId, e -> e, (a, b) -> a));
        Map<String, ACloudAck> dbMap = dbList.stream()
                .filter(e -> e.getClusterId() != null)
                .collect(Collectors.toMap(ACloudAck::getClusterId, e -> e, (a, b) -> a));

        List<ACloudAck> toInsert = apiList.stream()
                .filter(e -> e.getClusterId() != null && !dbMap.containsKey(e.getClusterId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudAckMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudAck> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudAck::getConfName, cloudConf.getName())
                    .in(ACloudAck::getClusterId, toDeleteIds)
                    .set(ACloudAck::getDeleted, 1);
            aCloudAckMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== SLS ====================

    private int syncSls(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudSls> apiList = aCloudClient.listSls();
        List<ACloudSls> dbList = aCloudSlsMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudSls> apiMap = apiList.stream()
                .filter(e -> e.getProjectName() != null)
                .collect(Collectors.toMap(ACloudSls::getProjectName, e -> e, (a, b) -> a));
        Map<String, ACloudSls> dbMap = dbList.stream()
                .filter(e -> e.getProjectName() != null)
                .collect(Collectors.toMap(ACloudSls::getProjectName, e -> e, (a, b) -> a));

        List<ACloudSls> toInsert = apiList.stream()
                .filter(e -> e.getProjectName() != null && !dbMap.containsKey(e.getProjectName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudSlsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudSls> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudSls::getConfName, cloudConf.getName())
                    .in(ACloudSls::getProjectName, toDeleteIds)
                    .set(ACloudSls::getDeleted, 1);
            aCloudSlsMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== SMS ====================

    private int syncSms(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudSms> apiList = aCloudClient.listSms();
        List<ACloudSms> dbList = aCloudSmsMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudSms> apiMap = apiList.stream()
                .filter(e -> e.getSignName() != null)
                .collect(Collectors.toMap(ACloudSms::getSignName, e -> e, (a, b) -> a));
        Map<String, ACloudSms> dbMap = dbList.stream()
                .filter(e -> e.getSignName() != null)
                .collect(Collectors.toMap(ACloudSms::getSignName, e -> e, (a, b) -> a));

        List<ACloudSms> toInsert = apiList.stream()
                .filter(e -> e.getSignName() != null && !dbMap.containsKey(e.getSignName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudSmsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudSms> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudSms::getConfName, cloudConf.getName())
                    .in(ACloudSms::getSignName, toDeleteIds)
                    .set(ACloudSms::getDeleted, 1);
            aCloudSmsMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== DNS Domain ====================

    private int syncDnsDomain(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudDnsDomain> apiList = aCloudClient.listDnsDomain();
        List<ACloudDnsDomain> dbList = aCloudDnsDomainMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudDnsDomain> apiMap = apiList.stream()
                .filter(e -> e.getDomainName() != null)
                .collect(Collectors.toMap(ACloudDnsDomain::getDomainName, e -> e, (a, b) -> a));
        Map<String, ACloudDnsDomain> dbMap = dbList.stream()
                .filter(e -> e.getDomainName() != null)
                .collect(Collectors.toMap(ACloudDnsDomain::getDomainName, e -> e, (a, b) -> a));

        List<ACloudDnsDomain> toInsert = apiList.stream()
                .filter(e -> e.getDomainName() != null && !dbMap.containsKey(e.getDomainName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudDnsDomainMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudDnsDomain> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudDnsDomain::getConfName, cloudConf.getName())
                    .in(ACloudDnsDomain::getDomainName, toDeleteIds)
                    .set(ACloudDnsDomain::getDeleted, 1);
            aCloudDnsDomainMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== MongoDB ====================

    private int syncMongoDb(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudMongoDb> apiList = aCloudClient.listMongoDb();
        List<ACloudMongoDb> dbList = aCloudMongoDbMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudMongoDb> apiMap = apiList.stream()
                .filter(e -> e.getDBInstanceId() != null)
                .collect(Collectors.toMap(ACloudMongoDb::getDBInstanceId, e -> e, (a, b) -> a));
        Map<String, ACloudMongoDb> dbMap = dbList.stream()
                .filter(e -> e.getDBInstanceId() != null)
                .collect(Collectors.toMap(ACloudMongoDb::getDBInstanceId, e -> e, (a, b) -> a));

        List<ACloudMongoDb> toInsert = apiList.stream()
                .filter(e -> e.getDBInstanceId() != null && !dbMap.containsKey(e.getDBInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudMongoDbMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudMongoDb> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudMongoDb::getConfName, cloudConf.getName())
                    .in(ACloudMongoDb::getDBInstanceId, toDeleteIds)
                    .set(ACloudMongoDb::getDeleted, 1);
            aCloudMongoDbMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== Kafka ====================

    private int syncKafka(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudKafka> apiList = aCloudClient.listKafka();
        List<ACloudKafka> dbList = aCloudKafkaMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudKafka> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(ACloudKafka::getInstanceId, e -> e, (a, b) -> a));
        Map<String, ACloudKafka> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(ACloudKafka::getInstanceId, e -> e, (a, b) -> a));

        List<ACloudKafka> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudKafkaMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudKafka> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudKafka::getConfName, cloudConf.getName())
                    .in(ACloudKafka::getInstanceId, toDeleteIds)
                    .set(ACloudKafka::getDeleted, 1);
            aCloudKafkaMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== RocketMQ ====================

    private int syncRocketMQ(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudRocketMQ> apiList = aCloudClient.listRocketMQ();
        List<ACloudRocketMQ> dbList = aCloudRocketMQMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudRocketMQ> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(ACloudRocketMQ::getInstanceId, e -> e, (a, b) -> a));
        Map<String, ACloudRocketMQ> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(ACloudRocketMQ::getInstanceId, e -> e, (a, b) -> a));

        List<ACloudRocketMQ> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudRocketMQMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudRocketMQ> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudRocketMQ::getConfName, cloudConf.getName())
                    .in(ACloudRocketMQ::getInstanceId, toDeleteIds)
                    .set(ACloudRocketMQ::getDeleted, 1);
            aCloudRocketMQMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== Disk ====================

    private int syncDisk(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudDisk> apiList = aCloudClient.listDisk();
        List<ACloudDisk> dbList = aCloudDiskMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudDisk> apiMap = apiList.stream()
                .filter(e -> e.getDiskId() != null)
                .collect(Collectors.toMap(ACloudDisk::getDiskId, e -> e, (a, b) -> a));
        Map<String, ACloudDisk> dbMap = dbList.stream()
                .filter(e -> e.getDiskId() != null)
                .collect(Collectors.toMap(ACloudDisk::getDiskId, e -> e, (a, b) -> a));

        List<ACloudDisk> toInsert = apiList.stream()
                .filter(e -> e.getDiskId() != null && !dbMap.containsKey(e.getDiskId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudDiskMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudDisk> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudDisk::getConfName, cloudConf.getName())
                    .in(ACloudDisk::getDiskId, toDeleteIds)
                    .set(ACloudDisk::getDeleted, 1);
            aCloudDiskMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== Elasticsearch ====================

    private int syncElasticsearch(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudElasticsearch> apiList = aCloudClient.listElasticsearch();
        List<ACloudElasticsearch> dbList = aCloudElasticsearchMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudElasticsearch> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(ACloudElasticsearch::getInstanceId, e -> e, (a, b) -> a));
        Map<String, ACloudElasticsearch> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(ACloudElasticsearch::getInstanceId, e -> e, (a, b) -> a));

        List<ACloudElasticsearch> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudElasticsearchMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudElasticsearch> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudElasticsearch::getConfName, cloudConf.getName())
                    .in(ACloudElasticsearch::getInstanceId, toDeleteIds)
                    .set(ACloudElasticsearch::getDeleted, 1);
            aCloudElasticsearchMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== FC ====================

    private int syncFc(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudFc> apiList = aCloudClient.listFc();
        List<ACloudFc> dbList = aCloudFcMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudFc> apiMap = apiList.stream()
                .filter(e -> e.getFunctionName() != null)
                .collect(Collectors.toMap(ACloudFc::getFunctionName, e -> e, (a, b) -> a));
        Map<String, ACloudFc> dbMap = dbList.stream()
                .filter(e -> e.getFunctionName() != null)
                .collect(Collectors.toMap(ACloudFc::getFunctionName, e -> e, (a, b) -> a));

        List<ACloudFc> toInsert = apiList.stream()
                .filter(e -> e.getFunctionName() != null && !dbMap.containsKey(e.getFunctionName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudFcMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudFc> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudFc::getConfName, cloudConf.getName())
                    .in(ACloudFc::getFunctionName, toDeleteIds)
                    .set(ACloudFc::getDeleted, 1);
            aCloudFcMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CMS ====================

    private int syncCms(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudCms> apiList = aCloudClient.listCms();
        List<ACloudCms> dbList = aCloudCmsMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudCms> apiMap = apiList.stream()
                .filter(e -> e.getGroupId() != null)
                .collect(Collectors.toMap(e -> String.valueOf(e.getGroupId()), e -> e, (a, b) -> a));
        Map<String, ACloudCms> dbMap = dbList.stream()
                .filter(e -> e.getGroupId() != null)
                .collect(Collectors.toMap(e -> String.valueOf(e.getGroupId()), e -> e, (a, b) -> a));

        List<ACloudCms> toInsert = apiList.stream()
                .filter(e -> e.getGroupId() != null && !dbMap.containsKey(e.getGroupId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudCmsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudCms> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudCms::getConfName, cloudConf.getName())
                    .in(ACloudCms::getGroupId, toDeleteIds)
                    .set(ACloudCms::getDeleted, 1);
            aCloudCmsMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== NAS ====================

    private int syncNas(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudNas> apiList = aCloudClient.listNas();
        List<ACloudNas> dbList = aCloudNasMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudNas> apiMap = apiList.stream()
                .filter(e -> e.getFileSystemId() != null)
                .collect(Collectors.toMap(ACloudNas::getFileSystemId, e -> e, (a, b) -> a));
        Map<String, ACloudNas> dbMap = dbList.stream()
                .filter(e -> e.getFileSystemId() != null)
                .collect(Collectors.toMap(ACloudNas::getFileSystemId, e -> e, (a, b) -> a));

        List<ACloudNas> toInsert = apiList.stream()
                .filter(e -> e.getFileSystemId() != null && !dbMap.containsKey(e.getFileSystemId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudNasMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudNas> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudNas::getConfName, cloudConf.getName())
                    .in(ACloudNas::getFileSystemId, toDeleteIds)
                    .set(ACloudNas::getDeleted, 1);
            aCloudNasMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== ESS ====================

    private int syncEss(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudEss> apiList = aCloudClient.listEss();
        List<ACloudEss> dbList = aCloudEssMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudEss> apiMap = apiList.stream()
                .filter(e -> e.getScalingGroupId() != null)
                .collect(Collectors.toMap(ACloudEss::getScalingGroupId, e -> e, (a, b) -> a));
        Map<String, ACloudEss> dbMap = dbList.stream()
                .filter(e -> e.getScalingGroupId() != null)
                .collect(Collectors.toMap(ACloudEss::getScalingGroupId, e -> e, (a, b) -> a));

        List<ACloudEss> toInsert = apiList.stream()
                .filter(e -> e.getScalingGroupId() != null && !dbMap.containsKey(e.getScalingGroupId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudEssMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudEss> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudEss::getConfName, cloudConf.getName())
                    .in(ACloudEss::getScalingGroupId, toDeleteIds)
                    .set(ACloudEss::getDeleted, 1);
            aCloudEssMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== HSS ====================

    private int syncHss(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudHss> apiList = aCloudClient.listHss();
        List<ACloudHss> dbList = aCloudHssMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudHss> apiMap = apiList.stream()
                .filter(e -> e.getUuid() != null)
                .collect(Collectors.toMap(ACloudHss::getUuid, e -> e, (a, b) -> a));
        Map<String, ACloudHss> dbMap = dbList.stream()
                .filter(e -> e.getUuid() != null)
                .collect(Collectors.toMap(ACloudHss::getUuid, e -> e, (a, b) -> a));

        List<ACloudHss> toInsert = apiList.stream()
                .filter(e -> e.getUuid() != null && !dbMap.containsKey(e.getUuid()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudHssMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudHss> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudHss::getConfName, cloudConf.getName())
                    .in(ACloudHss::getUuid, toDeleteIds)
                    .set(ACloudHss::getDeleted, 1);
            aCloudHssMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== ActionTrail ====================

    private int syncActionTrail(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudActionTrail> apiList = aCloudClient.listActionTrail();
        List<ACloudActionTrail> dbList = aCloudActionTrailMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudActionTrail> apiMap = apiList.stream()
                .filter(e -> e.getTrailName() != null)
                .collect(Collectors.toMap(ACloudActionTrail::getTrailName, e -> e, (a, b) -> a));
        Map<String, ACloudActionTrail> dbMap = dbList.stream()
                .filter(e -> e.getTrailName() != null)
                .collect(Collectors.toMap(ACloudActionTrail::getTrailName, e -> e, (a, b) -> a));

        List<ACloudActionTrail> toInsert = apiList.stream()
                .filter(e -> e.getTrailName() != null && !dbMap.containsKey(e.getTrailName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudActionTrailMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudActionTrail> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudActionTrail::getConfName, cloudConf.getName())
                    .in(ACloudActionTrail::getTrailName, toDeleteIds)
                    .set(ACloudActionTrail::getDeleted, 1);
            aCloudActionTrailMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== ApiGateway ====================

    private int syncApiGateway(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudApiGateway> apiList = aCloudClient.listApiGateway();
        List<ACloudApiGateway> dbList = aCloudApiGatewayMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudApiGateway> apiMap = apiList.stream()
                .filter(e -> e.getApiGroupId() != null)
                .collect(Collectors.toMap(ACloudApiGateway::getApiGroupId, e -> e, (a, b) -> a));
        Map<String, ACloudApiGateway> dbMap = dbList.stream()
                .filter(e -> e.getApiGroupId() != null)
                .collect(Collectors.toMap(ACloudApiGateway::getApiGroupId, e -> e, (a, b) -> a));

        List<ACloudApiGateway> toInsert = apiList.stream()
                .filter(e -> e.getApiGroupId() != null && !dbMap.containsKey(e.getApiGroupId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudApiGatewayMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudApiGateway> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudApiGateway::getConfName, cloudConf.getName())
                    .in(ACloudApiGateway::getApiGroupId, toDeleteIds)
                    .set(ACloudApiGateway::getDeleted, 1);
            aCloudApiGatewayMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== IoT ====================

    private int syncIoT(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudIoT> apiList = aCloudClient.listIoT();
        List<ACloudIoT> dbList = aCloudIoTMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudIoT> apiMap = apiList.stream()
                .filter(e -> e.getProductKey() != null)
                .collect(Collectors.toMap(ACloudIoT::getProductKey, e -> e, (a, b) -> a));
        Map<String, ACloudIoT> dbMap = dbList.stream()
                .filter(e -> e.getProductKey() != null)
                .collect(Collectors.toMap(ACloudIoT::getProductKey, e -> e, (a, b) -> a));

        List<ACloudIoT> toInsert = apiList.stream()
                .filter(e -> e.getProductKey() != null && !dbMap.containsKey(e.getProductKey()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudIoTMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudIoT> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudIoT::getConfName, cloudConf.getName())
                    .in(ACloudIoT::getProductKey, toDeleteIds)
                    .set(ACloudIoT::getDeleted, 1);
            aCloudIoTMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== Live ====================

    private int syncLive(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudLive> apiList = aCloudClient.listLive();
        List<ACloudLive> dbList = aCloudLiveMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudLive> apiMap = apiList.stream()
                .filter(e -> e.getDomainName() != null)
                .collect(Collectors.toMap(ACloudLive::getDomainName, e -> e, (a, b) -> a));
        Map<String, ACloudLive> dbMap = dbList.stream()
                .filter(e -> e.getDomainName() != null)
                .collect(Collectors.toMap(ACloudLive::getDomainName, e -> e, (a, b) -> a));

        List<ACloudLive> toInsert = apiList.stream()
                .filter(e -> e.getDomainName() != null && !dbMap.containsKey(e.getDomainName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudLiveMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudLive> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudLive::getConfName, cloudConf.getName())
                    .in(ACloudLive::getDomainName, toDeleteIds)
                    .set(ACloudLive::getDeleted, 1);
            aCloudLiveMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== EMR ====================

    private int syncEmr(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudEmr> apiList = aCloudClient.listEmr();
        List<ACloudEmr> dbList = aCloudEmrMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudEmr> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(ACloudEmr::getId, e -> e, (a, b) -> a));
        Map<String, ACloudEmr> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(ACloudEmr::getId, e -> e, (a, b) -> a));

        List<ACloudEmr> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudEmrMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudEmr> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudEmr::getConfName, cloudConf.getName())
                    .in(ACloudEmr::getId, toDeleteIds)
                    .set(ACloudEmr::getDeleted, 1);
            aCloudEmrMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== VOD ====================

    private int syncVod(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudVod> apiList = aCloudClient.listVod();
        List<ACloudVod> dbList = aCloudVodMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudVod> apiMap = apiList.stream()
                .filter(e -> e.getVideoId() != null)
                .collect(Collectors.toMap(ACloudVod::getVideoId, e -> e, (a, b) -> a));
        Map<String, ACloudVod> dbMap = dbList.stream()
                .filter(e -> e.getVideoId() != null)
                .collect(Collectors.toMap(ACloudVod::getVideoId, e -> e, (a, b) -> a));

        List<ACloudVod> toInsert = apiList.stream()
                .filter(e -> e.getVideoId() != null && !dbMap.containsKey(e.getVideoId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudVodMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudVod> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudVod::getConfName, cloudConf.getName())
                    .in(ACloudVod::getVideoId, toDeleteIds)
                    .set(ACloudVod::getDeleted, 1);
            aCloudVodMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== ACR ====================

    private int syncAcr(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudAcr> apiList = aCloudClient.listAcr();
        List<ACloudAcr> dbList = aCloudAcrMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudAcr> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(ACloudAcr::getInstanceId, e -> e, (a, b) -> a));
        Map<String, ACloudAcr> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(ACloudAcr::getInstanceId, e -> e, (a, b) -> a));

        List<ACloudAcr> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudAcrMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudAcr> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudAcr::getConfName, cloudConf.getName())
                    .in(ACloudAcr::getInstanceId, toDeleteIds)
                    .set(ACloudAcr::getDeleted, 1);
            aCloudAcrMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== DDoS ====================

    private int syncDdos(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudDdos> apiList = aCloudClient.listDdos();
        List<ACloudDdos> dbList = aCloudDdosMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudDdos> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(ACloudDdos::getInstanceId, e -> e, (a, b) -> a));
        Map<String, ACloudDdos> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(ACloudDdos::getInstanceId, e -> e, (a, b) -> a));

        List<ACloudDdos> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudDdosMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudDdos> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudDdos::getConfName, cloudConf.getName())
                    .in(ACloudDdos::getInstanceId, toDeleteIds)
                    .set(ACloudDdos::getDeleted, 1);
            aCloudDdosMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== SSL ====================

    private int syncSsl(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudSsl> apiList = aCloudClient.listSsl();
        List<ACloudSsl> dbList = aCloudSslMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudSsl> apiMap = apiList.stream()
                .filter(e -> e.getCertId() != null)
                .collect(Collectors.toMap(ACloudSsl::getCertId, e -> e, (a, b) -> a));
        Map<String, ACloudSsl> dbMap = dbList.stream()
                .filter(e -> e.getCertId() != null)
                .collect(Collectors.toMap(ACloudSsl::getCertId, e -> e, (a, b) -> a));

        List<ACloudSsl> toInsert = apiList.stream()
                .filter(e -> e.getCertId() != null && !dbMap.containsKey(e.getCertId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudSslMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudSsl> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudSsl::getConfName, cloudConf.getName())
                    .in(ACloudSsl::getCertId, toDeleteIds)
                    .set(ACloudSsl::getDeleted, 1);
            aCloudSslMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CloudFirewall ====================

    private int syncCloudFirewall(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudCloudFirewall> apiList = aCloudClient.listCloudFirewall();
        List<ACloudCloudFirewall> dbList = aCloudCloudFirewallMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudCloudFirewall> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(ACloudCloudFirewall::getInstanceId, e -> e, (a, b) -> a));
        Map<String, ACloudCloudFirewall> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(ACloudCloudFirewall::getInstanceId, e -> e, (a, b) -> a));

        List<ACloudCloudFirewall> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudCloudFirewallMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudCloudFirewall> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudCloudFirewall::getConfName, cloudConf.getName())
                    .in(ACloudCloudFirewall::getInstanceId, toDeleteIds)
                    .set(ACloudCloudFirewall::getDeleted, 1);
            aCloudCloudFirewallMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== DSC ====================

    private int syncDsc(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudDsc> apiList = aCloudClient.listDsc();
        List<ACloudDsc> dbList = aCloudDscMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudDsc> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(ACloudDsc::getInstanceId, e -> e, (a, b) -> a));
        Map<String, ACloudDsc> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(ACloudDsc::getInstanceId, e -> e, (a, b) -> a));

        List<ACloudDsc> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudDscMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudDsc> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudDsc::getConfName, cloudConf.getName())
                    .in(ACloudDsc::getInstanceId, toDeleteIds)
                    .set(ACloudDsc::getDeleted, 1);
            aCloudDscMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== PolarDB ====================

    private int syncPolarDb(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudPolarDb> apiList = aCloudClient.listPolarDb();
        List<ACloudPolarDb> dbList = aCloudPolarDbMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudPolarDb> apiMap = apiList.stream()
                .filter(e -> e.getDBClusterId() != null)
                .collect(Collectors.toMap(ACloudPolarDb::getDBClusterId, e -> e, (a, b) -> a));
        Map<String, ACloudPolarDb> dbMap = dbList.stream()
                .filter(e -> e.getDBClusterId() != null)
                .collect(Collectors.toMap(ACloudPolarDb::getDBClusterId, e -> e, (a, b) -> a));

        List<ACloudPolarDb> toInsert = apiList.stream()
                .filter(e -> e.getDBClusterId() != null && !dbMap.containsKey(e.getDBClusterId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudPolarDbMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudPolarDb> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudPolarDb::getConfName, cloudConf.getName())
                    .in(ACloudPolarDb::getDBClusterId, toDeleteIds)
                    .set(ACloudPolarDb::getDeleted, 1);
            aCloudPolarDbMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CBH ====================

    private int syncCbh(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudCbh> apiList = aCloudClient.listCbh();
        List<ACloudCbh> dbList = aCloudCbhMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudCbh> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(ACloudCbh::getInstanceId, e -> e, (a, b) -> a));
        Map<String, ACloudCbh> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(ACloudCbh::getInstanceId, e -> e, (a, b) -> a));

        List<ACloudCbh> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudCbhMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudCbh> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudCbh::getConfName, cloudConf.getName())
                    .in(ACloudCbh::getInstanceId, toDeleteIds)
                    .set(ACloudCbh::getDeleted, 1);
            aCloudCbhMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== RabbitMQ ====================

    private int syncRabbitMq(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudRabbitMq> apiList = aCloudClient.listRabbitMq();
        List<ACloudRabbitMq> dbList = aCloudRabbitMqMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudRabbitMq> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(ACloudRabbitMq::getInstanceId, e -> e, (a, b) -> a));
        Map<String, ACloudRabbitMq> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(ACloudRabbitMq::getInstanceId, e -> e, (a, b) -> a));

        List<ACloudRabbitMq> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudRabbitMqMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudRabbitMq> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudRabbitMq::getConfName, cloudConf.getName())
                    .in(ACloudRabbitMq::getInstanceId, toDeleteIds)
                    .set(ACloudRabbitMq::getDeleted, 1);
            aCloudRabbitMqMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== MSE ====================

    private int syncMse(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudMse> apiList = aCloudClient.listMse();
        List<ACloudMse> dbList = aCloudMseMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudMse> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(ACloudMse::getInstanceId, e -> e, (a, b) -> a));
        Map<String, ACloudMse> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(ACloudMse::getInstanceId, e -> e, (a, b) -> a));

        List<ACloudMse> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudMseMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudMse> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudMse::getConfName, cloudConf.getName())
                    .in(ACloudMse::getInstanceId, toDeleteIds)
                    .set(ACloudMse::getDeleted, 1);
            aCloudMseMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== MaxCompute ====================

    private int syncMaxCompute(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudMaxCompute> apiList = aCloudClient.listMaxCompute();
        List<ACloudMaxCompute> dbList = aCloudMaxComputeMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudMaxCompute> apiMap = apiList.stream()
                .filter(e -> e.getName() != null)
                .collect(Collectors.toMap(ACloudMaxCompute::getName, e -> e, (a, b) -> a));
        Map<String, ACloudMaxCompute> dbMap = dbList.stream()
                .filter(e -> e.getName() != null)
                .collect(Collectors.toMap(ACloudMaxCompute::getName, e -> e, (a, b) -> a));

        List<ACloudMaxCompute> toInsert = apiList.stream()
                .filter(e -> e.getName() != null && !dbMap.containsKey(e.getName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudMaxComputeMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudMaxCompute> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudMaxCompute::getConfName, cloudConf.getName())
                    .in(ACloudMaxCompute::getName, toDeleteIds)
                    .set(ACloudMaxCompute::getDeleted, 1);
            aCloudMaxComputeMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== AnalyticDB ====================

    private int syncAnalyticDb(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudAnalyticDb> apiList = aCloudClient.listAnalyticDb();
        List<ACloudAnalyticDb> dbList = aCloudAnalyticDbMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudAnalyticDb> apiMap = apiList.stream()
                .filter(e -> e.getDBClusterId() != null)
                .collect(Collectors.toMap(ACloudAnalyticDb::getDBClusterId, e -> e, (a, b) -> a));
        Map<String, ACloudAnalyticDb> dbMap = dbList.stream()
                .filter(e -> e.getDBClusterId() != null)
                .collect(Collectors.toMap(ACloudAnalyticDb::getDBClusterId, e -> e, (a, b) -> a));

        List<ACloudAnalyticDb> toInsert = apiList.stream()
                .filter(e -> e.getDBClusterId() != null && !dbMap.containsKey(e.getDBClusterId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudAnalyticDbMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudAnalyticDb> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudAnalyticDb::getConfName, cloudConf.getName())
                    .in(ACloudAnalyticDb::getDBClusterId, toDeleteIds)
                    .set(ACloudAnalyticDb::getDeleted, 1);
            aCloudAnalyticDbMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== ClickHouse ====================

    private int syncClickHouse(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudClickHouse> apiList = aCloudClient.listClickHouse();
        List<ACloudClickHouse> dbList = aCloudClickHouseMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudClickHouse> apiMap = apiList.stream()
                .filter(e -> e.getDBClusterId() != null)
                .collect(Collectors.toMap(ACloudClickHouse::getDBClusterId, e -> e, (a, b) -> a));
        Map<String, ACloudClickHouse> dbMap = dbList.stream()
                .filter(e -> e.getDBClusterId() != null)
                .collect(Collectors.toMap(ACloudClickHouse::getDBClusterId, e -> e, (a, b) -> a));

        List<ACloudClickHouse> toInsert = apiList.stream()
                .filter(e -> e.getDBClusterId() != null && !dbMap.containsKey(e.getDBClusterId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudClickHouseMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudClickHouse> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudClickHouse::getConfName, cloudConf.getName())
                    .in(ACloudClickHouse::getDBClusterId, toDeleteIds)
                    .set(ACloudClickHouse::getDeleted, 1);
            aCloudClickHouseMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== Hologres ====================

    private int syncHologres(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudHologres> apiList = aCloudClient.listHologres();
        List<ACloudHologres> dbList = aCloudHologresMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudHologres> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(ACloudHologres::getInstanceId, e -> e, (a, b) -> a));
        Map<String, ACloudHologres> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(ACloudHologres::getInstanceId, e -> e, (a, b) -> a));

        List<ACloudHologres> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudHologresMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudHologres> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudHologres::getConfName, cloudConf.getName())
                    .in(ACloudHologres::getInstanceId, toDeleteIds)
                    .set(ACloudHologres::getDeleted, 1);
            aCloudHologresMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== VpnGateway ====================

    private int syncVpnGateway(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudVpnGateway> apiList = aCloudClient.listVpnGateway();
        List<ACloudVpnGateway> dbList = aCloudVpnGatewayMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudVpnGateway> apiMap = apiList.stream()
                .filter(e -> e.getVpnGatewayId() != null)
                .collect(Collectors.toMap(ACloudVpnGateway::getVpnGatewayId, e -> e, (a, b) -> a));
        Map<String, ACloudVpnGateway> dbMap = dbList.stream()
                .filter(e -> e.getVpnGatewayId() != null)
                .collect(Collectors.toMap(ACloudVpnGateway::getVpnGatewayId, e -> e, (a, b) -> a));

        List<ACloudVpnGateway> toInsert = apiList.stream()
                .filter(e -> e.getVpnGatewayId() != null && !dbMap.containsKey(e.getVpnGatewayId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudVpnGatewayMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudVpnGateway> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudVpnGateway::getConfName, cloudConf.getName())
                    .in(ACloudVpnGateway::getVpnGatewayId, toDeleteIds)
                    .set(ACloudVpnGateway::getDeleted, 1);
            aCloudVpnGatewayMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CEN ====================

    private int syncCen(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudCen> apiList = aCloudClient.listCen();
        List<ACloudCen> dbList = aCloudCenMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudCen> apiMap = apiList.stream()
                .filter(e -> e.getCenId() != null)
                .collect(Collectors.toMap(ACloudCen::getCenId, e -> e, (a, b) -> a));
        Map<String, ACloudCen> dbMap = dbList.stream()
                .filter(e -> e.getCenId() != null)
                .collect(Collectors.toMap(ACloudCen::getCenId, e -> e, (a, b) -> a));

        List<ACloudCen> toInsert = apiList.stream()
                .filter(e -> e.getCenId() != null && !dbMap.containsKey(e.getCenId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudCenMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudCen> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudCen::getConfName, cloudConf.getName())
                    .in(ACloudCen::getCenId, toDeleteIds)
                    .set(ACloudCen::getDeleted, 1);
            aCloudCenMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== GA ====================

    private int syncGa(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudGa> apiList = aCloudClient.listGa();
        List<ACloudGa> dbList = aCloudGaMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudGa> apiMap = apiList.stream()
                .filter(e -> e.getAcceleratorId() != null)
                .collect(Collectors.toMap(ACloudGa::getAcceleratorId, e -> e, (a, b) -> a));
        Map<String, ACloudGa> dbMap = dbList.stream()
                .filter(e -> e.getAcceleratorId() != null)
                .collect(Collectors.toMap(ACloudGa::getAcceleratorId, e -> e, (a, b) -> a));

        List<ACloudGa> toInsert = apiList.stream()
                .filter(e -> e.getAcceleratorId() != null && !dbMap.containsKey(e.getAcceleratorId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudGaMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudGa> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudGa::getConfName, cloudConf.getName())
                    .in(ACloudGa::getAcceleratorId, toDeleteIds)
                    .set(ACloudGa::getDeleted, 1);
            aCloudGaMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== VpcEndpoint ====================

    private int syncVpcEndpoint(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudVpcEndpoint> apiList = aCloudClient.listVpcEndpoint();
        List<ACloudVpcEndpoint> dbList = aCloudVpcEndpointMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudVpcEndpoint> apiMap = apiList.stream()
                .filter(e -> e.getEndpointId() != null)
                .collect(Collectors.toMap(ACloudVpcEndpoint::getEndpointId, e -> e, (a, b) -> a));
        Map<String, ACloudVpcEndpoint> dbMap = dbList.stream()
                .filter(e -> e.getEndpointId() != null)
                .collect(Collectors.toMap(ACloudVpcEndpoint::getEndpointId, e -> e, (a, b) -> a));

        List<ACloudVpcEndpoint> toInsert = apiList.stream()
                .filter(e -> e.getEndpointId() != null && !dbMap.containsKey(e.getEndpointId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudVpcEndpointMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudVpcEndpoint> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudVpcEndpoint::getConfName, cloudConf.getName())
                    .in(ACloudVpcEndpoint::getEndpointId, toDeleteIds)
                    .set(ACloudVpcEndpoint::getDeleted, 1);
            aCloudVpcEndpointMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== SAE ====================

    private int syncSae(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudSae> apiList = aCloudClient.listSae();
        List<ACloudSae> dbList = aCloudSaeMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudSae> apiMap = apiList.stream()
                .filter(e -> e.getAppId() != null)
                .collect(Collectors.toMap(ACloudSae::getAppId, e -> e, (a, b) -> a));
        Map<String, ACloudSae> dbMap = dbList.stream()
                .filter(e -> e.getAppId() != null)
                .collect(Collectors.toMap(ACloudSae::getAppId, e -> e, (a, b) -> a));

        List<ACloudSae> toInsert = apiList.stream()
                .filter(e -> e.getAppId() != null && !dbMap.containsKey(e.getAppId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudSaeMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudSae> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudSae::getConfName, cloudConf.getName())
                    .in(ACloudSae::getAppId, toDeleteIds)
                    .set(ACloudSae::getDeleted, 1);
            aCloudSaeMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== ALB ====================

    private int syncAlb(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudAlb> apiList = aCloudClient.listAlb();
        List<ACloudAlb> dbList = aCloudAlbMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudAlb> apiMap = apiList.stream()
                .filter(e -> e.getLoadBalancerId() != null)
                .collect(Collectors.toMap(ACloudAlb::getLoadBalancerId, e -> e, (a, b) -> a));
        Map<String, ACloudAlb> dbMap = dbList.stream()
                .filter(e -> e.getLoadBalancerId() != null)
                .collect(Collectors.toMap(ACloudAlb::getLoadBalancerId, e -> e, (a, b) -> a));

        List<ACloudAlb> toInsert = apiList.stream()
                .filter(e -> e.getLoadBalancerId() != null && !dbMap.containsKey(e.getLoadBalancerId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudAlbMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudAlb> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudAlb::getConfName, cloudConf.getName())
                    .in(ACloudAlb::getLoadBalancerId, toDeleteIds)
                    .set(ACloudAlb::getDeleted, 1);
            aCloudAlbMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== NLB ====================

    private int syncNlb(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudNlb> apiList = aCloudClient.listNlb();
        List<ACloudNlb> dbList = aCloudNlbMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudNlb> apiMap = apiList.stream()
                .filter(e -> e.getLoadBalancerId() != null)
                .collect(Collectors.toMap(ACloudNlb::getLoadBalancerId, e -> e, (a, b) -> a));
        Map<String, ACloudNlb> dbMap = dbList.stream()
                .filter(e -> e.getLoadBalancerId() != null)
                .collect(Collectors.toMap(ACloudNlb::getLoadBalancerId, e -> e, (a, b) -> a));

        List<ACloudNlb> toInsert = apiList.stream()
                .filter(e -> e.getLoadBalancerId() != null && !dbMap.containsKey(e.getLoadBalancerId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudNlbMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudNlb> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudNlb::getConfName, cloudConf.getName())
                    .in(ACloudNlb::getLoadBalancerId, toDeleteIds)
                    .set(ACloudNlb::getDeleted, 1);
            aCloudNlbMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== Quota ====================

    private int syncQuota(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudQuota> apiList = aCloudClient.listQuota();
        List<ACloudQuota> dbList = aCloudQuotaMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudQuota> apiMap = apiList.stream()
                .filter(e -> e.getQuotaId() != null)
                .collect(Collectors.toMap(ACloudQuota::getQuotaId, e -> e, (a, b) -> a));
        Map<String, ACloudQuota> dbMap = dbList.stream()
                .filter(e -> e.getQuotaId() != null)
                .collect(Collectors.toMap(ACloudQuota::getQuotaId, e -> e, (a, b) -> a));

        List<ACloudQuota> toInsert = apiList.stream()
                .filter(e -> e.getQuotaId() != null && !dbMap.containsKey(e.getQuotaId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudQuotaMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudQuota> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudQuota::getConfName, cloudConf.getName())
                    .in(ACloudQuota::getQuotaId, toDeleteIds)
                    .set(ACloudQuota::getDeleted, 1);
            aCloudQuotaMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== Config ====================

    private int syncConfigResource(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudConfig> apiList = aCloudClient.listConfigResource();
        List<ACloudConfig> dbList = aCloudConfigMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudConfig> apiMap = apiList.stream()
                .filter(e -> e.getResourceId() != null)
                .collect(Collectors.toMap(ACloudConfig::getResourceId, e -> e, (a, b) -> a));
        Map<String, ACloudConfig> dbMap = dbList.stream()
                .filter(e -> e.getResourceId() != null)
                .collect(Collectors.toMap(ACloudConfig::getResourceId, e -> e, (a, b) -> a));

        List<ACloudConfig> toInsert = apiList.stream()
                .filter(e -> e.getResourceId() != null && !dbMap.containsKey(e.getResourceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudConfigMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudConfig> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudConfig::getConfName, cloudConf.getName())
                    .in(ACloudConfig::getResourceId, toDeleteIds)
                    .set(ACloudConfig::getDeleted, 1);
            aCloudConfigMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== OOS ====================

    private int syncOos(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudOos> apiList = aCloudClient.listOos();
        List<ACloudOos> dbList = aCloudOosMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudOos> apiMap = apiList.stream()
                .filter(e -> e.getTemplateName() != null)
                .collect(Collectors.toMap(ACloudOos::getTemplateName, e -> e, (a, b) -> a));
        Map<String, ACloudOos> dbMap = dbList.stream()
                .filter(e -> e.getTemplateName() != null)
                .collect(Collectors.toMap(ACloudOos::getTemplateName, e -> e, (a, b) -> a));

        List<ACloudOos> toInsert = apiList.stream()
                .filter(e -> e.getTemplateName() != null && !dbMap.containsKey(e.getTemplateName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudOosMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudOos> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudOos::getConfName, cloudConf.getName())
                    .in(ACloudOos::getTemplateName, toDeleteIds)
                    .set(ACloudOos::getDeleted, 1);
            aCloudOosMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== DTS ====================

    private int syncDts(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudDts> apiList = aCloudClient.listDts();
        List<ACloudDts> dbList = aCloudDtsMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudDts> apiMap = apiList.stream()
                .filter(e -> e.getDtsInstanceId() != null)
                .collect(Collectors.toMap(ACloudDts::getDtsInstanceId, e -> e, (a, b) -> a));
        Map<String, ACloudDts> dbMap = dbList.stream()
                .filter(e -> e.getDtsInstanceId() != null)
                .collect(Collectors.toMap(ACloudDts::getDtsInstanceId, e -> e, (a, b) -> a));

        List<ACloudDts> toInsert = apiList.stream()
                .filter(e -> e.getDtsInstanceId() != null && !dbMap.containsKey(e.getDtsInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudDtsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudDts> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudDts::getConfName, cloudConf.getName())
                    .in(ACloudDts::getDtsInstanceId, toDeleteIds)
                    .set(ACloudDts::getDeleted, 1);
            aCloudDtsMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== StorageGateway ====================

    private int syncStorageGateway(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudStorageGateway> apiList = aCloudClient.listStorageGateway();
        List<ACloudStorageGateway> dbList = aCloudStorageGatewayMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudStorageGateway> apiMap = apiList.stream()
                .filter(e -> e.getGatewayId() != null)
                .collect(Collectors.toMap(ACloudStorageGateway::getGatewayId, e -> e, (a, b) -> a));
        Map<String, ACloudStorageGateway> dbMap = dbList.stream()
                .filter(e -> e.getGatewayId() != null)
                .collect(Collectors.toMap(ACloudStorageGateway::getGatewayId, e -> e, (a, b) -> a));

        List<ACloudStorageGateway> toInsert = apiList.stream()
                .filter(e -> e.getGatewayId() != null && !dbMap.containsKey(e.getGatewayId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudStorageGatewayMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudStorageGateway> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudStorageGateway::getConfName, cloudConf.getName())
                    .in(ACloudStorageGateway::getGatewayId, toDeleteIds)
                    .set(ACloudStorageGateway::getDeleted, 1);
            aCloudStorageGatewayMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== SAG ====================

    private int syncSag(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudSag> apiList = aCloudClient.listSag();
        List<ACloudSag> dbList = aCloudSagMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudSag> apiMap = apiList.stream()
                .filter(e -> e.getSmartAGId() != null)
                .collect(Collectors.toMap(ACloudSag::getSmartAGId, e -> e, (a, b) -> a));
        Map<String, ACloudSag> dbMap = dbList.stream()
                .filter(e -> e.getSmartAGId() != null)
                .collect(Collectors.toMap(ACloudSag::getSmartAGId, e -> e, (a, b) -> a));

        List<ACloudSag> toInsert = apiList.stream()
                .filter(e -> e.getSmartAGId() != null && !dbMap.containsKey(e.getSmartAGId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudSagMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudSag> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudSag::getConfName, cloudConf.getName())
                    .in(ACloudSag::getSmartAGId, toDeleteIds)
                    .set(ACloudSag::getDeleted, 1);
            aCloudSagMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== SelectDB ====================

    private int syncSelectDb(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudSelectDb> apiList = aCloudClient.listSelectDb();
        List<ACloudSelectDb> dbList = aCloudSelectDbMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudSelectDb> apiMap = apiList.stream()
                .filter(e -> e.getDBClusterId() != null)
                .collect(Collectors.toMap(ACloudSelectDb::getDBClusterId, e -> e, (a, b) -> a));
        Map<String, ACloudSelectDb> dbMap = dbList.stream()
                .filter(e -> e.getDBClusterId() != null)
                .collect(Collectors.toMap(ACloudSelectDb::getDBClusterId, e -> e, (a, b) -> a));

        List<ACloudSelectDb> toInsert = apiList.stream()
                .filter(e -> e.getDBClusterId() != null && !dbMap.containsKey(e.getDBClusterId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudSelectDbMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudSelectDb> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudSelectDb::getConfName, cloudConf.getName())
                    .in(ACloudSelectDb::getDBClusterId, toDeleteIds)
                    .set(ACloudSelectDb::getDeleted, 1);
            aCloudSelectDbMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== Lindorm ====================

    private int syncLindorm(ACloudClient aCloudClient, CloudConf cloudConf) {
        List<ACloudLindorm> apiList = aCloudClient.listLindorm();
        List<ACloudLindorm> dbList = aCloudLindormMapper.selectByConfName(cloudConf.getName());

        Map<String, ACloudLindorm> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(ACloudLindorm::getInstanceId, e -> e, (a, b) -> a));
        Map<String, ACloudLindorm> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(ACloudLindorm::getInstanceId, e -> e, (a, b) -> a));

        List<ACloudLindorm> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = aCloudLindormMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<ACloudLindorm> uw = new LambdaUpdateWrapper<>();
            uw.eq(ACloudLindorm::getConfName, cloudConf.getName())
                    .in(ACloudLindorm::getInstanceId, toDeleteIds)
                    .set(ACloudLindorm::getDeleted, 1);
            aCloudLindormMapper.update(null, uw);
        }
        return insertCount;
    }
}
