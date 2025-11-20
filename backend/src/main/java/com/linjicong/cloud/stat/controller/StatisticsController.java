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

import java.util.HashMap;
import java.util.Map;

/**
 * 统计数据Controller
 * 
 * @author linjicong
 * @date 2024-12-19
 * @version 1.0.0
 */
@RestController
@RequestMapping("/statistics")
@CrossOrigin(origins = "*", maxAge = 3600)
public class StatisticsController {

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

    // 腾讯云Mapper
    @Autowired
    private QCloudCvmMapper qCloudCvmMapper;
    @Autowired
    private QCloudCdbMapper qCloudCdbMapper;
    @Autowired
    private QCloudClbMapper qCloudClbMapper;
    @Autowired
    private QCloudCbsMapper qCloudCbsMapper;

    // 阿里云Mapper
    @Autowired
    private ACloudDnsDomainMapper aCloudDnsDomainMapper;

    /**
     * 获取统计数据
     * 
     * @return 统计数据
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getStatistics() {
        try {
            Map<String, Object> statistics = new HashMap<>();

            // 华为云统计
            Map<String, Integer> huaweiStats = new HashMap<>();
            huaweiStats.put("ecs", hCloudEcsMapper.selectList(null).size());
            huaweiStats.put("rds", hCloudRdsMapper.selectList(null).size());
            huaweiStats.put("elb", hCloudElbMapper.selectList(null).size());
            huaweiStats.put("evs", hCloudEvsMapper.selectList(null).size());
            huaweiStats.put("vpc", hCloudVpcMapper.selectList(null).size());
            statistics.put("huawei", huaweiStats);

            // 腾讯云统计
            Map<String, Integer> tencentStats = new HashMap<>();
            tencentStats.put("cvm", qCloudCvmMapper.selectList(null).size());
            tencentStats.put("cdb", qCloudCdbMapper.selectList(null).size());
            tencentStats.put("clb", qCloudClbMapper.selectList(null).size());
            tencentStats.put("cbs", qCloudCbsMapper.selectList(null).size());
            statistics.put("tencent", tencentStats);

            // 阿里云统计
            Map<String, Integer> aliyunStats = new HashMap<>();
            aliyunStats.put("dns", aCloudDnsDomainMapper.selectList(null).size());
            statistics.put("aliyun", aliyunStats);

            // 总计
            int totalHuawei = huaweiStats.values().stream().mapToInt(Integer::intValue).sum();
            int totalTencent = tencentStats.values().stream().mapToInt(Integer::intValue).sum();
            int totalAliyun = aliyunStats.values().stream().mapToInt(Integer::intValue).sum();
            
            Map<String, Integer> totals = new HashMap<>();
            totals.put("huawei", totalHuawei);
            totals.put("tencent", totalTencent);
            totals.put("aliyun", totalAliyun);
            totals.put("all", totalHuawei + totalTencent + totalAliyun);
            statistics.put("totals", totals);

            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "获取统计数据失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}

