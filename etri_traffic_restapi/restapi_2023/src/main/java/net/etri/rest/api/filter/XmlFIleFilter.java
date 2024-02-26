package net.etri.rest.api.filter;

import java.io.File;
import java.io.FilenameFilter;

/**
  <p>클래스 설명 : XML파일 확장사 필터 클래스</p>
  <p>XmlFIleFilter</p>
  <pre>
   net.etri.rest.api.filter
          └ XmlFIleFilter.java
  </pre>
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
