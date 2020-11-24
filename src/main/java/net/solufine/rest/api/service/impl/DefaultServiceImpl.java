/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.service.impl
 * @file DefaultServiceImpl.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일				수정자			수정내용
 * -------------	------		-------------------------------
 * 2018. 5. 23.		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.solufine.rest.api.cmm.AbstractServiceImpl;
import net.solufine.rest.api.repository.CodeVO;
import net.solufine.rest.api.repository.DongVO;
import net.solufine.rest.api.repository.RequestVO;
import net.solufine.rest.api.repository.SiguVO;
import net.solufine.rest.api.service.DefaultService;
import net.solufine.rest.api.xml.Phase;

/**
  <p>클래스 설명 : 기본정보 서비스 구현 클래스</p>
  <p>DefaultServiceImpl</p>
  <pre>
   net.solufine.rest.api.service.impl
          └ DefaultServiceImpl.java
  </pre>
  @author  redkaras 
  @since  2018. 5. 23.
  @version 1.0
**/
@Service("defaultService")
public class DefaultServiceImpl extends AbstractServiceImpl implements DefaultService {
	
	@Resource(name="defaultDao")
	private DefaultDao dao;
	//@Value("${default.sigu}")
	//private String defaultSigu;
	
	/**
	  구정보조회
	 @param value 조회 조건
     @return List&lt;SiguVO&gt; 시구VO 목록
	*/
	@Override
	public List<SiguVO> selectSigu(String value) {
		return dao.selectSigu(value);
	}
	/**
	 동정보조회
 	 @param value 조회 조건
     @return List&lt;DongVO&gt; 동코드VO 목록
	*/
	@Override
	public List<DongVO> selectDong(String value) {
		return dao.selectDong(value);
	}
	/**
	  코드정보 목록조회
	 @param value 조회대상 코드그룹
     @return List&lt;CodeVO&gt; 코드정보VO객체 목록 반환   
	*/
	@Override
	public List<CodeVO> selectCode(String value){
		return dao.selectCode(value);
	}	
	/**
	 XML 요청 이력을 조회한다.
 	 @param vo 요청VO
     @return List&lt;RequestVO&gt; 요청VO 목록
	*/
	@Override
	public List<RequestVO> selecteRequstHistory(RequestVO vo) {
		return dao.selecteRequstHistory(vo);
	}
	/**
	  기본시구코드 반환
     @return String 기본시구코드
	
	@Override
	public String getDefaultSigu() {
		return defaultSigu;
	}
	*/
	/**
	 XML 요청 이력 개수조회
     @return int 개수
	*/
	@Override
	public int selecteRequstHistoryCount() {
		return dao.selecteRequstHistoryCount();
	}
	/**
	  파티션NO별 파티션 개수목록을 반환
	 @param value 시구코드
	 @return List&lt;String&gt;	 
	*/
	@Override
	public List<String> selectePartitionCount(String value){
		List<String> cntList = new ArrayList<String>();
		cntList.add("1");
		
		List<HashMap<String, String>> partition_cnt = dao.selectePartitionCount(value);
		if(partition_cnt != null){
			for(int i = 0; i < partition_cnt.size(); i++){
				cntList.add(String.valueOf(partition_cnt.get(i).get("PARTITION_CNT")));
			}
		}

		return cntList; 
	}
	/**
	  신호버전 목록을 반환
	 @return List&lt;String&gt;	 신호버전 목록
	*/
	@Override
	public List<String> selecteSignalVersion(){
		return dao.selecteSignalVersion();
	}
	/**
	  신호현시 상태가 계속 R인것만 추출
	 @return List&lt;String&gt;	  신호현시 상태가 계속 R인것만 추출
	*/
	public List<String> getAllR(){
		List<Phase> phaseList = dao.selectPhase();		
		List<String> result = new ArrayList<String>();
		Iterator<Phase> it = phaseList.iterator();
		String id = "";
		List<Phase> idList = new ArrayList<Phase>();
		while(it.hasNext()){
			Phase p = it.next();
			if( id == "" ){
				id = p.getId();				
				idList.add(p);
			}else if( id == p.getId() || id.equals(p.getId()) ){
				idList.add(p);
			}else{				
				String[][] sList = new String[idList.size()][idList.get(0).getState().length()];
				for(int i = 0; i < idList.size(); i++){
					for(int c = 0; c < idList.get(i).getState().length(); c++ ){
						sList[i][c] = String.valueOf(idList.get(i).getState().toLowerCase().charAt(c));
					}
				}
				
				for(int k = 0; k < idList.size(); k++ ){
					for(int c = 0; c < sList[k].length; c++ ){
						boolean allR = true;
						for(int i = 0; i < sList.length; i++){
							if( !sList[i][c].equals("r")){
								allR = false;
								break;
							}	
						}
						if(allR){
							if( !result.contains(idList.get(k).getId()))
								result.add(idList.get(k).getId());
							break;
						}
					}
				}
				
				id = p.getId();				
				idList.clear();
				idList.add(p);
			}			
		}
		
		return result;
	}
}
