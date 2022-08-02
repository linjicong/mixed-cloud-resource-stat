package com.linjicong.cloud.stat;

import cn.hutool.core.util.ArrayUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.linjicong.cloud.stat.dao.mapper")
@EntityScan("com.linjicong.cloud.stat.dao.entity.hcloud")
@EnableScheduling
@Import(cn.hutool.extra.spring.SpringUtil.class)
public class MixedCloudResourceStatApplication {

    public static void main(String[] args) {
        SpringApplication.run(MixedCloudResourceStatApplication.class, args);
        //ConfigurableApplicationContext run = SpringApplication.run(MixedCloudResourceStatApplication.class, args);
        //String[] beanDefinitionNames = run.getBeanDefinitionNames();
        //System.out.println(ArrayUtil.toString(beanDefinitionNames));
    }

}
