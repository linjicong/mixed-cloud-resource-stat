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
package com.linjicong.cloud.stat.service;

import com.linjicong.cloud.stat.util.EntityExtendContext;

import com.linjicong.cloud.stat.client.QCloudClient;
import com.linjicong.cloud.stat.dao.entity.CloudConf;
import com.linjicong.cloud.stat.service.qcloud.QCloudAIServicePart1;
import com.linjicong.cloud.stat.service.qcloud.QCloudAIServicePart2;
import com.linjicong.cloud.stat.service.qcloud.QCloudApplicationServicePart1;
import com.linjicong.cloud.stat.service.qcloud.QCloudApplicationServicePart2;
import com.linjicong.cloud.stat.service.qcloud.QCloudApplicationServicePart3;
import com.linjicong.cloud.stat.service.qcloud.QCloudApplicationServicePart4;
import com.linjicong.cloud.stat.service.qcloud.QCloudApplicationServicePart5;
import com.linjicong.cloud.stat.service.qcloud.QCloudApplicationServicePart6;
import com.linjicong.cloud.stat.service.qcloud.QCloudApplicationServicePart7;
import com.linjicong.cloud.stat.service.qcloud.QCloudComputeServicePart1;
import com.linjicong.cloud.stat.service.qcloud.QCloudComputeServicePart2;
import com.linjicong.cloud.stat.service.qcloud.QCloudDatabaseServicePart1;
import com.linjicong.cloud.stat.service.qcloud.QCloudDatabaseServicePart2;
import com.linjicong.cloud.stat.service.qcloud.QCloudDatabaseServicePart3;
import com.linjicong.cloud.stat.service.qcloud.QCloudMediaService;
import com.linjicong.cloud.stat.service.qcloud.QCloudNetworkServicePart1;
import com.linjicong.cloud.stat.service.qcloud.QCloudNetworkServicePart2;
import com.linjicong.cloud.stat.service.qcloud.QCloudNetworkServicePart3;
import com.linjicong.cloud.stat.service.qcloud.QCloudSecurityServicePart1;
import com.linjicong.cloud.stat.service.qcloud.QCloudSecurityServicePart2;
import com.linjicong.cloud.stat.service.qcloud.QCloudSecurityServicePart3;
import com.linjicong.cloud.stat.service.qcloud.QCloudStorageService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

/**
 * 腾讯云服务实现类
 * 实现腾讯云资源的同步功能，通过委托子服务类实现具体同步逻辑
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Service
public class QCloudService implements CloudService {

    // ==================== Sub-Service Injections ====================

    @Resource
    private QCloudAIServicePart1 qCloudAIServicePart1;
    @Resource
    private QCloudAIServicePart2 qCloudAIServicePart2;
    @Resource
    private QCloudApplicationServicePart1 qCloudApplicationServicePart1;
    @Resource
    private QCloudApplicationServicePart2 qCloudApplicationServicePart2;
    @Resource
    private QCloudApplicationServicePart3 qCloudApplicationServicePart3;
    @Resource
    private QCloudApplicationServicePart4 qCloudApplicationServicePart4;
    @Resource
    private QCloudApplicationServicePart5 qCloudApplicationServicePart5;
    @Resource
    private QCloudApplicationServicePart6 qCloudApplicationServicePart6;
    @Resource
    private QCloudApplicationServicePart7 qCloudApplicationServicePart7;
    @Resource
    private QCloudComputeServicePart1 qCloudComputeServicePart1;
    @Resource
    private QCloudComputeServicePart2 qCloudComputeServicePart2;
    @Resource
    private QCloudDatabaseServicePart1 qCloudDatabaseServicePart1;
    @Resource
    private QCloudDatabaseServicePart2 qCloudDatabaseServicePart2;
    @Resource
    private QCloudDatabaseServicePart3 qCloudDatabaseServicePart3;
    @Resource
    private QCloudMediaService qCloudMediaService;
    @Resource
    private QCloudNetworkServicePart1 qCloudNetworkServicePart1;
    @Resource
    private QCloudNetworkServicePart2 qCloudNetworkServicePart2;
    @Resource
    private QCloudNetworkServicePart3 qCloudNetworkServicePart3;
    @Resource
    private QCloudSecurityServicePart1 qCloudSecurityServicePart1;
    @Resource
    private QCloudSecurityServicePart2 qCloudSecurityServicePart2;
    @Resource
    private QCloudSecurityServicePart3 qCloudSecurityServicePart3;
    @Resource
    private QCloudStorageService qCloudStorageService;

    /**
     * 同步所有腾讯云资源
     *
     * @param cloudConf 云配置信息
     * @return 同步的资源总数
     */
    @Override
    public int syncEcs(CloudConf cloudConf) {
        QCloudClient qCloudClient = new QCloudClient(cloudConf);
        // Java 25: JEP-487 (Scoped Values) - 用 ScopedValue 包裹整个同步链路，自动传递上下文给 MyBatis 拦截器
        int[] totalHolder = {0};
        EntityExtendContext.runWith(qCloudClient.getEntityExtend(), () -> {
            totalHolder[0] += qCloudAIServicePart1.syncASR(qCloudClient, cloudConf);
            totalHolder[0] += qCloudAIServicePart1.syncContentRecognize(qCloudClient, cloudConf);
            totalHolder[0] += qCloudAIServicePart1.syncESign(qCloudClient, cloudConf);
            totalHolder[0] += qCloudAIServicePart1.syncEngWrite(qCloudClient, cloudConf);
            totalHolder[0] += qCloudAIServicePart1.syncFace(qCloudClient, cloudConf);
            totalHolder[0] += qCloudAIServicePart1.syncFaceFusion(qCloudClient, cloudConf);
            totalHolder[0] += qCloudAIServicePart1.syncFaceMakeup(qCloudClient, cloudConf);
            totalHolder[0] += qCloudAIServicePart1.syncFaceSwap(qCloudClient, cloudConf);
            totalHolder[0] += qCloudAIServicePart1.syncImageProcess2(qCloudClient, cloudConf);
            totalHolder[0] += qCloudAIServicePart1.syncImageSearch(qCloudClient, cloudConf);
            totalHolder[0] += qCloudAIServicePart1.syncKnowledgeEngine(qCloudClient, cloudConf);
            totalHolder[0] += qCloudAIServicePart1.syncMathGrade(qCloudClient, cloudConf);
            totalHolder[0] += qCloudAIServicePart2.syncNMT(qCloudClient, cloudConf);
            totalHolder[0] += qCloudAIServicePart2.syncOCR(qCloudClient, cloudConf);
            totalHolder[0] += qCloudAIServicePart2.syncSpokenEval(qCloudClient, cloudConf);
            totalHolder[0] += qCloudAIServicePart2.syncTI(qCloudClient, cloudConf);
            totalHolder[0] += qCloudAIServicePart2.syncTIHai(qCloudClient, cloudConf);
            totalHolder[0] += qCloudAIServicePart2.syncTTS(qCloudClient, cloudConf);
            totalHolder[0] += qCloudAIServicePart2.syncVoiceClone(qCloudClient, cloudConf);
            totalHolder[0] += qCloudAIServicePart2.syncSmartMedia(qCloudClient, cloudConf);
            totalHolder[0] += qCloudAIServicePart2.syncMediaAi(qCloudClient, cloudConf);
            totalHolder[0] += qCloudApplicationServicePart1.syncAPIGW(qCloudClient, cloudConf);
            totalHolder[0] += qCloudApplicationServicePart1.syncAgentGW(qCloudClient, cloudConf);
            totalHolder[0] += qCloudApplicationServicePart1.syncAgentPlatform(qCloudClient, cloudConf);
            totalHolder[0] += qCloudApplicationServicePart1.syncAudit(qCloudClient, cloudConf);
            totalHolder[0] += qCloudApplicationServicePart1.syncBI(qCloudClient, cloudConf);
            totalHolder[0] += qCloudApplicationServicePart1.syncBastion(qCloudClient, cloudConf);
            totalHolder[0] += qCloudApplicationServicePart1.syncBizProcess(qCloudClient, cloudConf);
            totalHolder[0] += qCloudApplicationServicePart1.syncCHC(qCloudClient, cloudConf);
            totalHolder[0] += qCloudApplicationServicePart1.syncCLS(qCloudClient, cloudConf);
            totalHolder[0] += qCloudApplicationServicePart1.syncCSP(qCloudClient, cloudConf);
            totalHolder[0] += qCloudApplicationServicePart1.syncCSPGateway(qCloudClient, cloudConf);
            totalHolder[0] += qCloudApplicationServicePart1.syncCWP(qCloudClient, cloudConf);
            totalHolder[0] += qCloudApplicationServicePart2.syncCWP3(qCloudClient, cloudConf);
            totalHolder[0] += qCloudApplicationServicePart2.syncCloudBase(qCloudClient, cloudConf);
            totalHolder[0] += qCloudApplicationServicePart2.syncCloudContact(qCloudClient, cloudConf);
            totalHolder[0] += qCloudApplicationServicePart2.syncCloudPhone(qCloudClient, cloudConf);
        });
        return totalHolder[0];

    }
}
