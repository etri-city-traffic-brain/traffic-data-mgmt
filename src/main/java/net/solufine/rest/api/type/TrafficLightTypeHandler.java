/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.type
 * @file TrafficLightTypeHandler.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일			버전			수정자		수정내용
 * -------------	--------	------		-------------------------------
 * 2018. 7. 10.		0.0.1		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import net.solufine.rest.api.xml.type.TrafficLightType;

/**
  <p>클래스 설명 : Mybatis에서 사용하는 열거형 핸들러</p>
  <p>TrafficLightTypeHandler</p>
  <pre>
   net.solufine.rest.api.type
          └ TrafficLightTypeHandler.java
  </pre>
  @author  redkaras 
  @since  2018. 7. 10.
  @version 1.0
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
