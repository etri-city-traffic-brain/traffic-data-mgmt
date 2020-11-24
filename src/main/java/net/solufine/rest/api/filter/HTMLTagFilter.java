/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.filter
 * @file HTMLTagFilter.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일				수정자			수정내용
 * -------------	------		-------------------------------
 * 2018. 5. 22.		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
  <p>클래스 설명 : HTML태크 필터 클래스 - 전자정부표준프레임워크 참조 </p>
  <p>HTMLTagFilter</p>
  <pre>
   net.solufine.rest.api.filter
          └ HTMLTagFilter.java
  </pre>
  @author  redkaras 
  @since  2018. 5. 22.
  @version 1.0
 **/
public class HTMLTagFilter implements Filter{

	@SuppressWarnings("unused")
	private FilterConfig config;
	/**
     @param request 서블릿요청
	 @param response 서블릿응답
	 @param chain 필터체인
     @exception IOException IO예외발생
	 @exception ServletException 서블릿예외발생
	*/
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		chain.doFilter(new HTMLTagFilterRequestWrapper((HttpServletRequest)request), response);
	}
	/**
    @param config 필터설정정보
    @exception ServletException 서블릿예외발생
	*/
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}
	
	public void destroy() {

	}
}
