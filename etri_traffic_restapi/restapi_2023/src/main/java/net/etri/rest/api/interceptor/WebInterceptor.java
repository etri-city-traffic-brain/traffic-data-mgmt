package net.etri.rest.api.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
  <p>클래스 설명 : 웹 인터셉터 구현 클래스</p>
  <p>WebInterceptor</p>
  <pre>
   net.etri.rest.api.interceptor
          └ WebInterceptor.java
  </pre>
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
