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
 * 腾讯云Application Part5资源同步服务
 * 包含以下资源类型的同步方法: PrivDNS, RTIEdu, RTIIndustrial, SES, SMS, SmartAdvisor, SmartGuide, SmartView, SmsSign, SmsTemplate...
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Service
public class QCloudApplicationServicePart5 {

    @Resource
    private QCloudPrivDNSMapper qCloudPrivDNSMapper;
    @Resource
    private QCloudRTIEduMapper qCloudRTIEduMapper;
    @Resource
    private QCloudRTIIndustrialMapper qCloudRTIIndustrialMapper;
    @Resource
    private QCloudSESMapper qCloudSESMapper;
    @Resource
    private QCloudSMSMapper qCloudSMSMapper;
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
    private QCloudTAPDMapper qCloudTAPDMapper;
    @Resource
    private QCloudTBAASMapper qCloudTBAASMapper;

    // ==================== PRIVDNS ====================

    public int syncPrivDNS(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    public int syncRTIEdu(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    public int syncRTIIndustrial(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== SES ====================

    public int syncSES(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    public int syncSMS(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== SMARTADVISOR ====================

    public int syncSmartAdvisor(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    public int syncSmartGuide(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    public int syncSmartView(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    public int syncSmsSign(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    public int syncSmsTemplate(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== TAPD ====================

    public int syncTAPD(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    public int syncTBAAS(QCloudClient qCloudClient, CloudConf cloudConf) {
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
}
