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
import com.linjicong.cloud.stat.client.HCloudClient;
import com.linjicong.cloud.stat.dao.entity.CloudConf;
import com.linjicong.cloud.stat.dao.entity.hcloud.*;
import com.linjicong.cloud.stat.dao.mapper.hcloud.*;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 华为云服务实现类
 * 实现华为云资源的同步功能
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Service
public class HCloudService implements CloudService {

    @Resource
    private HCloudEcsMapper hCloudEcsMapper;
    @Resource
    private HCloudEipMapper hCloudEipMapper;
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
    private HCloudDnsPrivateMapper hCloudDnsPrivateMapper;
    @Resource
    private HCloudDnsPrivateRecordSetsMapper hCloudDnsPrivateRecordSetsMapper;
    @Resource
    private HCloudCceMapper hCloudCceMapper;
    @Resource
    private HCloudAuthDomainMapper hCloudAuthDomainMapper;
    @Resource
    private HCloudUserMapper hCloudUserMapper;
    @Resource
    private HCloudPermanentAccessKeyMapper hCloudPermanentAccessKeyMapper;
    @Resource
    private HCloudImsMapper hCloudImsMapper;
    @Resource
    private HCloudCbrMapper hCloudCbrMapper;
    @Resource
    private HCloudResourceMapper hCloudResourceMapper;
    @Resource
    private HCloudBillsFeeRecordsMapper hCloudBillsFeeRecordsMapper;
    @Resource
    private HCloudBillsMonthlyBreakDownMapper hCloudBillsMonthlyBreakDownMapper;
    @Resource
    private HCloudResourceRecordDetailMapper hCloudResourceRecordDetailMapper;
    @Resource
    private HCloudNatMapper hCloudNatMapper;
    @Resource
    private HCloudFunctionGraphMapper hCloudFunctionGraphMapper;
    @Resource
    private HCloudVpnMapper hCloudVpnMapper;
    @Resource
    private HCloudGaussDbMapper hCloudGaussDbMapper;
    @Resource
    private HCloudKmsMapper hCloudKmsMapper;
    @Resource
    private HCloudWafMapper hCloudWafMapper;
    @Resource
    private HCloudCtsMapper hCloudCtsMapper;
    @Resource
    private HCloudKafkaMapper hCloudKafkaMapper;
    @Resource
    private HCloudRocketMqMapper hCloudRocketMqMapper;
    @Resource
    private HCloudRabbitMqMapper hCloudRabbitMqMapper;
    @Resource
    private HCloudLtsMapper hCloudLtsMapper;
    @Resource
    private HCloudCdnMapper hCloudCdnMapper;
    @Resource
    private HCloudAntiDdosMapper hCloudAntiDdosMapper;
    @Resource
    private HCloudHssMapper hCloudHssMapper;
    @Resource
    private HCloudSwrMapper hCloudSwrMapper;
    @Resource
    private HCloudSmnMapper hCloudSmnMapper;
    @Resource
    private HCloudApigMapper hCloudApigMapper;
    @Resource
    private HCloudAomMapper hCloudAomMapper;
    @Resource
    private HCloudCssMapper hCloudCssMapper;
    @Resource
    private HCloudCfwMapper hCloudCfwMapper;
    @Resource
    private HCloudCcmMapper hCloudCcmMapper;
    @Resource
    private HCloudDrsMapper hCloudDrsMapper;
    @Resource
    private HCloudMrsMapper hCloudMrsMapper;
    @Resource
    private HCloudAsMapper hCloudAsMapper;
    @Resource
    private HCloudBmsMapper hCloudBmsMapper;
    @Resource
    private HCloudWorkspaceMapper hCloudWorkspaceMapper;
    @Resource
    private HCloudDliMapper hCloudDliMapper;
    @Resource
    private HCloudDwsMapper hCloudDwsMapper;
    @Resource
    private HCloudGaussDbNoSqlMapper hCloudGaussDbNoSqlMapper;
    @Resource
    private HCloudGaussDbOpenGaussMapper hCloudGaussDbOpenGaussMapper;
    @Resource
    private HCloudDdmMapper hCloudDdmMapper;
    @Resource
    private HCloudCseMapper hCloudCseMapper;
    @Resource
    private HCloudServiceStageMapper hCloudServiceStageMapper;
    @Resource
    private HCloudCbhMapper hCloudCbhMapper;
    @Resource
    private HCloudDbssMapper hCloudDbssMapper;
    @Resource
    private HCloudVodMapper hCloudVodMapper;
    @Resource
    private HCloudLiveMapper hCloudLiveMapper;
    @Resource
    private HCloudOmsMapper hCloudOmsMapper;
    @Resource
    private HCloudSdrsMapper hCloudSdrsMapper;
    @Resource
    private HCloudSmsMapper hCloudSmsMapper;
    @Resource
    private HCloudDscMapper hCloudDscMapper;
    @Resource
    private HCloudRomaMapper hCloudRomaMapper;
    @Resource
    private HCloudCphMapper hCloudCphMapper;
    @Resource
    private HCloudErMapper hCloudErMapper;
    @Resource
    private HCloudVpcepMapper hCloudVpcepMapper;
    @Resource
    private HCloudIefMapper hCloudIefMapper;
    @Resource
    private HCloudIotDaMapper hCloudIotDaMapper;
    @Resource
    private HCloudDehMapper hCloudDehMapper;
    @Resource
    private HCloudBcsMapper hCloudBcsMapper;
    @Resource
    private HCloudDcMapper hCloudDcMapper;
    @Resource
    private HCloudGaMapper hCloudGaMapper;
    @Resource
    private HCloudEgMapper hCloudEgMapper;
    @Resource
    private HCloudApmMapper hCloudApmMapper;
    @Resource
    private HCloudCloudTableMapper hCloudCloudTableMapper;
    @Resource
    private HCloudDataArtsStudioMapper hCloudDataArtsStudioMapper;
    @Resource
    private HCloudDisMapper hCloudDisMapper;
    @Resource
    private HCloudMasMapper hCloudMasMapper;
    @Resource
    private HCloudMpcMapper hCloudMpcMapper;
    @Resource
    private HCloudCloudDcMapper hCloudCloudDcMapper;
    @Resource
    private HCloudKvsMapper hCloudKvsMapper;
    @Resource
    private HCloudEdsMapper hCloudEdsMapper;
    @Resource
    private HCloudTicsMapper hCloudTicsMapper;
    @Resource
    private HCloudOrganizationsMapper hCloudOrganizationsMapper;
    @Resource
    private HCloudRamMapper hCloudRamMapper;
    @Resource
    private HCloudCocMapper hCloudCocMapper;

    /**
     * 同步所有华为云资源
     *
     * @param cloudConf 云配置信息
     * @return 同步的资源总数
     */
    @Override
    public int syncEcs(CloudConf cloudConf) {
        HCloudClient hCloudClient = new HCloudClient(cloudConf);
        int total = 0;
        total += syncEcs(hCloudClient, cloudConf);
        total += syncEip(hCloudClient, cloudConf);
        total += syncRds(hCloudClient, cloudConf);
        total += syncDcs(hCloudClient, cloudConf);
        total += syncDds(hCloudClient, cloudConf);
        total += syncObs(hCloudClient, cloudConf);
        total += syncSfs(hCloudClient, cloudConf);
        total += syncElb(hCloudClient, cloudConf);
        total += syncVpc(hCloudClient, cloudConf);
        total += syncEvs(hCloudClient, cloudConf);
        total += syncCesMetric(hCloudClient, cloudConf);
        total += syncDnsPrivate(hCloudClient, cloudConf);
        total += syncDnsPrivateRecordSets(hCloudClient, cloudConf);
        total += syncCce(hCloudClient, cloudConf);
        total += syncAuthDomain(hCloudClient, cloudConf);
        total += syncUser(hCloudClient, cloudConf);
        total += syncIms(hCloudClient, cloudConf);
        total += syncCbr(hCloudClient, cloudConf);
        total += syncResource(hCloudClient, cloudConf);
        total += syncNat(hCloudClient, cloudConf);
        total += syncFunctionGraph(hCloudClient, cloudConf);
        total += syncVpn(hCloudClient, cloudConf);
        total += syncGaussDb(hCloudClient, cloudConf);
        total += syncKms(hCloudClient, cloudConf);
        total += syncWaf(hCloudClient, cloudConf);
        total += syncCts(hCloudClient, cloudConf);
        total += syncKafka(hCloudClient, cloudConf);
        total += syncRocketMq(hCloudClient, cloudConf);
        total += syncRabbitMq(hCloudClient, cloudConf);
        total += syncLts(hCloudClient, cloudConf);
        total += syncCdn(hCloudClient, cloudConf);
        total += syncAntiDdos(hCloudClient, cloudConf);
        total += syncHss(hCloudClient, cloudConf);
        total += syncSwr(hCloudClient, cloudConf);
        total += syncSmn(hCloudClient, cloudConf);
        total += syncApig(hCloudClient, cloudConf);
        total += syncAom(hCloudClient, cloudConf);
        total += syncCss(hCloudClient, cloudConf);
        total += syncCfw(hCloudClient, cloudConf);
        total += syncCcm(hCloudClient, cloudConf);
        total += syncDrs(hCloudClient, cloudConf);
        total += syncMrs(hCloudClient, cloudConf);
        total += syncAs(hCloudClient, cloudConf);
        total += syncBms(hCloudClient, cloudConf);
        total += syncWorkspace(hCloudClient, cloudConf);
        total += syncDli(hCloudClient, cloudConf);
        total += syncDws(hCloudClient, cloudConf);
        total += syncGaussDbNoSql(hCloudClient, cloudConf);
        total += syncGaussDbOpenGauss(hCloudClient, cloudConf);
        total += syncDdm(hCloudClient, cloudConf);
        total += syncCse(hCloudClient, cloudConf);
        total += syncServiceStage(hCloudClient, cloudConf);
        total += syncCbh(hCloudClient, cloudConf);
        total += syncDbss(hCloudClient, cloudConf);
        total += syncVod(hCloudClient, cloudConf);
        total += syncLive(hCloudClient, cloudConf);
        total += syncOms(hCloudClient, cloudConf);
        total += syncSdrs(hCloudClient, cloudConf);
        total += syncSms(hCloudClient, cloudConf);
        total += syncDsc(hCloudClient, cloudConf);
        total += syncRoma(hCloudClient, cloudConf);
        total += syncCph(hCloudClient, cloudConf);
        total += syncEr(hCloudClient, cloudConf);
        total += syncVpcep(hCloudClient, cloudConf);
        total += syncIef(hCloudClient, cloudConf);
        total += syncIotDa(hCloudClient, cloudConf);
        total += syncDeh(hCloudClient, cloudConf);
        total += syncBcs(hCloudClient, cloudConf);
        total += syncDc(hCloudClient, cloudConf);
        total += syncGa(hCloudClient, cloudConf);
        total += syncEg(hCloudClient, cloudConf);
        total += syncApm(hCloudClient, cloudConf);
        total += syncCloudTable(hCloudClient, cloudConf);
        total += syncDataArtsStudio(hCloudClient, cloudConf);
        total += syncDis(hCloudClient, cloudConf);
        total += syncMas(hCloudClient, cloudConf);
        total += syncMpc(hCloudClient, cloudConf);
        total += syncCloudDc(hCloudClient, cloudConf);
        total += syncKvs(hCloudClient, cloudConf);
        total += syncEds(hCloudClient, cloudConf);
        total += syncTics(hCloudClient, cloudConf);
        total += syncOrganizations(hCloudClient, cloudConf);
        total += syncRam(hCloudClient, cloudConf);
        total += syncCoc(hCloudClient, cloudConf);
        return total;
    }

    // ==================== ECS ====================

    private int syncEcs(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudEcs> apiList = hCloudClient.listEcs();
        List<HCloudEcs> dbList = hCloudEcsMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudEcs> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudEcs::getId, e -> e, (a, b) -> a));
        Map<String, HCloudEcs> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudEcs::getId, e -> e, (a, b) -> a));

        List<HCloudEcs> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudEcsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudEcs> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudEcs::getConfName, cloudConf.getName())
                    .in(HCloudEcs::getId, toDeleteIds)
                    .set(HCloudEcs::getDeleted, 1);
            hCloudEcsMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== EIP ====================

    private int syncEip(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudEip> apiList = hCloudClient.listEip();
        List<HCloudEip> dbList = hCloudEipMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudEip> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudEip::getId, e -> e, (a, b) -> a));
        Map<String, HCloudEip> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudEip::getId, e -> e, (a, b) -> a));

        List<HCloudEip> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudEipMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudEip> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudEip::getConfName, cloudConf.getName())
                    .in(HCloudEip::getId, toDeleteIds)
                    .set(HCloudEip::getDeleted, 1);
            hCloudEipMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== RDS ====================

    private int syncRds(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudRds> apiList = hCloudClient.listRds();
        List<HCloudRds> dbList = hCloudRdsMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudRds> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudRds::getId, e -> e, (a, b) -> a));
        Map<String, HCloudRds> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudRds::getId, e -> e, (a, b) -> a));

        List<HCloudRds> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudRdsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudRds> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudRds::getConfName, cloudConf.getName())
                    .in(HCloudRds::getId, toDeleteIds)
                    .set(HCloudRds::getDeleted, 1);
            hCloudRdsMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== DCS (Redis) ====================

    private int syncDcs(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudDcs> apiList = hCloudClient.listDcs();
        List<HCloudDcs> dbList = hCloudDcsMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudDcs> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(HCloudDcs::getInstanceId, e -> e, (a, b) -> a));
        Map<String, HCloudDcs> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(HCloudDcs::getInstanceId, e -> e, (a, b) -> a));

        List<HCloudDcs> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudDcsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudDcs> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudDcs::getConfName, cloudConf.getName())
                    .in(HCloudDcs::getInstanceId, toDeleteIds)
                    .set(HCloudDcs::getDeleted, 1);
            hCloudDcsMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== DDS (MongoDB) ====================

    private int syncDds(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudDds> apiList = hCloudClient.listDds();
        List<HCloudDds> dbList = hCloudDdsMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudDds> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudDds::getId, e -> e, (a, b) -> a));
        Map<String, HCloudDds> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudDds::getId, e -> e, (a, b) -> a));

        List<HCloudDds> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudDdsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudDds> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudDds::getConfName, cloudConf.getName())
                    .in(HCloudDds::getId, toDeleteIds)
                    .set(HCloudDds::getDeleted, 1);
            hCloudDdsMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== OBS ====================

    private int syncObs(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudObs> apiList = hCloudClient.listObs();
        List<HCloudObs> dbList = hCloudObsMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudObs> apiMap = apiList.stream()
                .filter(e -> e.getBucketName() != null)
                .collect(Collectors.toMap(HCloudObs::getBucketName, e -> e, (a, b) -> a));
        Map<String, HCloudObs> dbMap = dbList.stream()
                .filter(e -> e.getBucketName() != null)
                .collect(Collectors.toMap(HCloudObs::getBucketName, e -> e, (a, b) -> a));

        List<HCloudObs> toInsert = apiList.stream()
                .filter(e -> e.getBucketName() != null && !dbMap.containsKey(e.getBucketName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudObsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudObs> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudObs::getConfName, cloudConf.getName())
                    .in(HCloudObs::getBucketName, toDeleteIds)
                    .set(HCloudObs::getDeleted, 1);
            hCloudObsMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== SFS ====================

    private int syncSfs(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudSfs> apiList = hCloudClient.listSfs();
        List<HCloudSfs> dbList = hCloudSfsMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudSfs> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudSfs::getId, e -> e, (a, b) -> a));
        Map<String, HCloudSfs> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudSfs::getId, e -> e, (a, b) -> a));

        List<HCloudSfs> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudSfsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudSfs> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudSfs::getConfName, cloudConf.getName())
                    .in(HCloudSfs::getId, toDeleteIds)
                    .set(HCloudSfs::getDeleted, 1);
            hCloudSfsMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== ELB ====================

    private int syncElb(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudElb> apiList = hCloudClient.listElb();
        List<HCloudElb> dbList = hCloudElbMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudElb> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudElb::getId, e -> e, (a, b) -> a));
        Map<String, HCloudElb> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudElb::getId, e -> e, (a, b) -> a));

        List<HCloudElb> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudElbMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudElb> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudElb::getConfName, cloudConf.getName())
                    .in(HCloudElb::getId, toDeleteIds)
                    .set(HCloudElb::getDeleted, 1);
            hCloudElbMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== VPC ====================

    private int syncVpc(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudVpc> apiList = hCloudClient.listVpc();
        List<HCloudVpc> dbList = hCloudVpcMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudVpc> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudVpc::getId, e -> e, (a, b) -> a));
        Map<String, HCloudVpc> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudVpc::getId, e -> e, (a, b) -> a));

        List<HCloudVpc> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudVpcMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudVpc> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudVpc::getConfName, cloudConf.getName())
                    .in(HCloudVpc::getId, toDeleteIds)
                    .set(HCloudVpc::getDeleted, 1);
            hCloudVpcMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== EVS ====================

    private int syncEvs(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudEvs> apiList = hCloudClient.listEvs();
        List<HCloudEvs> dbList = hCloudEvsMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudEvs> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudEvs::getId, e -> e, (a, b) -> a));
        Map<String, HCloudEvs> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudEvs::getId, e -> e, (a, b) -> a));

        List<HCloudEvs> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudEvsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudEvs> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudEvs::getConfName, cloudConf.getName())
                    .in(HCloudEvs::getId, toDeleteIds)
                    .set(HCloudEvs::getDeleted, 1);
            hCloudEvsMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CES Metric（复合键，全量替换） ====================

    private int syncCesMetric(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudCesMetric> apiList = hCloudClient.listCesMetric();
        List<HCloudCesMetric> dbList = hCloudCesMetricMapper.selectByConfName(cloudConf.getName());

        if (!dbList.isEmpty()) {
            LambdaUpdateWrapper<HCloudCesMetric> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudCesMetric::getConfName, cloudConf.getName())
                    .set(HCloudCesMetric::getDeleted, 1);
            hCloudCesMetricMapper.update(null, uw);
        }

        int insertCount = 0;
        if (!apiList.isEmpty()) {
            insertCount = hCloudCesMetricMapper.insertBatch(apiList);
        }
        return insertCount;
    }

    // ==================== DNS Private ====================

    private int syncDnsPrivate(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudDnsPrivate> apiList = hCloudClient.listDnsPrivate();
        List<HCloudDnsPrivate> dbList = hCloudDnsPrivateMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudDnsPrivate> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudDnsPrivate::getId, e -> e, (a, b) -> a));
        Map<String, HCloudDnsPrivate> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudDnsPrivate::getId, e -> e, (a, b) -> a));

        List<HCloudDnsPrivate> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudDnsPrivateMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudDnsPrivate> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudDnsPrivate::getConfName, cloudConf.getName())
                    .in(HCloudDnsPrivate::getId, toDeleteIds)
                    .set(HCloudDnsPrivate::getDeleted, 1);
            hCloudDnsPrivateMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== DNS Private Record Sets ====================

    private int syncDnsPrivateRecordSets(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudDnsPrivateRecordSets> apiList = hCloudClient.listDnsPrivateRecordSets();
        List<HCloudDnsPrivateRecordSets> dbList = hCloudDnsPrivateRecordSetsMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudDnsPrivateRecordSets> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudDnsPrivateRecordSets::getId, e -> e, (a, b) -> a));
        Map<String, HCloudDnsPrivateRecordSets> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudDnsPrivateRecordSets::getId, e -> e, (a, b) -> a));

        List<HCloudDnsPrivateRecordSets> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudDnsPrivateRecordSetsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudDnsPrivateRecordSets> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudDnsPrivateRecordSets::getConfName, cloudConf.getName())
                    .in(HCloudDnsPrivateRecordSets::getId, toDeleteIds)
                    .set(HCloudDnsPrivateRecordSets::getDeleted, 1);
            hCloudDnsPrivateRecordSetsMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CCE（以 metadata.name 为标识） ====================

    private int syncCce(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudCce> apiList = hCloudClient.listClusters();
        List<HCloudCce> dbList = hCloudCceMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudCce> apiMap = apiList.stream()
                .filter(e -> e.getMetadata() != null && e.getMetadata().getName() != null)
                .collect(Collectors.toMap(e -> e.getMetadata().getName(), e -> e, (a, b) -> a));
        Map<String, HCloudCce> dbMap = dbList.stream()
                .filter(e -> e.getMetadata() != null && e.getMetadata().getName() != null)
                .collect(Collectors.toMap(e -> e.getMetadata().getName(), e -> e, (a, b) -> a));

        List<HCloudCce> toInsert = apiList.stream()
                .filter(e -> e.getMetadata() != null && e.getMetadata().getName() != null
                        && !dbMap.containsKey(e.getMetadata().getName()))
                .collect(Collectors.toList());
        Set<String> toDeleteNames = dbMap.keySet().stream()
                .filter(name -> !apiMap.containsKey(name))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudCceMapper.insertBatch(toInsert);
        }
        if (!toDeleteNames.isEmpty()) {
            for (HCloudCce dbItem : dbList) {
                if (dbItem.getMetadata() != null && toDeleteNames.contains(dbItem.getMetadata().getName())) {
                    LambdaUpdateWrapper<HCloudCce> uw = new LambdaUpdateWrapper<>();
                    uw.eq(HCloudCce::getPk, dbItem.getPk())
                            .set(HCloudCce::getDeleted, 1);
                    hCloudCceMapper.update(null, uw);
                }
            }
        }
        return insertCount;
    }

    // ==================== Auth Domain ====================

    private int syncAuthDomain(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudAuthDomain> apiList = hCloudClient.listAuthDomains();
        List<HCloudAuthDomain> dbList = hCloudAuthDomainMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudAuthDomain> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudAuthDomain::getId, e -> e, (a, b) -> a));
        Map<String, HCloudAuthDomain> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudAuthDomain::getId, e -> e, (a, b) -> a));

        List<HCloudAuthDomain> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudAuthDomainMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudAuthDomain> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudAuthDomain::getConfName, cloudConf.getName())
                    .in(HCloudAuthDomain::getId, toDeleteIds)
                    .set(HCloudAuthDomain::getDeleted, 1);
            hCloudAuthDomainMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== User ====================

    private int syncUser(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudUser> apiList = hCloudClient.listUsers();
        List<HCloudUser> dbList = hCloudUserMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudUser> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudUser::getId, e -> e, (a, b) -> a));
        Map<String, HCloudUser> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudUser::getId, e -> e, (a, b) -> a));

        List<HCloudUser> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudUserMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudUser> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudUser::getConfName, cloudConf.getName())
                    .in(HCloudUser::getId, toDeleteIds)
                    .set(HCloudUser::getDeleted, 1);
            hCloudUserMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== IMS ====================

    private int syncIms(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudIms> apiList = hCloudClient.listImages();
        List<HCloudIms> dbList = hCloudImsMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudIms> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudIms::getId, e -> e, (a, b) -> a));
        Map<String, HCloudIms> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudIms::getId, e -> e, (a, b) -> a));

        List<HCloudIms> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudImsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudIms> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudIms::getConfName, cloudConf.getName())
                    .in(HCloudIms::getId, toDeleteIds)
                    .set(HCloudIms::getDeleted, 1);
            hCloudImsMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CBR ====================

    private int syncCbr(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudCbr> apiList = hCloudClient.listBackups();
        List<HCloudCbr> dbList = hCloudCbrMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudCbr> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudCbr::getId, e -> e, (a, b) -> a));
        Map<String, HCloudCbr> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudCbr::getId, e -> e, (a, b) -> a));

        List<HCloudCbr> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudCbrMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudCbr> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudCbr::getConfName, cloudConf.getName())
                    .in(HCloudCbr::getId, toDeleteIds)
                    .set(HCloudCbr::getDeleted, 1);
            hCloudCbrMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== Resource ====================

    private int syncResource(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudResource> apiList = hCloudClient.listAllResources();
        List<HCloudResource> dbList = hCloudResourceMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudResource> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudResource::getId, e -> e, (a, b) -> a));
        Map<String, HCloudResource> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudResource::getId, e -> e, (a, b) -> a));

        List<HCloudResource> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudResourceMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudResource> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudResource::getConfName, cloudConf.getName())
                    .in(HCloudResource::getId, toDeleteIds)
                    .set(HCloudResource::getDeleted, 1);
            hCloudResourceMapper.update(null, uw);
        }
        return insertCount;
    }
    private int syncNat(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudNat> apiList = hCloudClient.listNat();
        List<HCloudNat> dbList = hCloudNatMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudNat> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudNat::getId, e -> e, (a, b) -> a));
        Map<String, HCloudNat> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudNat::getId, e -> e, (a, b) -> a));

        List<HCloudNat> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudNatMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudNat> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudNat::getConfName, cloudConf.getName())
                    .in(HCloudNat::getId, toDeleteIds)
                    .set(HCloudNat::getDeleted, 1);
            hCloudNatMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncFunctionGraph(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudFunctionGraph> apiList = hCloudClient.listFunctionGraph();
        List<HCloudFunctionGraph> dbList = hCloudFunctionGraphMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudFunctionGraph> apiMap = apiList.stream()
                .filter(e -> e.getFuncUrn() != null)
                .collect(Collectors.toMap(HCloudFunctionGraph::getFuncUrn, e -> e, (a, b) -> a));
        Map<String, HCloudFunctionGraph> dbMap = dbList.stream()
                .filter(e -> e.getFuncUrn() != null)
                .collect(Collectors.toMap(HCloudFunctionGraph::getFuncUrn, e -> e, (a, b) -> a));

        List<HCloudFunctionGraph> toInsert = apiList.stream()
                .filter(e -> e.getFuncUrn() != null && !dbMap.containsKey(e.getFuncUrn()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudFunctionGraphMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudFunctionGraph> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudFunctionGraph::getConfName, cloudConf.getName())
                    .in(HCloudFunctionGraph::getFuncUrn, toDeleteIds)
                    .set(HCloudFunctionGraph::getDeleted, 1);
            hCloudFunctionGraphMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncVpn(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudVpn> apiList = hCloudClient.listVpn();
        List<HCloudVpn> dbList = hCloudVpnMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudVpn> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudVpn::getId, e -> e, (a, b) -> a));
        Map<String, HCloudVpn> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudVpn::getId, e -> e, (a, b) -> a));

        List<HCloudVpn> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudVpnMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudVpn> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudVpn::getConfName, cloudConf.getName())
                    .in(HCloudVpn::getId, toDeleteIds)
                    .set(HCloudVpn::getDeleted, 1);
            hCloudVpnMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncGaussDb(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudGaussDb> apiList = hCloudClient.listGaussDb();
        List<HCloudGaussDb> dbList = hCloudGaussDbMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudGaussDb> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudGaussDb::getId, e -> e, (a, b) -> a));
        Map<String, HCloudGaussDb> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudGaussDb::getId, e -> e, (a, b) -> a));

        List<HCloudGaussDb> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudGaussDbMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudGaussDb> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudGaussDb::getConfName, cloudConf.getName())
                    .in(HCloudGaussDb::getId, toDeleteIds)
                    .set(HCloudGaussDb::getDeleted, 1);
            hCloudGaussDbMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncKms(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudKms> apiList = hCloudClient.listKms();
        List<HCloudKms> dbList = hCloudKmsMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudKms> apiMap = apiList.stream()
                .filter(e -> e.getKeyId() != null)
                .collect(Collectors.toMap(HCloudKms::getKeyId, e -> e, (a, b) -> a));
        Map<String, HCloudKms> dbMap = dbList.stream()
                .filter(e -> e.getKeyId() != null)
                .collect(Collectors.toMap(HCloudKms::getKeyId, e -> e, (a, b) -> a));

        List<HCloudKms> toInsert = apiList.stream()
                .filter(e -> e.getKeyId() != null && !dbMap.containsKey(e.getKeyId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudKmsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudKms> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudKms::getConfName, cloudConf.getName())
                    .in(HCloudKms::getKeyId, toDeleteIds)
                    .set(HCloudKms::getDeleted, 1);
            hCloudKmsMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncWaf(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudWaf> apiList = hCloudClient.listWaf();
        List<HCloudWaf> dbList = hCloudWafMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudWaf> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudWaf::getId, e -> e, (a, b) -> a));
        Map<String, HCloudWaf> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudWaf::getId, e -> e, (a, b) -> a));

        List<HCloudWaf> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudWafMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudWaf> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudWaf::getConfName, cloudConf.getName())
                    .in(HCloudWaf::getId, toDeleteIds)
                    .set(HCloudWaf::getDeleted, 1);
            hCloudWafMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncCts(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudCts> apiList = hCloudClient.listCts();
        List<HCloudCts> dbList = hCloudCtsMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudCts> apiMap = apiList.stream()
                .filter(e -> e.getTrackerId() != null)
                .collect(Collectors.toMap(HCloudCts::getTrackerId, e -> e, (a, b) -> a));
        Map<String, HCloudCts> dbMap = dbList.stream()
                .filter(e -> e.getTrackerId() != null)
                .collect(Collectors.toMap(HCloudCts::getTrackerId, e -> e, (a, b) -> a));

        List<HCloudCts> toInsert = apiList.stream()
                .filter(e -> e.getTrackerId() != null && !dbMap.containsKey(e.getTrackerId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudCtsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudCts> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudCts::getConfName, cloudConf.getName())
                    .in(HCloudCts::getTrackerId, toDeleteIds)
                    .set(HCloudCts::getDeleted, 1);
            hCloudCtsMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncKafka(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudKafka> apiList = hCloudClient.listKafka();
        List<HCloudKafka> dbList = hCloudKafkaMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudKafka> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(HCloudKafka::getInstanceId, e -> e, (a, b) -> a));
        Map<String, HCloudKafka> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(HCloudKafka::getInstanceId, e -> e, (a, b) -> a));

        List<HCloudKafka> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudKafkaMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudKafka> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudKafka::getConfName, cloudConf.getName())
                    .in(HCloudKafka::getInstanceId, toDeleteIds)
                    .set(HCloudKafka::getDeleted, 1);
            hCloudKafkaMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncRocketMq(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudRocketMq> apiList = hCloudClient.listRocketMq();
        List<HCloudRocketMq> dbList = hCloudRocketMqMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudRocketMq> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(HCloudRocketMq::getInstanceId, e -> e, (a, b) -> a));
        Map<String, HCloudRocketMq> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(HCloudRocketMq::getInstanceId, e -> e, (a, b) -> a));

        List<HCloudRocketMq> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudRocketMqMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudRocketMq> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudRocketMq::getConfName, cloudConf.getName())
                    .in(HCloudRocketMq::getInstanceId, toDeleteIds)
                    .set(HCloudRocketMq::getDeleted, 1);
            hCloudRocketMqMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncRabbitMq(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudRabbitMq> apiList = hCloudClient.listRabbitMq();
        List<HCloudRabbitMq> dbList = hCloudRabbitMqMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudRabbitMq> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(HCloudRabbitMq::getInstanceId, e -> e, (a, b) -> a));
        Map<String, HCloudRabbitMq> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(HCloudRabbitMq::getInstanceId, e -> e, (a, b) -> a));

        List<HCloudRabbitMq> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudRabbitMqMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudRabbitMq> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudRabbitMq::getConfName, cloudConf.getName())
                    .in(HCloudRabbitMq::getInstanceId, toDeleteIds)
                    .set(HCloudRabbitMq::getDeleted, 1);
            hCloudRabbitMqMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncLts(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudLts> apiList = hCloudClient.listLts();
        List<HCloudLts> dbList = hCloudLtsMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudLts> apiMap = apiList.stream()
                .filter(e -> e.getLogGroupId() != null)
                .collect(Collectors.toMap(HCloudLts::getLogGroupId, e -> e, (a, b) -> a));
        Map<String, HCloudLts> dbMap = dbList.stream()
                .filter(e -> e.getLogGroupId() != null)
                .collect(Collectors.toMap(HCloudLts::getLogGroupId, e -> e, (a, b) -> a));

        List<HCloudLts> toInsert = apiList.stream()
                .filter(e -> e.getLogGroupId() != null && !dbMap.containsKey(e.getLogGroupId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudLtsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudLts> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudLts::getConfName, cloudConf.getName())
                    .in(HCloudLts::getLogGroupId, toDeleteIds)
                    .set(HCloudLts::getDeleted, 1);
            hCloudLtsMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncCdn(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudCdn> apiList = hCloudClient.listCdn();
        List<HCloudCdn> dbList = hCloudCdnMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudCdn> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudCdn::getId, e -> e, (a, b) -> a));
        Map<String, HCloudCdn> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudCdn::getId, e -> e, (a, b) -> a));

        List<HCloudCdn> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudCdnMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudCdn> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudCdn::getConfName, cloudConf.getName())
                    .in(HCloudCdn::getId, toDeleteIds)
                    .set(HCloudCdn::getDeleted, 1);
            hCloudCdnMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncAntiDdos(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudAntiDdos> apiList = hCloudClient.listAntiDdos();
        List<HCloudAntiDdos> dbList = hCloudAntiDdosMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudAntiDdos> apiMap = apiList.stream()
                .filter(e -> e.getFloatingIpId() != null)
                .collect(Collectors.toMap(HCloudAntiDdos::getFloatingIpId, e -> e, (a, b) -> a));
        Map<String, HCloudAntiDdos> dbMap = dbList.stream()
                .filter(e -> e.getFloatingIpId() != null)
                .collect(Collectors.toMap(HCloudAntiDdos::getFloatingIpId, e -> e, (a, b) -> a));

        List<HCloudAntiDdos> toInsert = apiList.stream()
                .filter(e -> e.getFloatingIpId() != null && !dbMap.containsKey(e.getFloatingIpId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudAntiDdosMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudAntiDdos> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudAntiDdos::getConfName, cloudConf.getName())
                    .in(HCloudAntiDdos::getFloatingIpId, toDeleteIds)
                    .set(HCloudAntiDdos::getDeleted, 1);
            hCloudAntiDdosMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncHss(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudHss> apiList = hCloudClient.listHss();
        List<HCloudHss> dbList = hCloudHssMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudHss> apiMap = apiList.stream()
                .filter(e -> e.getHostId() != null)
                .collect(Collectors.toMap(HCloudHss::getHostId, e -> e, (a, b) -> a));
        Map<String, HCloudHss> dbMap = dbList.stream()
                .filter(e -> e.getHostId() != null)
                .collect(Collectors.toMap(HCloudHss::getHostId, e -> e, (a, b) -> a));

        List<HCloudHss> toInsert = apiList.stream()
                .filter(e -> e.getHostId() != null && !dbMap.containsKey(e.getHostId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudHssMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudHss> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudHss::getConfName, cloudConf.getName())
                    .in(HCloudHss::getHostId, toDeleteIds)
                    .set(HCloudHss::getDeleted, 1);
            hCloudHssMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncSwr(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudSwr> apiList = hCloudClient.listSwr();
        List<HCloudSwr> dbList = hCloudSwrMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudSwr> apiMap = apiList.stream()
                .filter(e -> e.getNamespace() != null)
                .collect(Collectors.toMap(HCloudSwr::getNamespace, e -> e, (a, b) -> a));
        Map<String, HCloudSwr> dbMap = dbList.stream()
                .filter(e -> e.getNamespace() != null)
                .collect(Collectors.toMap(HCloudSwr::getNamespace, e -> e, (a, b) -> a));

        List<HCloudSwr> toInsert = apiList.stream()
                .filter(e -> e.getNamespace() != null && !dbMap.containsKey(e.getNamespace()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudSwrMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudSwr> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudSwr::getConfName, cloudConf.getName())
                    .in(HCloudSwr::getNamespace, toDeleteIds)
                    .set(HCloudSwr::getDeleted, 1);
            hCloudSwrMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncSmn(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudSmn> apiList = hCloudClient.listSmn();
        List<HCloudSmn> dbList = hCloudSmnMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudSmn> apiMap = apiList.stream()
                .filter(e -> e.getTopicUrn() != null)
                .collect(Collectors.toMap(HCloudSmn::getTopicUrn, e -> e, (a, b) -> a));
        Map<String, HCloudSmn> dbMap = dbList.stream()
                .filter(e -> e.getTopicUrn() != null)
                .collect(Collectors.toMap(HCloudSmn::getTopicUrn, e -> e, (a, b) -> a));

        List<HCloudSmn> toInsert = apiList.stream()
                .filter(e -> e.getTopicUrn() != null && !dbMap.containsKey(e.getTopicUrn()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudSmnMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudSmn> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudSmn::getConfName, cloudConf.getName())
                    .in(HCloudSmn::getTopicUrn, toDeleteIds)
                    .set(HCloudSmn::getDeleted, 1);
            hCloudSmnMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncApig(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudApig> apiList = hCloudClient.listApig();
        List<HCloudApig> dbList = hCloudApigMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudApig> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudApig::getId, e -> e, (a, b) -> a));
        Map<String, HCloudApig> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudApig::getId, e -> e, (a, b) -> a));

        List<HCloudApig> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudApigMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudApig> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudApig::getConfName, cloudConf.getName())
                    .in(HCloudApig::getId, toDeleteIds)
                    .set(HCloudApig::getDeleted, 1);
            hCloudApigMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncAom(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudAom> apiList = hCloudClient.listAom();
        List<HCloudAom> dbList = hCloudAomMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudAom> apiMap = apiList.stream()
                .filter(e -> e.getAlarmRuleId() != null)
                .collect(Collectors.toMap(HCloudAom::getAlarmRuleId, e -> e, (a, b) -> a));
        Map<String, HCloudAom> dbMap = dbList.stream()
                .filter(e -> e.getAlarmRuleId() != null)
                .collect(Collectors.toMap(HCloudAom::getAlarmRuleId, e -> e, (a, b) -> a));

        List<HCloudAom> toInsert = apiList.stream()
                .filter(e -> e.getAlarmRuleId() != null && !dbMap.containsKey(e.getAlarmRuleId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudAomMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudAom> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudAom::getConfName, cloudConf.getName())
                    .in(HCloudAom::getAlarmRuleId, toDeleteIds)
                    .set(HCloudAom::getDeleted, 1);
            hCloudAomMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncCss(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudCss> apiList = hCloudClient.listCss();
        List<HCloudCss> dbList = hCloudCssMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudCss> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudCss::getId, e -> e, (a, b) -> a));
        Map<String, HCloudCss> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudCss::getId, e -> e, (a, b) -> a));

        List<HCloudCss> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudCssMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudCss> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudCss::getConfName, cloudConf.getName())
                    .in(HCloudCss::getId, toDeleteIds)
                    .set(HCloudCss::getDeleted, 1);
            hCloudCssMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncCfw(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudCfw> apiList = hCloudClient.listCfw();
        List<HCloudCfw> dbList = hCloudCfwMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudCfw> apiMap = apiList.stream()
                .filter(e -> e.getFwInstanceId() != null)
                .collect(Collectors.toMap(HCloudCfw::getFwInstanceId, e -> e, (a, b) -> a));
        Map<String, HCloudCfw> dbMap = dbList.stream()
                .filter(e -> e.getFwInstanceId() != null)
                .collect(Collectors.toMap(HCloudCfw::getFwInstanceId, e -> e, (a, b) -> a));

        List<HCloudCfw> toInsert = apiList.stream()
                .filter(e -> e.getFwInstanceId() != null && !dbMap.containsKey(e.getFwInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudCfwMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudCfw> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudCfw::getConfName, cloudConf.getName())
                    .in(HCloudCfw::getFwInstanceId, toDeleteIds)
                    .set(HCloudCfw::getDeleted, 1);
            hCloudCfwMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncCcm(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudCcm> apiList = hCloudClient.listCcm();
        List<HCloudCcm> dbList = hCloudCcmMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudCcm> apiMap = apiList.stream()
                .filter(e -> e.getCertId() != null)
                .collect(Collectors.toMap(HCloudCcm::getCertId, e -> e, (a, b) -> a));
        Map<String, HCloudCcm> dbMap = dbList.stream()
                .filter(e -> e.getCertId() != null)
                .collect(Collectors.toMap(HCloudCcm::getCertId, e -> e, (a, b) -> a));

        List<HCloudCcm> toInsert = apiList.stream()
                .filter(e -> e.getCertId() != null && !dbMap.containsKey(e.getCertId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudCcmMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudCcm> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudCcm::getConfName, cloudConf.getName())
                    .in(HCloudCcm::getCertId, toDeleteIds)
                    .set(HCloudCcm::getDeleted, 1);
            hCloudCcmMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncDrs(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudDrs> apiList = hCloudClient.listDrs();
        List<HCloudDrs> dbList = hCloudDrsMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudDrs> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudDrs::getId, e -> e, (a, b) -> a));
        Map<String, HCloudDrs> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudDrs::getId, e -> e, (a, b) -> a));

        List<HCloudDrs> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudDrsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudDrs> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudDrs::getConfName, cloudConf.getName())
                    .in(HCloudDrs::getId, toDeleteIds)
                    .set(HCloudDrs::getDeleted, 1);
            hCloudDrsMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncMrs(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudMrs> apiList = hCloudClient.listMrs();
        List<HCloudMrs> dbList = hCloudMrsMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudMrs> apiMap = apiList.stream()
                .filter(e -> e.getClusterId() != null)
                .collect(Collectors.toMap(HCloudMrs::getClusterId, e -> e, (a, b) -> a));
        Map<String, HCloudMrs> dbMap = dbList.stream()
                .filter(e -> e.getClusterId() != null)
                .collect(Collectors.toMap(HCloudMrs::getClusterId, e -> e, (a, b) -> a));

        List<HCloudMrs> toInsert = apiList.stream()
                .filter(e -> e.getClusterId() != null && !dbMap.containsKey(e.getClusterId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudMrsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudMrs> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudMrs::getConfName, cloudConf.getName())
                    .in(HCloudMrs::getClusterId, toDeleteIds)
                    .set(HCloudMrs::getDeleted, 1);
            hCloudMrsMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncAs(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudAs> apiList = hCloudClient.listAs();
        List<HCloudAs> dbList = hCloudAsMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudAs> apiMap = apiList.stream()
                .filter(e -> e.getScalingGroupId() != null)
                .collect(Collectors.toMap(HCloudAs::getScalingGroupId, e -> e, (a, b) -> a));
        Map<String, HCloudAs> dbMap = dbList.stream()
                .filter(e -> e.getScalingGroupId() != null)
                .collect(Collectors.toMap(HCloudAs::getScalingGroupId, e -> e, (a, b) -> a));

        List<HCloudAs> toInsert = apiList.stream()
                .filter(e -> e.getScalingGroupId() != null && !dbMap.containsKey(e.getScalingGroupId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudAsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudAs> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudAs::getConfName, cloudConf.getName())
                    .in(HCloudAs::getScalingGroupId, toDeleteIds)
                    .set(HCloudAs::getDeleted, 1);
            hCloudAsMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncBms(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudBms> apiList = hCloudClient.listBms();
        List<HCloudBms> dbList = hCloudBmsMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudBms> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudBms::getId, e -> e, (a, b) -> a));
        Map<String, HCloudBms> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudBms::getId, e -> e, (a, b) -> a));

        List<HCloudBms> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudBmsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudBms> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudBms::getConfName, cloudConf.getName())
                    .in(HCloudBms::getId, toDeleteIds)
                    .set(HCloudBms::getDeleted, 1);
            hCloudBmsMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncWorkspace(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudWorkspace> apiList = hCloudClient.listWorkspace();
        List<HCloudWorkspace> dbList = hCloudWorkspaceMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudWorkspace> apiMap = apiList.stream()
                .filter(e -> e.getDesktopId() != null)
                .collect(Collectors.toMap(HCloudWorkspace::getDesktopId, e -> e, (a, b) -> a));
        Map<String, HCloudWorkspace> dbMap = dbList.stream()
                .filter(e -> e.getDesktopId() != null)
                .collect(Collectors.toMap(HCloudWorkspace::getDesktopId, e -> e, (a, b) -> a));

        List<HCloudWorkspace> toInsert = apiList.stream()
                .filter(e -> e.getDesktopId() != null && !dbMap.containsKey(e.getDesktopId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudWorkspaceMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudWorkspace> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudWorkspace::getConfName, cloudConf.getName())
                    .in(HCloudWorkspace::getDesktopId, toDeleteIds)
                    .set(HCloudWorkspace::getDeleted, 1);
            hCloudWorkspaceMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncDli(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudDli> apiList = hCloudClient.listDli();
        List<HCloudDli> dbList = hCloudDliMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudDli> apiMap = apiList.stream()
                .filter(e -> e.getQueueName() != null)
                .collect(Collectors.toMap(HCloudDli::getQueueName, e -> e, (a, b) -> a));
        Map<String, HCloudDli> dbMap = dbList.stream()
                .filter(e -> e.getQueueName() != null)
                .collect(Collectors.toMap(HCloudDli::getQueueName, e -> e, (a, b) -> a));

        List<HCloudDli> toInsert = apiList.stream()
                .filter(e -> e.getQueueName() != null && !dbMap.containsKey(e.getQueueName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudDliMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudDli> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudDli::getConfName, cloudConf.getName())
                    .in(HCloudDli::getQueueName, toDeleteIds)
                    .set(HCloudDli::getDeleted, 1);
            hCloudDliMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncDws(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudDws> apiList = hCloudClient.listDws();
        List<HCloudDws> dbList = hCloudDwsMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudDws> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudDws::getId, e -> e, (a, b) -> a));
        Map<String, HCloudDws> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudDws::getId, e -> e, (a, b) -> a));

        List<HCloudDws> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudDwsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudDws> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudDws::getConfName, cloudConf.getName())
                    .in(HCloudDws::getId, toDeleteIds)
                    .set(HCloudDws::getDeleted, 1);
            hCloudDwsMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncGaussDbNoSql(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudGaussDbNoSql> apiList = hCloudClient.listGaussDbNoSql();
        List<HCloudGaussDbNoSql> dbList = hCloudGaussDbNoSqlMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudGaussDbNoSql> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudGaussDbNoSql::getId, e -> e, (a, b) -> a));
        Map<String, HCloudGaussDbNoSql> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudGaussDbNoSql::getId, e -> e, (a, b) -> a));

        List<HCloudGaussDbNoSql> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudGaussDbNoSqlMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudGaussDbNoSql> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudGaussDbNoSql::getConfName, cloudConf.getName())
                    .in(HCloudGaussDbNoSql::getId, toDeleteIds)
                    .set(HCloudGaussDbNoSql::getDeleted, 1);
            hCloudGaussDbNoSqlMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncGaussDbOpenGauss(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudGaussDbOpenGauss> apiList = hCloudClient.listGaussDbOpenGauss();
        List<HCloudGaussDbOpenGauss> dbList = hCloudGaussDbOpenGaussMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudGaussDbOpenGauss> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudGaussDbOpenGauss::getId, e -> e, (a, b) -> a));
        Map<String, HCloudGaussDbOpenGauss> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudGaussDbOpenGauss::getId, e -> e, (a, b) -> a));

        List<HCloudGaussDbOpenGauss> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudGaussDbOpenGaussMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudGaussDbOpenGauss> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudGaussDbOpenGauss::getConfName, cloudConf.getName())
                    .in(HCloudGaussDbOpenGauss::getId, toDeleteIds)
                    .set(HCloudGaussDbOpenGauss::getDeleted, 1);
            hCloudGaussDbOpenGaussMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncDdm(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudDdm> apiList = hCloudClient.listDdm();
        List<HCloudDdm> dbList = hCloudDdmMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudDdm> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudDdm::getId, e -> e, (a, b) -> a));
        Map<String, HCloudDdm> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudDdm::getId, e -> e, (a, b) -> a));

        List<HCloudDdm> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudDdmMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudDdm> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudDdm::getConfName, cloudConf.getName())
                    .in(HCloudDdm::getId, toDeleteIds)
                    .set(HCloudDdm::getDeleted, 1);
            hCloudDdmMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncCse(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudCse> apiList = hCloudClient.listCse();
        List<HCloudCse> dbList = hCloudCseMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudCse> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudCse::getId, e -> e, (a, b) -> a));
        Map<String, HCloudCse> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudCse::getId, e -> e, (a, b) -> a));

        List<HCloudCse> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudCseMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudCse> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudCse::getConfName, cloudConf.getName())
                    .in(HCloudCse::getId, toDeleteIds)
                    .set(HCloudCse::getDeleted, 1);
            hCloudCseMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncServiceStage(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudServiceStage> apiList = hCloudClient.listServiceStage();
        List<HCloudServiceStage> dbList = hCloudServiceStageMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudServiceStage> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudServiceStage::getId, e -> e, (a, b) -> a));
        Map<String, HCloudServiceStage> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudServiceStage::getId, e -> e, (a, b) -> a));

        List<HCloudServiceStage> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudServiceStageMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudServiceStage> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudServiceStage::getConfName, cloudConf.getName())
                    .in(HCloudServiceStage::getId, toDeleteIds)
                    .set(HCloudServiceStage::getDeleted, 1);
            hCloudServiceStageMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncCbh(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudCbh> apiList = hCloudClient.listCbh();
        List<HCloudCbh> dbList = hCloudCbhMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudCbh> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(HCloudCbh::getInstanceId, e -> e, (a, b) -> a));
        Map<String, HCloudCbh> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(HCloudCbh::getInstanceId, e -> e, (a, b) -> a));

        List<HCloudCbh> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudCbhMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudCbh> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudCbh::getConfName, cloudConf.getName())
                    .in(HCloudCbh::getInstanceId, toDeleteIds)
                    .set(HCloudCbh::getDeleted, 1);
            hCloudCbhMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncDbss(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudDbss> apiList = hCloudClient.listDbss();
        List<HCloudDbss> dbList = hCloudDbssMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudDbss> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudDbss::getId, e -> e, (a, b) -> a));
        Map<String, HCloudDbss> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudDbss::getId, e -> e, (a, b) -> a));

        List<HCloudDbss> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudDbssMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudDbss> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudDbss::getConfName, cloudConf.getName())
                    .in(HCloudDbss::getId, toDeleteIds)
                    .set(HCloudDbss::getDeleted, 1);
            hCloudDbssMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncVod(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudVod> apiList = hCloudClient.listVod();
        List<HCloudVod> dbList = hCloudVodMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudVod> apiMap = apiList.stream()
                .filter(e -> e.getAssetId() != null)
                .collect(Collectors.toMap(HCloudVod::getAssetId, e -> e, (a, b) -> a));
        Map<String, HCloudVod> dbMap = dbList.stream()
                .filter(e -> e.getAssetId() != null)
                .collect(Collectors.toMap(HCloudVod::getAssetId, e -> e, (a, b) -> a));

        List<HCloudVod> toInsert = apiList.stream()
                .filter(e -> e.getAssetId() != null && !dbMap.containsKey(e.getAssetId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudVodMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudVod> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudVod::getConfName, cloudConf.getName())
                    .in(HCloudVod::getAssetId, toDeleteIds)
                    .set(HCloudVod::getDeleted, 1);
            hCloudVodMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncLive(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudLive> apiList = hCloudClient.listLive();
        List<HCloudLive> dbList = hCloudLiveMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudLive> apiMap = apiList.stream()
                .filter(e -> e.getStream() != null)
                .collect(Collectors.toMap(HCloudLive::getStream, e -> e, (a, b) -> a));
        Map<String, HCloudLive> dbMap = dbList.stream()
                .filter(e -> e.getStream() != null)
                .collect(Collectors.toMap(HCloudLive::getStream, e -> e, (a, b) -> a));

        List<HCloudLive> toInsert = apiList.stream()
                .filter(e -> e.getStream() != null && !dbMap.containsKey(e.getStream()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudLiveMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudLive> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudLive::getConfName, cloudConf.getName())
                    .in(HCloudLive::getStream, toDeleteIds)
                    .set(HCloudLive::getDeleted, 1);
            hCloudLiveMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncOms(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudOms> apiList = hCloudClient.listOms();
        List<HCloudOms> dbList = hCloudOmsMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudOms> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudOms::getId, e -> e, (a, b) -> a));
        Map<String, HCloudOms> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudOms::getId, e -> e, (a, b) -> a));

        List<HCloudOms> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudOmsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudOms> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudOms::getConfName, cloudConf.getName())
                    .in(HCloudOms::getId, toDeleteIds)
                    .set(HCloudOms::getDeleted, 1);
            hCloudOmsMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncSdrs(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudSdrs> apiList = hCloudClient.listSdrs();
        List<HCloudSdrs> dbList = hCloudSdrsMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudSdrs> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudSdrs::getId, e -> e, (a, b) -> a));
        Map<String, HCloudSdrs> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudSdrs::getId, e -> e, (a, b) -> a));

        List<HCloudSdrs> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudSdrsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudSdrs> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudSdrs::getConfName, cloudConf.getName())
                    .in(HCloudSdrs::getId, toDeleteIds)
                    .set(HCloudSdrs::getDeleted, 1);
            hCloudSdrsMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncSms(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudSms> apiList = hCloudClient.listSms();
        List<HCloudSms> dbList = hCloudSmsMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudSms> apiMap = apiList.stream()
                .filter(e -> e.getServerId() != null)
                .collect(Collectors.toMap(HCloudSms::getServerId, e -> e, (a, b) -> a));
        Map<String, HCloudSms> dbMap = dbList.stream()
                .filter(e -> e.getServerId() != null)
                .collect(Collectors.toMap(HCloudSms::getServerId, e -> e, (a, b) -> a));

        List<HCloudSms> toInsert = apiList.stream()
                .filter(e -> e.getServerId() != null && !dbMap.containsKey(e.getServerId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudSmsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudSms> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudSms::getConfName, cloudConf.getName())
                    .in(HCloudSms::getServerId, toDeleteIds)
                    .set(HCloudSms::getDeleted, 1);
            hCloudSmsMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncDsc(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudDsc> apiList = hCloudClient.listDsc();
        List<HCloudDsc> dbList = hCloudDscMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudDsc> apiMap = apiList.stream()
                .filter(e -> e.getBucketId() != null)
                .collect(Collectors.toMap(HCloudDsc::getBucketId, e -> e, (a, b) -> a));
        Map<String, HCloudDsc> dbMap = dbList.stream()
                .filter(e -> e.getBucketId() != null)
                .collect(Collectors.toMap(HCloudDsc::getBucketId, e -> e, (a, b) -> a));

        List<HCloudDsc> toInsert = apiList.stream()
                .filter(e -> e.getBucketId() != null && !dbMap.containsKey(e.getBucketId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudDscMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudDsc> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudDsc::getConfName, cloudConf.getName())
                    .in(HCloudDsc::getBucketId, toDeleteIds)
                    .set(HCloudDsc::getDeleted, 1);
            hCloudDscMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncRoma(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudRoma> apiList = hCloudClient.listRoma();
        List<HCloudRoma> dbList = hCloudRomaMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudRoma> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(HCloudRoma::getInstanceId, e -> e, (a, b) -> a));
        Map<String, HCloudRoma> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(HCloudRoma::getInstanceId, e -> e, (a, b) -> a));

        List<HCloudRoma> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudRomaMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudRoma> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudRoma::getConfName, cloudConf.getName())
                    .in(HCloudRoma::getInstanceId, toDeleteIds)
                    .set(HCloudRoma::getDeleted, 1);
            hCloudRomaMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncCph(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudCph> apiList = hCloudClient.listCph();
        List<HCloudCph> dbList = hCloudCphMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudCph> apiMap = apiList.stream()
                .filter(e -> e.getPhoneId() != null)
                .collect(Collectors.toMap(HCloudCph::getPhoneId, e -> e, (a, b) -> a));
        Map<String, HCloudCph> dbMap = dbList.stream()
                .filter(e -> e.getPhoneId() != null)
                .collect(Collectors.toMap(HCloudCph::getPhoneId, e -> e, (a, b) -> a));

        List<HCloudCph> toInsert = apiList.stream()
                .filter(e -> e.getPhoneId() != null && !dbMap.containsKey(e.getPhoneId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudCphMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudCph> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudCph::getConfName, cloudConf.getName())
                    .in(HCloudCph::getPhoneId, toDeleteIds)
                    .set(HCloudCph::getDeleted, 1);
            hCloudCphMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncEr(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudEr> apiList = hCloudClient.listEr();
        List<HCloudEr> dbList = hCloudErMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudEr> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudEr::getId, e -> e, (a, b) -> a));
        Map<String, HCloudEr> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudEr::getId, e -> e, (a, b) -> a));

        List<HCloudEr> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudErMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudEr> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudEr::getConfName, cloudConf.getName())
                    .in(HCloudEr::getId, toDeleteIds)
                    .set(HCloudEr::getDeleted, 1);
            hCloudErMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncVpcep(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudVpcep> apiList = hCloudClient.listVpcep();
        List<HCloudVpcep> dbList = hCloudVpcepMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudVpcep> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudVpcep::getId, e -> e, (a, b) -> a));
        Map<String, HCloudVpcep> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudVpcep::getId, e -> e, (a, b) -> a));

        List<HCloudVpcep> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudVpcepMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudVpcep> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudVpcep::getConfName, cloudConf.getName())
                    .in(HCloudVpcep::getId, toDeleteIds)
                    .set(HCloudVpcep::getDeleted, 1);
            hCloudVpcepMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncIef(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudIef> apiList = hCloudClient.listIef();
        List<HCloudIef> dbList = hCloudIefMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudIef> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudIef::getId, e -> e, (a, b) -> a));
        Map<String, HCloudIef> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudIef::getId, e -> e, (a, b) -> a));

        List<HCloudIef> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudIefMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudIef> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudIef::getConfName, cloudConf.getName())
                    .in(HCloudIef::getId, toDeleteIds)
                    .set(HCloudIef::getDeleted, 1);
            hCloudIefMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncIotDa(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudIotDa> apiList = hCloudClient.listIotDa();
        List<HCloudIotDa> dbList = hCloudIotDaMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudIotDa> apiMap = apiList.stream()
                .filter(e -> e.getDeviceId() != null)
                .collect(Collectors.toMap(HCloudIotDa::getDeviceId, e -> e, (a, b) -> a));
        Map<String, HCloudIotDa> dbMap = dbList.stream()
                .filter(e -> e.getDeviceId() != null)
                .collect(Collectors.toMap(HCloudIotDa::getDeviceId, e -> e, (a, b) -> a));

        List<HCloudIotDa> toInsert = apiList.stream()
                .filter(e -> e.getDeviceId() != null && !dbMap.containsKey(e.getDeviceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudIotDaMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudIotDa> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudIotDa::getConfName, cloudConf.getName())
                    .in(HCloudIotDa::getDeviceId, toDeleteIds)
                    .set(HCloudIotDa::getDeleted, 1);
            hCloudIotDaMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncDeh(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudDeh> apiList = hCloudClient.listDeh();
        List<HCloudDeh> dbList = hCloudDehMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudDeh> apiMap = apiList.stream()
                .filter(e -> e.getDedicatedHostId() != null)
                .collect(Collectors.toMap(HCloudDeh::getDedicatedHostId, e -> e, (a, b) -> a));
        Map<String, HCloudDeh> dbMap = dbList.stream()
                .filter(e -> e.getDedicatedHostId() != null)
                .collect(Collectors.toMap(HCloudDeh::getDedicatedHostId, e -> e, (a, b) -> a));

        List<HCloudDeh> toInsert = apiList.stream()
                .filter(e -> e.getDedicatedHostId() != null && !dbMap.containsKey(e.getDedicatedHostId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudDehMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudDeh> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudDeh::getConfName, cloudConf.getName())
                    .in(HCloudDeh::getDedicatedHostId, toDeleteIds)
                    .set(HCloudDeh::getDeleted, 1);
            hCloudDehMapper.update(null, uw);
        }
        return insertCount;
    }

    private int syncBcs(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudBcs> apiList = hCloudClient.listBcs();
        List<HCloudBcs> dbList = hCloudBcsMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudBcs> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudBcs::getId, e -> e, (a, b) -> a));
        Map<String, HCloudBcs> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudBcs::getId, e -> e, (a, b) -> a));

        List<HCloudBcs> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudBcsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudBcs> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudBcs::getConfName, cloudConf.getName())
                    .in(HCloudBcs::getId, toDeleteIds)
                    .set(HCloudBcs::getDeleted, 1);
            hCloudBcsMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== DC (云专线) ====================

    private int syncDc(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudDc> apiList = hCloudClient.listDc();
        List<HCloudDc> dbList = hCloudDcMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudDc> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudDc::getId, e -> e, (a, b) -> a));
        Map<String, HCloudDc> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudDc::getId, e -> e, (a, b) -> a));

        List<HCloudDc> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudDcMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudDc> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudDc::getConfName, cloudConf.getName())
                    .in(HCloudDc::getId, toDeleteIds)
                    .set(HCloudDc::getDeleted, 1);
            hCloudDcMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== GA (全球加速) ====================

    private int syncGa(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudGa> apiList = hCloudClient.listGa();
        List<HCloudGa> dbList = hCloudGaMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudGa> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudGa::getId, e -> e, (a, b) -> a));
        Map<String, HCloudGa> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudGa::getId, e -> e, (a, b) -> a));

        List<HCloudGa> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudGaMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudGa> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudGa::getConfName, cloudConf.getName())
                    .in(HCloudGa::getId, toDeleteIds)
                    .set(HCloudGa::getDeleted, 1);
            hCloudGaMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== EG (事件网格) ====================

    private int syncEg(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudEg> apiList = hCloudClient.listEg();
        List<HCloudEg> dbList = hCloudEgMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudEg> apiMap = apiList.stream()
                .filter(e -> e.getChannelId() != null)
                .collect(Collectors.toMap(HCloudEg::getChannelId, e -> e, (a, b) -> a));
        Map<String, HCloudEg> dbMap = dbList.stream()
                .filter(e -> e.getChannelId() != null)
                .collect(Collectors.toMap(HCloudEg::getChannelId, e -> e, (a, b) -> a));

        List<HCloudEg> toInsert = apiList.stream()
                .filter(e -> e.getChannelId() != null && !dbMap.containsKey(e.getChannelId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudEgMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudEg> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudEg::getConfName, cloudConf.getName())
                    .in(HCloudEg::getChannelId, toDeleteIds)
                    .set(HCloudEg::getDeleted, 1);
            hCloudEgMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== APM (应用性能管理) ====================

    private int syncApm(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudApm> apiList = hCloudClient.listApm();
        List<HCloudApm> dbList = hCloudApmMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudApm> apiMap = apiList.stream()
                .filter(e -> e.getAppId() != null)
                .collect(Collectors.toMap(HCloudApm::getAppId, e -> e, (a, b) -> a));
        Map<String, HCloudApm> dbMap = dbList.stream()
                .filter(e -> e.getAppId() != null)
                .collect(Collectors.toMap(HCloudApm::getAppId, e -> e, (a, b) -> a));

        List<HCloudApm> toInsert = apiList.stream()
                .filter(e -> e.getAppId() != null && !dbMap.containsKey(e.getAppId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudApmMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudApm> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudApm::getConfName, cloudConf.getName())
                    .in(HCloudApm::getAppId, toDeleteIds)
                    .set(HCloudApm::getDeleted, 1);
            hCloudApmMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CloudTable (表格存储) ====================

    private int syncCloudTable(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudCloudTable> apiList = hCloudClient.listCloudTable();
        List<HCloudCloudTable> dbList = hCloudCloudTableMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudCloudTable> apiMap = apiList.stream()
                .filter(e -> e.getClusterId() != null)
                .collect(Collectors.toMap(HCloudCloudTable::getClusterId, e -> e, (a, b) -> a));
        Map<String, HCloudCloudTable> dbMap = dbList.stream()
                .filter(e -> e.getClusterId() != null)
                .collect(Collectors.toMap(HCloudCloudTable::getClusterId, e -> e, (a, b) -> a));

        List<HCloudCloudTable> toInsert = apiList.stream()
                .filter(e -> e.getClusterId() != null && !dbMap.containsKey(e.getClusterId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudCloudTableMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudCloudTable> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudCloudTable::getConfName, cloudConf.getName())
                    .in(HCloudCloudTable::getClusterId, toDeleteIds)
                    .set(HCloudCloudTable::getDeleted, 1);
            hCloudCloudTableMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== DataArtsStudio (数据治理中心) ====================

    private int syncDataArtsStudio(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudDataArtsStudio> apiList = hCloudClient.listDataArtsStudio();
        List<HCloudDataArtsStudio> dbList = hCloudDataArtsStudioMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudDataArtsStudio> apiMap = apiList.stream()
                .filter(e -> e.getWorkspaceId() != null)
                .collect(Collectors.toMap(HCloudDataArtsStudio::getWorkspaceId, e -> e, (a, b) -> a));
        Map<String, HCloudDataArtsStudio> dbMap = dbList.stream()
                .filter(e -> e.getWorkspaceId() != null)
                .collect(Collectors.toMap(HCloudDataArtsStudio::getWorkspaceId, e -> e, (a, b) -> a));

        List<HCloudDataArtsStudio> toInsert = apiList.stream()
                .filter(e -> e.getWorkspaceId() != null && !dbMap.containsKey(e.getWorkspaceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudDataArtsStudioMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudDataArtsStudio> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudDataArtsStudio::getConfName, cloudConf.getName())
                    .in(HCloudDataArtsStudio::getWorkspaceId, toDeleteIds)
                    .set(HCloudDataArtsStudio::getDeleted, 1);
            hCloudDataArtsStudioMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== DIS (数据接入) ====================

    private int syncDis(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudDis> apiList = hCloudClient.listDis();
        List<HCloudDis> dbList = hCloudDisMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudDis> apiMap = apiList.stream()
                .filter(e -> e.getStreamName() != null)
                .collect(Collectors.toMap(HCloudDis::getStreamName, e -> e, (a, b) -> a));
        Map<String, HCloudDis> dbMap = dbList.stream()
                .filter(e -> e.getStreamName() != null)
                .collect(Collectors.toMap(HCloudDis::getStreamName, e -> e, (a, b) -> a));

        List<HCloudDis> toInsert = apiList.stream()
                .filter(e -> e.getStreamName() != null && !dbMap.containsKey(e.getStreamName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudDisMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudDis> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudDis::getConfName, cloudConf.getName())
                    .in(HCloudDis::getStreamName, toDeleteIds)
                    .set(HCloudDis::getDeleted, 1);
            hCloudDisMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== MAS (多活高可用) ====================

    private int syncMas(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudMas> apiList = hCloudClient.listMas();
        List<HCloudMas> dbList = hCloudMasMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudMas> apiMap = apiList.stream()
                .filter(e -> e.getNamespaceId() != null)
                .collect(Collectors.toMap(HCloudMas::getNamespaceId, e -> e, (a, b) -> a));
        Map<String, HCloudMas> dbMap = dbList.stream()
                .filter(e -> e.getNamespaceId() != null)
                .collect(Collectors.toMap(HCloudMas::getNamespaceId, e -> e, (a, b) -> a));

        List<HCloudMas> toInsert = apiList.stream()
                .filter(e -> e.getNamespaceId() != null && !dbMap.containsKey(e.getNamespaceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudMasMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudMas> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudMas::getConfName, cloudConf.getName())
                    .in(HCloudMas::getNamespaceId, toDeleteIds)
                    .set(HCloudMas::getDeleted, 1);
            hCloudMasMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== MPC (媒体处理) ====================

    private int syncMpc(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudMpc> apiList = hCloudClient.listMpc();
        List<HCloudMpc> dbList = hCloudMpcMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudMpc> apiMap = apiList.stream()
                .filter(e -> e.getJobId() != null)
                .collect(Collectors.toMap(HCloudMpc::getJobId, e -> e, (a, b) -> a));
        Map<String, HCloudMpc> dbMap = dbList.stream()
                .filter(e -> e.getJobId() != null)
                .collect(Collectors.toMap(HCloudMpc::getJobId, e -> e, (a, b) -> a));

        List<HCloudMpc> toInsert = apiList.stream()
                .filter(e -> e.getJobId() != null && !dbMap.containsKey(e.getJobId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudMpcMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudMpc> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudMpc::getConfName, cloudConf.getName())
                    .in(HCloudMpc::getJobId, toDeleteIds)
                    .set(HCloudMpc::getDeleted, 1);
            hCloudMpcMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CloudDc (云化数据中心) ====================

    private int syncCloudDc(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudCloudDc> apiList = hCloudClient.listCloudDc();
        List<HCloudCloudDc> dbList = hCloudCloudDcMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudCloudDc> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(HCloudCloudDc::getInstanceId, e -> e, (a, b) -> a));
        Map<String, HCloudCloudDc> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(HCloudCloudDc::getInstanceId, e -> e, (a, b) -> a));

        List<HCloudCloudDc> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudCloudDcMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudCloudDc> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudCloudDc::getConfName, cloudConf.getName())
                    .in(HCloudCloudDc::getInstanceId, toDeleteIds)
                    .set(HCloudCloudDc::getDeleted, 1);
            hCloudCloudDcMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== KVS (键值存储) ====================

    private int syncKvs(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudKvs> apiList = hCloudClient.listKvs();
        List<HCloudKvs> dbList = hCloudKvsMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudKvs> apiMap = apiList.stream()
                .filter(e -> e.getTableName() != null)
                .collect(Collectors.toMap(HCloudKvs::getTableName, e -> e, (a, b) -> a));
        Map<String, HCloudKvs> dbMap = dbList.stream()
                .filter(e -> e.getTableName() != null)
                .collect(Collectors.toMap(HCloudKvs::getTableName, e -> e, (a, b) -> a));

        List<HCloudKvs> toInsert = apiList.stream()
                .filter(e -> e.getTableName() != null && !dbMap.containsKey(e.getTableName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudKvsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudKvs> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudKvs::getConfName, cloudConf.getName())
                    .in(HCloudKvs::getTableName, toDeleteIds)
                    .set(HCloudKvs::getDeleted, 1);
            hCloudKvsMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== EDS (交换数据空间) ====================

    private int syncEds(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudEds> apiList = hCloudClient.listEds();
        List<HCloudEds> dbList = hCloudEdsMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudEds> apiMap = apiList.stream()
                .filter(e -> e.getOfferId() != null)
                .collect(Collectors.toMap(HCloudEds::getOfferId, e -> e, (a, b) -> a));
        Map<String, HCloudEds> dbMap = dbList.stream()
                .filter(e -> e.getOfferId() != null)
                .collect(Collectors.toMap(HCloudEds::getOfferId, e -> e, (a, b) -> a));

        List<HCloudEds> toInsert = apiList.stream()
                .filter(e -> e.getOfferId() != null && !dbMap.containsKey(e.getOfferId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudEdsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudEds> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudEds::getConfName, cloudConf.getName())
                    .in(HCloudEds::getOfferId, toDeleteIds)
                    .set(HCloudEds::getDeleted, 1);
            hCloudEdsMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== TICS (可信智能计算) ====================

    private int syncTics(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudTics> apiList = hCloudClient.listTics();
        List<HCloudTics> dbList = hCloudTicsMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudTics> apiMap = apiList.stream()
                .filter(e -> e.getLeagueId() != null)
                .collect(Collectors.toMap(HCloudTics::getLeagueId, e -> e, (a, b) -> a));
        Map<String, HCloudTics> dbMap = dbList.stream()
                .filter(e -> e.getLeagueId() != null)
                .collect(Collectors.toMap(HCloudTics::getLeagueId, e -> e, (a, b) -> a));

        List<HCloudTics> toInsert = apiList.stream()
                .filter(e -> e.getLeagueId() != null && !dbMap.containsKey(e.getLeagueId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudTicsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudTics> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudTics::getConfName, cloudConf.getName())
                    .in(HCloudTics::getLeagueId, toDeleteIds)
                    .set(HCloudTics::getDeleted, 1);
            hCloudTicsMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== Organizations (组织) ====================

    private int syncOrganizations(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudOrganizations> apiList = hCloudClient.listOrganizations();
        List<HCloudOrganizations> dbList = hCloudOrganizationsMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudOrganizations> apiMap = apiList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudOrganizations::getId, e -> e, (a, b) -> a));
        Map<String, HCloudOrganizations> dbMap = dbList.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(HCloudOrganizations::getId, e -> e, (a, b) -> a));

        List<HCloudOrganizations> toInsert = apiList.stream()
                .filter(e -> e.getId() != null && !dbMap.containsKey(e.getId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudOrganizationsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudOrganizations> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudOrganizations::getConfName, cloudConf.getName())
                    .in(HCloudOrganizations::getId, toDeleteIds)
                    .set(HCloudOrganizations::getDeleted, 1);
            hCloudOrganizationsMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== RAM (资源访问管理) ====================

    private int syncRam(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudRam> apiList = hCloudClient.listRam();
        List<HCloudRam> dbList = hCloudRamMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudRam> apiMap = apiList.stream()
                .filter(e -> e.getResourceShareId() != null)
                .collect(Collectors.toMap(HCloudRam::getResourceShareId, e -> e, (a, b) -> a));
        Map<String, HCloudRam> dbMap = dbList.stream()
                .filter(e -> e.getResourceShareId() != null)
                .collect(Collectors.toMap(HCloudRam::getResourceShareId, e -> e, (a, b) -> a));

        List<HCloudRam> toInsert = apiList.stream()
                .filter(e -> e.getResourceShareId() != null && !dbMap.containsKey(e.getResourceShareId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudRamMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudRam> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudRam::getConfName, cloudConf.getName())
                    .in(HCloudRam::getResourceShareId, toDeleteIds)
                    .set(HCloudRam::getDeleted, 1);
            hCloudRamMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== COC (云运维中心) ====================

    private int syncCoc(HCloudClient hCloudClient, CloudConf cloudConf) {
        List<HCloudCoc> apiList = hCloudClient.listCoc();
        List<HCloudCoc> dbList = hCloudCocMapper.selectByConfName(cloudConf.getName());

        Map<String, HCloudCoc> apiMap = apiList.stream()
                .filter(e -> e.getIncidentId() != null)
                .collect(Collectors.toMap(HCloudCoc::getIncidentId, e -> e, (a, b) -> a));
        Map<String, HCloudCoc> dbMap = dbList.stream()
                .filter(e -> e.getIncidentId() != null)
                .collect(Collectors.toMap(HCloudCoc::getIncidentId, e -> e, (a, b) -> a));

        List<HCloudCoc> toInsert = apiList.stream()
                .filter(e -> e.getIncidentId() != null && !dbMap.containsKey(e.getIncidentId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudCocMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudCoc> uw = new LambdaUpdateWrapper<>();
            uw.eq(HCloudCoc::getConfName, cloudConf.getName())
                    .in(HCloudCoc::getIncidentId, toDeleteIds)
                    .set(HCloudCoc::getDeleted, 1);
            hCloudCocMapper.update(null, uw);
        }
        return insertCount;
    }
}
