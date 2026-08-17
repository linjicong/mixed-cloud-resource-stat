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
 * 腾讯云Compute Part1资源同步服务
 * 包含以下资源类型的同步方法: AS, BMS, Cvm, EMR, Lighthouse, NativeBuild, Scf, TCB, TKE, TSF...
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Service
public class QCloudComputeServicePart1 {

    @Resource
    private QCloudASMapper qCloudASMapper;
    @Resource
    private QCloudBMSMapper qCloudBMSMapper;
    @Resource
    private QCloudCvmMapper qCloudCvmMapper;
    @Resource
    private QCloudEMRMapper qCloudEMRMapper;
    @Resource
    private QCloudFpgaCvmMapper qCloudFpgaCvmMapper;
    @Resource
    private QCloudGpuCvmMapper qCloudGpuCvmMapper;
    @Resource
    private QCloudLighthouseMapper qCloudLighthouseMapper;
    @Resource
    private QCloudNativeBuildMapper qCloudNativeBuildMapper;
    @Resource
    private QCloudScfMapper qCloudScfMapper;
    @Resource
    private QCloudTCBMapper qCloudTCBMapper;
    @Resource
    private QCloudTKEMapper qCloudTKEMapper;
    @Resource
    private QCloudTSFMapper qCloudTSFMapper;

    // ==================== AS ====================

    public int syncAS(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudAS> apiList = qCloudClient.listAS();
        List<QCloudAS> dbList = qCloudASMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudAS> apiMap = apiList.stream()
                .filter(e -> e.getAutoScalingGroupId() != null)
                .collect(Collectors.toMap(QCloudAS::getAutoScalingGroupId, e -> e, (a, b) -> a));
        Map<String, QCloudAS> dbMap = dbList.stream()
                .filter(e -> e.getAutoScalingGroupId() != null)
                .collect(Collectors.toMap(QCloudAS::getAutoScalingGroupId, e -> e, (a, b) -> a));

        List<QCloudAS> toInsert = apiList.stream()
                .filter(e -> e.getAutoScalingGroupId() != null && !dbMap.containsKey(e.getAutoScalingGroupId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudASMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudAS> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudAS::getConfName, cloudConf.getName())
                    .in(QCloudAS::getAutoScalingGroupId, toDeleteIds)
                    .set(QCloudAS::getDeleted, 1);
            qCloudASMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== BMS ====================

    public int syncBMS(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudBMS> apiList = qCloudClient.listBMS();
        List<QCloudBMS> dbList = qCloudBMSMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudBMS> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudBMS::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudBMS> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudBMS::getInstanceId, e -> e, (a, b) -> a));

        List<QCloudBMS> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudBMSMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudBMS> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudBMS::getConfName, cloudConf.getName())
                    .in(QCloudBMS::getInstanceId, toDeleteIds)
                    .set(QCloudBMS::getDeleted, 1);
            qCloudBMSMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CVM ====================

    public int syncCvm(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudCvm> apiList = qCloudClient.listCvm();
        List<QCloudCvm> dbList = qCloudCvmMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudCvm> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudCvm::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudCvm> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudCvm::getInstanceId, e -> e, (a, b) -> a));

        List<QCloudCvm> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudCvmMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudCvm> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudCvm::getConfName, cloudConf.getName())
                    .in(QCloudCvm::getInstanceId, toDeleteIds)
                    .set(QCloudCvm::getDeleted, 1);
            qCloudCvmMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== EMR ====================

    public int syncEMR(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudEMR> apiList = qCloudClient.listEMR();
        List<QCloudEMR> dbList = qCloudEMRMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudEMR> apiMap = apiList.stream()
                .filter(e -> e.getClusterId() != null)
                .collect(Collectors.toMap(QCloudEMR::getClusterId, e -> e, (a, b) -> a));
        Map<String, QCloudEMR> dbMap = dbList.stream()
                .filter(e -> e.getClusterId() != null)
                .collect(Collectors.toMap(QCloudEMR::getClusterId, e -> e, (a, b) -> a));

        List<QCloudEMR> toInsert = apiList.stream()
                .filter(e -> e.getClusterId() != null && !dbMap.containsKey(e.getClusterId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudEMRMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudEMR> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudEMR::getConfName, cloudConf.getName())
                    .in(QCloudEMR::getClusterId, toDeleteIds)
                    .set(QCloudEMR::getDeleted, 1);
            qCloudEMRMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== LIGHTHOUSE ====================

    public int syncLighthouse(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudLighthouse> apiList = qCloudClient.listLighthouse();
        List<QCloudLighthouse> dbList = qCloudLighthouseMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudLighthouse> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudLighthouse::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudLighthouse> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudLighthouse::getInstanceId, e -> e, (a, b) -> a));

        List<QCloudLighthouse> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudLighthouseMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudLighthouse> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudLighthouse::getConfName, cloudConf.getName())
                    .in(QCloudLighthouse::getInstanceId, toDeleteIds)
                    .set(QCloudLighthouse::getDeleted, 1);
            qCloudLighthouseMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== NATIVEBUILD ====================

    public int syncNativeBuild(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudNativeBuild> apiList = qCloudClient.listNativeBuild();
        List<QCloudNativeBuild> dbList = qCloudNativeBuildMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudNativeBuild> apiMap = apiList.stream()
                .filter(e -> e.getBuildId() != null)
                .collect(Collectors.toMap(QCloudNativeBuild::getBuildId, e -> e, (a, b) -> a));
        Map<String, QCloudNativeBuild> dbMap = dbList.stream()
                .filter(e -> e.getBuildId() != null)
                .collect(Collectors.toMap(QCloudNativeBuild::getBuildId, e -> e, (a, b) -> a));

        List<QCloudNativeBuild> toInsert = apiList.stream()
                .filter(e -> e.getBuildId() != null && !dbMap.containsKey(e.getBuildId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudNativeBuildMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudNativeBuild> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudNativeBuild::getConfName, cloudConf.getName())
                    .in(QCloudNativeBuild::getBuildId, toDeleteIds)
                    .set(QCloudNativeBuild::getDeleted, 1);
            qCloudNativeBuildMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== SCF ====================

    public int syncScf(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudScf> apiList = qCloudClient.listScf();
        List<QCloudScf> dbList = qCloudScfMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudScf> apiMap = apiList.stream()
                .filter(e -> e.getFunctionName() != null)
                .collect(Collectors.toMap(QCloudScf::getFunctionName, e -> e, (a, b) -> a));
        Map<String, QCloudScf> dbMap = dbList.stream()
                .filter(e -> e.getFunctionName() != null)
                .collect(Collectors.toMap(QCloudScf::getFunctionName, e -> e, (a, b) -> a));

        List<QCloudScf> toInsert = apiList.stream()
                .filter(e -> e.getFunctionName() != null && !dbMap.containsKey(e.getFunctionName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudScfMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudScf> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudScf::getConfName, cloudConf.getName())
                    .in(QCloudScf::getFunctionName, toDeleteIds)
                    .set(QCloudScf::getDeleted, 1);
            qCloudScfMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== TCB ====================

    public int syncTCB(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudTCB> apiList = qCloudClient.listTCB();
        List<QCloudTCB> dbList = qCloudTCBMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudTCB> apiMap = apiList.stream()
                .filter(e -> e.getEnvId() != null)
                .collect(Collectors.toMap(QCloudTCB::getEnvId, e -> e, (a, b) -> a));
        Map<String, QCloudTCB> dbMap = dbList.stream()
                .filter(e -> e.getEnvId() != null)
                .collect(Collectors.toMap(QCloudTCB::getEnvId, e -> e, (a, b) -> a));

        List<QCloudTCB> toInsert = apiList.stream()
                .filter(e -> e.getEnvId() != null && !dbMap.containsKey(e.getEnvId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudTCBMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudTCB> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudTCB::getConfName, cloudConf.getName())
                    .in(QCloudTCB::getEnvId, toDeleteIds)
                    .set(QCloudTCB::getDeleted, 1);
            qCloudTCBMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== TKE ====================

    public int syncTKE(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudTKE> apiList = qCloudClient.listTKE();
        List<QCloudTKE> dbList = qCloudTKEMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudTKE> apiMap = apiList.stream()
                .filter(e -> e.getClusterId() != null)
                .collect(Collectors.toMap(QCloudTKE::getClusterId, e -> e, (a, b) -> a));
        Map<String, QCloudTKE> dbMap = dbList.stream()
                .filter(e -> e.getClusterId() != null)
                .collect(Collectors.toMap(QCloudTKE::getClusterId, e -> e, (a, b) -> a));

        List<QCloudTKE> toInsert = apiList.stream()
                .filter(e -> e.getClusterId() != null && !dbMap.containsKey(e.getClusterId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudTKEMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudTKE> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudTKE::getConfName, cloudConf.getName())
                    .in(QCloudTKE::getClusterId, toDeleteIds)
                    .set(QCloudTKE::getDeleted, 1);
            qCloudTKEMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== TSF ====================

    public int syncTSF(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudTSF> apiList = qCloudClient.listTSF();
        List<QCloudTSF> dbList = qCloudTSFMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudTSF> apiMap = apiList.stream()
                .filter(e -> e.getApplicationId() != null)
                .collect(Collectors.toMap(QCloudTSF::getApplicationId, e -> e, (a, b) -> a));
        Map<String, QCloudTSF> dbMap = dbList.stream()
                .filter(e -> e.getApplicationId() != null)
                .collect(Collectors.toMap(QCloudTSF::getApplicationId, e -> e, (a, b) -> a));

        List<QCloudTSF> toInsert = apiList.stream()
                .filter(e -> e.getApplicationId() != null && !dbMap.containsKey(e.getApplicationId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudTSFMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudTSF> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudTSF::getConfName, cloudConf.getName())
                    .in(QCloudTSF::getApplicationId, toDeleteIds)
                    .set(QCloudTSF::getDeleted, 1);
            qCloudTSFMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== 子产品 ====================

    public int syncGpuCvm(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudGpuCvm> apiList = qCloudClient.listGpuCvm();
        List<QCloudGpuCvm> dbList = qCloudGpuCvmMapper.selectByConfName(cloudConf.getName());
        Map<String, QCloudGpuCvm> apiMap = apiList.stream().filter(e -> e.getInstanceId() != null).collect(Collectors.toMap(QCloudGpuCvm::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudGpuCvm> dbMap = dbList.stream().filter(e -> e.getInstanceId() != null).collect(Collectors.toMap(QCloudGpuCvm::getInstanceId, e -> e, (a, b) -> a));
        List<QCloudGpuCvm> toInsert = apiList.stream().filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId())).collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream().filter(id -> !apiMap.containsKey(id)).collect(Collectors.toSet());
        int insertCount = 0;
        if (!toInsert.isEmpty()) insertCount = qCloudGpuCvmMapper.insertBatch(toInsert);
        if (!toDeleteIds.isEmpty()) { LambdaUpdateWrapper<QCloudGpuCvm> uw = new LambdaUpdateWrapper<>(); uw.eq(QCloudGpuCvm::getConfName, cloudConf.getName()).in(QCloudGpuCvm::getInstanceId, toDeleteIds).set(QCloudGpuCvm::getDeleted, 1); qCloudGpuCvmMapper.update(null, uw); }
        return insertCount;
    }

    public int syncFpgaCvm(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudFpgaCvm> apiList = qCloudClient.listFpgaCvm();
        List<QCloudFpgaCvm> dbList = qCloudFpgaCvmMapper.selectByConfName(cloudConf.getName());
        Map<String, QCloudFpgaCvm> apiMap = apiList.stream().filter(e -> e.getInstanceId() != null).collect(Collectors.toMap(QCloudFpgaCvm::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudFpgaCvm> dbMap = dbList.stream().filter(e -> e.getInstanceId() != null).collect(Collectors.toMap(QCloudFpgaCvm::getInstanceId, e -> e, (a, b) -> a));
        List<QCloudFpgaCvm> toInsert = apiList.stream().filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId())).collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream().filter(id -> !apiMap.containsKey(id)).collect(Collectors.toSet());
        int insertCount = 0;
        if (!toInsert.isEmpty()) insertCount = qCloudFpgaCvmMapper.insertBatch(toInsert);
        if (!toDeleteIds.isEmpty()) { LambdaUpdateWrapper<QCloudFpgaCvm> uw = new LambdaUpdateWrapper<>(); uw.eq(QCloudFpgaCvm::getConfName, cloudConf.getName()).in(QCloudFpgaCvm::getInstanceId, toDeleteIds).set(QCloudFpgaCvm::getDeleted, 1); qCloudFpgaCvmMapper.update(null, uw); }
        return insertCount;
    }
}
