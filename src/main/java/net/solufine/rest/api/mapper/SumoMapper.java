/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.mapper
 * @file SumoMapper.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일			버전			수정자		수정내용
 * -------------	--------	------		-------------------------------
 * 2019. 6. 21.		0.0.1		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.mapper;

import java.util.HashMap;
import java.util.List;

import net.solufine.rest.api.cmm.Mapper;
import net.solufine.rest.api.repository.RequestVO;
import net.solufine.rest.api.sumo.service.Connection;
import net.solufine.rest.api.sumo.service.TlLogic;

/**
<p>클래스 설명 : XML 데이터 처리를 위한 맵퍼 인터페이스</p>
<p>XmlMapper</p>
<pre>
 net.solufine.rest.api.mapper
        └ SumoMapper.java
</pre>
@author  redkaras 
@version 1.0
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






