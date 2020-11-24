/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.json
 * @file JsonController.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일			버전			수정자		수정내용
 * -------------	--------	------		-------------------------------
 * 2019. 6. 21.		0.0.1		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.json;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.solufine.rest.api.AbstractController;
import net.solufine.rest.api.json.service.Connection;
import net.solufine.rest.api.json.service.JsonService;
import net.solufine.rest.api.json.service.JsonSignal;
import net.solufine.rest.api.json.service.TrafficSignal;

/**
<p>클래스 설명 : JSON관련 요청에 대한 처리를 위한 컨트롤러 클래스</p>
<p>JsonController</p>
<pre>
 net.solufine.rest.api.json
        └ JsonController.java
</pre>
@author  redkaras 
@since  2019. 6. 21.
@version 1.0
**/
@RestController("jsonController")
public class JsonController extends AbstractController {

	Logger log = LoggerFactory.getLogger(JsonController.class);
	
	@Value("#{props['upload.store.path']}")
	private String storePath;
	
	@Resource(name="jsonService")
	private JsonService service;
	
	@RequestMapping(value="/updateSignalFile")
	public void updateSignalFile(MultipartFile uploadFile, HttpServletResponse response) throws IOException {
		
		if(uploadFile == null ){ response.getWriter().write("The upload file is an Null. [ Fail ]"); return;}
		if(uploadFile.isEmpty() ){ response.getWriter().write("The upload file is an empty file. [ Fail ]"); return;}
		if( !uploadFile.getContentType().equals("application/json") ){ response.getWriter().write("The upload file is not an Json file. [ Fail ]"); return; }
		
		CheckDirectory(storePath);
		
		File json = new File( storePath + uploadFile.getName());
		uploadFile.transferTo(json);
		
		ObjectMapper mapper = new ObjectMapper(); // create once, reuse
		TrafficSignal value = mapper.readValue(json, TrafficSignal.class);
		
		if( service.updateSignal(value) > 0 ) {
			response.getWriter().write("success");
		}else {
			response.getWriter().write("fail");
		}
	}
	
	@RequestMapping(value="/updateSignal")
	public void updateSignal(@RequestBody TrafficSignal value,
			HttpServletResponse response) throws IOException {

		CheckDirectory(storePath);
		
		if( service.updateSignal(value) > 0 ) {
			response.getWriter().write("success");
		}else {
			response.getWriter().write("fail");
		}
	}
	
	/**
	 	입력: signal id, 버전
		출력: trafficSignal.json
	 * */
	@RequestMapping(value="/getSignal", method=RequestMethod.GET, produces="application/json; charset=utf8")
	public @ResponseBody TrafficSignal getSignal(@RequestParam(name="signal_id", required = true) String signal_id,
								 @RequestParam(name="version", required = true) String version,
								 HttpServletRequest request, HttpServletResponse response) {
		
		return service.getSignal(signal_id, version);
	}
	
	/**
 	입력: signal id
	 @throws JsonProcessingException 
	 * */
	@RequestMapping(value="/getSignalInfo", method=RequestMethod.GET, produces="application/json; charset=utf8")
	public @ResponseBody JsonSignal getSignalInfo(@RequestParam(name="signal_id", required = true) String signal_id,
								 HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
		
		JsonSignal json = new JsonSignal();
		json.setSignal(service.getSignalInfo(signal_id)); 
		return json;
	}
	
	/**
 	입력: junction_id
	 * */
	@RequestMapping(value="/getConnection", method=RequestMethod.GET, produces="application/json; charset=utf8")
	public @ResponseBody List<Connection> getConnection(@RequestParam(name="junction_id", required = true) String junction_id,
								 HttpServletRequest request, HttpServletResponse response) {
		
		return service.getConnection(junction_id);
	}
	
	/**
 	입력: signal id, 버전
	 * @throws IOException 
	**/
	@RequestMapping(value="/deleteSignal")
	public void deleteSignal(@RequestParam(name="signal_id", required = true) String signal_id,
								 @RequestParam(name="version", required = true) String version,
								 HttpServletRequest request, HttpServletResponse response) throws IOException {
	
		service.deleteSignal(signal_id, version);
		response.getWriter().write("Delete Signal ID : " + signal_id + " , Version : " + version);
	}

}
