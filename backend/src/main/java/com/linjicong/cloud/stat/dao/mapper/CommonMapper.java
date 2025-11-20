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
package com.linjicong.cloud.stat.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * 通用Mapper接口
 *
 * @param <T> 不能为空
 * @author liuzh
 * @author linjicong
  * @version 1.0.0
  * @date 2022-08-01-11:42
 */
public interface CommonMapper <T> extends BaseMapper<T> {

    int insertBatchSomeColumn(Collection<T> entityList);

    int insertBatch(@Param("list") Collection<T> entityList);

    /**
     * 删除某天的数据
     *
     */
    int deleteByStatDateAndConfName(String statDate, String confName);

    /**
     * 获取某天的数据
     *
     */
    List<T> selectByStatDateAndConfName(String statDate, String confName);

    /**
     * 删除某天的数据
     *
     */
    int deleteByStatDate(String statDate);

    /**
     * 获取某天的数据
     *
     */
    List<T> selectByStatDate(String statDate);

    /**
     * 获取某个配置的数据
     *
     */
    List<T> selectByConfName(String confName);
}
