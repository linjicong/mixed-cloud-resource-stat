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
 * 腾讯云Security Part1资源同步服务
 * 包含以下资源类型的同步方法: CACert, CAPTCHA, CloudHSM, ContentSafe, DDoS, DataAudit, DataSafeGov, DeviceSafety, ExposedMgr, HSM...
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Service
public class QCloudSecurityServicePart1 {

    @Resource
    private QCloudCACertMapper qCloudCACertMapper;
    @Resource
    private QCloudCAPTCHAMapper qCloudCAPTCHAMapper;
    @Resource
    private QCloudCloudHSMMapper qCloudCloudHSMMapper;
    @Resource
    private QCloudContentSafeMapper qCloudContentSafeMapper;
    @Resource
    private QCloudDDoSMapper qCloudDDoSMapper;
    @Resource
    private QCloudDataAuditMapper qCloudDataAuditMapper;
    @Resource
    private QCloudDataSafeGovMapper qCloudDataSafeGovMapper;
    @Resource
    private QCloudDeviceSafetyMapper qCloudDeviceSafetyMapper;
    @Resource
    private QCloudExposedMgrMapper qCloudExposedMgrMapper;
    @Resource
    private QCloudHSMMapper qCloudHSMMapper;
    @Resource
    private QCloudKMSMapper qCloudKMSMapper;
    @Resource
    private QCloudMiniSafeMapper qCloudMiniSafeMapper;

    // ==================== CACERT ====================

    public int syncCACert(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudCACert> apiList = qCloudClient.listCACert();
        List<QCloudCACert> dbList = qCloudCACertMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudCACert> apiMap = apiList.stream()
                .filter(e -> e.getCertId() != null)
                .collect(Collectors.toMap(QCloudCACert::getCertId, e -> e, (a, b) -> a));
        Map<String, QCloudCACert> dbMap = dbList.stream()
                .filter(e -> e.getCertId() != null)
                .collect(Collectors.toMap(QCloudCACert::getCertId, e -> e, (a, b) -> a));

        List<QCloudCACert> toInsert = apiList.stream()
                .filter(e -> e.getCertId() != null && !dbMap.containsKey(e.getCertId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudCACertMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudCACert> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudCACert::getConfName, cloudConf.getName())
                    .in(QCloudCACert::getCertId, toDeleteIds)
                    .set(QCloudCACert::getDeleted, 1);
            qCloudCACertMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CAPTCHA ====================

    public int syncCAPTCHA(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudCAPTCHA> apiList = qCloudClient.listCAPTCHA();
        List<QCloudCAPTCHA> dbList = qCloudCAPTCHAMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudCAPTCHA> apiMap = apiList.stream()
                .filter(e -> e.getCaptchaName() != null)
                .collect(Collectors.toMap(QCloudCAPTCHA::getCaptchaName, e -> e, (a, b) -> a));
        Map<String, QCloudCAPTCHA> dbMap = dbList.stream()
                .filter(e -> e.getCaptchaName() != null)
                .collect(Collectors.toMap(QCloudCAPTCHA::getCaptchaName, e -> e, (a, b) -> a));

        List<QCloudCAPTCHA> toInsert = apiList.stream()
                .filter(e -> e.getCaptchaName() != null && !dbMap.containsKey(e.getCaptchaName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudCAPTCHAMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudCAPTCHA> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudCAPTCHA::getConfName, cloudConf.getName())
                    .in(QCloudCAPTCHA::getCaptchaName, toDeleteIds)
                    .set(QCloudCAPTCHA::getDeleted, 1);
            qCloudCAPTCHAMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CLOUDHSM ====================

    public int syncCloudHSM(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudCloudHSM> apiList = qCloudClient.listCloudHSM();
        List<QCloudCloudHSM> dbList = qCloudCloudHSMMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudCloudHSM> apiMap = apiList.stream()
                .filter(e -> e.getHsmId() != null)
                .collect(Collectors.toMap(QCloudCloudHSM::getHsmId, e -> e, (a, b) -> a));
        Map<String, QCloudCloudHSM> dbMap = dbList.stream()
                .filter(e -> e.getHsmId() != null)
                .collect(Collectors.toMap(QCloudCloudHSM::getHsmId, e -> e, (a, b) -> a));

        List<QCloudCloudHSM> toInsert = apiList.stream()
                .filter(e -> e.getHsmId() != null && !dbMap.containsKey(e.getHsmId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudCloudHSMMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudCloudHSM> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudCloudHSM::getConfName, cloudConf.getName())
                    .in(QCloudCloudHSM::getHsmId, toDeleteIds)
                    .set(QCloudCloudHSM::getDeleted, 1);
            qCloudCloudHSMMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CONTENTSAFE ====================

    public int syncContentSafe(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudContentSafe> apiList = qCloudClient.listContentSafe();
        List<QCloudContentSafe> dbList = qCloudContentSafeMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudContentSafe> apiMap = apiList.stream()
                .filter(e -> e.getBizType() != null)
                .collect(Collectors.toMap(QCloudContentSafe::getBizType, e -> e, (a, b) -> a));
        Map<String, QCloudContentSafe> dbMap = dbList.stream()
                .filter(e -> e.getBizType() != null)
                .collect(Collectors.toMap(QCloudContentSafe::getBizType, e -> e, (a, b) -> a));

        List<QCloudContentSafe> toInsert = apiList.stream()
                .filter(e -> e.getBizType() != null && !dbMap.containsKey(e.getBizType()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudContentSafeMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudContentSafe> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudContentSafe::getConfName, cloudConf.getName())
                    .in(QCloudContentSafe::getBizType, toDeleteIds)
                    .set(QCloudContentSafe::getDeleted, 1);
            qCloudContentSafeMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== DDOS ====================

    public int syncDDoS(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudDDoS> apiList = qCloudClient.listDDoS();
        List<QCloudDDoS> dbList = qCloudDDoSMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudDDoS> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudDDoS::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudDDoS> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudDDoS::getInstanceId, e -> e, (a, b) -> a));

        List<QCloudDDoS> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudDDoSMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudDDoS> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudDDoS::getConfName, cloudConf.getName())
                    .in(QCloudDDoS::getInstanceId, toDeleteIds)
                    .set(QCloudDDoS::getDeleted, 1);
            qCloudDDoSMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== DATAAUDIT ====================

    public int syncDataAudit(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudDataAudit> apiList = qCloudClient.listDataAudit();
        List<QCloudDataAudit> dbList = qCloudDataAuditMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudDataAudit> apiMap = apiList.stream()
                .filter(e -> e.getResourceId() != null)
                .collect(Collectors.toMap(QCloudDataAudit::getResourceId, e -> e, (a, b) -> a));
        Map<String, QCloudDataAudit> dbMap = dbList.stream()
                .filter(e -> e.getResourceId() != null)
                .collect(Collectors.toMap(QCloudDataAudit::getResourceId, e -> e, (a, b) -> a));

        List<QCloudDataAudit> toInsert = apiList.stream()
                .filter(e -> e.getResourceId() != null && !dbMap.containsKey(e.getResourceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudDataAuditMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudDataAudit> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudDataAudit::getConfName, cloudConf.getName())
                    .in(QCloudDataAudit::getResourceId, toDeleteIds)
                    .set(QCloudDataAudit::getDeleted, 1);
            qCloudDataAuditMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== DATASAFEGOV ====================

    public int syncDataSafeGov(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudDataSafeGov> apiList = qCloudClient.listDataSafeGov();
        List<QCloudDataSafeGov> dbList = qCloudDataSafeGovMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudDataSafeGov> apiMap = apiList.stream()
                .filter(e -> e.getPolicyId() != null)
                .collect(Collectors.toMap(QCloudDataSafeGov::getPolicyId, e -> e, (a, b) -> a));
        Map<String, QCloudDataSafeGov> dbMap = dbList.stream()
                .filter(e -> e.getPolicyId() != null)
                .collect(Collectors.toMap(QCloudDataSafeGov::getPolicyId, e -> e, (a, b) -> a));

        List<QCloudDataSafeGov> toInsert = apiList.stream()
                .filter(e -> e.getPolicyId() != null && !dbMap.containsKey(e.getPolicyId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudDataSafeGovMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudDataSafeGov> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudDataSafeGov::getConfName, cloudConf.getName())
                    .in(QCloudDataSafeGov::getPolicyId, toDeleteIds)
                    .set(QCloudDataSafeGov::getDeleted, 1);
            qCloudDataSafeGovMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== DEVICESAFETY ====================

    public int syncDeviceSafety(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudDeviceSafety> apiList = qCloudClient.listDeviceSafety();
        List<QCloudDeviceSafety> dbList = qCloudDeviceSafetyMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudDeviceSafety> apiMap = apiList.stream()
                .filter(e -> e.getSceneCode() != null)
                .collect(Collectors.toMap(QCloudDeviceSafety::getSceneCode, e -> e, (a, b) -> a));
        Map<String, QCloudDeviceSafety> dbMap = dbList.stream()
                .filter(e -> e.getSceneCode() != null)
                .collect(Collectors.toMap(QCloudDeviceSafety::getSceneCode, e -> e, (a, b) -> a));

        List<QCloudDeviceSafety> toInsert = apiList.stream()
                .filter(e -> e.getSceneCode() != null && !dbMap.containsKey(e.getSceneCode()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudDeviceSafetyMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudDeviceSafety> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudDeviceSafety::getConfName, cloudConf.getName())
                    .in(QCloudDeviceSafety::getSceneCode, toDeleteIds)
                    .set(QCloudDeviceSafety::getDeleted, 1);
            qCloudDeviceSafetyMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== EXPOSEDMGR ====================

    public int syncExposedMgr(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudExposedMgr> apiList = qCloudClient.listExposedMgr();
        List<QCloudExposedMgr> dbList = qCloudExposedMgrMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudExposedMgr> apiMap = apiList.stream()
                .filter(e -> e.getAssetId() != null)
                .collect(Collectors.toMap(QCloudExposedMgr::getAssetId, e -> e, (a, b) -> a));
        Map<String, QCloudExposedMgr> dbMap = dbList.stream()
                .filter(e -> e.getAssetId() != null)
                .collect(Collectors.toMap(QCloudExposedMgr::getAssetId, e -> e, (a, b) -> a));

        List<QCloudExposedMgr> toInsert = apiList.stream()
                .filter(e -> e.getAssetId() != null && !dbMap.containsKey(e.getAssetId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudExposedMgrMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudExposedMgr> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudExposedMgr::getConfName, cloudConf.getName())
                    .in(QCloudExposedMgr::getAssetId, toDeleteIds)
                    .set(QCloudExposedMgr::getDeleted, 1);
            qCloudExposedMgrMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== HSM ====================

    public int syncHSM(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudHSM> apiList = qCloudClient.listHSM();
        List<QCloudHSM> dbList = qCloudHSMMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudHSM> apiMap = apiList.stream()
                .filter(e -> e.getHsmId() != null)
                .collect(Collectors.toMap(QCloudHSM::getHsmId, e -> e, (a, b) -> a));
        Map<String, QCloudHSM> dbMap = dbList.stream()
                .filter(e -> e.getHsmId() != null)
                .collect(Collectors.toMap(QCloudHSM::getHsmId, e -> e, (a, b) -> a));

        List<QCloudHSM> toInsert = apiList.stream()
                .filter(e -> e.getHsmId() != null && !dbMap.containsKey(e.getHsmId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudHSMMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudHSM> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudHSM::getConfName, cloudConf.getName())
                    .in(QCloudHSM::getHsmId, toDeleteIds)
                    .set(QCloudHSM::getDeleted, 1);
            qCloudHSMMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== KMS ====================

    public int syncKMS(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudKMS> apiList = qCloudClient.listKMS();
        List<QCloudKMS> dbList = qCloudKMSMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudKMS> apiMap = apiList.stream()
                .filter(e -> e.getKeyId() != null)
                .collect(Collectors.toMap(QCloudKMS::getKeyId, e -> e, (a, b) -> a));
        Map<String, QCloudKMS> dbMap = dbList.stream()
                .filter(e -> e.getKeyId() != null)
                .collect(Collectors.toMap(QCloudKMS::getKeyId, e -> e, (a, b) -> a));

        List<QCloudKMS> toInsert = apiList.stream()
                .filter(e -> e.getKeyId() != null && !dbMap.containsKey(e.getKeyId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudKMSMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudKMS> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudKMS::getConfName, cloudConf.getName())
                    .in(QCloudKMS::getKeyId, toDeleteIds)
                    .set(QCloudKMS::getDeleted, 1);
            qCloudKMSMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== MINISAFE ====================

    public int syncMiniSafe(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudMiniSafe> apiList = qCloudClient.listMiniSafe();
        List<QCloudMiniSafe> dbList = qCloudMiniSafeMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudMiniSafe> apiMap = apiList.stream()
                .filter(e -> e.getAppId() != null)
                .collect(Collectors.toMap(QCloudMiniSafe::getAppId, e -> e, (a, b) -> a));
        Map<String, QCloudMiniSafe> dbMap = dbList.stream()
                .filter(e -> e.getAppId() != null)
                .collect(Collectors.toMap(QCloudMiniSafe::getAppId, e -> e, (a, b) -> a));

        List<QCloudMiniSafe> toInsert = apiList.stream()
                .filter(e -> e.getAppId() != null && !dbMap.containsKey(e.getAppId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudMiniSafeMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudMiniSafe> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudMiniSafe::getConfName, cloudConf.getName())
                    .in(QCloudMiniSafe::getAppId, toDeleteIds)
                    .set(QCloudMiniSafe::getDeleted, 1);
            qCloudMiniSafeMapper.update(null, uw);
        }
        return insertCount;
    }
}
