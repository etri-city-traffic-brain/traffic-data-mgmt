package net.etri.rest.api.sumo.service;

import java.util.List;

import net.etri.rest.api.repository.RequestVO;
import org.apache.ibatis.javassist.NotFoundException;

/**
  <p>클래스 설명 : XML관련처리를 위한 인터페이스</p>
  <p>SumoService</p>
  <pre>
   net.etri.rest.api.sumo.service
          └ SumoService.java
  </pre>
**/
public interface SumoService {

	/**
	 TlLogic 목록을 조회 한다.
 	 @param vo 요청VO
    @return List&lt;TlLogic&gt; TLogic 목록
	*/
	public List<TlLogic> selectTlLogic(RequestVO vo) throws NotFoundException;
	/**
	 Connection 목록을 조회 한다.
	 @param List&lt;TlLogic&gt; TLogic 목록
     @return List&lt;Connection&gt; Connection 목록
	*/
	public List<Connection> selectTLtoConnection(List<TlLogic> tls);
	/**
	 Connection 목록을 조회 한다.
	 @param vo 요청VO
     @return List&lt;Connection&gt; 커넥션 목록
	*/
	public List<Connection> selectConnection(RequestVO vo);
}
