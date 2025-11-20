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
package com.linjicong.cloud.stat.controller;

import com.linjicong.cloud.stat.dao.constant.CloudConstant;
import com.linjicong.cloud.stat.dao.entity.CloudConf;
import com.linjicong.cloud.stat.dao.mapper.CloudConfMapper;
import com.linjicong.cloud.stat.service.CloudFactory;
import com.linjicong.cloud.stat.service.CloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资源同步Controller
 * 
 * @author linjicong
 * @date 2024-12-19
 * @version 1.0.0
 */
@RestController
@RequestMapping("/sync")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SyncController {

    @Autowired
    private CloudConfMapper cloudConfMapper;

    /**
     * 同步指定云厂商的资源
     * 
     * @param provider 云厂商 (huawei, tencent, aliyun)
     * @return 同步结果
     */
    @PostMapping("/{provider}")
    public ResponseEntity<Map<String, Object>> syncResources(@PathVariable String provider) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 将前端传入的provider转换为后端常量
            String providerConstant = switch (provider.toLowerCase()) {
                case "huawei", "hcloud" -> CloudConstant.H_CLOUD;
                case "tencent", "qcloud" -> CloudConstant.Q_CLOUD;
                case "aliyun", "acloud" -> "ACloud"; // 阿里云暂时不支持同步
                default -> null;
            };

            if (providerConstant == null) {
                result.put("success", false);
                result.put("message", "不支持的云厂商: " + provider);
                return ResponseEntity.badRequest().body(result);
            }

            // 获取该云厂商的所有配置
            List<CloudConf> configs = cloudConfMapper.selectList(null);
            List<CloudConf> filteredConfigs = configs.stream()
                    .filter(conf -> providerConstant.equals(conf.getProvider()) && 
                            (conf.getEnable() == null || conf.getEnable()))
                    .toList();

            if (filteredConfigs.isEmpty()) {
                result.put("success", false);
                result.put("message", "未找到启用的" + provider + "云配置");
                return ResponseEntity.badRequest().body(result);
            }

            int totalSynced = 0;
            for (CloudConf conf : filteredConfigs) {
                try {
                    CloudService service = CloudFactory.getService(conf.getProvider());
                    int count = service.syncEcs(conf);
                    totalSynced += count;
                } catch (Exception e) {
                    result.put("success", false);
                    result.put("message", "同步配置 " + conf.getName() + " 失败: " + e.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
                }
            }

            result.put("success", true);
            result.put("message", "同步成功");
            result.put("syncedCount", totalSynced);
            result.put("configCount", filteredConfigs.size());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "同步失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }
}

