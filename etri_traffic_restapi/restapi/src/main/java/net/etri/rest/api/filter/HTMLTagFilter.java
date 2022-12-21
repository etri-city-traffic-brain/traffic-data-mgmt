package net.etri.rest.api.filter;

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
   net.etri.rest.api.filter
          └ HTMLTagFilter.java
  </pre>
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
