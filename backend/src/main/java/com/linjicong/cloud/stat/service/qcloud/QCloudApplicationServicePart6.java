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
 * 腾讯云Application Part6资源同步服务
 * 包含以下资源类型的同步方法: TCR, TSE, TcrEnt, TencentConnect, TokenHub, TourismBigdata, User, VoiceMsg, WeData, WeLink...
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Service
public class QCloudApplicationServicePart6 {

    @Resource
    private QCloudTCRMapper qCloudTCRMapper;
    @Resource
    private QCloudTSEMapper qCloudTSEMapper;
    @Resource
    private QCloudTcrEntMapper qCloudTcrEntMapper;
    @Resource
    private QCloudTencentConnectMapper qCloudTencentConnectMapper;
    @Resource
    private QCloudTokenHubMapper qCloudTokenHubMapper;
    @Resource
    private QCloudTourismBigdataMapper qCloudTourismBigdataMapper;
    @Resource
    private QCloudUserMapper qCloudUserMapper;
    @Resource
    private QCloudVoiceMsgMapper qCloudVoiceMsgMapper;
    @Resource
    private QCloudWeDataMapper qCloudWeDataMapper;
    @Resource
    private QCloudWeLinkMapper qCloudWeLinkMapper;
    @Resource
    private QCloudWebSearchMapper qCloudWebSearchMapper;
    @Resource
    private QCloudWedaMapper qCloudWedaMapper;

    // ==================== TCR ====================

    public int syncTCR(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudTCR> apiList = qCloudClient.listTCR();
        List<QCloudTCR> dbList = qCloudTCRMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudTCR> apiMap = apiList.stream()
                .filter(e -> e.getRegistryId() != null)
                .collect(Collectors.toMap(QCloudTCR::getRegistryId, e -> e, (a, b) -> a));
        Map<String, QCloudTCR> dbMap = dbList.stream()
                .filter(e -> e.getRegistryId() != null)
                .collect(Collectors.toMap(QCloudTCR::getRegistryId, e -> e, (a, b) -> a));

        List<QCloudTCR> toInsert = apiList.stream()
                .filter(e -> e.getRegistryId() != null && !dbMap.containsKey(e.getRegistryId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudTCRMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudTCR> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudTCR::getConfName, cloudConf.getName())
                    .in(QCloudTCR::getRegistryId, toDeleteIds)
                    .set(QCloudTCR::getDeleted, 1);
            qCloudTCRMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== TSE ====================

    public int syncTSE(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudTSE> apiList = qCloudClient.listTSE();
        List<QCloudTSE> dbList = qCloudTSEMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudTSE> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudTSE::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudTSE> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudTSE::getInstanceId, e -> e, (a, b) -> a));

        List<QCloudTSE> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudTSEMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudTSE> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudTSE::getConfName, cloudConf.getName())
                    .in(QCloudTSE::getInstanceId, toDeleteIds)
                    .set(QCloudTSE::getDeleted, 1);
            qCloudTSEMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== TCRENT ====================

    public int syncTcrEnt(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudTcrEnt> apiList = qCloudClient.listTcrEnt();
        List<QCloudTcrEnt> dbList = qCloudTcrEntMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudTcrEnt> apiMap = apiList.stream()
                .filter(e -> e.getRegistryId() != null)
                .collect(Collectors.toMap(QCloudTcrEnt::getRegistryId, e -> e, (a, b) -> a));
        Map<String, QCloudTcrEnt> dbMap = dbList.stream()
                .filter(e -> e.getRegistryId() != null)
                .collect(Collectors.toMap(QCloudTcrEnt::getRegistryId, e -> e, (a, b) -> a));

        List<QCloudTcrEnt> toInsert = apiList.stream()
                .filter(e -> e.getRegistryId() != null && !dbMap.containsKey(e.getRegistryId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudTcrEntMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudTcrEnt> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudTcrEnt::getConfName, cloudConf.getName())
                    .in(QCloudTcrEnt::getRegistryId, toDeleteIds)
                    .set(QCloudTcrEnt::getDeleted, 1);
            qCloudTcrEntMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== TENCENTCONNECT ====================

    public int syncTencentConnect(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudTencentConnect> apiList = qCloudClient.listTencentConnect();
        List<QCloudTencentConnect> dbList = qCloudTencentConnectMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudTencentConnect> apiMap = apiList.stream()
                .filter(e -> e.getConnectorId() != null)
                .collect(Collectors.toMap(QCloudTencentConnect::getConnectorId, e -> e, (a, b) -> a));
        Map<String, QCloudTencentConnect> dbMap = dbList.stream()
                .filter(e -> e.getConnectorId() != null)
                .collect(Collectors.toMap(QCloudTencentConnect::getConnectorId, e -> e, (a, b) -> a));

        List<QCloudTencentConnect> toInsert = apiList.stream()
                .filter(e -> e.getConnectorId() != null && !dbMap.containsKey(e.getConnectorId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudTencentConnectMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudTencentConnect> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudTencentConnect::getConfName, cloudConf.getName())
                    .in(QCloudTencentConnect::getConnectorId, toDeleteIds)
                    .set(QCloudTencentConnect::getDeleted, 1);
            qCloudTencentConnectMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== TOKENHUB ====================

    public int syncTokenHub(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudTokenHub> apiList = qCloudClient.listTokenHub();
        List<QCloudTokenHub> dbList = qCloudTokenHubMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudTokenHub> apiMap = apiList.stream()
                .filter(e -> e.getModelName() != null)
                .collect(Collectors.toMap(QCloudTokenHub::getModelName, e -> e, (a, b) -> a));
        Map<String, QCloudTokenHub> dbMap = dbList.stream()
                .filter(e -> e.getModelName() != null)
                .collect(Collectors.toMap(QCloudTokenHub::getModelName, e -> e, (a, b) -> a));

        List<QCloudTokenHub> toInsert = apiList.stream()
                .filter(e -> e.getModelName() != null && !dbMap.containsKey(e.getModelName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudTokenHubMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudTokenHub> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudTokenHub::getConfName, cloudConf.getName())
                    .in(QCloudTokenHub::getModelName, toDeleteIds)
                    .set(QCloudTokenHub::getDeleted, 1);
            qCloudTokenHubMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== TOURISMBIGDATA ====================

    public int syncTourismBigdata(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudTourismBigdata> apiList = qCloudClient.listTourismBigdata();
        List<QCloudTourismBigdata> dbList = qCloudTourismBigdataMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudTourismBigdata> apiMap = apiList.stream()
                .filter(e -> e.getSceneId() != null)
                .collect(Collectors.toMap(QCloudTourismBigdata::getSceneId, e -> e, (a, b) -> a));
        Map<String, QCloudTourismBigdata> dbMap = dbList.stream()
                .filter(e -> e.getSceneId() != null)
                .collect(Collectors.toMap(QCloudTourismBigdata::getSceneId, e -> e, (a, b) -> a));

        List<QCloudTourismBigdata> toInsert = apiList.stream()
                .filter(e -> e.getSceneId() != null && !dbMap.containsKey(e.getSceneId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudTourismBigdataMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudTourismBigdata> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudTourismBigdata::getConfName, cloudConf.getName())
                    .in(QCloudTourismBigdata::getSceneId, toDeleteIds)
                    .set(QCloudTourismBigdata::getDeleted, 1);
            qCloudTourismBigdataMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== USER ====================

    public int syncUser(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudUser> apiList = qCloudClient.listUsers();
        List<QCloudUser> dbList = qCloudUserMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudUser> apiMap = apiList.stream()
                .filter(e -> e.getName() != null)
                .collect(Collectors.toMap(QCloudUser::getName, e -> e, (a, b) -> a));
        Map<String, QCloudUser> dbMap = dbList.stream()
                .filter(e -> e.getName() != null)
                .collect(Collectors.toMap(QCloudUser::getName, e -> e, (a, b) -> a));

        List<QCloudUser> toInsert = apiList.stream()
                .filter(e -> e.getName() != null && !dbMap.containsKey(e.getName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudUserMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudUser> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudUser::getConfName, cloudConf.getName())
                    .in(QCloudUser::getName, toDeleteIds)
                    .set(QCloudUser::getDeleted, 1);
            qCloudUserMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== VOICEMSG ====================

    public int syncVoiceMsg(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudVoiceMsg> apiList = qCloudClient.listVoiceMsg();
        List<QCloudVoiceMsg> dbList = qCloudVoiceMsgMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudVoiceMsg> apiMap = apiList.stream()
                .filter(e -> e.getSdkAppId() != null)
                .collect(Collectors.toMap(e -> String.valueOf(e.getSdkAppId()), e -> e, (a, b) -> a));
        Map<String, QCloudVoiceMsg> dbMap = dbList.stream()
                .filter(e -> e.getSdkAppId() != null)
                .collect(Collectors.toMap(e -> String.valueOf(e.getSdkAppId()), e -> e, (a, b) -> a));

        List<QCloudVoiceMsg> toInsert = apiList.stream()
                .filter(e -> e.getSdkAppId() != null && !dbMap.containsKey(String.valueOf(e.getSdkAppId())))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudVoiceMsgMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudVoiceMsg> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudVoiceMsg::getConfName, cloudConf.getName())
                    .in(QCloudVoiceMsg::getSdkAppId, toDeleteIds)
                    .set(QCloudVoiceMsg::getDeleted, 1);
            qCloudVoiceMsgMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== WEDATA ====================

    public int syncWeData(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudWeData> apiList = qCloudClient.listWeData();
        List<QCloudWeData> dbList = qCloudWeDataMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudWeData> apiMap = apiList.stream()
                .filter(e -> e.getProjectId() != null)
                .collect(Collectors.toMap(QCloudWeData::getProjectId, e -> e, (a, b) -> a));
        Map<String, QCloudWeData> dbMap = dbList.stream()
                .filter(e -> e.getProjectId() != null)
                .collect(Collectors.toMap(QCloudWeData::getProjectId, e -> e, (a, b) -> a));

        List<QCloudWeData> toInsert = apiList.stream()
                .filter(e -> e.getProjectId() != null && !dbMap.containsKey(e.getProjectId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudWeDataMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudWeData> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudWeData::getConfName, cloudConf.getName())
                    .in(QCloudWeData::getProjectId, toDeleteIds)
                    .set(QCloudWeData::getDeleted, 1);
            qCloudWeDataMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== WELINK ====================

    public int syncWeLink(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudWeLink> apiList = qCloudClient.listWeLink();
        List<QCloudWeLink> dbList = qCloudWeLinkMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudWeLink> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudWeLink::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudWeLink> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudWeLink::getInstanceId, e -> e, (a, b) -> a));

        List<QCloudWeLink> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudWeLinkMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudWeLink> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudWeLink::getConfName, cloudConf.getName())
                    .in(QCloudWeLink::getInstanceId, toDeleteIds)
                    .set(QCloudWeLink::getDeleted, 1);
            qCloudWeLinkMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== WEBSEARCH ====================

    public int syncWebSearch(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudWebSearch> apiList = qCloudClient.listWebSearch();
        List<QCloudWebSearch> dbList = qCloudWebSearchMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudWebSearch> apiMap = apiList.stream()
                .filter(e -> e.getQuery() != null)
                .collect(Collectors.toMap(QCloudWebSearch::getQuery, e -> e, (a, b) -> a));
        Map<String, QCloudWebSearch> dbMap = dbList.stream()
                .filter(e -> e.getQuery() != null)
                .collect(Collectors.toMap(QCloudWebSearch::getQuery, e -> e, (a, b) -> a));

        List<QCloudWebSearch> toInsert = apiList.stream()
                .filter(e -> e.getQuery() != null && !dbMap.containsKey(e.getQuery()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudWebSearchMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudWebSearch> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudWebSearch::getConfName, cloudConf.getName())
                    .in(QCloudWebSearch::getQuery, toDeleteIds)
                    .set(QCloudWebSearch::getDeleted, 1);
            qCloudWebSearchMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== WEDA ====================

    public int syncWeda(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudWeda> apiList = qCloudClient.listWeda();
        List<QCloudWeda> dbList = qCloudWedaMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudWeda> apiMap = apiList.stream()
                .filter(e -> e.getAppId() != null)
                .collect(Collectors.toMap(QCloudWeda::getAppId, e -> e, (a, b) -> a));
        Map<String, QCloudWeda> dbMap = dbList.stream()
                .filter(e -> e.getAppId() != null)
                .collect(Collectors.toMap(QCloudWeda::getAppId, e -> e, (a, b) -> a));

        List<QCloudWeda> toInsert = apiList.stream()
                .filter(e -> e.getAppId() != null && !dbMap.containsKey(e.getAppId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudWedaMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudWeda> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudWeda::getConfName, cloudConf.getName())
                    .in(QCloudWeda::getAppId, toDeleteIds)
                    .set(QCloudWeda::getDeleted, 1);
            qCloudWedaMapper.update(null, uw);
        }
        return insertCount;
    }
}
