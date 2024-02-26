package net.etri.rest.api.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.etri.rest.api.xml.type.TlLogicType;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
  <p>클래스 설명 : Mybatis에서 사용하는 열거형 핸들러</p>
  <p>TlLogicTypeHandler</p>
  <pre>
   net.etri.rest.api.type
          └ TlLogicTypeHandler.java
  </pre>
**/
public class TlLogicTypeHandler implements TypeHandler<TlLogicType> {

	@Override
	public TlLogicType getResult(ResultSet rs, String param) throws SQLException {
		return TlLogicType.get(rs.getString(param));
	}

	@Override
	public TlLogicType getResult(ResultSet rs, int col) throws SQLException {
		return TlLogicType.get(rs.getString(col));
	}

	@Override
	public TlLogicType getResult(CallableStatement cs, int col) throws SQLException {
		return TlLogicType.get(cs.getString(col));
	}

	@Override
	public void setParameter(PreparedStatement ps, int paramInt, TlLogicType paramType, JdbcType jdbctype) throws SQLException {
		ps.setInt(paramInt,paramInt);
		
	}
}
