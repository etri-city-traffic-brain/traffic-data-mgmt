package net.etri.rest.api.xml;

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
   net.etri.rest.api.xml
          └ BorderSet.java
  </pre>
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
