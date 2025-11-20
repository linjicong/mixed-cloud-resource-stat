package com.linjicong.cloud.stat.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.linjicong.cloud.stat.dao.entity.BasicEntityExtend;
import com.linjicong.cloud.stat.dao.mapper.CommonSqlInjector;
import com.linjicong.cloud.stat.util.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * MyBatis Plus配置类
 * 配置分页插件、SQL注入器以及自动填充功能
 * 
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
@Configuration
@Slf4j
public class MybatisPlusConfig implements MetaObjectHandler {

    /**
     * 配置MyBatis Plus拦截器，添加分页插件
     * 
     * @return MyBatis Plus拦截器
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 添加分页内部拦截器
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }

    /**
     * 配置自定义SQL注入器
     * 用于扩展MyBatis Plus的SQL功能
     * 
     * @return 自定义SQL注入器
     */
    @Bean
    public CommonSqlInjector commonSqlInjector(){
        return new CommonSqlInjector();
    }

    /**
     * 插入时自动填充字段
     * 自动填充统计日期、配置名称、云厂商、区域等公共字段
     * 
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        //log.info("start insert fill ....");
        // 自动填充统计日期
        this.strictInsertFill(metaObject, "statDate", Date.class, new Date());
        // 从ThreadLocal获取扩展信息并填充配置名称
        this.strictInsertFill(metaObject, "confName", String.class, ((BasicEntityExtend)ThreadLocalUtil.get("entityExtend")).getConfName());
        // 填充云厂商
        this.strictInsertFill(metaObject, "confProvider", String.class, ((BasicEntityExtend)ThreadLocalUtil.get("entityExtend")).getConfProvider());
        // 填充区域
        this.strictInsertFill(metaObject, "confRegion", String.class, ((BasicEntityExtend)ThreadLocalUtil.get("entityExtend")).getConfRegion());
    }

    /**
     * 更新时自动填充字段
     * 当前未实现更新时的自动填充
     * 
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新时暂不需要自动填充
    }
}
