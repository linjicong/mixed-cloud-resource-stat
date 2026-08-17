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
 * 腾讯云Database Part1资源同步服务
 * 包含以下资源类型的同步方法: CTSDB, Cdb, Ckafka, CynosDB, DCDB, DLC, ES, HBase, KeeWiDB, MariaDb...
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Service
public class QCloudDatabaseServicePart1 {

    @Resource
    private QCloudCTSDBMapper qCloudCTSDBMapper;
    @Resource
    private QCloudCdbMapper qCloudCdbMapper;
    @Resource
    private QCloudCkafkaMapper qCloudCkafkaMapper;
    @Resource
    private QCloudCynosDBMapper qCloudCynosDBMapper;
    @Resource
    private QCloudDCDBMapper qCloudDCDBMapper;
    @Resource
    private QCloudDLCMapper qCloudDLCMapper;
    @Resource
    private QCloudESMapper qCloudESMapper;
    @Resource
    private QCloudHBaseMapper qCloudHBaseMapper;
    @Resource
    private QCloudKeeWiDBMapper qCloudKeeWiDBMapper;
    @Resource
    private QCloudMariaDbMapper qCloudMariaDbMapper;
    @Resource
    private QCloudMemcachedMapper qCloudMemcachedMapper;
    @Resource
    private QCloudMongoDB_CKafkaMapper qCloudMongoDB_CKafkaMapper;

    // ==================== CTSDB ====================

    public int syncCTSDB(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== CDB ====================

    public int syncCdb(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== CKAFKA ====================

    public int syncCkafka(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== CYNOSDB ====================

    public int syncCynosDB(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== DCDB ====================

    public int syncDCDB(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== DLC ====================

    public int syncDLC(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== ES ====================

    public int syncES(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== HBASE ====================

    public int syncHBase(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== KEEWIDB ====================

    public int syncKeeWiDB(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== MARIADB ====================

    public int syncMariaDb(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== MEMCACHED ====================

    public int syncMemcached(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== MONGODB_CKAFKA ====================

    public int syncMongoDB_CKafka(QCloudClient qCloudClient, CloudConf cloudConf) {
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
}
