package net.etri.rest.api.service.impl;

import java.util.HashMap;
import java.util.List;

import net.etri.rest.api.mapper.DefaultMapper;
import net.etri.rest.api.repository.RequestVO;
import net.etri.rest.api.xml.Phase;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.etri.rest.api.repository.CodeVO;
import net.etri.rest.api.repository.DongVO;
import net.etri.rest.api.repository.SiguVO;

/**
  <p>클래스 설명 : 기본정보 데이터 처리 클래스</p>
  <p>DefaultDao</p>
  <pre>
   net.etri.rest.api.service.impl
          └ DefaultDao.java
  </pre>
**/
@Repository("defaultDao")
public class DefaultDao {

	Logger log = LoggerFactory.getLogger(DefaultDao.class);
			
	@Autowired
	private SqlSession  session;
	
	/**
	  구정보조회
	 @param value 조회 조건
   	 @return List&lt;SiguVO&gt; 시구VO 목록
	*/
	public List<SiguVO> selectSigu(String value){
		try {	
 			return session.getMapper(DefaultMapper.class).selectSigu(value);
		} catch (Exception e) {
			log.error("selectSigu", e);
		}
		return null;
	}
	/**
	 동정보조회
 	 @param value 조회 조건
     @return List&lt;DongVO&gt; 동코드VO 목록
	*/
	public List<DongVO> selectDong(String value){
		try {
			return session.getMapper(DefaultMapper.class).selectDong(value);
		} catch (Exception e) {
			log.error("selectDong", e);
		}
		return null;
	}
	/**
	  코드정보 목록조회
	 @param value 조회대상 코드그룹
     @return List&lt;CodeVO&gt; 코드정보VO객체 목록 반환   
	*/
	public List<CodeVO> selectCode(String value){
		try {
			return session.getMapper(DefaultMapper.class).selectCode(value);
		} catch (Exception e) {
			log.error("selectCode", e);
		}
		return null;
	}
	/**
	 XML 요청 이력을 조회한다.
 	 @param vo 요청VO
     @return List&lt;RequestVO&gt; 요청VO 목록
	*/
	public List<RequestVO> selecteRequstHistory(RequestVO vo) {
		try {
			return session.getMapper(DefaultMapper.class).selecteRequstHistory(vo);
		} catch (Exception e) {
			log.error("selecteRuestHistory", e);
		}
		return null;
	}
	/**
	 XML 요청 이력 개수조회
     @return int 개수
	*/
	public int selecteRequstHistoryCount(){
		try {
			return session.getMapper(DefaultMapper.class).selecteRequstHistoryCount();
		} catch (Exception e) {
			log.error("selecteRuestHistory", e);
		}
		return 0;
	}
	/**
	  파티션NO별 파티션 개수목록을 반환
   	 @param value 시구코드
   	 @return List&lt;HashMap&lt;String&#44; String&gt;&gt;	  
	*/
	public List<HashMap<String, String>> selectePartitionCount(String value){
		try {
			return session.getMapper(DefaultMapper.class).selectePartitionCount(value);
		} catch (Exception e) {
			log.error("selectePartitionCount", e);
		}
		return null;	
	}
	/**
	  신호버전 목록을 반환
	 @return List&lt;String&gt;	 신호버전 목록
	*/
	public List<String> selecteSignalVersion(){
		try {
			return session.getMapper(DefaultMapper.class).selecteSignalVersion();
		} catch (Exception e) {
			log.error("selecteSignalVersion", e);
		}
		return null;	
	}
	/**
	  신호현시 전체목록
	 @return List&lt;Phase&gt;	신호현시 전체목록
	*/
	public List<Phase> selectPhase(){
		try {
			return session.getMapper(DefaultMapper.class).selectPhase();
		} catch (Exception e) {
			log.error("selecteSignalVersion", e);
		}
		return null;	
	}
}
