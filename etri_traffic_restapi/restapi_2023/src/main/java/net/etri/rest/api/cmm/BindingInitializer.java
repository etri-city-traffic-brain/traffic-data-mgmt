package net.etri.rest.api.cmm;

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
   net.etri.rest.api.cmm
          └ BindingInitializer.java
  </pre>
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
