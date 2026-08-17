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
 * 腾讯云Application Part4资源同步服务
 * 包含以下资源类型的同步方法: HealthReport2, ICPBeian, IOA, IoT, IoTDevice, IoTHub, Mail, MallTraffic, Meeting, MicroWeda...
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Service
public class QCloudApplicationServicePart4 {

    @Resource
    private QCloudHealthReport2Mapper qCloudHealthReport2Mapper;
    @Resource
    private QCloudICPBeianMapper qCloudICPBeianMapper;
    @Resource
    private QCloudIOAMapper qCloudIOAMapper;
    @Resource
    private QCloudIoTDeviceMapper qCloudIoTDeviceMapper;
    @Resource
    private QCloudIoTHubMapper qCloudIoTHubMapper;
    @Resource
    private QCloudIoTMapper qCloudIoTMapper;
    @Resource
    private QCloudMailMapper qCloudMailMapper;
    @Resource
    private QCloudMallTrafficMapper qCloudMallTrafficMapper;
    @Resource
    private QCloudMeetingMapper qCloudMeetingMapper;
    @Resource
    private QCloudMicroWedaMapper qCloudMicroWedaMapper;
    @Resource
    private QCloudMonitorMapper qCloudMonitorMapper;
    @Resource
    private QCloudOrgMapper qCloudOrgMapper;

    // ==================== HEALTHREPORT2 ====================

    public int syncHealthReport2(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    public int syncICPBeian(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    public int syncIOA(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== IOT ====================

    public int syncIoT(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    public int syncIoTDevice(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    public int syncIoTHub(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== MAIL ====================

    public int syncMail(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    public int syncMallTraffic(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== MEETING ====================

    public int syncMeeting(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== MICROWEDA ====================

    public int syncMicroWeda(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== MONITOR ====================

    public int syncMonitor(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== ORG ====================

    public int syncOrg(QCloudClient qCloudClient, CloudConf cloudConf) {
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
}
