/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.filter
 * @file XmlFIleFilter.java
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *	수정일			버전			수정자		수정내용
 * -------------	--------	------		-------------------------------
 * 2018. 10. 1.		0.0.1		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.filter;

import java.io.File;
import java.io.FilenameFilter;

/**
  <p>클래스 설명 : XML파일 확장사 필터 클래스</p>
  <p>XmlFIleFilter</p>
  <pre>
   net.solufine.rest.api.filter
          └ XmlFIleFilter.java
  </pre>
  @author  redkaras 
  @since  2018. 10. 1.
  @version 1.0
 **/
public class XmlFIleFilter implements FilenameFilter {

	/* (non-Javadoc)
	  @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
	 */
	@Override
	public boolean accept(File dir, String name) {
		return name.toLowerCase().endsWith(".xml");
	}

}
