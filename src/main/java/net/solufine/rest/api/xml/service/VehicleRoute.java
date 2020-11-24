/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 * 
 * @project rest.api
 * @package net.solufine.rest.api.xml.service
 * @file VehicleRoute.java
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 * 	수정일			버전			수정자		수정내용
 * -------------	--------	------		-------------------------------
 * 2018. 10. 10.		0.0.1		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.xml.service;

/**
  <p>클래스 설명 : </p>
  <p>VehicleRoute</p>
  <pre>
   net.solufine.rest.api.xml.service
          └ VehicleRoute.java
  </pre>
  @author  redkaras 
  @since  2018. 10. 10.
  @version 1.0
**/
public class VehicleRoute {

	private String id = null; 
	private String type = null;
	private String depart = null;
    private String departLane = null;	
    
	private String links = null; 
	private String startingOffset = null;
	/**
	 @return the id
	*/
	public String getId() {
		return id;
	}
	/**
	 @param id the id to set
	*/
	public void setId(String id) {
		this.id = id;
	}
	/**
	 @return the type
	*/
	public String getType() {
		return type;
	}
	/**
	 @param type the type to set
	*/
	public void setType(String type) {
		this.type = type;
	}
	/**
	 @return the depart
	*/
	public String getDepart() {
		return depart;
	}
	/**
	 @param depart the depart to set
	*/
	public void setDepart(String depart) {
		this.depart = depart;
	}
	/**
	 @return the departLane
	*/
	public String getDepartLane() {
		return departLane;
	}
	/**
	 @param departLane the departLane to set
	*/
	public void setDepartLane(String departLane) {
		this.departLane = departLane;
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
		sb.append("id : ").append(id);
		sb.append(", type : ").append(type);
		sb.append(", depart : ").append(depart);
		sb.append(", departLane : ").append(departLane);
		sb.append("links : ").append(links);
		sb.append(", startingOffset : ").append(startingOffset);		
		return sb.toString();
	}
}
