/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api
 * @file DefaultController.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일				수정자			수정내용
 * -------------	------		-------------------------------
 * 2018. 2. 12.		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import net.solufine.rest.api.repository.CodeVO;
import net.solufine.rest.api.repository.DongVO;
import net.solufine.rest.api.repository.RequestVO;
import net.solufine.rest.api.repository.SiguVO;
import net.solufine.rest.api.service.DefaultService;

/**
  <p>클래스 설명 : 기본 컨트롤러 클래스</p>
  <p>DefaultController</p>
  <pre>
   net.solufine.rest.api
          └ DefaultController.java
  </pre>
  @author  redkaras 
  @since  2018. 2. 12.
  @version 1.0
 **/
@Controller
public class DefaultController extends AbstractController {
	Logger log = LoggerFactory.getLogger(DefaultController.class);
	@Resource(name="defaultService")
	private DefaultService service;
	
	/**
	   메인화면 요청 처리
  	  @param model 응답처리시 동적 데이터 반환을 위한 인터페이스 
      @return String 요청에 대한 응답화면 명
	 */
	@RequestMapping("/main")
	public String main(Model model){
		List<SiguVO> sigu = service.selectSigu(null);		
		String sigu_cd = null;
		String dong_cd = null;
		
		/**
		 2019.07.11 Redkaras 강남4구 전체가 기본으로 되어 기본구코드 처리가 불필요하여 제외 및 변경처리 
		 */
		/*
		Iterator<SiguVO> it = sigu.iterator();
		while(it.hasNext()){
			SiguVO vo = it.next();
			if( vo.getSigu_cd() == service.getDefaultSigu() || vo.getSigu_cd().equals(service.getDefaultSigu()) ){
				sigu_cd = vo.getSigu_cd();
				break;
			}
		}
		
		List<String> cntList = service.selectePartitionCount(sigu_cd);
		List<String> verList = service.selecteSignalVersion();
		List<DongVO> dong = service.selectDong(sigu_cd);
		dong_cd = dong.get(0).getDong_cd();
		*/
		
		List<String> cntList = new ArrayList<String>();
		cntList.add("1");
		cntList.add("4");
		cntList.add("8");
		cntList.add("16");
		
		List<String> verList = service.selecteSignalVersion();
		List<DongVO> dong = new ArrayList<DongVO>();
		
		model.addAttribute("sigu_cd", sigu_cd);
		model.addAttribute("dong_cd", dong_cd);
		
		model.addAttribute("sigu", sigu);
		model.addAttribute("dong", dong);
		
		model.addAttribute("partition_cnt_list", cntList);
		
		if(verList == null){
			verList = new ArrayList<String>();
			verList.add("1");
		}
		model.addAttribute("version_list", verList);
		
		return "main";
	}
	
	/**
	   업로드이력화면 요청 처리
	  @param model 응답처리시 동적 데이터 반환을 위한 인터페이스 
      @return String 요청에 대한 응답화면 명
	 */
	@RequestMapping("/uploadhistory")
	public String uploadhistory(Model model){
		
		return "history/UploadHistory";
	}
	
	/**
	   요청이력화면 요청 처리
	  @param model 응답처리시 동적 데이터 반환을 위한 인터페이스 
	  @param vo 검색을 위한 사용자 파라메터 객체
	  @return String 요청에 대한 응답화면 명
	 */
	@RequestMapping("/requesthistory")
	public String requesthistory(Model model, @ModelAttribute RequestVO vo){
		List<RequestVO> list = service.selecteRequstHistory(vo);
		if(list == null){
			list = new ArrayList<RequestVO>();
		}
		
		int total = service.selecteRequstHistoryCount();
		int totalPage = total / vo.getPageSize();
		if( total % vo.getPageSize() > 0){
			totalPage++;
		}
		
		List<CodeVO> cdList = service.selectCode("IN");
		
		vo.setPageUnit(totalPage);
		
		model.addAttribute("list", list);
		model.addAttribute("req", vo);
		model.addAttribute("codes", cdList);
		
		return "history/RequestHistory";
	}
	
}
	
