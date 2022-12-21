package net.etri.rest.api;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.etri.rest.api.xml.service.XmlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.etri.rest.api.xml.service.json.service.JsonService;
import net.etri.rest.api.repository.DongVO;
import net.etri.rest.api.service.DefaultService;

/**
  <p>클래스 설명 : 객체를 JSON형식으로 변환하는 REST컨트롤러 클래스</p>
  <p>DefaultJsonController</p>
  <pre>
   net.etri.rest.api
          └ DefaultJsonController.java
  </pre>
 **/
@RestController("defaultJsonController")
public class DefaultJsonController {
	Logger log = LoggerFactory.getLogger(DefaultJsonController.class);
	@Resource(name="defaultService")
	private DefaultService service;
	
	@Resource(name="xmlService")
	private XmlService xmlService;
	
	@Resource(name="jsonService")
	private JsonService jsonService;
	
	@Value("#{props['default.sigu']}")
	private String sigu;
	
	/**
	   동코드 목록 JSON 반환
	  @param sigu_cd 검색대상 시구코드
      @return List&lt;DongVO&gt; 검색된 동코드VO목록 반환
	 */
	@RequestMapping(value="/getDong", method=RequestMethod.POST, produces={"application/json"})	
	public List<DongVO> getDong(@RequestParam(name="sigu_cd", required=true ) String sigu_cd){
		return service.selectDong(sigu_cd);
	}	
	/**
	   파티션 개수 목록 JSON 반환
	  @param sigu_cd 검색대상 시구코드
   	  @return List&lt;String&gt; 검색된 파티션개수 목록 반환
	*/
	@RequestMapping(value="/getPartitionCnt", method=RequestMethod.POST, produces={"application/json"})	
	public List<String> getPartitionCnt(@RequestParam(name="sigu_cd", required=true ) String sigu_cd){
		if(sigu_cd == "112" || sigu_cd.equals("112")){
			List<String> cntList = new ArrayList<String>();
			cntList.add("1");
			cntList.add("4");
			cntList.add("8");
			cntList.add("16");
			return cntList;
		}		
		return service.selectePartitionCount(sigu_cd);
	}
	
	/**
	   신호버전 목록 JSON 반환
	  @return List&lt;String&gt; 검색된 신호버전 목록 반환
	*/
	@RequestMapping(value="/getSignalVersion", method=RequestMethod.POST, produces={"application/json"})	
	public List<String> getSignalVersion(){
		return service.selecteSignalVersion();
	}
	
	/**	   
	 신호현시 상태가 계속 R인것만 추출
	  @return List&lt;String&gt; 신호현시 상태가 계속 R인것만 추출
	*/
	@RequestMapping(value="/getAllR", produces={"application/json"})	
	public List<String> getAllR(){
		return service.getAllR();
	}

}
