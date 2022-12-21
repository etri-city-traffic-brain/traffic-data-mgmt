package net.etri.rest.api.sumo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.etri.rest.api.repository.RequestVO;
import net.etri.rest.api.xml.type.TlLogicType;
import org.apache.ibatis.javassist.NotFoundException;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.etri.rest.api.mapper.SumoMapper;
import net.etri.rest.api.sumo.service.Connection;
import net.etri.rest.api.sumo.service.TlLogic;

/**
 * <p>클래스 설명</p>
 * <p>SumoDao</p>
 * <pre>
 *  net.etri.rest.api.sumo.service.impl
 *         └ SumoDao.java
 * </pre>
 *
 **/
@Repository("sumoDao")
@Transactional(transactionManager="txManager")
public class SumoDao {

	Logger log = LoggerFactory.getLogger(SumoDao.class);
	@Autowired
	private SqlSession session;
	
	/**
	 TlLogic 목록을 조회 한다.
	 @param vo 요청VO
     @return List&lt;TlLogic&gt; TLogic 목록
	 @throws NotFoundException 
	*/
	public List<TlLogic> selectTlLogic(RequestVO vo) throws NotFoundException{
		List<TlLogic> result = new ArrayList<TlLogic>();
		
		HashMap<String, TlLogic> tllogic = new HashMap<String, TlLogic>();
		List<HashMap<String, Object>> tls = session.getMapper(SumoMapper.class).selectTlLogic(vo);
		if( tls == null || tls.isEmpty())
			throw new NotFoundException("TlLogic data not found");
		
		for(int i = 0; i < tls.size(); i++) {
			HashMap<String, Object> tl = tls.get(i);
			TlLogic logic = null;
			String id = String.valueOf( tl.get("ID") );
			String programId = String.valueOf( tl.get("PROGRAMID") );
			String tlType = String.valueOf( tl.get("TLLOGICTYPE") );
			String offset = String.valueOf( tl.get("OFFSET") );
			String seq = String.valueOf( tl.get("SEQ") );
			
			String yellow = "0";
			if( tl.get("YELLOW_DURATION") != null)
				yellow = String.valueOf( tl.get("YELLOW_DURATION") );
			
			String red = "0";
			if( tl.get("RED_DURATION") != null)	
				red = String.valueOf( tl.get("RED_DURATION") );
			
			String duration = String.valueOf( tl.get("DURATION") );
			String state = String.valueOf( tl.get("STATE") );
			
			if( tllogic.containsKey(id)) {
				logic = tllogic.get(id);
			}else {
				logic = new TlLogic();
				logic.setId(id);
				logic.setProgramID( Integer.valueOf(programId) );
				logic.setTlLogicType(TlLogicType.get(tlType));
				logic.setOffset( Integer.valueOf(offset) );
				tllogic.put(id, logic);
			}
			logic.addPhases(Integer.valueOf(seq), Integer.valueOf(duration), 
					Integer.valueOf(yellow), Integer.valueOf(red), state);
			
		}
	
		Iterator<String> it = tllogic.keySet().iterator();
		while( it.hasNext() ) {
			TlLogic logic = tllogic.get(it.next());
			logic.calcPhases();
			result.add( logic );
		}
		
		return result;
	}
	
	/**
	 Connection 목록을 조회 한다.
	 @param List&lt;TlLogic&gt; TLogic 목록
     @return List&lt;Connection&gt; Connection 목록
	*/
	public List<Connection> selectTLtoConnection(List<TlLogic> tls) {
		return session.getMapper(SumoMapper.class).selectTLtoConnection(tls);
	}
	
	/**
	 Connection 목록을 조회 한다.
	 @param vo 요청VO
     @return List&lt;Connection&gt; 커넥션 목록
	*/
	public List<Connection> selectConnection(RequestVO vo){
		return session.getMapper(SumoMapper.class).selectConnection(vo);
	}
}
