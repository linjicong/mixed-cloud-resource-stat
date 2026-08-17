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
 * 腾讯云AI Part1资源同步服务
 * 包含以下资源类型的同步方法: ASR, ContentRecognize, ESign, EngWrite, Face, FaceFusion, FaceMakeup, FaceSwap, ImageProcess2, ImageSearch...
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Service
public class QCloudAIServicePart1 {

    @Resource
    private QCloudASRMapper qCloudASRMapper;
    @Resource
    private QCloudContentRecognizeMapper qCloudContentRecognizeMapper;
    @Resource
    private QCloudESignMapper qCloudESignMapper;
    @Resource
    private QCloudEngWriteMapper qCloudEngWriteMapper;
    @Resource
    private QCloudFaceFusionMapper qCloudFaceFusionMapper;
    @Resource
    private QCloudFaceMakeupMapper qCloudFaceMakeupMapper;
    @Resource
    private QCloudFaceMapper qCloudFaceMapper;
    @Resource
    private QCloudFaceSwapMapper qCloudFaceSwapMapper;
    @Resource
    private QCloudImageProcess2Mapper qCloudImageProcess2Mapper;
    @Resource
    private QCloudImageSearchMapper qCloudImageSearchMapper;
    @Resource
    private QCloudKnowledgeEngineMapper qCloudKnowledgeEngineMapper;
    @Resource
    private QCloudMathGradeMapper qCloudMathGradeMapper;

    // ==================== ASR ====================

    public int syncASR(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudASR> apiList = qCloudClient.listASR();
        List<QCloudASR> dbList = qCloudASRMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudASR> apiMap = apiList.stream()
                .filter(e -> e.getEngineType() != null)
                .collect(Collectors.toMap(QCloudASR::getEngineType, e -> e, (a, b) -> a));
        Map<String, QCloudASR> dbMap = dbList.stream()
                .filter(e -> e.getEngineType() != null)
                .collect(Collectors.toMap(QCloudASR::getEngineType, e -> e, (a, b) -> a));

        List<QCloudASR> toInsert = apiList.stream()
                .filter(e -> e.getEngineType() != null && !dbMap.containsKey(e.getEngineType()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudASRMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudASR> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudASR::getConfName, cloudConf.getName())
                    .in(QCloudASR::getEngineType, toDeleteIds)
                    .set(QCloudASR::getDeleted, 1);
            qCloudASRMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CONTENTRECOGNIZE ====================

    public int syncContentRecognize(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudContentRecognize> apiList = qCloudClient.listContentRecognize();
        List<QCloudContentRecognize> dbList = qCloudContentRecognizeMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudContentRecognize> apiMap = apiList.stream()
                .filter(e -> e.getType() != null)
                .collect(Collectors.toMap(QCloudContentRecognize::getType, e -> e, (a, b) -> a));
        Map<String, QCloudContentRecognize> dbMap = dbList.stream()
                .filter(e -> e.getType() != null)
                .collect(Collectors.toMap(QCloudContentRecognize::getType, e -> e, (a, b) -> a));

        List<QCloudContentRecognize> toInsert = apiList.stream()
                .filter(e -> e.getType() != null && !dbMap.containsKey(e.getType()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudContentRecognizeMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudContentRecognize> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudContentRecognize::getConfName, cloudConf.getName())
                    .in(QCloudContentRecognize::getType, toDeleteIds)
                    .set(QCloudContentRecognize::getDeleted, 1);
            qCloudContentRecognizeMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== ESIGN ====================

    public int syncESign(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudESign> apiList = qCloudClient.listESign();
        List<QCloudESign> dbList = qCloudESignMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudESign> apiMap = apiList.stream()
                .filter(e -> e.getFlowId() != null)
                .collect(Collectors.toMap(QCloudESign::getFlowId, e -> e, (a, b) -> a));
        Map<String, QCloudESign> dbMap = dbList.stream()
                .filter(e -> e.getFlowId() != null)
                .collect(Collectors.toMap(QCloudESign::getFlowId, e -> e, (a, b) -> a));

        List<QCloudESign> toInsert = apiList.stream()
                .filter(e -> e.getFlowId() != null && !dbMap.containsKey(e.getFlowId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudESignMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudESign> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudESign::getConfName, cloudConf.getName())
                    .in(QCloudESign::getFlowId, toDeleteIds)
                    .set(QCloudESign::getDeleted, 1);
            qCloudESignMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== ENGWRITE ====================

    public int syncEngWrite(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudEngWrite> apiList = qCloudClient.listEngWrite();
        List<QCloudEngWrite> dbList = qCloudEngWriteMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudEngWrite> apiMap = apiList.stream()
                .filter(e -> e.getTaskId() != null)
                .collect(Collectors.toMap(QCloudEngWrite::getTaskId, e -> e, (a, b) -> a));
        Map<String, QCloudEngWrite> dbMap = dbList.stream()
                .filter(e -> e.getTaskId() != null)
                .collect(Collectors.toMap(QCloudEngWrite::getTaskId, e -> e, (a, b) -> a));

        List<QCloudEngWrite> toInsert = apiList.stream()
                .filter(e -> e.getTaskId() != null && !dbMap.containsKey(e.getTaskId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudEngWriteMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudEngWrite> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudEngWrite::getConfName, cloudConf.getName())
                    .in(QCloudEngWrite::getTaskId, toDeleteIds)
                    .set(QCloudEngWrite::getDeleted, 1);
            qCloudEngWriteMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== FACE ====================

    public int syncFace(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudFace> apiList = qCloudClient.listFace();
        List<QCloudFace> dbList = qCloudFaceMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudFace> apiMap = apiList.stream()
                .filter(e -> e.getGroupName() != null)
                .collect(Collectors.toMap(QCloudFace::getGroupName, e -> e, (a, b) -> a));
        Map<String, QCloudFace> dbMap = dbList.stream()
                .filter(e -> e.getGroupName() != null)
                .collect(Collectors.toMap(QCloudFace::getGroupName, e -> e, (a, b) -> a));

        List<QCloudFace> toInsert = apiList.stream()
                .filter(e -> e.getGroupName() != null && !dbMap.containsKey(e.getGroupName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudFaceMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudFace> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudFace::getConfName, cloudConf.getName())
                    .in(QCloudFace::getGroupName, toDeleteIds)
                    .set(QCloudFace::getDeleted, 1);
            qCloudFaceMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== FACEFUSION ====================

    public int syncFaceFusion(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudFaceFusion> apiList = qCloudClient.listFaceFusion();
        List<QCloudFaceFusion> dbList = qCloudFaceFusionMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudFaceFusion> apiMap = apiList.stream()
                .filter(e -> e.getTemplateName() != null)
                .collect(Collectors.toMap(QCloudFaceFusion::getTemplateName, e -> e, (a, b) -> a));
        Map<String, QCloudFaceFusion> dbMap = dbList.stream()
                .filter(e -> e.getTemplateName() != null)
                .collect(Collectors.toMap(QCloudFaceFusion::getTemplateName, e -> e, (a, b) -> a));

        List<QCloudFaceFusion> toInsert = apiList.stream()
                .filter(e -> e.getTemplateName() != null && !dbMap.containsKey(e.getTemplateName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudFaceFusionMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudFaceFusion> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudFaceFusion::getConfName, cloudConf.getName())
                    .in(QCloudFaceFusion::getTemplateName, toDeleteIds)
                    .set(QCloudFaceFusion::getDeleted, 1);
            qCloudFaceFusionMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== FACEMAKEUP ====================

    public int syncFaceMakeup(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudFaceMakeup> apiList = qCloudClient.listFaceMakeup();
        List<QCloudFaceMakeup> dbList = qCloudFaceMakeupMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudFaceMakeup> apiMap = apiList.stream()
                .filter(e -> e.getMakeupType() != null)
                .collect(Collectors.toMap(QCloudFaceMakeup::getMakeupType, e -> e, (a, b) -> a));
        Map<String, QCloudFaceMakeup> dbMap = dbList.stream()
                .filter(e -> e.getMakeupType() != null)
                .collect(Collectors.toMap(QCloudFaceMakeup::getMakeupType, e -> e, (a, b) -> a));

        List<QCloudFaceMakeup> toInsert = apiList.stream()
                .filter(e -> e.getMakeupType() != null && !dbMap.containsKey(e.getMakeupType()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudFaceMakeupMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudFaceMakeup> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudFaceMakeup::getConfName, cloudConf.getName())
                    .in(QCloudFaceMakeup::getMakeupType, toDeleteIds)
                    .set(QCloudFaceMakeup::getDeleted, 1);
            qCloudFaceMakeupMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== FACESWAP ====================

    public int syncFaceSwap(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudFaceSwap> apiList = qCloudClient.listFaceSwap();
        List<QCloudFaceSwap> dbList = qCloudFaceSwapMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudFaceSwap> apiMap = apiList.stream()
                .filter(e -> e.getTemplateName() != null)
                .collect(Collectors.toMap(QCloudFaceSwap::getTemplateName, e -> e, (a, b) -> a));
        Map<String, QCloudFaceSwap> dbMap = dbList.stream()
                .filter(e -> e.getTemplateName() != null)
                .collect(Collectors.toMap(QCloudFaceSwap::getTemplateName, e -> e, (a, b) -> a));

        List<QCloudFaceSwap> toInsert = apiList.stream()
                .filter(e -> e.getTemplateName() != null && !dbMap.containsKey(e.getTemplateName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudFaceSwapMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudFaceSwap> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudFaceSwap::getConfName, cloudConf.getName())
                    .in(QCloudFaceSwap::getTemplateName, toDeleteIds)
                    .set(QCloudFaceSwap::getDeleted, 1);
            qCloudFaceSwapMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== IMAGEPROCESS2 ====================

    public int syncImageProcess2(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudImageProcess2> apiList = qCloudClient.listImageProcess2();
        List<QCloudImageProcess2> dbList = qCloudImageProcess2Mapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudImageProcess2> apiMap = apiList.stream()
                .filter(e -> e.getType() != null)
                .collect(Collectors.toMap(QCloudImageProcess2::getType, e -> e, (a, b) -> a));
        Map<String, QCloudImageProcess2> dbMap = dbList.stream()
                .filter(e -> e.getType() != null)
                .collect(Collectors.toMap(QCloudImageProcess2::getType, e -> e, (a, b) -> a));

        List<QCloudImageProcess2> toInsert = apiList.stream()
                .filter(e -> e.getType() != null && !dbMap.containsKey(e.getType()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudImageProcess2Mapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudImageProcess2> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudImageProcess2::getConfName, cloudConf.getName())
                    .in(QCloudImageProcess2::getType, toDeleteIds)
                    .set(QCloudImageProcess2::getDeleted, 1);
            qCloudImageProcess2Mapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== IMAGESEARCH ====================

    public int syncImageSearch(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudImageSearch> apiList = qCloudClient.listImageSearch();
        List<QCloudImageSearch> dbList = qCloudImageSearchMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudImageSearch> apiMap = apiList.stream()
                .filter(e -> e.getGroupId() != null)
                .collect(Collectors.toMap(QCloudImageSearch::getGroupId, e -> e, (a, b) -> a));
        Map<String, QCloudImageSearch> dbMap = dbList.stream()
                .filter(e -> e.getGroupId() != null)
                .collect(Collectors.toMap(QCloudImageSearch::getGroupId, e -> e, (a, b) -> a));

        List<QCloudImageSearch> toInsert = apiList.stream()
                .filter(e -> e.getGroupId() != null && !dbMap.containsKey(e.getGroupId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudImageSearchMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudImageSearch> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudImageSearch::getConfName, cloudConf.getName())
                    .in(QCloudImageSearch::getGroupId, toDeleteIds)
                    .set(QCloudImageSearch::getDeleted, 1);
            qCloudImageSearchMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== KNOWLEDGEENGINE ====================

    public int syncKnowledgeEngine(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudKnowledgeEngine> apiList = qCloudClient.listKnowledgeEngine();
        List<QCloudKnowledgeEngine> dbList = qCloudKnowledgeEngineMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudKnowledgeEngine> apiMap = apiList.stream()
                .filter(e -> e.getKnowledgeBaseId() != null)
                .collect(Collectors.toMap(QCloudKnowledgeEngine::getKnowledgeBaseId, e -> e, (a, b) -> a));
        Map<String, QCloudKnowledgeEngine> dbMap = dbList.stream()
                .filter(e -> e.getKnowledgeBaseId() != null)
                .collect(Collectors.toMap(QCloudKnowledgeEngine::getKnowledgeBaseId, e -> e, (a, b) -> a));

        List<QCloudKnowledgeEngine> toInsert = apiList.stream()
                .filter(e -> e.getKnowledgeBaseId() != null && !dbMap.containsKey(e.getKnowledgeBaseId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudKnowledgeEngineMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudKnowledgeEngine> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudKnowledgeEngine::getConfName, cloudConf.getName())
                    .in(QCloudKnowledgeEngine::getKnowledgeBaseId, toDeleteIds)
                    .set(QCloudKnowledgeEngine::getDeleted, 1);
            qCloudKnowledgeEngineMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== MATHGRADE ====================

    public int syncMathGrade(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudMathGrade> apiList = qCloudClient.listMathGrade();
        List<QCloudMathGrade> dbList = qCloudMathGradeMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudMathGrade> apiMap = apiList.stream()
                .filter(e -> e.getTaskId() != null)
                .collect(Collectors.toMap(QCloudMathGrade::getTaskId, e -> e, (a, b) -> a));
        Map<String, QCloudMathGrade> dbMap = dbList.stream()
                .filter(e -> e.getTaskId() != null)
                .collect(Collectors.toMap(QCloudMathGrade::getTaskId, e -> e, (a, b) -> a));

        List<QCloudMathGrade> toInsert = apiList.stream()
                .filter(e -> e.getTaskId() != null && !dbMap.containsKey(e.getTaskId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudMathGradeMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudMathGrade> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudMathGrade::getConfName, cloudConf.getName())
                    .in(QCloudMathGrade::getTaskId, toDeleteIds)
                    .set(QCloudMathGrade::getDeleted, 1);
            qCloudMathGradeMapper.update(null, uw);
        }
        return insertCount;
    }
}
