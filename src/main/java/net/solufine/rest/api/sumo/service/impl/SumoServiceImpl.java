/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.sumo.service.impl
 * @file SumoServiceImpl.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일			버전			수정자		수정내용
 * -------------	--------	------		-------------------------------
 * 2019. 6. 21.		0.0.1		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.sumo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;

import net.solufine.rest.api.cmm.AbstractServiceImpl;
import net.solufine.rest.api.repository.RequestVO;
import net.solufine.rest.api.sumo.service.Connection;
import net.solufine.rest.api.sumo.service.SumoService;
import net.solufine.rest.api.sumo.service.TlLogic;

/**
 * <p>클래스 설명</p>
 * <p>SumoServiceImpl</p>
 * <pre>
 *  net.solufine.rest.api.sumo.service.impl
 *         └ SumoServiceImpl.java
 * </pre>
 *
 @author  redkaras 
 @since 0.0.1
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
