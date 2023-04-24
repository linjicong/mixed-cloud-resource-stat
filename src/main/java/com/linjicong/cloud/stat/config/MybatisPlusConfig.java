package com.linjicong.cloud.stat.config;

import com.baomidou.mybatisplus.annotation.DbType;
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

@Configuration
@Slf4j
public class MybatisPlusConfig implements MetaObjectHandler {

    /**
     * 分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Bean
    public CommonSqlInjector commonSqlInjector(){
        return new CommonSqlInjector();
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        //log.info("start insert fill ....");
        this.strictInsertFill(metaObject, "statDate", Date.class, new Date());
        this.strictInsertFill(metaObject, "confName", String.class, ((BasicEntityExtend)ThreadLocalUtil.get("entityExtend")).getConfName());
        this.strictInsertFill(metaObject, "confProvider", String.class, ((BasicEntityExtend)ThreadLocalUtil.get("entityExtend")).getConfProvider());
        this.strictInsertFill(metaObject, "confRegion", String.class, ((BasicEntityExtend)ThreadLocalUtil.get("entityExtend")).getConfRegion());
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
