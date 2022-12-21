package net.etri.rest.api.mapper;

import java.util.HashMap;
import java.util.List;

import net.etri.rest.api.xml.BusStop;
import net.etri.rest.api.xml.Connection;
import net.etri.rest.api.xml.Phase;
import net.etri.rest.api.xml.service.SignalDetail;
import net.etri.rest.api.xml.service.SignalPhase;
import net.etri.rest.api.xml.service.SignalScenario;
import net.etri.rest.api.xml.service.SignalTime;
import net.etri.rest.api.cmm.Mapper;
import net.etri.rest.api.repository.RequestVO;
import net.etri.rest.api.xml.Edge;
import net.etri.rest.api.xml.Node;
import net.etri.rest.api.xml.TlLogic;
import net.etri.rest.api.xml.Unexpected;
import net.etri.rest.api.xml.Weather;
import net.etri.rest.api.xml.service.Signal;
import net.etri.rest.api.xml.service.SignalTod;
import net.etri.rest.api.xml.service.SpecialDay;
import net.etri.rest.api.xml.service.VehicleRoute;
/**
  <p>클래스 설명 : XML 데이터 처리를 위한 맵퍼 인터페이스</p>
  <p>XmlMapper</p>
  <pre>
   net.etri.rest.api.mapper
          └ XmlMapper.java
  </pre>
 **/
@Mapper("xmlMapper")
public interface XmlMapper {
	/**
	  Node 목록을 업데이트 한다.
  	  @param node 업데이트 대상 Node목록
      @return int 처리결과 개수
 	  @exception Exception 예외발생
	*/
	public int updateNode(List<Node> node) throws Exception;
	/**
	  Node 목록을 조회 한다.
  	  @param vo 요청VO 객체
      @return List&lt;Node&gt; Node목록
 	  @exception Exception 예외발생
	*/
	public List<Node> selectNode(RequestVO vo) throws Exception;	
	
	
	/**
	  Node 목록을 조회 한다.
  	  @param map Node 검색을 위한 맵
      @return List&lt;Node&gt; Node목록
      @exception Exception 예외발생
	*/
	public List<Node> selectEdgeToNode(HashMap<String, Object> map) throws Exception;
	/**
	 범위밖의 Node 목록을 조회 한다.
	 파티션 및 보더정보 테이블 참조시 다른구에 대한 참조가 문제가 되어 제외
	 fromNode가 강동, toNode가 송파일 경우 제외처리되어 문제가됨. 
	 
	 selectNotInNode
	 SELECT N.ID, N.X, N.Y, N.Z, N.NODETYPE, N.TLTYPE, N.TL, N.RADIUS, N.KEEPCLEAR, N.DONG_CD
			   	   , CONCAT(B.TO_PARTITION_ID) AS PARTITIONOWNER
			 FROM NODE N LEFT JOIN V_BORDER_INFO B ON B.TO_NODE = N.ID 
			 						AND B.PARTITION_NO = '${partitionNo}' 		
			 						AND B.PARTITION_ID = '${partition_id}'
 	 @param map Node 검색을 위한 맵
     @return List&lt;Node&gt; Node목록
     @exception Exception 예외발생
	*/
	public List<Node> selectNotInNode(HashMap<String, Object> map) throws Exception;
	/**
	  Edge 목록을 조회 한다.
  	  @param node 엣지 검색을 위한 Node목록
      @return List&lt;Edge&gt; Edge목록
      @exception Exception 예외발생
    */
	public List<Edge> selectNodeToEdge(List<Node> node) throws Exception;
	/**
	 edge 목록을 업데이트 한다.
     @param edge 업데이트 대상 Edge객체
     @return int 적용결과 개수
 	 @exception Exception 예외발생
	*/
	public int updateRowEdge(Edge edge) throws Exception;
	/**
	  edge 목록을 업데이트 한다.
  	  @param edge 업데이트 대상 Edge객체 목록
      @return int 적용결과 개수
 	  @exception Exception 예외발생
	*/
	public int updateEdge(List<Edge> edge) throws Exception;
	/**
	 Edge 목록을 조회 한다.
  	 @param vo 요청VO
     @return List&lt;Edge&gt; Edge 목록
 	 @exception Exception 예외발생
	*/
	public List<Edge> selectEdge(RequestVO vo) throws Exception;	
	/**
	 Connection 목록을 업데이트 한다.
  	 @param cons 커넥션 목록
     @return int 적용결과 개수
     @exception Exception 예외발생
	*/
	public int updateConnection(List<Connection> cons) throws Exception;
	/**
	 Connection 목록을 조회 한다.
  	 @param vo 요청VO
     @return List&lt;Connection&gt; 커넥션 목록
 	 @exception Exception 예외발생
	*/
	public List<Connection> selectConnection(RequestVO vo) throws Exception;	
	/**
	 Connection 목록을 조회 한다.
  	 @param edges Edge 목록
     @return List&lt;Connection&gt; 커넥션 목록
 	 @exception Exception 예외발생
	*/
	public List<Connection> selectConnection2(List<Edge> edges) throws Exception;
	/**
	 Connection 목록을 조회 한다.
  	 @param tls Tlogic 목록
     @return List&lt;Connection&gt; 커넥션 목록
 	 @exception Exception 예외발생
	*/
	public List<Connection> selectConnection3(List<TlLogic> tls) throws Exception;
	/**
	 Connection 목록을 업데이트 한다.	 
  	 @param cons 커넥션 목록
     @return int 적용결과 개수
     @exception Exception 예외발생
	*/
	public int updateConnection2(List<Connection> cons) throws Exception;
	/**
	 TlLogics 목록을 업데이트 한다.	
  	 @param tls  Tlogic 목록
     @return int 적용결과 개수
     @exception Exception 예외발생
	*/
	public int updateTlLogic(List<TlLogic> tls) throws Exception;
	/**
	 TlLogic 목록을 조회 한다.
  	 @param vo 요청VO
     @return List&lt;TlLogic&gt; Tlogic 목록
 	 @exception Exception 예외발생
	*/
	public List<TlLogic> selectTlLogic(RequestVO vo) throws Exception;	
	/**
	 TlLogic 목록을 조회 한다.
  	 @param edge Edge 목록
     @return List&lt;TlLogic&gt; Tlogic 목록
 	 @exception Exception 예외발생
	*/
	public List<TlLogic> selectEdgeToTlLogic(List<Edge> edge) throws Exception;
	/**
	 Phase 목록을 업데이트 한다.	
  	 @param phases Phase 목록
     @return int 적용결과 개수
     @exception Exception 예외발생
	*/
	public int updatePhase(List<Phase> phases) throws Exception;
	/**
	 Phase 목록을 삭제 한다.
  	 @param phases Phase 목록
     @return int 적용결과 개수
     @exception Exception 예외발생
	*/
	public int deletePhase(List<Phase> phases) throws Exception;
	/**
	 Phase 목록을 조회 한다.
  	 @param tls Tlogic 목록
     @return List&lt;Phase&gt; Phase 목록
 	 @exception Exception 예외발생
	*/
	public List<Phase> selectPhaseList(List<TlLogic> tls) throws Exception;	
	/**
	 날씨 목록을 조회 한다.
  	 @param vo
     @return List&lt;Weather&gt;
 	 @exception Exception 예외발생
	*/	
	public List<Weather> selectWeather(RequestVO vo) throws Exception;
	/**
	 돌발 목록을 조회 한다.
  	 @param vo 요청VO
     @return List&lt;Unexpected&gt; 돌발 목록
 	 @exception Exception 예외발생
	*/	
	public List<Unexpected> selectUnexpected(RequestVO vo) throws Exception;
	/**
	 신호 목록을 업데이트 한다.
 	 @param vo 업데이트 대상 신호
    @return int 적용결과 개수
	 @exception Exception 예외발생
	*/
	public int updateSignalOne(Signal vo) throws Exception;
	/**
	 신호 목록을 업데이트 한다.
  	 @param SignalList 업데이트 대상 신호목록
     @return int 적용결과 개수
 	 @exception Exception 예외발생
	*/
	public int updateSignal(List<Signal> SignalList) throws Exception;
	/**
	 신호별시간 목록을 업데이트 한다.
 	 @param vo 업데이트 대상 신호시간
    @return int 적용결과 개수
	 @exception Exception 예외발생
	*/
	public int updateSignalTimeOne(SignalTime vo) throws Exception;
	/**
	 신호별시간 목록을 업데이트 한다.
  	 @param SignalTimeList 업데이트 대상 신호시간목록
     @return int 적용결과 개수
 	 @exception Exception 예외발생
	*/
	public int updateSignalTime(List<SignalTime> SignalTimeList) throws Exception;
	/**
	 신호별시간 목록을 업데이트 한다.
 	 @param vo 업데이트 대상 TOD플랜
     @return int 적용결과 개수
	 @exception Exception 예외발생
	*/
	public int updateTodPlanOne(SignalTod vo) throws Exception;
	/**
	 신호별시간 목록을 업데이트 한다.
  	 @param TodPlanList 업데이트 대상 TOD플랜 목록
     @return int 적용결과 개수
 	 @exception Exception 예외발생
	*/
	public int updateTodPlan(List<SignalTod> TodPlanList) throws Exception;
	/**
	 신호시나리오 목록을 업데이트 한다.
 	 @param vo 업데이트 대상 신호시나리오
    @return int 적용결과 개수
	 @exception Exception 예외발생
	*/
	public int updateSignalScenarioOne(SignalScenario vo) throws Exception;
	/**
	 신호시나리오 목록을 업데이트 한다.
  	 @param SignalScenarioList 업데이트 대상 신호시나리오 목록
     @return int 적용결과 개수
 	 @exception Exception 예외발생
	*/
	public int updateSignalScenario(List<SignalScenario> SignalScenarioList) throws Exception;
	/**
	 신호시나리오를 삭제 한다.
  	 @param scenario 신호시나리오
     @return int 적용결과 개수
	 @exception Exception  예외발생
	*/
	public int deleteSignalScenario(SignalScenario scenario) throws Exception;
	/**
	 신호별현시 목록을 업데이트 한다.
 	 @param vo 업데이트 대상 신호별현시
    @return int 적용결과 개수
	 @exception Exception 예외발생
	*/
	public int updateSignalPhaseOne(SignalPhase vo) throws Exception;
	/**
	 신호별현시 목록을 업데이트 한다.
  	 @param SignalScenarioPhaseList 업데이트 대상 신호별현시 목록
     @return int 적용결과 개수
 	 @exception Exception 예외발생
	*/
	public int updateSignalPhase(List<SignalPhase> SignalScenarioPhaseList) throws Exception;
	/**
	 신호현시를 삭제 한다.
  	 @param phase 삭세 대상 신호현시
     @return int 적용결과 개수
     @exception Exception 예외발생
	*/
	public int deleteSignalPhase(SignalPhase phase) throws Exception;
	/**
	 신호시나리오별현시 목록을 업데이트 한다.
    @param vo 업데이트 대상 신호시나리오별현시
    @return int 적용결과 개수
    @exception Exception 예외발생
	*/
	public int updateSignalScenarioPhaseOne(SignalPhase vo) throws Exception;
	/**
	 신호시나리오별현시 목록을 업데이트 한다.
     @param SignalScenarioPhaseList 업데이트 대상 신호시나리오별현시
     @return int 적용결과 개수
     @exception Exception 예외발생
	*/
	public int updateSignalScenarioPhase(List<SignalPhase> SignalScenarioPhaseList) throws Exception;
	/**
	 신호시나리오별현시를 삭제 한다.
  	 @param phase 삭제 대상 신호현시
     @return int 적용결과 개수
     @exception Exception 예외발생
	*/
	public int deleteSignalScenarioPhase(SignalPhase phase) throws Exception;
	/**
	 신호특수일 목록을 업데이트 한다.
 	 @param vo 업데이트 대상 특수일
    @return int 적용결과 개수
	 @exception Exception 예외발생
	*/
	public int updateSpecialDayOne(SpecialDay vo) throws Exception;
	/**
	 신호특수일 목록을 업데이트 한다.
  	 @param SpecialDayList 업데이트 대상 특수일 목록
     @return int 적용결과 개수
 	 @exception Exception 예외발생
	*/
	public int updateSpecialDay(List<SpecialDay> SpecialDayList) throws Exception;
	/**
	 신호 목록을 조회 한다.
  	 @param map Node목록을 포함하는 맵
     @return List&lt;Signal&gt; 신호 목록
     @exception Exception 예외발생
	*/
	public List<Signal> selectNodeToSignal(HashMap<String, Object> map) throws Exception;
	/**
	 신호 목록을 조회 한다.
 	 @param map 신호ID목록을 포함하는 맵
     @return List&lt;Signal&gt; 신호 목록
     @exception Exception 예외발생
	*/
	public List<Signal> selectSignal(HashMap<String, Object> map) throws Exception;
	/**
	 신호 목록을 조회 한다.
	 @param vlaue 버전
     @return List&lt;Signal&gt; 신호 목록
     @exception Exception 예외발생
	*/
	public List<Signal> selectVerSignal(String vlaue) throws Exception;	
	/**
	 신호 TOD Plan을 조회 한다.
  	 @param vo 요청VO
     @return List&lt;SignalTod&gt; TOD플랜 목록
 	 @exception Exception 예외발생
	*/
	public List<SignalTod> selectSignalTodPlan(RequestVO vo) throws Exception;
	/**
	 신호상세 목록을 조회 한다.
  	 @param vo 요청VO
     @return List&lt;SignalDetail&gt; 신호상세 목록
 	 @exception Exception 예외발생
	*/
	public List<SignalDetail> selectTrafficSignalDetail(RequestVO vo) throws Exception;
	/**
	 신호상세 목록을 조회 한다. ( 시작시간과 종료시간 무시 )
  	 @param vo 요청VO
     @return List&lt;SignalDetail&gt; 신호상세 목록
 	 @exception Exception 예외발생
	*/
	public List<SignalDetail> selectNoTimeTrafficSignalDetail(RequestVO vo) throws Exception;
	/**
	 신호상세 목록을 조회 한다. ( 기간조회 )
 	 @param vo 요청VO
     @return List&lt;SignalDetail&gt; 신호상세 목록
	 @exception Exception 예외발생
	*/
	public List<SignalDetail> selectTrafficSignalPeriodDetail(RequestVO vo) throws Exception;	
	/**
	 파티션번호를 조회 한다.
  	 @param vo 요청VO
     @return String 파티션번호 
 	 @exception Exception 예외발생
	*/
	public String GetPartitionNo(RequestVO vo) throws Exception;
	/**
	 궤적 목록을 조회 한다.
 	 @param vo 요청VO
     @return List&lt;VehicleRoute&gt;
	 @exception Exception 예외발생
	*/
	public List<VehicleRoute> selectRoute(RequestVO vo) throws Exception;
	/**
	  Node 목록을 조회 한다.
      @param vo 요청VO
      @return List&lt;Node&gt; Node 목록
      @exception Exception 예외발생
	*/
	public List<Node> RegionNode(RequestVO vo) throws Exception; 
	/**
	  Node 목록을 조회 한다.
    @param vo 요청VO
    @return List&lt;Node&gt; Node 목록
    @exception Exception 예외발생
	*/
	public List<Node> CoordinateNode(RequestVO vo) throws Exception; 
	

	////////////////////////////////////////////////////////////////////////////////////////
	//  2차 추가 개편작업
		
	/**
	 phase_signal_time 신호 목록을 조회 한다.
	@param map 신호ID목록을 포함하는 맵
    @return List&lt;SignalTime&gt; 신호 목록
    @exception Exception 예외발생
	*/
	public List<SignalTime> selectSignalTime(HashMap<String, Object> map) throws Exception;
	
	
	// 20211118 장길수 추가 : 버스 정류장
	/**
	 phase_signal_time 신호 목록을 조회 한다.
	@param edge 목록
    @return List<BusStop> 버스 정류소 목록
    @exception Exception 예외발생
	*/
	public List<BusStop> selectBusStopList(List<Edge> edge) throws Exception;
}


