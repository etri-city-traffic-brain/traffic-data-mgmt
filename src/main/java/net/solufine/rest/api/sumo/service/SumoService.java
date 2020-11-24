/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.sumo.service
 * @file SumoService.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일			버전			수정자		수정내용
 * -------------	--------	------		-------------------------------
 * 2018. 6. 11.		0.0.1		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.sumo.service;

import java.util.List;

import org.apache.ibatis.javassist.NotFoundException;

import net.solufine.rest.api.repository.RequestVO;

/**
  <p>클래스 설명 : XML관련처리를 위한 인터페이스</p>
  <p>SumoService</p>
  <pre>
   net.solufine.rest.api.sumo.service
          └ SumoService.java
  </pre>
  @author  redkaras 
  @since  2018. 6. 11.
  @version 1.0
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
