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
 * 腾讯云Application Part1资源同步服务
 * 包含以下资源类型的同步方法: APIGW, AgentGW, AgentPlatform, Audit, BI, Bastion, BizProcess, CHC, CLS, CSP...
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Service
public class QCloudApplicationServicePart1 {

    @Resource
    private QCloudAPIGWMapper qCloudAPIGWMapper;
    @Resource
    private QCloudAgentGWMapper qCloudAgentGWMapper;
    @Resource
    private QCloudAgentPlatformMapper qCloudAgentPlatformMapper;
    @Resource
    private QCloudAuditMapper qCloudAuditMapper;
    @Resource
    private QCloudBIMapper qCloudBIMapper;
    @Resource
    private QCloudBastionMapper qCloudBastionMapper;
    @Resource
    private QCloudBizProcessMapper qCloudBizProcessMapper;
    @Resource
    private QCloudCHCMapper qCloudCHCMapper;
    @Resource
    private QCloudCLSMapper qCloudCLSMapper;
    @Resource
    private QCloudCSPGatewayMapper qCloudCSPGatewayMapper;
    @Resource
    private QCloudCSPMapper qCloudCSPMapper;
    @Resource
    private QCloudCWPMapper qCloudCWPMapper;

    // ==================== APIGW ====================

    public int syncAPIGW(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudAPIGW> apiList = qCloudClient.listAPIGW();
        List<QCloudAPIGW> dbList = qCloudAPIGWMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudAPIGW> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudAPIGW::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudAPIGW> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudAPIGW::getInstanceId, e -> e, (a, b) -> a));

        List<QCloudAPIGW> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudAPIGWMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudAPIGW> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudAPIGW::getConfName, cloudConf.getName())
                    .in(QCloudAPIGW::getInstanceId, toDeleteIds)
                    .set(QCloudAPIGW::getDeleted, 1);
            qCloudAPIGWMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== AGENTGW ====================

    public int syncAgentGW(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudAgentGW> apiList = qCloudClient.listAgentGW();
        List<QCloudAgentGW> dbList = qCloudAgentGWMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudAgentGW> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudAgentGW::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudAgentGW> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudAgentGW::getInstanceId, e -> e, (a, b) -> a));

        List<QCloudAgentGW> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudAgentGWMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudAgentGW> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudAgentGW::getConfName, cloudConf.getName())
                    .in(QCloudAgentGW::getInstanceId, toDeleteIds)
                    .set(QCloudAgentGW::getDeleted, 1);
            qCloudAgentGWMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== AGENTPLATFORM ====================

    public int syncAgentPlatform(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudAgentPlatform> apiList = qCloudClient.listAgentPlatform();
        List<QCloudAgentPlatform> dbList = qCloudAgentPlatformMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudAgentPlatform> apiMap = apiList.stream()
                .filter(e -> e.getAgentId() != null)
                .collect(Collectors.toMap(QCloudAgentPlatform::getAgentId, e -> e, (a, b) -> a));
        Map<String, QCloudAgentPlatform> dbMap = dbList.stream()
                .filter(e -> e.getAgentId() != null)
                .collect(Collectors.toMap(QCloudAgentPlatform::getAgentId, e -> e, (a, b) -> a));

        List<QCloudAgentPlatform> toInsert = apiList.stream()
                .filter(e -> e.getAgentId() != null && !dbMap.containsKey(e.getAgentId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudAgentPlatformMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudAgentPlatform> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudAgentPlatform::getConfName, cloudConf.getName())
                    .in(QCloudAgentPlatform::getAgentId, toDeleteIds)
                    .set(QCloudAgentPlatform::getDeleted, 1);
            qCloudAgentPlatformMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== AUDIT ====================

    public int syncAudit(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudAudit> apiList = qCloudClient.listAudit();
        List<QCloudAudit> dbList = qCloudAuditMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudAudit> apiMap = apiList.stream()
                .filter(e -> e.getAuditName() != null)
                .collect(Collectors.toMap(QCloudAudit::getAuditName, e -> e, (a, b) -> a));
        Map<String, QCloudAudit> dbMap = dbList.stream()
                .filter(e -> e.getAuditName() != null)
                .collect(Collectors.toMap(QCloudAudit::getAuditName, e -> e, (a, b) -> a));

        List<QCloudAudit> toInsert = apiList.stream()
                .filter(e -> e.getAuditName() != null && !dbMap.containsKey(e.getAuditName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudAuditMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudAudit> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudAudit::getConfName, cloudConf.getName())
                    .in(QCloudAudit::getAuditName, toDeleteIds)
                    .set(QCloudAudit::getDeleted, 1);
            qCloudAuditMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== BI ====================

    public int syncBI(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudBI> apiList = qCloudClient.listBI();
        List<QCloudBI> dbList = qCloudBIMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudBI> apiMap = apiList.stream()
                .filter(e -> e.getPageId() != null)
                .collect(Collectors.toMap(QCloudBI::getPageId, e -> e, (a, b) -> a));
        Map<String, QCloudBI> dbMap = dbList.stream()
                .filter(e -> e.getPageId() != null)
                .collect(Collectors.toMap(QCloudBI::getPageId, e -> e, (a, b) -> a));

        List<QCloudBI> toInsert = apiList.stream()
                .filter(e -> e.getPageId() != null && !dbMap.containsKey(e.getPageId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudBIMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudBI> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudBI::getConfName, cloudConf.getName())
                    .in(QCloudBI::getPageId, toDeleteIds)
                    .set(QCloudBI::getDeleted, 1);
            qCloudBIMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== BASTION ====================

    public int syncBastion(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudBastion> apiList = qCloudClient.listBastion();
        List<QCloudBastion> dbList = qCloudBastionMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudBastion> apiMap = apiList.stream()
                .filter(e -> e.getResourceId() != null)
                .collect(Collectors.toMap(QCloudBastion::getResourceId, e -> e, (a, b) -> a));
        Map<String, QCloudBastion> dbMap = dbList.stream()
                .filter(e -> e.getResourceId() != null)
                .collect(Collectors.toMap(QCloudBastion::getResourceId, e -> e, (a, b) -> a));

        List<QCloudBastion> toInsert = apiList.stream()
                .filter(e -> e.getResourceId() != null && !dbMap.containsKey(e.getResourceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudBastionMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudBastion> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudBastion::getConfName, cloudConf.getName())
                    .in(QCloudBastion::getResourceId, toDeleteIds)
                    .set(QCloudBastion::getDeleted, 1);
            qCloudBastionMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== BIZPROCESS ====================

    public int syncBizProcess(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudBizProcess> apiList = qCloudClient.listBizProcess();
        List<QCloudBizProcess> dbList = qCloudBizProcessMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudBizProcess> apiMap = apiList.stream()
                .filter(e -> e.getProcessId() != null)
                .collect(Collectors.toMap(QCloudBizProcess::getProcessId, e -> e, (a, b) -> a));
        Map<String, QCloudBizProcess> dbMap = dbList.stream()
                .filter(e -> e.getProcessId() != null)
                .collect(Collectors.toMap(QCloudBizProcess::getProcessId, e -> e, (a, b) -> a));

        List<QCloudBizProcess> toInsert = apiList.stream()
                .filter(e -> e.getProcessId() != null && !dbMap.containsKey(e.getProcessId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudBizProcessMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudBizProcess> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudBizProcess::getConfName, cloudConf.getName())
                    .in(QCloudBizProcess::getProcessId, toDeleteIds)
                    .set(QCloudBizProcess::getDeleted, 1);
            qCloudBizProcessMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CHC ====================

    public int syncCHC(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudCHC> apiList = qCloudClient.listCHC();
        List<QCloudCHC> dbList = qCloudCHCMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudCHC> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudCHC::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudCHC> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudCHC::getInstanceId, e -> e, (a, b) -> a));

        List<QCloudCHC> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudCHCMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudCHC> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudCHC::getConfName, cloudConf.getName())
                    .in(QCloudCHC::getInstanceId, toDeleteIds)
                    .set(QCloudCHC::getDeleted, 1);
            qCloudCHCMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CLS ====================

    public int syncCLS(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudCLS> apiList = qCloudClient.listCLS();
        List<QCloudCLS> dbList = qCloudCLSMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudCLS> apiMap = apiList.stream()
                .filter(e -> e.getTopicId() != null)
                .collect(Collectors.toMap(QCloudCLS::getTopicId, e -> e, (a, b) -> a));
        Map<String, QCloudCLS> dbMap = dbList.stream()
                .filter(e -> e.getTopicId() != null)
                .collect(Collectors.toMap(QCloudCLS::getTopicId, e -> e, (a, b) -> a));

        List<QCloudCLS> toInsert = apiList.stream()
                .filter(e -> e.getTopicId() != null && !dbMap.containsKey(e.getTopicId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudCLSMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudCLS> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudCLS::getConfName, cloudConf.getName())
                    .in(QCloudCLS::getTopicId, toDeleteIds)
                    .set(QCloudCLS::getDeleted, 1);
            qCloudCLSMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CSP ====================

    public int syncCSP(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudCSP> apiList = qCloudClient.listCSP();
        List<QCloudCSP> dbList = qCloudCSPMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudCSP> apiMap = apiList.stream()
                .filter(e -> e.getName() != null)
                .collect(Collectors.toMap(QCloudCSP::getName, e -> e, (a, b) -> a));
        Map<String, QCloudCSP> dbMap = dbList.stream()
                .filter(e -> e.getName() != null)
                .collect(Collectors.toMap(QCloudCSP::getName, e -> e, (a, b) -> a));

        List<QCloudCSP> toInsert = apiList.stream()
                .filter(e -> e.getName() != null && !dbMap.containsKey(e.getName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudCSPMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudCSP> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudCSP::getConfName, cloudConf.getName())
                    .in(QCloudCSP::getName, toDeleteIds)
                    .set(QCloudCSP::getDeleted, 1);
            qCloudCSPMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CSPGATEWAY ====================

    public int syncCSPGateway(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudCSPGateway> apiList = qCloudClient.listCSPGateway();
        List<QCloudCSPGateway> dbList = qCloudCSPGatewayMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudCSPGateway> apiMap = apiList.stream()
                .filter(e -> e.getGatewayId() != null)
                .collect(Collectors.toMap(QCloudCSPGateway::getGatewayId, e -> e, (a, b) -> a));
        Map<String, QCloudCSPGateway> dbMap = dbList.stream()
                .filter(e -> e.getGatewayId() != null)
                .collect(Collectors.toMap(QCloudCSPGateway::getGatewayId, e -> e, (a, b) -> a));

        List<QCloudCSPGateway> toInsert = apiList.stream()
                .filter(e -> e.getGatewayId() != null && !dbMap.containsKey(e.getGatewayId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudCSPGatewayMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudCSPGateway> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudCSPGateway::getConfName, cloudConf.getName())
                    .in(QCloudCSPGateway::getGatewayId, toDeleteIds)
                    .set(QCloudCSPGateway::getDeleted, 1);
            qCloudCSPGatewayMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CWP ====================

    public int syncCWP(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudCWP> apiList = qCloudClient.listCWP();
        List<QCloudCWP> dbList = qCloudCWPMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudCWP> apiMap = apiList.stream()
                .filter(e -> e.getQuuid() != null)
                .collect(Collectors.toMap(QCloudCWP::getQuuid, e -> e, (a, b) -> a));
        Map<String, QCloudCWP> dbMap = dbList.stream()
                .filter(e -> e.getQuuid() != null)
                .collect(Collectors.toMap(QCloudCWP::getQuuid, e -> e, (a, b) -> a));

        List<QCloudCWP> toInsert = apiList.stream()
                .filter(e -> e.getQuuid() != null && !dbMap.containsKey(e.getQuuid()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudCWPMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudCWP> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudCWP::getConfName, cloudConf.getName())
                    .in(QCloudCWP::getQuuid, toDeleteIds)
                    .set(QCloudCWP::getDeleted, 1);
            qCloudCWPMapper.update(null, uw);
        }
        return insertCount;
    }
}
