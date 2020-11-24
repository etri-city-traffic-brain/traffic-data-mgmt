/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.mapper
 * @file JsonMapper.java
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

import org.apache.ibatis.annotations.Param;

import net.solufine.rest.api.cmm.Mapper;
import net.solufine.rest.api.json.service.Connection;
import net.solufine.rest.api.json.service.Signal;
import net.solufine.rest.api.json.service.SignalPhase;
import net.solufine.rest.api.json.service.SignalPhaseDefault;
import net.solufine.rest.api.json.service.SignalScenario;
import net.solufine.rest.api.json.service.TrafficSignal;
import net.solufine.rest.api.xml.service.SignalTod;

/**
<p>클래스 설명 : XML 데이터 처리를 위한 맵퍼 인터페이스</p>
<p>XmlMapper</p>
<pre>
 net.solufine.rest.api.mapper
        └ JsonMapper.java
</pre>
@author  redkaras 
@version 1.0
**/
@Mapper("jsonMapper")
public interface JsonMapper {
	/**
	 신호데이터를 조회 한다.
	 @param signal_id 신호ID
	 @param version 버전
    @return TrafficSignal 신호데이터 반환
	*/
	public TrafficSignal getSignal(@Param("signal_id") String signal_id, @Param("version") String version);
	/**
	 신호시간정보를 조회 한다.
	 @param signal_id 신호ID
	 @param version 버전
   	 @return List&lt;SignalPhaseDefault&gt; 신호시간정보 반환
	*/
	public List<SignalPhaseDefault> getSignalTime(@Param("signal_id") String signal_id, @Param("version") String version);
	/**
	 신호현시정보를 조회 한다.
	 @param signal_id 신호ID
	 @param version 버전
  	 @return List&lt;SignalPhaseDefault&gt; 신호시간정보 반환
	*/
	public List<SignalPhase> getSignalPhase(@Param("signal_id") String signal_id, @Param("version") String version);
	/**
	 신호현시정보를 조회 한다.
	 @param signal_id 신호ID
	 @param version 버전
 	 @return List&lt;SignalTod&gt; 신호시간정보 반환
	*/
	public List<SignalTod> getSignalTodPlan(@Param("signal_id") String signal_id, @Param("version") String version);
	
	/**
	 신호시나리오정보를 조회 한다.
	 @param signal_id 신호ID
	 @param version 버전
	 @return List&lt;SignalScenario&gt; 신호시나리오 반환
	*/
	public List<SignalScenario> getSignalScenario(@Param("signal_id") String signal_id, @Param("version") String version);
	
	/**
	 신호목록을 조회 한다.
	 @param signal_id 검색조건
	 @param version 버전
     @return List&lt;Signal&gt; 신호데이터 반환
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
	 신규버전 번호를 조회한다.
	 @param signal_id 신호ID
     @return int 신규버전
	*/
	public int getEditVersion(String signal_id);
	/**
	 신호데이터를 등록 한다.
     @param trafficSignal 업데이트 대상 신호 Json 데이터
     @return int 적용결과 개수
	*/
	public int insertSignal(TrafficSignal trafficSignal);
	/**
	 신호부가데이터를 등록 한다.
    @param list 업데이트 대상 신호부가 데이터
    @return int 적용결과 개수
	*/
	public int insertSignalPhaseDefault(List<HashMap<String, String>> list);
	/**
	 신호현시데이터를 등록 한다.
     @param list 업데이트 대상 신호현시 데이터
     @return int 적용결과 개수
	*/
	public int insertSignalPhase(List<HashMap<String, String>> list);
	/**
	 신호TOD데이터를 등록 한다.
    @param list 업데이트 대상 신호TOD 데이터
    @return int 적용결과 개수
	*/
	public int insertSignalTodPlan(List<HashMap<String, String>> list);
	/**
	 신호시나리오데이터를 등록 한다.
    @param list 업데이트 대상 신호시나리오 데이터
    @return int 적용결과 개수
	*/
	public int insertSignalScenario(List<HashMap<String, String>> list);
	/**
	 신호시나리오현시데이터를 등록 한다.
     @param list 업데이트 대상 신호시나리오현시 데이터
     @return int 적용결과 개수
	*/
	public int insertSignalScenarioPhase(List<HashMap<String, String>> list);
	/**
	 특수일정보를 삭제  한다.
	 @param signal_id 신호ID
	 @param version 버전
	 @return int 적용결과 개수
	*/
	public int deleteSpecialDay(@Param("signal_id") String signal_id, @Param("version") String version);
	/**
	 신호현시시간정보를 삭제  한다.
	 @param signal_id 신호ID
	 @param version 버전
	 @return int 적용결과 개수
	*/
	public int deletePhaseSignalTime(@Param("signal_id") String signal_id, @Param("version") String version);
	/**
	 TOD Plan정보를 삭제  한다.
	 @param signal_id 신호ID
	 @param version 버전
	 @return int 적용결과 개수
	*/
	public int deleteTodPlan(@Param("signal_id") String signal_id, @Param("version") String version);
	/**
	 신호시나리오현시정보를 삭제  한다.
	 @param signal_id 신호ID
	 @param version 버전
	 @return int 적용결과 개수
	*/
	public int deleteSignalScenarioPhase(@Param("signal_id") String signal_id, @Param("version") String version);
	/**
	 신호시나리오정보를 삭제  한다.
	 @param signal_id 신호ID
	 @param version 버전
	 @return int 적용결과 개수
	*/
	public int deleteSignalScenario(@Param("signal_id") String signal_id, @Param("version") String version);
	/**
	 신호현시정보를 삭제  한다.
	 @param signal_id 신호ID
	 @param version 버전
	 @return int 적용결과 개수
	*/
	public int deleteSignalPhase(@Param("signal_id") String signal_id, @Param("version") String version);
	/**
	 신호정보를 삭제  한다.
	 @param signal_id 신호ID
	 @param version 버전
	 @return int 적용결과 개수
	*/
	public int deleteSignal(@Param("signal_id") String signal_id, @Param("version") String version);
}






