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

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.linjicong.cloud.stat.client.QCloudClient;
import com.linjicong.cloud.stat.dao.entity.CloudConf;
import com.linjicong.cloud.stat.dao.entity.qcloud.*;
import com.linjicong.cloud.stat.dao.mapper.qcloud.*;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 腾讯云服务实现类
 * 实现腾讯云资源的同步功能
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Service
public class QCloudService implements CloudService {

    // ==================== Mapper Injections ====================

    @Resource
    private QCloudAPIGWMapper qCloudAPIGWMapper;
    @Resource
    private QCloudASMapper qCloudASMapper;
    @Resource
    private QCloudASRMapper qCloudASRMapper;
    @Resource
    private QCloudAccessKeyMapper qCloudAccessKeyMapper;
    @Resource
    private QCloudAccessKeyLastUsedMapper qCloudAccessKeyLastUsedMapper;
    @Resource
    private QCloudAgentGWMapper qCloudAgentGWMapper;
    @Resource
    private QCloudAgentPlatformMapper qCloudAgentPlatformMapper;
    @Resource
    private QCloudAppRenderMapper qCloudAppRenderMapper;
    @Resource
    private QCloudAuditMapper qCloudAuditMapper;
    @Resource
    private QCloudBIMapper qCloudBIMapper;
    @Resource
    private QCloudBMSMapper qCloudBMSMapper;
    @Resource
    private QCloudBastionMapper qCloudBastionMapper;
    @Resource
    private QCloudBillResourceSummaryMapper qCloudBillResourceSummaryMapper;
    @Resource
    private QCloudBizProcessMapper qCloudBizProcessMapper;
    @Resource
    private QCloudCACertMapper qCloudCACertMapper;
    @Resource
    private QCloudCAPTCHAMapper qCloudCAPTCHAMapper;
    @Resource
    private QCloudCHCMapper qCloudCHCMapper;
    @Resource
    private QCloudCHDFSMapper qCloudCHDFSMapper;
    @Resource
    private QCloudCLB_gwMapper qCloudCLB_gwMapper;
    @Resource
    private QCloudCLSMapper qCloudCLSMapper;
    @Resource
    private QCloudCSPMapper qCloudCSPMapper;
    @Resource
    private QCloudCSPGatewayMapper qCloudCSPGatewayMapper;
    @Resource
    private QCloudCSSMapper qCloudCSSMapper;
    @Resource
    private QCloudCTSDBMapper qCloudCTSDBMapper;
    @Resource
    private QCloudCWPMapper qCloudCWPMapper;
    @Resource
    private QCloudCWP3Mapper qCloudCWP3Mapper;
    @Resource
    private QCloudCbsMapper qCloudCbsMapper;
    @Resource
    private QCloudCdbMapper qCloudCdbMapper;
    @Resource
    private QCloudCdnDomainMapper qCloudCdnDomainMapper;
    @Resource
    private QCloudCfsMapper qCloudCfsMapper;
    @Resource
    private QCloudCkafkaMapper qCloudCkafkaMapper;
    @Resource
    private QCloudClbMapper qCloudClbMapper;
    @Resource
    private QCloudCloudBaseMapper qCloudCloudBaseMapper;
    @Resource
    private QCloudCloudContactMapper qCloudCloudContactMapper;
    @Resource
    private QCloudCloudHSMMapper qCloudCloudHSMMapper;
    @Resource
    private QCloudCloudPhoneMapper qCloudCloudPhoneMapper;
    @Resource
    private QCloudCloudStudioMapper qCloudCloudStudioMapper;
    @Resource
    private QCloudCmqMapper qCloudCmqMapper;
    @Resource
    private QCloudCodingDevopsMapper qCloudCodingDevopsMapper;
    @Resource
    private QCloudConfigMapper qCloudConfigMapper;
    @Resource
    private QCloudContentRecognizeMapper qCloudContentRecognizeMapper;
    @Resource
    private QCloudContentSafeMapper qCloudContentSafeMapper;
    @Resource
    private QCloudControlCenterMapper qCloudControlCenterMapper;
    @Resource
    private QCloudCosMapper qCloudCosMapper;
    @Resource
    private QCloudCvmMapper qCloudCvmMapper;
    @Resource
    private QCloudCynosDBMapper qCloudCynosDBMapper;
    @Resource
    private QCloudDCMapper qCloudDCMapper;
    @Resource
    private QCloudDCDBMapper qCloudDCDBMapper;
    @Resource
    private QCloudDDoSMapper qCloudDDoSMapper;
    @Resource
    private QCloudDLCMapper qCloudDLCMapper;
    @Resource
    private QCloudDNSPrivateMapper qCloudDNSPrivateMapper;
    @Resource
    private QCloudDNSSecMapper qCloudDNSSecMapper;
    @Resource
    private QCloudDataAuditMapper qCloudDataAuditMapper;
    @Resource
    private QCloudDataSafeGovMapper qCloudDataSafeGovMapper;
    @Resource
    private QCloudDeviceSafetyMapper qCloudDeviceSafetyMapper;
    @Resource
    private QCloudDistIDMapper qCloudDistIDMapper;
    @Resource
    private QCloudDnsDomainMapper qCloudDnsDomainMapper;
    @Resource
    private QCloudDocProcessMapper qCloudDocProcessMapper;
    @Resource
    private QCloudDocsMapper qCloudDocsMapper;
    @Resource
    private QCloudDomainMapper qCloudDomainMapper;
    @Resource
    private QCloudDomainRegMapper qCloudDomainRegMapper;
    @Resource
    private QCloudEMRMapper qCloudEMRMapper;
    @Resource
    private QCloudEOMapper qCloudEOMapper;
    @Resource
    private QCloudESMapper qCloudESMapper;
    @Resource
    private QCloudESignMapper qCloudESignMapper;
    @Resource
    private QCloudEipMapper qCloudEipMapper;
    @Resource
    private QCloudEngWriteMapper qCloudEngWriteMapper;
    @Resource
    private QCloudEventBusMapper qCloudEventBusMapper;
    @Resource
    private QCloudExposedMgrMapper qCloudExposedMgrMapper;
    @Resource
    private QCloudFaceMapper qCloudFaceMapper;
    @Resource
    private QCloudFaceDeformMapper qCloudFaceDeformMapper;
    @Resource
    private QCloudFaceFusionMapper qCloudFaceFusionMapper;
    @Resource
    private QCloudFaceMakeupMapper qCloudFaceMakeupMapper;
    @Resource
    private QCloudFaceSwapMapper qCloudFaceSwapMapper;
    @Resource
    private QCloudGHPhoneMapper qCloudGHPhoneMapper;
    @Resource
    private QCloudGSEMapper qCloudGSEMapper;
    @Resource
    private QCloudGTMMapper qCloudGTMMapper;
    @Resource
    private QCloudGaapMapper qCloudGaapMapper;
    @Resource
    private QCloudGameAntiACEMapper qCloudGameAntiACEMapper;
    @Resource
    private QCloudGameDBMapper qCloudGameDBMapper;
    @Resource
    private QCloudGameServerMapper qCloudGameServerMapper;
    @Resource
    private QCloudGameVoiceMapper qCloudGameVoiceMapper;
    @Resource
    private QCloudHBaseMapper qCloudHBaseMapper;
    @Resource
    private QCloudHSMMapper qCloudHSMMapper;
    @Resource
    private QCloudHealthDashMapper qCloudHealthDashMapper;
    @Resource
    private QCloudHealthOmicsMapper qCloudHealthOmicsMapper;
    @Resource
    private QCloudHealthReport2Mapper qCloudHealthReport2Mapper;
    @Resource
    private QCloudICPBeianMapper qCloudICPBeianMapper;
    @Resource
    private QCloudIOAMapper qCloudIOAMapper;
    @Resource
    private QCloudImageProcess2Mapper qCloudImageProcess2Mapper;
    @Resource
    private QCloudImageSearchMapper qCloudImageSearchMapper;
    @Resource
    private QCloudIoTMapper qCloudIoTMapper;
    @Resource
    private QCloudIoTDeviceMapper qCloudIoTDeviceMapper;
    @Resource
    private QCloudIoTHubMapper qCloudIoTHubMapper;
    @Resource
    private QCloudKMSMapper qCloudKMSMapper;
    @Resource
    private QCloudKeeWiDBMapper qCloudKeeWiDBMapper;
    @Resource
    private QCloudKnowledgeEngineMapper qCloudKnowledgeEngineMapper;
    @Resource
    private QCloudLighthouseMapper qCloudLighthouseMapper;
    @Resource
    private QCloudLiveMapper qCloudLiveMapper;
    @Resource
    private QCloudLive2Mapper qCloudLive2Mapper;
    @Resource
    private QCloudMailMapper qCloudMailMapper;
    @Resource
    private QCloudMallTrafficMapper qCloudMallTrafficMapper;
    @Resource
    private QCloudMariaDbMapper qCloudMariaDbMapper;
    @Resource
    private QCloudMathGradeMapper qCloudMathGradeMapper;
    @Resource
    private QCloudMediaAssetMapper qCloudMediaAssetMapper;
    @Resource
    private QCloudMeetingMapper qCloudMeetingMapper;
    @Resource
    private QCloudMemcachedMapper qCloudMemcachedMapper;
    @Resource
    private QCloudMicroWedaMapper qCloudMicroWedaMapper;
    @Resource
    private QCloudMiniSafeMapper qCloudMiniSafeMapper;
    @Resource
    private QCloudMongoDB_CKafkaMapper qCloudMongoDB_CKafkaMapper;
    @Resource
    private QCloudMongoDbMapper qCloudMongoDbMapper;
    @Resource
    private QCloudMonitorMapper qCloudMonitorMapper;
    @Resource
    private QCloudNMTMapper qCloudNMTMapper;
    @Resource
    private QCloudNatGatewayMapper qCloudNatGatewayMapper;
    @Resource
    private QCloudNativeBuildMapper qCloudNativeBuildMapper;
    @Resource
    private QCloudOCRMapper qCloudOCRMapper;
    @Resource
    private QCloudOceanusMapper qCloudOceanusMapper;
    @Resource
    private QCloudOrgMapper qCloudOrgMapper;
    @Resource
    private QCloudPenTestMapper qCloudPenTestMapper;
    @Resource
    private QCloudPostgresqlMapper qCloudPostgresqlMapper;
    @Resource
    private QCloudPrivDNSMapper qCloudPrivDNSMapper;
    @Resource
    private QCloudRTIEduMapper qCloudRTIEduMapper;
    @Resource
    private QCloudRTIIndustrialMapper qCloudRTIIndustrialMapper;
    @Resource
    private QCloudRabbitMQMapper qCloudRabbitMQMapper;
    @Resource
    private QCloudRedisMapper qCloudRedisMapper;
    @Resource
    private QCloudRegionMgrMapper qCloudRegionMgrMapper;
    @Resource
    private QCloudResourceTagMapper qCloudResourceTagMapper;
    @Resource
    private QCloudRiskIdentifyMapper qCloudRiskIdentifyMapper;
    @Resource
    private QCloudRocketMQMapper qCloudRocketMQMapper;
    @Resource
    private QCloudSESMapper qCloudSESMapper;
    @Resource
    private QCloudSMSMapper qCloudSMSMapper;
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
    @Resource
    private QCloudSafeVideoMapper qCloudSafeVideoMapper;
    @Resource
    private QCloudScfMapper qCloudScfMapper;
    @Resource
    private QCloudSecCredentialMapper qCloudSecCredentialMapper;
    @Resource
    private QCloudSecretMgrMapper qCloudSecretMgrMapper;
    @Resource
    private QCloudSecurityGroupMapper qCloudSecurityGroupMapper;
    @Resource
    private QCloudSmartAdvisorMapper qCloudSmartAdvisorMapper;
    @Resource
    private QCloudSmartGuideMapper qCloudSmartGuideMapper;
    @Resource
    private QCloudSmartViewMapper qCloudSmartViewMapper;
    @Resource
    private QCloudSmsSignMapper qCloudSmsSignMapper;
    @Resource
    private QCloudSmsTemplateMapper qCloudSmsTemplateMapper;
    @Resource
    private QCloudSpokenEvalMapper qCloudSpokenEvalMapper;
    @Resource
    private QCloudSqlserverMapper qCloudSqlserverMapper;
    @Resource
    private QCloudTAPDMapper qCloudTAPDMapper;
    @Resource
    private QCloudTBAASMapper qCloudTBAASMapper;
    @Resource
    private QCloudTCBMapper qCloudTCBMapper;
    @Resource
    private QCloudTCHouseCMapper qCloudTCHouseCMapper;
    @Resource
    private QCloudTCHouseDMapper qCloudTCHouseDMapper;
    @Resource
    private QCloudTCHousePMapper qCloudTCHousePMapper;
    @Resource
    private QCloudTCRMapper qCloudTCRMapper;
    @Resource
    private QCloudTDMQMapper qCloudTDMQMapper;
    @Resource
    private QCloudTIMapper qCloudTIMapper;
    @Resource
    private QCloudTIHaiMapper qCloudTIHaiMapper;
    @Resource
    private QCloudTKEMapper qCloudTKEMapper;
    @Resource
    private QCloudTRTCMapper qCloudTRTCMapper;
    @Resource
    private QCloudTRTCRoomMapper qCloudTRTCRoomMapper;
    @Resource
    private QCloudTSEMapper qCloudTSEMapper;
    @Resource
    private QCloudTSFMapper qCloudTSFMapper;
    @Resource
    private QCloudTTSMapper qCloudTTSMapper;
    @Resource
    private QCloudTcaplusDBMapper qCloudTcaplusDBMapper;
    @Resource
    private QCloudTcrEntMapper qCloudTcrEntMapper;
    @Resource
    private QCloudTencentConnectMapper qCloudTencentConnectMapper;
    @Resource
    private QCloudTendisMapper qCloudTendisMapper;
    @Resource
    private QCloudTokenHubMapper qCloudTokenHubMapper;
    @Resource
    private QCloudTourismBigdataMapper qCloudTourismBigdataMapper;
    @Resource
    private QCloudUserMapper qCloudUserMapper;
    @Resource
    private QCloudUserToAccessKeyMapper qCloudUserToAccessKeyMapper;
    @Resource
    private QCloudVODMapper qCloudVODMapper;
    @Resource
    private QCloudVODMediaMapper qCloudVODMediaMapper;
    @Resource
    private QCloudVODProcessMapper qCloudVODProcessMapper;
    @Resource
    private QCloudVoiceCloneMapper qCloudVoiceCloneMapper;
    @Resource
    private QCloudVoiceMsgMapper qCloudVoiceMsgMapper;
    @Resource
    private QCloudVpcMapper qCloudVpcMapper;
    @Resource
    private QCloudVpcSubnetMapper qCloudVpcSubnetMapper;
    @Resource
    private QCloudVulnMgrMapper qCloudVulnMgrMapper;
    @Resource
    private QCloudWAFMapper qCloudWAFMapper;
    @Resource
    private QCloudWeDataMapper qCloudWeDataMapper;
    @Resource
    private QCloudWeLinkMapper qCloudWeLinkMapper;
    @Resource
    private QCloudWebSearchMapper qCloudWebSearchMapper;
    @Resource
    private QCloudWedaMapper qCloudWedaMapper;

    /**
     * 同步所有腾讯云资源
     *
     * @param cloudConf 云配置信息
     * @return 同步的资源总数
     */
    @Override
    public int syncEcs(CloudConf cloudConf) {
        QCloudClient qCloudClient = new QCloudClient(cloudConf);
        int total = 0;

        total += syncAPIGW(qCloudClient, cloudConf);
        total += syncAS(qCloudClient, cloudConf);
        total += syncASR(qCloudClient, cloudConf);
        total += syncAgentGW(qCloudClient, cloudConf);
        total += syncAgentPlatform(qCloudClient, cloudConf);
        total += syncAppRender(qCloudClient, cloudConf);
        total += syncAudit(qCloudClient, cloudConf);
        total += syncBI(qCloudClient, cloudConf);
        total += syncBMS(qCloudClient, cloudConf);
        total += syncBastion(qCloudClient, cloudConf);
        total += syncBizProcess(qCloudClient, cloudConf);
        total += syncCACert(qCloudClient, cloudConf);
        total += syncCAPTCHA(qCloudClient, cloudConf);
        total += syncCHC(qCloudClient, cloudConf);
        total += syncCHDFS(qCloudClient, cloudConf);
        total += syncCLB_gw(qCloudClient, cloudConf);
        total += syncCLS(qCloudClient, cloudConf);
        total += syncCSP(qCloudClient, cloudConf);
        total += syncCSPGateway(qCloudClient, cloudConf);
        total += syncCSS(qCloudClient, cloudConf);
        total += syncCTSDB(qCloudClient, cloudConf);
        total += syncCWP(qCloudClient, cloudConf);
        total += syncCWP3(qCloudClient, cloudConf);
        total += syncCbs(qCloudClient, cloudConf);
        total += syncCdb(qCloudClient, cloudConf);
        total += syncCdnDomain(qCloudClient, cloudConf);
        total += syncCfs(qCloudClient, cloudConf);
        total += syncCkafka(qCloudClient, cloudConf);
        total += syncClb(qCloudClient, cloudConf);
        total += syncCloudBase(qCloudClient, cloudConf);
        total += syncCloudContact(qCloudClient, cloudConf);
        total += syncCloudHSM(qCloudClient, cloudConf);
        total += syncCloudPhone(qCloudClient, cloudConf);
        total += syncCloudStudio(qCloudClient, cloudConf);
        total += syncCmq(qCloudClient, cloudConf);
        total += syncCodingDevops(qCloudClient, cloudConf);
        total += syncConfig(qCloudClient, cloudConf);
        total += syncContentRecognize(qCloudClient, cloudConf);
        total += syncContentSafe(qCloudClient, cloudConf);
        total += syncControlCenter(qCloudClient, cloudConf);
        total += syncCos(qCloudClient, cloudConf);
        total += syncCvm(qCloudClient, cloudConf);
        total += syncCynosDB(qCloudClient, cloudConf);
        total += syncDC(qCloudClient, cloudConf);
        total += syncDCDB(qCloudClient, cloudConf);
        total += syncDDoS(qCloudClient, cloudConf);
        total += syncDLC(qCloudClient, cloudConf);
        total += syncDNSPrivate(qCloudClient, cloudConf);
        total += syncDNSSec(qCloudClient, cloudConf);
        total += syncDataAudit(qCloudClient, cloudConf);
        total += syncDataSafeGov(qCloudClient, cloudConf);
        total += syncDeviceSafety(qCloudClient, cloudConf);
        total += syncDistID(qCloudClient, cloudConf);
        total += syncDnsDomain(qCloudClient, cloudConf);
        total += syncDocProcess(qCloudClient, cloudConf);
        total += syncDocs(qCloudClient, cloudConf);
        total += syncDomain(qCloudClient, cloudConf);
        total += syncDomainReg(qCloudClient, cloudConf);
        total += syncEMR(qCloudClient, cloudConf);
        total += syncEO(qCloudClient, cloudConf);
        total += syncES(qCloudClient, cloudConf);
        total += syncESign(qCloudClient, cloudConf);
        total += syncEip(qCloudClient, cloudConf);
        total += syncEngWrite(qCloudClient, cloudConf);
        total += syncEventBus(qCloudClient, cloudConf);
        total += syncExposedMgr(qCloudClient, cloudConf);
        total += syncFace(qCloudClient, cloudConf);
        total += syncFaceFusion(qCloudClient, cloudConf);
        total += syncFaceMakeup(qCloudClient, cloudConf);
        total += syncFaceSwap(qCloudClient, cloudConf);
        total += syncGHPhone(qCloudClient, cloudConf);
        total += syncGSE(qCloudClient, cloudConf);
        total += syncGTM(qCloudClient, cloudConf);
        total += syncGaap(qCloudClient, cloudConf);
        total += syncGameAntiACE(qCloudClient, cloudConf);
        total += syncGameDB(qCloudClient, cloudConf);
        total += syncGameServer(qCloudClient, cloudConf);
        total += syncGameVoice(qCloudClient, cloudConf);
        total += syncHBase(qCloudClient, cloudConf);
        total += syncHSM(qCloudClient, cloudConf);
        total += syncHealthDash(qCloudClient, cloudConf);
        total += syncHealthOmics(qCloudClient, cloudConf);
        total += syncHealthReport2(qCloudClient, cloudConf);
        total += syncICPBeian(qCloudClient, cloudConf);
        total += syncIOA(qCloudClient, cloudConf);
        total += syncImageProcess2(qCloudClient, cloudConf);
        total += syncImageSearch(qCloudClient, cloudConf);
        total += syncIoT(qCloudClient, cloudConf);
        total += syncIoTDevice(qCloudClient, cloudConf);
        total += syncIoTHub(qCloudClient, cloudConf);
        total += syncKMS(qCloudClient, cloudConf);
        total += syncKeeWiDB(qCloudClient, cloudConf);
        total += syncKnowledgeEngine(qCloudClient, cloudConf);
        total += syncLighthouse(qCloudClient, cloudConf);
        total += syncLive(qCloudClient, cloudConf);
        total += syncLive2(qCloudClient, cloudConf);
        total += syncMail(qCloudClient, cloudConf);
        total += syncMallTraffic(qCloudClient, cloudConf);
        total += syncMariaDb(qCloudClient, cloudConf);
        total += syncMathGrade(qCloudClient, cloudConf);
        total += syncMediaAsset(qCloudClient, cloudConf);
        total += syncMeeting(qCloudClient, cloudConf);
        total += syncMemcached(qCloudClient, cloudConf);
        total += syncMicroWeda(qCloudClient, cloudConf);
        total += syncMiniSafe(qCloudClient, cloudConf);
        total += syncMongoDB_CKafka(qCloudClient, cloudConf);
        total += syncMongoDb(qCloudClient, cloudConf);
        total += syncMonitor(qCloudClient, cloudConf);
        total += syncNMT(qCloudClient, cloudConf);
        total += syncNatGateway(qCloudClient, cloudConf);
        total += syncNativeBuild(qCloudClient, cloudConf);
        total += syncOCR(qCloudClient, cloudConf);
        total += syncOceanus(qCloudClient, cloudConf);
        total += syncOrg(qCloudClient, cloudConf);
        total += syncPenTest(qCloudClient, cloudConf);
        total += syncPostgresql(qCloudClient, cloudConf);
        total += syncPrivDNS(qCloudClient, cloudConf);
        total += syncRTIEdu(qCloudClient, cloudConf);
        total += syncRTIIndustrial(qCloudClient, cloudConf);
        total += syncRabbitMQ(qCloudClient, cloudConf);
        total += syncRedis(qCloudClient, cloudConf);
        total += syncRegionMgr(qCloudClient, cloudConf);
        total += syncRiskIdentify(qCloudClient, cloudConf);
        total += syncRocketMQ(qCloudClient, cloudConf);
        total += syncSES(qCloudClient, cloudConf);
        total += syncSMS(qCloudClient, cloudConf);
        total += syncSSL(qCloudClient, cloudConf);
        total += syncSSLPod(qCloudClient, cloudConf);
        total += syncSafeAudio(qCloudClient, cloudConf);
        total += syncSafeCenter(qCloudClient, cloudConf);
        total += syncSafeDoc(qCloudClient, cloudConf);
        total += syncSafeGuard(qCloudClient, cloudConf);
        total += syncSafeImage(qCloudClient, cloudConf);
        total += syncSafeMonitor(qCloudClient, cloudConf);
        total += syncSafePlatform(qCloudClient, cloudConf);
        total += syncSafeText(qCloudClient, cloudConf);
        total += syncSafeVideo(qCloudClient, cloudConf);
        total += syncScf(qCloudClient, cloudConf);
        total += syncSecCredential(qCloudClient, cloudConf);
        total += syncSecretMgr(qCloudClient, cloudConf);
        total += syncSecurityGroup(qCloudClient, cloudConf);
        total += syncSmartAdvisor(qCloudClient, cloudConf);
        total += syncSmartGuide(qCloudClient, cloudConf);
        total += syncSmartView(qCloudClient, cloudConf);
        total += syncSmsSign(qCloudClient, cloudConf);
        total += syncSmsTemplate(qCloudClient, cloudConf);
        total += syncSpokenEval(qCloudClient, cloudConf);
        total += syncSqlserver(qCloudClient, cloudConf);
        total += syncTAPD(qCloudClient, cloudConf);
        total += syncTBAAS(qCloudClient, cloudConf);
        total += syncTCB(qCloudClient, cloudConf);
        total += syncTCHouseC(qCloudClient, cloudConf);
        total += syncTCHouseD(qCloudClient, cloudConf);
        total += syncTCHouseP(qCloudClient, cloudConf);
        total += syncTCR(qCloudClient, cloudConf);
        total += syncTDMQ(qCloudClient, cloudConf);
        total += syncTI(qCloudClient, cloudConf);
        total += syncTIHai(qCloudClient, cloudConf);
        total += syncTKE(qCloudClient, cloudConf);
        total += syncTRTC(qCloudClient, cloudConf);
        total += syncTRTCRoom(qCloudClient, cloudConf);
        total += syncTSE(qCloudClient, cloudConf);
        total += syncTSF(qCloudClient, cloudConf);
        total += syncTTS(qCloudClient, cloudConf);
        total += syncTcaplusDB(qCloudClient, cloudConf);
        total += syncTcrEnt(qCloudClient, cloudConf);
        total += syncTencentConnect(qCloudClient, cloudConf);
        total += syncTendis(qCloudClient, cloudConf);
        total += syncTokenHub(qCloudClient, cloudConf);
        total += syncTourismBigdata(qCloudClient, cloudConf);
        total += syncUser(qCloudClient, cloudConf);
        total += syncVOD(qCloudClient, cloudConf);
        total += syncVODMedia(qCloudClient, cloudConf);
        total += syncVODProcess(qCloudClient, cloudConf);
        total += syncVoiceClone(qCloudClient, cloudConf);
        total += syncVoiceMsg(qCloudClient, cloudConf);
        total += syncVpc(qCloudClient, cloudConf);
        total += syncVpcSubnet(qCloudClient, cloudConf);
        total += syncVulnMgr(qCloudClient, cloudConf);
        total += syncWAF(qCloudClient, cloudConf);
        total += syncWeData(qCloudClient, cloudConf);
        total += syncWeLink(qCloudClient, cloudConf);
        total += syncWebSearch(qCloudClient, cloudConf);
        total += syncWeda(qCloudClient, cloudConf);
        return total;
    }

    // ==================== APIGW ====================

    private int syncAPIGW(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== AS ====================

    private int syncAS(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== ASR ====================

    private int syncASR(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== AGENTGW ====================

    private int syncAgentGW(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    private int syncAgentPlatform(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== APPRENDER ====================

    private int syncAppRender(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== AUDIT ====================

    private int syncAudit(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    private int syncBI(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== BMS ====================

    private int syncBMS(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== BASTION ====================

    private int syncBastion(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    private int syncBizProcess(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== CACERT ====================

    private int syncCACert(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    private int syncCAPTCHA(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== CHC ====================

    private int syncCHC(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== CHDFS ====================

    private int syncCHDFS(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudCHDFS> apiList = qCloudClient.listCHDFS();
        List<QCloudCHDFS> dbList = qCloudCHDFSMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudCHDFS> apiMap = apiList.stream()
                .filter(e -> e.getFileSystemId() != null)
                .collect(Collectors.toMap(QCloudCHDFS::getFileSystemId, e -> e, (a, b) -> a));
        Map<String, QCloudCHDFS> dbMap = dbList.stream()
                .filter(e -> e.getFileSystemId() != null)
                .collect(Collectors.toMap(QCloudCHDFS::getFileSystemId, e -> e, (a, b) -> a));

        List<QCloudCHDFS> toInsert = apiList.stream()
                .filter(e -> e.getFileSystemId() != null && !dbMap.containsKey(e.getFileSystemId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudCHDFSMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudCHDFS> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudCHDFS::getConfName, cloudConf.getName())
                    .in(QCloudCHDFS::getFileSystemId, toDeleteIds)
                    .set(QCloudCHDFS::getDeleted, 1);
            qCloudCHDFSMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CLB_GW ====================

    private int syncCLB_gw(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudCLB_gw> apiList = qCloudClient.listCLB_gw();
        List<QCloudCLB_gw> dbList = qCloudCLB_gwMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudCLB_gw> apiMap = apiList.stream()
                .filter(e -> e.getLoadBalancerId() != null)
                .collect(Collectors.toMap(QCloudCLB_gw::getLoadBalancerId, e -> e, (a, b) -> a));
        Map<String, QCloudCLB_gw> dbMap = dbList.stream()
                .filter(e -> e.getLoadBalancerId() != null)
                .collect(Collectors.toMap(QCloudCLB_gw::getLoadBalancerId, e -> e, (a, b) -> a));

        List<QCloudCLB_gw> toInsert = apiList.stream()
                .filter(e -> e.getLoadBalancerId() != null && !dbMap.containsKey(e.getLoadBalancerId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudCLB_gwMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudCLB_gw> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudCLB_gw::getConfName, cloudConf.getName())
                    .in(QCloudCLB_gw::getLoadBalancerId, toDeleteIds)
                    .set(QCloudCLB_gw::getDeleted, 1);
            qCloudCLB_gwMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CLS ====================

    private int syncCLS(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    private int syncCSP(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    private int syncCSPGateway(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== CSS ====================

    private int syncCSS(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== CTSDB ====================

    private int syncCTSDB(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudCTSDB> apiList = qCloudClient.listCTSDB();
        List<QCloudCTSDB> dbList = qCloudCTSDBMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudCTSDB> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudCTSDB::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudCTSDB> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudCTSDB::getInstanceId, e -> e, (a, b) -> a));

        List<QCloudCTSDB> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudCTSDBMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudCTSDB> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudCTSDB::getConfName, cloudConf.getName())
                    .in(QCloudCTSDB::getInstanceId, toDeleteIds)
                    .set(QCloudCTSDB::getDeleted, 1);
            qCloudCTSDBMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CWP ====================

    private int syncCWP(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== CWP3 ====================

    private int syncCWP3(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudCWP3> apiList = qCloudClient.listCWP3();
        List<QCloudCWP3> dbList = qCloudCWP3Mapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudCWP3> apiMap = apiList.stream()
                .filter(e -> e.getQuuid() != null)
                .collect(Collectors.toMap(QCloudCWP3::getQuuid, e -> e, (a, b) -> a));
        Map<String, QCloudCWP3> dbMap = dbList.stream()
                .filter(e -> e.getQuuid() != null)
                .collect(Collectors.toMap(QCloudCWP3::getQuuid, e -> e, (a, b) -> a));

        List<QCloudCWP3> toInsert = apiList.stream()
                .filter(e -> e.getQuuid() != null && !dbMap.containsKey(e.getQuuid()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudCWP3Mapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudCWP3> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudCWP3::getConfName, cloudConf.getName())
                    .in(QCloudCWP3::getQuuid, toDeleteIds)
                    .set(QCloudCWP3::getDeleted, 1);
            qCloudCWP3Mapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CBS ====================

    private int syncCbs(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudCbs> apiList = qCloudClient.listCbs();
        List<QCloudCbs> dbList = qCloudCbsMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudCbs> apiMap = apiList.stream()
                .filter(e -> e.getDiskId() != null)
                .collect(Collectors.toMap(QCloudCbs::getDiskId, e -> e, (a, b) -> a));
        Map<String, QCloudCbs> dbMap = dbList.stream()
                .filter(e -> e.getDiskId() != null)
                .collect(Collectors.toMap(QCloudCbs::getDiskId, e -> e, (a, b) -> a));

        List<QCloudCbs> toInsert = apiList.stream()
                .filter(e -> e.getDiskId() != null && !dbMap.containsKey(e.getDiskId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudCbsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudCbs> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudCbs::getConfName, cloudConf.getName())
                    .in(QCloudCbs::getDiskId, toDeleteIds)
                    .set(QCloudCbs::getDeleted, 1);
            qCloudCbsMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CDB ====================

    private int syncCdb(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudCdb> apiList = qCloudClient.listCdb();
        List<QCloudCdb> dbList = qCloudCdbMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudCdb> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudCdb::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudCdb> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudCdb::getInstanceId, e -> e, (a, b) -> a));

        List<QCloudCdb> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudCdbMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudCdb> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudCdb::getConfName, cloudConf.getName())
                    .in(QCloudCdb::getInstanceId, toDeleteIds)
                    .set(QCloudCdb::getDeleted, 1);
            qCloudCdbMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CDNDOMAIN ====================

    private int syncCdnDomain(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudCdnDomain> apiList = qCloudClient.listCdnDomain();
        List<QCloudCdnDomain> dbList = qCloudCdnDomainMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudCdnDomain> apiMap = apiList.stream()
                .filter(e -> e.getResourceId() != null)
                .collect(Collectors.toMap(QCloudCdnDomain::getResourceId, e -> e, (a, b) -> a));
        Map<String, QCloudCdnDomain> dbMap = dbList.stream()
                .filter(e -> e.getResourceId() != null)
                .collect(Collectors.toMap(QCloudCdnDomain::getResourceId, e -> e, (a, b) -> a));

        List<QCloudCdnDomain> toInsert = apiList.stream()
                .filter(e -> e.getResourceId() != null && !dbMap.containsKey(e.getResourceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudCdnDomainMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudCdnDomain> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudCdnDomain::getConfName, cloudConf.getName())
                    .in(QCloudCdnDomain::getResourceId, toDeleteIds)
                    .set(QCloudCdnDomain::getDeleted, 1);
            qCloudCdnDomainMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CFS ====================

    private int syncCfs(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudCfs> apiList = qCloudClient.listCfs();
        List<QCloudCfs> dbList = qCloudCfsMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudCfs> apiMap = apiList.stream()
                .filter(e -> e.getFileSystemId() != null)
                .collect(Collectors.toMap(QCloudCfs::getFileSystemId, e -> e, (a, b) -> a));
        Map<String, QCloudCfs> dbMap = dbList.stream()
                .filter(e -> e.getFileSystemId() != null)
                .collect(Collectors.toMap(QCloudCfs::getFileSystemId, e -> e, (a, b) -> a));

        List<QCloudCfs> toInsert = apiList.stream()
                .filter(e -> e.getFileSystemId() != null && !dbMap.containsKey(e.getFileSystemId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudCfsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudCfs> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudCfs::getConfName, cloudConf.getName())
                    .in(QCloudCfs::getFileSystemId, toDeleteIds)
                    .set(QCloudCfs::getDeleted, 1);
            qCloudCfsMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CKAFKA ====================

    private int syncCkafka(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudCkafka> apiList = qCloudClient.listCkafka();
        List<QCloudCkafka> dbList = qCloudCkafkaMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudCkafka> apiMap = apiList.stream()
                .filter(e -> e.getInstanceName() != null)
                .collect(Collectors.toMap(QCloudCkafka::getInstanceName, e -> e, (a, b) -> a));
        Map<String, QCloudCkafka> dbMap = dbList.stream()
                .filter(e -> e.getInstanceName() != null)
                .collect(Collectors.toMap(QCloudCkafka::getInstanceName, e -> e, (a, b) -> a));

        List<QCloudCkafka> toInsert = apiList.stream()
                .filter(e -> e.getInstanceName() != null && !dbMap.containsKey(e.getInstanceName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudCkafkaMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudCkafka> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudCkafka::getConfName, cloudConf.getName())
                    .in(QCloudCkafka::getInstanceName, toDeleteIds)
                    .set(QCloudCkafka::getDeleted, 1);
            qCloudCkafkaMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CLB ====================

    private int syncClb(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudClb> apiList = qCloudClient.listClb();
        List<QCloudClb> dbList = qCloudClbMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudClb> apiMap = apiList.stream()
                .filter(e -> e.getLoadBalancerId() != null)
                .collect(Collectors.toMap(QCloudClb::getLoadBalancerId, e -> e, (a, b) -> a));
        Map<String, QCloudClb> dbMap = dbList.stream()
                .filter(e -> e.getLoadBalancerId() != null)
                .collect(Collectors.toMap(QCloudClb::getLoadBalancerId, e -> e, (a, b) -> a));

        List<QCloudClb> toInsert = apiList.stream()
                .filter(e -> e.getLoadBalancerId() != null && !dbMap.containsKey(e.getLoadBalancerId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudClbMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudClb> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudClb::getConfName, cloudConf.getName())
                    .in(QCloudClb::getLoadBalancerId, toDeleteIds)
                    .set(QCloudClb::getDeleted, 1);
            qCloudClbMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CLOUDBASE ====================

    private int syncCloudBase(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudCloudBase> apiList = qCloudClient.listCloudBase();
        List<QCloudCloudBase> dbList = qCloudCloudBaseMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudCloudBase> apiMap = apiList.stream()
                .filter(e -> e.getEnvId() != null)
                .collect(Collectors.toMap(QCloudCloudBase::getEnvId, e -> e, (a, b) -> a));
        Map<String, QCloudCloudBase> dbMap = dbList.stream()
                .filter(e -> e.getEnvId() != null)
                .collect(Collectors.toMap(QCloudCloudBase::getEnvId, e -> e, (a, b) -> a));

        List<QCloudCloudBase> toInsert = apiList.stream()
                .filter(e -> e.getEnvId() != null && !dbMap.containsKey(e.getEnvId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudCloudBaseMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudCloudBase> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudCloudBase::getConfName, cloudConf.getName())
                    .in(QCloudCloudBase::getEnvId, toDeleteIds)
                    .set(QCloudCloudBase::getDeleted, 1);
            qCloudCloudBaseMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CLOUDCONTACT ====================

    private int syncCloudContact(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudCloudContact> apiList = qCloudClient.listCloudContact();
        List<QCloudCloudContact> dbList = qCloudCloudContactMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudCloudContact> apiMap = apiList.stream()
                .filter(e -> e.getSipTrunkId() != null)
                .collect(Collectors.toMap(QCloudCloudContact::getSipTrunkId, e -> e, (a, b) -> a));
        Map<String, QCloudCloudContact> dbMap = dbList.stream()
                .filter(e -> e.getSipTrunkId() != null)
                .collect(Collectors.toMap(QCloudCloudContact::getSipTrunkId, e -> e, (a, b) -> a));

        List<QCloudCloudContact> toInsert = apiList.stream()
                .filter(e -> e.getSipTrunkId() != null && !dbMap.containsKey(e.getSipTrunkId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudCloudContactMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudCloudContact> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudCloudContact::getConfName, cloudConf.getName())
                    .in(QCloudCloudContact::getSipTrunkId, toDeleteIds)
                    .set(QCloudCloudContact::getDeleted, 1);
            qCloudCloudContactMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CLOUDHSM ====================

    private int syncCloudHSM(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== CLOUDPHONE ====================

    private int syncCloudPhone(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudCloudPhone> apiList = qCloudClient.listCloudPhone();
        List<QCloudCloudPhone> dbList = qCloudCloudPhoneMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudCloudPhone> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudCloudPhone::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudCloudPhone> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudCloudPhone::getInstanceId, e -> e, (a, b) -> a));

        List<QCloudCloudPhone> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudCloudPhoneMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudCloudPhone> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudCloudPhone::getConfName, cloudConf.getName())
                    .in(QCloudCloudPhone::getInstanceId, toDeleteIds)
                    .set(QCloudCloudPhone::getDeleted, 1);
            qCloudCloudPhoneMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CLOUDSTUDIO ====================

    private int syncCloudStudio(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudCloudStudio> apiList = qCloudClient.listCloudStudio();
        List<QCloudCloudStudio> dbList = qCloudCloudStudioMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudCloudStudio> apiMap = apiList.stream()
                .filter(e -> e.getSpaceId() != null)
                .collect(Collectors.toMap(QCloudCloudStudio::getSpaceId, e -> e, (a, b) -> a));
        Map<String, QCloudCloudStudio> dbMap = dbList.stream()
                .filter(e -> e.getSpaceId() != null)
                .collect(Collectors.toMap(QCloudCloudStudio::getSpaceId, e -> e, (a, b) -> a));

        List<QCloudCloudStudio> toInsert = apiList.stream()
                .filter(e -> e.getSpaceId() != null && !dbMap.containsKey(e.getSpaceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudCloudStudioMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudCloudStudio> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudCloudStudio::getConfName, cloudConf.getName())
                    .in(QCloudCloudStudio::getSpaceId, toDeleteIds)
                    .set(QCloudCloudStudio::getDeleted, 1);
            qCloudCloudStudioMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CMQ ====================

    private int syncCmq(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudCmq> apiList = qCloudClient.listCmq();
        List<QCloudCmq> dbList = qCloudCmqMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudCmq> apiMap = apiList.stream()
                .filter(e -> e.getQueueId() != null)
                .collect(Collectors.toMap(QCloudCmq::getQueueId, e -> e, (a, b) -> a));
        Map<String, QCloudCmq> dbMap = dbList.stream()
                .filter(e -> e.getQueueId() != null)
                .collect(Collectors.toMap(QCloudCmq::getQueueId, e -> e, (a, b) -> a));

        List<QCloudCmq> toInsert = apiList.stream()
                .filter(e -> e.getQueueId() != null && !dbMap.containsKey(e.getQueueId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudCmqMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudCmq> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudCmq::getConfName, cloudConf.getName())
                    .in(QCloudCmq::getQueueId, toDeleteIds)
                    .set(QCloudCmq::getDeleted, 1);
            qCloudCmqMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CODINGDEVOPS ====================

    private int syncCodingDevops(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudCodingDevops> apiList = qCloudClient.listCodingDevops();
        List<QCloudCodingDevops> dbList = qCloudCodingDevopsMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudCodingDevops> apiMap = apiList.stream()
                .filter(e -> e.getProjectName() != null)
                .collect(Collectors.toMap(QCloudCodingDevops::getProjectName, e -> e, (a, b) -> a));
        Map<String, QCloudCodingDevops> dbMap = dbList.stream()
                .filter(e -> e.getProjectName() != null)
                .collect(Collectors.toMap(QCloudCodingDevops::getProjectName, e -> e, (a, b) -> a));

        List<QCloudCodingDevops> toInsert = apiList.stream()
                .filter(e -> e.getProjectName() != null && !dbMap.containsKey(e.getProjectName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudCodingDevopsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudCodingDevops> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudCodingDevops::getConfName, cloudConf.getName())
                    .in(QCloudCodingDevops::getProjectName, toDeleteIds)
                    .set(QCloudCodingDevops::getDeleted, 1);
            qCloudCodingDevopsMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CONFIG ====================

    private int syncConfig(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudConfig> apiList = qCloudClient.listConfig();
        List<QCloudConfig> dbList = qCloudConfigMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudConfig> apiMap = apiList.stream()
                .filter(e -> e.getResourceId() != null)
                .collect(Collectors.toMap(QCloudConfig::getResourceId, e -> e, (a, b) -> a));
        Map<String, QCloudConfig> dbMap = dbList.stream()
                .filter(e -> e.getResourceId() != null)
                .collect(Collectors.toMap(QCloudConfig::getResourceId, e -> e, (a, b) -> a));

        List<QCloudConfig> toInsert = apiList.stream()
                .filter(e -> e.getResourceId() != null && !dbMap.containsKey(e.getResourceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudConfigMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudConfig> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudConfig::getConfName, cloudConf.getName())
                    .in(QCloudConfig::getResourceId, toDeleteIds)
                    .set(QCloudConfig::getDeleted, 1);
            qCloudConfigMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CONTENTRECOGNIZE ====================

    private int syncContentRecognize(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== CONTENTSAFE ====================

    private int syncContentSafe(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== CONTROLCENTER ====================

    private int syncControlCenter(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudControlCenter> apiList = qCloudClient.listControlCenter();
        List<QCloudControlCenter> dbList = qCloudControlCenterMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudControlCenter> apiMap = apiList.stream()
                .filter(e -> e.getName() != null)
                .collect(Collectors.toMap(QCloudControlCenter::getName, e -> e, (a, b) -> a));
        Map<String, QCloudControlCenter> dbMap = dbList.stream()
                .filter(e -> e.getName() != null)
                .collect(Collectors.toMap(QCloudControlCenter::getName, e -> e, (a, b) -> a));

        List<QCloudControlCenter> toInsert = apiList.stream()
                .filter(e -> e.getName() != null && !dbMap.containsKey(e.getName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudControlCenterMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudControlCenter> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudControlCenter::getConfName, cloudConf.getName())
                    .in(QCloudControlCenter::getName, toDeleteIds)
                    .set(QCloudControlCenter::getDeleted, 1);
            qCloudControlCenterMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== COS ====================

    private int syncCos(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudCos> apiList = qCloudClient.listCos();
        List<QCloudCos> dbList = qCloudCosMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudCos> apiMap = apiList.stream()
                .filter(e -> e.getName() != null)
                .collect(Collectors.toMap(QCloudCos::getName, e -> e, (a, b) -> a));
        Map<String, QCloudCos> dbMap = dbList.stream()
                .filter(e -> e.getName() != null)
                .collect(Collectors.toMap(QCloudCos::getName, e -> e, (a, b) -> a));

        List<QCloudCos> toInsert = apiList.stream()
                .filter(e -> e.getName() != null && !dbMap.containsKey(e.getName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudCosMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudCos> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudCos::getConfName, cloudConf.getName())
                    .in(QCloudCos::getName, toDeleteIds)
                    .set(QCloudCos::getDeleted, 1);
            qCloudCosMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== CVM ====================

    private int syncCvm(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== CYNOSDB ====================

    private int syncCynosDB(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudCynosDB> apiList = qCloudClient.listCynosDB();
        List<QCloudCynosDB> dbList = qCloudCynosDBMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudCynosDB> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudCynosDB::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudCynosDB> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudCynosDB::getInstanceId, e -> e, (a, b) -> a));

        List<QCloudCynosDB> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudCynosDBMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudCynosDB> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudCynosDB::getConfName, cloudConf.getName())
                    .in(QCloudCynosDB::getInstanceId, toDeleteIds)
                    .set(QCloudCynosDB::getDeleted, 1);
            qCloudCynosDBMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== DC ====================

    private int syncDC(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudDC> apiList = qCloudClient.listDC();
        List<QCloudDC> dbList = qCloudDCMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudDC> apiMap = apiList.stream()
                .filter(e -> e.getDirectConnectId() != null)
                .collect(Collectors.toMap(QCloudDC::getDirectConnectId, e -> e, (a, b) -> a));
        Map<String, QCloudDC> dbMap = dbList.stream()
                .filter(e -> e.getDirectConnectId() != null)
                .collect(Collectors.toMap(QCloudDC::getDirectConnectId, e -> e, (a, b) -> a));

        List<QCloudDC> toInsert = apiList.stream()
                .filter(e -> e.getDirectConnectId() != null && !dbMap.containsKey(e.getDirectConnectId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudDCMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudDC> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudDC::getConfName, cloudConf.getName())
                    .in(QCloudDC::getDirectConnectId, toDeleteIds)
                    .set(QCloudDC::getDeleted, 1);
            qCloudDCMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== DCDB ====================

    private int syncDCDB(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudDCDB> apiList = qCloudClient.listDCDB();
        List<QCloudDCDB> dbList = qCloudDCDBMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudDCDB> apiMap = apiList.stream()
                .filter(e -> e.getInstanceName() != null)
                .collect(Collectors.toMap(QCloudDCDB::getInstanceName, e -> e, (a, b) -> a));
        Map<String, QCloudDCDB> dbMap = dbList.stream()
                .filter(e -> e.getInstanceName() != null)
                .collect(Collectors.toMap(QCloudDCDB::getInstanceName, e -> e, (a, b) -> a));

        List<QCloudDCDB> toInsert = apiList.stream()
                .filter(e -> e.getInstanceName() != null && !dbMap.containsKey(e.getInstanceName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudDCDBMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudDCDB> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudDCDB::getConfName, cloudConf.getName())
                    .in(QCloudDCDB::getInstanceName, toDeleteIds)
                    .set(QCloudDCDB::getDeleted, 1);
            qCloudDCDBMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== DDOS ====================

    private int syncDDoS(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== DLC ====================

    private int syncDLC(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudDLC> apiList = qCloudClient.listDLC();
        List<QCloudDLC> dbList = qCloudDLCMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudDLC> apiMap = apiList.stream()
                .filter(e -> e.getDatabaseName() != null)
                .collect(Collectors.toMap(QCloudDLC::getDatabaseName, e -> e, (a, b) -> a));
        Map<String, QCloudDLC> dbMap = dbList.stream()
                .filter(e -> e.getDatabaseName() != null)
                .collect(Collectors.toMap(QCloudDLC::getDatabaseName, e -> e, (a, b) -> a));

        List<QCloudDLC> toInsert = apiList.stream()
                .filter(e -> e.getDatabaseName() != null && !dbMap.containsKey(e.getDatabaseName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudDLCMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudDLC> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudDLC::getConfName, cloudConf.getName())
                    .in(QCloudDLC::getDatabaseName, toDeleteIds)
                    .set(QCloudDLC::getDeleted, 1);
            qCloudDLCMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== DNSPRIVATE ====================

    private int syncDNSPrivate(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudDNSPrivate> apiList = qCloudClient.listDNSPrivate();
        List<QCloudDNSPrivate> dbList = qCloudDNSPrivateMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudDNSPrivate> apiMap = apiList.stream()
                .filter(e -> e.getZoneId() != null)
                .collect(Collectors.toMap(QCloudDNSPrivate::getZoneId, e -> e, (a, b) -> a));
        Map<String, QCloudDNSPrivate> dbMap = dbList.stream()
                .filter(e -> e.getZoneId() != null)
                .collect(Collectors.toMap(QCloudDNSPrivate::getZoneId, e -> e, (a, b) -> a));

        List<QCloudDNSPrivate> toInsert = apiList.stream()
                .filter(e -> e.getZoneId() != null && !dbMap.containsKey(e.getZoneId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudDNSPrivateMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudDNSPrivate> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudDNSPrivate::getConfName, cloudConf.getName())
                    .in(QCloudDNSPrivate::getZoneId, toDeleteIds)
                    .set(QCloudDNSPrivate::getDeleted, 1);
            qCloudDNSPrivateMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== DNSSEC ====================

    private int syncDNSSec(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudDNSSec> apiList = qCloudClient.listDNSSec();
        List<QCloudDNSSec> dbList = qCloudDNSSecMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudDNSSec> apiMap = apiList.stream()
                .filter(e -> e.getZoneId() != null)
                .collect(Collectors.toMap(QCloudDNSSec::getZoneId, e -> e, (a, b) -> a));
        Map<String, QCloudDNSSec> dbMap = dbList.stream()
                .filter(e -> e.getZoneId() != null)
                .collect(Collectors.toMap(QCloudDNSSec::getZoneId, e -> e, (a, b) -> a));

        List<QCloudDNSSec> toInsert = apiList.stream()
                .filter(e -> e.getZoneId() != null && !dbMap.containsKey(e.getZoneId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudDNSSecMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudDNSSec> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudDNSSec::getConfName, cloudConf.getName())
                    .in(QCloudDNSSec::getZoneId, toDeleteIds)
                    .set(QCloudDNSSec::getDeleted, 1);
            qCloudDNSSecMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== DATAAUDIT ====================

    private int syncDataAudit(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    private int syncDataSafeGov(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    private int syncDeviceSafety(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== DISTID ====================

    private int syncDistID(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudDistID> apiList = qCloudClient.listDistID();
        List<QCloudDistID> dbList = qCloudDistIDMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudDistID> apiMap = apiList.stream()
                .filter(e -> e.getIdName() != null)
                .collect(Collectors.toMap(QCloudDistID::getIdName, e -> e, (a, b) -> a));
        Map<String, QCloudDistID> dbMap = dbList.stream()
                .filter(e -> e.getIdName() != null)
                .collect(Collectors.toMap(QCloudDistID::getIdName, e -> e, (a, b) -> a));

        List<QCloudDistID> toInsert = apiList.stream()
                .filter(e -> e.getIdName() != null && !dbMap.containsKey(e.getIdName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudDistIDMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudDistID> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudDistID::getConfName, cloudConf.getName())
                    .in(QCloudDistID::getIdName, toDeleteIds)
                    .set(QCloudDistID::getDeleted, 1);
            qCloudDistIDMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== DNSDOMAIN ====================

    private int syncDnsDomain(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudDnsDomain> apiList = qCloudClient.listDnsDomain();
        List<QCloudDnsDomain> dbList = qCloudDnsDomainMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudDnsDomain> apiMap = apiList.stream()
                .filter(e -> e.getName() != null)
                .collect(Collectors.toMap(QCloudDnsDomain::getName, e -> e, (a, b) -> a));
        Map<String, QCloudDnsDomain> dbMap = dbList.stream()
                .filter(e -> e.getName() != null)
                .collect(Collectors.toMap(QCloudDnsDomain::getName, e -> e, (a, b) -> a));

        List<QCloudDnsDomain> toInsert = apiList.stream()
                .filter(e -> e.getName() != null && !dbMap.containsKey(e.getName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudDnsDomainMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudDnsDomain> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudDnsDomain::getConfName, cloudConf.getName())
                    .in(QCloudDnsDomain::getName, toDeleteIds)
                    .set(QCloudDnsDomain::getDeleted, 1);
            qCloudDnsDomainMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== DOCPROCESS ====================

    private int syncDocProcess(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudDocProcess> apiList = qCloudClient.listDocProcess();
        List<QCloudDocProcess> dbList = qCloudDocProcessMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudDocProcess> apiMap = apiList.stream()
                .filter(e -> e.getBucket() != null)
                .collect(Collectors.toMap(QCloudDocProcess::getBucket, e -> e, (a, b) -> a));
        Map<String, QCloudDocProcess> dbMap = dbList.stream()
                .filter(e -> e.getBucket() != null)
                .collect(Collectors.toMap(QCloudDocProcess::getBucket, e -> e, (a, b) -> a));

        List<QCloudDocProcess> toInsert = apiList.stream()
                .filter(e -> e.getBucket() != null && !dbMap.containsKey(e.getBucket()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudDocProcessMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudDocProcess> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudDocProcess::getConfName, cloudConf.getName())
                    .in(QCloudDocProcess::getBucket, toDeleteIds)
                    .set(QCloudDocProcess::getDeleted, 1);
            qCloudDocProcessMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== DOCS ====================

    private int syncDocs(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudDocs> apiList = qCloudClient.listDocs();
        List<QCloudDocs> dbList = qCloudDocsMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudDocs> apiMap = apiList.stream()
                .filter(e -> e.getDocId() != null)
                .collect(Collectors.toMap(QCloudDocs::getDocId, e -> e, (a, b) -> a));
        Map<String, QCloudDocs> dbMap = dbList.stream()
                .filter(e -> e.getDocId() != null)
                .collect(Collectors.toMap(QCloudDocs::getDocId, e -> e, (a, b) -> a));

        List<QCloudDocs> toInsert = apiList.stream()
                .filter(e -> e.getDocId() != null && !dbMap.containsKey(e.getDocId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudDocsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudDocs> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudDocs::getConfName, cloudConf.getName())
                    .in(QCloudDocs::getDocId, toDeleteIds)
                    .set(QCloudDocs::getDeleted, 1);
            qCloudDocsMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== DOMAIN ====================

    private int syncDomain(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudDomain> apiList = qCloudClient.listDomain();
        List<QCloudDomain> dbList = qCloudDomainMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudDomain> apiMap = apiList.stream()
                .filter(e -> e.getDomainId() != null)
                .collect(Collectors.toMap(QCloudDomain::getDomainId, e -> e, (a, b) -> a));
        Map<String, QCloudDomain> dbMap = dbList.stream()
                .filter(e -> e.getDomainId() != null)
                .collect(Collectors.toMap(QCloudDomain::getDomainId, e -> e, (a, b) -> a));

        List<QCloudDomain> toInsert = apiList.stream()
                .filter(e -> e.getDomainId() != null && !dbMap.containsKey(e.getDomainId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudDomainMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudDomain> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudDomain::getConfName, cloudConf.getName())
                    .in(QCloudDomain::getDomainId, toDeleteIds)
                    .set(QCloudDomain::getDeleted, 1);
            qCloudDomainMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== DOMAINREG ====================

    private int syncDomainReg(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudDomainReg> apiList = qCloudClient.listDomainReg();
        List<QCloudDomainReg> dbList = qCloudDomainRegMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudDomainReg> apiMap = apiList.stream()
                .filter(e -> e.getDomainName() != null)
                .collect(Collectors.toMap(QCloudDomainReg::getDomainName, e -> e, (a, b) -> a));
        Map<String, QCloudDomainReg> dbMap = dbList.stream()
                .filter(e -> e.getDomainName() != null)
                .collect(Collectors.toMap(QCloudDomainReg::getDomainName, e -> e, (a, b) -> a));

        List<QCloudDomainReg> toInsert = apiList.stream()
                .filter(e -> e.getDomainName() != null && !dbMap.containsKey(e.getDomainName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudDomainRegMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudDomainReg> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudDomainReg::getConfName, cloudConf.getName())
                    .in(QCloudDomainReg::getDomainName, toDeleteIds)
                    .set(QCloudDomainReg::getDeleted, 1);
            qCloudDomainRegMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== EMR ====================

    private int syncEMR(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== EO ====================

    private int syncEO(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudEO> apiList = qCloudClient.listEO();
        List<QCloudEO> dbList = qCloudEOMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudEO> apiMap = apiList.stream()
                .filter(e -> e.getZoneId() != null)
                .collect(Collectors.toMap(QCloudEO::getZoneId, e -> e, (a, b) -> a));
        Map<String, QCloudEO> dbMap = dbList.stream()
                .filter(e -> e.getZoneId() != null)
                .collect(Collectors.toMap(QCloudEO::getZoneId, e -> e, (a, b) -> a));

        List<QCloudEO> toInsert = apiList.stream()
                .filter(e -> e.getZoneId() != null && !dbMap.containsKey(e.getZoneId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudEOMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudEO> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudEO::getConfName, cloudConf.getName())
                    .in(QCloudEO::getZoneId, toDeleteIds)
                    .set(QCloudEO::getDeleted, 1);
            qCloudEOMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== ES ====================

    private int syncES(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudES> apiList = qCloudClient.listES();
        List<QCloudES> dbList = qCloudESMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudES> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudES::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudES> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudES::getInstanceId, e -> e, (a, b) -> a));

        List<QCloudES> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudESMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudES> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudES::getConfName, cloudConf.getName())
                    .in(QCloudES::getInstanceId, toDeleteIds)
                    .set(QCloudES::getDeleted, 1);
            qCloudESMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== ESIGN ====================

    private int syncESign(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== EIP ====================

    private int syncEip(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudEip> apiList = qCloudClient.listEip();
        List<QCloudEip> dbList = qCloudEipMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudEip> apiMap = apiList.stream()
                .filter(e -> e.getAddressId() != null)
                .collect(Collectors.toMap(QCloudEip::getAddressId, e -> e, (a, b) -> a));
        Map<String, QCloudEip> dbMap = dbList.stream()
                .filter(e -> e.getAddressId() != null)
                .collect(Collectors.toMap(QCloudEip::getAddressId, e -> e, (a, b) -> a));

        List<QCloudEip> toInsert = apiList.stream()
                .filter(e -> e.getAddressId() != null && !dbMap.containsKey(e.getAddressId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudEipMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudEip> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudEip::getConfName, cloudConf.getName())
                    .in(QCloudEip::getAddressId, toDeleteIds)
                    .set(QCloudEip::getDeleted, 1);
            qCloudEipMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== ENGWRITE ====================

    private int syncEngWrite(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== EVENTBUS ====================

    private int syncEventBus(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudEventBus> apiList = qCloudClient.listEventBus();
        List<QCloudEventBus> dbList = qCloudEventBusMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudEventBus> apiMap = apiList.stream()
                .filter(e -> e.getEventBusId() != null)
                .collect(Collectors.toMap(QCloudEventBus::getEventBusId, e -> e, (a, b) -> a));
        Map<String, QCloudEventBus> dbMap = dbList.stream()
                .filter(e -> e.getEventBusId() != null)
                .collect(Collectors.toMap(QCloudEventBus::getEventBusId, e -> e, (a, b) -> a));

        List<QCloudEventBus> toInsert = apiList.stream()
                .filter(e -> e.getEventBusId() != null && !dbMap.containsKey(e.getEventBusId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudEventBusMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudEventBus> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudEventBus::getConfName, cloudConf.getName())
                    .in(QCloudEventBus::getEventBusId, toDeleteIds)
                    .set(QCloudEventBus::getDeleted, 1);
            qCloudEventBusMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== EXPOSEDMGR ====================

    private int syncExposedMgr(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== FACE ====================

    private int syncFace(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    private int syncFaceFusion(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    private int syncFaceMakeup(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    private int syncFaceSwap(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== GHPHONE ====================

    private int syncGHPhone(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== GSE ====================

    private int syncGSE(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudGSE> apiList = qCloudClient.listGSE();
        List<QCloudGSE> dbList = qCloudGSEMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudGSE> apiMap = apiList.stream()
                .filter(e -> e.getFleetId() != null)
                .collect(Collectors.toMap(QCloudGSE::getFleetId, e -> e, (a, b) -> a));
        Map<String, QCloudGSE> dbMap = dbList.stream()
                .filter(e -> e.getFleetId() != null)
                .collect(Collectors.toMap(QCloudGSE::getFleetId, e -> e, (a, b) -> a));

        List<QCloudGSE> toInsert = apiList.stream()
                .filter(e -> e.getFleetId() != null && !dbMap.containsKey(e.getFleetId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudGSEMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudGSE> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudGSE::getConfName, cloudConf.getName())
                    .in(QCloudGSE::getFleetId, toDeleteIds)
                    .set(QCloudGSE::getDeleted, 1);
            qCloudGSEMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== GTM ====================

    private int syncGTM(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudGTM> apiList = qCloudClient.listGTM();
        List<QCloudGTM> dbList = qCloudGTMMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudGTM> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudGTM::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudGTM> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudGTM::getInstanceId, e -> e, (a, b) -> a));

        List<QCloudGTM> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudGTMMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudGTM> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudGTM::getConfName, cloudConf.getName())
                    .in(QCloudGTM::getInstanceId, toDeleteIds)
                    .set(QCloudGTM::getDeleted, 1);
            qCloudGTMMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== GAAP ====================

    private int syncGaap(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudGaap> apiList = qCloudClient.listGaap();
        List<QCloudGaap> dbList = qCloudGaapMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudGaap> apiMap = apiList.stream()
                .filter(e -> e.getProxyId() != null)
                .collect(Collectors.toMap(QCloudGaap::getProxyId, e -> e, (a, b) -> a));
        Map<String, QCloudGaap> dbMap = dbList.stream()
                .filter(e -> e.getProxyId() != null)
                .collect(Collectors.toMap(QCloudGaap::getProxyId, e -> e, (a, b) -> a));

        List<QCloudGaap> toInsert = apiList.stream()
                .filter(e -> e.getProxyId() != null && !dbMap.containsKey(e.getProxyId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudGaapMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudGaap> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudGaap::getConfName, cloudConf.getName())
                    .in(QCloudGaap::getProxyId, toDeleteIds)
                    .set(QCloudGaap::getDeleted, 1);
            qCloudGaapMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== GAMEANTIACE ====================

    private int syncGameAntiACE(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudGameAntiACE> apiList = qCloudClient.listGameAntiACE();
        List<QCloudGameAntiACE> dbList = qCloudGameAntiACEMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudGameAntiACE> apiMap = apiList.stream()
                .filter(e -> e.getGameId() != null)
                .collect(Collectors.toMap(QCloudGameAntiACE::getGameId, e -> e, (a, b) -> a));
        Map<String, QCloudGameAntiACE> dbMap = dbList.stream()
                .filter(e -> e.getGameId() != null)
                .collect(Collectors.toMap(QCloudGameAntiACE::getGameId, e -> e, (a, b) -> a));

        List<QCloudGameAntiACE> toInsert = apiList.stream()
                .filter(e -> e.getGameId() != null && !dbMap.containsKey(e.getGameId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudGameAntiACEMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudGameAntiACE> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudGameAntiACE::getConfName, cloudConf.getName())
                    .in(QCloudGameAntiACE::getGameId, toDeleteIds)
                    .set(QCloudGameAntiACE::getDeleted, 1);
            qCloudGameAntiACEMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== GAMEDB ====================

    private int syncGameDB(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudGameDB> apiList = qCloudClient.listGameDB();
        List<QCloudGameDB> dbList = qCloudGameDBMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudGameDB> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudGameDB::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudGameDB> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudGameDB::getInstanceId, e -> e, (a, b) -> a));

        List<QCloudGameDB> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudGameDBMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudGameDB> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudGameDB::getConfName, cloudConf.getName())
                    .in(QCloudGameDB::getInstanceId, toDeleteIds)
                    .set(QCloudGameDB::getDeleted, 1);
            qCloudGameDBMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== GAMESERVER ====================

    private int syncGameServer(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudGameServer> apiList = qCloudClient.listGameServer();
        List<QCloudGameServer> dbList = qCloudGameServerMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudGameServer> apiMap = apiList.stream()
                .filter(e -> e.getFleetId() != null)
                .collect(Collectors.toMap(QCloudGameServer::getFleetId, e -> e, (a, b) -> a));
        Map<String, QCloudGameServer> dbMap = dbList.stream()
                .filter(e -> e.getFleetId() != null)
                .collect(Collectors.toMap(QCloudGameServer::getFleetId, e -> e, (a, b) -> a));

        List<QCloudGameServer> toInsert = apiList.stream()
                .filter(e -> e.getFleetId() != null && !dbMap.containsKey(e.getFleetId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudGameServerMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudGameServer> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudGameServer::getConfName, cloudConf.getName())
                    .in(QCloudGameServer::getFleetId, toDeleteIds)
                    .set(QCloudGameServer::getDeleted, 1);
            qCloudGameServerMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== GAMEVOICE ====================

    private int syncGameVoice(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudGameVoice> apiList = qCloudClient.listGameVoice();
        List<QCloudGameVoice> dbList = qCloudGameVoiceMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudGameVoice> apiMap = apiList.stream()
                .filter(e -> e.getAppId() != null)
                .collect(Collectors.toMap(QCloudGameVoice::getAppId, e -> e, (a, b) -> a));
        Map<String, QCloudGameVoice> dbMap = dbList.stream()
                .filter(e -> e.getAppId() != null)
                .collect(Collectors.toMap(QCloudGameVoice::getAppId, e -> e, (a, b) -> a));

        List<QCloudGameVoice> toInsert = apiList.stream()
                .filter(e -> e.getAppId() != null && !dbMap.containsKey(e.getAppId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudGameVoiceMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudGameVoice> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudGameVoice::getConfName, cloudConf.getName())
                    .in(QCloudGameVoice::getAppId, toDeleteIds)
                    .set(QCloudGameVoice::getDeleted, 1);
            qCloudGameVoiceMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== HBASE ====================

    private int syncHBase(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudHBase> apiList = qCloudClient.listHBase();
        List<QCloudHBase> dbList = qCloudHBaseMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudHBase> apiMap = apiList.stream()
                .filter(e -> e.getClusterId() != null)
                .collect(Collectors.toMap(QCloudHBase::getClusterId, e -> e, (a, b) -> a));
        Map<String, QCloudHBase> dbMap = dbList.stream()
                .filter(e -> e.getClusterId() != null)
                .collect(Collectors.toMap(QCloudHBase::getClusterId, e -> e, (a, b) -> a));

        List<QCloudHBase> toInsert = apiList.stream()
                .filter(e -> e.getClusterId() != null && !dbMap.containsKey(e.getClusterId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudHBaseMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudHBase> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudHBase::getConfName, cloudConf.getName())
                    .in(QCloudHBase::getClusterId, toDeleteIds)
                    .set(QCloudHBase::getDeleted, 1);
            qCloudHBaseMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== HSM ====================

    private int syncHSM(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== HEALTHDASH ====================

    private int syncHealthDash(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudHealthDash> apiList = qCloudClient.listHealthDash();
        List<QCloudHealthDash> dbList = qCloudHealthDashMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudHealthDash> apiMap = apiList.stream()
                .filter(e -> e.getDashboardId() != null)
                .collect(Collectors.toMap(QCloudHealthDash::getDashboardId, e -> e, (a, b) -> a));
        Map<String, QCloudHealthDash> dbMap = dbList.stream()
                .filter(e -> e.getDashboardId() != null)
                .collect(Collectors.toMap(QCloudHealthDash::getDashboardId, e -> e, (a, b) -> a));

        List<QCloudHealthDash> toInsert = apiList.stream()
                .filter(e -> e.getDashboardId() != null && !dbMap.containsKey(e.getDashboardId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudHealthDashMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudHealthDash> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudHealthDash::getConfName, cloudConf.getName())
                    .in(QCloudHealthDash::getDashboardId, toDeleteIds)
                    .set(QCloudHealthDash::getDeleted, 1);
            qCloudHealthDashMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== HEALTHOMICS ====================

    private int syncHealthOmics(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudHealthOmics> apiList = qCloudClient.listHealthOmics();
        List<QCloudHealthOmics> dbList = qCloudHealthOmicsMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudHealthOmics> apiMap = apiList.stream()
                .filter(e -> e.getDatasetId() != null)
                .collect(Collectors.toMap(QCloudHealthOmics::getDatasetId, e -> e, (a, b) -> a));
        Map<String, QCloudHealthOmics> dbMap = dbList.stream()
                .filter(e -> e.getDatasetId() != null)
                .collect(Collectors.toMap(QCloudHealthOmics::getDatasetId, e -> e, (a, b) -> a));

        List<QCloudHealthOmics> toInsert = apiList.stream()
                .filter(e -> e.getDatasetId() != null && !dbMap.containsKey(e.getDatasetId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudHealthOmicsMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudHealthOmics> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudHealthOmics::getConfName, cloudConf.getName())
                    .in(QCloudHealthOmics::getDatasetId, toDeleteIds)
                    .set(QCloudHealthOmics::getDeleted, 1);
            qCloudHealthOmicsMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== HEALTHREPORT2 ====================

    private int syncHealthReport2(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudHealthReport2> apiList = qCloudClient.listHealthReport2();
        List<QCloudHealthReport2> dbList = qCloudHealthReport2Mapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudHealthReport2> apiMap = apiList.stream()
                .filter(e -> e.getReportId() != null)
                .collect(Collectors.toMap(QCloudHealthReport2::getReportId, e -> e, (a, b) -> a));
        Map<String, QCloudHealthReport2> dbMap = dbList.stream()
                .filter(e -> e.getReportId() != null)
                .collect(Collectors.toMap(QCloudHealthReport2::getReportId, e -> e, (a, b) -> a));

        List<QCloudHealthReport2> toInsert = apiList.stream()
                .filter(e -> e.getReportId() != null && !dbMap.containsKey(e.getReportId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudHealthReport2Mapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudHealthReport2> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudHealthReport2::getConfName, cloudConf.getName())
                    .in(QCloudHealthReport2::getReportId, toDeleteIds)
                    .set(QCloudHealthReport2::getDeleted, 1);
            qCloudHealthReport2Mapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== ICPBEIAN ====================

    private int syncICPBeian(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudICPBeian> apiList = qCloudClient.listICPBeian();
        List<QCloudICPBeian> dbList = qCloudICPBeianMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudICPBeian> apiMap = apiList.stream()
                .filter(e -> e.getDomain() != null)
                .collect(Collectors.toMap(QCloudICPBeian::getDomain, e -> e, (a, b) -> a));
        Map<String, QCloudICPBeian> dbMap = dbList.stream()
                .filter(e -> e.getDomain() != null)
                .collect(Collectors.toMap(QCloudICPBeian::getDomain, e -> e, (a, b) -> a));

        List<QCloudICPBeian> toInsert = apiList.stream()
                .filter(e -> e.getDomain() != null && !dbMap.containsKey(e.getDomain()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudICPBeianMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudICPBeian> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudICPBeian::getConfName, cloudConf.getName())
                    .in(QCloudICPBeian::getDomain, toDeleteIds)
                    .set(QCloudICPBeian::getDeleted, 1);
            qCloudICPBeianMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== IOA ====================

    private int syncIOA(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudIOA> apiList = qCloudClient.listIOA();
        List<QCloudIOA> dbList = qCloudIOAMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudIOA> apiMap = apiList.stream()
                .filter(e -> e.getDeviceId() != null)
                .collect(Collectors.toMap(QCloudIOA::getDeviceId, e -> e, (a, b) -> a));
        Map<String, QCloudIOA> dbMap = dbList.stream()
                .filter(e -> e.getDeviceId() != null)
                .collect(Collectors.toMap(QCloudIOA::getDeviceId, e -> e, (a, b) -> a));

        List<QCloudIOA> toInsert = apiList.stream()
                .filter(e -> e.getDeviceId() != null && !dbMap.containsKey(e.getDeviceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudIOAMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudIOA> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudIOA::getConfName, cloudConf.getName())
                    .in(QCloudIOA::getDeviceId, toDeleteIds)
                    .set(QCloudIOA::getDeleted, 1);
            qCloudIOAMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== IMAGEPROCESS2 ====================

    private int syncImageProcess2(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    private int syncImageSearch(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== IOT ====================

    private int syncIoT(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudIoT> apiList = qCloudClient.listIoT();
        List<QCloudIoT> dbList = qCloudIoTMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudIoT> apiMap = apiList.stream()
                .filter(e -> e.getProductId() != null)
                .collect(Collectors.toMap(QCloudIoT::getProductId, e -> e, (a, b) -> a));
        Map<String, QCloudIoT> dbMap = dbList.stream()
                .filter(e -> e.getProductId() != null)
                .collect(Collectors.toMap(QCloudIoT::getProductId, e -> e, (a, b) -> a));

        List<QCloudIoT> toInsert = apiList.stream()
                .filter(e -> e.getProductId() != null && !dbMap.containsKey(e.getProductId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudIoTMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudIoT> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudIoT::getConfName, cloudConf.getName())
                    .in(QCloudIoT::getProductId, toDeleteIds)
                    .set(QCloudIoT::getDeleted, 1);
            qCloudIoTMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== IOTDEVICE ====================

    private int syncIoTDevice(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudIoTDevice> apiList = qCloudClient.listIoTDevice();
        List<QCloudIoTDevice> dbList = qCloudIoTDeviceMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudIoTDevice> apiMap = apiList.stream()
                .filter(e -> e.getDeviceId() != null)
                .collect(Collectors.toMap(QCloudIoTDevice::getDeviceId, e -> e, (a, b) -> a));
        Map<String, QCloudIoTDevice> dbMap = dbList.stream()
                .filter(e -> e.getDeviceId() != null)
                .collect(Collectors.toMap(QCloudIoTDevice::getDeviceId, e -> e, (a, b) -> a));

        List<QCloudIoTDevice> toInsert = apiList.stream()
                .filter(e -> e.getDeviceId() != null && !dbMap.containsKey(e.getDeviceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudIoTDeviceMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudIoTDevice> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudIoTDevice::getConfName, cloudConf.getName())
                    .in(QCloudIoTDevice::getDeviceId, toDeleteIds)
                    .set(QCloudIoTDevice::getDeleted, 1);
            qCloudIoTDeviceMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== IOTHUB ====================

    private int syncIoTHub(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudIoTHub> apiList = qCloudClient.listIoTHub();
        List<QCloudIoTHub> dbList = qCloudIoTHubMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudIoTHub> apiMap = apiList.stream()
                .filter(e -> e.getProductKey() != null)
                .collect(Collectors.toMap(QCloudIoTHub::getProductKey, e -> e, (a, b) -> a));
        Map<String, QCloudIoTHub> dbMap = dbList.stream()
                .filter(e -> e.getProductKey() != null)
                .collect(Collectors.toMap(QCloudIoTHub::getProductKey, e -> e, (a, b) -> a));

        List<QCloudIoTHub> toInsert = apiList.stream()
                .filter(e -> e.getProductKey() != null && !dbMap.containsKey(e.getProductKey()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudIoTHubMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudIoTHub> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudIoTHub::getConfName, cloudConf.getName())
                    .in(QCloudIoTHub::getProductKey, toDeleteIds)
                    .set(QCloudIoTHub::getDeleted, 1);
            qCloudIoTHubMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== KMS ====================

    private int syncKMS(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== KEEWIDB ====================

    private int syncKeeWiDB(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudKeeWiDB> apiList = qCloudClient.listKeeWiDB();
        List<QCloudKeeWiDB> dbList = qCloudKeeWiDBMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudKeeWiDB> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudKeeWiDB::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudKeeWiDB> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudKeeWiDB::getInstanceId, e -> e, (a, b) -> a));

        List<QCloudKeeWiDB> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudKeeWiDBMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudKeeWiDB> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudKeeWiDB::getConfName, cloudConf.getName())
                    .in(QCloudKeeWiDB::getInstanceId, toDeleteIds)
                    .set(QCloudKeeWiDB::getDeleted, 1);
            qCloudKeeWiDBMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== KNOWLEDGEENGINE ====================

    private int syncKnowledgeEngine(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== LIGHTHOUSE ====================

    private int syncLighthouse(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== LIVE ====================

    private int syncLive(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    private int syncLive2(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== MAIL ====================

    private int syncMail(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudMail> apiList = qCloudClient.listMail();
        List<QCloudMail> dbList = qCloudMailMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudMail> apiMap = apiList.stream()
                .filter(e -> e.getEmailAddress() != null)
                .collect(Collectors.toMap(QCloudMail::getEmailAddress, e -> e, (a, b) -> a));
        Map<String, QCloudMail> dbMap = dbList.stream()
                .filter(e -> e.getEmailAddress() != null)
                .collect(Collectors.toMap(QCloudMail::getEmailAddress, e -> e, (a, b) -> a));

        List<QCloudMail> toInsert = apiList.stream()
                .filter(e -> e.getEmailAddress() != null && !dbMap.containsKey(e.getEmailAddress()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudMailMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudMail> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudMail::getConfName, cloudConf.getName())
                    .in(QCloudMail::getEmailAddress, toDeleteIds)
                    .set(QCloudMail::getDeleted, 1);
            qCloudMailMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== MALLTRAFFIC ====================

    private int syncMallTraffic(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudMallTraffic> apiList = qCloudClient.listMallTraffic();
        List<QCloudMallTraffic> dbList = qCloudMallTrafficMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudMallTraffic> apiMap = apiList.stream()
                .filter(e -> e.getMallId() != null)
                .collect(Collectors.toMap(QCloudMallTraffic::getMallId, e -> e, (a, b) -> a));
        Map<String, QCloudMallTraffic> dbMap = dbList.stream()
                .filter(e -> e.getMallId() != null)
                .collect(Collectors.toMap(QCloudMallTraffic::getMallId, e -> e, (a, b) -> a));

        List<QCloudMallTraffic> toInsert = apiList.stream()
                .filter(e -> e.getMallId() != null && !dbMap.containsKey(e.getMallId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudMallTrafficMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudMallTraffic> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudMallTraffic::getConfName, cloudConf.getName())
                    .in(QCloudMallTraffic::getMallId, toDeleteIds)
                    .set(QCloudMallTraffic::getDeleted, 1);
            qCloudMallTrafficMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== MARIADB ====================

    private int syncMariaDb(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudMariaDb> apiList = qCloudClient.listMariaDb();
        List<QCloudMariaDb> dbList = qCloudMariaDbMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudMariaDb> apiMap = apiList.stream()
                .filter(e -> e.getInstanceName() != null)
                .collect(Collectors.toMap(QCloudMariaDb::getInstanceName, e -> e, (a, b) -> a));
        Map<String, QCloudMariaDb> dbMap = dbList.stream()
                .filter(e -> e.getInstanceName() != null)
                .collect(Collectors.toMap(QCloudMariaDb::getInstanceName, e -> e, (a, b) -> a));

        List<QCloudMariaDb> toInsert = apiList.stream()
                .filter(e -> e.getInstanceName() != null && !dbMap.containsKey(e.getInstanceName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudMariaDbMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudMariaDb> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudMariaDb::getConfName, cloudConf.getName())
                    .in(QCloudMariaDb::getInstanceName, toDeleteIds)
                    .set(QCloudMariaDb::getDeleted, 1);
            qCloudMariaDbMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== MATHGRADE ====================

    private int syncMathGrade(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== MEDIAASSET ====================

    private int syncMediaAsset(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== MEETING ====================

    private int syncMeeting(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudMeeting> apiList = qCloudClient.listMeeting();
        List<QCloudMeeting> dbList = qCloudMeetingMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudMeeting> apiMap = apiList.stream()
                .filter(e -> e.getMeetingId() != null)
                .collect(Collectors.toMap(QCloudMeeting::getMeetingId, e -> e, (a, b) -> a));
        Map<String, QCloudMeeting> dbMap = dbList.stream()
                .filter(e -> e.getMeetingId() != null)
                .collect(Collectors.toMap(QCloudMeeting::getMeetingId, e -> e, (a, b) -> a));

        List<QCloudMeeting> toInsert = apiList.stream()
                .filter(e -> e.getMeetingId() != null && !dbMap.containsKey(e.getMeetingId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudMeetingMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudMeeting> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudMeeting::getConfName, cloudConf.getName())
                    .in(QCloudMeeting::getMeetingId, toDeleteIds)
                    .set(QCloudMeeting::getDeleted, 1);
            qCloudMeetingMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== MEMCACHED ====================

    private int syncMemcached(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudMemcached> apiList = qCloudClient.listMemcached();
        List<QCloudMemcached> dbList = qCloudMemcachedMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudMemcached> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudMemcached::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudMemcached> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudMemcached::getInstanceId, e -> e, (a, b) -> a));

        List<QCloudMemcached> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudMemcachedMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudMemcached> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudMemcached::getConfName, cloudConf.getName())
                    .in(QCloudMemcached::getInstanceId, toDeleteIds)
                    .set(QCloudMemcached::getDeleted, 1);
            qCloudMemcachedMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== MICROWEDA ====================

    private int syncMicroWeda(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudMicroWeda> apiList = qCloudClient.listMicroWeda();
        List<QCloudMicroWeda> dbList = qCloudMicroWedaMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudMicroWeda> apiMap = apiList.stream()
                .filter(e -> e.getAppId() != null)
                .collect(Collectors.toMap(QCloudMicroWeda::getAppId, e -> e, (a, b) -> a));
        Map<String, QCloudMicroWeda> dbMap = dbList.stream()
                .filter(e -> e.getAppId() != null)
                .collect(Collectors.toMap(QCloudMicroWeda::getAppId, e -> e, (a, b) -> a));

        List<QCloudMicroWeda> toInsert = apiList.stream()
                .filter(e -> e.getAppId() != null && !dbMap.containsKey(e.getAppId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudMicroWedaMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudMicroWeda> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudMicroWeda::getConfName, cloudConf.getName())
                    .in(QCloudMicroWeda::getAppId, toDeleteIds)
                    .set(QCloudMicroWeda::getDeleted, 1);
            qCloudMicroWedaMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== MINISAFE ====================

    private int syncMiniSafe(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== MONGODB_CKAFKA ====================

    private int syncMongoDB_CKafka(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudMongoDB_CKafka> apiList = qCloudClient.listMongoDB_CKafka();
        List<QCloudMongoDB_CKafka> dbList = qCloudMongoDB_CKafkaMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudMongoDB_CKafka> apiMap = apiList.stream()
                .filter(e -> e.getDedicatedClusterId() != null)
                .collect(Collectors.toMap(QCloudMongoDB_CKafka::getDedicatedClusterId, e -> e, (a, b) -> a));
        Map<String, QCloudMongoDB_CKafka> dbMap = dbList.stream()
                .filter(e -> e.getDedicatedClusterId() != null)
                .collect(Collectors.toMap(QCloudMongoDB_CKafka::getDedicatedClusterId, e -> e, (a, b) -> a));

        List<QCloudMongoDB_CKafka> toInsert = apiList.stream()
                .filter(e -> e.getDedicatedClusterId() != null && !dbMap.containsKey(e.getDedicatedClusterId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudMongoDB_CKafkaMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudMongoDB_CKafka> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudMongoDB_CKafka::getConfName, cloudConf.getName())
                    .in(QCloudMongoDB_CKafka::getDedicatedClusterId, toDeleteIds)
                    .set(QCloudMongoDB_CKafka::getDeleted, 1);
            qCloudMongoDB_CKafkaMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== MONGODB ====================

    private int syncMongoDb(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudMongoDb> apiList = qCloudClient.listMongoDb();
        List<QCloudMongoDb> dbList = qCloudMongoDbMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudMongoDb> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudMongoDb::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudMongoDb> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudMongoDb::getInstanceId, e -> e, (a, b) -> a));

        List<QCloudMongoDb> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudMongoDbMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudMongoDb> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudMongoDb::getConfName, cloudConf.getName())
                    .in(QCloudMongoDb::getInstanceId, toDeleteIds)
                    .set(QCloudMongoDb::getDeleted, 1);
            qCloudMongoDbMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== MONITOR ====================

    private int syncMonitor(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudMonitor> apiList = qCloudClient.listMonitor();
        List<QCloudMonitor> dbList = qCloudMonitorMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudMonitor> apiMap = apiList.stream()
                .filter(e -> e.getNamespaceValue() != null)
                .collect(Collectors.toMap(QCloudMonitor::getNamespaceValue, e -> e, (a, b) -> a));
        Map<String, QCloudMonitor> dbMap = dbList.stream()
                .filter(e -> e.getNamespaceValue() != null)
                .collect(Collectors.toMap(QCloudMonitor::getNamespaceValue, e -> e, (a, b) -> a));

        List<QCloudMonitor> toInsert = apiList.stream()
                .filter(e -> e.getNamespaceValue() != null && !dbMap.containsKey(e.getNamespaceValue()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudMonitorMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudMonitor> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudMonitor::getConfName, cloudConf.getName())
                    .in(QCloudMonitor::getNamespaceValue, toDeleteIds)
                    .set(QCloudMonitor::getDeleted, 1);
            qCloudMonitorMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== NMT ====================

    private int syncNMT(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== NATGATEWAY ====================

    private int syncNatGateway(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudNatGateway> apiList = qCloudClient.listNatGateway();
        List<QCloudNatGateway> dbList = qCloudNatGatewayMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudNatGateway> apiMap = apiList.stream()
                .filter(e -> e.getNatGatewayId() != null)
                .collect(Collectors.toMap(QCloudNatGateway::getNatGatewayId, e -> e, (a, b) -> a));
        Map<String, QCloudNatGateway> dbMap = dbList.stream()
                .filter(e -> e.getNatGatewayId() != null)
                .collect(Collectors.toMap(QCloudNatGateway::getNatGatewayId, e -> e, (a, b) -> a));

        List<QCloudNatGateway> toInsert = apiList.stream()
                .filter(e -> e.getNatGatewayId() != null && !dbMap.containsKey(e.getNatGatewayId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudNatGatewayMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudNatGateway> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudNatGateway::getConfName, cloudConf.getName())
                    .in(QCloudNatGateway::getNatGatewayId, toDeleteIds)
                    .set(QCloudNatGateway::getDeleted, 1);
            qCloudNatGatewayMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== NATIVEBUILD ====================

    private int syncNativeBuild(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== OCR ====================

    private int syncOCR(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== OCEANUS ====================

    private int syncOceanus(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudOceanus> apiList = qCloudClient.listOceanus();
        List<QCloudOceanus> dbList = qCloudOceanusMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudOceanus> apiMap = apiList.stream()
                .filter(e -> e.getJobId() != null)
                .collect(Collectors.toMap(QCloudOceanus::getJobId, e -> e, (a, b) -> a));
        Map<String, QCloudOceanus> dbMap = dbList.stream()
                .filter(e -> e.getJobId() != null)
                .collect(Collectors.toMap(QCloudOceanus::getJobId, e -> e, (a, b) -> a));

        List<QCloudOceanus> toInsert = apiList.stream()
                .filter(e -> e.getJobId() != null && !dbMap.containsKey(e.getJobId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudOceanusMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudOceanus> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudOceanus::getConfName, cloudConf.getName())
                    .in(QCloudOceanus::getJobId, toDeleteIds)
                    .set(QCloudOceanus::getDeleted, 1);
            qCloudOceanusMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== ORG ====================

    private int syncOrg(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudOrg> apiList = qCloudClient.listOrg();
        List<QCloudOrg> dbList = qCloudOrgMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudOrg> apiMap = apiList.stream()
                .filter(e -> e.getOrgId() != null)
                .collect(Collectors.toMap(QCloudOrg::getOrgId, e -> e, (a, b) -> a));
        Map<String, QCloudOrg> dbMap = dbList.stream()
                .filter(e -> e.getOrgId() != null)
                .collect(Collectors.toMap(QCloudOrg::getOrgId, e -> e, (a, b) -> a));

        List<QCloudOrg> toInsert = apiList.stream()
                .filter(e -> e.getOrgId() != null && !dbMap.containsKey(e.getOrgId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudOrgMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudOrg> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudOrg::getConfName, cloudConf.getName())
                    .in(QCloudOrg::getOrgId, toDeleteIds)
                    .set(QCloudOrg::getDeleted, 1);
            qCloudOrgMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== PENTEST ====================

    private int syncPenTest(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== POSTGRESQL ====================

    private int syncPostgresql(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudPostgresql> apiList = qCloudClient.listPostgresql();
        List<QCloudPostgresql> dbList = qCloudPostgresqlMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudPostgresql> apiMap = apiList.stream()
                .filter(e -> e.getDBInstanceId() != null)
                .collect(Collectors.toMap(QCloudPostgresql::getDBInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudPostgresql> dbMap = dbList.stream()
                .filter(e -> e.getDBInstanceId() != null)
                .collect(Collectors.toMap(QCloudPostgresql::getDBInstanceId, e -> e, (a, b) -> a));

        List<QCloudPostgresql> toInsert = apiList.stream()
                .filter(e -> e.getDBInstanceId() != null && !dbMap.containsKey(e.getDBInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudPostgresqlMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudPostgresql> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudPostgresql::getConfName, cloudConf.getName())
                    .in(QCloudPostgresql::getDBInstanceId, toDeleteIds)
                    .set(QCloudPostgresql::getDeleted, 1);
            qCloudPostgresqlMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== PRIVDNS ====================

    private int syncPrivDNS(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudPrivDNS> apiList = qCloudClient.listPrivDNS();
        List<QCloudPrivDNS> dbList = qCloudPrivDNSMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudPrivDNS> apiMap = apiList.stream()
                .filter(e -> e.getZoneId() != null)
                .collect(Collectors.toMap(QCloudPrivDNS::getZoneId, e -> e, (a, b) -> a));
        Map<String, QCloudPrivDNS> dbMap = dbList.stream()
                .filter(e -> e.getZoneId() != null)
                .collect(Collectors.toMap(QCloudPrivDNS::getZoneId, e -> e, (a, b) -> a));

        List<QCloudPrivDNS> toInsert = apiList.stream()
                .filter(e -> e.getZoneId() != null && !dbMap.containsKey(e.getZoneId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudPrivDNSMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudPrivDNS> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudPrivDNS::getConfName, cloudConf.getName())
                    .in(QCloudPrivDNS::getZoneId, toDeleteIds)
                    .set(QCloudPrivDNS::getDeleted, 1);
            qCloudPrivDNSMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== RTIEDU ====================

    private int syncRTIEdu(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudRTIEdu> apiList = qCloudClient.listRTIEdu();
        List<QCloudRTIEdu> dbList = qCloudRTIEduMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudRTIEdu> apiMap = apiList.stream()
                .filter(e -> e.getSdkAppId() != null)
                .collect(Collectors.toMap(e -> String.valueOf(e.getSdkAppId()), e -> e, (a, b) -> a));
        Map<String, QCloudRTIEdu> dbMap = dbList.stream()
                .filter(e -> e.getSdkAppId() != null)
                .collect(Collectors.toMap(e -> String.valueOf(e.getSdkAppId()), e -> e, (a, b) -> a));

        List<QCloudRTIEdu> toInsert = apiList.stream()
                .filter(e -> e.getSdkAppId() != null && !dbMap.containsKey(String.valueOf(e.getSdkAppId())))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudRTIEduMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudRTIEdu> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudRTIEdu::getConfName, cloudConf.getName())
                    .in(QCloudRTIEdu::getSdkAppId, toDeleteIds)
                    .set(QCloudRTIEdu::getDeleted, 1);
            qCloudRTIEduMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== RTIINDUSTRIAL ====================

    private int syncRTIIndustrial(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudRTIIndustrial> apiList = qCloudClient.listRTIIndustrial();
        List<QCloudRTIIndustrial> dbList = qCloudRTIIndustrialMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudRTIIndustrial> apiMap = apiList.stream()
                .filter(e -> e.getSdkAppId() != null)
                .collect(Collectors.toMap(e -> String.valueOf(e.getSdkAppId()), e -> e, (a, b) -> a));
        Map<String, QCloudRTIIndustrial> dbMap = dbList.stream()
                .filter(e -> e.getSdkAppId() != null)
                .collect(Collectors.toMap(e -> String.valueOf(e.getSdkAppId()), e -> e, (a, b) -> a));

        List<QCloudRTIIndustrial> toInsert = apiList.stream()
                .filter(e -> e.getSdkAppId() != null && !dbMap.containsKey(String.valueOf(e.getSdkAppId())))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudRTIIndustrialMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudRTIIndustrial> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudRTIIndustrial::getConfName, cloudConf.getName())
                    .in(QCloudRTIIndustrial::getSdkAppId, toDeleteIds)
                    .set(QCloudRTIIndustrial::getDeleted, 1);
            qCloudRTIIndustrialMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== RABBITMQ ====================

    private int syncRabbitMQ(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudRabbitMQ> apiList = qCloudClient.listRabbitMQ();
        List<QCloudRabbitMQ> dbList = qCloudRabbitMQMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudRabbitMQ> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudRabbitMQ::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudRabbitMQ> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudRabbitMQ::getInstanceId, e -> e, (a, b) -> a));

        List<QCloudRabbitMQ> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudRabbitMQMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudRabbitMQ> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudRabbitMQ::getConfName, cloudConf.getName())
                    .in(QCloudRabbitMQ::getInstanceId, toDeleteIds)
                    .set(QCloudRabbitMQ::getDeleted, 1);
            qCloudRabbitMQMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== REDIS ====================

    private int syncRedis(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudRedis> apiList = qCloudClient.listRedis();
        List<QCloudRedis> dbList = qCloudRedisMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudRedis> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudRedis::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudRedis> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudRedis::getInstanceId, e -> e, (a, b) -> a));

        List<QCloudRedis> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudRedisMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudRedis> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudRedis::getConfName, cloudConf.getName())
                    .in(QCloudRedis::getInstanceId, toDeleteIds)
                    .set(QCloudRedis::getDeleted, 1);
            qCloudRedisMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== REGIONMGR ====================

    private int syncRegionMgr(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudRegionMgr> apiList = qCloudClient.listRegionMgr();
        List<QCloudRegionMgr> dbList = qCloudRegionMgrMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudRegionMgr> apiMap = apiList.stream()
                .filter(e -> e.getRegionId() != null)
                .collect(Collectors.toMap(QCloudRegionMgr::getRegionId, e -> e, (a, b) -> a));
        Map<String, QCloudRegionMgr> dbMap = dbList.stream()
                .filter(e -> e.getRegionId() != null)
                .collect(Collectors.toMap(QCloudRegionMgr::getRegionId, e -> e, (a, b) -> a));

        List<QCloudRegionMgr> toInsert = apiList.stream()
                .filter(e -> e.getRegionId() != null && !dbMap.containsKey(e.getRegionId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudRegionMgrMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudRegionMgr> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudRegionMgr::getConfName, cloudConf.getName())
                    .in(QCloudRegionMgr::getRegionId, toDeleteIds)
                    .set(QCloudRegionMgr::getDeleted, 1);
            qCloudRegionMgrMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== RISKIDENTIFY ====================

    private int syncRiskIdentify(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== ROCKETMQ ====================

    private int syncRocketMQ(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudRocketMQ> apiList = qCloudClient.listRocketMQ();
        List<QCloudRocketMQ> dbList = qCloudRocketMQMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudRocketMQ> apiMap = apiList.stream()
                .filter(e -> e.getInstanceName() != null)
                .collect(Collectors.toMap(QCloudRocketMQ::getInstanceName, e -> e, (a, b) -> a));
        Map<String, QCloudRocketMQ> dbMap = dbList.stream()
                .filter(e -> e.getInstanceName() != null)
                .collect(Collectors.toMap(QCloudRocketMQ::getInstanceName, e -> e, (a, b) -> a));

        List<QCloudRocketMQ> toInsert = apiList.stream()
                .filter(e -> e.getInstanceName() != null && !dbMap.containsKey(e.getInstanceName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudRocketMQMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudRocketMQ> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudRocketMQ::getConfName, cloudConf.getName())
                    .in(QCloudRocketMQ::getInstanceName, toDeleteIds)
                    .set(QCloudRocketMQ::getDeleted, 1);
            qCloudRocketMQMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== SES ====================

    private int syncSES(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudSES> apiList = qCloudClient.listSES();
        List<QCloudSES> dbList = qCloudSESMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudSES> apiMap = apiList.stream()
                .filter(e -> e.getEmailAddress() != null)
                .collect(Collectors.toMap(QCloudSES::getEmailAddress, e -> e, (a, b) -> a));
        Map<String, QCloudSES> dbMap = dbList.stream()
                .filter(e -> e.getEmailAddress() != null)
                .collect(Collectors.toMap(QCloudSES::getEmailAddress, e -> e, (a, b) -> a));

        List<QCloudSES> toInsert = apiList.stream()
                .filter(e -> e.getEmailAddress() != null && !dbMap.containsKey(e.getEmailAddress()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudSESMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudSES> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudSES::getConfName, cloudConf.getName())
                    .in(QCloudSES::getEmailAddress, toDeleteIds)
                    .set(QCloudSES::getDeleted, 1);
            qCloudSESMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== SMS ====================

    private int syncSMS(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudSMS> apiList = qCloudClient.listSMS();
        List<QCloudSMS> dbList = qCloudSMSMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudSMS> apiMap = apiList.stream()
                .filter(e -> e.getSignName() != null)
                .collect(Collectors.toMap(QCloudSMS::getSignName, e -> e, (a, b) -> a));
        Map<String, QCloudSMS> dbMap = dbList.stream()
                .filter(e -> e.getSignName() != null)
                .collect(Collectors.toMap(QCloudSMS::getSignName, e -> e, (a, b) -> a));

        List<QCloudSMS> toInsert = apiList.stream()
                .filter(e -> e.getSignName() != null && !dbMap.containsKey(e.getSignName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudSMSMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudSMS> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudSMS::getConfName, cloudConf.getName())
                    .in(QCloudSMS::getSignName, toDeleteIds)
                    .set(QCloudSMS::getDeleted, 1);
            qCloudSMSMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== SSL ====================

    private int syncSSL(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    private int syncSSLPod(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    private int syncSafeAudio(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    private int syncSafeCenter(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    private int syncSafeDoc(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    private int syncSafeGuard(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    private int syncSafeImage(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    private int syncSafeMonitor(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    private int syncSafePlatform(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    private int syncSafeText(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== SAFEVIDEO ====================

    private int syncSafeVideo(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== SCF ====================

    private int syncScf(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== SECCREDENTIAL ====================

    private int syncSecCredential(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    private int syncSecretMgr(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    private int syncSecurityGroup(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== SMARTADVISOR ====================

    private int syncSmartAdvisor(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudSmartAdvisor> apiList = qCloudClient.listSmartAdvisor();
        List<QCloudSmartAdvisor> dbList = qCloudSmartAdvisorMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudSmartAdvisor> apiMap = apiList.stream()
                .filter(e -> e.getSuggestionId() != null)
                .collect(Collectors.toMap(QCloudSmartAdvisor::getSuggestionId, e -> e, (a, b) -> a));
        Map<String, QCloudSmartAdvisor> dbMap = dbList.stream()
                .filter(e -> e.getSuggestionId() != null)
                .collect(Collectors.toMap(QCloudSmartAdvisor::getSuggestionId, e -> e, (a, b) -> a));

        List<QCloudSmartAdvisor> toInsert = apiList.stream()
                .filter(e -> e.getSuggestionId() != null && !dbMap.containsKey(e.getSuggestionId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudSmartAdvisorMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudSmartAdvisor> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudSmartAdvisor::getConfName, cloudConf.getName())
                    .in(QCloudSmartAdvisor::getSuggestionId, toDeleteIds)
                    .set(QCloudSmartAdvisor::getDeleted, 1);
            qCloudSmartAdvisorMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== SMARTGUIDE ====================

    private int syncSmartGuide(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudSmartGuide> apiList = qCloudClient.listSmartGuide();
        List<QCloudSmartGuide> dbList = qCloudSmartGuideMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudSmartGuide> apiMap = apiList.stream()
                .filter(e -> e.getHospitalId() != null)
                .collect(Collectors.toMap(QCloudSmartGuide::getHospitalId, e -> e, (a, b) -> a));
        Map<String, QCloudSmartGuide> dbMap = dbList.stream()
                .filter(e -> e.getHospitalId() != null)
                .collect(Collectors.toMap(QCloudSmartGuide::getHospitalId, e -> e, (a, b) -> a));

        List<QCloudSmartGuide> toInsert = apiList.stream()
                .filter(e -> e.getHospitalId() != null && !dbMap.containsKey(e.getHospitalId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudSmartGuideMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudSmartGuide> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudSmartGuide::getConfName, cloudConf.getName())
                    .in(QCloudSmartGuide::getHospitalId, toDeleteIds)
                    .set(QCloudSmartGuide::getDeleted, 1);
            qCloudSmartGuideMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== SMARTVIEW ====================

    private int syncSmartView(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudSmartView> apiList = qCloudClient.listSmartView();
        List<QCloudSmartView> dbList = qCloudSmartViewMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudSmartView> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudSmartView::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudSmartView> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudSmartView::getInstanceId, e -> e, (a, b) -> a));

        List<QCloudSmartView> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudSmartViewMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudSmartView> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudSmartView::getConfName, cloudConf.getName())
                    .in(QCloudSmartView::getInstanceId, toDeleteIds)
                    .set(QCloudSmartView::getDeleted, 1);
            qCloudSmartViewMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== SMSSIGN ====================

    private int syncSmsSign(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudSmsSign> apiList = qCloudClient.listSmsSign();
        List<QCloudSmsSign> dbList = qCloudSmsSignMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudSmsSign> apiMap = apiList.stream()
                .filter(e -> e.getSignName() != null)
                .collect(Collectors.toMap(QCloudSmsSign::getSignName, e -> e, (a, b) -> a));
        Map<String, QCloudSmsSign> dbMap = dbList.stream()
                .filter(e -> e.getSignName() != null)
                .collect(Collectors.toMap(QCloudSmsSign::getSignName, e -> e, (a, b) -> a));

        List<QCloudSmsSign> toInsert = apiList.stream()
                .filter(e -> e.getSignName() != null && !dbMap.containsKey(e.getSignName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudSmsSignMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudSmsSign> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudSmsSign::getConfName, cloudConf.getName())
                    .in(QCloudSmsSign::getSignName, toDeleteIds)
                    .set(QCloudSmsSign::getDeleted, 1);
            qCloudSmsSignMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== SMSTEMPLATE ====================

    private int syncSmsTemplate(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudSmsTemplate> apiList = qCloudClient.listSmsTemplate();
        List<QCloudSmsTemplate> dbList = qCloudSmsTemplateMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudSmsTemplate> apiMap = apiList.stream()
                .filter(e -> e.getTemplateName() != null)
                .collect(Collectors.toMap(QCloudSmsTemplate::getTemplateName, e -> e, (a, b) -> a));
        Map<String, QCloudSmsTemplate> dbMap = dbList.stream()
                .filter(e -> e.getTemplateName() != null)
                .collect(Collectors.toMap(QCloudSmsTemplate::getTemplateName, e -> e, (a, b) -> a));

        List<QCloudSmsTemplate> toInsert = apiList.stream()
                .filter(e -> e.getTemplateName() != null && !dbMap.containsKey(e.getTemplateName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudSmsTemplateMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudSmsTemplate> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudSmsTemplate::getConfName, cloudConf.getName())
                    .in(QCloudSmsTemplate::getTemplateName, toDeleteIds)
                    .set(QCloudSmsTemplate::getDeleted, 1);
            qCloudSmsTemplateMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== SPOKENEVAL ====================

    private int syncSpokenEval(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== SQLSERVER ====================

    private int syncSqlserver(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudSqlserver> apiList = qCloudClient.listSqlserver();
        List<QCloudSqlserver> dbList = qCloudSqlserverMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudSqlserver> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudSqlserver::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudSqlserver> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudSqlserver::getInstanceId, e -> e, (a, b) -> a));

        List<QCloudSqlserver> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudSqlserverMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudSqlserver> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudSqlserver::getConfName, cloudConf.getName())
                    .in(QCloudSqlserver::getInstanceId, toDeleteIds)
                    .set(QCloudSqlserver::getDeleted, 1);
            qCloudSqlserverMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== TAPD ====================

    private int syncTAPD(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudTAPD> apiList = qCloudClient.listTAPD();
        List<QCloudTAPD> dbList = qCloudTAPDMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudTAPD> apiMap = apiList.stream()
                .filter(e -> e.getProjectId() != null)
                .collect(Collectors.toMap(QCloudTAPD::getProjectId, e -> e, (a, b) -> a));
        Map<String, QCloudTAPD> dbMap = dbList.stream()
                .filter(e -> e.getProjectId() != null)
                .collect(Collectors.toMap(QCloudTAPD::getProjectId, e -> e, (a, b) -> a));

        List<QCloudTAPD> toInsert = apiList.stream()
                .filter(e -> e.getProjectId() != null && !dbMap.containsKey(e.getProjectId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudTAPDMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudTAPD> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudTAPD::getConfName, cloudConf.getName())
                    .in(QCloudTAPD::getProjectId, toDeleteIds)
                    .set(QCloudTAPD::getDeleted, 1);
            qCloudTAPDMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== TBAAS ====================

    private int syncTBAAS(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudTBAAS> apiList = qCloudClient.listTBAAS();
        List<QCloudTBAAS> dbList = qCloudTBAASMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudTBAAS> apiMap = apiList.stream()
                .filter(e -> e.getClusterId() != null)
                .collect(Collectors.toMap(QCloudTBAAS::getClusterId, e -> e, (a, b) -> a));
        Map<String, QCloudTBAAS> dbMap = dbList.stream()
                .filter(e -> e.getClusterId() != null)
                .collect(Collectors.toMap(QCloudTBAAS::getClusterId, e -> e, (a, b) -> a));

        List<QCloudTBAAS> toInsert = apiList.stream()
                .filter(e -> e.getClusterId() != null && !dbMap.containsKey(e.getClusterId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudTBAASMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudTBAAS> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudTBAAS::getConfName, cloudConf.getName())
                    .in(QCloudTBAAS::getClusterId, toDeleteIds)
                    .set(QCloudTBAAS::getDeleted, 1);
            qCloudTBAASMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== TCB ====================

    private int syncTCB(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== TCHOUSEC ====================

    private int syncTCHouseC(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudTCHouseC> apiList = qCloudClient.listTCHouseC();
        List<QCloudTCHouseC> dbList = qCloudTCHouseCMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudTCHouseC> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudTCHouseC::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudTCHouseC> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudTCHouseC::getInstanceId, e -> e, (a, b) -> a));

        List<QCloudTCHouseC> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudTCHouseCMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudTCHouseC> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudTCHouseC::getConfName, cloudConf.getName())
                    .in(QCloudTCHouseC::getInstanceId, toDeleteIds)
                    .set(QCloudTCHouseC::getDeleted, 1);
            qCloudTCHouseCMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== TCHOUSED ====================

    private int syncTCHouseD(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudTCHouseD> apiList = qCloudClient.listTCHouseD();
        List<QCloudTCHouseD> dbList = qCloudTCHouseDMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudTCHouseD> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudTCHouseD::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudTCHouseD> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudTCHouseD::getInstanceId, e -> e, (a, b) -> a));

        List<QCloudTCHouseD> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudTCHouseDMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudTCHouseD> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudTCHouseD::getConfName, cloudConf.getName())
                    .in(QCloudTCHouseD::getInstanceId, toDeleteIds)
                    .set(QCloudTCHouseD::getDeleted, 1);
            qCloudTCHouseDMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== TCHOUSEP ====================

    private int syncTCHouseP(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudTCHouseP> apiList = qCloudClient.listTCHouseP();
        List<QCloudTCHouseP> dbList = qCloudTCHousePMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudTCHouseP> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudTCHouseP::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudTCHouseP> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudTCHouseP::getInstanceId, e -> e, (a, b) -> a));

        List<QCloudTCHouseP> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudTCHousePMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudTCHouseP> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudTCHouseP::getConfName, cloudConf.getName())
                    .in(QCloudTCHouseP::getInstanceId, toDeleteIds)
                    .set(QCloudTCHouseP::getDeleted, 1);
            qCloudTCHousePMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== TCR ====================

    private int syncTCR(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudTCR> apiList = qCloudClient.listTCR();
        List<QCloudTCR> dbList = qCloudTCRMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudTCR> apiMap = apiList.stream()
                .filter(e -> e.getRegistryId() != null)
                .collect(Collectors.toMap(QCloudTCR::getRegistryId, e -> e, (a, b) -> a));
        Map<String, QCloudTCR> dbMap = dbList.stream()
                .filter(e -> e.getRegistryId() != null)
                .collect(Collectors.toMap(QCloudTCR::getRegistryId, e -> e, (a, b) -> a));

        List<QCloudTCR> toInsert = apiList.stream()
                .filter(e -> e.getRegistryId() != null && !dbMap.containsKey(e.getRegistryId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudTCRMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudTCR> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudTCR::getConfName, cloudConf.getName())
                    .in(QCloudTCR::getRegistryId, toDeleteIds)
                    .set(QCloudTCR::getDeleted, 1);
            qCloudTCRMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== TDMQ ====================

    private int syncTDMQ(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudTDMQ> apiList = qCloudClient.listTDMQ();
        List<QCloudTDMQ> dbList = qCloudTDMQMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudTDMQ> apiMap = apiList.stream()
                .filter(e -> e.getClusterId() != null)
                .collect(Collectors.toMap(QCloudTDMQ::getClusterId, e -> e, (a, b) -> a));
        Map<String, QCloudTDMQ> dbMap = dbList.stream()
                .filter(e -> e.getClusterId() != null)
                .collect(Collectors.toMap(QCloudTDMQ::getClusterId, e -> e, (a, b) -> a));

        List<QCloudTDMQ> toInsert = apiList.stream()
                .filter(e -> e.getClusterId() != null && !dbMap.containsKey(e.getClusterId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudTDMQMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudTDMQ> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudTDMQ::getConfName, cloudConf.getName())
                    .in(QCloudTDMQ::getClusterId, toDeleteIds)
                    .set(QCloudTDMQ::getDeleted, 1);
            qCloudTDMQMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== TI ====================

    private int syncTI(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    private int syncTIHai(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== TKE ====================

    private int syncTKE(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== TRTC ====================

    private int syncTRTC(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    private int syncTRTCRoom(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== TSE ====================

    private int syncTSE(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudTSE> apiList = qCloudClient.listTSE();
        List<QCloudTSE> dbList = qCloudTSEMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudTSE> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudTSE::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudTSE> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudTSE::getInstanceId, e -> e, (a, b) -> a));

        List<QCloudTSE> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudTSEMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudTSE> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudTSE::getConfName, cloudConf.getName())
                    .in(QCloudTSE::getInstanceId, toDeleteIds)
                    .set(QCloudTSE::getDeleted, 1);
            qCloudTSEMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== TSF ====================

    private int syncTSF(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== TTS ====================

    private int syncTTS(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== TCAPLUSDB ====================

    private int syncTcaplusDB(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudTcaplusDB> apiList = qCloudClient.listTcaplusDB();
        List<QCloudTcaplusDB> dbList = qCloudTcaplusDBMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudTcaplusDB> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudTcaplusDB::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudTcaplusDB> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudTcaplusDB::getInstanceId, e -> e, (a, b) -> a));

        List<QCloudTcaplusDB> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudTcaplusDBMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudTcaplusDB> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudTcaplusDB::getConfName, cloudConf.getName())
                    .in(QCloudTcaplusDB::getInstanceId, toDeleteIds)
                    .set(QCloudTcaplusDB::getDeleted, 1);
            qCloudTcaplusDBMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== TCRENT ====================

    private int syncTcrEnt(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudTcrEnt> apiList = qCloudClient.listTcrEnt();
        List<QCloudTcrEnt> dbList = qCloudTcrEntMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudTcrEnt> apiMap = apiList.stream()
                .filter(e -> e.getRegistryId() != null)
                .collect(Collectors.toMap(QCloudTcrEnt::getRegistryId, e -> e, (a, b) -> a));
        Map<String, QCloudTcrEnt> dbMap = dbList.stream()
                .filter(e -> e.getRegistryId() != null)
                .collect(Collectors.toMap(QCloudTcrEnt::getRegistryId, e -> e, (a, b) -> a));

        List<QCloudTcrEnt> toInsert = apiList.stream()
                .filter(e -> e.getRegistryId() != null && !dbMap.containsKey(e.getRegistryId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudTcrEntMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudTcrEnt> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudTcrEnt::getConfName, cloudConf.getName())
                    .in(QCloudTcrEnt::getRegistryId, toDeleteIds)
                    .set(QCloudTcrEnt::getDeleted, 1);
            qCloudTcrEntMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== TENCENTCONNECT ====================

    private int syncTencentConnect(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudTencentConnect> apiList = qCloudClient.listTencentConnect();
        List<QCloudTencentConnect> dbList = qCloudTencentConnectMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudTencentConnect> apiMap = apiList.stream()
                .filter(e -> e.getConnectorId() != null)
                .collect(Collectors.toMap(QCloudTencentConnect::getConnectorId, e -> e, (a, b) -> a));
        Map<String, QCloudTencentConnect> dbMap = dbList.stream()
                .filter(e -> e.getConnectorId() != null)
                .collect(Collectors.toMap(QCloudTencentConnect::getConnectorId, e -> e, (a, b) -> a));

        List<QCloudTencentConnect> toInsert = apiList.stream()
                .filter(e -> e.getConnectorId() != null && !dbMap.containsKey(e.getConnectorId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudTencentConnectMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudTencentConnect> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudTencentConnect::getConfName, cloudConf.getName())
                    .in(QCloudTencentConnect::getConnectorId, toDeleteIds)
                    .set(QCloudTencentConnect::getDeleted, 1);
            qCloudTencentConnectMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== TENDIS ====================

    private int syncTendis(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudTendis> apiList = qCloudClient.listTendis();
        List<QCloudTendis> dbList = qCloudTendisMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudTendis> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudTendis::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudTendis> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudTendis::getInstanceId, e -> e, (a, b) -> a));

        List<QCloudTendis> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudTendisMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudTendis> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudTendis::getConfName, cloudConf.getName())
                    .in(QCloudTendis::getInstanceId, toDeleteIds)
                    .set(QCloudTendis::getDeleted, 1);
            qCloudTendisMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== TOKENHUB ====================

    private int syncTokenHub(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudTokenHub> apiList = qCloudClient.listTokenHub();
        List<QCloudTokenHub> dbList = qCloudTokenHubMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudTokenHub> apiMap = apiList.stream()
                .filter(e -> e.getModelName() != null)
                .collect(Collectors.toMap(QCloudTokenHub::getModelName, e -> e, (a, b) -> a));
        Map<String, QCloudTokenHub> dbMap = dbList.stream()
                .filter(e -> e.getModelName() != null)
                .collect(Collectors.toMap(QCloudTokenHub::getModelName, e -> e, (a, b) -> a));

        List<QCloudTokenHub> toInsert = apiList.stream()
                .filter(e -> e.getModelName() != null && !dbMap.containsKey(e.getModelName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudTokenHubMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudTokenHub> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudTokenHub::getConfName, cloudConf.getName())
                    .in(QCloudTokenHub::getModelName, toDeleteIds)
                    .set(QCloudTokenHub::getDeleted, 1);
            qCloudTokenHubMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== TOURISMBIGDATA ====================

    private int syncTourismBigdata(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudTourismBigdata> apiList = qCloudClient.listTourismBigdata();
        List<QCloudTourismBigdata> dbList = qCloudTourismBigdataMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudTourismBigdata> apiMap = apiList.stream()
                .filter(e -> e.getSceneId() != null)
                .collect(Collectors.toMap(QCloudTourismBigdata::getSceneId, e -> e, (a, b) -> a));
        Map<String, QCloudTourismBigdata> dbMap = dbList.stream()
                .filter(e -> e.getSceneId() != null)
                .collect(Collectors.toMap(QCloudTourismBigdata::getSceneId, e -> e, (a, b) -> a));

        List<QCloudTourismBigdata> toInsert = apiList.stream()
                .filter(e -> e.getSceneId() != null && !dbMap.containsKey(e.getSceneId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudTourismBigdataMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudTourismBigdata> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudTourismBigdata::getConfName, cloudConf.getName())
                    .in(QCloudTourismBigdata::getSceneId, toDeleteIds)
                    .set(QCloudTourismBigdata::getDeleted, 1);
            qCloudTourismBigdataMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== USER ====================

    private int syncUser(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudUser> apiList = qCloudClient.listUsers();
        List<QCloudUser> dbList = qCloudUserMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudUser> apiMap = apiList.stream()
                .filter(e -> e.getName() != null)
                .collect(Collectors.toMap(QCloudUser::getName, e -> e, (a, b) -> a));
        Map<String, QCloudUser> dbMap = dbList.stream()
                .filter(e -> e.getName() != null)
                .collect(Collectors.toMap(QCloudUser::getName, e -> e, (a, b) -> a));

        List<QCloudUser> toInsert = apiList.stream()
                .filter(e -> e.getName() != null && !dbMap.containsKey(e.getName()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudUserMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudUser> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudUser::getConfName, cloudConf.getName())
                    .in(QCloudUser::getName, toDeleteIds)
                    .set(QCloudUser::getDeleted, 1);
            qCloudUserMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== VOD ====================

    private int syncVOD(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    private int syncVODMedia(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    private int syncVODProcess(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== VOICECLONE ====================

    private int syncVoiceClone(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== VOICEMSG ====================

    private int syncVoiceMsg(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudVoiceMsg> apiList = qCloudClient.listVoiceMsg();
        List<QCloudVoiceMsg> dbList = qCloudVoiceMsgMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudVoiceMsg> apiMap = apiList.stream()
                .filter(e -> e.getSdkAppId() != null)
                .collect(Collectors.toMap(e -> String.valueOf(e.getSdkAppId()), e -> e, (a, b) -> a));
        Map<String, QCloudVoiceMsg> dbMap = dbList.stream()
                .filter(e -> e.getSdkAppId() != null)
                .collect(Collectors.toMap(e -> String.valueOf(e.getSdkAppId()), e -> e, (a, b) -> a));

        List<QCloudVoiceMsg> toInsert = apiList.stream()
                .filter(e -> e.getSdkAppId() != null && !dbMap.containsKey(String.valueOf(e.getSdkAppId())))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudVoiceMsgMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudVoiceMsg> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudVoiceMsg::getConfName, cloudConf.getName())
                    .in(QCloudVoiceMsg::getSdkAppId, toDeleteIds)
                    .set(QCloudVoiceMsg::getDeleted, 1);
            qCloudVoiceMsgMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== VPC ====================

    private int syncVpc(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudVpc> apiList = qCloudClient.listVpc();
        List<QCloudVpc> dbList = qCloudVpcMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudVpc> apiMap = apiList.stream()
                .filter(e -> e.getVpcId() != null)
                .collect(Collectors.toMap(QCloudVpc::getVpcId, e -> e, (a, b) -> a));
        Map<String, QCloudVpc> dbMap = dbList.stream()
                .filter(e -> e.getVpcId() != null)
                .collect(Collectors.toMap(QCloudVpc::getVpcId, e -> e, (a, b) -> a));

        List<QCloudVpc> toInsert = apiList.stream()
                .filter(e -> e.getVpcId() != null && !dbMap.containsKey(e.getVpcId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudVpcMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudVpc> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudVpc::getConfName, cloudConf.getName())
                    .in(QCloudVpc::getVpcId, toDeleteIds)
                    .set(QCloudVpc::getDeleted, 1);
            qCloudVpcMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== VPCSUBNET ====================

    private int syncVpcSubnet(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudVpcSubnet> apiList = qCloudClient.listSubnet();
        List<QCloudVpcSubnet> dbList = qCloudVpcSubnetMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudVpcSubnet> apiMap = apiList.stream()
                .filter(e -> e.getSubnetId() != null)
                .collect(Collectors.toMap(QCloudVpcSubnet::getSubnetId, e -> e, (a, b) -> a));
        Map<String, QCloudVpcSubnet> dbMap = dbList.stream()
                .filter(e -> e.getSubnetId() != null)
                .collect(Collectors.toMap(QCloudVpcSubnet::getSubnetId, e -> e, (a, b) -> a));

        List<QCloudVpcSubnet> toInsert = apiList.stream()
                .filter(e -> e.getSubnetId() != null && !dbMap.containsKey(e.getSubnetId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudVpcSubnetMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudVpcSubnet> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudVpcSubnet::getConfName, cloudConf.getName())
                    .in(QCloudVpcSubnet::getSubnetId, toDeleteIds)
                    .set(QCloudVpcSubnet::getDeleted, 1);
            qCloudVpcSubnetMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== VULNMGR ====================

    private int syncVulnMgr(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    private int syncWAF(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== WEDATA ====================

    private int syncWeData(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudWeData> apiList = qCloudClient.listWeData();
        List<QCloudWeData> dbList = qCloudWeDataMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudWeData> apiMap = apiList.stream()
                .filter(e -> e.getProjectId() != null)
                .collect(Collectors.toMap(QCloudWeData::getProjectId, e -> e, (a, b) -> a));
        Map<String, QCloudWeData> dbMap = dbList.stream()
                .filter(e -> e.getProjectId() != null)
                .collect(Collectors.toMap(QCloudWeData::getProjectId, e -> e, (a, b) -> a));

        List<QCloudWeData> toInsert = apiList.stream()
                .filter(e -> e.getProjectId() != null && !dbMap.containsKey(e.getProjectId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudWeDataMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudWeData> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudWeData::getConfName, cloudConf.getName())
                    .in(QCloudWeData::getProjectId, toDeleteIds)
                    .set(QCloudWeData::getDeleted, 1);
            qCloudWeDataMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== WELINK ====================

    private int syncWeLink(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudWeLink> apiList = qCloudClient.listWeLink();
        List<QCloudWeLink> dbList = qCloudWeLinkMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudWeLink> apiMap = apiList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudWeLink::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudWeLink> dbMap = dbList.stream()
                .filter(e -> e.getInstanceId() != null)
                .collect(Collectors.toMap(QCloudWeLink::getInstanceId, e -> e, (a, b) -> a));

        List<QCloudWeLink> toInsert = apiList.stream()
                .filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudWeLinkMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudWeLink> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudWeLink::getConfName, cloudConf.getName())
                    .in(QCloudWeLink::getInstanceId, toDeleteIds)
                    .set(QCloudWeLink::getDeleted, 1);
            qCloudWeLinkMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== WEBSEARCH ====================

    private int syncWebSearch(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudWebSearch> apiList = qCloudClient.listWebSearch();
        List<QCloudWebSearch> dbList = qCloudWebSearchMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudWebSearch> apiMap = apiList.stream()
                .filter(e -> e.getQuery() != null)
                .collect(Collectors.toMap(QCloudWebSearch::getQuery, e -> e, (a, b) -> a));
        Map<String, QCloudWebSearch> dbMap = dbList.stream()
                .filter(e -> e.getQuery() != null)
                .collect(Collectors.toMap(QCloudWebSearch::getQuery, e -> e, (a, b) -> a));

        List<QCloudWebSearch> toInsert = apiList.stream()
                .filter(e -> e.getQuery() != null && !dbMap.containsKey(e.getQuery()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudWebSearchMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudWebSearch> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudWebSearch::getConfName, cloudConf.getName())
                    .in(QCloudWebSearch::getQuery, toDeleteIds)
                    .set(QCloudWebSearch::getDeleted, 1);
            qCloudWebSearchMapper.update(null, uw);
        }
        return insertCount;
    }

    // ==================== WEDA ====================

    private int syncWeda(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudWeda> apiList = qCloudClient.listWeda();
        List<QCloudWeda> dbList = qCloudWedaMapper.selectByConfName(cloudConf.getName());

        Map<String, QCloudWeda> apiMap = apiList.stream()
                .filter(e -> e.getAppId() != null)
                .collect(Collectors.toMap(QCloudWeda::getAppId, e -> e, (a, b) -> a));
        Map<String, QCloudWeda> dbMap = dbList.stream()
                .filter(e -> e.getAppId() != null)
                .collect(Collectors.toMap(QCloudWeda::getAppId, e -> e, (a, b) -> a));

        List<QCloudWeda> toInsert = apiList.stream()
                .filter(e -> e.getAppId() != null && !dbMap.containsKey(e.getAppId()))
                .collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream()
                .filter(id -> !apiMap.containsKey(id))
                .collect(Collectors.toSet());

        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = qCloudWedaMapper.insertBatch(toInsert);
        }
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<QCloudWeda> uw = new LambdaUpdateWrapper<>();
            uw.eq(QCloudWeda::getConfName, cloudConf.getName())
                    .in(QCloudWeda::getAppId, toDeleteIds)
                    .set(QCloudWeda::getDeleted, 1);
            qCloudWedaMapper.update(null, uw);
        }
        return insertCount;
    }
}