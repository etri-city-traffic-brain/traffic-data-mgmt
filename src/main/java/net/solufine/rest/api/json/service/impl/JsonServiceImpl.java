/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.json.service.impl
 * @file JsonServiceImpl.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일			버전			수정자		수정내용
 * -------------	--------	------		-------------------------------
 * 2019. 6. 21.		0.0.1		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.json.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.solufine.rest.api.cmm.AbstractServiceImpl;
import net.solufine.rest.api.json.service.Connection;
import net.solufine.rest.api.json.service.JsonService;
import net.solufine.rest.api.json.service.Signal;
import net.solufine.rest.api.json.service.TrafficSignal;

/**
 * <p>클래스 설명</p>
 * <p>JsonServiceImpl</p>
 * <pre>
 *  net.solufine.rest.api.json.service.impl
 *         └ JsonServiceImpl.java
 * </pre>
 *
 @author  redkaras 
 @since 0.0.1
 **/
@Service("jsonService")
public class JsonServiceImpl extends AbstractServiceImpl implements JsonService {

	@Resource(name="jsonDao")
	private JsonDao dao;
	
	@Override
	public int updateSignal(TrafficSignal trafficSignal) {		
		return dao.updateSignal(trafficSignal);
	}

	@Override
	public TrafficSignal getSignal(String signal_id, String version) {
		return dao.getSignal(signal_id, version);
	}

	@Override
	public List<Signal> getSignalInfo(String signal_id) {
		return dao.getSignalInfo(signal_id);
	}

	@Override
	public List<Connection> getConnection(String junction_id) {
		return dao.getConnection(junction_id);
	}

	@Override
	public void deleteSignal(String signal_id, String version) {
		dao.deleteSignal(signal_id, version);
	}

}
