/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.cmm
 * @file BindingInitializer.java
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

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;

/**
  <p>클래스 설명 : 웹어플리케이션 구동시 초기화 클래스</p>
  <p>BindingInitializer</p>
  <pre>
   net.solufine.rest.api.cmm
          └ BindingInitializer.java
  </pre>
  @author  redkaras 
  @since  2018. 5. 23.
  @version 1.0
 **/
public class BindingInitializer implements WebBindingInitializer {

	/* (non-Javadoc)
	  @see org.springframework.web.bind.support.WebBindingInitializer#initBinder(org.springframework.web.bind.WebDataBinder)
	 */
	@Override
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
	}

}
