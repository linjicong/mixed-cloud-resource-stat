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
 * 腾讯云Compute Part2资源同步服务
 * 包含以下资源类型的同步方法: CvmDedicated, HpcCluster, HpcPlatform, Desktop, Ecm
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Service
public class QCloudComputeServicePart2 {

    @Resource
    private QCloudCvmDedicatedMapper qCloudCvmDedicatedMapper;
    @Resource
    private QCloudDesktopMapper qCloudDesktopMapper;
    @Resource
    private QCloudEcmMapper qCloudEcmMapper;
    @Resource
    private QCloudHpcClusterMapper qCloudHpcClusterMapper;
    @Resource
    private QCloudHpcPlatformMapper qCloudHpcPlatformMapper;

    public int syncCvmDedicated(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudCvmDedicated> apiList = qCloudClient.listCvmDedicated();
        List<QCloudCvmDedicated> dbList = qCloudCvmDedicatedMapper.selectByConfName(cloudConf.getName());
        Map<String, QCloudCvmDedicated> apiMap = apiList.stream().filter(e -> e.getInstanceId() != null).collect(Collectors.toMap(QCloudCvmDedicated::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudCvmDedicated> dbMap = dbList.stream().filter(e -> e.getInstanceId() != null).collect(Collectors.toMap(QCloudCvmDedicated::getInstanceId, e -> e, (a, b) -> a));
        List<QCloudCvmDedicated> toInsert = apiList.stream().filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId())).collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream().filter(id -> !apiMap.containsKey(id)).collect(Collectors.toSet());
        int insertCount = 0;
        if (!toInsert.isEmpty()) insertCount = qCloudCvmDedicatedMapper.insertBatch(toInsert);
        if (!toDeleteIds.isEmpty()) { LambdaUpdateWrapper<QCloudCvmDedicated> uw = new LambdaUpdateWrapper<>(); uw.eq(QCloudCvmDedicated::getConfName, cloudConf.getName()).in(QCloudCvmDedicated::getInstanceId, toDeleteIds).set(QCloudCvmDedicated::getDeleted, 1); qCloudCvmDedicatedMapper.update(null, uw); }
        return insertCount;
    }

    public int syncHpcCluster(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudHpcCluster> apiList = qCloudClient.listHpcCluster();
        List<QCloudHpcCluster> dbList = qCloudHpcClusterMapper.selectByConfName(cloudConf.getName());
        Map<String, QCloudHpcCluster> apiMap = apiList.stream().filter(e -> e.getClusterId() != null).collect(Collectors.toMap(QCloudHpcCluster::getClusterId, e -> e, (a, b) -> a));
        Map<String, QCloudHpcCluster> dbMap = dbList.stream().filter(e -> e.getClusterId() != null).collect(Collectors.toMap(QCloudHpcCluster::getClusterId, e -> e, (a, b) -> a));
        List<QCloudHpcCluster> toInsert = apiList.stream().filter(e -> e.getClusterId() != null && !dbMap.containsKey(e.getClusterId())).collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream().filter(id -> !apiMap.containsKey(id)).collect(Collectors.toSet());
        int insertCount = 0;
        if (!toInsert.isEmpty()) insertCount = qCloudHpcClusterMapper.insertBatch(toInsert);
        if (!toDeleteIds.isEmpty()) { LambdaUpdateWrapper<QCloudHpcCluster> uw = new LambdaUpdateWrapper<>(); uw.eq(QCloudHpcCluster::getConfName, cloudConf.getName()).in(QCloudHpcCluster::getClusterId, toDeleteIds).set(QCloudHpcCluster::getDeleted, 1); qCloudHpcClusterMapper.update(null, uw); }
        return insertCount;
    }

    public int syncHpcPlatform(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudHpcPlatform> apiList = qCloudClient.listHpcPlatform();
        List<QCloudHpcPlatform> dbList = qCloudHpcPlatformMapper.selectByConfName(cloudConf.getName());
        Map<String, QCloudHpcPlatform> apiMap = apiList.stream().filter(e -> e.getClusterId() != null).collect(Collectors.toMap(QCloudHpcPlatform::getClusterId, e -> e, (a, b) -> a));
        Map<String, QCloudHpcPlatform> dbMap = dbList.stream().filter(e -> e.getClusterId() != null).collect(Collectors.toMap(QCloudHpcPlatform::getClusterId, e -> e, (a, b) -> a));
        List<QCloudHpcPlatform> toInsert = apiList.stream().filter(e -> e.getClusterId() != null && !dbMap.containsKey(e.getClusterId())).collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream().filter(id -> !apiMap.containsKey(id)).collect(Collectors.toSet());
        int insertCount = 0;
        if (!toInsert.isEmpty()) insertCount = qCloudHpcPlatformMapper.insertBatch(toInsert);
        if (!toDeleteIds.isEmpty()) { LambdaUpdateWrapper<QCloudHpcPlatform> uw = new LambdaUpdateWrapper<>(); uw.eq(QCloudHpcPlatform::getConfName, cloudConf.getName()).in(QCloudHpcPlatform::getClusterId, toDeleteIds).set(QCloudHpcPlatform::getDeleted, 1); qCloudHpcPlatformMapper.update(null, uw); }
        return insertCount;
    }

    public int syncDesktop(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudDesktop> apiList = qCloudClient.listDesktop();
        List<QCloudDesktop> dbList = qCloudDesktopMapper.selectByConfName(cloudConf.getName());
        Map<String, QCloudDesktop> apiMap = apiList.stream().filter(e -> e.getDesktopId() != null).collect(Collectors.toMap(QCloudDesktop::getDesktopId, e -> e, (a, b) -> a));
        Map<String, QCloudDesktop> dbMap = dbList.stream().filter(e -> e.getDesktopId() != null).collect(Collectors.toMap(QCloudDesktop::getDesktopId, e -> e, (a, b) -> a));
        List<QCloudDesktop> toInsert = apiList.stream().filter(e -> e.getDesktopId() != null && !dbMap.containsKey(e.getDesktopId())).collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream().filter(id -> !apiMap.containsKey(id)).collect(Collectors.toSet());
        int insertCount = 0;
        if (!toInsert.isEmpty()) insertCount = qCloudDesktopMapper.insertBatch(toInsert);
        if (!toDeleteIds.isEmpty()) { LambdaUpdateWrapper<QCloudDesktop> uw = new LambdaUpdateWrapper<>(); uw.eq(QCloudDesktop::getConfName, cloudConf.getName()).in(QCloudDesktop::getDesktopId, toDeleteIds).set(QCloudDesktop::getDeleted, 1); qCloudDesktopMapper.update(null, uw); }
        return insertCount;
    }

    public int syncEcm(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudEcm> apiList = qCloudClient.listEcm();
        List<QCloudEcm> dbList = qCloudEcmMapper.selectByConfName(cloudConf.getName());
        Map<String, QCloudEcm> apiMap = apiList.stream().filter(e -> e.getInstanceId() != null).collect(Collectors.toMap(QCloudEcm::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudEcm> dbMap = dbList.stream().filter(e -> e.getInstanceId() != null).collect(Collectors.toMap(QCloudEcm::getInstanceId, e -> e, (a, b) -> a));
        List<QCloudEcm> toInsert = apiList.stream().filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId())).collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream().filter(id -> !apiMap.containsKey(id)).collect(Collectors.toSet());
        int insertCount = 0;
        if (!toInsert.isEmpty()) insertCount = qCloudEcmMapper.insertBatch(toInsert);
        if (!toDeleteIds.isEmpty()) { LambdaUpdateWrapper<QCloudEcm> uw = new LambdaUpdateWrapper<>(); uw.eq(QCloudEcm::getConfName, cloudConf.getName()).in(QCloudEcm::getInstanceId, toDeleteIds).set(QCloudEcm::getDeleted, 1); qCloudEcmMapper.update(null, uw); }
        return insertCount;
    }
}
