package net.etri.rest.api.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.etri.rest.api.xml.type.TrafficLightType;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
  <p>클래스 설명 : Mybatis에서 사용하는 열거형 핸들러</p>
  <p>TrafficLightTypeHandler</p>
  <pre>
   net.etri.rest.api.type
          └ TrafficLightTypeHandler.java
  </pre>
**/
public class TrafficLightTypeHandler implements TypeHandler<TrafficLightType> {

	@Override
	public TrafficLightType getResult(ResultSet rs, String param) throws SQLException {
		return TrafficLightType.get(rs.getString(param));
	}

	@Override
	public TrafficLightType getResult(ResultSet rs, int col) throws SQLException {
		return TrafficLightType.get(rs.getString(col));
	}

	@Override
	public TrafficLightType getResult(CallableStatement cs, int col) throws SQLException {
		return TrafficLightType.get(cs.getString(col));
	}

	@Override
	public void setParameter(PreparedStatement ps, int paramInt, TrafficLightType paramType, JdbcType jdbctype) throws SQLException {
		ps.setInt(paramInt,paramInt);
		
	}
}
