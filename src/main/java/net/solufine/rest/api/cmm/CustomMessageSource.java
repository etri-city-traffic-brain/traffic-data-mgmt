/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.cmm
 * @file CustomMessageSource.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일				수정자			수정내용
 * -------------	------		-------------------------------
 * 2018. 5. 23.		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.cmm;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
  <p>클래스 설명 : 사용자정의 메시지 처리 클래스</p>
  <p>CustomMessageSource</p>
  <pre>
   net.solufine.rest.api.cmm
          └ CustomMessageSource.java
  </pre>
  @author  redkaras 
  @since  2018. 5. 23.
  @version 1.0
 **/
public class CustomMessageSource extends ReloadableResourceBundleMessageSource implements MessageSource {

	private ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource;

	/**
	  getReloadableResourceBundleMessageSource() 
  	  @param reloadableResourceBundleMessageSource - resource MessageSource
	 */	
	public void setReloadableResourceBundleMessageSource(ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource) {
		this.reloadableResourceBundleMessageSource = reloadableResourceBundleMessageSource;
	}
	
	/**
	  getReloadableResourceBundleMessageSource() 
      @return ReloadableResourceBundleMessageSource
	 */	
	public ReloadableResourceBundleMessageSource getReloadableResourceBundleMessageSource() {
		return reloadableResourceBundleMessageSource;
	}
	
	/**
	   정의된 메세지 조회	 
  	  @param code 메세지 코드
      @return String 메시지 내용
	 */	
	public String getMessage(String code) {
		return getReloadableResourceBundleMessageSource().getMessage(code, null, Locale.getDefault());
	}

}