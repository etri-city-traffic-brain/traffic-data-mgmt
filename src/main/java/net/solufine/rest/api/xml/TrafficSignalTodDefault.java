/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.xml
 * @file TrafficSignalTodDefault.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일			버전			수정자		수정내용
 * -------------	--------	------		-------------------------------
 * 2018. 9. 27.		0.0.1		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
  <p>클래스 설명 : TrafficSignalTodDefault정보 XML처리를 위한 클래스</p>
  <p>TrafficSignalTodDefault</p>
  <pre>
   net.solufine.rest.api.xml
          └ TrafficSignalTodDefault.java
  </pre>
  @author  redkaras 
  @since  2018. 9. 27.
  @version 1.0
**/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"plan", "offset", "defaultPlan"})
@JsonInclude(Include.NON_NULL)
public class TrafficSignalTodDefault {

	@XmlAttribute(name = "defaultPlan", required = true)
	private int defaultPlan;	//첫번째꺼.	
	@XmlAttribute(name = "offset")
	private int offset = 0;		// 계산식  시작시간을 기준으로 +,- 연산
	@XmlElement(name="plan")
	private List<TrafficSignalTodDefaultPlan> plan = null;
	
	/**
     @return the defaultPlan
	*/
	public int getDefaultPlan() {
		return defaultPlan;
	}
	/**
  	 @param defaultPlan the defaultPlan to set
	*/
	public void setDefaultPlan(int defaultPlan) {
		this.defaultPlan = defaultPlan;
	}

	/**
     @return the offset
	*/
	public int getOffset() {
		return offset;
	}
	/**
  	 @param offset the offset to set
	*/
	public void setOffset(int offset) {
		this.offset = offset;
	}
	/**
     @return the plan
	*/
	public List<TrafficSignalTodDefaultPlan> getPlan() {
		return plan;
	}
	/**
  	 @param plan the plan to set
	*/
	public void setPlan(List<TrafficSignalTodDefaultPlan> plan) {
		this.plan = plan;
	} 
	/**
 	 @param plan the plan to set
	*/
	public void setPlan(TrafficSignalTodDefaultPlan plan) {
		if( this.plan == null)
			this.plan = new ArrayList<TrafficSignalTodDefaultPlan>();
		
		if(!this.plan.contains(plan))
			this.plan.add(plan);
		
	} 
}

/**
<p>클래스 설명 : TrafficSignalTodDefaultPlan정보 XML처리를 위한 클래스</p>
<p>TrafficSignalTodDefaultPlan</p>
<pre>
 net.solufine.rest.api.xml
        └ TrafficSignalTodDefaultPlan.java
</pre>
@author  redkaras 
@since  2018. 9. 27.
@version 1.0
**/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"offset", "schedule", "startTime"})
@JsonInclude(Include.NON_NULL)
class TrafficSignalTodDefaultPlan {

	@XmlAttribute(name = "schedule", required = true)
	private int schedule; 	
	@XmlAttribute(name = "startTime")
	private int startTime;	// 시작시간의 누적(초)
	@XmlAttribute(name = "offset")
	private int offset = 0;		// 계산식  시작시간을 기준으로 +,- 연산
	/**
	 Constructor of TrafficSignalTodDefault.java class
	 */
	public TrafficSignalTodDefaultPlan(){
		
	}
	/**
	 Constructor of TrafficSignalTodDefault.java class
	 @param schedule
	 @param startTime
	 @param offset
	 */
	public TrafficSignalTodDefaultPlan(int schedule, int startTime, int offset){
		this.schedule = schedule;
		this.startTime = startTime;
		this.offset = offset;
	}
	
	/**
     @return the schedule
	*/
	public int getSchedule() {
		return schedule;
	}
	/**
  	 @param schedule the schedule to set
	*/
	public void setSchedule(int schedule) {
		this.schedule = schedule;
	}
	/**
     @return the startTime
	*/
	public int getStartTime() {
		return startTime;
	}
	/**
  	 @param startTime the startTime to set
	*/
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	/**
     @return the offset
	*/
	public int getOffset() {
		return offset;
	}
	/**
  	 @param offset the offset to set
	*/
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	
}