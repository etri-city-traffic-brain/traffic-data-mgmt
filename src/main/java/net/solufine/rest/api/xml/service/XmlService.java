/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.xml.service
 * @file XmlService.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일			버전			수정자		수정내용
 * -------------	--------	------		-------------------------------
 * 2018. 6. 11.		0.0.1		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.xml.service;

import java.io.File;
import java.util.List;

import net.solufine.rest.api.repository.FileVO;
import net.solufine.rest.api.repository.RequestVO;
import net.solufine.rest.api.xml.Connection;
import net.solufine.rest.api.xml.Edge;
import net.solufine.rest.api.xml.Node;
import net.solufine.rest.api.xml.TlLogic;
import net.solufine.rest.api.xml.TlLogics;
import net.solufine.rest.api.xml.TrafficSignal;
import net.solufine.rest.api.xml.Unexpected;
import net.solufine.rest.api.xml.Weather;

/**
 * <p>클래스 설명</p>
 * <p>XmlService</p>
 * <pre>
 *  net.solufine.rest.api.xml.service
 *         └ XmlService.java
 * </pre>
 *
 @author  redkaras 
 @since 0.0.1
 **/

/**
  <p>클래스 설명 : XML관련처리를 위한 인터페이스</p>
  <p>XmlService</p>
  <pre>
   net.solufine.rest.api.xml.service
          └ XmlService.java
  </pre>
  @author  redkaras 
  @since  2018. 6. 11.
  @version 1.0
**/
public interface XmlService {
	/**
	 node 목록을 업데이트 한다.
     @param nodes 업데이트 대상 Node목록
     @return int 적용결과 개수
	*/
	public int updateNode(List<Node> nodes);
	/**
	 Node 목록을 조회 한다.
     @param vo 요청VO
     @return List&lt;Node&gt; Node 목록
	*/
	public List<Node> selectNode(RequestVO vo);
	/**
	 Edge 목록을 조회 한다. 
  	 @param node Node 목록
     @return List&lt;Edge&gt; Edge 목록
	*/
	public List<Edge> selectNodeToEdge(List<Node> node);
	/**
	 Node 목록을 조회 한다.
  	 @param edge Edge 목록
  	 @param selectType 검색유형
  	 @param vo 요청VO
     @return List&lt;Node&gt; Node 목록
	*/
	public List<Node> selectEdgeToNode(List<Edge> edge, int selectType, RequestVO vo);
	/**
	 Node 목록을 조회 한다.
  	 @param id Node ID 목록
  	 @param vo 요청VO
     @return List&lt;Node&gt; Node 목록
	*/
	public List<Node> selectNotInNode(List<String> id, RequestVO vo);
	/**
	 Edge 목록을 업데이트 한다.
  	 @param edges 업데이트 대상 Edge 목록
     @return int 적용결과 개수
	*/
	public int updateEdge(List<Edge> edges);
	/**
	 Edge 목록을 조회 한다.
  	 @param vo 요청VO
     @return List&lt;Edge&gt; Edge 목록
	*/
	public List<Edge> selectEdge(RequestVO vo);
	/**
	 Connection 목록을 업데이트 한다.	
  	 @param cons 업데이트 대상 커넥션 목록
     @return int 적용결과 개수
	*/
	public int updateConnection(List<Connection> cons);
	/**
	 Connection 목록을 조회 한다.
  	 @param vo 요청VO
     @return List&lt;Connection&gt; 커넥션 목록
	*/
	public List<Connection> selectConnection(RequestVO vo);
	/**
	 Connection 목록을 조회 한다.
  	 @param edges Edge 목록
     @return List&lt;Connection&gt; 커넥션 목록
	*/
	public List<Connection> selectConnection2(List<Edge> edges);
	/**
	 Connection 목록을 조회 한다.
  	 @param tls TLogic 목록
     @return List&lt;Connection&gt; 커넥션 목록
	*/
	public List<Connection> selectConnection3(List<TlLogic> tls);
	/**
	 날씨 목록을 조회 한다.
  	 @param vo 요청VO
     @return List&lt;Weather&gt; 날씨목록
	*/	
	public List<Weather> selectWeather(RequestVO vo);
	/**
	 돌발 목록을 조회 한다.
  	 @param vo 요청VO
     @return List&lt;Unexpected&gt; 돌발 목록
	*/	
	public List<Unexpected> selectUnexpected(RequestVO vo);
	/**
	 TlLogics 목록을 업데이트 한다.
  	 @param tls 업데이트 대상 TLogic 목록
     @return int 적용결과 개수
	*/
	public int updatetTlLogic(TlLogics tls);
	/**
	 TlLogic 목록을 조회 한다.
  	 @param vo 요청VO
     @return List&lt;TlLogic&gt; TLogic 목록
	*/
	public List<TlLogic> selectTlLogic(RequestVO vo);
	/**
	 TlLogic 목록을 조회 한다.
     @param edge Edge 목록
     @return List&lt;TlLogic&gt; TLogic 목록
	*/
	public List<TlLogic> selectEdgeToTlLogic(List<Edge> edge);
	/**
	 파일업로드 이력을저장
  	 @param vo 이력저장을 위한 파일VO
	*/
	public void insertUploadHistory(FileVO vo);
	/**
	 XML 요청 이력을저장
  	 @param vo 이력저장을 위한 요청VO
	*/
	public void insertRequestHistory(RequestVO vo);
	/**
	 신호 엑셀파일을 데이터베이스 저장
  	 @param excel 저장 대상 엑셀 파일
     @return boolean 정상처리 여부
	*/
	public boolean ExcelToDatabase(File excel);
	/**
	 XML용 Signal 목록을 조회 한다.
 	 @param id 신호ID 목록
 	 @param vo 요청VO
     @return List&lt;TrafficSignal&gt; XML용 신호목록
	*/
	public List<TrafficSignal> selectSignal(List<String> id, RequestVO vo);
	/**
	 XML용 Signal 목록을 조회 한다.
  	 @param node Node 목록
  	 @param vo 요청VO
     @return List&lt;TrafficSignal&gt; XML용 신호목록
	*/
	public List<TrafficSignal> selectNodeToSignal(List<Node> node, RequestVO vo);	
	/**
	 파티션번호를 조회 한다.
  	 @param vo 요청VO
     @return String; 파티션번호
	*/
	public String GetPartitionNo(RequestVO vo);
	/**
	 궤적 목록을 조회 한다.
 	 @param vo 요청VO
     @return List&lt;VehicleRoute&gt; XML용 궤적목록
	*/
	public List<VehicleRoute> selectRoute(RequestVO vo);	
	/**
	 Node 목록을 조회 한다.
   @param vo 요청VO
   @return List&lt;Node&gt; Node 목록
	*/
	public List<Node> RegionNode(RequestVO vo);
	/**
	  Node 목록을 조회 한다.
	  @param vo 요청VO
	  @return List&lt;Node&gt; Node 목록
	*/
	public List<Node> CoordinateNode(RequestVO vo);
}


