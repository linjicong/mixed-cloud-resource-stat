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
 * 腾讯云Media资源同步服务
 * 包含以下资源类型的同步方法: AppRender, CSS, GHPhone, Live, Live2, MediaAsset, TRTC, TRTCRoom, VOD, VODMedia...
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Service
public class QCloudMediaService {

    @Resource
    private QCloudAppRenderMapper qCloudAppRenderMapper;
    @Resource
    private QCloudCSSMapper qCloudCSSMapper;
    @Resource
    private QCloudEnhanceMediaMapper qCloudEnhanceMediaMapper;
    @Resource
    private QCloudGHPhoneMapper qCloudGHPhoneMapper;
    @Resource
    private QCloudLive2Mapper qCloudLive2Mapper;
    @Resource
    private QCloudLiveMapper qCloudLiveMapper;
    @Resource
    private QCloudMediaAssetMapper qCloudMediaAssetMapper;
    @Resource
    private QCloudMpsMapper qCloudMpsMapper;
    @Resource
    private QCloudTRTCMapper qCloudTRTCMapper;
    @Resource
    private QCloudTRTCRoomMapper qCloudTRTCRoomMapper;
    @Resource
    private QCloudVODMapper qCloudVODMapper;
    @Resource
    private QCloudVODMediaMapper qCloudVODMediaMapper;
    @Resource
    private QCloudVODProcessMapper qCloudVODProcessMapper;

    // ==================== APPRENDER ====================

    public int syncAppRender(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudAppRender> apiList = qCloudClient.listAppRender();
        List<QCloudAppRender> dbList = qCloudAppRenderMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudAppRender> apiMap = apiList.stream()
                .filter(e -> e.getAppId() != null)
                .collect(Collectors.toMap(QCloudAppRender::getAppId, e -> e, (a, b) -> a));
        Map<String, QCloudAppRender> dbMap = dbList.stream()
                .filter(e -> e.getAppId() != null)
                .collect(Collectors.toMap(QCloudAppRender::getAppId, e -> e, (a, b) -> a));

        List<QCloudAppRender> toInsert = apiList.stream()
                .filter(e -> e.getAppId() != null && !dbMap.containsKey(e.getAppId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudAppRenderMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudAppRender> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudAppRender::getConfName, cloudConf.getName())
                    .in(QCloudAppRender::getAppId, toDeleteIds)
                    .set(QCloudAppRender::getDeleted, 1);
            qCloudAppRenderMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CSS ====================

    public int syncCSS(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudCSS> apiList = qCloudClient.listCSS();
        List<QCloudCSS> dbList = qCloudCSSMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudCSS> apiMap = apiList.stream()
                .filter(e -> e.getDomainName() != null)
                .collect(Collectors.toMap(QCloudCSS::getDomainName, e -> e, (a, b) -> a));
        Map<String, QCloudCSS> dbMap = dbList.stream()
                .filter(e -> e.getDomainName() != null)
                .collect(Collectors.toMap(QCloudCSS::getDomainName, e -> e, (a, b) -> a));

        List<QCloudCSS> toInsert = apiList.stream()
                .filter(e -> e.getDomainName() != null && !dbMap.containsKey(e.getDomainName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudCSSMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudCSS> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudCSS::getConfName, cloudConf.getName())
                    .in(QCloudCSS::getDomainName, toDeleteIds)
                    .set(QCloudCSS::getDeleted, 1);
            qCloudCSSMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== GHPHONE ====================

    public int syncGHPhone(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudGHPhone> apiList = qCloudClient.listGHPhone();
        List<QCloudGHPhone> dbList = qCloudGHPhoneMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudGHPhone> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudGHPhone::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudGHPhone> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudGHPhone::getInstanceId, e -> e, (a, b) -> a));

        List<QCloudGHPhone> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudGHPhoneMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudGHPhone> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudGHPhone::getConfName, cloudConf.getName())
                    .in(QCloudGHPhone::getInstanceId, toDeleteIds)
                    .set(QCloudGHPhone::getDeleted, 1);
            qCloudGHPhoneMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== LIVE ====================

    public int syncLive(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudLive> apiList = qCloudClient.listLive();
        List<QCloudLive> dbList = qCloudLiveMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudLive> apiMap = apiList.stream()
                .filter(e -> e.getDomainName() != null)
                .collect(Collectors.toMap(QCloudLive::getDomainName, e -> e, (a, b) -> a));
        Map<String, QCloudLive> dbMap = dbList.stream()
                .filter(e -> e.getDomainName() != null)
                .collect(Collectors.toMap(QCloudLive::getDomainName, e -> e, (a, b) -> a));

        List<QCloudLive> toInsert = apiList.stream()
                .filter(e -> e.getDomainName() != null && !dbMap.containsKey(e.getDomainName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudLiveMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudLive> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudLive::getConfName, cloudConf.getName())
                    .in(QCloudLive::getDomainName, toDeleteIds)
                    .set(QCloudLive::getDeleted, 1);
            qCloudLiveMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== LIVE2 ====================

    public int syncLive2(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudLive2> apiList = qCloudClient.listLive2();
        List<QCloudLive2> dbList = qCloudLive2Mapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudLive2> apiMap = apiList.stream()
                .filter(e -> e.getDomainName() != null)
                .collect(Collectors.toMap(QCloudLive2::getDomainName, e -> e, (a, b) -> a));
        Map<String, QCloudLive2> dbMap = dbList.stream()
                .filter(e -> e.getDomainName() != null)
                .collect(Collectors.toMap(QCloudLive2::getDomainName, e -> e, (a, b) -> a));

        List<QCloudLive2> toInsert = apiList.stream()
                .filter(e -> e.getDomainName() != null && !dbMap.containsKey(e.getDomainName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudLive2Mapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudLive2> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudLive2::getConfName, cloudConf.getName())
                    .in(QCloudLive2::getDomainName, toDeleteIds)
                    .set(QCloudLive2::getDeleted, 1);
            qCloudLive2Mapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== MEDIAASSET ====================

    public int syncMediaAsset(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudMediaAsset> apiList = qCloudClient.listMediaAsset();
        List<QCloudMediaAsset> dbList = qCloudMediaAssetMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudMediaAsset> apiMap = apiList.stream()
                .filter(e -> e.getBucket() != null)
                .collect(Collectors.toMap(QCloudMediaAsset::getBucket, e -> e, (a, b) -> a));
        Map<String, QCloudMediaAsset> dbMap = dbList.stream()
                .filter(e -> e.getBucket() != null)
                .collect(Collectors.toMap(QCloudMediaAsset::getBucket, e -> e, (a, b) -> a));

        List<QCloudMediaAsset> toInsert = apiList.stream()
                .filter(e -> e.getBucket() != null && !dbMap.containsKey(e.getBucket()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudMediaAssetMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudMediaAsset> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudMediaAsset::getConfName, cloudConf.getName())
                    .in(QCloudMediaAsset::getBucket, toDeleteIds)
                    .set(QCloudMediaAsset::getDeleted, 1);
            qCloudMediaAssetMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== TRTC ====================

    public int syncTRTC(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudTRTC> apiList = qCloudClient.listTRTC();
        List<QCloudTRTC> dbList = qCloudTRTCMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudTRTC> apiMap = apiList.stream()
                .filter(e -> e.getRoomId() != null)
                .collect(Collectors.toMap(QCloudTRTC::getRoomId, e -> e, (a, b) -> a));
        Map<String, QCloudTRTC> dbMap = dbList.stream()
                .filter(e -> e.getRoomId() != null)
                .collect(Collectors.toMap(QCloudTRTC::getRoomId, e -> e, (a, b) -> a));

        List<QCloudTRTC> toInsert = apiList.stream()
                .filter(e -> e.getRoomId() != null && !dbMap.containsKey(e.getRoomId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudTRTCMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudTRTC> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudTRTC::getConfName, cloudConf.getName())
                    .in(QCloudTRTC::getRoomId, toDeleteIds)
                    .set(QCloudTRTC::getDeleted, 1);
            qCloudTRTCMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== TRTCROOM ====================

    public int syncTRTCRoom(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudTRTCRoom> apiList = qCloudClient.listTRTCRoom();
        List<QCloudTRTCRoom> dbList = qCloudTRTCRoomMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudTRTCRoom> apiMap = apiList.stream()
                .filter(e -> e.getRoomId() != null)
                .collect(Collectors.toMap(QCloudTRTCRoom::getRoomId, e -> e, (a, b) -> a));
        Map<String, QCloudTRTCRoom> dbMap = dbList.stream()
                .filter(e -> e.getRoomId() != null)
                .collect(Collectors.toMap(QCloudTRTCRoom::getRoomId, e -> e, (a, b) -> a));

        List<QCloudTRTCRoom> toInsert = apiList.stream()
                .filter(e -> e.getRoomId() != null && !dbMap.containsKey(e.getRoomId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudTRTCRoomMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudTRTCRoom> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudTRTCRoom::getConfName, cloudConf.getName())
                    .in(QCloudTRTCRoom::getRoomId, toDeleteIds)
                    .set(QCloudTRTCRoom::getDeleted, 1);
            qCloudTRTCRoomMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== VOD ====================

    public int syncVOD(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudVOD> apiList = qCloudClient.listVOD();
        List<QCloudVOD> dbList = qCloudVODMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudVOD> apiMap = apiList.stream()
                .filter(e -> e.getSubAppId() != null)
                .collect(Collectors.toMap(e -> String.valueOf(e.getSubAppId()), e -> e, (a, b) -> a));
        Map<String, QCloudVOD> dbMap = dbList.stream()
                .filter(e -> e.getSubAppId() != null)
                .collect(Collectors.toMap(e -> String.valueOf(e.getSubAppId()), e -> e, (a, b) -> a));

        List<QCloudVOD> toInsert = apiList.stream()
                .filter(e -> e.getSubAppId() != null && !dbMap.containsKey(String.valueOf(e.getSubAppId())))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudVODMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudVOD> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudVOD::getConfName, cloudConf.getName())
                    .in(QCloudVOD::getSubAppId, toDeleteIds)
                    .set(QCloudVOD::getDeleted, 1);
            qCloudVODMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== VODMEDIA ====================

    public int syncVODMedia(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudVODMedia> apiList = qCloudClient.listVODMedia();
        List<QCloudVODMedia> dbList = qCloudVODMediaMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudVODMedia> apiMap = apiList.stream()
                .filter(e -> e.getFileId() != null)
                .collect(Collectors.toMap(QCloudVODMedia::getFileId, e -> e, (a, b) -> a));
        Map<String, QCloudVODMedia> dbMap = dbList.stream()
                .filter(e -> e.getFileId() != null)
                .collect(Collectors.toMap(QCloudVODMedia::getFileId, e -> e, (a, b) -> a));

        List<QCloudVODMedia> toInsert = apiList.stream()
                .filter(e -> e.getFileId() != null && !dbMap.containsKey(e.getFileId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudVODMediaMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudVODMedia> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudVODMedia::getConfName, cloudConf.getName())
                    .in(QCloudVODMedia::getFileId, toDeleteIds)
                    .set(QCloudVODMedia::getDeleted, 1);
            qCloudVODMediaMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== VODPROCESS ====================

    public int syncVODProcess(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudVODProcess> apiList = qCloudClient.listVODProcess();
        List<QCloudVODProcess> dbList = qCloudVODProcessMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudVODProcess> apiMap = apiList.stream()
                .filter(e -> e.getFileId() != null)
                .collect(Collectors.toMap(QCloudVODProcess::getFileId, e -> e, (a, b) -> a));
        Map<String, QCloudVODProcess> dbMap = dbList.stream()
                .filter(e -> e.getFileId() != null)
                .collect(Collectors.toMap(QCloudVODProcess::getFileId, e -> e, (a, b) -> a));

        List<QCloudVODProcess> toInsert = apiList.stream()
                .filter(e -> e.getFileId() != null && !dbMap.containsKey(e.getFileId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudVODProcessMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudVODProcess> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudVODProcess::getConfName, cloudConf.getName())
                    .in(QCloudVODProcess::getFileId, toDeleteIds)
                    .set(QCloudVODProcess::getDeleted, 1);
            qCloudVODProcessMapper.update(null, uw);
        }
        return insertCount;
    }

    public int syncMps(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudMps> apiList = qCloudClient.listMps();
        List<QCloudMps> dbList = qCloudMpsMapper.selectByConfName(cloudConf.getName());
        Map<String, QCloudMps> apiMap = apiList.stream().filter(e -> e.getTaskId() != null).collect(Collectors.toMap(QCloudMps::getTaskId, e -> e, (a, b) -> a));
        Map<String, QCloudMps> dbMap = dbList.stream().filter(e -> e.getTaskId() != null).collect(Collectors.toMap(QCloudMps::getTaskId, e -> e, (a, b) -> a));
        List<QCloudMps> toInsert = apiList.stream().filter(e -> e.getTaskId() != null && !dbMap.containsKey(e.getTaskId())).collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream().filter(id -> !apiMap.containsKey(id)).collect(Collectors.toSet());
        int insertCount = 0;
        if (!toInsert.isEmpty()) insertCount = qCloudMpsMapper.insertBatch(toInsert);
        if (!toDeleteIds.isEmpty()) { LambdaUpdateWrapper<QCloudMps> uw = new LambdaUpdateWrapper<>(); uw.eq(QCloudMps::getConfName, cloudConf.getName()).in(QCloudMps::getTaskId, toDeleteIds).set(QCloudMps::getDeleted, 1); qCloudMpsMapper.update(null, uw); }
        return insertCount;
    }

    public int syncEnhanceMedia(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudEnhanceMedia> apiList = qCloudClient.listEnhanceMedia();
        List<QCloudEnhanceMedia> dbList = qCloudEnhanceMediaMapper.selectByConfName(cloudConf.getName());
        Map<String, QCloudEnhanceMedia> apiMap = apiList.stream().filter(e -> e.getTaskId() != null).collect(Collectors.toMap(QCloudEnhanceMedia::getTaskId, e -> e, (a, b) -> a));
        Map<String, QCloudEnhanceMedia> dbMap = dbList.stream().filter(e -> e.getTaskId() != null).collect(Collectors.toMap(QCloudEnhanceMedia::getTaskId, e -> e, (a, b) -> a));
        List<QCloudEnhanceMedia> toInsert = apiList.stream().filter(e -> e.getTaskId() != null && !dbMap.containsKey(e.getTaskId())).collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream().filter(id -> !apiMap.containsKey(id)).collect(Collectors.toSet());
        int insertCount = 0;
        if (!toInsert.isEmpty()) insertCount = qCloudEnhanceMediaMapper.insertBatch(toInsert);
        if (!toDeleteIds.isEmpty()) { LambdaUpdateWrapper<QCloudEnhanceMedia> uw = new LambdaUpdateWrapper<>(); uw.eq(QCloudEnhanceMedia::getConfName, cloudConf.getName()).in(QCloudEnhanceMedia::getTaskId, toDeleteIds).set(QCloudEnhanceMedia::getDeleted, 1); qCloudEnhanceMediaMapper.update(null, uw); }
        return insertCount;
    }
}
