package net.etri.rest.api.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.etri.rest.api.xml.type.NodeType;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
  <p>클래스 설명 : Mybatis에서 사용하는 열거형 핸들러</p>
  <p>NodeTypeHandler</p>
  <pre>
   net.etri.rest.api.type
          └ NodeTypeHandler.java
  </pre>
**/
public class NodeTypeHandler implements TypeHandler<NodeType> {

	@Override
	public NodeType getResult(ResultSet rs, String param) throws SQLException {
		return NodeType.get(rs.getString(param));
	}

	@Override
	public NodeType getResult(ResultSet rs, int col) throws SQLException {
		return NodeType.get(rs.getString(col));
	}

	@Override
	public NodeType getResult(CallableStatement cs, int col) throws SQLException {
		return NodeType.get(cs.getString(col));
	}

	@Override
	public void setParameter(PreparedStatement ps, int paramInt, NodeType paramType, JdbcType jdbctype) throws SQLException {
		ps.setInt(paramInt,paramInt);
		
	}

}
