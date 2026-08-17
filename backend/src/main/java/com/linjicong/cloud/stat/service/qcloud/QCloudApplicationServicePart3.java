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
package com.linjicong.cloud.stat.service.qcloud;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.linjicong.cloud.stat.client.QCloudClient;
import com.linjicong.cloud.stat.dao.entity.CloudConf;
import com.linjicong.cloud.stat.dao.entity.qcloud.*;
import com.linjicong.cloud.stat.dao.mapper.qcloud.*;
import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * 腾讯云Application Part3资源同步服务
 * 包含以下资源类型的同步方法: DocProcess, Docs, Domain, DomainReg, EventBus, GSE, GameAntiACE, GameDB, GameServer, GameVoice...
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Service
public class QCloudApplicationServicePart3 {

    @Resource
    private QCloudDocProcessMapper qCloudDocProcessMapper;
    @Resource
    private QCloudDocsMapper qCloudDocsMapper;
    @Resource
    private QCloudDomainMapper qCloudDomainMapper;
    @Resource
    private QCloudDomainRegMapper qCloudDomainRegMapper;
    @Resource
    private QCloudEventBusMapper qCloudEventBusMapper;
    @Resource
    private QCloudGSEMapper qCloudGSEMapper;
    @Resource
    private QCloudGameAntiACEMapper qCloudGameAntiACEMapper;
    @Resource
    private QCloudGameDBMapper qCloudGameDBMapper;
    @Resource
    private QCloudGameServerMapper qCloudGameServerMapper;
    @Resource
    private QCloudGameVoiceMapper qCloudGameVoiceMapper;
    @Resource
    private QCloudHealthDashMapper qCloudHealthDashMapper;
    @Resource
    private QCloudHealthOmicsMapper qCloudHealthOmicsMapper;

    // ==================== DOCPROCESS ====================

    public int syncDocProcess(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudDocProcess> apiList = qCloudClient.listDocProcess();
        List<QCloudDocProcess> dbList = qCloudDocProcessMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudDocProcess> apiMap = apiList.stream()
                .filter(e -> e.getBucket() != null)
                .collect(Collectors.toMap(QCloudDocProcess::getBucket, e -> e, (a, b) -> a));
        Map<String, QCloudDocProcess> dbMap = dbList.stream()
                .filter(e -> e.getBucket() != null)
                .collect(Collectors.toMap(QCloudDocProcess::getBucket, e -> e, (a, b) -> a));

        List<QCloudDocProcess> toInsert = apiList.stream()
                .filter(e -> e.getBucket() != null && !dbMap.containsKey(e.getBucket()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudDocProcessMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudDocProcess> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudDocProcess::getConfName, cloudConf.getName())
                    .in(QCloudDocProcess::getBucket, toDeleteIds)
                    .set(QCloudDocProcess::getDeleted, 1);
            qCloudDocProcessMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== DOCS ====================

    public int syncDocs(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudDocs> apiList = qCloudClient.listDocs();
        List<QCloudDocs> dbList = qCloudDocsMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudDocs> apiMap = apiList.stream()
                .filter(e -> e.getDocId() != null)
                .collect(Collectors.toMap(QCloudDocs::getDocId, e -> e, (a, b) -> a));
        Map<String, QCloudDocs> dbMap = dbList.stream()
                .filter(e -> e.getDocId() != null)
                .collect(Collectors.toMap(QCloudDocs::getDocId, e -> e, (a, b) -> a));

        List<QCloudDocs> toInsert = apiList.stream()
                .filter(e -> e.getDocId() != null && !dbMap.containsKey(e.getDocId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudDocsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudDocs> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudDocs::getConfName, cloudConf.getName())
                    .in(QCloudDocs::getDocId, toDeleteIds)
                    .set(QCloudDocs::getDeleted, 1);
            qCloudDocsMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== DOMAIN ====================

    public int syncDomain(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudDomain> apiList = qCloudClient.listDomain();
        List<QCloudDomain> dbList = qCloudDomainMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudDomain> apiMap = apiList.stream()
                .filter(e -> e.getDomainId() != null)
                .collect(Collectors.toMap(QCloudDomain::getDomainId, e -> e, (a, b) -> a));
        Map<String, QCloudDomain> dbMap = dbList.stream()
                .filter(e -> e.getDomainId() != null)
                .collect(Collectors.toMap(QCloudDomain::getDomainId, e -> e, (a, b) -> a));

        List<QCloudDomain> toInsert = apiList.stream()
                .filter(e -> e.getDomainId() != null && !dbMap.containsKey(e.getDomainId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudDomainMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudDomain> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudDomain::getConfName, cloudConf.getName())
                    .in(QCloudDomain::getDomainId, toDeleteIds)
                    .set(QCloudDomain::getDeleted, 1);
            qCloudDomainMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== DOMAINREG ====================

    public int syncDomainReg(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudDomainReg> apiList = qCloudClient.listDomainReg();
        List<QCloudDomainReg> dbList = qCloudDomainRegMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudDomainReg> apiMap = apiList.stream()
                .filter(e -> e.getDomainName() != null)
                .collect(Collectors.toMap(QCloudDomainReg::getDomainName, e -> e, (a, b) -> a));
        Map<String, QCloudDomainReg> dbMap = dbList.stream()
                .filter(e -> e.getDomainName() != null)
                .collect(Collectors.toMap(QCloudDomainReg::getDomainName, e -> e, (a, b) -> a));

        List<QCloudDomainReg> toInsert = apiList.stream()
                .filter(e -> e.getDomainName() != null && !dbMap.containsKey(e.getDomainName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudDomainRegMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudDomainReg> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudDomainReg::getConfName, cloudConf.getName())
                    .in(QCloudDomainReg::getDomainName, toDeleteIds)
                    .set(QCloudDomainReg::getDeleted, 1);
            qCloudDomainRegMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== EVENTBUS ====================

    public int syncEventBus(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudEventBus> apiList = qCloudClient.listEventBus();
        List<QCloudEventBus> dbList = qCloudEventBusMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudEventBus> apiMap = apiList.stream()
                .filter(e -> e.getEventBusId() != null)
                .collect(Collectors.toMap(QCloudEventBus::getEventBusId, e -> e, (a, b) -> a));
        Map<String, QCloudEventBus> dbMap = dbList.stream()
                .filter(e -> e.getEventBusId() != null)
                .collect(Collectors.toMap(QCloudEventBus::getEventBusId, e -> e, (a, b) -> a));

        List<QCloudEventBus> toInsert = apiList.stream()
                .filter(e -> e.getEventBusId() != null && !dbMap.containsKey(e.getEventBusId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudEventBusMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudEventBus> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudEventBus::getConfName, cloudConf.getName())
                    .in(QCloudEventBus::getEventBusId, toDeleteIds)
                    .set(QCloudEventBus::getDeleted, 1);
            qCloudEventBusMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== GSE ====================

    public int syncGSE(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudGSE> apiList = qCloudClient.listGSE();
        List<QCloudGSE> dbList = qCloudGSEMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudGSE> apiMap = apiList.stream()
                .filter(e -> e.getFleetId() != null)
                .collect(Collectors.toMap(QCloudGSE::getFleetId, e -> e, (a, b) -> a));
        Map<String, QCloudGSE> dbMap = dbList.stream()
                .filter(e -> e.getFleetId() != null)
                .collect(Collectors.toMap(QCloudGSE::getFleetId, e -> e, (a, b) -> a));

        List<QCloudGSE> toInsert = apiList.stream()
                .filter(e -> e.getFleetId() != null && !dbMap.containsKey(e.getFleetId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudGSEMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudGSE> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudGSE::getConfName, cloudConf.getName())
                    .in(QCloudGSE::getFleetId, toDeleteIds)
                    .set(QCloudGSE::getDeleted, 1);
            qCloudGSEMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== GAMEANTIACE ====================

    public int syncGameAntiACE(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudGameAntiACE> apiList = qCloudClient.listGameAntiACE();
        List<QCloudGameAntiACE> dbList = qCloudGameAntiACEMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudGameAntiACE> apiMap = apiList.stream()
                .filter(e -> e.getGameId() != null)
                .collect(Collectors.toMap(QCloudGameAntiACE::getGameId, e -> e, (a, b) -> a));
        Map<String, QCloudGameAntiACE> dbMap = dbList.stream()
                .filter(e -> e.getGameId() != null)
                .collect(Collectors.toMap(QCloudGameAntiACE::getGameId, e -> e, (a, b) -> a));

        List<QCloudGameAntiACE> toInsert = apiList.stream()
                .filter(e -> e.getGameId() != null && !dbMap.containsKey(e.getGameId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudGameAntiACEMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudGameAntiACE> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudGameAntiACE::getConfName, cloudConf.getName())
                    .in(QCloudGameAntiACE::getGameId, toDeleteIds)
                    .set(QCloudGameAntiACE::getDeleted, 1);
            qCloudGameAntiACEMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== GAMEDB ====================

    public int syncGameDB(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudGameDB> apiList = qCloudClient.listGameDB();
        List<QCloudGameDB> dbList = qCloudGameDBMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudGameDB> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudGameDB::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudGameDB> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudGameDB::getInstanceId, e -> e, (a, b) -> a));

        List<QCloudGameDB> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudGameDBMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudGameDB> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudGameDB::getConfName, cloudConf.getName())
                    .in(QCloudGameDB::getInstanceId, toDeleteIds)
                    .set(QCloudGameDB::getDeleted, 1);
            qCloudGameDBMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== GAMESERVER ====================

    public int syncGameServer(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudGameServer> apiList = qCloudClient.listGameServer();
        List<QCloudGameServer> dbList = qCloudGameServerMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudGameServer> apiMap = apiList.stream()
                .filter(e -> e.getFleetId() != null)
                .collect(Collectors.toMap(QCloudGameServer::getFleetId, e -> e, (a, b) -> a));
        Map<String, QCloudGameServer> dbMap = dbList.stream()
                .filter(e -> e.getFleetId() != null)
                .collect(Collectors.toMap(QCloudGameServer::getFleetId, e -> e, (a, b) -> a));

        List<QCloudGameServer> toInsert = apiList.stream()
                .filter(e -> e.getFleetId() != null && !dbMap.containsKey(e.getFleetId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudGameServerMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudGameServer> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudGameServer::getConfName, cloudConf.getName())
                    .in(QCloudGameServer::getFleetId, toDeleteIds)
                    .set(QCloudGameServer::getDeleted, 1);
            qCloudGameServerMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== GAMEVOICE ====================

    public int syncGameVoice(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudGameVoice> apiList = qCloudClient.listGameVoice();
        List<QCloudGameVoice> dbList = qCloudGameVoiceMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudGameVoice> apiMap = apiList.stream()
                .filter(e -> e.getAppId() != null)
                .collect(Collectors.toMap(QCloudGameVoice::getAppId, e -> e, (a, b) -> a));
        Map<String, QCloudGameVoice> dbMap = dbList.stream()
                .filter(e -> e.getAppId() != null)
                .collect(Collectors.toMap(QCloudGameVoice::getAppId, e -> e, (a, b) -> a));

        List<QCloudGameVoice> toInsert = apiList.stream()
                .filter(e -> e.getAppId() != null && !dbMap.containsKey(e.getAppId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudGameVoiceMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudGameVoice> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudGameVoice::getConfName, cloudConf.getName())
                    .in(QCloudGameVoice::getAppId, toDeleteIds)
                    .set(QCloudGameVoice::getDeleted, 1);
            qCloudGameVoiceMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== HEALTHDASH ====================

    public int syncHealthDash(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudHealthDash> apiList = qCloudClient.listHealthDash();
        List<QCloudHealthDash> dbList = qCloudHealthDashMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudHealthDash> apiMap = apiList.stream()
                .filter(e -> e.getDashboardId() != null)
                .collect(Collectors.toMap(QCloudHealthDash::getDashboardId, e -> e, (a, b) -> a));
        Map<String, QCloudHealthDash> dbMap = dbList.stream()
                .filter(e -> e.getDashboardId() != null)
                .collect(Collectors.toMap(QCloudHealthDash::getDashboardId, e -> e, (a, b) -> a));

        List<QCloudHealthDash> toInsert = apiList.stream()
                .filter(e -> e.getDashboardId() != null && !dbMap.containsKey(e.getDashboardId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudHealthDashMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudHealthDash> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudHealthDash::getConfName, cloudConf.getName())
                    .in(QCloudHealthDash::getDashboardId, toDeleteIds)
                    .set(QCloudHealthDash::getDeleted, 1);
            qCloudHealthDashMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== HEALTHOMICS ====================

    public int syncHealthOmics(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudHealthOmics> apiList = qCloudClient.listHealthOmics();
        List<QCloudHealthOmics> dbList = qCloudHealthOmicsMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudHealthOmics> apiMap = apiList.stream()
                .filter(e -> e.getDatasetId() != null)
                .collect(Collectors.toMap(QCloudHealthOmics::getDatasetId, e -> e, (a, b) -> a));
        Map<String, QCloudHealthOmics> dbMap = dbList.stream()
                .filter(e -> e.getDatasetId() != null)
                .collect(Collectors.toMap(QCloudHealthOmics::getDatasetId, e -> e, (a, b) -> a));

        List<QCloudHealthOmics> toInsert = apiList.stream()
                .filter(e -> e.getDatasetId() != null && !dbMap.containsKey(e.getDatasetId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudHealthOmicsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudHealthOmics> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudHealthOmics::getConfName, cloudConf.getName())
                    .in(QCloudHealthOmics::getDatasetId, toDeleteIds)
                    .set(QCloudHealthOmics::getDeleted, 1);
            qCloudHealthOmicsMapper.update(null, uw);
        }
        return insertCount;
    }
}
