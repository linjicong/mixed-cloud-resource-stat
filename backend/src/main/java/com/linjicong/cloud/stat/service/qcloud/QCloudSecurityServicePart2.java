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
 * 腾讯云Security Part2资源同步服务
 * 包含以下资源类型的同步方法: PenTest, RiskIdentify, SSL, SSLPod, SafeAudio, SafeCenter, SafeDoc, SafeGuard, SafeImage, SafeMonitor...
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Service
public class QCloudSecurityServicePart2 {

    @Resource
    private QCloudPenTestMapper qCloudPenTestMapper;
    @Resource
    private QCloudRiskIdentifyMapper qCloudRiskIdentifyMapper;
    @Resource
    private QCloudSSLMapper qCloudSSLMapper;
    @Resource
    private QCloudSSLPodMapper qCloudSSLPodMapper;
    @Resource
    private QCloudSafeAudioMapper qCloudSafeAudioMapper;
    @Resource
    private QCloudSafeCenterMapper qCloudSafeCenterMapper;
    @Resource
    private QCloudSafeDocMapper qCloudSafeDocMapper;
    @Resource
    private QCloudSafeGuardMapper qCloudSafeGuardMapper;
    @Resource
    private QCloudSafeImageMapper qCloudSafeImageMapper;
    @Resource
    private QCloudSafeMonitorMapper qCloudSafeMonitorMapper;
    @Resource
    private QCloudSafePlatformMapper qCloudSafePlatformMapper;
    @Resource
    private QCloudSafeTextMapper qCloudSafeTextMapper;

    // ==================== PENTEST ====================

    public int syncPenTest(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudPenTest> apiList = qCloudClient.listPenTest();
        List<QCloudPenTest> dbList = qCloudPenTestMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudPenTest> apiMap = apiList.stream()
                .filter(e -> e.getTaskId() != null)
                .collect(Collectors.toMap(QCloudPenTest::getTaskId, e -> e, (a, b) -> a));
        Map<String, QCloudPenTest> dbMap = dbList.stream()
                .filter(e -> e.getTaskId() != null)
                .collect(Collectors.toMap(QCloudPenTest::getTaskId, e -> e, (a, b) -> a));

        List<QCloudPenTest> toInsert = apiList.stream()
                .filter(e -> e.getTaskId() != null && !dbMap.containsKey(e.getTaskId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudPenTestMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudPenTest> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudPenTest::getConfName, cloudConf.getName())
                    .in(QCloudPenTest::getTaskId, toDeleteIds)
                    .set(QCloudPenTest::getDeleted, 1);
            qCloudPenTestMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== RISKIDENTIFY ====================

    public int syncRiskIdentify(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudRiskIdentify> apiList = qCloudClient.listRiskIdentify();
        List<QCloudRiskIdentify> dbList = qCloudRiskIdentifyMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudRiskIdentify> apiMap = apiList.stream()
                .filter(e -> e.getSceneCode() != null)
                .collect(Collectors.toMap(QCloudRiskIdentify::getSceneCode, e -> e, (a, b) -> a));
        Map<String, QCloudRiskIdentify> dbMap = dbList.stream()
                .filter(e -> e.getSceneCode() != null)
                .collect(Collectors.toMap(QCloudRiskIdentify::getSceneCode, e -> e, (a, b) -> a));

        List<QCloudRiskIdentify> toInsert = apiList.stream()
                .filter(e -> e.getSceneCode() != null && !dbMap.containsKey(e.getSceneCode()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudRiskIdentifyMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudRiskIdentify> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudRiskIdentify::getConfName, cloudConf.getName())
                    .in(QCloudRiskIdentify::getSceneCode, toDeleteIds)
                    .set(QCloudRiskIdentify::getDeleted, 1);
            qCloudRiskIdentifyMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== SSL ====================

    public int syncSSL(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudSSL> apiList = qCloudClient.listSSL();
        List<QCloudSSL> dbList = qCloudSSLMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudSSL> apiMap = apiList.stream()
                .filter(e -> e.getAlias() != null)
                .collect(Collectors.toMap(QCloudSSL::getAlias, e -> e, (a, b) -> a));
        Map<String, QCloudSSL> dbMap = dbList.stream()
                .filter(e -> e.getAlias() != null)
                .collect(Collectors.toMap(QCloudSSL::getAlias, e -> e, (a, b) -> a));

        List<QCloudSSL> toInsert = apiList.stream()
                .filter(e -> e.getAlias() != null && !dbMap.containsKey(e.getAlias()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudSSLMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudSSL> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudSSL::getConfName, cloudConf.getName())
                    .in(QCloudSSL::getAlias, toDeleteIds)
                    .set(QCloudSSL::getDeleted, 1);
            qCloudSSLMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== SSLPOD ====================

    public int syncSSLPod(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudSSLPod> apiList = qCloudClient.listSSLPod();
        List<QCloudSSLPod> dbList = qCloudSSLPodMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudSSLPod> apiMap = apiList.stream()
                .filter(e -> e.getDomain() != null)
                .collect(Collectors.toMap(QCloudSSLPod::getDomain, e -> e, (a, b) -> a));
        Map<String, QCloudSSLPod> dbMap = dbList.stream()
                .filter(e -> e.getDomain() != null)
                .collect(Collectors.toMap(QCloudSSLPod::getDomain, e -> e, (a, b) -> a));

        List<QCloudSSLPod> toInsert = apiList.stream()
                .filter(e -> e.getDomain() != null && !dbMap.containsKey(e.getDomain()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudSSLPodMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudSSLPod> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudSSLPod::getConfName, cloudConf.getName())
                    .in(QCloudSSLPod::getDomain, toDeleteIds)
                    .set(QCloudSSLPod::getDeleted, 1);
            qCloudSSLPodMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== SAFEAUDIO ====================

    public int syncSafeAudio(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudSafeAudio> apiList = qCloudClient.listSafeAudio();
        List<QCloudSafeAudio> dbList = qCloudSafeAudioMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudSafeAudio> apiMap = apiList.stream()
                .filter(e -> e.getBizType() != null)
                .collect(Collectors.toMap(QCloudSafeAudio::getBizType, e -> e, (a, b) -> a));
        Map<String, QCloudSafeAudio> dbMap = dbList.stream()
                .filter(e -> e.getBizType() != null)
                .collect(Collectors.toMap(QCloudSafeAudio::getBizType, e -> e, (a, b) -> a));

        List<QCloudSafeAudio> toInsert = apiList.stream()
                .filter(e -> e.getBizType() != null && !dbMap.containsKey(e.getBizType()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudSafeAudioMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudSafeAudio> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudSafeAudio::getConfName, cloudConf.getName())
                    .in(QCloudSafeAudio::getBizType, toDeleteIds)
                    .set(QCloudSafeAudio::getDeleted, 1);
            qCloudSafeAudioMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== SAFECENTER ====================

    public int syncSafeCenter(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudSafeCenter> apiList = qCloudClient.listSafeCenter();
        List<QCloudSafeCenter> dbList = qCloudSafeCenterMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudSafeCenter> apiMap = apiList.stream()
                .filter(e -> e.getAssetId() != null)
                .collect(Collectors.toMap(QCloudSafeCenter::getAssetId, e -> e, (a, b) -> a));
        Map<String, QCloudSafeCenter> dbMap = dbList.stream()
                .filter(e -> e.getAssetId() != null)
                .collect(Collectors.toMap(QCloudSafeCenter::getAssetId, e -> e, (a, b) -> a));

        List<QCloudSafeCenter> toInsert = apiList.stream()
                .filter(e -> e.getAssetId() != null && !dbMap.containsKey(e.getAssetId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudSafeCenterMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudSafeCenter> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudSafeCenter::getConfName, cloudConf.getName())
                    .in(QCloudSafeCenter::getAssetId, toDeleteIds)
                    .set(QCloudSafeCenter::getDeleted, 1);
            qCloudSafeCenterMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== SAFEDOC ====================

    public int syncSafeDoc(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudSafeDoc> apiList = qCloudClient.listSafeDoc();
        List<QCloudSafeDoc> dbList = qCloudSafeDocMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudSafeDoc> apiMap = apiList.stream()
                .filter(e -> e.getBizType() != null)
                .collect(Collectors.toMap(QCloudSafeDoc::getBizType, e -> e, (a, b) -> a));
        Map<String, QCloudSafeDoc> dbMap = dbList.stream()
                .filter(e -> e.getBizType() != null)
                .collect(Collectors.toMap(QCloudSafeDoc::getBizType, e -> e, (a, b) -> a));

        List<QCloudSafeDoc> toInsert = apiList.stream()
                .filter(e -> e.getBizType() != null && !dbMap.containsKey(e.getBizType()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudSafeDocMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudSafeDoc> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudSafeDoc::getConfName, cloudConf.getName())
                    .in(QCloudSafeDoc::getBizType, toDeleteIds)
                    .set(QCloudSafeDoc::getDeleted, 1);
            qCloudSafeDocMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== SAFEGUARD ====================

    public int syncSafeGuard(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudSafeGuard> apiList = qCloudClient.listSafeGuard();
        List<QCloudSafeGuard> dbList = qCloudSafeGuardMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudSafeGuard> apiMap = apiList.stream()
                .filter(e -> e.getServiceId() != null)
                .collect(Collectors.toMap(QCloudSafeGuard::getServiceId, e -> e, (a, b) -> a));
        Map<String, QCloudSafeGuard> dbMap = dbList.stream()
                .filter(e -> e.getServiceId() != null)
                .collect(Collectors.toMap(QCloudSafeGuard::getServiceId, e -> e, (a, b) -> a));

        List<QCloudSafeGuard> toInsert = apiList.stream()
                .filter(e -> e.getServiceId() != null && !dbMap.containsKey(e.getServiceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudSafeGuardMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudSafeGuard> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudSafeGuard::getConfName, cloudConf.getName())
                    .in(QCloudSafeGuard::getServiceId, toDeleteIds)
                    .set(QCloudSafeGuard::getDeleted, 1);
            qCloudSafeGuardMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== SAFEIMAGE ====================

    public int syncSafeImage(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudSafeImage> apiList = qCloudClient.listSafeImage();
        List<QCloudSafeImage> dbList = qCloudSafeImageMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudSafeImage> apiMap = apiList.stream()
                .filter(e -> e.getBizType() != null)
                .collect(Collectors.toMap(QCloudSafeImage::getBizType, e -> e, (a, b) -> a));
        Map<String, QCloudSafeImage> dbMap = dbList.stream()
                .filter(e -> e.getBizType() != null)
                .collect(Collectors.toMap(QCloudSafeImage::getBizType, e -> e, (a, b) -> a));

        List<QCloudSafeImage> toInsert = apiList.stream()
                .filter(e -> e.getBizType() != null && !dbMap.containsKey(e.getBizType()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudSafeImageMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudSafeImage> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudSafeImage::getConfName, cloudConf.getName())
                    .in(QCloudSafeImage::getBizType, toDeleteIds)
                    .set(QCloudSafeImage::getDeleted, 1);
            qCloudSafeImageMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== SAFEMONITOR ====================

    public int syncSafeMonitor(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudSafeMonitor> apiList = qCloudClient.listSafeMonitor();
        List<QCloudSafeMonitor> dbList = qCloudSafeMonitorMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudSafeMonitor> apiMap = apiList.stream()
                .filter(e -> e.getMonitorId() != null)
                .collect(Collectors.toMap(QCloudSafeMonitor::getMonitorId, e -> e, (a, b) -> a));
        Map<String, QCloudSafeMonitor> dbMap = dbList.stream()
                .filter(e -> e.getMonitorId() != null)
                .collect(Collectors.toMap(QCloudSafeMonitor::getMonitorId, e -> e, (a, b) -> a));

        List<QCloudSafeMonitor> toInsert = apiList.stream()
                .filter(e -> e.getMonitorId() != null && !dbMap.containsKey(e.getMonitorId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudSafeMonitorMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudSafeMonitor> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudSafeMonitor::getConfName, cloudConf.getName())
                    .in(QCloudSafeMonitor::getMonitorId, toDeleteIds)
                    .set(QCloudSafeMonitor::getDeleted, 1);
            qCloudSafeMonitorMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== SAFEPLATFORM ====================

    public int syncSafePlatform(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudSafePlatform> apiList = qCloudClient.listSafePlatform();
        List<QCloudSafePlatform> dbList = qCloudSafePlatformMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudSafePlatform> apiMap = apiList.stream()
                .filter(e -> e.getPlatformId() != null)
                .collect(Collectors.toMap(QCloudSafePlatform::getPlatformId, e -> e, (a, b) -> a));
        Map<String, QCloudSafePlatform> dbMap = dbList.stream()
                .filter(e -> e.getPlatformId() != null)
                .collect(Collectors.toMap(QCloudSafePlatform::getPlatformId, e -> e, (a, b) -> a));

        List<QCloudSafePlatform> toInsert = apiList.stream()
                .filter(e -> e.getPlatformId() != null && !dbMap.containsKey(e.getPlatformId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudSafePlatformMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudSafePlatform> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudSafePlatform::getConfName, cloudConf.getName())
                    .in(QCloudSafePlatform::getPlatformId, toDeleteIds)
                    .set(QCloudSafePlatform::getDeleted, 1);
            qCloudSafePlatformMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== SAFETEXT ====================

    public int syncSafeText(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudSafeText> apiList = qCloudClient.listSafeText();
        List<QCloudSafeText> dbList = qCloudSafeTextMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudSafeText> apiMap = apiList.stream()
                .filter(e -> e.getBizType() != null)
                .collect(Collectors.toMap(QCloudSafeText::getBizType, e -> e, (a, b) -> a));
        Map<String, QCloudSafeText> dbMap = dbList.stream()
                .filter(e -> e.getBizType() != null)
                .collect(Collectors.toMap(QCloudSafeText::getBizType, e -> e, (a, b) -> a));

        List<QCloudSafeText> toInsert = apiList.stream()
                .filter(e -> e.getBizType() != null && !dbMap.containsKey(e.getBizType()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudSafeTextMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudSafeText> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudSafeText::getConfName, cloudConf.getName())
                    .in(QCloudSafeText::getBizType, toDeleteIds)
                    .set(QCloudSafeText::getDeleted, 1);
            qCloudSafeTextMapper.update(null, uw);
        }
        return insertCount;
    }
}
