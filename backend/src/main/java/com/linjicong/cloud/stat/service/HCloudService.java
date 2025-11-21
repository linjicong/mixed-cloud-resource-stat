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
package com.linjicong.cloud.stat.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.linjicong.cloud.stat.client.HCloudClient;
import com.linjicong.cloud.stat.dao.entity.CloudConf;
import com.linjicong.cloud.stat.dao.entity.hcloud.HCloudEcs;
import com.linjicong.cloud.stat.dao.entity.hcloud.HCloudEip;
import com.linjicong.cloud.stat.dao.mapper.hcloud.HCloudEcsMapper;
import com.linjicong.cloud.stat.dao.mapper.hcloud.HCloudEipMapper;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 华为云服务实现类
 * 实现华为云资源的同步功能
 * 
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Service
public class HCloudService implements CloudService{

    /**
     * 华为云ECS数据访问对象
     */
    @Resource
    private HCloudEcsMapper hCloudEcsMapper;

    /**
     * 华为云EIP数据访问对象
     */
    @Resource
    private HCloudEipMapper hCloudEipMapper;

    /**
     * 同步华为云ECS资源
     * 1. 创建华为云客户端
     * 2. 获取ECS列表（从接口）
     * 3. 查询数据库中该配置的所有未删除数据（不按日期）
     * 4. 对比接口数据和数据库数据：
     *    - 接口有但数据库没有的：新增插入
     *    - 数据库有但接口没有的：逻辑删除
     * 
     * @param cloudConf 云配置信息
     * @return 同步的ECS数量（新增数量）
     */
    @Override
    public int syncEcs(CloudConf cloudConf) {
        // 创建华为云客户端
        HCloudClient hCloudClient = new HCloudClient(cloudConf);
        // 获取ECS列表（从接口）
        List<HCloudEcs> apiEcsList = hCloudClient.listEcs();
        
        // 查询数据库中该配置的所有未删除数据（不按日期，@TableLogic自动过滤已删除的数据）
        List<HCloudEcs> dbEcsList = hCloudEcsMapper.selectByConfName(cloudConf.getName());
        
        // 将接口数据转换为Map，以id为key
        Map<String, HCloudEcs> apiEcsMap = apiEcsList.stream()
                .filter(ecs -> ecs.getId() != null)
                .collect(Collectors.toMap(HCloudEcs::getId, ecs -> ecs, (existing, replacement) -> existing));
        
        // 将数据库数据转换为Map，以id为key
        Map<String, HCloudEcs> dbEcsMap = dbEcsList.stream()
                .filter(ecs -> ecs.getId() != null)
                .collect(Collectors.toMap(HCloudEcs::getId, ecs -> ecs, (existing, replacement) -> existing));
        
        // 找出需要新增的数据（接口有但数据库未删除记录中没有的）
        List<HCloudEcs> toInsert = apiEcsList.stream()
                .filter(ecs -> ecs.getId() != null && !dbEcsMap.containsKey(ecs.getId()))
                .collect(Collectors.toList());
        
        // 找出需要逻辑删除的数据（数据库有但接口没有的）
        Set<String> toDeleteIds = dbEcsMap.keySet().stream()
                .filter(id -> !apiEcsMap.containsKey(id))
                .collect(Collectors.toSet());
        
        // 批量插入新增的数据
        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudEcsMapper.insertBatch(toInsert);
        }
        
        // 批量逻辑删除（将deleted字段设置为1）
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudEcs> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(HCloudEcs::getConfName, cloudConf.getName())
                    .in(HCloudEcs::getId, toDeleteIds)
                    .set(HCloudEcs::getDeleted, 1);
            hCloudEcsMapper.update(null, updateWrapper);
        }
        
        return insertCount;
    }

    /**
     * 同步华为云EIP资源
     * 1. 创建华为云客户端
     * 2. 获取EIP列表（从接口）
     * 3. 查询数据库中该配置的所有未删除数据（不按日期）
     * 4. 对比接口数据和数据库数据：
     *    - 接口有但数据库没有的：新增插入
     *    - 数据库有但接口没有的：逻辑删除
     * 
     * @param cloudConf 云配置信息
     * @return 同步的EIP数量（新增数量）
     */
    public int syncEip(CloudConf cloudConf) {
        // 创建华为云客户端
        HCloudClient hCloudClient = new HCloudClient(cloudConf);
        // 获取EIP列表（从接口）
        List<HCloudEip> apiEipList = hCloudClient.listEip();
        
        // 查询数据库中该配置的所有未删除数据（不按日期，@TableLogic自动过滤已删除的数据）
        List<HCloudEip> dbEipList = hCloudEipMapper.selectByConfName(cloudConf.getName());
        
        // 将接口数据转换为Map，以id为key
        Map<String, HCloudEip> apiEipMap = apiEipList.stream()
                .filter(eip -> eip.getId() != null)
                .collect(Collectors.toMap(HCloudEip::getId, eip -> eip, (existing, replacement) -> existing));
        
        // 将数据库数据转换为Map，以id为key
        Map<String, HCloudEip> dbEipMap = dbEipList.stream()
                .filter(eip -> eip.getId() != null)
                .collect(Collectors.toMap(HCloudEip::getId, eip -> eip, (existing, replacement) -> existing));
        
        // 找出需要新增的数据（接口有但数据库未删除记录中没有的）
        List<HCloudEip> toInsert = apiEipList.stream()
                .filter(eip -> eip.getId() != null && !dbEipMap.containsKey(eip.getId()))
                .collect(Collectors.toList());
        
        // 找出需要逻辑删除的数据（数据库有但接口没有的）
        Set<String> toDeleteIds = dbEipMap.keySet().stream()
                .filter(id -> !apiEipMap.containsKey(id))
                .collect(Collectors.toSet());
        
        // 批量插入新增的数据
        int insertCount = 0;
        if (!toInsert.isEmpty()) {
            insertCount = hCloudEipMapper.insertBatch(toInsert);
        }
        
        // 批量逻辑删除（将deleted字段设置为1）
        if (!toDeleteIds.isEmpty()) {
            LambdaUpdateWrapper<HCloudEip> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(HCloudEip::getConfName, cloudConf.getName())
                    .in(HCloudEip::getId, toDeleteIds)
                    .set(HCloudEip::getDeleted, 1);
            hCloudEipMapper.update(null, updateWrapper);
        }
        
        return insertCount;
    }
}
