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
 * 腾讯云Application Part7资源同步服务
 * 包含以下资源类型的同步方法: Im
 *
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Service
public class QCloudApplicationServicePart7 {

    @Resource
    private QCloudImMapper qCloudImMapper;

    public int syncIm(QCloudClient qCloudClient, CloudConf cloudConf) {
        List<QCloudIm> apiList = qCloudClient.listIm();
        List<QCloudIm> dbList = qCloudImMapper.selectByConfName(cloudConf.getName());
        Map<String, QCloudIm> apiMap = apiList.stream().filter(e -> e.getAppId() != null).collect(Collectors.toMap(QCloudIm::getAppId, e -> e, (a, b) -> a));
        Map<String, QCloudIm> dbMap = dbList.stream().filter(e -> e.getAppId() != null).collect(Collectors.toMap(QCloudIm::getAppId, e -> e, (a, b) -> a));
        List<QCloudIm> toInsert = apiList.stream().filter(e -> e.getAppId() != null && !dbMap.containsKey(e.getAppId())).collect(Collectors.toList());
        Set<String> toDeleteIds = dbMap.keySet().stream().filter(id -> !apiMap.containsKey(id)).collect(Collectors.toSet());
        int insertCount = 0;
        if (!toInsert.isEmpty()) insertCount = qCloudImMapper.insertBatch(toInsert);
        if (!toDeleteIds.isEmpty()) { LambdaUpdateWrapper<QCloudIm> uw = new LambdaUpdateWrapper<>(); uw.eq(QCloudIm::getConfName, cloudConf.getName()).in(QCloudIm::getAppId, toDeleteIds).set(QCloudIm::getDeleted, 1); qCloudImMapper.update(null, uw); }
        return insertCount;
    }
}
