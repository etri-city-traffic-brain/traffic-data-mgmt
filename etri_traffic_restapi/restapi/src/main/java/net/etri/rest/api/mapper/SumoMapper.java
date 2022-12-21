package net.etri.rest.api.mapper;

import java.util.HashMap;
import java.util.List;

import net.etri.rest.api.repository.RequestVO;
import net.etri.rest.api.sumo.service.Connection;
import net.etri.rest.api.sumo.service.TlLogic;
import net.etri.rest.api.cmm.Mapper;

/**
<p>클래스 설명 : XML 데이터 처리를 위한 맵퍼 인터페이스</p>
<p>XmlMapper</p>
<pre>
 net.etri.rest.api.mapper
        └ SumoMapper.java
</pre>
**/
@Mapper("sumoMapper")
public interface SumoMapper {
	/**
	 TlLogic 목록을 조회 한다.
	 @param vo 요청VO
     @return List&lt;HashMap&lt;String, Object&gt;&gt; TLogic 목록
	*/
	public List<HashMap<String, Object>> selectTlLogic(RequestVO vo);
	
	/**
	 Connection 목록을 조회 한다.
 	 @param tls TLogic 목록
     @return List&lt;Connection&gt; 커넥션 목록
	*/
	public List<Connection> selectTLtoConnection(List<TlLogic> tls);
	
	/**
	 Connection 목록을 조회 한다.
	 @param vo 요청VO
     @return List&lt;Connection&gt; 커넥션 목록
	*/
	public List<Connection> selectConnection(RequestVO vo);
}






