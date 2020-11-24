/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.xml.service.impl
 * @file XmlDao.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일			버전			수정자		수정내용
 * -------------	--------	------		-------------------------------
 * 2018. 6. 11.		0.0.1		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.xml.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.solufine.rest.api.mapper.DefaultMapper;
import net.solufine.rest.api.mapper.XmlMapper;
import net.solufine.rest.api.repository.FileVO;
import net.solufine.rest.api.repository.RequestVO;
import net.solufine.rest.api.xml.Connection;
import net.solufine.rest.api.xml.Edge;
import net.solufine.rest.api.xml.Node;
import net.solufine.rest.api.xml.Phase;
import net.solufine.rest.api.xml.TlLogic;
import net.solufine.rest.api.xml.TlLogics;
import net.solufine.rest.api.xml.TrafficSignal;
import net.solufine.rest.api.xml.Unexpected;
import net.solufine.rest.api.xml.Weather;
import net.solufine.rest.api.xml.service.Signal;
import net.solufine.rest.api.xml.service.SignalDetail;
import net.solufine.rest.api.xml.service.SignalPhase;
import net.solufine.rest.api.xml.service.SignalScenario;
import net.solufine.rest.api.xml.service.SignalTime;
import net.solufine.rest.api.xml.service.SignalTod;
import net.solufine.rest.api.xml.service.SpecialDay;
import net.solufine.rest.api.xml.service.VehicleRoute;
import net.solufine.rest.api.xml.type.TlLogicType;

/**
 * <p>클래스 설명</p>
 * <p>XmlDao</p>
 * <pre>
 *  net.solufine.rest.api.xml.service.impl
 *         └ XmlDao.java
 * </pre>
 *
 @author  redkaras 
 @since 0.0.1
 **/
@Repository("xmlDao")
@Transactional(transactionManager="txManager")
public class XmlDao {
	
	Logger log = LoggerFactory.getLogger(XmlDao.class);
	
	/**
	 myBatis-Spring을 사용하는 경우, MyBatis의 SqlSessionTemplate 객체는 commit(), rollback() 메소드를 사용할 수 없다. 
	 즉 SqlSessionTemplate 객체를 이용하여 프로그램적으로는 트랜잭션을 관리할 수 없게 한다. 
	 억지로 SqlSessionTemplate 객체의 commit() 메소드를 호출하면 다음과 같은 예외를 발생한다.​
	java.lang.UnsupportedOperationException: Manual commit is not allowed over a Spring managed SqlSession
	 */
	@Autowired
	private SqlSession session;
	
	/**
	 node 목록을 업데이트 한다.
     @param nodes 업데이트 대상 Node목록
     @return int 적용결과 개수
	*/
	public int updateNode(List<Node> nodes){
		// TODO : 노드 목록 업데이트
		int result = 0;
		try {
			while( nodes.size() > 0){
				int iOffset = 0;
				List<Node> regList = nodes.subList(iOffset, nodes.size() > 999? 1000:nodes.size());
				session.getMapper(XmlMapper.class).updateNode(regList);
				nodes.removeAll(regList);
			}
		} catch (Exception e) {
			log.error("updateNode", e);
			return -1;
		}
		return result;
		
	}
	/**
	 Node 목록을 조회 한다. 파티션 조회일 경우 BORDER_INFO 에서 EDGE의 TO_NODE에 포함되는 NODE는 PARTITIONOWNER 컬럼을 추가
     @param vo 요청VO
     @return List&lt;Node&gt; Node 목록
	*/
	public List<Node> selectNode(RequestVO vo){
		// TODO : 노드 목록 조회
		try {
			return session.getMapper(XmlMapper.class).selectNode(vo);
		} catch (Exception e) {
			log.error("selectNode", e);
		}
		return null;
	}
	/**
	 Node 목록을 조회 한다.
 	 @param edge Edge 목록
 	 @param selectType 검색유형
 	 @param vo 요청VO
     @return List&lt;Node&gt; Node 목록
	*/
	public List<Node> selectEdgeToNode(List<Edge> edge, int selectType, RequestVO vo) {
		// TODO : 엣지를 통한 노드 목록 조회
		try {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("list", edge);
			map.put("selectType", selectType);
			map.put("requestType", vo.getRequestType().getValue());
			map.put("partition_cnt", vo.getPartition_cnt());
			map.put("region", vo.getRegion());
			
			return session.getMapper(XmlMapper.class).selectEdgeToNode(map);
		} catch (Exception e) {
			log.error("selectEdgeToNode", e);
		}
		return null;
	}
	/**
	 Node 목록을 조회 한다.
 	 @param id Node ID 목록
 	 @param vo 요청VO
     @return List&lt;Node&gt; Node 목록
	*/
	public List<Node> selectNotInNode(List<String> id, RequestVO vo){
		// TODO : 엣지를 통한 노드 목록 조회
		try {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("list", id);
			map.put("requestType", vo.getRequestType().getValue());
			map.put("partitionNo", vo.getPartitionNo());
			map.put("partition_id", vo.getPartition_id());
			map.put("partition_cnt", vo.getPartition_cnt());
			map.put("region", vo.getRegion());
			
			return session.getMapper(XmlMapper.class).selectNotInNode(map);
		} catch (Exception e) {
			log.error("selectNotInNode", e);
		}
		return null;
	}
	/**
	 Edge 목록을 조회 한다.  FROM 또는 TO 에 NODE ID가 포함되는 전체 EDGE 조회 
 	 @param node Node 목록
     @return List&lt;Edge&gt; Edge 목록
	*/
	public List<Edge> selectNodeToEdge(List<Node> node){
		// TODO : 노드를 통한 엣지 목록 조회
		try {
			return session.getMapper(XmlMapper.class).selectNodeToEdge(node);
		} catch (Exception e) {
			log.error("selectNodeToEdge", e);
		}
		return null;
	}
	/**
	 Edge 목록을 업데이트 한다.
 	 @param edges 업데이트 대상 Edge 목록
     @return int 적용결과 개수
	*/
	public int updateEdge(List<Edge> edges){
		// TODO : 엣지 업데이트
		int result = 0;		
		try {			
			while( edges.size() > 0){
				int iOffset = 0;
				List<Edge> regList = edges.subList(iOffset, edges.size() > 999? 1000:edges.size());
				session.getMapper(XmlMapper.class).updateEdge(regList);
				edges.removeAll(regList);
			}
		} catch (Exception e) {
			log.error("updateEdge", e);
			return -1;
		}
		return result;
	}
	/**
	 Edge 목록을 조회 한다.
 	 @param vo 요청VO
     @return List&lt;Edge&gt; Edge 목록
	*/
	public List<Edge> selectEdge(RequestVO vo){
		// TODO : 요청 파라미터를 통한 엣지 조회
		try {
			return session.getMapper(XmlMapper.class).selectEdge(vo);
		} catch (Exception e) {
			log.error("selectEdge", e);
		}
		return null;
	}
	/**
	 Connection 목록을 업데이트 한다.	
 	 @param cons 업데이트 대상 커넥션 목록
     @return int 적용결과 개수
	*/
	public int updateConnection(List<Connection> cons){
		// TODO : 커넥션 업데이트
		int result = 0;
		try {
			while( cons.size() > 0){
				int iOffset = 0;
				List<Connection> regList = cons.subList(iOffset, cons.size() > 999? 1000:cons.size());
				session.getMapper(XmlMapper.class).updateConnection(regList);
				cons.removeAll(regList);
			}
		} catch (Exception e) {
			log.error("updateConnection", e);
			return -1;
		}
		return result;
	}
	/**
	 Connection 목록을 조회 한다.
 	 @param vo 요청VO
     @return List&lt;Connection&gt; 커넥션 목록
	*/
	public List<Connection> selectConnection(RequestVO vo){
		// TODO : 요청 파라미터를 통한 커넥션 목록 조회
		try {
			return session.getMapper(XmlMapper.class).selectConnection(vo);
		} catch (Exception e) {
			log.error("selectConnection", e);
		}
		return null;	
	}
	/**
	 날씨 목록을 조회 한다.
 	 @param vo 요청VO
     @return List&lt;Weather&gt; 날씨목록
	*/	
	public List<Weather> selectWeather(RequestVO vo){
		// TODO : 요청 파라미터를 통한 날씨 목록 조회
		try {
			return session.getMapper(XmlMapper.class).selectWeather(vo);
		} catch (Exception e) {
			log.error("selectWeather", e);
		}
		return null;
	}
	/**
	 돌발 목록을 조회 한다.
 	 @param vo 요청VO
     @return List&lt;Unexpected&gt; 돌발 목록
	*/	
	public List<Unexpected> selectUnexpected(RequestVO vo){
		// TODO : 요청 파라미터를 통한 돌발 목록 조회
		try {
			return session.getMapper(XmlMapper.class).selectUnexpected(vo);
		} catch (Exception e) {
			log.error("selectUnexpected", e);
		}
		return null;
	}
	/**
	 Connection 목록을 조회 한다.
 	 @param edges Edge 목록
     @return List&lt;Connection&gt; 커넥션 목록
	*/
	public List<Connection> selectConnection2(List<Edge> edges) {
		// TODO : 엣지를 통한 커넥션 목록 조회
		try {
			return session.getMapper(XmlMapper.class).selectConnection2(edges);
		} catch (Exception e) {
			log.error("selectConnection2", e);
		}
		return null;	
	}
	/**
	 Connection 목록을 조회 한다.
 	 @param tls TLogic 목록
     @return List&lt;Connection&gt; 커넥션 목록
	*/
	public List<Connection> selectConnection3(List<TlLogic> tls){
		// TODO : 신호(TLogic)을 통한 커넥션 목록 조회
		try {
			return session.getMapper(XmlMapper.class).selectConnection3(tls);
		} catch (Exception e) {
			log.error("selectConnection3", e);
		}
		return null;
	}
	/**
	 TlLogics 목록을 업데이트 한다.
 	 @param tls 업데이트 대상 TLogic 목록
     @return int 적용결과 개수
	*/
	public int updatetTlLogic(TlLogics tls) {
		// TODO : 신호(TLogic) 업데이트
		int result = 0;
		try {
			List<TlLogic> tlLogics = tls.getTlLogics();
			if(tlLogics != null){
				if( tlLogics != null){
					List<Phase> phases = new ArrayList<Phase>();
					while( tlLogics.size() > 0){
						int iOffset = 0;
						List<TlLogic> regList = tlLogics.subList(iOffset, tlLogics.size() > 999? 1000:tlLogics.size());
						session.getMapper(XmlMapper.class).updateTlLogic(regList);
						Iterator<TlLogic> it = tlLogics.iterator();
						while( it.hasNext() ){
							TlLogic tl = it.next();
							Iterator<Phase> it2 = tl.getPhases().listIterator();
							int iSeq = 1;
							while( it2.hasNext() ){
								Phase p = it2.next();
								p.setId(tl.getId());
								p.setProgramID(tl.getProgramID());
								p.setSeq( String.valueOf(iSeq) );
								phases.add(p);
								iSeq++;
							}
						}
						tlLogics.removeAll(regList);
					}
					
					if( phases.size() > 0 )
						session.getMapper(XmlMapper.class).deletePhase(phases);
					
					while( phases.size() > 0){						
						int iOffset = 0;
						List<Phase> regPhaseList = phases.subList(iOffset, phases.size() > 999? 1000:phases.size());
						//session.getMapper(XmlMapper.class).deletePhase(regPhaseList);
						session.getMapper(XmlMapper.class).updatePhase(regPhaseList);	
						phases.removeAll(regPhaseList);
					}					
				}				
			}			
			
			List<Connection> cons = tls.getCon();
			if( cons != null){
				while( cons.size() > 0){
					int iOffset = 0;
					List<Connection> regList = cons.subList(iOffset, cons.size() > 999? 1000:cons.size());
					session.getMapper(XmlMapper.class).updateConnection2(regList);
					cons.removeAll(regList);
				}
			}
		} catch (Exception e) {
			log.error("updatetTlLogic", e);
			return -1;
		}
		return result;	
	}
	/**
	 TlLogic 목록을 조회 한다.
 	 @param vo 요청VO
     @return List&lt;TlLogic&gt; TLogic 목록
	*/
	public List<TlLogic> selectTlLogic(RequestVO vo){
		// TODO : 요청 파라미터를 통한 신호(TLogic) 목록 조회
		List<TlLogic> list = null;
		try {
			list = session.getMapper(XmlMapper.class).selectTlLogic(vo);
			if( list != null && !list.isEmpty()){
				List<Phase> phases = session.getMapper(XmlMapper.class).selectPhaseList(list);
				if( phases != null && !phases.isEmpty()){
					Iterator<Phase> it = phases.iterator();
					while(it.hasNext()){
						Phase phase = it.next();
						Iterator<TlLogic> it2 = list.iterator();
						while(it2.hasNext()){
							TlLogic tl = it2.next();
							if(tl.getId() == phase.getId() || tl.getId().equals(phase.getId())){
								if( tl.getPhases() == null ){
									tl.setPhases(new ArrayList<Phase>());
								}
								if(tl.getProgramID() == phase.getProgramID() || tl.getProgramID().equals(phase.getProgramID())){
									phase.setId("");
									phase.setSeq("");
									phase.setProgramID("");
									tl.getPhases().add(phase);
									break;
								}								
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("selectTlLogic", e);
			return null;
		}
		return list;
	}
	/**
	 TlLogic 목록을 조회 한다.
     @param edge Edge 목록
     @return List&lt;TlLogic&gt; TLogic 목록
	*/
	public List<TlLogic> selectEdgeToTlLogic(List<Edge> edge){
		// TODO : 엣지를 통한 신호(TLogic) 목록 조회
		List<TlLogic> list = null;
		try {
			list = session.getMapper(XmlMapper.class).selectEdgeToTlLogic(edge);
			if( list != null && !list.isEmpty()){
				List<Phase> phases = session.getMapper(XmlMapper.class).selectPhaseList(list);
				if( phases != null && !phases.isEmpty()){
					Iterator<Phase> it = phases.iterator();
					while(it.hasNext()){
						Phase phase = it.next();
						Iterator<TlLogic> it2 = list.iterator();
						while(it2.hasNext()){
							TlLogic tl = it2.next();
							if(tl.getId() == phase.getId() || tl.getId().equals(phase.getId())){
								if( tl.getPhases() == null ){
									tl.setPhases(new ArrayList<Phase>());
								}
								if(tl.getProgramID() == phase.getProgramID() || tl.getProgramID().equals(phase.getProgramID())){
									phase.setId("");
									phase.setSeq("");
									phase.setProgramID("");
									tl.getPhases().add(phase);
									break;
								}			
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("selectEdgeToTlLogic", e);
			return null;
		}
		return list;
	}
	/**
	 파일업로드 이력을저장
 	 @param vo 이력저장을 위한 파일VO
	*/
	public void insertUploadHistory(FileVO vo){
		// TODO : 업로드 이력 저장
		try {
			session.getMapper(DefaultMapper.class).insertUploadHistory(vo);
		} catch (Exception e) {
			log.error("insertUploadHistory", e);
		}
	}
	/**
	 XML 요청 이력을저장
 	 @param vo 이력저장을 위한 요청VO
	*/
	public void insertRequestHistory(RequestVO vo){
		// TODO : 요청 이력 저장
		try {
			session.getMapper(DefaultMapper.class).insertRequestHistory(vo);
		} catch (Exception e) {
			log.error("insertRequestHistory", e);
		}
	}
	/**
	 신호 엑셀파일을 데이터베이스 저장
 	 @param excel 저장 대상 엑셀 파일
     @return boolean 정상처리 여부
	*/
	public boolean ExcelToDatabase(File excel){
		// TODO : 신호 엑셀 양식 데이터 저장
		FileInputStream fis = null;
		XSSFWorkbook workbook = null;
		
		try {
			fis = new FileInputStream(excel);
			workbook = new XSSFWorkbook(fis);
			SignalSheetToDatabase(workbook.getSheet("신호"));
			SignalTimeSheetToDatabase(workbook.getSheet("현시별주기"));
			TodPlanSheetToDatabase(workbook.getSheet("TOD PLAN"));
			PatternSheetToDatabase(workbook.getSheet("패턴"));
			SpecialDaySheetToDatabase(workbook.getSheet("특수일"));
			
		} catch (Exception e) {			
			e.printStackTrace();
			return false;
		} finally {
			try {
				if(workbook != null)
					workbook.close();
				
				if(fis != null)
					fis.close();
			} catch (IOException e) {

			}
		}		
		return true;
	}
	/**
	 엑셀파일의 신호 시트 내요을 데이터베이스 저장
 	 @param sheet 저장 대상 엑셀 시트
     @return int 적용결과 개수
	*/
	private int SignalSheetToDatabase(XSSFSheet sheet) throws Exception{
		// TODO : 신호 시트 데이터 저장
		if(sheet == null)
			return 0;
		
		ArrayList<Signal> SignalList = new ArrayList<Signal>();
		
		//신호 시트 읽기 시작				
		int rows = sheet.getPhysicalNumberOfRows();
		for(int rowindex = 1; rowindex < rows; rowindex++){				
			XSSFRow row = sheet.getRow(rowindex);
		    if(row != null){
		    	// 1:신호ID	2:버전	3:신호유형 	4:표준신호ID	5:교차로번호	6:교차로명 	7:시행일	8:참고    9:관할서
		    	XSSFCell SIGNAL_ID = row.getCell(1);
		    	XSSFCell VERSION = row.getCell(2);
		    	XSSFCell STD_ID = row.getCell(4);
		    	
		    	if(SIGNAL_ID == null || VERSION == null || STD_ID == null)
		    		continue;
		    	
		    	XSSFCell TLLOGICTYPE = row.getCell(3);		    	
		    	XSSFCell CROSS_NO = row.getCell(5);
		    	XSSFCell CROSS_NM = row.getCell(6);
		    	XSSFCell EFF_DATE = row.getCell(7);
		    	XSSFCell SIGNAL_GROUP = row.getCell(8);
		    	XSSFCell POLICE_STATION = row.getCell(9);
		    				    	
		    	Signal signal = new Signal();
		    	signal.setSignal_id( getCellValue(SIGNAL_ID) );
		    	signal.setVersion( getCellValue(VERSION) );
		    	
		    	if(TLLOGICTYPE != null){
		    		String tlType = getCellValue(TLLOGICTYPE);
		    		if( tlType.equals("") || tlType.equals("false") )
		    			tlType = "STATIC";
		    		signal.setTllogictype(TlLogicType.get( tlType ));
		    	}		    		
		    	
		    	if(STD_ID != null)signal.setStd_id( getCellValue(STD_ID) );
		    	
		    	if(signal.getSignal_id().equals("") || signal.getSignal_id().equals("0") || 
		    			signal.getSignal_id() == ("false") || signal.getSignal_id().equals("false") || 
		    			signal.getVersion().equals("") || signal.getStd_id().equals("") || signal.getStd_id().equals("false")
		    			|| signal.getStd_id() == "false"){
		    		log.warn("Signal Input data miss : " + signal.toString());
		    		continue;
		    	}
		    	
		    	if(CROSS_NO != null)signal.setCross_no( getCellValue(CROSS_NO) ); 
		    	if(CROSS_NM != null)signal.setCross_nm( getCellValue(CROSS_NM) ); 
		    	if(EFF_DATE != null)signal.setEff_date( getCellValue(EFF_DATE) );
		    	if(SIGNAL_GROUP != null)signal.setSignal_group( getCellValue(SIGNAL_GROUP) );
		    	if(POLICE_STATION != null)signal.setPolice_station( getCellValue(POLICE_STATION) );
		    	
		    	SignalList.add(signal);
		    }
		}
		
		if( !SignalList.isEmpty() ){
			/*
			int count = 0;
			for(int i = 0; i < SignalList.size(); i++){
				session.getMapper(XmlMapper.class).updateSignalOne(SignalList.get(i)); 
				count++;
			}
			*/
			int count = session.getMapper(XmlMapper.class).updateSignal(SignalList); 
			SignalList.clear();
			return count;
		}
		
		return 0;
	}
	/**
	 엑셀파일의 신호별시간 시트 내요을 데이터베이스 저장
	 @param sheet 저장 대상 엑셀 시트
     @return int 적용결과 개수
	*/
	private int SignalTimeSheetToDatabase(XSSFSheet sheet) throws Exception{
		// TODO : 신호별시간 시트 저장
		if(sheet == null)
			return 0;
				
		//현시별주기 시트 읽기 시작
		ArrayList<SignalTime> SignalTimeList = new ArrayList<SignalTime>();				
		int rows = sheet.getPhysicalNumberOfRows();
		for(int rowindex = 1; rowindex < rows; rowindex++){
			XSSFRow row = sheet.getRow(rowindex);
			if(row != null){
				/**
				  2019.08.14 Redkaras TM6까지 확장
				  */
				// 1:신호ID	2:버전	3:신호유형 	
				//5,6,7,8,9,10:주현시	
				//11,12,13,14,15,16:최소녹색		
				//17,18,19,20,21,22:맵최대녹색				
				//23,24,25,26,27,28:중앙최대녹색		
				//29,30,31,32,33,34:보행녹색	
				//35,36,37,38,39,40:보행점멸		
				//41,42,43,44,45,46:황색신호		
				//47,48,49,50,51,52:전적색신호
				//53,54,55,56,57,58:보행전시간	
				//59,60,61,62,63,64:MDS			

				XSSFCell SIGNAL_ID = row.getCell(1);
		    	XSSFCell VERSION = row.getCell(2);			    	
		    	if(SIGNAL_ID == null || VERSION == null)
		    		continue;
		    	
		    	XSSFCell PROGRAM_ID = row.getCell(4);
		    	if(PROGRAM_ID != null ){
		    		String id = getCellValue(PROGRAM_ID);
		    		if( !id.equals("0") )
		    			continue;
		    	}
		    		
		    	for(int cellindex = 5; cellindex < 65; cellindex += 6 ){
		    		if( row.getCell(cellindex) == null)
		    			continue;
		    		
		    		XSSFCell TM1 = row.getCell(cellindex);	
		    		XSSFCell TM2 = row.getCell(cellindex + 1);	
		    		XSSFCell TM3 = row.getCell(cellindex + 2);	
		    		XSSFCell TM4 = row.getCell(cellindex + 3);	
		    		XSSFCell TM5 = row.getCell(cellindex + 4);
		    		XSSFCell TM6 = row.getCell(cellindex + 5);
		    		
		    		SignalTime st = new SignalTime();
		    		st.setSignal_id( getCellValue(SIGNAL_ID) );
		    		st.setVersion( getCellValue(VERSION) );
		    		if(st.getSignal_id().equals("") || st.getSignal_id().equals("0") || st.getVersion().equals("")){
			    		log.warn("Signal Time Input data miss : " + st.toString());
			    		continue;
			    	}
		    		
		    		st.setSignal_type( String.valueOf( cellindex / 6  ) );
		    		String sStr = getCellValue(TM1);
		    		boolean isNumeric = sStr.chars().allMatch( Character::isDigit );
		    		if(!isNumeric ){
		    			log.debug(sStr);
		    		}
		    		if(TM1 != null)st.setTm1( Short.valueOf(getCellValue(TM1)) );
		    		if(TM2 != null)st.setTm2( Short.valueOf(getCellValue(TM2)) );
		    		if(TM3 != null)st.setTm3( Short.valueOf(getCellValue(TM3)) );
		    		if(TM4 != null)st.setTm4( Short.valueOf(getCellValue(TM4)) );
		    		if(TM5 != null)st.setTm5( Short.valueOf(getCellValue(TM5).equals("false") == true? "0":getCellValue(TM5)) );
		    		if(TM6 != null)st.setTm6( Short.valueOf(getCellValue(TM6).equals("false") == true? "0":getCellValue(TM6)) );
		    		
		    		SignalTimeList.add(st);
		    	}	
			}
		}
		
		if( !SignalTimeList.isEmpty() ){
			/*
			int count = 0;
			for(int i = 0; i < SignalTimeList.size(); i++){
				session.getMapper(XmlMapper.class).updateSignalTimeOne(SignalTimeList.get(i)); 
				count++;
			}
			*/			
			int count = session.getMapper(XmlMapper.class).updateSignalTime(SignalTimeList); 
			SignalTimeList.clear();
			return count;
		}
		
		return 0;
	}	
	/**
	 엑셀파일의 TOD Plan 시트 내요을 데이터베이스 저장
	 @param sheet 저장 대상 엑셀 시트
     @return int 적용결과 개수
	*/
	private int TodPlanSheetToDatabase(XSSFSheet sheet) throws Exception{
		// TODO : TOD Plan 시트 저장
		if(sheet == null)
			return 0;
		
		//TOD PLAN 시트 읽기 시작
		ArrayList<SignalTod> TodPlanList = new ArrayList<SignalTod>();	
		int rows = sheet.getPhysicalNumberOfRows();
		for(int rowindex = 1; rowindex < rows; rowindex++){
			XSSFRow row = sheet.getRow(rowindex);
			if(row != null){
				// 1:신호ID	2:버전	3:신호유형	  4:프로그램ID 	5:Plan ID	6:순번	7:시작시간		8:종료시간		9:주기	10:패턴ID 	
				XSSFCell SIGNAL_ID = row.getCell(1);
		    	XSSFCell VERSION = row.getCell(2);	
		    	XSSFCell PLAN_ID = row.getCell(5);
		    	XSSFCell PATTERN_ID = row.getCell(10);	
		    	if(SIGNAL_ID == null || VERSION == null || PLAN_ID == null || PATTERN_ID == null)
		    		continue;
		    	
		    	XSSFCell PROGRAM_ID = row.getCell(4);
		    	if(PROGRAM_ID != null ){
		    		String id = getCellValue(PROGRAM_ID);
		    		if( !id.equals("0") )
		    			continue;
		    	}
		    	
		    	XSSFCell SEQ = row.getCell(6);	
		    	XSSFCell FROM_TIME = row.getCell(7);	
		    	XSSFCell TO_TIME = row.getCell(8);
		    	
		    	SignalTod plan = new SignalTod();
		    	plan.setSignal_id( getCellValue(SIGNAL_ID) );
		    	plan.setVersion( getCellValue(VERSION) );
		    	plan.setPlan_id(  getCellValue(PLAN_ID) );
		    	
		    	if(plan.getSignal_id().equals("") || plan.getSignal_id().equals("0") || plan.getVersion().equals("") 
		    			|| plan.getPlan_id().equals("")	|| plan.getPlan_id().equals("false")){
		    		log.warn("TodPlan Input data miss : " + plan.toString());
		    		continue;
		    	}
		    	String patternId = getCellValue(PATTERN_ID);
		    	if(patternId.equals("") || patternId.equals("false")){
		    		log.warn("TodPlan Input data miss : " + plan.toString());
		    		continue;
		    	}
		    	plan.setPattern_id( Integer.valueOf(patternId) );
		    	
		    	if(SEQ != null)plan.setSeq( Integer.valueOf(getCellValue(SEQ)) );
		    	if(FROM_TIME != null)plan.setFrom_time( getCellValue(FROM_TIME) );
		    	if(TO_TIME != null)plan.setTo_time( getCellValue(TO_TIME) );
		    	
		    	TodPlanList.add(plan);
			}
		}
		
		if( !TodPlanList.isEmpty() ){
			/*
			int count = 0;
			for(int i = 0; i < TodPlanList.size(); i++){
				session.getMapper(XmlMapper.class).updateTodPlanOne(TodPlanList.get(i)); 
				count++;
			}	
			*/
			int count = session.getMapper(XmlMapper.class).updateTodPlan(TodPlanList); 
			TodPlanList.clear();
			return count;
		}
		
		return 0;
	}
	/**
	 엑셀파일의 패턴 시트 내요을 데이터베이스 저장
	 @param sheet 저장 대상 엑셀 시트
     @return int 적용결과 개수
	*/
	private int PatternSheetToDatabase(XSSFSheet sheet) throws Exception{
		// TODO : 패턴 시트 저장
		if(sheet == null)
			return 0;
		
		//패턴 시트 읽기 시작
		ArrayList<SignalScenario> SignalScenarioList = new ArrayList<SignalScenario>();
		ArrayList<SignalPhase> SignalScenarioPhaseList = new ArrayList<SignalPhase>();
		
		int rows = sheet.getPhysicalNumberOfRows();
		for(int rowindex = 1; rowindex < rows; rowindex++){
			XSSFRow row = sheet.getRow(rowindex);
			if(row != null){
				// 1:신호ID	2:버전	3:신호유형		4:프로그램ID    5:시나리오번호     6:주기    7:패턴 ID	8:연동	9: 현시값 	
				XSSFCell SIGNAL_ID = row.getCell(1);
		    	XSSFCell VERSION = row.getCell(2);	
		    	XSSFCell PATTERN_ID = row.getCell(7);		    	
		    	if(SIGNAL_ID == null || VERSION == null || PATTERN_ID == null)
		    		continue;
		    	
		    	XSSFCell PROGRAM_ID = row.getCell(4);
		    	if(PROGRAM_ID != null ){
		    		String id = getCellValue(PROGRAM_ID);
		    		if( !id.equals("0") )
		    			continue;
		    	}
		    	
		    	XSSFCell SCENARIO_NO = row.getCell(5);	
		    	XSSFCell CYCLE = row.getCell(6);	
		    	XSSFCell PERISTALSIS = row.getCell(8);
		    	
		    	SignalScenario scenario = new SignalScenario();
		    	scenario.setSignal_id( getCellValue(SIGNAL_ID) );
		    	scenario.setVersion( getCellValue(VERSION) );
		    	
		    	String patternId = getCellValue(PATTERN_ID);
		    	if(scenario.getSignal_id().equals("") || scenario.getSignal_id().equals("0") || 
		    			scenario.getVersion().equals("") || patternId.equals("") || patternId.equals("false")){
		    		log.warn("Pattern Input data miss : " + scenario.toString());
		    		continue;
		    	}
		
		    	scenario.setPattern_id( Integer.valueOf(patternId) );
		    	if(SCENARIO_NO != null)scenario.setScenario_no( Short.valueOf(getCellValue(SCENARIO_NO)) );
		    	if(CYCLE != null)scenario.setCycle( Short.valueOf(getCellValue(CYCLE)) );
		    	if(PERISTALSIS != null)scenario.setPeristalsis( Short.valueOf(getCellValue(PERISTALSIS)) );
		    	
		    	for(int cellindex = 9; cellindex < 21; cellindex++){
		    		XSSFCell duration = row.getCell(cellindex);
		    		if(duration == null)
		    			continue;
		    		
		    		if(duration.getCellTypeEnum() != CellType.NUMERIC)
		    			continue;
		    		
		    		SignalPhase phase = new SignalPhase();
		    		phase.setSignal_id(scenario.getSignal_id());
		    		phase.setVersion(scenario.getVersion());
		    		phase.setPattern_id(scenario.getPattern_id());
		    		phase.setPhase((cellindex - 8));
		    		phase.setDuration((int) duration.getNumericCellValue());
		    		
		    		SignalScenarioPhaseList.add(phase);
		    	}
		    	
		    	SignalScenarioList.add(scenario);
		    	
		    	
			}
		}
		
		if( !SignalScenarioList.isEmpty() ){
			ArrayList<SignalScenario> scenarioList = new ArrayList<SignalScenario>();
			Iterator<SignalScenario> it = SignalScenarioList.iterator();
			while(it.hasNext()){
				SignalScenario scenario = it.next();
				if(!scenarioList.contains(scenario))
					scenarioList.add(scenario);
			}
			
			it = scenarioList.iterator();
			while(it.hasNext()){		
				SignalScenario scenario = it.next();
				session.getMapper(XmlMapper.class).deleteSignalScenario(scenario);
			}			
			
			/*
			for(int i = 0; i < SignalScenarioList.size(); i++){
				session.getMapper(XmlMapper.class).updateSignalScenarioOne(SignalScenarioList.get(i)); 
			}	
			*/
			session.getMapper(XmlMapper.class).updateSignalScenario(SignalScenarioList);
		}
		
		if( !SignalScenarioList.isEmpty() ){
			ArrayList<SignalPhase> scenarioPhaseList = new ArrayList<SignalPhase>();
			Iterator<SignalPhase> it = SignalScenarioPhaseList.iterator();
			while(it.hasNext()){
				SignalPhase phase = it.next();
				if(!scenarioPhaseList.contains(phase))
					scenarioPhaseList.add(phase);
			}
			
			it = scenarioPhaseList.iterator();
			while(it.hasNext()){
				SignalPhase phase = it.next();
				session.getMapper(XmlMapper.class).deleteSignalPhase(phase);
				session.getMapper(XmlMapper.class).deleteSignalScenarioPhase(phase);
			}
			/*
			for(int i = 0; i < SignalScenarioPhaseList.size(); i++){
				session.getMapper(XmlMapper.class).updateSignalPhaseOne(SignalScenarioPhaseList.get(i));
				session.getMapper(XmlMapper.class).updateSignalScenarioPhaseOne(SignalScenarioPhaseList.get(i));				 
			}
			*/
			session.getMapper(XmlMapper.class).updateSignalPhase(SignalScenarioPhaseList);
			session.getMapper(XmlMapper.class).updateSignalScenarioPhase(SignalScenarioPhaseList);
			
			SignalScenarioPhaseList.clear();
			SignalScenarioList.clear();
		}
		
		return 0;
	}
	/**
	 엑셀파일의 특수일 시트 내요을 데이터베이스 저장
	 @param sheet 저장 대상 엑셀 시트
     @return int 적용결과 개수
	*/
	private int SpecialDaySheetToDatabase(XSSFSheet sheet) throws Exception{
		// TODO : 특수일 시트 저장
		if(sheet == null)
			return 0;
		
		ArrayList<SpecialDay> SpecialDayList = new ArrayList<SpecialDay>();
		int rows = sheet.getPhysicalNumberOfRows();
		for(int rowindex = 1; rowindex < rows; rowindex++){
			XSSFRow row = sheet.getRow(rowindex);
			if(row != null){
				// 1:신호ID	2:버전	3:신호유형		4:패턴 ID		5:순번	6:시작시간		7:종료시간		8:주기	9:패턴ID 	
				XSSFCell SIGNAL_ID = row.getCell(1);
		    	XSSFCell VERSION = row.getCell(2);	
		    	XSSFCell SPECIAL_NM = row.getCell(5);
		    	
		    	if(SIGNAL_ID == null || VERSION == null || SPECIAL_NM == null || SPECIAL_NM.getCellTypeEnum() == CellType.BLANK )
		    		continue;
		    		
		    	XSSFCell PLAN_ID = row.getCell(6);
		    	
				SpecialDay day = new SpecialDay();
				day.setSignal_id( getCellValue(SIGNAL_ID) );
				day.setVersion( getCellValue(VERSION) );		    	
		    	day.setSpecial_nm( getCellValue(SPECIAL_NM) );
		    	if( day.getSpecial_nm() == null || day.getSpecial_nm().equals("") || day.getSpecial_nm().equals("false") ){
		    		log.warn("Special Day Input data miss : " + day.toString());
		    		continue;
		    	}
		    	if(PLAN_ID != null)day.setPlan_id( getCellValue(PLAN_ID) );
		    	
		    	SpecialDayList.add(day);  
			}
		}
		
		if( !SpecialDayList.isEmpty() ){
			/*
			int count = 0;
			for(int i = 0; i < SpecialDayList.size(); i++){
				session.getMapper(XmlMapper.class).updateSpecialDayOne(SpecialDayList.get(i));
				count++;								 
			}
			*/
			int count = session.getMapper(XmlMapper.class).updateSpecialDay(SpecialDayList); 
			SpecialDayList.clear();
			return count;
		}
		
		return 0;
	}

	/**
	 엑셀 셀 객체에서 셀유형에 따라 값을 읽어서 반환
	 @param cell 셀객체
     @return String 셀의 값
	*/
	private String getCellValue(XSSFCell cell){
		// TODO : 엑셀 셀값 반환
		if( cell == null)
			return "";
		
		switch (cell.getCellTypeEnum()){
	        case FORMULA: 
	        	switch(cell.getCachedFormulaResultTypeEnum()){
	        		case NUMERIC: 
	        			if( DateUtil.isCellDateFormatted(cell) ){
	        				Date date = cell.getDateCellValue();
	    	        		Calendar c = Calendar.getInstance ( );
	    	        		c.setTime(date);
	    	        		if( c.get(Calendar.YEAR) > 2000 ){
	    	        			return new SimpleDateFormat("yyyy-MM-dd").format(date);
	    	        		}else{
	    	        			return new SimpleDateFormat("HH:mm:ss").format(date);
	    	        		}	    	
	    				}else{
	    	        		return (int) cell.getNumericCellValue() + "";
	    	        	}	
	        		case STRING: return cell.getStringCellValue() + "";
	    	        case BLANK: return cell.getBooleanCellValue() + "";
	    	        case ERROR:	return cell.getErrorCellValue() + "";
	    			default: return "";
	        	}
	        case NUMERIC: 
	        	if( DateUtil.isCellDateFormatted(cell) ){
	        		Date date = cell.getDateCellValue();
	        		Calendar c = Calendar.getInstance ( );
	        		c.setTime(date);
	        		if( c.get(Calendar.YEAR) > 2000 ){
	        			return new SimpleDateFormat("yyyy-MM-dd").format(date);
	        		}else{
	        			return new SimpleDateFormat("HH:mm:ss").format(date);
	        		}	        					
				}else{
	        		return (int) cell.getNumericCellValue() + "";
	        	}	        		
	        case STRING: return cell.getStringCellValue() + "";
	        case BLANK: return cell.getBooleanCellValue() + "";
	        case ERROR:	return cell.getErrorCellValue() + "";
			default: return "";
        }
	}
	/**
	 XML용 Signal 목록을 조회 한다.
	 @param id 신호ID 목록
	 @param vo 요청VO
     @return List&lt;TrafficSignal&gt; XML용 신호목록
	*/
	public List<TrafficSignal> selectSignal(List<String> id, RequestVO vo){
		// TODO : TL ID 또는 버전을 통한 신호(Signal) 목록 조회
		if(id == null || id.isEmpty())
			return null;
		
		List<Signal> list = null;
		List<TrafficSignal> result = null;
		try {
			if(id == null || id.isEmpty()){
				list = session.getMapper(XmlMapper.class).selectVerSignal(vo.getVersion());
			}else{
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("list", id);
				map.put("req_date", vo.getReq_date());
				list = session.getMapper(XmlMapper.class).selectSignal(map);
			}			
			if( list != null && !list.isEmpty()){
				result = new ArrayList<TrafficSignal>();
				Iterator<Signal> it = list.iterator();
				while( it.hasNext()){
					Signal signal = it.next();
					List<SignalDetail> detaillist = null;
					vo.setSignal_id( signal.getSignal_id() );
					vo.setVersion( signal.getVersion() );
					detaillist = session.getMapper(XmlMapper.class).selectTrafficSignalDetail(vo);
					if(detaillist == null || detaillist.isEmpty()){
						List<SignalTod> todList = session.getMapper(XmlMapper.class).selectSignalTodPlan(vo);
						if(todList != null && todList.size() == 1){
							detaillist = session.getMapper(XmlMapper.class).selectNoTimeTrafficSignalDetail(vo);
						}
					}
					if( detaillist != null && !detaillist.isEmpty()){
						Iterator<SignalDetail> detailIt = detaillist.iterator();
						SignalTod tod = null;
						SignalScenario scenario = null;
						while( detailIt.hasNext()){
							SignalDetail detail = detailIt.next();
							tod =  signal.getTod(signal.getSignal_id(), signal.getVersion(),
									detail.getPlan_id(), detail.getSeq(), detail.getPattern_id(), vo.getReq_date());
							scenario = signal.getSchedule(signal.getSignal_id(), signal.getVersion(),
									detail.getPattern_id(), detail.getScenario_no());
							
							if( tod == null ){
								tod = new SignalTod(signal.getSignal_id(), signal.getVersion(),
										detail.getPlan_id(), detail.getSeq(), detail.getPattern_id(),detail.getFrom_time(),
										detail.getTo_time(), detail.getStart_time(), detail.getCycle(), detail.getPeristalsis(), 
										vo.getReq_date(), vo.getFromTime(), vo.getToTime() );
								signal.getTod().add(tod);								
							}
							
							if( scenario == null){							
								scenario = new SignalScenario(signal.getSignal_id(), signal.getVersion(),
										detail.getPattern_id(), detail.getScenario_no(), detail.getCycle(), detail.getPeristalsis());
								signal.getSchedule().add( scenario );
							}
							
							if(scenario.getPhase(signal.getSignal_id(), signal.getVersion(), detail.getPattern_id(), detail.getPhase()) == null){
								scenario.getPhase().add(new SignalPhase(signal.getSignal_id(), signal.getVersion(),
										detail.getPattern_id(), detail.getPhase(), detail.getState(), 
										detail.getDuration(), detail.getYellow_duration(), detail.getRed_duration()) );
							};		
						}
						result.add( new TrafficSignal(signal) );
					}
				}
			}
		} catch (Exception e) {
			log.error("selectNodeToSignal", e);
			return null;
		}
		return result;
	}
	
	/**
	 XML용 Signal 목록을 조회 한다.
 	 @param node Node 목록
 	 @param vo 요청VO
     @return List&lt;TrafficSignal&gt; XML용 신호목록
	*/
	public List<TrafficSignal> selectNodeToSignal(List<Node> node, RequestVO vo){
		// TODO : 노드 또는 버전을 통한 신호(Signal) 목록 조회
		/**
		 * 2019.06.07 Redkaras 일자 기준 변경에 따른 분기처리 추가 
		 */
		boolean sameDay = (vo.getFrom_date() == vo.getTo_date()) || (vo.getFrom_date().equals(vo.getTo_date()));
		if( sameDay ){
			return daySignal(node, vo);
		}else {
			return periodSignal(node, vo);
		}
	}
	
	private List<TrafficSignal> daySignal(List<Node> node, RequestVO vo){
		List<Signal> list = null;
		List<TrafficSignal> result = null;
		try {
			if(node == null || node.isEmpty()){
				list = session.getMapper(XmlMapper.class).selectVerSignal(vo.getVersion());
			}else{
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("list", node);
				map.put("req_date", vo.getReq_date());
				list = session.getMapper(XmlMapper.class).selectNodeToSignal(map);
			}
			
			if( list != null && !list.isEmpty()){
				result = new ArrayList<TrafficSignal>();
				Iterator<Signal> it = list.iterator();
				while( it.hasNext()){
					Signal signal = it.next();
					List<SignalDetail> detaillist = null;
					vo.setSignal_id( signal.getSignal_id() );
					vo.setVersion( signal.getVersion() );
					detaillist = session.getMapper(XmlMapper.class).selectTrafficSignalDetail(vo);
					if(detaillist == null || detaillist.isEmpty()){
						List<SignalTod> todList = session.getMapper(XmlMapper.class).selectSignalTodPlan(vo);
						if(todList != null && todList.size() == 1){
							detaillist = session.getMapper(XmlMapper.class).selectNoTimeTrafficSignalDetail(vo);
						}
					}
					if( detaillist != null && !detaillist.isEmpty()){
						Iterator<SignalDetail> detailIt = detaillist.iterator();
						SignalTod tod = null;
						SignalScenario scenario = null;
						while( detailIt.hasNext()){
							SignalDetail detail = detailIt.next();
							tod =  signal.getTod(signal.getSignal_id(), signal.getVersion(),
									detail.getPlan_id(), detail.getSeq(), detail.getPattern_id(), vo.getReq_date());
							scenario = signal.getSchedule(signal.getSignal_id(), signal.getVersion(),
									detail.getPattern_id(), detail.getScenario_no());
							
							if( tod == null ){
								tod = new SignalTod(signal.getSignal_id(), signal.getVersion(),
										detail.getPlan_id(), detail.getSeq(), detail.getPattern_id(),detail.getFrom_time(),
										detail.getTo_time(), detail.getStart_time(), detail.getCycle(), detail.getPeristalsis(), 
										vo.getReq_date(), vo.getFromTime(), vo.getToTime() );
								signal.getTod().add(tod);								
							}
							
							if( scenario == null){							
								scenario = new SignalScenario(signal.getSignal_id(), signal.getVersion(),
										detail.getPattern_id(), detail.getScenario_no(), detail.getCycle(), detail.getPeristalsis());
								signal.getSchedule().add( scenario );
							}
							
							if(scenario.getPhase(signal.getSignal_id(), signal.getVersion(), detail.getPattern_id(), detail.getPhase()) == null){
								scenario.getPhase().add(new SignalPhase(signal.getSignal_id(), signal.getVersion(),
										detail.getPattern_id(), detail.getPhase(), detail.getState(), 
										detail.getDuration(), detail.getYellow_duration(), detail.getRed_duration()) );
							};		
						}
						result.add( new TrafficSignal(signal) );
					}
				}
			}
		} catch (Exception e) {
			log.error("selectNodeToSignal daySignal", e);
			return null;
		}
		return result;
	}
	
	private List<TrafficSignal> periodSignal(List<Node> node, RequestVO vo){
		List<Signal> list = null;
		List<TrafficSignal> result = null;
		
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		String sFromDate = vo.getFrom_date();
		String sToDate = vo.getTo_date();
		String sFromTime = vo.getFromTime();
		String sToTime = vo.getToTime();
		long calDateDays = 0;
		
		try {
			Date fromDate = transFormat.parse(vo.getFrom_date());
			Date toDate = transFormat.parse(vo.getTo_date());
			c.setTime(fromDate);
			// Date로 변환된 두 날짜를 계산한 뒤 그 리턴값으로 long type 변수를 초기화 하고 있다.
	        // 연산결과 -950400000. long type.
	        long calDate = fromDate.getTime() - toDate.getTime();
	        // Date.getTime() 은 해당날짜를 기준으로1970년 00:00:00 부터 몇 초가 흘렀는지를 반환. 
	        //  24*60*60*1000(각 시간값에 따른 차이점) 을 나눠준다.
	        calDateDays = calDate / ( 24*60*60*1000);
	        calDateDays = Math.abs(calDateDays) + 1;
			
	        for(int i = 0; i < calDateDays; i++){	
				c.add(Calendar.DATE, i); //일 증가
				if( i == 0  ){
	        		vo.addPeriodDay( sFromDate, sFromTime, "235959");
	        	}else if( calDateDays -1 == i  ){
	        		vo.addPeriodDay( sToDate, "000000", sToTime);
	        	}else{
	        		vo.addPeriodDay( transFormat.format(c.getTime()), "000000", "235959");
	        	}
	        }
	        
			if(node == null || node.isEmpty()){
				list = session.getMapper(XmlMapper.class).selectVerSignal(vo.getVersion());
			}else{
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("list", node);
				map.put("req_date", vo.getReq_date());
				list = session.getMapper(XmlMapper.class).selectNodeToSignal(map);
			}
			
			if( list != null && !list.isEmpty()){
				result = new ArrayList<TrafficSignal>();
				Iterator<Signal> it = list.iterator();	
				while( it.hasNext()){
					Signal signal = it.next();					
		        	List<SignalDetail> detaillist = null;
					vo.setSignal_id( signal.getSignal_id() );
					vo.setVersion( signal.getVersion() );
					detaillist = session.getMapper(XmlMapper.class).selectTrafficSignalPeriodDetail(vo);
					if(detaillist == null || detaillist.isEmpty()){
						List<SignalTod> todList = session.getMapper(XmlMapper.class).selectSignalTodPlan(vo);
						if(todList != null && todList.size() == 1){
							detaillist = session.getMapper(XmlMapper.class).selectNoTimeTrafficSignalDetail(vo);
						}
					}
					
					if( detaillist != null && !detaillist.isEmpty()){
						Iterator<SignalDetail> detailIt = detaillist.iterator();
						SignalTod tod = null;
						SignalScenario scenario = null;
						while( detailIt.hasNext()){
							SignalDetail detail = detailIt.next();
							tod =  signal.getTod(signal.getSignal_id(), signal.getVersion(),
									detail.getPlan_id(), detail.getSeq(), detail.getPattern_id(), detail.getReq_date());
							scenario = signal.getSchedule(signal.getSignal_id(), signal.getVersion(),
									detail.getPattern_id(), detail.getScenario_no());
							
							if( tod == null ){
								tod = new SignalTod(signal.getSignal_id(), signal.getVersion(),
										detail.getPlan_id(), detail.getSeq(), detail.getPattern_id(),detail.getFrom_time(),
										detail.getTo_time(), detail.getStart_time(), detail.getCycle(), detail.getPeristalsis(), 
										detail.getReq_date(), vo.getFromTime(), vo.getToTime() );
								signal.getTod().add(tod);								
							}
							
							if( scenario == null){							
								scenario = new SignalScenario(signal.getSignal_id(), signal.getVersion(),
										detail.getPattern_id(), detail.getScenario_no(), detail.getCycle(), detail.getPeristalsis());
								signal.getSchedule().add( scenario );
							}
							
							if(scenario.getPhase(signal.getSignal_id(), signal.getVersion(), detail.getPattern_id(), detail.getPhase()) == null){
								scenario.getPhase().add(new SignalPhase(signal.getSignal_id(), signal.getVersion(),
										detail.getPattern_id(), detail.getPhase(), detail.getState(), 
										detail.getDuration(), detail.getYellow_duration(), detail.getRed_duration()) );
							};		
						}
						result.add( new TrafficSignal(signal) );
					}
				}
			}
		} catch (Exception e) {		
			log.error("selectNodeToSignal periodSignal", e);
			return null;
		}
		return result;
	}
	
	private List<TrafficSignal> getSignalDetail(RequestVO vo, Signal signal) throws Exception{
		List<TrafficSignal> result =  new ArrayList<TrafficSignal>();
		List<SignalDetail> detaillist = null;
		vo.setSignal_id( signal.getSignal_id() );
		vo.setVersion( signal.getVersion() );
		
		detaillist = session.getMapper(XmlMapper.class).selectTrafficSignalDetail(vo);
		if(detaillist == null || detaillist.isEmpty()){
			//일수만큼 돌면서 
			List<SignalTod> todList = session.getMapper(XmlMapper.class).selectSignalTodPlan(vo);
			if(todList != null && todList.size() == 1){
				detaillist = session.getMapper(XmlMapper.class).selectNoTimeTrafficSignalDetail(vo);
			}
		}
		
		if( detaillist != null && !detaillist.isEmpty()){
			Iterator<SignalDetail> detailIt = detaillist.iterator();
			SignalTod tod = null;
			SignalScenario scenario = null;
			while( detailIt.hasNext()){
				SignalDetail detail = detailIt.next();
				tod =  signal.getTod(signal.getSignal_id(), signal.getVersion(),
						detail.getPlan_id(), detail.getSeq(), detail.getPattern_id(), vo.getReq_date());
				scenario = signal.getSchedule(signal.getSignal_id(), signal.getVersion(),
						detail.getPattern_id(), detail.getScenario_no());
				
				if( tod == null ){
					tod = new SignalTod(signal.getSignal_id(), signal.getVersion(),
							detail.getPlan_id(), detail.getSeq(), detail.getPattern_id(),detail.getFrom_time(),
							detail.getTo_time(), detail.getStart_time(), detail.getCycle(), detail.getPeristalsis(), 
							vo.getReq_date(), vo.getFromTime(), vo.getToTime() );
					signal.getTod().add(tod);								
				}
				
				if( scenario == null){							
					scenario = new SignalScenario(signal.getSignal_id(), signal.getVersion(),
							detail.getPattern_id(), detail.getScenario_no(), detail.getCycle(), detail.getPeristalsis());
					signal.getSchedule().add( scenario );
				}
				
				if(scenario.getPhase(signal.getSignal_id(), signal.getVersion(), detail.getPattern_id(), detail.getPhase()) == null){
					scenario.getPhase().add(new SignalPhase(signal.getSignal_id(), signal.getVersion(),
							detail.getPattern_id(), detail.getPhase(), detail.getState(), 
							detail.getDuration(), detail.getYellow_duration(), detail.getRed_duration()) );
				};		
			}
			result.add( new TrafficSignal(signal) );
		}
		return result;
	}
	
	/**
	 파티션번호를 조회 한다.
 	 @param vo 요청VO
     @return String; 파티션번호
	*/
	public String GetPartitionNo(RequestVO vo){
		// TODO : 요청 파라미터를 통한 파티션 번호 조회
		try {
			return session.getMapper(XmlMapper.class).GetPartitionNo(vo);
		} catch (Exception e) {
			log.error("GetPartitionNo", e);
		}
		return null;
	}
	/**
	 궤적 목록을 조회 한다.
	 @param vo 요청VO
     @return List&lt;VehicleRoute&gt; XML용 궤적목록
	*/
	public List<VehicleRoute> selectRoute(RequestVO vo){
		// TODO : 요청 파라미터를 통한 궤적 목록 조회
		try {
			return session.getMapper(XmlMapper.class).selectRoute(vo);
		} catch (Exception e) {
			log.error("GetPartitionNo", e);
		}
		return null;
	}
	/**
	 Node 목록을 조회 한다.
     @param vo 요청VO
     @return List&lt;Node&gt; Node 목록
	*/
	public List<Node> RegionNode(RequestVO vo) {
		try {
			return session.getMapper(XmlMapper.class).RegionNode(vo);
		} catch (Exception e) {
			log.error("RegionNode", e);
		}
		return null;	
	}
	/**
	  Node 목록을 조회 한다.
	  @param vo 요청VO
	  @return List&lt;Node&gt; Node 목록
	*/
	public List<Node> CoordinateNode(RequestVO vo) {
		try {
			return session.getMapper(XmlMapper.class).CoordinateNode(vo);
		} catch (Exception e) {
			log.error("CoordinateNode", e);
		}
		return null;	
	}
}
