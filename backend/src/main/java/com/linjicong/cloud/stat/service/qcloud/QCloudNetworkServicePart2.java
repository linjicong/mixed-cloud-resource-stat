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
 * 腾讯云Network Part2资源同步服务
 * 包含以下资源类型的同步方法: VpcSubnet, FlowLog, BandwidthPackage, TrafficPackage, Ipv6, Cc, Vpn, Peering, Sdwan, WsA...
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Service
public class QCloudNetworkServicePart2 {

    @Resource
    private QCloudBandwidthPackageMapper qCloudBandwidthPackageMapper;
    @Resource
    private QCloudCcMapper qCloudCcMapper;
    @Resource
    private QCloudFlowLogMapper qCloudFlowLogMapper;
    @Resource
    private QCloudGaapV2Mapper qCloudGaapV2Mapper;
    @Resource
    private QCloudIpv6Mapper qCloudIpv6Mapper;
    @Resource
    private QCloudPeeringMapper qCloudPeeringMapper;
    @Resource
    private QCloudScdnMapper qCloudScdnMapper;
    @Resource
    private QCloudSdwanMapper qCloudSdwanMapper;
    @Resource
    private QCloudTrafficPackageMapper qCloudTrafficPackageMapper;
    @Resource
    private QCloudVpcSubnetMapper qCloudVpcSubnetMapper;
    @Resource
    private QCloudVpnMapper qCloudVpnMapper;
    @Resource
    private QCloudWsAMapper qCloudWsAMapper;

    // ==================== VPCSUBNET ====================

    public int syncVpcSubnet(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    public int syncFlowLog(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudFlowLog> apiList = qCloudClient.listFlowLog();
        List<QCloudFlowLog> dbList = qCloudFlowLogMapper.selectByConfName(cloudConf.getName());
        Map<String, QCloudFlowLog> apiMap = apiList.stream().filter(e -> e.getFlowLogId() != null).collect(Collectors.toMap(QCloudFlowLog::getFlowLogId, e -> e, (a, b) -> a));
        Map<String, QCloudFlowLog> dbMap = dbList.stream().filter(e -> e.getFlowLogId() != null).collect(Collectors.toMap(QCloudFlowLog::getFlowLogId, e -> e, (a, b) -> a));
        List<QCloudFlowLog> toInsert = apiList.stream().filter(e -> e.getFlowLogId() != null && !dbMap.containsKey(e.getFlowLogId())).collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream().filter(id -> !apiMap.containsKey(id)).collect(Collectors.toSet());
        int insertCount = 0;
        if (!toInsert.isEmpty()) insertCount = qCloudFlowLogMapper.insertBatch(toInsert);
        if (!toDeleteIds.isEmpty()) { LambdaUpdateWrapper<QCloudFlowLog> uw = new LambdaUpdateWrapper<>(); uw.eq(QCloudFlowLog::getConfName, cloudConf.getName()).in(QCloudFlowLog::getFlowLogId, toDeleteIds).set(QCloudFlowLog::getDeleted, 1); qCloudFlowLogMapper.update(null, uw); }
        return insertCount;
    }

    public int syncBandwidthPackage(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudBandwidthPackage> apiList = qCloudClient.listBandwidthPackage();
        List<QCloudBandwidthPackage> dbList = qCloudBandwidthPackageMapper.selectByConfName(cloudConf.getName());
        Map<String, QCloudBandwidthPackage> apiMap = apiList.stream().filter(e -> e.getBandwidthPackageId() != null).collect(Collectors.toMap(QCloudBandwidthPackage::getBandwidthPackageId, e -> e, (a, b) -> a));
        Map<String, QCloudBandwidthPackage> dbMap = dbList.stream().filter(e -> e.getBandwidthPackageId() != null).collect(Collectors.toMap(QCloudBandwidthPackage::getBandwidthPackageId, e -> e, (a, b) -> a));
        List<QCloudBandwidthPackage> toInsert = apiList.stream().filter(e -> e.getBandwidthPackageId() != null && !dbMap.containsKey(e.getBandwidthPackageId())).collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream().filter(id -> !apiMap.containsKey(id)).collect(Collectors.toSet());
        int insertCount = 0;
        if (!toInsert.isEmpty()) insertCount = qCloudBandwidthPackageMapper.insertBatch(toInsert);
        if (!toDeleteIds.isEmpty()) { LambdaUpdateWrapper<QCloudBandwidthPackage> uw = new LambdaUpdateWrapper<>(); uw.eq(QCloudBandwidthPackage::getConfName, cloudConf.getName()).in(QCloudBandwidthPackage::getBandwidthPackageId, toDeleteIds).set(QCloudBandwidthPackage::getDeleted, 1); qCloudBandwidthPackageMapper.update(null, uw); }
        return insertCount;
    }

    public int syncTrafficPackage(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudTrafficPackage> apiList = qCloudClient.listTrafficPackage();
        List<QCloudTrafficPackage> dbList = qCloudTrafficPackageMapper.selectByConfName(cloudConf.getName());
        Map<String, QCloudTrafficPackage> apiMap = apiList.stream().filter(e -> e.getTrafficPackageId() != null).collect(Collectors.toMap(QCloudTrafficPackage::getTrafficPackageId, e -> e, (a, b) -> a));
        Map<String, QCloudTrafficPackage> dbMap = dbList.stream().filter(e -> e.getTrafficPackageId() != null).collect(Collectors.toMap(QCloudTrafficPackage::getTrafficPackageId, e -> e, (a, b) -> a));
        List<QCloudTrafficPackage> toInsert = apiList.stream().filter(e -> e.getTrafficPackageId() != null && !dbMap.containsKey(e.getTrafficPackageId())).collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream().filter(id -> !apiMap.containsKey(id)).collect(Collectors.toSet());
        int insertCount = 0;
        if (!toInsert.isEmpty()) insertCount = qCloudTrafficPackageMapper.insertBatch(toInsert);
        if (!toDeleteIds.isEmpty()) { LambdaUpdateWrapper<QCloudTrafficPackage> uw = new LambdaUpdateWrapper<>(); uw.eq(QCloudTrafficPackage::getConfName, cloudConf.getName()).in(QCloudTrafficPackage::getTrafficPackageId, toDeleteIds).set(QCloudTrafficPackage::getDeleted, 1); qCloudTrafficPackageMapper.update(null, uw); }
        return insertCount;
    }

    public int syncIpv6(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudIpv6> apiList = qCloudClient.listIpv6();
        List<QCloudIpv6> dbList = qCloudIpv6Mapper.selectByConfName(cloudConf.getName());
        Map<String, QCloudIpv6> apiMap = apiList.stream().filter(e -> e.getIpv6Address() != null).collect(Collectors.toMap(QCloudIpv6::getIpv6Address, e -> e, (a, b) -> a));
        Map<String, QCloudIpv6> dbMap = dbList.stream().filter(e -> e.getIpv6Address() != null).collect(Collectors.toMap(QCloudIpv6::getIpv6Address, e -> e, (a, b) -> a));
        List<QCloudIpv6> toInsert = apiList.stream().filter(e -> e.getIpv6Address() != null && !dbMap.containsKey(e.getIpv6Address())).collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream().filter(id -> !apiMap.containsKey(id)).collect(Collectors.toSet());
        int insertCount = 0;
        if (!toInsert.isEmpty()) insertCount = qCloudIpv6Mapper.insertBatch(toInsert);
        if (!toDeleteIds.isEmpty()) { LambdaUpdateWrapper<QCloudIpv6> uw = new LambdaUpdateWrapper<>(); uw.eq(QCloudIpv6::getConfName, cloudConf.getName()).in(QCloudIpv6::getIpv6Address, toDeleteIds).set(QCloudIpv6::getDeleted, 1); qCloudIpv6Mapper.update(null, uw); }
        return insertCount;
    }

    public int syncCc(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudCc> apiList = qCloudClient.listCc();
        List<QCloudCc> dbList = qCloudCcMapper.selectByConfName(cloudConf.getName());
        Map<String, QCloudCc> apiMap = apiList.stream().filter(e -> e.getCcnId() != null).collect(Collectors.toMap(QCloudCc::getCcnId, e -> e, (a, b) -> a));
        Map<String, QCloudCc> dbMap = dbList.stream().filter(e -> e.getCcnId() != null).collect(Collectors.toMap(QCloudCc::getCcnId, e -> e, (a, b) -> a));
        List<QCloudCc> toInsert = apiList.stream().filter(e -> e.getCcnId() != null && !dbMap.containsKey(e.getCcnId())).collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream().filter(id -> !apiMap.containsKey(id)).collect(Collectors.toSet());
        int insertCount = 0;
        if (!toInsert.isEmpty()) insertCount = qCloudCcMapper.insertBatch(toInsert);
        if (!toDeleteIds.isEmpty()) { LambdaUpdateWrapper<QCloudCc> uw = new LambdaUpdateWrapper<>(); uw.eq(QCloudCc::getConfName, cloudConf.getName()).in(QCloudCc::getCcnId, toDeleteIds).set(QCloudCc::getDeleted, 1); qCloudCcMapper.update(null, uw); }
        return insertCount;
    }

    public int syncVpn(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudVpn> apiList = qCloudClient.listVpn();
        List<QCloudVpn> dbList = qCloudVpnMapper.selectByConfName(cloudConf.getName());
        Map<String, QCloudVpn> apiMap = apiList.stream().filter(e -> e.getVpnGatewayId() != null).collect(Collectors.toMap(QCloudVpn::getVpnGatewayId, e -> e, (a, b) -> a));
        Map<String, QCloudVpn> dbMap = dbList.stream().filter(e -> e.getVpnGatewayId() != null).collect(Collectors.toMap(QCloudVpn::getVpnGatewayId, e -> e, (a, b) -> a));
        List<QCloudVpn> toInsert = apiList.stream().filter(e -> e.getVpnGatewayId() != null && !dbMap.containsKey(e.getVpnGatewayId())).collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream().filter(id -> !apiMap.containsKey(id)).collect(Collectors.toSet());
        int insertCount = 0;
        if (!toInsert.isEmpty()) insertCount = qCloudVpnMapper.insertBatch(toInsert);
        if (!toDeleteIds.isEmpty()) { LambdaUpdateWrapper<QCloudVpn> uw = new LambdaUpdateWrapper<>(); uw.eq(QCloudVpn::getConfName, cloudConf.getName()).in(QCloudVpn::getVpnGatewayId, toDeleteIds).set(QCloudVpn::getDeleted, 1); qCloudVpnMapper.update(null, uw); }
        return insertCount;
    }

    public int syncPeering(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudPeering> apiList = qCloudClient.listPeering();
        List<QCloudPeering> dbList = qCloudPeeringMapper.selectByConfName(cloudConf.getName());
        Map<String, QCloudPeering> apiMap = apiList.stream().filter(e -> e.getPeeringConnectionId() != null).collect(Collectors.toMap(QCloudPeering::getPeeringConnectionId, e -> e, (a, b) -> a));
        Map<String, QCloudPeering> dbMap = dbList.stream().filter(e -> e.getPeeringConnectionId() != null).collect(Collectors.toMap(QCloudPeering::getPeeringConnectionId, e -> e, (a, b) -> a));
        List<QCloudPeering> toInsert = apiList.stream().filter(e -> e.getPeeringConnectionId() != null && !dbMap.containsKey(e.getPeeringConnectionId())).collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream().filter(id -> !apiMap.containsKey(id)).collect(Collectors.toSet());
        int insertCount = 0;
        if (!toInsert.isEmpty()) insertCount = qCloudPeeringMapper.insertBatch(toInsert);
        if (!toDeleteIds.isEmpty()) { LambdaUpdateWrapper<QCloudPeering> uw = new LambdaUpdateWrapper<>(); uw.eq(QCloudPeering::getConfName, cloudConf.getName()).in(QCloudPeering::getPeeringConnectionId, toDeleteIds).set(QCloudPeering::getDeleted, 1); qCloudPeeringMapper.update(null, uw); }
        return insertCount;
    }

    public int syncSdwan(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudSdwan> apiList = qCloudClient.listSdwan();
        List<QCloudSdwan> dbList = qCloudSdwanMapper.selectByConfName(cloudConf.getName());
        Map<String, QCloudSdwan> apiMap = apiList.stream().filter(e -> e.getInstanceId() != null).collect(Collectors.toMap(QCloudSdwan::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudSdwan> dbMap = dbList.stream().filter(e -> e.getInstanceId() != null).collect(Collectors.toMap(QCloudSdwan::getInstanceId, e -> e, (a, b) -> a));
        List<QCloudSdwan> toInsert = apiList.stream().filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId())).collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream().filter(id -> !apiMap.containsKey(id)).collect(Collectors.toSet());
        int insertCount = 0;
        if (!toInsert.isEmpty()) insertCount = qCloudSdwanMapper.insertBatch(toInsert);
        if (!toDeleteIds.isEmpty()) { LambdaUpdateWrapper<QCloudSdwan> uw = new LambdaUpdateWrapper<>(); uw.eq(QCloudSdwan::getConfName, cloudConf.getName()).in(QCloudSdwan::getInstanceId, toDeleteIds).set(QCloudSdwan::getDeleted, 1); qCloudSdwanMapper.update(null, uw); }
        return insertCount;
    }

    public int syncWsA(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudWsA> apiList = qCloudClient.listWsA();
        List<QCloudWsA> dbList = qCloudWsAMapper.selectByConfName(cloudConf.getName());
        Map<String, QCloudWsA> apiMap = apiList.stream().filter(e -> e.getDomain() != null).collect(Collectors.toMap(QCloudWsA::getDomain, e -> e, (a, b) -> a));
        Map<String, QCloudWsA> dbMap = dbList.stream().filter(e -> e.getDomain() != null).collect(Collectors.toMap(QCloudWsA::getDomain, e -> e, (a, b) -> a));
        List<QCloudWsA> toInsert = apiList.stream().filter(e -> e.getDomain() != null && !dbMap.containsKey(e.getDomain())).collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream().filter(id -> !apiMap.containsKey(id)).collect(Collectors.toSet());
        int insertCount = 0;
        if (!toInsert.isEmpty()) insertCount = qCloudWsAMapper.insertBatch(toInsert);
        if (!toDeleteIds.isEmpty()) { LambdaUpdateWrapper<QCloudWsA> uw = new LambdaUpdateWrapper<>(); uw.eq(QCloudWsA::getConfName, cloudConf.getName()).in(QCloudWsA::getDomain, toDeleteIds).set(QCloudWsA::getDeleted, 1); qCloudWsAMapper.update(null, uw); }
        return insertCount;
    }

    public int syncScdn(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudScdn> apiList = qCloudClient.listScdn();
        List<QCloudScdn> dbList = qCloudScdnMapper.selectByConfName(cloudConf.getName());
        Map<String, QCloudScdn> apiMap = apiList.stream().filter(e -> e.getDomain() != null).collect(Collectors.toMap(QCloudScdn::getDomain, e -> e, (a, b) -> a));
        Map<String, QCloudScdn> dbMap = dbList.stream().filter(e -> e.getDomain() != null).collect(Collectors.toMap(QCloudScdn::getDomain, e -> e, (a, b) -> a));
        List<QCloudScdn> toInsert = apiList.stream().filter(e -> e.getDomain() != null && !dbMap.containsKey(e.getDomain())).collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream().filter(id -> !apiMap.containsKey(id)).collect(Collectors.toSet());
        int insertCount = 0;
        if (!toInsert.isEmpty()) insertCount = qCloudScdnMapper.insertBatch(toInsert);
        if (!toDeleteIds.isEmpty()) { LambdaUpdateWrapper<QCloudScdn> uw = new LambdaUpdateWrapper<>(); uw.eq(QCloudScdn::getConfName, cloudConf.getName()).in(QCloudScdn::getDomain, toDeleteIds).set(QCloudScdn::getDeleted, 1); qCloudScdnMapper.update(null, uw); }
        return insertCount;
    }

    public int syncGaapV2(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudGaapV2> apiList = qCloudClient.listGaapV2();
        List<QCloudGaapV2> dbList = qCloudGaapV2Mapper.selectByConfName(cloudConf.getName());
        Map<String, QCloudGaapV2> apiMap = apiList.stream().filter(e -> e.getProxyId() != null).collect(Collectors.toMap(QCloudGaapV2::getProxyId, e -> e, (a, b) -> a));
        Map<String, QCloudGaapV2> dbMap = dbList.stream().filter(e -> e.getProxyId() != null).collect(Collectors.toMap(QCloudGaapV2::getProxyId, e -> e, (a, b) -> a));
        List<QCloudGaapV2> toInsert = apiList.stream().filter(e -> e.getProxyId() != null && !dbMap.containsKey(e.getProxyId())).collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream().filter(id -> !apiMap.containsKey(id)).collect(Collectors.toSet());
        int insertCount = 0;
        if (!toInsert.isEmpty()) insertCount = qCloudGaapV2Mapper.insertBatch(toInsert);
        if (!toDeleteIds.isEmpty()) { LambdaUpdateWrapper<QCloudGaapV2> uw = new LambdaUpdateWrapper<>(); uw.eq(QCloudGaapV2::getConfName, cloudConf.getName()).in(QCloudGaapV2::getProxyId, toDeleteIds).set(QCloudGaapV2::getDeleted, 1); qCloudGaapV2Mapper.update(null, uw); }
        return insertCount;
    }
}
