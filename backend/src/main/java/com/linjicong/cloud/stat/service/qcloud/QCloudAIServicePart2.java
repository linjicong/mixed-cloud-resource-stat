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
 * 腾讯云AI Part2资源同步服务
 * 包含以下资源类型的同步方法: NMT, OCR, SpokenEval, TI, TIHai, TTS, VoiceClone, SmartMedia, MediaAi
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Service
public class QCloudAIServicePart2 {

    @Resource
    private QCloudMediaAiMapper qCloudMediaAiMapper;
    @Resource
    private QCloudNMTMapper qCloudNMTMapper;
    @Resource
    private QCloudOCRMapper qCloudOCRMapper;
    @Resource
    private QCloudSmartMediaMapper qCloudSmartMediaMapper;
    @Resource
    private QCloudSpokenEvalMapper qCloudSpokenEvalMapper;
    @Resource
    private QCloudTIHaiMapper qCloudTIHaiMapper;
    @Resource
    private QCloudTIMapper qCloudTIMapper;
    @Resource
    private QCloudTTSMapper qCloudTTSMapper;
    @Resource
    private QCloudVoiceCloneMapper qCloudVoiceCloneMapper;

    // ==================== NMT ====================

    public int syncNMT(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudNMT> apiList = qCloudClient.listNMT();
        List<QCloudNMT> dbList = qCloudNMTMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudNMT> apiMap = apiList.stream()
                .filter(e -> e.getSourceLang() != null)
                .collect(Collectors.toMap(QCloudNMT::getSourceLang, e -> e, (a, b) -> a));
        Map<String, QCloudNMT> dbMap = dbList.stream()
                .filter(e -> e.getSourceLang() != null)
                .collect(Collectors.toMap(QCloudNMT::getSourceLang, e -> e, (a, b) -> a));

        List<QCloudNMT> toInsert = apiList.stream()
                .filter(e -> e.getSourceLang() != null && !dbMap.containsKey(e.getSourceLang()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudNMTMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudNMT> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudNMT::getConfName, cloudConf.getName())
                    .in(QCloudNMT::getSourceLang, toDeleteIds)
                    .set(QCloudNMT::getDeleted, 1);
            qCloudNMTMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== OCR ====================

    public int syncOCR(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudOCR> apiList = qCloudClient.listOCR();
        List<QCloudOCR> dbList = qCloudOCRMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudOCR> apiMap = apiList.stream()
                .filter(e -> e.getType() != null)
                .collect(Collectors.toMap(QCloudOCR::getType, e -> e, (a, b) -> a));
        Map<String, QCloudOCR> dbMap = dbList.stream()
                .filter(e -> e.getType() != null)
                .collect(Collectors.toMap(QCloudOCR::getType, e -> e, (a, b) -> a));

        List<QCloudOCR> toInsert = apiList.stream()
                .filter(e -> e.getType() != null && !dbMap.containsKey(e.getType()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudOCRMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudOCR> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudOCR::getConfName, cloudConf.getName())
                    .in(QCloudOCR::getType, toDeleteIds)
                    .set(QCloudOCR::getDeleted, 1);
            qCloudOCRMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== SPOKENEVAL ====================

    public int syncSpokenEval(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudSpokenEval> apiList = qCloudClient.listSpokenEval();
        List<QCloudSpokenEval> dbList = qCloudSpokenEvalMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudSpokenEval> apiMap = apiList.stream()
                .filter(e -> e.getTaskId() != null)
                .collect(Collectors.toMap(QCloudSpokenEval::getTaskId, e -> e, (a, b) -> a));
        Map<String, QCloudSpokenEval> dbMap = dbList.stream()
                .filter(e -> e.getTaskId() != null)
                .collect(Collectors.toMap(QCloudSpokenEval::getTaskId, e -> e, (a, b) -> a));

        List<QCloudSpokenEval> toInsert = apiList.stream()
                .filter(e -> e.getTaskId() != null && !dbMap.containsKey(e.getTaskId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudSpokenEvalMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudSpokenEval> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudSpokenEval::getConfName, cloudConf.getName())
                    .in(QCloudSpokenEval::getTaskId, toDeleteIds)
                    .set(QCloudSpokenEval::getDeleted, 1);
            qCloudSpokenEvalMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== TI ====================

    public int syncTI(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudTI> apiList = qCloudClient.listTI();
        List<QCloudTI> dbList = qCloudTIMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudTI> apiMap = apiList.stream()
                .filter(e -> e.getModelId() != null)
                .collect(Collectors.toMap(QCloudTI::getModelId, e -> e, (a, b) -> a));
        Map<String, QCloudTI> dbMap = dbList.stream()
                .filter(e -> e.getModelId() != null)
                .collect(Collectors.toMap(QCloudTI::getModelId, e -> e, (a, b) -> a));

        List<QCloudTI> toInsert = apiList.stream()
                .filter(e -> e.getModelId() != null && !dbMap.containsKey(e.getModelId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudTIMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudTI> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudTI::getConfName, cloudConf.getName())
                    .in(QCloudTI::getModelId, toDeleteIds)
                    .set(QCloudTI::getDeleted, 1);
            qCloudTIMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== TIHAI ====================

    public int syncTIHai(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudTIHai> apiList = qCloudClient.listTIHai();
        List<QCloudTIHai> dbList = qCloudTIHaiMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudTIHai> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudTIHai::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudTIHai> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudTIHai::getInstanceId, e -> e, (a, b) -> a));

        List<QCloudTIHai> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudTIHaiMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudTIHai> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudTIHai::getConfName, cloudConf.getName())
                    .in(QCloudTIHai::getInstanceId, toDeleteIds)
                    .set(QCloudTIHai::getDeleted, 1);
            qCloudTIHaiMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== TTS ====================

    public int syncTTS(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudTTS> apiList = qCloudClient.listTTS();
        List<QCloudTTS> dbList = qCloudTTSMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudTTS> apiMap = apiList.stream()
                .filter(e -> e.getEngineType() != null)
                .collect(Collectors.toMap(QCloudTTS::getEngineType, e -> e, (a, b) -> a));
        Map<String, QCloudTTS> dbMap = dbList.stream()
                .filter(e -> e.getEngineType() != null)
                .collect(Collectors.toMap(QCloudTTS::getEngineType, e -> e, (a, b) -> a));

        List<QCloudTTS> toInsert = apiList.stream()
                .filter(e -> e.getEngineType() != null && !dbMap.containsKey(e.getEngineType()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudTTSMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudTTS> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudTTS::getConfName, cloudConf.getName())
                    .in(QCloudTTS::getEngineType, toDeleteIds)
                    .set(QCloudTTS::getDeleted, 1);
            qCloudTTSMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== VOICECLONE ====================

    public int syncVoiceClone(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudVoiceClone> apiList = qCloudClient.listVoiceClone();
        List<QCloudVoiceClone> dbList = qCloudVoiceCloneMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudVoiceClone> apiMap = apiList.stream()
                .filter(e -> e.getVoiceId() != null)
                .collect(Collectors.toMap(QCloudVoiceClone::getVoiceId, e -> e, (a, b) -> a));
        Map<String, QCloudVoiceClone> dbMap = dbList.stream()
                .filter(e -> e.getVoiceId() != null)
                .collect(Collectors.toMap(QCloudVoiceClone::getVoiceId, e -> e, (a, b) -> a));

        List<QCloudVoiceClone> toInsert = apiList.stream()
                .filter(e -> e.getVoiceId() != null && !dbMap.containsKey(e.getVoiceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudVoiceCloneMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudVoiceClone> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudVoiceClone::getConfName, cloudConf.getName())
                    .in(QCloudVoiceClone::getVoiceId, toDeleteIds)
                    .set(QCloudVoiceClone::getDeleted, 1);
            qCloudVoiceCloneMapper.update(null, uw);
        }
        return insertCount;
    }

    public int syncSmartMedia(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudSmartMedia> apiList = qCloudClient.listSmartMedia();
        List<QCloudSmartMedia> dbList = qCloudSmartMediaMapper.selectByConfName(cloudConf.getName());
        Map<String, QCloudSmartMedia> apiMap = apiList.stream().filter(e -> e.getBucketId() != null).collect(Collectors.toMap(QCloudSmartMedia::getBucketId, e -> e, (a, b) -> a));
        Map<String, QCloudSmartMedia> dbMap = dbList.stream().filter(e -> e.getBucketId() != null).collect(Collectors.toMap(QCloudSmartMedia::getBucketId, e -> e, (a, b) -> a));
        List<QCloudSmartMedia> toInsert = apiList.stream().filter(e -> e.getBucketId() != null && !dbMap.containsKey(e.getBucketId())).collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream().filter(id -> !apiMap.containsKey(id)).collect(Collectors.toSet());
        int insertCount = 0;
        if (!toInsert.isEmpty()) insertCount = qCloudSmartMediaMapper.insertBatch(toInsert);
        if (!toDeleteIds.isEmpty()) { LambdaUpdateWrapper<QCloudSmartMedia> uw = new LambdaUpdateWrapper<>(); uw.eq(QCloudSmartMedia::getConfName, cloudConf.getName()).in(QCloudSmartMedia::getBucketId, toDeleteIds).set(QCloudSmartMedia::getDeleted, 1); qCloudSmartMediaMapper.update(null, uw); }
        return insertCount;
    }

    public int syncMediaAi(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudMediaAi> apiList = qCloudClient.listMediaAi();
        List<QCloudMediaAi> dbList = qCloudMediaAiMapper.selectByConfName(cloudConf.getName());
        Map<String, QCloudMediaAi> apiMap = apiList.stream().filter(e -> e.getTaskId() != null).collect(Collectors.toMap(QCloudMediaAi::getTaskId, e -> e, (a, b) -> a));
        Map<String, QCloudMediaAi> dbMap = dbList.stream().filter(e -> e.getTaskId() != null).collect(Collectors.toMap(QCloudMediaAi::getTaskId, e -> e, (a, b) -> a));
        List<QCloudMediaAi> toInsert = apiList.stream().filter(e -> e.getTaskId() != null && !dbMap.containsKey(e.getTaskId())).collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream().filter(id -> !apiMap.containsKey(id)).collect(Collectors.toSet());
        int insertCount = 0;
        if (!toInsert.isEmpty()) insertCount = qCloudMediaAiMapper.insertBatch(toInsert);
        if (!toDeleteIds.isEmpty()) { LambdaUpdateWrapper<QCloudMediaAi> uw = new LambdaUpdateWrapper<>(); uw.eq(QCloudMediaAi::getConfName, cloudConf.getName()).in(QCloudMediaAi::getTaskId, toDeleteIds).set(QCloudMediaAi::getDeleted, 1); qCloudMediaAiMapper.update(null, uw); }
        return insertCount;
    }
}
