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

import com.linjicong.cloud.stat.dao.entity.CloudConf;
import com.linjicong.cloud.stat.dao.mapper.CloudConfMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 云配置管理Controller
 * 
 * @author linjicong
 * @date 2024-12-19
 * @version 1.0.0
 */
@RestController
@RequestMapping("/cloud-configs")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CloudConfigController {

    @Autowired
    private CloudConfMapper cloudConfMapper;

    /**
     * 获取所有云配置列表
     * 
     * @return 云配置列表
     */
    @GetMapping
    public ResponseEntity<List<CloudConf>> getAllConfigs() {
        try {
            List<CloudConf> configs = cloudConfMapper.selectList(null);
            return ResponseEntity.ok(configs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 根据ID获取云配置
     * 
     * @param id 配置ID
     * @return 云配置信息
     */
    @GetMapping("/{id}")
    public ResponseEntity<CloudConf> getConfigById(@PathVariable Integer id) {
        try {
            CloudConf config = cloudConfMapper.selectById(id);
            if (config != null) {
                return ResponseEntity.ok(config);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 添加新的云配置
     * 
     * @param cloudConf 云配置信息
     * @return 创建后的云配置信息
     */
    @PostMapping
    public ResponseEntity<CloudConf> addConfig(@RequestBody CloudConf cloudConf) {
        try {
            Date now = new Date();
            cloudConf.setCreateTime(now);
            cloudConf.setUpdateTime(now);
            if (cloudConf.getEnable() == null) {
                cloudConf.setEnable(true);
            }
            cloudConfMapper.insert(cloudConf);
            return ResponseEntity.status(HttpStatus.CREATED).body(cloudConf);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 更新云配置
     * 
     * @param id 配置ID
     * @param cloudConf 更新的云配置信息
     * @return 更新后的云配置信息
     */
    @PutMapping("/{id}")
    public ResponseEntity<CloudConf> updateConfig(@PathVariable Integer id, @RequestBody CloudConf cloudConf) {
        try {
            CloudConf existingConfig = cloudConfMapper.selectById(id);
            if (existingConfig == null) {
                return ResponseEntity.notFound().build();
            }
            
            cloudConf.setPk(id);
            cloudConf.setUpdateTime(new Date());
            // 如果createTime为空，保留原有的创建时间
            if (cloudConf.getCreateTime() == null) {
                cloudConf.setCreateTime(existingConfig.getCreateTime());
            }
            
            cloudConfMapper.updateById(cloudConf);
            CloudConf updatedConfig = cloudConfMapper.selectById(id);
            return ResponseEntity.ok(updatedConfig);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 删除云配置
     * 
     * @param id 配置ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConfig(@PathVariable Integer id) {
        try {
            CloudConf existingConfig = cloudConfMapper.selectById(id);
            if (existingConfig == null) {
                return ResponseEntity.notFound().build();
            }
            
            cloudConfMapper.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

