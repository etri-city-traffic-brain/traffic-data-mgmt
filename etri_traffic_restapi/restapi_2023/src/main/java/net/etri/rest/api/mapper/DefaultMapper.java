package net.etri.rest.api.mapper;

import java.util.HashMap;
import java.util.List;

import net.etri.rest.api.repository.RequestVO;
import net.etri.rest.api.xml.Phase;
import net.etri.rest.api.cmm.Mapper;
import net.etri.rest.api.repository.CodeVO;
import net.etri.rest.api.repository.DongVO;
import net.etri.rest.api.repository.FileVO;
import net.etri.rest.api.repository.SiguVO;

/**
  <p>클래스 설명 : 기본정보 처리를 위한 맵퍼 인터페이스</p>
  <p>DefaultMapper</p>
  <pre>
   net.etri.rest.api.mapper
          └ DefaultMapper.java
  </pre>
 **/
@Mapper("defaultMapper")
public interface DefaultMapper {
	/**
	   구정보조회
      @param value 조회대상 코드값
      @return List&lt;SiguVO&gt; 시구VO객체 목록 반환
      @exception Exception 예외발생
	*/
	public List<SiguVO> selectSigu(String value) throws Exception;
	/**
	   동정보조회
  	  @param value 조회대상 코드값
      @return List&lt;DongVO&gt; 동코드VO객체 목록 반환
      @exception Exception 예외발생
	*/
	public List<DongVO> selectDong(String value) throws Exception;
	/**
	  코드정보 목록조회
	 @param value 조회대상 코드그룹
     @return List&lt;CodeVO&gt; 코드정보VO객체 목록 반환
     @exception Exception 예외발생
	*/
	public List<CodeVO> selectCode(String value) throws Exception;
	/**
	   파일업로드 이력을저장
   	  @param vo 파일VO객체
 	  @exception Exception 예외발생
	*/
	public void insertUploadHistory(FileVO vo) throws Exception;
	/**
	  XML 요청 이력을저장
  	  @param vo 요청VO객체
 	  @exception Exception 예외발생
	*/
	public void insertRequestHistory(RequestVO vo) throws Exception;
	/**
	  XML 요청 이력을 조회한다.
  	  @param vo 요청VO객체
      @return List&lt;RequestVO&gt; 요청VO 목록 반환
 	  @exception Exception 예외발생
	*/
	public List<RequestVO> selecteRequstHistory(RequestVO vo) throws Exception;
	/**
	  XML 요청 이력 개수조회
      @return int 요청이력개수 반환
 	  @exception Exception 예외발생
	*/
	public int selecteRequstHistoryCount() throws Exception;
	/**
	  파티션NO별 파티션 개수목록을 반환
     @param value 시구코드
     @return List&lt;HashMap&lt;String&#44; String&gt;&gt; 파티션개수 목록 반환
	 @exception Exception 예외발생
	*/
	public List<HashMap<String, String>> selectePartitionCount(String value) throws Exception;
	/**
	  신호버전 목록을 반환
	 @return List&lt;String&gt;	 신호버전 목록
	 @exception Exception 예외발생
	*/
	public List<String> selecteSignalVersion() throws Exception;
	/**
	  신호현시 전체목록
	 @return List&lt;Phase&gt;	신호현시 전체목록
	 @exception Exception 예외발생
	*/
	public List<Phase> selectPhase() throws Exception;
}
