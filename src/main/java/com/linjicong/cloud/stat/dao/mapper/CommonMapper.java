package com.linjicong.cloud.stat.dao.mapper;

import org.apache.ibatis.annotations.DeleteProvider;

/**
 * 通用Mapper接口
 *
 * @param <T> 不能为空
 * @author liuzh
 * @author linjicong
  * @version 1.0.0
  * @date 2022-08-01-11:42
 */
@tk.mybatis.mapper.annotation.RegisterMapper
public interface CommonMapper <T> extends MultiMapper<T> {

    /**
     * 删除某天的数据
     *
     */
    @DeleteProvider(type = CommonSelectProvider.class, method = "dynamicSQL")
    int deleteByStatDate(String statDate);
}
