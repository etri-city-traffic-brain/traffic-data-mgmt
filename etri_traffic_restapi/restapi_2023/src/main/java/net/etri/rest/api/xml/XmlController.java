package net.etri.rest.api.xml;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import net.etri.rest.api.repository.RequestVO;
import net.etri.rest.api.xml.service.XmlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.etri.rest.api.AbstractController;
import net.etri.rest.api.type.IncludeType;
import net.etri.rest.api.type.RequestType;
import net.etri.rest.api.type.RouteType;

/**
  <p>클래스 설명 : XML관련 요청에 대한 처리를 위한 컨트롤러 클래스</p>
  <p>XmlController</p>
  <pre>
   net.etri.rest.api.xml
          └ XmlController.java
  </pre>
**/
@RestController("xmlController")
public class XmlController extends AbstractController {

	Logger log = LoggerFactory.getLogger(XmlController.class);
	
	@Value("#{props['upload.store.path']}")
	private String storePath;
	@Value("#{props['download.store.path']}")
	private String downPath;
	
	@Resource(name="xmlManager")
	private XmlManager manager;
	
	@Resource(name="xmlService")
	private XmlService service;
	
	/**
	 Route : 0, 1 : SK 샘플에서 시간대에 맞게 끔
	 Trip Make : Routing 수동
	   파이썬 코드 참고하여 데이터베이스 정보를 변환
	**/
	@RequestMapping(value="/ScenarioByRegion", method=RequestMethod.GET)
	public void ScenarioByRegion(@RequestParam(name="include", defaultValue="0") int include,
										 @RequestParam(name="fromDate") String fromDate,
										 @RequestParam(name="toDate") String toDate,
										 @RequestParam(name="fromTime") String fromTime,
										 @RequestParam(name="toTime") String toTime,
										 @RequestParam(name="region") String region,
										 @RequestParam(name="subregion") String subregion,
										 @RequestParam(name="partitions", defaultValue="1") int partitions,
										 @RequestParam(name="signal", defaultValue="0") String signal,
										 @RequestParam(name="route", defaultValue="0") int route, 
										 @RequestParam(name="event", defaultValue="0" ) String event,	// 돌발
										 @RequestParam(name="weather", defaultValue="0" ) String weather,	
										 @RequestParam(name="busstop", defaultValue="false" ) String busstop,
										 HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {
		// TODO : 시나리오 지역 코드 요청
		//route : 0: not included, 1: skt route, 2: traffic demand		
		if( signal == "1" || signal.equals("1")){signal = "true";}
		if( event == "1" || event.equals("1")){ event = "true"; }
		if( weather == "1" || weather.equals("1")){ weather = "true"; }
		if( busstop == "1" || busstop.equals("1")){ busstop = "true"; }
		
		RequestVO vo = new RequestVO(IncludeType.get(include), fromDate, toDate, fromTime, toTime, region, subregion,
				partitions, Boolean.valueOf(signal), RouteType.get(route), Boolean.valueOf(event), Boolean.valueOf(weather), Boolean.valueOf(busstop), RequestType.SCENARIO_REGION);
				
		/*
		if( vo.getRegion() == "0" && vo.getPartition_cnt() > 1 ){
			alertMessage(response, request.getHeader("Referer"), "파티션 기능은 구를 선택하셔야 합니다.");
			return;
		}
		*/
		if( vo.getRegion() == "0" || vo.getRegion().equals("0") ){
			//vo.setRegion("112");  // 강남4구
			vo.setRegion("250");  // 대전
		}
		
		if( vo.getSubregion() == "0" || vo.getSubregion().equals("0") ){
			vo.setSubregion("");  //모든동
		}
		
		if( vo.getPartition_cnt() > 1){
			responsePartitionXml(vo, request, response);
			return;
		}
		
		Nodes nodes = null;
		Edges edges = null;
		Connections cons = null;
		TrafficSignals signals = null;
		Weathers weathers = null;
		Unexpecteds unexpected = null;	
		BusStops busstops = null;
		
		Scenarios sc = null;
		sc = new Scenarios(); 
		nodes = new Nodes();
		nodes.setNode( service.RegionNode(vo) );		
		if(nodes.getNode() != null && !nodes.getNode().isEmpty()){
			log.info("Node Count : " + nodes.getNode().size());
			sc.setNodes(nodes);
			edges = new Edges();
			edges.setEdge( service.selectNodeToEdge(nodes.getNode()));
			if(edges.getEdge() != null && !edges.getEdge().isEmpty()){
				log.info("Edge Count : " + edges.getEdge().size());
				/*
				  EDGE 목록과  NODE 목록을 비교하여 없는 NODE가 있다면 추가 ( 경계부근 )
				  EDGE 목록에서 TO_NODE가 없는것.
				 */
				List<String> notIn = edges.notInNode(nodes.getNode());
				if(notIn != null && !notIn.isEmpty()){
					log.info("Not In Node Count : " + notIn.size());
					sc.getNodes().appendNode( service.selectNotInNode(notIn, vo), "");
				}
				sc.setEdges(edges);
				cons = new Connections();
				cons.setCon( service.selectConnection2(edges.getEdge()) );
				if(cons.getCon() != null && !cons.getCon().isEmpty()){
					log.info("Connection Count : " + cons.getCon().size());
					sc.setConnections(cons);
					if( vo.isSignal_yn() ){
						signals = new TrafficSignals();
						signals.setTrafficSignal(service.selectNodeToSignal(nodes.getNode(), vo));
						if( signals.getTrafficSignal() != null)
							log.info("Signal Count : " + signals.getTrafficSignal().size());
						sc.setTrafficSignalSystem(signals);
					}
				}						
			}
		}

		if( vo.isWeather_yn() ){
			weathers = new Weathers();
			weathers.setWeather(service.selectWeather(vo));
			sc.setWeathers( weathers );
		}
		
		if( vo.isEvent_yn() ){
			unexpected = new Unexpecteds();
			unexpected.setUnexpected( service.selectUnexpected(vo) );
			sc.setEvents(unexpected);
		}
		
		// 20211118 장길수 추가 : 버스 정류소 목록
		if (vo.isBusstop_yn()) {
			busstops = new BusStops();
			busstops.setBusstop(service.selectBusStopList(edges.getEdge()));
			sc.setBusStops(busstops);
		}
		
		CheckDirectory(downPath);
		manager.ScenearioXmlResponse(sc, response, downPath);			
    }
	
	
	/**
	 Route : 0, 1 : SK 샘플에서 시간대에 맞게 끔
	 Trip Make : Routing 수동
	   파이썬 코드 참고하여 데이터베이스 정보를 변환
	**/
	@RequestMapping(value="/ScenarioByRegion2", method=RequestMethod.GET)
	public void ScenarioByRegion2(@RequestParam(name="include", defaultValue="0") int include,
										 @RequestParam(name="fromDate") String fromDate,
										 @RequestParam(name="toDate") String toDate,
										 @RequestParam(name="fromTime") String fromTime,
										 @RequestParam(name="toTime") String toTime,
										 @RequestParam(name="region") String region,
										 @RequestParam(name="subregion") String subregion,
										 @RequestParam(name="partitions", defaultValue="1") int partitions,
										 @RequestParam(name="signal", defaultValue="0") String signal,
										 @RequestParam(name="route", defaultValue="0") int route, 
										 @RequestParam(name="event", defaultValue="0" ) String event,	// 돌발
										 @RequestParam(name="weather", defaultValue="0" ) String weather,
										 @RequestParam(name="busstop", defaultValue="false" ) String busstop,
										 HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {
		// TODO : 시나리오 지역 코드 요청
		//route : 0: not included, 1: skt route, 2: traffic demand		
		if( signal == "1" || signal.equals("1")){signal = "true";}
		if( event == "1" || event.equals("1")){ event = "true"; }
		if( weather == "1" || weather.equals("1")){ weather = "true"; }
		if( busstop == "1" || busstop.equals("1")){ busstop = "true"; }
		
		RequestVO vo = new RequestVO(IncludeType.get(include), fromDate, toDate, fromTime, toTime, region, subregion, 
				partitions, Boolean.valueOf(signal), RouteType.get(route), Boolean.valueOf(event), Boolean.valueOf(weather), Boolean.valueOf(busstop), RequestType.SCENARIO_REGION);
				
		/*
		if( vo.getRegion() == "0" && vo.getPartition_cnt() > 1 ){
			alertMessage(response, request.getHeader("Referer"), "파티션 기능은 구를 선택하셔야 합니다.");
			return;
		}
		*/
		if( vo.getRegion() == "0" || vo.getRegion().equals("0") ){
			//vo.setRegion("112");  // 강남4구
			vo.setRegion("290");  // 대전
		}
		
		if( vo.getSubregion() == "0" || vo.getSubregion().equals("0") ){
			vo.setSubregion("");  //모든동
		}
		
		if( vo.getPartition_cnt() > 1){
			responsePartitionXml(vo, request, response);
			return;
		}
		
		Nodes nodes = null;
		Edges edges = null;
		Connections cons = null;
		TrafficSignals signals = null;
		Weathers weathers = null;
		Unexpecteds unexpected = null;	
		BusStops busstops = null;
		
		Scenarios sc = null;
		sc = new Scenarios(); 
		nodes = new Nodes();
		nodes.setNode( service.RegionNode(vo) );		
		if(nodes.getNode() != null && !nodes.getNode().isEmpty()){
			log.info("Node Count : " + nodes.getNode().size());
			sc.setNodes(nodes);
			edges = new Edges();
			edges.setEdge( service.selectNodeToEdge(nodes.getNode()));
			if(edges.getEdge() != null && !edges.getEdge().isEmpty()){
				log.info("Edge Count : " + edges.getEdge().size());
				/*
				  EDGE 목록과  NODE 목록을 비교하여 없는 NODE가 있다면 추가 ( 경계부근 )
				  EDGE 목록에서 TO_NODE가 없는것.
				 */
				List<String> notIn = edges.notInNode(nodes.getNode());
				if(notIn != null && !notIn.isEmpty()){
					log.info("Not In Node Count : " + notIn.size());
					sc.getNodes().appendNode( service.selectNotInNode(notIn, vo), "");
				}
				sc.setEdges(edges);
				cons = new Connections();
				cons.setCon( service.selectConnection2(edges.getEdge()) );
				if(cons.getCon() != null && !cons.getCon().isEmpty()){
					log.info("Connection Count : " + cons.getCon().size());
					sc.setConnections(cons);
					if( vo.isSignal_yn() ){
						signals = new TrafficSignals();
						signals.setTrafficSignal(service.selectNodeToSignal(nodes.getNode(), vo));
						if( signals.getTrafficSignal() != null)
							log.info("Signal Count : " + signals.getTrafficSignal().size());
						sc.setTrafficSignalSystem(signals);
					}
				}						
			}
		}

		if( vo.isWeather_yn() ){
			weathers = new Weathers();
			weathers.setWeather(service.selectWeather(vo));
			sc.setWeathers( weathers );
		}
		
		if( vo.isEvent_yn() ){
			unexpected = new Unexpecteds();
			unexpected.setUnexpected( service.selectUnexpected(vo) );
			sc.setEvents(unexpected);
		}
		
		// 20211118 장길수 추가 : 버스 정류소 목록
		if (vo.isBusstop_yn()) {
			busstops = new BusStops();
			busstops.setBusstop(service.selectBusStopList(edges.getEdge()));
			sc.setBusStops(busstops);
		}
		
		CheckDirectory(downPath);
		manager.ScenearioXmlResponse(sc, response, downPath);			
   }
	
	/**
	 20211115 장길수 : 버스정류소 추가	 
	**/
	@RequestMapping(value="/ScenarioByCoordinate", method=RequestMethod.GET)
	public void ScenarioByCoordinate(@RequestParam(name="include", defaultValue="0") int include,
											 @RequestParam(name="fromDate") String fromDate,
											 @RequestParam(name="toDate") String toDate,
											 @RequestParam(name="fromTime") String fromTime,
											 @RequestParam(name="toTime") String toTime,
											 @RequestParam(name="minX") float minX,
											 @RequestParam(name="minY") float minY,
											 @RequestParam(name="maxX") float maxX,
											 @RequestParam(name="maxY") float maxY,
											 @RequestParam(name="partitions", defaultValue="1") int partitions,
											 @RequestParam(name="signal", defaultValue="0") String signal,
											 @RequestParam(name="route", defaultValue="0") int route, 
											 @RequestParam(name="event", defaultValue="false") String event,	// 돌발
											 @RequestParam(name="weather", defaultValue="false" ) String weather,		
											 @RequestParam(name="busstop", defaultValue="false" ) String busstop,
									 		 @RequestParam(name="bydong", defaultValue="0" ) int bydong,
											 HttpServletRequest request,HttpServletResponse response) throws JAXBException, IOException {		
		// TODO : 시나리오 좌표 요청
		//route : 0: not included, 1: skt route, 2: traffic demand
		if( signal == "1" || signal.equals("1")){signal = "true";}
		if( event == "1" || event.equals("1")){ event = "true"; }
		if( weather == "1" || weather.equals("1")){ weather = "true"; }
		if( busstop == "1" || busstop.equals("1")){ busstop = "true"; }
		
		RequestVO vo = new RequestVO(IncludeType.get(include),fromDate, toDate, fromTime, toTime, minX, minY, maxX, maxY, 
				partitions, Boolean.valueOf(signal), RouteType.get(route), Boolean.valueOf(event), Boolean.valueOf(weather), Boolean.valueOf(busstop), RequestType.SCENARIO_COORDINATE);
			
		if( vo.getPartition_cnt() > 1){
			responsePartitionXml(vo, request, response);
			return;
		}
		
		Nodes nodes = null;
		Edges edges = null;
		Connections cons = null;
		TrafficSignals signals = null;
		Weathers weathers = null;
		Unexpecteds unexpected = null;
		BusStops busstops = null;
		
		Scenarios sc = null;
		sc = new Scenarios(); 
		nodes = new Nodes();

		if( bydong == 0){
			nodes.setNode( service.CoordinateNode(vo) );
		}
		else{
			nodes.setNode( service.CoordinateNodeByDong(vo) );
		}

		if(nodes.getNode() != null && !nodes.getNode().isEmpty()){
			sc.setNodes(nodes);
			edges = new Edges();
			edges.setEdge( service.selectNodeToEdge(nodes.getNode()));
			if(edges.getEdge() != null && !edges.getEdge().isEmpty()){
				/*
				  EDGE 목록과  NODE 목록을 비교하여 없는 NODE가 있다면 추가 ( 경계부근 )
				  EDGE 목록에서 TO_NODE가 없는것.
				 */
				List<String> notIn = edges.notInNode(nodes.getNode());
				if(notIn != null && !notIn.isEmpty()){
					sc.getNodes().appendNode( service.selectNotInNode(notIn, vo), "");
				}
				sc.setEdges(edges);
				cons = new Connections();
				cons.setCon( service.selectConnection2(edges.getEdge()) );
				if(cons.getCon() != null && !cons.getCon().isEmpty()){
					sc.setConnections(cons);
					if( vo.isSignal_yn() ){
						signals = new TrafficSignals();
						signals.setTrafficSignal(service.selectNodeToSignal(nodes.getNode(), vo));
						sc.setTrafficSignalSystem(signals);
					}					
				}						
			}
		}

		if( vo.isWeather_yn() ){
			weathers = new Weathers();
			weathers.setWeather(service.selectWeather(vo));
			sc.setWeathers( weathers );
		}
		
		if( vo.isEvent_yn() ){
			unexpected = new Unexpecteds();
			unexpected.setUnexpected( service.selectUnexpected(vo) );
			sc.setEvents(unexpected);
		}
		
		// 20211118 장길수 추가 : 버스 정류소 목록
		if (vo.isBusstop_yn()) {
			busstops = new BusStops();
			busstops.setBusstop(service.selectBusStopList(edges.getEdge()));
			sc.setBusStops(busstops);
		}
		
		CheckDirectory(downPath);
		manager.ScenearioXmlResponse(sc, response, downPath);		
    }
	/**
	 테이블 단위로 파티션 정보를 컬럼으로 가지고 있고 이를 통해 반환
	 파티션 정보에 대한 분할 필요.(속성)
	 20211118 장길수 : 버스 정류장 추가
	 * **/
	@RequestMapping(value="/MapByRegion", method=RequestMethod.GET)
	public void MapByRegion(  @RequestParam(name="include", defaultValue="1") int include,
							  @RequestParam(name="region") String region,
							  @RequestParam(name="subregion") String subregion,
							  @RequestParam(name="partitions", required=false, defaultValue="1") int partitions,
							  HttpServletRequest request,HttpServletResponse response) throws JAXBException, IOException {
		// TODO : 지도 지역코드 요청
		
		RequestVO vo = new RequestVO(IncludeType.get(include), region, subregion, partitions, RequestType.MAP_REGION);
		/*
		if( vo.getRegion() == "0" && vo.getPartition_cnt() > 1 ){
			alertMessage(response, request.getHeader("Referer"), "파티션 기능은 구를 선택하셔야 합니다.");
			return;
		}
		*/
		if( vo.getRegion() == "0" || vo.getRegion().equals("0") ){
			vo.setRegion("250");  // 대전광역시
		}
		
		if( vo.getSubregion() == "0" || vo.getSubregion().equals("0") ){
			vo.setSubregion("");  //모든동
		}
		
		if( vo.getPartition_cnt() > 1){
			responsePartitionXml(vo, request, response);
			return;
		}
		
		Nodes nodes = null;
		Edges edges = null;
		Connections cons = null;
		Scenarios sc = null;
		BusStops busstops = null;
		
		sc = new Scenarios(); 
		nodes = new Nodes();
		nodes.setNode( service.RegionNode(vo) );
		
		if(nodes.getNode() != null && !nodes.getNode().isEmpty()){
			sc.setNodes(nodes);
			edges = new Edges();
			edges.setEdge( service.selectNodeToEdge(nodes.getNode()));
			if(edges.getEdge() != null && !edges.getEdge().isEmpty()){
				/*
				  EDGE 목록과  NODE 목록을 비교하여 없는 NODE가 있다면 추가 ( 경계부근 )
				  EDGE 목록에서 TO_NODE가 없는것.
				 */
				List<String> notIn = edges.notInNode(nodes.getNode());
				if(notIn != null && !notIn.isEmpty()){
					sc.getNodes().appendNode( service.selectNotInNode(notIn, vo), "");
				}
				sc.setEdges(edges);
				cons = new Connections();
				cons.setCon( service.selectConnection2(edges.getEdge()) );	
				if(cons.getCon() != null && !cons.getCon().isEmpty()){
					sc.setConnections(cons);
				}
				
				busstops = new BusStops();					
				busstops.setBusstop(service.selectBusStopList(edges.getEdge()));
				if(busstops.getBusstop() != null && !busstops.getBusstop().isEmpty()) {
					sc.setBusStops(busstops);				
				}
			}
		}
		
		CheckDirectory(downPath);
		manager.ResponseMapZip(sc, response, downPath);	
    }
	
	/**
	 테이블 단위로 파티션 정보를 컬럼으로 가지고 있고 이를 통해 반환
	 파티션 정보에 대한 분할 필요.(속성)
	 20211118 장길수 : 버스 정류장 추가
	 * **/
	@RequestMapping(value="/MapByRegion2", method=RequestMethod.GET)
	public void MapByRegion2(  @RequestParam(name="include", defaultValue="1") int include,
							  @RequestParam(name="region") String region,
							  @RequestParam(name="subregion") String subregion,
							  @RequestParam(name="partitions", required=false, defaultValue="1") int partitions,
							  HttpServletRequest request,HttpServletResponse response) throws JAXBException, IOException {
		// TODO : 지도 지역코드 요청
		
		RequestVO vo = new RequestVO(IncludeType.get(include), region, subregion, partitions, RequestType.MAP_REGION);
		/*
		if( vo.getRegion() == "0" && vo.getPartition_cnt() > 1 ){
			alertMessage(response, request.getHeader("Referer"), "파티션 기능은 구를 선택하셔야 합니다.");
			return;
		}
		*/
		if( vo.getRegion() == "0" || vo.getRegion().equals("0") ){
			vo.setRegion("290");  // 세종특별자치시
		}
		
		if( vo.getSubregion() == "0" || vo.getSubregion().equals("0") ){
			vo.setSubregion("");  //모든동
		}
		
		if( vo.getPartition_cnt() > 1){
			responsePartitionXml(vo, request, response);
			return;
		}
		
		Nodes nodes = null;
		Edges edges = null;
		Connections cons = null;
		Scenarios sc = null;
		BusStops busstops = null;
		
		sc = new Scenarios(); 
		nodes = new Nodes();
		nodes.setNode( service.RegionNode(vo) );
		
		if(nodes.getNode() != null && !nodes.getNode().isEmpty()){
			sc.setNodes(nodes);
			edges = new Edges();
			edges.setEdge( service.selectNodeToEdge(nodes.getNode()));
			if(edges.getEdge() != null && !edges.getEdge().isEmpty()){
				/*
				  EDGE 목록과  NODE 목록을 비교하여 없는 NODE가 있다면 추가 ( 경계부근 )
				  EDGE 목록에서 TO_NODE가 없는것.
				 */
				List<String> notIn = edges.notInNode(nodes.getNode());
				if(notIn != null && !notIn.isEmpty()){
					sc.getNodes().appendNode( service.selectNotInNode(notIn, vo), "");
				}
				sc.setEdges(edges);
				cons = new Connections();
				cons.setCon( service.selectConnection2(edges.getEdge()) );	
				if(cons.getCon() != null && !cons.getCon().isEmpty()){
					sc.setConnections(cons);
				}
				
				busstops = new BusStops();					
				busstops.setBusstop(service.selectBusStopList(edges.getEdge()));
				if(busstops.getBusstop() != null && !busstops.getBusstop().isEmpty()) {
					sc.setBusStops(busstops);				
				}
			}
		}
		
		CheckDirectory(downPath);
		manager.ResponseMapZip(sc, response, downPath);	
   }
	
	
	// 20211118 장길수 : 버스 정류장 추가
	@RequestMapping(value="/MapByCoordinate", method=RequestMethod.GET)
	public void MapByCoordinate(  @RequestParam(name="include", defaultValue="1") int include,
								  @RequestParam(name="minX") float minX,
								  @RequestParam(name="minY") float minY,
								  @RequestParam(name="maxX") float maxX,
								  @RequestParam(name="maxY") float maxY,
								  @RequestParam(name="partitions", required=false, defaultValue="1") int partitions,
								  HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {
		// TODO : 지도 좌표 요청
			
		RequestVO vo = new RequestVO(IncludeType.get(include), minX, minY, maxX, maxY, partitions, RequestType.MAP_COORDINATE);
		
		if( vo.getPartition_cnt() > 1){
			responsePartitionXml(vo, request, response);
			return;
		}
		
		Nodes nodes = null;
		Edges edges = null;
		Connections cons = null;
		Scenarios sc = null;
		BusStops busstops = null;
		
		sc = new Scenarios(); 
		nodes = new Nodes();
		nodes.setNode( service.CoordinateNode(vo) );	
		
		if(nodes.getNode() != null && !nodes.getNode().isEmpty()){
			sc.setNodes(nodes);
			edges = new Edges();
			edges.setEdge( service.selectNodeToEdge(nodes.getNode()));
			if(edges.getEdge() != null && !edges.getEdge().isEmpty()){
				/*
				  EDGE 목록과  NODE 목록을 비교하여 없는 NODE가 있다면 추가 ( 경계부근 )
				  EDGE 목록에서 TO_NODE가 없는것.
				 */
				List<String> notIn = edges.notInNode(nodes.getNode());
				if(notIn != null && !notIn.isEmpty()){
					sc.getNodes().appendNode( service.selectNotInNode(notIn, vo), "");
				}
				sc.setEdges(edges);
				cons = new Connections();
				cons.setCon( service.selectConnection2(edges.getEdge()) );	
				if(cons.getCon() != null && !cons.getCon().isEmpty()){
					sc.setConnections(cons);
				}
				
				busstops = new BusStops();					
				busstops.setBusstop(service.selectBusStopList(edges.getEdge()));
				if(busstops.getBusstop() != null && !busstops.getBusstop().isEmpty()) {
					sc.setBusStops(busstops);				
				}
			}
		}
		
		CheckDirectory(downPath);
		manager.ResponseMapZip(sc, response, downPath);	
    }
	
	@RequestMapping(value="/SignalByRegion", method=RequestMethod.GET)
	public void SignalByRegion(  @RequestParam(name="include", defaultValue="2") int include,
								 @RequestParam(name="fromDate") String fromDate,
								 @RequestParam(name="toDate") String toDate,
								 @RequestParam(name="fromTime") String fromTime,
								 @RequestParam(name="toTime") String toTime,
								 @RequestParam(name="region") String region,
								 @RequestParam(name="subregion", defaultValue="0") String subregion,
								 @RequestParam(name="partitions", defaultValue="1") int partitions,
								 HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {
		
		RequestVO vo = new RequestVO(IncludeType.get(include), fromDate, toDate, fromTime, toTime, region, subregion, partitions , RequestType.SIGNAL_REGION);
		/*
		if( vo.getRegion() == "0" && vo.getPartition_cnt() > 1 ){
			alertMessage(response, request.getHeader("Referer"), "파티션 기능은 구를 선택하셔야 합니다.");
			return;
		}
		*/
		if( vo.getRegion() == "0" || vo.getRegion().equals("0") ){
			vo.setRegion("250");  // 대전광역시
		}
		
		if( vo.getSubregion() == "0" || vo.getSubregion().equals("0") ){
			vo.setSubregion("");  //모든동
		}
		
		if( vo.getPartition_cnt() > 1){
			responsePartitionXml(vo, request, response);
			return;
		}
		
		Nodes nodes = null;
		TrafficSignals signals = null;
		
		nodes = new Nodes();
		nodes.setNode( service.RegionNode(vo) );
		if(nodes.getNode() != null && !nodes.getNode().isEmpty()){
			signals = new TrafficSignals();
			signals.setTrafficSignal(service.selectNodeToSignal(nodes.getNode(), vo));
		}
		
		manager.TrafficSignalXmlResponse(signals, response);		
	}
	
	@RequestMapping(value="/SignalByRegion2", method=RequestMethod.GET)
	public void SignalByRegion2(  @RequestParam(name="include", defaultValue="2") int include,
								 @RequestParam(name="fromDate") String fromDate,
								 @RequestParam(name="toDate") String toDate,
								 @RequestParam(name="fromTime") String fromTime,
								 @RequestParam(name="toTime") String toTime,
								 @RequestParam(name="region") String region,
								 @RequestParam(name="subregion", defaultValue="0") String subregion,
								 @RequestParam(name="partitions", defaultValue="1") int partitions,
								 HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {
		
		RequestVO vo = new RequestVO(IncludeType.get(include), fromDate, toDate, fromTime, toTime, region, subregion, partitions , RequestType.SIGNAL_REGION);
		/*
		if( vo.getRegion() == "0" && vo.getPartition_cnt() > 1 ){
			alertMessage(response, request.getHeader("Referer"), "파티션 기능은 구를 선택하셔야 합니다.");
			return;
		}
		*/
		if( vo.getRegion() == "0" || vo.getRegion().equals("0") ){
			vo.setRegion("290");  // 세종특별자치시
		}
		
		if( vo.getSubregion() == "0" || vo.getSubregion().equals("0") ){
			vo.setSubregion("");  //모든동
		}
		
		if( vo.getPartition_cnt() > 1){
			responsePartitionXml(vo, request, response);
			return;
		}
		
		Nodes nodes = null;
		TrafficSignals signals = null;
		
		nodes = new Nodes();
		nodes.setNode( service.RegionNode(vo) );
		if(nodes.getNode() != null && !nodes.getNode().isEmpty()){
			signals = new TrafficSignals();
			signals.setTrafficSignal(service.selectNodeToSignal(nodes.getNode(), vo));
		}
		
		manager.TrafficSignalXmlResponse(signals, response);		
	}
	
	@RequestMapping(value="/SignalByCoordinate", method=RequestMethod.GET)
	public void SignalByCoordinate(  @RequestParam(name="include", defaultValue="2") int include,
									 @RequestParam(name="fromDate") String fromDate,
									 @RequestParam(name="toDate") String toDate,
									 @RequestParam(name="fromTime") String fromTime,
									 @RequestParam(name="toTime") String toTime,
									 @RequestParam(name="minX") float minX,
									 @RequestParam(name="minY") float minY,
									 @RequestParam(name="maxX") float maxX,
									 @RequestParam(name="maxY") float maxY,
									 @RequestParam(name="partitions", defaultValue="1") int partitions,
									 HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {
		// TODO : 신호 버전별 요청
		RequestVO vo = new RequestVO(IncludeType.get(include), fromDate, toDate, fromTime, toTime, minX, minY, maxX, maxY, partitions, RequestType.SIGNAL_COORDINATE);
		
		if( vo.getPartition_cnt() > 1){
			responsePartitionXml(vo, request, response);
			return;
		}
		
		Nodes nodes = null;
		TrafficSignals signals = null;
		
		nodes = new Nodes();
		nodes.setNode( service.CoordinateNode(vo) );
		if(nodes.getNode() != null && !nodes.getNode().isEmpty()){
			signals = new TrafficSignals();
			signals.setTrafficSignal(service.selectNodeToSignal(nodes.getNode(), vo));
		}
		
		manager.TrafficSignalXmlResponse(signals, response);		
	}
	
	private void responsePartitionXml(RequestVO vo, HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException{
		/*
		if( vo.getRegion() == "112" || vo.getRegion().equals("112") ){
			//강남4구 파티션
			responsePartitionXml2(vo, request, response);
			return;
		}
		*/
		PartitionScenarios partitions = null;
		
		if( vo.getPartitionNo() == 0){
			//vo.setPartitionNo( Integer.valueOf(service.GetPartitionNo(vo)) );
			//2019.08.23 Redkaras 구별 지정 번호로 변경
			if( vo.getRegion() == "112" || vo.getRegion().equals("112") ){
				if( vo.getPartition_cnt() == 4) {
					vo.setPartitionNo(10);
				}else if( vo.getPartition_cnt() == 8) {
					vo.setPartitionNo(11);
				}else if( vo.getPartition_cnt() == 16) {
					vo.setPartitionNo(12);
				}
			}else if( vo.getRegion() == "11250" || vo.getRegion().equals("11250") ){
				if( vo.getPartition_cnt() == 2) {
					vo.setPartitionNo(1);
				}else {
					vo.setPartitionNo(2);
				}
			}else if( vo.getRegion() == "11240" || vo.getRegion().equals("11240") ){
				if( vo.getPartition_cnt() == 2) {
					vo.setPartitionNo(3);
				}else {
					vo.setPartitionNo(4);
				}
			}else if( vo.getRegion() == "11230" || vo.getRegion().equals("11230") ){
				if( vo.getPartition_cnt() == 2) {
					vo.setPartitionNo(5);
				}else {
					vo.setPartitionNo(6);
				}
			}else if( vo.getRegion() == "11220" || vo.getRegion().equals("11220") ){
				if( vo.getPartition_cnt() == 2) {
					vo.setPartitionNo(7);
				}else {
					vo.setPartitionNo(8);
				}
			}
		}
		
		partitions = new PartitionScenarios();
		partitions.setPartitionNo( String.valueOf(vo.getPartitionNo()) );
		/*
		for(int i = 0; i < vo.getPartition_cnt(); i++){
			vo.setPartition_id(i + 1);
			Partition partition = new Partition();
			partition.setId( String.valueOf( vo.getPartition_id()  ) );			
			log.info("Partition No : " + partitions.getPartitionNo() + " , Partition Id : " + partition.getId());			
			Nodes part_nodes = new Nodes();
			part_nodes.setNode( service.selectNode(vo) );
			if(part_nodes.getNode() != null && !part_nodes.getNode().isEmpty()){
				log.info("Node Count : " + part_nodes.getNode().size());
				partition.setNodes(part_nodes);
				Edges part_edges = new Edges();
				part_edges.setEdge( service.selectNodeToEdge(part_nodes.getNode()));
				if(part_edges.getEdge() != null && !part_edges.getEdge().isEmpty()){
					log.info("Edge Count : " + part_edges.getEdge().size());
					partition.setEdges(part_edges);
					  //EDGE 목록과  NODE 목록을 비교하여 없는 NODE가 있다면 추가 ( 경계부근 )
					  //EDGE 목록에서 TO_NODE가 없는것.
					List<String> notIn = part_edges.notInNode(part_nodes.getNode());
					if(notIn != null && !notIn.isEmpty()){
						log.info("Not In Node Count : " + notIn.size());
						List<Node> notInNodes = service.selectNotInNode(notIn, vo);
						if( notInNodes != null && !notInNodes.isEmpty()) {
							//partition.getNodes().appendNode( service.selectNotnNode(notIn, vo), partition.getId() );
							Iterator<Node> it = notInNodes.iterator();
							while(it.hasNext()) {
								Node n = it.next(); 
								if( n.getPartitionOwner() == null || n.getPartitionOwner() == "" || n.getPartitionOwner().equals("")) {
									log.info("Not In Node Partition Owner not exist : " + "Partition No : " + partitions.getPartitionNo() + " , Partition Id : " + partition.getId()
										+ " , Node Id : " + n.getId() + " , Dong Cd : " + n.getDong_cd());
								}
							}
							partition.getNodes().appendNode( notInNodes, partition.getId() );
						}
					}
					Connections part_cons = new Connections();
					part_cons.setCon( service.selectConnection2(part_edges.getEdge()) );
					if(part_cons.getCon() != null && !part_cons.getCon().isEmpty()){
						log.info("Connection Count : " + part_cons.getCon().size());
						partition.setConnections(part_cons);
						if( vo.isSignal_yn() ){
							TrafficSignals part_signals = new TrafficSignals();
							part_signals.setTrafficSignal(service.selectNodeToSignal(part_nodes.getNode(), vo));
							if( part_signals.getTrafficSignal() != null)
								log.info("Signal Count : " + part_signals.getTrafficSignal().size());
							partition.setTrafficSignalSystem(part_signals);
						}
					}							
				}
			}
			partitions.setPartition(partition);
		}
		*/
		 /**
		  Not in node Partition Owner 산출을 위하여  분리 처리
		  **/
		
		for(int i = 0; i < vo.getPartition_cnt(); i++){
			vo.setPartition_id(i + 1);
			Partition partition = new Partition();
			partition.setId( String.valueOf( vo.getPartition_id()  ) );			
			log.info("Partition No : " + partitions.getPartitionNo() + " , Partition Id : " + partition.getId());			
			Nodes part_nodes = new Nodes();
			part_nodes.setNode( service.selectNode(vo) );
			if(part_nodes.getNode() != null && !part_nodes.getNode().isEmpty()){
				log.info("Node Count : " + part_nodes.getNode().size());
				partition.setNodes(part_nodes);
				Edges part_edges = new Edges();
				part_edges.setEdge( service.selectNodeToEdge(part_nodes.getNode()));
				if(part_edges.getEdge() != null && !part_edges.getEdge().isEmpty()){
					log.info("Edge Count : " + part_edges.getEdge().size());
					partition.setEdges(part_edges);					
					List<String> notIn = part_edges.notInNode(part_nodes.getNode());
					if(notIn != null && !notIn.isEmpty()){
						log.info("Not In Node Count : " + notIn.size());
						partition.setNotInNodes(service.selectNotInNode(notIn, vo));

					}
					Connections part_cons = new Connections();
					part_cons.setCon( service.selectConnection2(part_edges.getEdge()) );
					if(part_cons.getCon() != null && !part_cons.getCon().isEmpty()){
						log.info("Connection Count : " + part_cons.getCon().size());
						partition.setConnections(part_cons);
						/*
						if( vo.isSignal_yn() ){
							TrafficSignals part_signals = new TrafficSignals();
							part_signals.setTrafficSignal(service.selectNodeToSignal(partition.getNodes().getNode(), vo));
							if( part_signals.getTrafficSignal() != null)
								log.info("Signal Count : " + part_signals.getTrafficSignal().size());
							partition.setTrafficSignalSystem(part_signals);
						}
						*/
					}							
				}
			}
			partitions.setPartition(partition);
		}

		/**
		 2019.09.23 redkaras  Not In Node의 Partition Owner를  구한다.
		 		 			    대상범위 밖의 Node도 포함하도록 추가	
		 */
		for(int i = 0; i < partitions.getPartition().size(); i++){
			Partition partition = partitions.getPartition().get(i);
			
			if( partition.getNotInNodes() == null && !partition.getNotInNodes().isEmpty())
				continue;
			
			for( int c = 0; c < partitions.getPartition().size(); c++){
				if( i == c) 
					continue;
				
				Partition ownerPartition = partitions.getPartition().get(c);
				for(int j = partition.getNotInNodes().size() - 1; j > -1; j--) {
					if(  partition.getNotInNodes().get(j).getPartitionOwner() == null || 
							partition.getNotInNodes().get(j).getPartitionOwner() == "" || 
									partition.getNotInNodes().get(j).getPartitionOwner().equals("")) {
						if( ownerPartition.getEdges().existToNode(partition.getNotInNodes().get(j)) ) {
							partition.getNotInNodes().get(j).setPartitionOwner(ownerPartition.getId());
							partition.getNodes().appendNode(partition.getNotInNodes().get(j), partition.getId());
							partition.getNotInNodes().remove(j);
						}else {
							// Partition Owner가 없더라도 Edge의 toNode 문제가 없도록 추가시켜 준다.
							partition.getNodes().appendNode(partition.getNotInNodes().get(j), partition.getId());
							partition.getNotInNodes().remove(j);
						}												
					}else {
						partition.getNodes().appendNode(partition.getNotInNodes().get(j), partition.getId());
						partition.getNotInNodes().remove(j);
					}
				}
			}
		}
		
		for(int i = 0; i < partitions.getPartition().size(); i++){
			Partition partition = partitions.getPartition().get(i);
			if( partition.getNotInNodes() != null && !partition.getNotInNodes().isEmpty()) {
				Iterator<Node> it = partition.getNotInNodes().iterator();
				while(it.hasNext()) {
					Node n = it.next();
					if( n.getPartitionOwner() == null || n.getPartitionOwner() == "" || n.getPartitionOwner().equals("")) {
						if( n.getDong_cd().contains(vo.getRegion()) )
						log.info("Not In Node Partition Owner not exist : " + "Partition No : " + partitions.getPartitionNo() + " , Partition Id : " + partition.getId()
							+ " , Node Id : " + n.getId() + " , Dong Cd : " + n.getDong_cd());
					}
				}
			}
			/*
			if(partition.getConnections().getCon() != null && !partition.getConnections().getCon().isEmpty()){
				if( vo.isSignal_yn() ){
					TrafficSignals part_signals = new TrafficSignals();
					part_signals.setTrafficSignal(service.selectNodeToSignal(partition.getNodes().getNode(), vo));
					if( part_signals.getTrafficSignal() != null)
						log.info("Signal Count : " + part_signals.getTrafficSignal().size());
					partition.setTrafficSignalSystem(part_signals);
				}
			}
			*/
		}
		
		/**
		 2019.09.23 redkaras Node를 기준으로 Signal을 구하기 때문에 Node관련 처리가 끝난후 신호정보를 구한다.		 
		 */
		for(int i = 0; i < partitions.getPartition().size(); i++){
			Partition partition = partitions.getPartition().get(i);
			if(partition.getConnections().getCon() != null && !partition.getConnections().getCon().isEmpty()){
				if( vo.isSignal_yn() ){
					log.info("Add Not in Node Node Count : " + partition.getNodes().getNode().size());
					TrafficSignals part_signals = new TrafficSignals();
					part_signals.setTrafficSignal(service.selectNodeToSignal(partition.getNodes().getNode(), vo));
					if( part_signals.getTrafficSignal() != null)
						log.info("Signal Count : " + part_signals.getTrafficSignal().size());
					partition.setTrafficSignalSystem(part_signals);
				}
			}
		}

		/**
		 2019.09.23 redkaras Edge의 from, to Node가 Node 정보에 모두 포함되어 있는지 한번더 체크 한다.		 		 
		 */
		for(int i = 0; i < partitions.getPartition().size(); i++){
			Partition partition = partitions.getPartition().get(i);
			if(partition.getEdges().getEdge() != null && !partition.getEdges().getEdge().isEmpty()){
				for(int c = 0; c < partition.getEdges().getEdge().size(); c++) {
					Edge edge = partition.getEdges().getEdge().get(c);
					if(!partition.getNodes().existNode(edge.getFrom_node()) )
						log.info("Node list Not exist Edge from Node : " + edge.getFrom_node());
					
					if(!partition.getNodes().existNode(edge.getTo_node()) )
						log.info("Node list Not exist Edge to Node : " + edge.getTo_node());
				}
			}
		}
		
		CheckDirectory(downPath);
		
		if( vo.getRequestType() == RequestType.SCENARIO_REGION || vo.getRequestType() == RequestType.SCENARIO_COORDINATE ){
			manager.PartitionXmlResponse(partitions, response, downPath);
		}else if( vo.getRequestType() == RequestType.MAP_REGION || vo.getRequestType() == RequestType.MAP_COORDINATE ){
			manager.PartitionXmlMapResponse(partitions, response, downPath);
		}
	}
	
	private void responsePartitionXml2(RequestVO vo, HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException{
		//강남 4구
		PartitionScenarios partitions = null;
		//String[] siguList = {"11220", "11230","11240","11250"};
		String[] siguList = {"25010", "25020","25030","25040","25050"};
		int part_count = vo.getPartition_cnt() / siguList.length;
		System.out.println("Patition count : " + part_count);
		
		partitions = new PartitionScenarios();
		partitions.setPartitionNo( String.valueOf(vo.getPartition_cnt()) );
		System.out.println("Patitions No : " + partitions.getPartitionNo());
		
		for(int s = 0; s < siguList.length; s++) {
			vo.setRegion(siguList[s]);
			if( part_count == 1) {
				//구별 전체
				vo.setPartitionNo( 0 );
			}else {
				vo.setPartitionNo( part_count / 2);
			}
			
			vo.setPartition_cnt(part_count);
			
			for(int i = 0; i < part_count; i++){
				Partition partition = new Partition();
				vo.setPartition_id(i + 1);
				System.out.println("Partition SIGU : " + vo.getRegion() + " , Partition No : " + vo.getPartitionNo() + " , Partition ID : " + vo.getPartition_id());
				partition.setId( String.valueOf( vo.getPartition_id()  ) );
				Nodes part_nodes = new Nodes();
				part_nodes.setNode( service.selectNode(vo) );
				if(part_nodes.getNode() != null && !part_nodes.getNode().isEmpty()){
					partition.setNodes(part_nodes);
					Edges part_edges = new Edges();
					part_edges.setEdge( service.selectNodeToEdge(part_nodes.getNode()));
					if(part_edges.getEdge() != null && !part_edges.getEdge().isEmpty()){
						partition.setEdges(part_edges);
						/*
						  EDGE 목록과  NODE 목록을 비교하여 없는 NODE가 있다면 추가 ( 경계부근 )
						  EDGE 목록에서 TO_NODE가 없는것.
						 */
						List<String> notIn = part_edges.notInNode(part_nodes.getNode());
						if(notIn != null && !notIn.isEmpty()){
							partition.getNodes().appendNode( service.selectNotInNode(notIn, vo), partition.getId() );
						}
						Connections part_cons = new Connections();
						part_cons.setCon( service.selectConnection2(part_edges.getEdge()) );
						if(part_cons.getCon() != null && !part_cons.getCon().isEmpty()){
							partition.setConnections(part_cons);
							if( vo.isSignal_yn() ){
								TrafficSignals part_signals = new TrafficSignals();
								part_signals.setTrafficSignal(service.selectNodeToSignal(part_nodes.getNode(), vo));
								partition.setTrafficSignalSystem(part_signals);
							}
						}							
					}
				}else {
					System.out.println("Partition Node Select Fail Partition SIGU : " + vo.getRegion() + " , Partition No : " + vo.getPartitionNo() + " , Partition ID : " + vo.getPartition_id());
				}
				partitions.setPartition(partition);
			}			
		}
		
		CheckDirectory(downPath);
		
		if( vo.getRequestType() == RequestType.SCENARIO_REGION || vo.getRequestType() == RequestType.SCENARIO_COORDINATE ){
			manager.PartitionXmlResponse(partitions, response, downPath);
		}else if( vo.getRequestType() == RequestType.MAP_REGION || vo.getRequestType() == RequestType.MAP_COORDINATE ){
			manager.PartitionXmlMapResponse(partitions, response, downPath);
		}
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	

	@RequestMapping(value="/getScenarioByRegion", method=RequestMethod.GET)
	public void getScenarioByRegion(@RequestParam(name="include", defaultValue="0") int include,
										 @RequestParam(name="fromDate") String fromDate,
										 @RequestParam(name="toDate") String toDate,
										 @RequestParam(name="fromTime") String fromTime,
										 @RequestParam(name="toTime") String toTime,
										 @RequestParam(name="city", defaultValue="250") String city,
										 @RequestParam(name="region") String region,
										 @RequestParam(name="subregion") String subregion,
										 @RequestParam(name="partitions", defaultValue="1") int partitions,
										 @RequestParam(name="signal", defaultValue="0") String signal,
										 @RequestParam(name="route", defaultValue="0") int route, 
										 @RequestParam(name="event", defaultValue="0" ) String event,	// 돌발
										 @RequestParam(name="weather", defaultValue="0" ) String weather,	
										 @RequestParam(name="busstop", defaultValue="false" ) String busstop,
										 HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {
		// TODO : 시나리오 지역 코드 요청
		//route : 0: not included, 1: skt route, 2: traffic demand		
		if( signal == "1" || signal.equals("1")){signal = "true";}
		if( event == "1" || event.equals("1")){ event = "true"; }
		if( weather == "1" || weather.equals("1")){ weather = "true"; }
		if( busstop == "1" || busstop.equals("1")){ busstop = "true"; }
		
		RequestVO vo = new RequestVO(IncludeType.get(include), fromDate, toDate, fromTime, toTime, region, subregion, 
				partitions, Boolean.valueOf(signal), RouteType.get(route), Boolean.valueOf(event), Boolean.valueOf(weather), Boolean.valueOf(busstop), RequestType.SCENARIO_REGION);
				
		if( vo.getRegion() == "0" || vo.getRegion().equals("0") ){
			vo.setRegion(city);  // 대전
		}
		
		if( vo.getSubregion() == "0" || vo.getSubregion().equals("0") ){
			vo.setSubregion("");  //모든동
		}
		
		if( vo.getPartition_cnt() > 1){
			responsePartitionXml(vo, request, response);
			return;
		}
		
		Nodes nodes = null;
		Edges edges = null;
		Connections cons = null;
		TrafficSignals signals = null;
		Weathers weathers = null;
		Unexpecteds unexpected = null;	
		BusStops busstops = null;
		
		Scenarios sc = null;
		sc = new Scenarios(); 
		nodes = new Nodes();
		nodes.setNode( service.RegionNode(vo) );		
		if(nodes.getNode() != null && !nodes.getNode().isEmpty()){
			log.info("Node Count : " + nodes.getNode().size());
			sc.setNodes(nodes);
			edges = new Edges();
			edges.setEdge( service.selectNodeToEdge(nodes.getNode()));
			if(edges.getEdge() != null && !edges.getEdge().isEmpty()){
				log.info("Edge Count : " + edges.getEdge().size());
				/*
				  EDGE 목록과  NODE 목록을 비교하여 없는 NODE가 있다면 추가 ( 경계부근 )
				  EDGE 목록에서 TO_NODE가 없는것.
				 */
				List<String> notIn = edges.notInNode(nodes.getNode());
				if(notIn != null && !notIn.isEmpty()){
					log.info("Not In Node Count : " + notIn.size());
					sc.getNodes().appendNode( service.selectNotInNode(notIn, vo), "");
				}
				sc.setEdges(edges);
				cons = new Connections();
				cons.setCon( service.selectConnection2(edges.getEdge()) );
				if(cons.getCon() != null && !cons.getCon().isEmpty()){
					log.info("Connection Count : " + cons.getCon().size());
					sc.setConnections(cons);
					if( vo.isSignal_yn() ){
						signals = new TrafficSignals();
						signals.setTrafficSignal(service.selectNodeToSignal(nodes.getNode(), vo));
						if( signals.getTrafficSignal() != null)
							log.info("Signal Count : " + signals.getTrafficSignal().size());
						sc.setTrafficSignalSystem(signals);
					}
				}						
			}
		}

		if( vo.isWeather_yn() ){
			weathers = new Weathers();
			weathers.setWeather(service.selectWeather(vo));
			sc.setWeathers( weathers );
		}
		
		if( vo.isEvent_yn() ){
			unexpected = new Unexpecteds();
			unexpected.setUnexpected( service.selectUnexpected(vo) );
			sc.setEvents(unexpected);
		}
		
		// 20211118 장길수 추가 : 버스 정류소 목록
		if (vo.isBusstop_yn()) {
			busstops = new BusStops();
			busstops.setBusstop(service.selectBusStopList(edges.getEdge()));
			sc.setBusStops(busstops);
		}
		
		CheckDirectory(downPath);
		manager.ScenearioXmlResponse(sc, response, downPath);			
    }

	
	@RequestMapping(value="/getMapByRegion", method=RequestMethod.GET)
	public void getMapByRegion(  @RequestParam(name="include", defaultValue="1") int include,
							  @RequestParam(name="city", defaultValue="250") String city,
							  @RequestParam(name="region") String region,
							  @RequestParam(name="subregion") String subregion,
							  @RequestParam(name="partitions", required=false, defaultValue="1") int partitions,
							  HttpServletRequest request,HttpServletResponse response) throws JAXBException, IOException {
		// TODO : 지도 지역코드 요청
		
		RequestVO vo = new RequestVO(IncludeType.get(include), region, subregion, partitions, RequestType.MAP_REGION);
		
		if( vo.getRegion() == "0" || vo.getRegion().equals("0") ){
			vo.setRegion(city);  // 대전광역시
		}
		
		if( vo.getSubregion() == "0" || vo.getSubregion().equals("0") ){
			vo.setSubregion("");  //모든동
		}
		
		if( vo.getPartition_cnt() > 1){
			responsePartitionXml(vo, request, response);
			return;
		}
		
		Nodes nodes = null;
		Edges edges = null;
		Connections cons = null;
		Scenarios sc = null;
		BusStops busstops = null;
		
		sc = new Scenarios(); 
		nodes = new Nodes();
		nodes.setNode( service.RegionNode(vo) );
		
		if(nodes.getNode() != null && !nodes.getNode().isEmpty()){
			sc.setNodes(nodes);
			edges = new Edges();
			edges.setEdge( service.selectNodeToEdge(nodes.getNode()));
			if(edges.getEdge() != null && !edges.getEdge().isEmpty()){
				/*
				  EDGE 목록과  NODE 목록을 비교하여 없는 NODE가 있다면 추가 ( 경계부근 )
				  EDGE 목록에서 TO_NODE가 없는것.
				 */
				List<String> notIn = edges.notInNode(nodes.getNode());
				if(notIn != null && !notIn.isEmpty()){
					sc.getNodes().appendNode( service.selectNotInNode(notIn, vo), "");
				}
				sc.setEdges(edges);
				cons = new Connections();
				cons.setCon( service.selectConnection2(edges.getEdge()) );	
				if(cons.getCon() != null && !cons.getCon().isEmpty()){
					sc.setConnections(cons);
				}
				
				busstops = new BusStops();					
				busstops.setBusstop(service.selectBusStopList(edges.getEdge()));
				if(busstops.getBusstop() != null && !busstops.getBusstop().isEmpty()) {
					sc.setBusStops(busstops);				
				}
			}
		}
		
		CheckDirectory(downPath);
		manager.ResponseMapZip(sc, response, downPath);	
    }	

	

	@RequestMapping(value="/getSignalByRegion", method=RequestMethod.GET)
	public void getSignalByRegion(  @RequestParam(name="include", defaultValue="2") int include,
								 @RequestParam(name="fromDate") String fromDate,
								 @RequestParam(name="toDate") String toDate,
								 @RequestParam(name="fromTime") String fromTime,
								 @RequestParam(name="toTime") String toTime,
								 @RequestParam(name="city", defaultValue="250") String city,
								 @RequestParam(name="region") String region,
								 @RequestParam(name="subregion", defaultValue="0") String subregion,
								 @RequestParam(name="partitions", defaultValue="1") int partitions,
								 HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {
		
		RequestVO vo = new RequestVO(IncludeType.get(include), fromDate, toDate, fromTime, toTime, region, subregion, partitions , RequestType.SIGNAL_REGION);

		if( vo.getRegion() == "0" || vo.getRegion().equals("0") ){
			vo.setRegion(city);  // 대전광역시
		}
		
		if( vo.getSubregion() == "0" || vo.getSubregion().equals("0") ){
			vo.setSubregion("");  //모든동
		}
		
		if( vo.getPartition_cnt() > 1){
			responsePartitionXml(vo, request, response);
			return;
		}
		
		Nodes nodes = null;
		TrafficSignals signals = null;
		
		nodes = new Nodes();
		nodes.setNode( service.RegionNode(vo) );
		if(nodes.getNode() != null && !nodes.getNode().isEmpty()){
			signals = new TrafficSignals();
			signals.setTrafficSignal(service.selectNodeToSignal(nodes.getNode(), vo));
		}
		
		manager.TrafficSignalXmlResponse(signals, response);		
	}


	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 기존 인터페이스 --> Open API용 인터페이스용 추가 (ygsgun: 2022.10.19)

	@RequestMapping(value="/getMapByCoordinate", method=RequestMethod.GET)
	public void getMapByCoordinate(  @RequestParam(name="include", defaultValue="1") int include,
								  @RequestParam(name="minX") float minX,
								  @RequestParam(name="minY") float minY,
								  @RequestParam(name="maxX") float maxX,
								  @RequestParam(name="maxY") float maxY,
								  @RequestParam(name="partitions", required=false, defaultValue="1") int partitions,
								  HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {
		// TODO : 지도 좌표 요청

		RequestVO vo = new RequestVO(IncludeType.get(include), minX, minY, maxX, maxY, partitions, RequestType.MAP_COORDINATE);

		if( vo.getPartition_cnt() > 1){
			responsePartitionXml(vo, request, response);
			return;
		}

		Nodes nodes = null;
		Edges edges = null;
		Connections cons = null;
		Scenarios sc = null;
		BusStops busstops = null;

		sc = new Scenarios();
		nodes = new Nodes();
		nodes.setNode( service.CoordinateNode(vo) );

		if(nodes.getNode() != null && !nodes.getNode().isEmpty()){
			sc.setNodes(nodes);
			edges = new Edges();
			edges.setEdge( service.selectNodeToEdge(nodes.getNode()));
			if(edges.getEdge() != null && !edges.getEdge().isEmpty()){
				/*
				  EDGE 목록과  NODE 목록을 비교하여 없는 NODE가 있다면 추가 ( 경계부근 )
				  EDGE 목록에서 TO_NODE가 없는것.
				 */
				List<String> notIn = edges.notInNode(nodes.getNode());
				if(notIn != null && !notIn.isEmpty()){
					sc.getNodes().appendNode( service.selectNotInNode(notIn, vo), "");
				}
				sc.setEdges(edges);
				cons = new Connections();
				cons.setCon( service.selectConnection2(edges.getEdge()) );
				if(cons.getCon() != null && !cons.getCon().isEmpty()){
					sc.setConnections(cons);
				}

				busstops = new BusStops();
				busstops.setBusstop(service.selectBusStopList(edges.getEdge()));
				if(busstops.getBusstop() != null && !busstops.getBusstop().isEmpty()) {
					sc.setBusStops(busstops);
				}
			}
		}

		CheckDirectory(downPath);
		manager.ResponseMapZip(sc, response, downPath);
	}


	@RequestMapping(value="/getSignalByCoordinate", method=RequestMethod.GET)
	public void getSignalByCoordinate(  @RequestParam(name="include", defaultValue="2") int include,
									 @RequestParam(name="fromDate") String fromDate,
									 @RequestParam(name="toDate") String toDate,
									 @RequestParam(name="fromTime") String fromTime,
									 @RequestParam(name="toTime") String toTime,
									 @RequestParam(name="minX") float minX,
									 @RequestParam(name="minY") float minY,
									 @RequestParam(name="maxX") float maxX,
									 @RequestParam(name="maxY") float maxY,
									 @RequestParam(name="partitions", defaultValue="1") int partitions,
									 HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {
		// TODO : 신호 버전별 요청
		RequestVO vo = new RequestVO(IncludeType.get(include), fromDate, toDate, fromTime, toTime, minX, minY, maxX, maxY, partitions, RequestType.SIGNAL_COORDINATE);

		if( vo.getPartition_cnt() > 1){
			responsePartitionXml(vo, request, response);
			return;
		}

		Nodes nodes = null;
		TrafficSignals signals = null;

		nodes = new Nodes();
		nodes.setNode( service.CoordinateNode(vo) );
		if(nodes.getNode() != null && !nodes.getNode().isEmpty()){
			signals = new TrafficSignals();
			signals.setTrafficSignal(service.selectNodeToSignal(nodes.getNode(), vo));
		}

		manager.TrafficSignalXmlResponse(signals, response);
	}

	@RequestMapping(value="/getScenarioByCoordinate", method=RequestMethod.GET)
	public void getScenarioByCoordinate(@RequestParam(name="include", defaultValue="0") int include,
									 @RequestParam(name="fromDate") String fromDate,
									 @RequestParam(name="toDate") String toDate,
									 @RequestParam(name="fromTime") String fromTime,
									 @RequestParam(name="toTime") String toTime,
									 @RequestParam(name="minX") float minX,
									 @RequestParam(name="minY") float minY,
									 @RequestParam(name="maxX") float maxX,
									 @RequestParam(name="maxY") float maxY,
									 @RequestParam(name="partitions", defaultValue="1") int partitions,
									 @RequestParam(name="signal", defaultValue="0") String signal,
									 @RequestParam(name="route", defaultValue="0") int route,
									 @RequestParam(name="event", defaultValue="false") String event,	// 돌발
									 @RequestParam(name="weather", defaultValue="false" ) String weather,
									 @RequestParam(name="busstop", defaultValue="false" ) String busstop,
									 HttpServletRequest request,HttpServletResponse response) throws JAXBException, IOException {
		// TODO : 시나리오 좌표 요청
		//route : 0: not included, 1: skt route, 2: traffic demand
		if( signal == "1" || signal.equals("1")){signal = "true";}
		if( event == "1" || event.equals("1")){ event = "true"; }
		if( weather == "1" || weather.equals("1")){ weather = "true"; }
		if( busstop == "1" || busstop.equals("1")){ busstop = "true"; }

		RequestVO vo = new RequestVO(IncludeType.get(include),fromDate, toDate, fromTime, toTime, minX, minY, maxX, maxY,
				partitions, Boolean.valueOf(signal), RouteType.get(route), Boolean.valueOf(event), Boolean.valueOf(weather), Boolean.valueOf(busstop), RequestType.SCENARIO_COORDINATE);

		if( vo.getPartition_cnt() > 1){
			responsePartitionXml(vo, request, response);
			return;
		}

		Nodes nodes = null;
		Edges edges = null;
		Connections cons = null;
		TrafficSignals signals = null;
		Weathers weathers = null;
		Unexpecteds unexpected = null;
		BusStops busstops = null;

		Scenarios sc = null;
		sc = new Scenarios();
		nodes = new Nodes();
		nodes.setNode( service.CoordinateNode(vo) );
		if(nodes.getNode() != null && !nodes.getNode().isEmpty()){
			sc.setNodes(nodes);
			edges = new Edges();
			edges.setEdge( service.selectNodeToEdge(nodes.getNode()));
			if(edges.getEdge() != null && !edges.getEdge().isEmpty()){
				/*
				  EDGE 목록과  NODE 목록을 비교하여 없는 NODE가 있다면 추가 ( 경계부근 )
				  EDGE 목록에서 TO_NODE가 없는것.
				 */
				List<String> notIn = edges.notInNode(nodes.getNode());
				if(notIn != null && !notIn.isEmpty()){
					sc.getNodes().appendNode( service.selectNotInNode(notIn, vo), "");
				}
				sc.setEdges(edges);
				cons = new Connections();
				cons.setCon( service.selectConnection2(edges.getEdge()) );
				if(cons.getCon() != null && !cons.getCon().isEmpty()){
					sc.setConnections(cons);
					if( vo.isSignal_yn() ){
						signals = new TrafficSignals();
						signals.setTrafficSignal(service.selectNodeToSignal(nodes.getNode(), vo));
						sc.setTrafficSignalSystem(signals);
					}
				}
			}
		}

		if( vo.isWeather_yn() ){
			weathers = new Weathers();
			weathers.setWeather(service.selectWeather(vo));
			sc.setWeathers( weathers );
		}

		if( vo.isEvent_yn() ){
			unexpected = new Unexpecteds();
			unexpected.setUnexpected( service.selectUnexpected(vo) );
			sc.setEvents(unexpected);
		}

		// 20211118 장길수 추가 : 버스 정류소 목록
		if (vo.isBusstop_yn()) {
			busstops = new BusStops();
			busstops.setBusstop(service.selectBusStopList(edges.getEdge()));
			sc.setBusStops(busstops);
		}

		CheckDirectory(downPath);
		manager.ScenearioXmlResponse(sc, response, downPath);
	}
}
