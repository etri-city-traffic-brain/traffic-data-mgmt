package net.etri.rest.api.sumo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.etri.rest.api.repository.RequestVO;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;

import net.etri.rest.api.cmm.AbstractServiceImpl;
import net.etri.rest.api.sumo.service.Connection;
import net.etri.rest.api.sumo.service.SumoService;
import net.etri.rest.api.sumo.service.TlLogic;

/**
 * <p>클래스 설명</p>
 * <p>SumoServiceImpl</p>
 * <pre>
 *  net.etri.rest.api.sumo.service.impl
 *         └ SumoServiceImpl.java
 * </pre>
 *
 **/
@Service("sumoService")
public class SumoServiceImpl extends AbstractServiceImpl implements SumoService {

	@Resource(name="sumoDao")
	private SumoDao dao;
	
	@Override
	public List<TlLogic> selectTlLogic(RequestVO vo) throws NotFoundException {
		
		return dao.selectTlLogic(vo);
	}

	@Override
	public List<Connection> selectTLtoConnection(List<TlLogic> tls) {
	
		return dao.selectTLtoConnection(tls);
	}

	@Override
	public List<Connection> selectConnection(RequestVO vo) {
	
		return dao.selectConnection(vo);
	}

}
