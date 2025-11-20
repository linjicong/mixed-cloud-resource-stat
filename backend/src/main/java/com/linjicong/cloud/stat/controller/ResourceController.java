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

import com.linjicong.cloud.stat.dao.mapper.acloud.ACloudDnsDomainMapper;
import com.linjicong.cloud.stat.dao.mapper.hcloud.*;
import com.linjicong.cloud.stat.dao.mapper.qcloud.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 资源查询Controller
 * 
 * @author linjicong
 * @date 2024-12-19
 * @version 1.0.0
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ResourceController {

    // 华为云Mapper
    @Autowired
    private HCloudEcsMapper hCloudEcsMapper;
    @Autowired
    private HCloudRdsMapper hCloudRdsMapper;
    @Autowired
    private HCloudElbMapper hCloudElbMapper;
    @Autowired
    private HCloudEvsMapper hCloudEvsMapper;
    @Autowired
    private HCloudVpcMapper hCloudVpcMapper;
    @Autowired
    private HCloudBillsMonthlyBreakDownMapper hCloudBillsMonthlyBreakDownMapper;

    // 腾讯云Mapper
    @Autowired
    private QCloudCvmMapper qCloudCvmMapper;
    @Autowired
    private QCloudCdbMapper qCloudCdbMapper;
    @Autowired
    private QCloudClbMapper qCloudClbMapper;
    @Autowired
    private QCloudCbsMapper qCloudCbsMapper;
    @Autowired
    private QCloudBillResourceSummaryMapper qCloudBillResourceSummaryMapper;

    // 阿里云Mapper
    @Autowired
    private ACloudDnsDomainMapper aCloudDnsDomainMapper;

    /**
     * 获取华为云资源
     * 
     * @param type 资源类型 (ecs, rds, elb, evs, vpc, bills)
     * @return 资源列表
     */
    @GetMapping("/huawei/{type}")
    public ResponseEntity<?> getHuaweiResources(@PathVariable String type) {
        try {
            List<?> resources = switch (type.toLowerCase()) {
                case "ecs" -> hCloudEcsMapper.selectList(null);
                case "rds" -> hCloudRdsMapper.selectList(null);
                case "elb" -> hCloudElbMapper.selectList(null);
                case "evs" -> hCloudEvsMapper.selectList(null);
                case "vpc" -> hCloudVpcMapper.selectList(null);
                case "bills" -> hCloudBillsMonthlyBreakDownMapper.selectList(null);
                default -> null;
            };
            
            if (resources != null) {
                return ResponseEntity.ok(resources);
            } else {
                return ResponseEntity.badRequest().body("不支持的资源类型: " + type);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("查询失败: " + e.getMessage());
        }
    }

    /**
     * 获取腾讯云资源
     * 
     * @param type 资源类型 (cvm, cdb, clb, cbs, bills)
     * @return 资源列表
     */
    @GetMapping("/tencent/{type}")
    public ResponseEntity<?> getTencentResources(@PathVariable String type) {
        try {
            List<?> resources = switch (type.toLowerCase()) {
                case "cvm" -> qCloudCvmMapper.selectList(null);
                case "cdb" -> qCloudCdbMapper.selectList(null);
                case "clb" -> qCloudClbMapper.selectList(null);
                case "cbs" -> qCloudCbsMapper.selectList(null);
                case "bills" -> qCloudBillResourceSummaryMapper.selectList(null);
                default -> null;
            };
            
            if (resources != null) {
                return ResponseEntity.ok(resources);
            } else {
                return ResponseEntity.badRequest().body("不支持的资源类型: " + type);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("查询失败: " + e.getMessage());
        }
    }

    /**
     * 获取阿里云资源
     * 
     * @param type 资源类型 (dns)
     * @return 资源列表
     */
    @GetMapping("/aliyun/{type}")
    public ResponseEntity<?> getAliyunResources(@PathVariable String type) {
        try {
            List<?> resources = switch (type.toLowerCase()) {
                case "dns" -> aCloudDnsDomainMapper.selectList(null);
                default -> null;
            };
            
            if (resources != null) {
                return ResponseEntity.ok(resources);
            } else {
                return ResponseEntity.badRequest().body("不支持的资源类型: " + type);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("查询失败: " + e.getMessage());
        }
    }
}

