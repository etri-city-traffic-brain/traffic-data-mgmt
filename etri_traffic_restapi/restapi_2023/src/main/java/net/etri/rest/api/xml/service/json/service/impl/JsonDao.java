package net.etri.rest.api.xml.service.json.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.etri.rest.api.xml.service.json.service.Connection;
import net.etri.rest.api.xml.service.json.service.TrafficSignal;
import net.etri.rest.api.xml.service.json.service.Signal;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.etri.rest.api.mapper.JsonMapper;
import net.etri.rest.api.xml.service.SignalTod;

/**
 * <p>클래스 설명</p>
 * <p>JsonDao</p>
 * <pre>
 *  net.etri.rest.api.json.service.impl
 *         └ JsonDao.java
 * </pre>
 *
 **/
@Repository("jsonDao")
@Transactional(transactionManager="txManager")
public class JsonDao {

	Logger log = LoggerFactory.getLogger(JsonDao.class);
	/**
	 myBatis-Spring을 사용하는 경우, MyBatis의 SqlSessionTemplate 객체는 commit(), rollback() 메소드를 사용할 수 없다. 
	 즉 SqlSessionTemplate 객체를 이용하여 프로그램적으로는 트랜잭션을 관리할 수 없게 한다. 
	 억지로 SqlSessionTemplate 객체의 commit() 메소드를 호출하면 다음과 같은 예외를 발생한다.​
	java.lang.UnsupportedOperationException: Manual commit is not allowed over a Spring managed SqlSession
	 */
	@Autowired
	private SqlSession session;
	
	public int updateSignal(TrafficSignal trafficSignal) {
		int newVersion = session.getMapper(JsonMapper.class).getEditVersion(trafficSignal.getSignal_id());
		trafficSignal.setVersion( String.valueOf(newVersion) );
		
		if( trafficSignal.getEdit_date() == null || trafficSignal.getEdit_date().isEmpty() ) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			trafficSignal.setEdit_date( sdf.format(new Date()) );
		}
		
		insertSignal(trafficSignal);
		insertSignalPhaseDefault(trafficSignal);
		insertSignalPhase(trafficSignal);
		insertSignalTodPlan(trafficSignal);
		insertSignalScenario(trafficSignal);
		
		return 1;
	}
	
	private int insertSignal(TrafficSignal trafficSignal) {
		return session.getMapper(JsonMapper.class).insertSignal(trafficSignal); 
	}
	
	private int insertSignalPhaseDefault(TrafficSignal trafficSignal) {
		if( trafficSignal.getSignalPhaseDefault() == null ||
				trafficSignal.getSignalPhaseDefault().isEmpty())
			return 0;
		
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		for( int i = 0; i < trafficSignal.getSignalPhaseDefault().size(); i++) {
			HashMap<String, String> param = new HashMap<String, String>();
			param.put("signal_id", trafficSignal.getSignal_id());
			param.put("version", trafficSignal.getVersion());
			param.put("signal_type", trafficSignal.getSignalPhaseDefault().get(i).getSignal_type() );
			String[] tm = trafficSignal.getSignalPhaseDefault().get(i).getTm().split(":");
			for( int c = 0; c < 6; c++) {
				if( tm.length > c ) {
					param.put("tm" + (c + 1), tm[c]);
				}else {
					param.put("tm" + (c + 1), "0");
				}
			}
			list.add(param);
		}
		return session.getMapper(JsonMapper.class).insertSignalPhaseDefault(list); 
	}
	
	private int insertSignalPhase(TrafficSignal trafficSignal) {
		if( trafficSignal.getSignalPhase() == null ||
				trafficSignal.getSignalPhase().isEmpty())
			return 0;
		
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		for( int i = 0; i < trafficSignal.getSignalPhase().size(); i++) {
			HashMap<String, String> param = new HashMap<String, String>();
			param.put("signal_id", trafficSignal.getSignal_id());
			param.put("version", trafficSignal.getVersion());
			param.put("phase", trafficSignal.getSignalPhase().get(i).getPhase());
			param.put("state", trafficSignal.getSignalPhase().get(i).getState());
			list.add(param);
		}
		
		return session.getMapper(JsonMapper.class).insertSignalPhase(list); 
	}

//	private int insertSignalTodPlan(TrafficSignal trafficSignal) {
//		if( trafficSignal.getTodPlan() == null ||
//				trafficSignal.getTodPlan().isEmpty())
//			return 0;
//		
//		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
//		for( int i = 0; i < trafficSignal.getTodPlan().size(); i++) {
//			if( trafficSignal.getTodPlan().get(i).getTods() == null ||
//					trafficSignal.getTodPlan().get(i).getTods().isEmpty())
//				continue;
//			
//			for( int c = 0; c < trafficSignal.getTodPlan().get(i).getTods().size(); c++) {
//				HashMap<String, String> param = new HashMap<String, String>();
//				param.put("signal_id", trafficSignal.getSignal_id());
//				param.put("version", trafficSignal.getVersion());
//				param.put("plan_id", trafficSignal.getTodPlan().get(i).getPlan_id() );
//			
//				param.put("seq", String.valueOf(trafficSignal.getTodPlan().get(i).getTods().get(c).getSeq()) );
//				param.put("pattern_id", String.valueOf(trafficSignal.getTodPlan().get(i).getTods().get(c).getPattern_id()) );
//				param.put("from_time", trafficSignal.getTodPlan().get(i).getTods().get(c).getFrom_time() + ":00" );
//				param.put("to_time", trafficSignal.getTodPlan().get(i).getTods().get(c).getTo_time() + ":00" );
//				
//				list.add(param);
//			}			
//		}
//		
//		return session.getMapper(JsonMapper.class).insertSignalTodPlan(list); 
//	}
	
	private int insertSignalScenario(TrafficSignal trafficSignal) {
		if( trafficSignal.getSignalScenario() == null ||
				trafficSignal.getSignalScenario().isEmpty())
			return 0;
		
		
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		List<HashMap<String, String>> list2 = new ArrayList<HashMap<String, String>>();
		
		for( int i = 0; i < trafficSignal.getSignalScenario().size(); i++) {
			HashMap<String, String> param = new HashMap<String, String>();
			param.put("signal_id", trafficSignal.getSignal_id());
			param.put("version", trafficSignal.getVersion());			
			param.put("pattern_id", trafficSignal.getSignalScenario().get(i).getPattern_id() );
			param.put("scenario_no", trafficSignal.getSignalScenario().get(i).getScenario_no() );
			param.put("cycle", trafficSignal.getSignalScenario().get(i).getCycle() );
			param.put("peristalsis", trafficSignal.getSignalScenario().get(i).getPeristalsis() );
			
			list.add(param);
			
			String[] phase = trafficSignal.getSignalScenario().get(i).getPhase().split(":");
			for( int c = 0; c < phase.length; c++) {
				HashMap<String, String> param2 = new HashMap<String, String>();
				param2.put("signal_id", trafficSignal.getSignal_id());
				param2.put("version", trafficSignal.getVersion());		
				param2.put("pattern_id", trafficSignal.getSignalScenario().get(i).getPattern_id() );
				param2.put("phase", String.valueOf(c + 1));
				param2.put("duration", phase[c]);
				list2.add(param2);
			}
		}			
		
		return session.getMapper(JsonMapper.class).insertSignalScenario(list) + 
			   session.getMapper(JsonMapper.class).insertSignalScenarioPhase(list2);
	}

	/**
	 신호데이터를 조회 한다.
	 @param signal_id 신호ID
	 @param version 버전
     @return TrafficSignal 신호데이터 반환
	*/
	public TrafficSignal getSignal(String signal_id, String version) {
		TrafficSignal traffic = null;
		try {
			traffic = session.getMapper(JsonMapper.class).getSignal(signal_id, version);
			if( traffic == null)
				return null;
			
			traffic.setSignalPhaseDefault(session.getMapper(JsonMapper.class).getSignalTime(signal_id, version) );
			traffic.setSignalPhase(session.getMapper(JsonMapper.class).getSignalPhase(signal_id, version));
			
			List<SignalTod> tod = session.getMapper(JsonMapper.class).getSignalTodPlan(signal_id, version);
			if( tod != null && !tod.isEmpty()) {
				Iterator<SignalTod> it = tod.iterator();
				while( it.hasNext() ) {
					SignalTod st = it.next();
					traffic.addTodPlan(st.getPlan_id(), st.getSeq(), st.getPattern_id(), st.getFrom_time(), st.getTo_time());					
				}
			}
			
			traffic.setSignalScenario(session.getMapper(JsonMapper.class).getSignalScenario(signal_id, version));
			
			return traffic;
		} catch (Exception e) {
			log.error("getSignalInfo", e);
		}
		return null;
	}
	/**
	 신호목록을 조회 한다.
	 @param signal_id 검색조건
	 @param version 버전
    @return List&lt;Signal&gt; 신호데이터 반환
	*/
	public List<Signal> getSignalInfo(String signal_id) {
		// TODO : JSON용 신호정보 조회
		try {
			return session.getMapper(JsonMapper.class).getSignalInfo(signal_id);
		} catch (Exception e) {
			log.error("getSignalInfo", e);
		}
		return null;
	}
	
	/**
	 커넥션목록을 조회 한다.
	 @param junction_id 검색조건
	 @param version 버전
   	 @return List&lt;Connection&gt; 커넥션데이터 반환
	*/
	public List<Connection> getConnection(String junction_id){
		// TODO : JSON용 커넥션정보 조회
		try {
			return session.getMapper(JsonMapper.class).getConnection(junction_id);
		} catch (Exception e) {
			log.error("getConnection", e);
		}
		return null;
	}

	/**
	 신호데이터를 삭제 한다.
	 @param signal_id 신호ID
	 @param version 버전    	
	*/
	public void deleteSignal(String signal_id, String version) {
		// TODO : 신호데이터를 삭제
		session.getMapper(JsonMapper.class).deleteSpecialDay(signal_id, version);
		session.getMapper(JsonMapper.class).deletePhaseSignalTime(signal_id, version);
		session.getMapper(JsonMapper.class).deleteTodPlan(signal_id, version);
		session.getMapper(JsonMapper.class).deleteSignalScenarioPhase(signal_id, version);
		session.getMapper(JsonMapper.class).deleteSignalScenario(signal_id, version);
		session.getMapper(JsonMapper.class).deleteSignalPhase(signal_id, version);
		session.getMapper(JsonMapper.class).deleteSignal(signal_id, version);
	}

	

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	private int insertSignalTodPlan(TrafficSignal trafficSignal) {
		if( trafficSignal.getTodPlan() == null ||
				trafficSignal.getTodPlan().isEmpty())
			return 0;
		
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		for( int i = 0; i < trafficSignal.getTodPlan().size(); i++) {
			if( trafficSignal.getTodPlan().get(i).getTods() == null ||
					trafficSignal.getTodPlan().get(i).getTods().isEmpty())
				continue;
			
			for( int c = 0; c < trafficSignal.getTodPlan().get(i).getTods().size(); c++) {
				HashMap<String, String> param = new HashMap<String, String>();
				param.put("signal_id", trafficSignal.getSignal_id());
				param.put("version", trafficSignal.getVersion());
				param.put("plan_id", trafficSignal.getTodPlan().get(i).getPlan_id() );
			
				param.put("seq", String.valueOf(trafficSignal.getTodPlan().get(i).getTods().get(c).getSeq()) );
				param.put("pattern_id", String.valueOf(trafficSignal.getTodPlan().get(i).getTods().get(c).getPattern_id()) );
				param.put("from_time", trafficSignal.getTodPlan().get(i).getTods().get(c).getFrom_time());
				param.put("to_time", trafficSignal.getTodPlan().get(i).getTods().get(c).getTo_time());
				
				list.add(param);
			}			
		}
		
		return session.getMapper(JsonMapper.class).insertSignalTodPlan(list); 
	}

}
