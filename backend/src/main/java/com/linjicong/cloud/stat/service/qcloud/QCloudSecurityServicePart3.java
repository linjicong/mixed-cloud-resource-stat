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
 * 腾讯云Security Part3资源同步服务
 * 包含以下资源类型的同步方法: SafeVideo, SecCredential, SecretMgr, SecurityGroup, VulnMgr, WAF, ThreatIntel, AntiFraud, Cfw
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Service
public class QCloudSecurityServicePart3 {

    @Resource
    private QCloudAntiFraudMapper qCloudAntiFraudMapper;
    @Resource
    private QCloudCfwMapper qCloudCfwMapper;
    @Resource
    private QCloudSafeVideoMapper qCloudSafeVideoMapper;
    @Resource
    private QCloudSecCredentialMapper qCloudSecCredentialMapper;
    @Resource
    private QCloudSecretMgrMapper qCloudSecretMgrMapper;
    @Resource
    private QCloudSecurityGroupMapper qCloudSecurityGroupMapper;
    @Resource
    private QCloudThreatIntelMapper qCloudThreatIntelMapper;
    @Resource
    private QCloudVulnMgrMapper qCloudVulnMgrMapper;
    @Resource
    private QCloudWAFMapper qCloudWAFMapper;

    // ==================== SAFEVIDEO ====================

    public int syncSafeVideo(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudSafeVideo> apiList = qCloudClient.listSafeVideo();
        List<QCloudSafeVideo> dbList = qCloudSafeVideoMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudSafeVideo> apiMap = apiList.stream()
                .filter(e -> e.getBizType() != null)
                .collect(Collectors.toMap(QCloudSafeVideo::getBizType, e -> e, (a, b) -> a));
        Map<String, QCloudSafeVideo> dbMap = dbList.stream()
                .filter(e -> e.getBizType() != null)
                .collect(Collectors.toMap(QCloudSafeVideo::getBizType, e -> e, (a, b) -> a));

        List<QCloudSafeVideo> toInsert = apiList.stream()
                .filter(e -> e.getBizType() != null && !dbMap.containsKey(e.getBizType()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudSafeVideoMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudSafeVideo> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudSafeVideo::getConfName, cloudConf.getName())
                    .in(QCloudSafeVideo::getBizType, toDeleteIds)
                    .set(QCloudSafeVideo::getDeleted, 1);
            qCloudSafeVideoMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== SECCREDENTIAL ====================

    public int syncSecCredential(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudSecCredential> apiList = qCloudClient.listSecCredential();
        List<QCloudSecCredential> dbList = qCloudSecCredentialMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudSecCredential> apiMap = apiList.stream()
                .filter(e -> e.getSecretId() != null)
                .collect(Collectors.toMap(QCloudSecCredential::getSecretId, e -> e, (a, b) -> a));
        Map<String, QCloudSecCredential> dbMap = dbList.stream()
                .filter(e -> e.getSecretId() != null)
                .collect(Collectors.toMap(QCloudSecCredential::getSecretId, e -> e, (a, b) -> a));

        List<QCloudSecCredential> toInsert = apiList.stream()
                .filter(e -> e.getSecretId() != null && !dbMap.containsKey(e.getSecretId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudSecCredentialMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudSecCredential> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudSecCredential::getConfName, cloudConf.getName())
                    .in(QCloudSecCredential::getSecretId, toDeleteIds)
                    .set(QCloudSecCredential::getDeleted, 1);
            qCloudSecCredentialMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== SECRETMGR ====================

    public int syncSecretMgr(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudSecretMgr> apiList = qCloudClient.listSecretMgr();
        List<QCloudSecretMgr> dbList = qCloudSecretMgrMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudSecretMgr> apiMap = apiList.stream()
                .filter(e -> e.getSecretName() != null)
                .collect(Collectors.toMap(QCloudSecretMgr::getSecretName, e -> e, (a, b) -> a));
        Map<String, QCloudSecretMgr> dbMap = dbList.stream()
                .filter(e -> e.getSecretName() != null)
                .collect(Collectors.toMap(QCloudSecretMgr::getSecretName, e -> e, (a, b) -> a));

        List<QCloudSecretMgr> toInsert = apiList.stream()
                .filter(e -> e.getSecretName() != null && !dbMap.containsKey(e.getSecretName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudSecretMgrMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudSecretMgr> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudSecretMgr::getConfName, cloudConf.getName())
                    .in(QCloudSecretMgr::getSecretName, toDeleteIds)
                    .set(QCloudSecretMgr::getDeleted, 1);
            qCloudSecretMgrMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== SECURITYGROUP ====================

    public int syncSecurityGroup(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudSecurityGroup> apiList = qCloudClient.listSecurityGroup();
        List<QCloudSecurityGroup> dbList = qCloudSecurityGroupMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudSecurityGroup> apiMap = apiList.stream()
                .filter(e -> e.getSecurityGroupId() != null)
                .collect(Collectors.toMap(QCloudSecurityGroup::getSecurityGroupId, e -> e, (a, b) -> a));
        Map<String, QCloudSecurityGroup> dbMap = dbList.stream()
                .filter(e -> e.getSecurityGroupId() != null)
                .collect(Collectors.toMap(QCloudSecurityGroup::getSecurityGroupId, e -> e, (a, b) -> a));

        List<QCloudSecurityGroup> toInsert = apiList.stream()
                .filter(e -> e.getSecurityGroupId() != null && !dbMap.containsKey(e.getSecurityGroupId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudSecurityGroupMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudSecurityGroup> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudSecurityGroup::getConfName, cloudConf.getName())
                    .in(QCloudSecurityGroup::getSecurityGroupId, toDeleteIds)
                    .set(QCloudSecurityGroup::getDeleted, 1);
            qCloudSecurityGroupMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== VULNMGR ====================

    public int syncVulnMgr(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudVulnMgr> apiList = qCloudClient.listVulnMgr();
        List<QCloudVulnMgr> dbList = qCloudVulnMgrMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudVulnMgr> apiMap = apiList.stream()
                .filter(e -> e.getVulnId() != null)
                .collect(Collectors.toMap(QCloudVulnMgr::getVulnId, e -> e, (a, b) -> a));
        Map<String, QCloudVulnMgr> dbMap = dbList.stream()
                .filter(e -> e.getVulnId() != null)
                .collect(Collectors.toMap(QCloudVulnMgr::getVulnId, e -> e, (a, b) -> a));

        List<QCloudVulnMgr> toInsert = apiList.stream()
                .filter(e -> e.getVulnId() != null && !dbMap.containsKey(e.getVulnId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudVulnMgrMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudVulnMgr> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudVulnMgr::getConfName, cloudConf.getName())
                    .in(QCloudVulnMgr::getVulnId, toDeleteIds)
                    .set(QCloudVulnMgr::getDeleted, 1);
            qCloudVulnMgrMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== WAF ====================

    public int syncWAF(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudWAF> apiList = qCloudClient.listWAF();
        List<QCloudWAF> dbList = qCloudWAFMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudWAF> apiMap = apiList.stream()
                .filter(e -> e.getDomain() != null)
                .collect(Collectors.toMap(QCloudWAF::getDomain, e -> e, (a, b) -> a));
        Map<String, QCloudWAF> dbMap = dbList.stream()
                .filter(e -> e.getDomain() != null)
                .collect(Collectors.toMap(QCloudWAF::getDomain, e -> e, (a, b) -> a));

        List<QCloudWAF> toInsert = apiList.stream()
                .filter(e -> e.getDomain() != null && !dbMap.containsKey(e.getDomain()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudWAFMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudWAF> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudWAF::getConfName, cloudConf.getName())
                    .in(QCloudWAF::getDomain, toDeleteIds)
                    .set(QCloudWAF::getDeleted, 1);
            qCloudWAFMapper.update(null, uw);
        }
        return insertCount;
    }

    public int syncThreatIntel(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudThreatIntel> apiList = qCloudClient.listThreatIntel();
        List<QCloudThreatIntel> dbList = qCloudThreatIntelMapper.selectByConfName(cloudConf.getName());
        Map<String, QCloudThreatIntel> apiMap = apiList.stream().filter(e -> e.getIndicatorId() != null).collect(Collectors.toMap(QCloudThreatIntel::getIndicatorId, e -> e, (a, b) -> a));
        Map<String, QCloudThreatIntel> dbMap = dbList.stream().filter(e -> e.getIndicatorId() != null).collect(Collectors.toMap(QCloudThreatIntel::getIndicatorId, e -> e, (a, b) -> a));
        List<QCloudThreatIntel> toInsert = apiList.stream().filter(e -> e.getIndicatorId() != null && !dbMap.containsKey(e.getIndicatorId())).collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream().filter(id -> !apiMap.containsKey(id)).collect(Collectors.toSet());
        int insertCount = 0;
        if (!toInsert.isEmpty()) insertCount = qCloudThreatIntelMapper.insertBatch(toInsert);
        if (!toDeleteIds.isEmpty()) { LambdaUpdateWrapper<QCloudThreatIntel> uw = new LambdaUpdateWrapper<>(); uw.eq(QCloudThreatIntel::getConfName, cloudConf.getName()).in(QCloudThreatIntel::getIndicatorId, toDeleteIds).set(QCloudThreatIntel::getDeleted, 1); qCloudThreatIntelMapper.update(null, uw); }
        return insertCount;
    }

    public int syncAntiFraud(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudAntiFraud> apiList = qCloudClient.listAntiFraud();
        List<QCloudAntiFraud> dbList = qCloudAntiFraudMapper.selectByConfName(cloudConf.getName());
        Map<String, QCloudAntiFraud> apiMap = apiList.stream().filter(e -> e.getTaskId() != null).collect(Collectors.toMap(QCloudAntiFraud::getTaskId, e -> e, (a, b) -> a));
        Map<String, QCloudAntiFraud> dbMap = dbList.stream().filter(e -> e.getTaskId() != null).collect(Collectors.toMap(QCloudAntiFraud::getTaskId, e -> e, (a, b) -> a));
        List<QCloudAntiFraud> toInsert = apiList.stream().filter(e -> e.getTaskId() != null && !dbMap.containsKey(e.getTaskId())).collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream().filter(id -> !apiMap.containsKey(id)).collect(Collectors.toSet());
        int insertCount = 0;
        if (!toInsert.isEmpty()) insertCount = qCloudAntiFraudMapper.insertBatch(toInsert);
        if (!toDeleteIds.isEmpty()) { LambdaUpdateWrapper<QCloudAntiFraud> uw = new LambdaUpdateWrapper<>(); uw.eq(QCloudAntiFraud::getConfName, cloudConf.getName()).in(QCloudAntiFraud::getTaskId, toDeleteIds).set(QCloudAntiFraud::getDeleted, 1); qCloudAntiFraudMapper.update(null, uw); }
        return insertCount;
    }

    public int syncCfw(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudCfw> apiList = qCloudClient.listCfw();
        List<QCloudCfw> dbList = qCloudCfwMapper.selectByConfName(cloudConf.getName());
        Map<String, QCloudCfw> apiMap = apiList.stream().filter(e -> e.getInstanceId() != null).collect(Collectors.toMap(QCloudCfw::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudCfw> dbMap = dbList.stream().filter(e -> e.getInstanceId() != null).collect(Collectors.toMap(QCloudCfw::getInstanceId, e -> e, (a, b) -> a));
        List<QCloudCfw> toInsert = apiList.stream().filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId())).collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream().filter(id -> !apiMap.containsKey(id)).collect(Collectors.toSet());
        int insertCount = 0;
        if (!toInsert.isEmpty()) insertCount = qCloudCfwMapper.insertBatch(toInsert);
        if (!toDeleteIds.isEmpty()) { LambdaUpdateWrapper<QCloudCfw> uw = new LambdaUpdateWrapper<>(); uw.eq(QCloudCfw::getConfName, cloudConf.getName()).in(QCloudCfw::getInstanceId, toDeleteIds).set(QCloudCfw::getDeleted, 1); qCloudCfwMapper.update(null, uw); }
        return insertCount;
    }
}
