package com.linjicong.cloud.stat.dao.mapper;

import org.apache.ibatis.annotations.DeleteProvider;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

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
public interface MultiMapper<T> extends BaseMapper<T>, InsertListMapper<T> {
}
