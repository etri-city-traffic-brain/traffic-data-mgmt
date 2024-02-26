package net.etri.rest.api.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
  <p>클래스 설명 : Mybatis에서 사용하는 열거형 핸들러</p>
  <p>RequestTypeHandler</p>
  <pre>
   net.etri.rest.api.type
          └ RequestTypeHandler.java
  </pre>
**/
public class RequestTypeHandler implements  TypeHandler<RequestType> {

	@Override
	public RequestType getResult(ResultSet rs, String param) throws SQLException {
		return RequestType.get(rs.getInt(param));
	}

	@Override
	public RequestType getResult(ResultSet rs, int col) throws SQLException {
		return RequestType.get(rs.getInt(col));
	}

	@Override
	public RequestType getResult(CallableStatement cs, int col) throws SQLException {
		return RequestType.get(cs.getInt(col));
	}

	@Override
	public void setParameter(PreparedStatement ps, int paramInt, RequestType paramType, JdbcType jdbctype) throws SQLException {
		ps.setInt(paramInt,paramInt);
		
	}

}
