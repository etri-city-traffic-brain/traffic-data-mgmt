/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.sumo.service
 * @file Connection.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일			버전			수정자		수정내용
 * -------------	--------	------		-------------------------------
 * 2018. 6. 28.		0.0.1		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.sumo.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 <p>클래스 설명 : Connection정보 XML처리를 위한 클래스</p>
 <p>Connection</p>
 <pre>
  net.solufine.rest.api.sumo.service
         └ Connection.java
 </pre>
 @author  redkaras 
 @since  2018. 6. 28.
 @version 1.0
**/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"from_edge", "to_edge", "fromLane", "toLane", "tl", "linkIndex"})
@JsonInclude(Include.NON_NULL)
public class Connection {

	@XmlAttribute(name = "from")
	private String from_edge = null;	
	@XmlAttribute(name = "to")
	private String to_edge = null;
	@XmlAttribute(name = "fromLane")
	private int fromLane;	
	@XmlAttribute(name = "toLane")
	private int toLane;		
	@XmlAttribute(name = "tl")
	private String tl = null;		
	@XmlAttribute(name = "linkIndex")
	private String linkIndex = null;	
	/**
	 * @return the from_edge
	 */
	public String getFrom_edge() {
		return from_edge;
	}
	/**
	 * @param from_edge the from_edge to set
	 */
	public void setFrom_edge(String from_edge) {
		this.from_edge = from_edge;
		if(this.from_edge.isEmpty()){
			this.from_edge = null;
		}
	}
	/**
	 * @return the to_edge
	 */
	public String getTo_edge() {
		return to_edge;
	}
	/**
	 * @param to_edge the to_edge to set
	 */
	public void setTo_edge(String to_edge) {
		this.to_edge = to_edge;
		if(this.to_edge.isEmpty()){
			this.to_edge = null;
		}
	}
	/**
	 * @return the fromLane
	 */
	public int getFromLane() {
		return fromLane;
	}
	/**
	 * @param fromLane the fromLane to set
	 */
	public void setFromLane(int fromLane) {
		this.fromLane = fromLane;
	}
	/**
	 * @return the toLane
	 */
	public int getToLane() {
		return toLane;
	}
	/**
	 * @param toLane the toLane to set
	 */
	public void setToLane(int toLane) {
		this.toLane = toLane;
	}
	/**
	 * @return the tl
	 */
	public String getTl() {
		return tl;
	}
	/**
	 * @param tl the tl to set
	 */
	public void setTl(String tl) {
		this.tl = tl;
	}
	/**
	 * @return the linkIndex
	 */
	public String getLinkIndex() {
		return linkIndex;
	}
	/**
	 * @param linkIndex the linkIndex to set
	 */
	public void setLinkIndex(String linkIndex) {
		this.linkIndex = linkIndex;
	}
	
	
}
