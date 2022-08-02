package com.linjicong.cloud.stat.dao.typehandle.impl.huawei;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;
import java.time.OffsetDateTime;
import java.time.ZoneId;

public class OffsetDateTimeTypeHandler extends BaseTypeHandler<OffsetDateTime> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, OffsetDateTime parameter, JdbcType jdbcType)
          throws SQLException {
    ps.setTimestamp(i, Timestamp.from(parameter.toInstant()));
  }

  @Override
  public OffsetDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
    Timestamp timestamp = rs.getTimestamp(columnName);
    return getOffsetDateTime(timestamp);
  }

  @Override
  public OffsetDateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    Timestamp timestamp = rs.getTimestamp(columnIndex);
    return getOffsetDateTime(timestamp);
  }

  @Override
  public OffsetDateTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    Timestamp timestamp = cs.getTimestamp(columnIndex);
    return getOffsetDateTime(timestamp);
  }

  private static OffsetDateTime getOffsetDateTime(Timestamp timestamp) {
    if (timestamp != null) {
      // 这里可以考虑自定义系统的时区，例如ZoneId.of("Asia/Shanghai")
      return OffsetDateTime.ofInstant(timestamp.toInstant(), ZoneId.systemDefault());
    }
    return null;
  }
}
