/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.service
 * @file DefaultService.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일				수정자			수정내용
 * -------------	------		-------------------------------
 * 2018. 5. 23.		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.service;

import java.util.List;

import net.solufine.rest.api.repository.CodeVO;
import net.solufine.rest.api.repository.DongVO;
import net.solufine.rest.api.repository.RequestVO;
import net.solufine.rest.api.repository.SiguVO;

/**
  <p>클래스 설명 : 기본정보 서비스 인터페이스</p>
  <p>DefaultService</p>
  <pre>
   net.solufine.rest.api.service
          └ DefaultService.java
  </pre>
  @author  redkaras 
  @since  2018. 5. 23.
  @version 1.0
**/
public interface DefaultService {
	/**
	 초기구코드
     @return String 초기구코드
	
	public String getDefaultSigu();
	*/
	/**
	  구정보조회
  	 @param value 조회 조건
     @return List&lt;SiguVO&gt; 시구VO 목록
	*/
	public List<SiguVO> selectSigu(String value);
	/**
	 동정보조회
  	 @param value 조회 조건
     @return List&lt;DongVO&gt; 동코드VO 목록
	*/
	public List<DongVO> selectDong(String value);
	/**
	  코드정보 목록조회
	 @param value 조회대상 코드그룹
     @return List&lt;CodeVO&gt; 코드정보VO객체 목록 반환   
	*/
	public List<CodeVO> selectCode(String value);
	/**
	 XML 요청 이력을 조회한다.
  	 @param vo 요청VO
     @return List&lt;RequestVO&gt; 요청VO 목록
	*/
	public List<RequestVO> selecteRequstHistory(RequestVO vo);
	/**
	 XML 요청 이력 개수조회
     @return int 개수
	*/
	public int selecteRequstHistoryCount();
	/**
	  파티션NO별 파티션 개수목록을 반환
 	 @param value 시구코드
 	 @return List&lt;String&gt;	 파티션개수 목록
	*/
	public List<String> selectePartitionCount(String value);
	/**
	  신호버전 목록을 반환
	 @return List&lt;String&gt;	 신호버전 목록
	*/
	public List<String> selecteSignalVersion();
	/**
	  신호현시 상태가 계속 R인것만 추출
	 @return List&lt;String&gt;	  신호현시 상태가 계속 R인것만 추출
	*/
	public List<String> getAllR();
}
