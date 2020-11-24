/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.xml
 * @file TrafficSignalTod.java
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
  <p>클래스 설명 : TrafficSignalTod정보 XML처리를 위한 클래스</p>
  <p>TrafficSignalTod</p>
  <pre>
   net.solufine.rest.api.xml
          └ TrafficSignalTod.java
  </pre>
  @author  redkaras 
  @since  2018. 9. 21.
  @version 1.0
**/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"id", "todSeq", "scheduleID", "fromTime", "toTime"})
@JsonInclude(Include.NON_NULL)
public class TrafficSignalTod {

	@XmlAttribute(name = "id", required = true)
	private String id = null;
	@XmlAttribute(name = "todSeq")
	private int todSeq = 0;	
	@XmlAttribute(name = "scheduleID")
	private int scheduleID = 0;
	@XmlAttribute(name = "fromTime")
	private String fromTime = null;
	@XmlAttribute(name = "toTime")
	private String toTime = null;
	
	/**
	 Constructor of TrafficSignalTod.java class
	*/
	public TrafficSignalTod(){
		
	}
	/**
	 Constructor of TrafficSignalTod.java class
	 @param signal_id
	 @param seq
	 @param pattern_id
	 @param f
	 @param t
	*/
	public TrafficSignalTod(String signal_id, int seq, int pattern_id, String f, String t){
		this.id = signal_id;
		this.todSeq = seq;	
		this.scheduleID = pattern_id;
		String[] fTime = f.split(":");
		String[] tTime = t.split(":");
		this.fromTime = fTime[0] + ":" + fTime[1];
		this.toTime = tTime[0] + ":" + tTime[1];	
	}
	
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
     @return the todSeq
	*/
	public int getTodSeq() {
		return todSeq;
	}
	/**
  	 @param todSeq the todSeq to set
	*/
	public void setTodSeq(int todSeq) {
		this.todSeq = todSeq;
	}
	/**
     @return the scheduleID
	*/
	public int getScheduleID() {
		return scheduleID;
	}
	/**
  	 @param scheduleID the scheduleID to set
	*/
	public void setScheduleID(int scheduleID) {
		this.scheduleID = scheduleID;
	}
	/**
     @return the fromTime
	*/
	public String getFromTime() {
		return fromTime;
	}
	/**
  	 @param fromTime the fromTime to set
	*/
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}
	/**
     @return the toTime
	*/
	public String getToTime() {
		return toTime;
	}
	/**
  	 @param toTime the toTime to set
	*/
	public void setToTime(String toTime) {
		this.toTime = toTime;
	}
	
}
