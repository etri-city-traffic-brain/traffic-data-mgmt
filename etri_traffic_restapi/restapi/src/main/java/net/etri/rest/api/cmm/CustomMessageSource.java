package net.etri.rest.api.cmm;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
  <p>클래스 설명 : 사용자정의 메시지 처리 클래스</p>
  <p>CustomMessageSource</p>
  <pre>
   net.etri.rest.api.cmm
          └ CustomMessageSource.java
  </pre>
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