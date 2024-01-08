package net.etri.rest.api.xml.service.impl;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.etri.rest.api.cmm.AbstractServiceImpl;
import net.etri.rest.api.repository.FileVO;
import net.etri.rest.api.repository.RequestVO;
import net.etri.rest.api.xml.BusStop;
import net.etri.rest.api.xml.Connection;
import net.etri.rest.api.xml.Edge;
import net.etri.rest.api.xml.Node;
import net.etri.rest.api.xml.TlLogic;
import net.etri.rest.api.xml.TlLogics;
import net.etri.rest.api.xml.TrafficSignal;
import net.etri.rest.api.xml.Unexpected;
import net.etri.rest.api.xml.Weather;
import net.etri.rest.api.xml.service.VehicleRoute;
import net.etri.rest.api.xml.service.XmlService;

/**
 * <p>클래스 설명</p>
 * <p>XmlServiceImpl</p>
 * <pre>
 *  net.etri.rest.api.xml.service.impl
 *         └ XmlServiceImpl.java
 * </pre>
 *
 **/
@Service("xmlService")
public class XmlServiceImpl extends AbstractServiceImpl implements XmlService {

	@Resource(name="xmlDao")
	private XmlDao dao;
	/**
	 node 목록을 업데이트 한다.
     @param nodes 업데이트 대상 Node목록
     @return int 적용결과 개수
	*/
	@Override
	public int updateNode(List<Node> nodes) {
		return dao.updateNode(nodes);
	}
	/**
	 Node 목록을 조회 한다.
     @param vo 요청VO
     @return List&lt;Node&gt; Node 목록
	*/
	@Override
	public List<Node> selectNode(RequestVO vo) {
		return dao.selectNode(vo);
	}
	/**
	 Edge 목록을 조회 한다. 
 	 @param node Node 목록
     @return List&lt;Edge&gt; Edge 목록
	*/
	@Override
	public List<Edge> selectNodeToEdge(List<Node> node){
		return dao.selectNodeToEdge(node);
	}
	/**
	 Node 목록을 조회 한다.
 	 @param edge Edge 목록
 	 @param selectType 검색유형
 	 @param vo 요청VO
     @return List&lt;Node&gt; Node 목록
	*/
	@Override
	public List<Node> selectEdgeToNode(List<Edge> edge, int selectType, RequestVO vo) {
		return dao.selectEdgeToNode(edge, selectType, vo);
	}
	/**
	 Node 목록을 조회 한다.
 	 @param id Node ID 목록
 	 @param vo 요청VO
     @return List&lt;Node&gt; Node 목록
	*/
	@Override
	public List<Node> selectNotInNode(List<String> id, RequestVO vo){
		return dao.selectNotInNode(id, vo);
	}
	/**
	 Edge 목록을 업데이트 한다.
 	 @param edges 업데이트 대상 Edge 목록
     @return int 적용결과 개수
	*/
	@Override
	public int updateEdge(List<Edge> edges){
		return dao.updateEdge(edges);
	}
	/**
	 Edge 목록을 조회 한다.
 	 @param vo 요청VO
     @return List&lt;Edge&gt; Edge 목록
	*/
	@Override
	public List<Edge> selectEdge(RequestVO vo) {
		return dao.selectEdge(vo);
	}	
	/**
	 Connection 목록을 업데이트 한다.	
 	 @param cons 업데이트 대상 커넥션 목록
     @return int 적용결과 개수
	*/
	@Override
	public int updateConnection(List<Connection> cons) {
		return dao.updateConnection(cons);
	}
	/**
	 Connection 목록을 조회 한다.
 	 @param vo 요청VO
     @return List&lt;Connection&gt; 커넥션 목록
	*/
	@Override
	public List<Connection> selectConnection(RequestVO vo) {
		return dao.selectConnection(vo);
	}
	/**
	 TlLogics 목록을 업데이트 한다.
 	 @param tls 업데이트 대상 TLogic 목록
     @return int 적용결과 개수
	*/
	@Override
	public int updatetTlLogic(TlLogics tls) {
		return dao.updatetTlLogic(tls);
	}
	/**
	 TlLogic 목록을 조회 한다.
 	 @param vo 요청VO
     @return List&lt;TlLogic&gt; TLogic 목록
	*/
	@Override
	public List<TlLogic> selectTlLogic(RequestVO vo) {
		return dao.selectTlLogic(vo);
	}
	/**
	 파일업로드 이력을저장
 	 @param vo 이력저장을 위한 파일VO
	*/
	@Override
	public void insertUploadHistory(FileVO vo) {
		dao.insertUploadHistory(vo);
	}
	/**
	 XML 요청 이력을저장
 	 @param vo 이력저장을 위한 요청VO
	*/
	@Override
	public void insertRequestHistory(RequestVO vo) {
		dao.insertRequestHistory(vo);
	}
	/**
	 Connection 목록을 조회 한다.
 	 @param edges Edge 목록
     @return List&lt;Connection&gt; 커넥션 목록
	*/
	@Override
	public List<Connection> selectConnection2(List<Edge> edges) {
		return dao.selectConnection2(edges);
	}
	/**
	 Connection 목록을 조회 한다.
 	 @param tls TLogic 목록
     @return List&lt;Connection&gt; 커넥션 목록
	*/
	@Override
	public List<Connection> selectConnection3(List<TlLogic> tls) {
		return dao.selectConnection3(tls);
	}
	/**
	 TlLogic 목록을 조회 한다.
     @param edge Edge 목록
     @return List&lt;TlLogic&gt; TLogic 목록
	*/
	@Override
	public List<TlLogic> selectEdgeToTlLogic(List<Edge> edge) {
		return dao.selectEdgeToTlLogic(edge);
	}
	/**
	 날씨 목록을 조회 한다.
 	 @param vo 요청VO
     @return List&lt;Weather&gt; 날씨목록
	*/	
	@Override
	public List<Weather> selectWeather(RequestVO vo) {
		return dao.selectWeather(vo);
	}
	/**
	 돌발 목록을 조회 한다.
 	 @param vo 요청VO
     @return List&lt;Unexpected&gt; 돌발 목록
	*/	
	@Override
	public List<Unexpected> selectUnexpected(RequestVO vo) {
		return dao.selectUnexpected(vo);
	}	
	/**
	 신호 엑셀파일을 데이터베이스 저장
 	 @param excel 저장 대상 엑셀 파일
     @return boolean 정상처리 여부
	*/
	@Override
	public boolean ExcelToDatabase(File excel){
		return dao.ExcelToDatabase(excel);
	}
	/**
	 XML용 Signal 목록을 조회 한다.
	 @param id 신호ID 목록
	 @param vo 요청VO
     @return List&lt;TrafficSignal&gt; XML용 신호목록
	*/
	public List<TrafficSignal> selectSignal(List<String> id, RequestVO vo){
		return dao.selectSignal(id, vo);
	}
	/**
	 XML용 Signal 목록을 조회 한다.
 	 @param node Node 목록
 	 @param vo 요청VO
     @return List&lt;TrafficSignal&gt; XML용 신호목록
	*/
	public List<TrafficSignal> selectNodeToSignal(List<Node> node, RequestVO vo){
		return dao.selectNodeToSignal(node, vo);
	}
	/**
	 파티션번호를 조회 한다.
 	 @param vo 요청VO
     @return String; 파티션번호
	*/
	@Override
	public String GetPartitionNo(RequestVO vo){
		return dao.GetPartitionNo(vo);
	}	
	/**
	 궤적 목록을 조회 한다.
	 @param vo 요청VO
     @return List&lt;VehicleRoute&gt; XML용 궤적목록
	*/
	@Override
	public List<VehicleRoute> selectRoute(RequestVO vo){
		return dao.selectRoute(vo);
	}
	/**
	 Node 목록을 조회 한다.
    @param vo 요청VO
    @return List&lt;Node&gt; Node 목록
	*/
	@Override
	public List<Node> RegionNode(RequestVO vo) {
		return dao.RegionNode(vo);
	}
	
	@Override
	public List<Node> CoordinateNode(RequestVO vo) {
		return dao.CoordinateNode(vo);
	}
	/**
	 * 20211118 장길수 추가
	 * 버스 정류소 목록을 조회한다.
	 * @param edge edge 목록
	 * @return List<BusStop> 버스 정류소 목록
	 */
	@Override
	public List<BusStop> selectBusStopList(List<Edge> edge) {
		return dao.selectBusStopList(edge);
	}


	@Override
	public List<Node> CoordinateNodeByDong(RequestVO vo) {
		return dao.CoordinateNodeByDong(vo);
	}
}
