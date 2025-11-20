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
package com.linjicong.cloud.stat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 混合云资源统计应用主类
 * 支持华为云、腾讯云、阿里云等多家云厂商的资源统计和管理
 * 
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@SpringBootApplication
@MapperScan("com.linjicong.cloud.stat.dao.mapper") // 扫描MyBatis Mapper接口
@EntityScan("com.linjicong.cloud.stat.dao.entity") // 扫描JPA实体类
@EnableScheduling // 启用定时任务支持
//@EnableCaching // 缓存功能（已注释）
@Import(cn.hutool.extra.spring.SpringUtil.class) // 导入Hutool的Spring工具类
public class MixedCloudResourceStatApplication {

    /**
     * 应用启动入口
     * 
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(MixedCloudResourceStatApplication.class, args);
        //ConfigurableApplicationContext run = SpringApplication.run(MixedCloudResourceStatApplication.class, args);
        //String[] beanDefinitionNames = run.getBeanDefinitionNames();
        //System.out.println(ArrayUtil.toString(beanDefinitionNames));
    }

}
