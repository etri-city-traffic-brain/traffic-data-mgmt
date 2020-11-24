/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.interceptor
 * @file WebInterceptor.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일				수정자			수정내용
 * -------------	------		-------------------------------
 * 2018. 5. 29.		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
  <p>클래스 설명 : 웹 인터셉터 구현 클래스</p>
  <p>WebInterceptor</p>
  <pre>
   net.solufine.rest.api.interceptor
          └ WebInterceptor.java
  </pre>
  @author  redkaras 
  @since  2018. 5. 29.
  @version 1.0
 **/
public class WebInterceptor extends HandlerInterceptorAdapter {

	Logger log = LoggerFactory.getLogger(WebInterceptor.class);
	
	/**
	   요청에 대한 선처리 인터셉터
	  @param request 서블릿요청
	  @param response 서블릿응답
	  @param handler 인터셉터 핸들러
      @return boolean 처리결과 반환
      @exception Exception 인터셉터 처리간 예외발생
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		return super.preHandle(request, response, handler);
	}
}
