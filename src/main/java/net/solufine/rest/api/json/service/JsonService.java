/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.json.service
 * @file JsonService.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일			버전			수정자		수정내용
 * -------------	--------	------		-------------------------------
 * 2019. 6. 21.		0.0.1		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.json.service;

import java.util.List;

/**
<p>클래스 설명 : </p>
<p>JsonService</p>
<pre>
 net.solufine.rest.api.json.service
        └ JsonService.java
</pre>
@author  redkaras 
@since  2019. 6. 21.
@version 1.0
**/
public interface JsonService {

	/**
	 신호데이터를 업데이트 한다.
     @param trafficSignal 업데이트 대상 신호 Json 데이터
     @return int 적용결과 개수
	*/
	public int updateSignal(TrafficSignal trafficSignal);
	
	/**
	 신호데이터를 조회 한다.
 	 @param signal_id 신호ID
 	 @param version 버전
     @return TrafficSignal 신호데이터 반환
	*/
	public TrafficSignal getSignal(String signal_id, String version);
	
	/**
	 신호목록을 조회 한다.
	 @param signal_id
	 @param version 버전
     @return TrafficSignal 신호데이터 반환
	*/
	public List<Signal> getSignalInfo(String signal_id);
	
	/**
	 커넥션목록을 조회 한다.
	 @param junction_id 검색조건
	 @param version 버전
   @return List&lt;Connection&gt; 커넥션데이터 반환
	*/
	public List<Connection> getConnection(String junction_id);
	/**
	 신호데이터를 삭제 한다.
	 @param signal_id 신호ID
	 @param version 버전
	*/
	public void deleteSignal(String signal_id, String version);
}
