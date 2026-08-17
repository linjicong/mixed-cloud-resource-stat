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
 * 腾讯云Database Part2资源同步服务
 * 包含以下资源类型的同步方法: MongoDb, Oceanus, Postgresql, RabbitMQ, Redis, RocketMQ, Sqlserver, TCHouseC, TCHouseD, TCHouseP...
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Service
public class QCloudDatabaseServicePart2 {

    @Resource
    private QCloudMongoDbMapper qCloudMongoDbMapper;
    @Resource
    private QCloudOceanusMapper qCloudOceanusMapper;
    @Resource
    private QCloudPostgresqlMapper qCloudPostgresqlMapper;
    @Resource
    private QCloudRabbitMQMapper qCloudRabbitMQMapper;
    @Resource
    private QCloudRedisMapper qCloudRedisMapper;
    @Resource
    private QCloudRocketMQMapper qCloudRocketMQMapper;
    @Resource
    private QCloudSqlserverMapper qCloudSqlserverMapper;
    @Resource
    private QCloudTCHouseCMapper qCloudTCHouseCMapper;
    @Resource
    private QCloudTCHouseDMapper qCloudTCHouseDMapper;
    @Resource
    private QCloudTCHousePMapper qCloudTCHousePMapper;
    @Resource
    private QCloudTDMQMapper qCloudTDMQMapper;
    @Resource
    private QCloudTcaplusDBMapper qCloudTcaplusDBMapper;

    // ==================== MONGODB ====================

    public int syncMongoDb(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== OCEANUS ====================

    public int syncOceanus(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== POSTGRESQL ====================

    public int syncPostgresql(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== RABBITMQ ====================

    public int syncRabbitMQ(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    public int syncRedis(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== ROCKETMQ ====================

    public int syncRocketMQ(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== SQLSERVER ====================

    public int syncSqlserver(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== TCHOUSEC ====================

    public int syncTCHouseC(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    public int syncTCHouseD(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    public int syncTCHouseP(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== TDMQ ====================

    public int syncTDMQ(QCloudClient qCloudClient, CloudConf cloudConf) {
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

    // ==================== TCAPLUSDB ====================

    public int syncTcaplusDB(QCloudClient qCloudClient, CloudConf cloudConf) {
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
}
