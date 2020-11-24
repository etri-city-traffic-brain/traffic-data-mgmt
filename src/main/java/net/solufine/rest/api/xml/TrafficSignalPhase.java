/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.xml
 * @file TrafficSignalPhase.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일			버전			수정자		수정내용
 * -------------	--------	------		-------------------------------
 * 2018. 9. 21.		0.0.1		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
  <p>클래스 설명 : TrafficSignalPhase정보 XML처리를 위한 클래스</p>
  <p>TrafficSignalPhase</p>
  <pre>
   net.solufine.rest.api.xml
          └ TrafficSignalPhase.java
  </pre>
  @author  redkaras 
  @since  2018. 9. 21.
  @version 1.0
**/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"state", "duration"})
@JsonInclude(Include.NON_NULL)
public class TrafficSignalPhase {

	@XmlAttribute(name = "duration")
	private int duration = 0;	
	@XmlAttribute(name = "state")
	private String state = null;
	/**
	 Constructor of TrafficSignalPhase.java class
	*/
	public TrafficSignalPhase(){
		
	}
	/**
	 Constructor of TrafficSignalPhase.java class
	 @param d
	 @param s	 
	*/
	public TrafficSignalPhase(int d, String s){
		this.duration = d;
		this.state = s;
	}	
	/**
     @return the duration
	*/
	public int getDuration() {
		return duration;
	}
	/**
  	 @param duration the duration to set
	*/
	public void setDuration(int duration) {
		this.duration = duration;
	}
	/**
     @return the state
	*/
	public String getState() {
		return state;
	}
	/**
  	 @param state the state to set
	*/
	public void setState(String state) {
		this.state = state;
	}	
	
	
}
