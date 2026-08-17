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
 * 腾讯云Storage资源同步服务
 * 包含以下资源类型的同步方法: CHDFS, Cbs, Cfs, Cos, GooseFS, Ci
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Service
public class QCloudStorageService {

    @Resource
    private QCloudCHDFSMapper qCloudCHDFSMapper;
    @Resource
    private QCloudCbsMapper qCloudCbsMapper;
    @Resource
    private QCloudCfsMapper qCloudCfsMapper;
    @Resource
    private QCloudCiMapper qCloudCiMapper;
    @Resource
    private QCloudCosMapper qCloudCosMapper;
    @Resource
    private QCloudGooseFSMapper qCloudGooseFSMapper;

    // ==================== CHDFS ====================

    public int syncCHDFS(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== CBS ====================

    public int syncCbs(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== CFS ====================

    public int syncCfs(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== COS ====================

    public int syncCos(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    public int syncGooseFS(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudGooseFS> apiList = qCloudClient.listGooseFS();
        List<QCloudGooseFS> dbList = qCloudGooseFSMapper.selectByConfName(cloudConf.getName());
        Map<String, QCloudGooseFS> apiMap = apiList.stream().filter(e -> e.getClusterId() != null).collect(Collectors.toMap(QCloudGooseFS::getClusterId, e -> e, (a, b) -> a));
        Map<String, QCloudGooseFS> dbMap = dbList.stream().filter(e -> e.getClusterId() != null).collect(Collectors.toMap(QCloudGooseFS::getClusterId, e -> e, (a, b) -> a));
        List<QCloudGooseFS> toInsert = apiList.stream().filter(e -> e.getClusterId() != null && !dbMap.containsKey(e.getClusterId())).collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream().filter(id -> !apiMap.containsKey(id)).collect(Collectors.toSet());
        int insertCount = 0;
        if (!toInsert.isEmpty()) insertCount = qCloudGooseFSMapper.insertBatch(toInsert);
        if (!toDeleteIds.isEmpty()) { LambdaUpdateWrapper<QCloudGooseFS> uw = new LambdaUpdateWrapper<>(); uw.eq(QCloudGooseFS::getConfName, cloudConf.getName()).in(QCloudGooseFS::getClusterId, toDeleteIds).set(QCloudGooseFS::getDeleted, 1); qCloudGooseFSMapper.update(null, uw); }
        return insertCount;
    }

    public int syncCi(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudCi> apiList = qCloudClient.listCi();
        List<QCloudCi> dbList = qCloudCiMapper.selectByConfName(cloudConf.getName());
        Map<String, QCloudCi> apiMap = apiList.stream().filter(e -> e.getBucketId() != null).collect(Collectors.toMap(QCloudCi::getBucketId, e -> e, (a, b) -> a));
        Map<String, QCloudCi> dbMap = dbList.stream().filter(e -> e.getBucketId() != null).collect(Collectors.toMap(QCloudCi::getBucketId, e -> e, (a, b) -> a));
        List<QCloudCi> toInsert = apiList.stream().filter(e -> e.getBucketId() != null && !dbMap.containsKey(e.getBucketId())).collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream().filter(id -> !apiMap.containsKey(id)).collect(Collectors.toSet());
        int insertCount = 0;
        if (!toInsert.isEmpty()) insertCount = qCloudCiMapper.insertBatch(toInsert);
        if (!toDeleteIds.isEmpty()) { LambdaUpdateWrapper<QCloudCi> uw = new LambdaUpdateWrapper<>(); uw.eq(QCloudCi::getConfName, cloudConf.getName()).in(QCloudCi::getBucketId, toDeleteIds).set(QCloudCi::getDeleted, 1); qCloudCiMapper.update(null, uw); }
        return insertCount;
    }
}
