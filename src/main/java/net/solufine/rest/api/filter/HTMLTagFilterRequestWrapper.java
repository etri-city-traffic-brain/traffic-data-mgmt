/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.filter
 * @file HTMLTagFilterRequestWrapper.java
 @see 
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일				수정자			수정내용
 * -------------	------		-------------------------------
 * 2018. 5. 22.		redkaras	최초작성
 * 
 * </pre>
 @author redkaras
 @since 2018. 5. 22.
 * @see 
 ********************************************************************/
package net.solufine.rest.api.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
  <p>클래스 설명 : HTTP 요청에 대한 HTML 태그 와핑 클래스  - 전자정부표준프레임워크 참조 </p>
  <p>HTMLTagFilterRequestWrapper</p>
  <pre>
   net.solufine.rest.api.filter
          └ HTMLTagFilterRequestWrapper.java
  </pre>
  @author  redkaras 
  @since  2018. 5. 22.
  @version 1.0
 **/
public class HTMLTagFilterRequestWrapper extends HttpServletRequestWrapper {
	/**
	 Constructor of HTMLTagFilterRequestWrapper.java class
	 @param request 서블릿요청
	 */
	public HTMLTagFilterRequestWrapper(HttpServletRequest request) {
		super(request);
	}
	/**
	 @param parameter 파라메터키
	 @return String[] 파라메터값 목록
	*/
	public String[] getParameterValues(String parameter) {

		String[] values = super.getParameterValues(parameter);
		
		if(values==null){
			return null;			
		}
		
		for (int i = 0; i < values.length; i++) {			
			if (values[i] != null) {				
				StringBuffer strBuff = new StringBuffer();
				for (int j = 0; j < values[i].length(); j++) {
					char c = values[i].charAt(j);
					switch (c) {
					case '<':
						strBuff.append("&lt;");
						break;
					case '>':
						strBuff.append("&gt;");
						break;
					//case '&':
						//strBuff.append("&amp;");
						//break;
					case '"':
						strBuff.append("&quot;");
						break;
					case '\'':
						strBuff.append("&apos;");
						break;
					default:
						strBuff.append(c);
						break;
					}
				}				
				values[i] = strBuff.toString();
			} else {
				values[i] = null;
			}
		}

		return values;
	}
	/**
	 @param parameter 파라메터키
	 @return String 파라메터값
	*/
	public String getParameter(String parameter) {
		
		String value = super.getParameter(parameter);
		
		if(value==null){
			return null;
		}
		
		StringBuffer strBuff = new StringBuffer();

		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			switch (c) {
			case '<':
				strBuff.append("&lt;");
				break;
			case '>':
				strBuff.append("&gt;");
				break;
			case '&':
				strBuff.append("&amp;");
				break;
			case '"':
				strBuff.append("&quot;");
				break;
			case '\'':
				strBuff.append("&apos;");
				break;	
			default:
				strBuff.append(c);
				break;
			}
		}
		
		value = strBuff.toString();
		
		return value;
	}

}