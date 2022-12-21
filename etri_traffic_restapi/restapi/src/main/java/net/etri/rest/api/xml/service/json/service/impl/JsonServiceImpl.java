package net.etri.rest.api.xml.service.json.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.etri.rest.api.xml.service.json.service.Connection;
import net.etri.rest.api.xml.service.json.service.TrafficSignal;
import net.etri.rest.api.xml.service.json.service.JsonService;
import net.etri.rest.api.xml.service.json.service.Signal;
import org.springframework.stereotype.Service;

import net.etri.rest.api.cmm.AbstractServiceImpl;

/**
 * <p>클래스 설명</p>
 * <p>JsonServiceImpl</p>
 * <pre>
 *  net.etri.rest.api.json.service.impl
 *         └ JsonServiceImpl.java
 * </pre>
 *
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
