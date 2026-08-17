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
 * 腾讯云Network Part3资源同步服务
 * 包含以下资源类型的同步方法: DedicatedZone, EdgeZone
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Service
public class QCloudNetworkServicePart3 {

    @Resource
    private QCloudDedicatedZoneMapper qCloudDedicatedZoneMapper;
    @Resource
    private QCloudEdgeZoneMapper qCloudEdgeZoneMapper;

    public int syncDedicatedZone(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudDedicatedZone> apiList = qCloudClient.listDedicatedZone();
        List<QCloudDedicatedZone> dbList = qCloudDedicatedZoneMapper.selectByConfName(cloudConf.getName());
        Map<String, QCloudDedicatedZone> apiMap = apiList.stream().filter(e -> e.getZoneId() != null).collect(Collectors.toMap(QCloudDedicatedZone::getZoneId, e -> e, (a, b) -> a));
        Map<String, QCloudDedicatedZone> dbMap = dbList.stream().filter(e -> e.getZoneId() != null).collect(Collectors.toMap(QCloudDedicatedZone::getZoneId, e -> e, (a, b) -> a));
        List<QCloudDedicatedZone> toInsert = apiList.stream().filter(e -> e.getZoneId() != null && !dbMap.containsKey(e.getZoneId())).collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream().filter(id -> !apiMap.containsKey(id)).collect(Collectors.toSet());
        int insertCount = 0;
        if (!toInsert.isEmpty()) insertCount = qCloudDedicatedZoneMapper.insertBatch(toInsert);
        if (!toDeleteIds.isEmpty()) { LambdaUpdateWrapper<QCloudDedicatedZone> uw = new LambdaUpdateWrapper<>(); uw.eq(QCloudDedicatedZone::getConfName, cloudConf.getName()).in(QCloudDedicatedZone::getZoneId, toDeleteIds).set(QCloudDedicatedZone::getDeleted, 1); qCloudDedicatedZoneMapper.update(null, uw); }
        return insertCount;
    }

    public int syncEdgeZone(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudEdgeZone> apiList = qCloudClient.listEdgeZone();
        List<QCloudEdgeZone> dbList = qCloudEdgeZoneMapper.selectByConfName(cloudConf.getName());
        Map<String, QCloudEdgeZone> apiMap = apiList.stream().filter(e -> e.getZoneId() != null).collect(Collectors.toMap(QCloudEdgeZone::getZoneId, e -> e, (a, b) -> a));
        Map<String, QCloudEdgeZone> dbMap = dbList.stream().filter(e -> e.getZoneId() != null).collect(Collectors.toMap(QCloudEdgeZone::getZoneId, e -> e, (a, b) -> a));
        List<QCloudEdgeZone> toInsert = apiList.stream().filter(e -> e.getZoneId() != null && !dbMap.containsKey(e.getZoneId())).collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream().filter(id -> !apiMap.containsKey(id)).collect(Collectors.toSet());
        int insertCount = 0;
        if (!toInsert.isEmpty()) insertCount = qCloudEdgeZoneMapper.insertBatch(toInsert);
        if (!toDeleteIds.isEmpty()) { LambdaUpdateWrapper<QCloudEdgeZone> uw = new LambdaUpdateWrapper<>(); uw.eq(QCloudEdgeZone::getConfName, cloudConf.getName()).in(QCloudEdgeZone::getZoneId, toDeleteIds).set(QCloudEdgeZone::getDeleted, 1); qCloudEdgeZoneMapper.update(null, uw); }
        return insertCount;
    }
}
