/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.type
 * @file RequestTypeHandler.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일			버전			수정자		수정내용
 * -------------	--------	------		-------------------------------
 * 2018. 7. 9.		0.0.1		redkaras	최초작성
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

/**
  <p>클래스 설명 : Mybatis에서 사용하는 열거형 핸들러</p>
  <p>RequestTypeHandler</p>
  <pre>
   net.solufine.rest.api.type
          └ RequestTypeHandler.java
  </pre>
  @author  redkaras 
  @since  2018. 7. 9.
  @version 1.0
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
