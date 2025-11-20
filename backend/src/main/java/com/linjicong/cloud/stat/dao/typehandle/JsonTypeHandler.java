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
package com.linjicong.cloud.stat.dao.typehandle;

import cn.hutool.json.JSONUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * JSON类型处理器
 * 用于MyBatis中JSON类型字段的序列化和反序列化
 * 将Java对象转换为JSON字符串存储到数据库，从数据库读取时转换为Java对象
 * 
 * @param <T> 处理的Java对象类型
 * @author linjicong
 * @date 2022-07-28
 * @version 1.0.0
 */
public class JsonTypeHandler<T> extends BaseTypeHandler<T> {
    //private static final ObjectMapper mapper = new ObjectMapper();
    private final Class<T> clazz;

    public JsonTypeHandler(Class<T> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.clazz = clazz;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, JSONUtil.toJsonStr(parameter));
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return this.toObject(rs.getString(columnName), clazz);
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return this.toObject(rs.getString(columnIndex), clazz);
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return this.toObject(cs.getString(columnIndex), clazz);
    }

    //private String toJson(T object) {
    //    try {
    //        return mapper.writeValueAsString(object);
    //    } catch (Exception e) {
    //        throw new RuntimeException(e);
    //    }
    //}

    @SuppressWarnings("unchecked")
    private T toObject(String content, Class<?> clazz) {
        return (T) JSONUtil.toBean(content, clazz);
    }

    //static {
    //    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    //}
}
