/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.xml
 * @file BorderSet.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일			버전			수정자		수정내용
 * -------------	--------	------		-------------------------------
 * 2018. 9. 28.		0.0.1		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
  <p>클래스 설명 : BorderSet정보 XML처리를 위한 클래스</p>
  <p>BorderSet</p>
  <pre>
   net.solufine.rest.api.xml
          └ BorderSet.java
  </pre>
  @author  redkaras 
  @since  2018. 9. 28.
  @version 1.0
**/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"border"})
@JsonInclude(Include.NON_NULL)
public class BorderSet {

	@XmlElement(name="border")
	private List<Border> border = null;

	/**
     @return the border
	*/
	public List<Border> getBorder() {
		return border;
	}

	/**
  	 @param border the border to set
	*/
	public void setBorder(List<Border> border) {
		this.border = border;
	}
	
	
}
