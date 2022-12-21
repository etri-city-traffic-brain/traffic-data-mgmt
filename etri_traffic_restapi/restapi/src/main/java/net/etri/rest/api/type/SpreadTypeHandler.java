package net.etri.rest.api.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.etri.rest.api.xml.type.SpreadType;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
  <p>클래스 설명 : Mybatis에서 사용하는 열거형 핸들러</p>
  <p>SpreadTypeHandler</p>
  <pre>
   net.etri.rest.api.type
          └ SpreadTypeHandler.java
  </pre>
**/
public class SpreadTypeHandler implements TypeHandler<SpreadType> {

	@Override
	public SpreadType getResult(ResultSet rs, String param) throws SQLException {
		return SpreadType.get(rs.getString(param));
	}

	@Override
	public SpreadType getResult(ResultSet rs, int col) throws SQLException {
		return SpreadType.get(rs.getString(col));
	}

	@Override
	public SpreadType getResult(CallableStatement cs, int col) throws SQLException {
		return SpreadType.get(cs.getString(col));
	}

	@Override
	public void setParameter(PreparedStatement ps, int paramInt, SpreadType paramType, JdbcType jdbctype) throws SQLException {
		ps.setInt(paramInt,paramInt);
		
	}
}
