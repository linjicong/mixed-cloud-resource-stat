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
 * 腾讯云Network Part1资源同步服务
 * 包含以下资源类型的同步方法: CLB_gw, CdnDomain, Clb, DC, DistID, EO, Eip, GTM, Gaap, NatGateway...
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Service
public class QCloudNetworkServicePart1 {

    @Resource
    private QCloudCLB_gwMapper qCloudCLB_gwMapper;
    @Resource
    private QCloudCdnDomainMapper qCloudCdnDomainMapper;
    @Resource
    private QCloudClbMapper qCloudClbMapper;
    @Resource
    private QCloudDCMapper qCloudDCMapper;
    @Resource
    private QCloudDistIDMapper qCloudDistIDMapper;
    @Resource
    private QCloudEOMapper qCloudEOMapper;
    @Resource
    private QCloudEipMapper qCloudEipMapper;
    @Resource
    private QCloudGTMMapper qCloudGTMMapper;
    @Resource
    private QCloudGaapMapper qCloudGaapMapper;
    @Resource
    private QCloudNatGatewayMapper qCloudNatGatewayMapper;
    @Resource
    private QCloudRegionMgrMapper qCloudRegionMgrMapper;
    @Resource
    private QCloudVpcMapper qCloudVpcMapper;

    // ==================== CLB_GW ====================

    public int syncCLB_gw(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== CDNDOMAIN ====================

    public int syncCdnDomain(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== CLB ====================

    public int syncClb(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== DC ====================

    public int syncDC(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== DISTID ====================

    public int syncDistID(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== EO ====================

    public int syncEO(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== EIP ====================

    public int syncEip(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== GTM ====================

    public int syncGTM(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    public int syncGaap(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== NATGATEWAY ====================

    public int syncNatGateway(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== REGIONMGR ====================

    public int syncRegionMgr(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== VPC ====================

    public int syncVpc(QCloudClient qCloudClient, CloudConf cloudConf) {
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
}
