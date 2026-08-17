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
 * 腾讯云Database Part3资源同步服务
 * 包含以下资源类型的同步方法: Tendis, TdsqlBoundless, VectorDb, TdsqlDistributed
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Service
public class QCloudDatabaseServicePart3 {

    @Resource
    private QCloudTdsqlBoundlessMapper qCloudTdsqlBoundlessMapper;
    @Resource
    private QCloudTdsqlDistributedMapper qCloudTdsqlDistributedMapper;
    @Resource
    private QCloudTendisMapper qCloudTendisMapper;
    @Resource
    private QCloudVectorDbMapper qCloudVectorDbMapper;

    // ==================== TENDIS ====================

    public int syncTendis(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    public int syncTdsqlBoundless(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudTdsqlBoundless> apiList = qCloudClient.listTdsqlBoundless();
        List<QCloudTdsqlBoundless> dbList = qCloudTdsqlBoundlessMapper.selectByConfName(cloudConf.getName());
        Map<String, QCloudTdsqlBoundless> apiMap = apiList.stream().filter(e -> e.getInstanceId() != null).collect(Collectors.toMap(QCloudTdsqlBoundless::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudTdsqlBoundless> dbMap = dbList.stream().filter(e -> e.getInstanceId() != null).collect(Collectors.toMap(QCloudTdsqlBoundless::getInstanceId, e -> e, (a, b) -> a));
        List<QCloudTdsqlBoundless> toInsert = apiList.stream().filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId())).collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream().filter(id -> !apiMap.containsKey(id)).collect(Collectors.toSet());
        int insertCount = 0;
        if (!toInsert.isEmpty()) insertCount = qCloudTdsqlBoundlessMapper.insertBatch(toInsert);
        if (!toDeleteIds.isEmpty()) { LambdaUpdateWrapper<QCloudTdsqlBoundless> uw = new LambdaUpdateWrapper<>(); uw.eq(QCloudTdsqlBoundless::getConfName, cloudConf.getName()).in(QCloudTdsqlBoundless::getInstanceId, toDeleteIds).set(QCloudTdsqlBoundless::getDeleted, 1); qCloudTdsqlBoundlessMapper.update(null, uw); }
        return insertCount;
    }

    public int syncVectorDb(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudVectorDb> apiList = qCloudClient.listVectorDb();
        List<QCloudVectorDb> dbList = qCloudVectorDbMapper.selectByConfName(cloudConf.getName());
        Map<String, QCloudVectorDb> apiMap = apiList.stream().filter(e -> e.getInstanceId() != null).collect(Collectors.toMap(QCloudVectorDb::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudVectorDb> dbMap = dbList.stream().filter(e -> e.getInstanceId() != null).collect(Collectors.toMap(QCloudVectorDb::getInstanceId, e -> e, (a, b) -> a));
        List<QCloudVectorDb> toInsert = apiList.stream().filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId())).collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream().filter(id -> !apiMap.containsKey(id)).collect(Collectors.toSet());
        int insertCount = 0;
        if (!toInsert.isEmpty()) insertCount = qCloudVectorDbMapper.insertBatch(toInsert);
        if (!toDeleteIds.isEmpty()) { LambdaUpdateWrapper<QCloudVectorDb> uw = new LambdaUpdateWrapper<>(); uw.eq(QCloudVectorDb::getConfName, cloudConf.getName()).in(QCloudVectorDb::getInstanceId, toDeleteIds).set(QCloudVectorDb::getDeleted, 1); qCloudVectorDbMapper.update(null, uw); }
        return insertCount;
    }

    public int syncTdsqlDistributed(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudTdsqlDistributed> apiList = qCloudClient.listTdsqlDistributed();
        List<QCloudTdsqlDistributed> dbList = qCloudTdsqlDistributedMapper.selectByConfName(cloudConf.getName());
        Map<String, QCloudTdsqlDistributed> apiMap = apiList.stream().filter(e -> e.getInstanceId() != null).collect(Collectors.toMap(QCloudTdsqlDistributed::getInstanceId, e -> e, (a, b) -> a));
        Map<String, QCloudTdsqlDistributed> dbMap = dbList.stream().filter(e -> e.getInstanceId() != null).collect(Collectors.toMap(QCloudTdsqlDistributed::getInstanceId, e -> e, (a, b) -> a));
        List<QCloudTdsqlDistributed> toInsert = apiList.stream().filter(e -> e.getInstanceId() != null && !dbMap.containsKey(e.getInstanceId())).collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream().filter(id -> !apiMap.containsKey(id)).collect(Collectors.toSet());
        int insertCount = 0;
        if (!toInsert.isEmpty()) insertCount = qCloudTdsqlDistributedMapper.insertBatch(toInsert);
        if (!toDeleteIds.isEmpty()) { LambdaUpdateWrapper<QCloudTdsqlDistributed> uw = new LambdaUpdateWrapper<>(); uw.eq(QCloudTdsqlDistributed::getConfName, cloudConf.getName()).in(QCloudTdsqlDistributed::getInstanceId, toDeleteIds).set(QCloudTdsqlDistributed::getDeleted, 1); qCloudTdsqlDistributedMapper.update(null, uw); }
        return insertCount;
    }
}
