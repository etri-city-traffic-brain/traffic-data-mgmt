package net.etri.rest.api.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
  <p>클래스 설명 : </p>
  <p>Route</p>
  <pre>
   net.etri.rest.api.xml
          └ Route.java
  </pre>
**/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"links", "startingOffset"})
@JsonInclude(Include.NON_NULL)
public class Route {

	@XmlAttribute(name = "links")
	private String links = null; 
	@XmlAttribute(name = "startingOffset")
	private String startingOffset = null;
	/**
	 Constructor of Route.java class
	*/
	public Route(){
		
	}
	/**
	 Constructor of Route.java class
	 @param startingOffset 시작 오프셋
	 @param links 링크목록
	*/
	public Route(String links, String startingOffset){
		this.links = links;
		this.startingOffset = startingOffset;
	}
	
	/**
	 @return the links
	*/
	public String getLinks() {
		return links;
	}
	/**
	 @param links the links to set
	*/
	public void setLinks(String links) {
		this.links = links;
	}
	/**
	 @return the startingOffset
	*/
	public String getStartingOffset() {
		return startingOffset;
	}
	/**
	 @param startingOffset the startingOffset to set
	*/
	public void setStartingOffset(String startingOffset) {
		this.startingOffset = startingOffset;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("links : ").append(links);
		sb.append(", startingOffset : ").append(startingOffset);
		return sb.toString();
	}
}
