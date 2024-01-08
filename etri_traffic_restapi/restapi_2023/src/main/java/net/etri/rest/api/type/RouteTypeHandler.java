package net.etri.rest.api.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
  <p>클래스 설명 : Mybatis에서 사용하는 열거형 핸들러</p>
  <p>RouteTypeHandler</p>
  <pre>
   net.etri.rest.api.type
          └ RouteTypeHandler.java
  </pre>
**/
public class RouteTypeHandler implements TypeHandler<RouteType> {
	
	@Override
	public RouteType getResult(ResultSet rs, String param) throws SQLException {
		return RouteType.get(rs.getInt(param));
	}

	@Override
	public RouteType getResult(ResultSet rs, int col) throws SQLException {
		return RouteType.get(rs.getInt(col));
	}

	@Override
	public RouteType getResult(CallableStatement cs, int col) throws SQLException {
		return RouteType.get(cs.getInt(col));
	}

	@Override
	public void setParameter(PreparedStatement ps, int paramInt, RouteType paramType, JdbcType jdbctype) throws SQLException {
		ps.setInt(paramInt,paramInt);
		
	}
}
