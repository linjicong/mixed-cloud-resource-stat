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
 * 腾讯云Application Part2资源同步服务
 * 包含以下资源类型的同步方法: CWP3, CloudBase, CloudContact, CloudPhone, CloudStudio, Cmq, CodingDevops, Config, ControlCenter, DNSPrivate...
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Service
public class QCloudApplicationServicePart2 {

    @Resource
    private QCloudCWP3Mapper qCloudCWP3Mapper;
    @Resource
    private QCloudCloudBaseMapper qCloudCloudBaseMapper;
    @Resource
    private QCloudCloudContactMapper qCloudCloudContactMapper;
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
    private QCloudControlCenterMapper qCloudControlCenterMapper;
    @Resource
    private QCloudDNSPrivateMapper qCloudDNSPrivateMapper;
    @Resource
    private QCloudDNSSecMapper qCloudDNSSecMapper;
    @Resource
    private QCloudDnsDomainMapper qCloudDnsDomainMapper;

    // ==================== CWP3 ====================

    public int syncCWP3(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== CLOUDBASE ====================

    public int syncCloudBase(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    public int syncCloudContact(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== CLOUDPHONE ====================

    public int syncCloudPhone(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    public int syncCloudStudio(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    public int syncCmq(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    public int syncCodingDevops(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    public int syncConfig(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== CONTROLCENTER ====================

    public int syncControlCenter(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== DNSPRIVATE ====================

    public int syncDNSPrivate(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    public int syncDNSSec(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== DNSDOMAIN ====================

    public int syncDnsDomain(QCloudClient qCloudClient, CloudConf cloudConf) {
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
}
