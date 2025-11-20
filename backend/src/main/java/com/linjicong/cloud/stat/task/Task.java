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
package com.linjicong.cloud.stat.task;

import com.linjicong.cloud.stat.dao.entity.CloudConf;
import com.linjicong.cloud.stat.dao.mapper.CloudConfMapper;
import com.linjicong.cloud.stat.service.CloudFactory;
import com.linjicong.cloud.stat.service.CloudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * 定时任务类
 * 用于定时同步各云厂商的资源数据
 * 
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Component
@Slf4j
public class Task {

    /**
     * 云配置数据访问对象
     */
    @Resource
    private CloudConfMapper cloudConfMapper;

    /**
     * 定时同步ECS资源
     * 每天凌晨1点执行，同步所有启用的云配置的ECS资源
     * Cron表达式: "* * 1 * * ?" 表示每天凌晨1点执行
     */
    @Scheduled(cron="* * 1 * * ?")
    public void syncEcs(){
        // 获取所有云配置
        List<CloudConf> cloudConf = cloudConfMapper.selectList(null);
        // 遍历每个配置，执行同步
        cloudConf.forEach(conf->{
            try {
                // 根据云厂商类型获取对应的服务实例
                CloudService service = CloudFactory.getService(conf.getProvider());
                // 执行同步并获取同步数量
                int result = service.syncEcs(conf);
                log.info("成功同步ECS: {} 条记录，配置: {}", result, conf.getName());
            } catch (Exception e) {
                log.error("同步ECS失败，配置: {}，错误: {}", conf.getName(), e.getMessage(), e);
            }
        });
    }
}
