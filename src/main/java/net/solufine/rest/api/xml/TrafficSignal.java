/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.xml
 * @file TrafficSignal.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일			버전			수정자		수정내용
 * -------------	--------	------		-------------------------------
 * 2018. 9. 21.		0.0.1		redkaras	최초작성
 * 2019. 6.  7.		0.0.1		redkaras	tod 엘리먼트 불필요 항목으로 제외
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.xml;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import net.solufine.rest.api.xml.service.Signal;
import net.solufine.rest.api.xml.service.SignalScenario;
import net.solufine.rest.api.xml.service.SignalTod;

/**
  <p>클래스 설명 : TrafficSignal정보 XML처리를 위한 클래스 </p>
  <p>TrafficSignal</p>
  <pre>
   net.solufine.rest.api.xml
          └ TrafficSignal.java
  </pre>
  @author  redkaras 
  @since  2018. 9. 21.
  @version 1.0
**/
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder={"schedule", "TODPlan", "nodeID", "version", "crossNo", "crossName", "date", "signalGroup"})
@JsonInclude(Include.NON_NULL)
public class TrafficSignal {

	@XmlAttribute(name = "nodeID", required = true)
	private String nodeID = null;
	@XmlAttribute(name = "version")
	private String version = null;
	@XmlAttribute(name = "crossNo")
	private String crossNo = null;
	@XmlAttribute(name = "crossName")
	private String crossName = null;
	@XmlAttribute(name = "date")
	private String date = null;
	@XmlAttribute(name = "signalGroup")
	private String signalGroup = null;
	
	private List<TrafficSignalTod> tod = null;
	
	@XmlElement(name="schedule")
	private List<TrafficSignalScenario> schedule = null;
	@XmlElement(name="TODPlan")
	private TrafficSignalTodDefault TODPlan = null;	// defaultPlan = 첫번째꺼. offset=계산식”
	/**
	 Constructor of TrafficSignal.java class
	 */
	public TrafficSignal(){
		
	}
	/**
	 Constructor of TrafficSignal.java class
	 @param signal
	*/
	public TrafficSignal(Signal signal ){
		this.nodeID = signal.getSignal_id();
		this.version = signal.getVersion();
		this.crossNo = signal.getCross_no();
		this.crossName = signal.getCross_nm();
		this.date = signal.getEff_date();
		this.signalGroup = signal.getSignal_group();
		
		if( signal.getTod() != null && !signal.getTod().isEmpty() ){
			this.TODPlan = new TrafficSignalTodDefault(); 
			this.tod = new ArrayList<TrafficSignalTod>(); 
			setSignalTod(signal.getTod());
		}
		
		if( signal.getSchedule() != null && !signal.getSchedule().isEmpty() ){
			this.schedule = new ArrayList<TrafficSignalScenario>(); 
			setSignalSchedule(signal.getSchedule());
		}
	}	
	/**
     @return the nodeID
	*/
	public String getNodeID() {
		return nodeID;
	}
	/**
  	 @param nodeID the nodeID to set
	*/
	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}
	/**
     @return the version
	*/
	public String getVersion() {
		return version;
	}
	/**
  	 @param version the version to set
	*/
	public void setVersion(String version) {
		this.version = version;
	}
	/**
     @return the crossNo
	*/
	public String getCrossNo() {
		return crossNo;
	}
	/**
  	 @param crossNo the crossNo to set
	*/
	public void setCrossNo(String crossNo) {
		this.crossNo = crossNo;
	}
	/**
     @return the crossName
	*/
	public String getCrossName() {
		return crossName;
	}
	/**
  	 @param crossName the crossName to set
	*/
	public void setCrossName(String crossName) {
		this.crossName = crossName;
	}
	/**
     @return the date
	*/
	public String getDate() {
		return date;
	}
	/**
  	 @param date the date to set
	*/
	public void setDate(String date) {
		this.date = date;
	}
	/**
     @return the signalGroup
	*/
	public String getSignalGroup() {
		return signalGroup;
	}
	/**
  	 @param signalGroup the signalGroup to set
	*/
	public void setSignalGroup(String signalGroup) {
		this.signalGroup = signalGroup;
	}
	/**
     @return the tod
	*/
	public List<TrafficSignalTod> getTod() {
		return tod;
	}
	/**
  	 @param tod the tod to set
	*/
	public void setTod(List<TrafficSignalTod> tod) {
		this.tod = tod;
	}
	/**
     @return the schedule
	*/
	public List<TrafficSignalScenario> getSchedule() {
		return schedule;
	}
	/**
  	 @param schedule the schedule to set
	*/
	public void setSchedule(List<TrafficSignalScenario> schedule) {
		this.schedule = schedule;
	}
	
	private int calcDefaultPlanOffset(SignalTod defaultTod) {
		int from_time = LocalTime.parse(defaultTod.getFrom_time()).toSecondOfDay();
		int to_time = LocalTime.parse(defaultTod.getTo_time()).toSecondOfDay();
		int req_time = LocalTime.parse(defaultTod.getReq_from_time(":")).toSecondOfDay();
		int current_time = defaultTod.getOffset();
		int start_time = 0;
		int cycle = defaultTod.getCycle();
		
		if(req_time == from_time) {
			// 요청 시작시간과 TOD 시작시간이 같다면 TOD의 offset( Peristalsis(연동) ) 적용
			return current_time;
		}else if(req_time > from_time){
			// 요청 시작시간이 Plan의 시작시간보다 클경우
			start_time = req_time - from_time;
			while( current_time < start_time){
				current_time += cycle;
			}
			return current_time - start_time;
		}else{
			// 요청 시작시간이 Plan의 시작시간보다 작을경우			
			if(to_time > from_time) {
				//TOD의 종료시간이 시작시간보다 클경우 07:00 ~ 10:00
				start_time = from_time - req_time;
			}else {
				//TOD의 종료시간이 시작시간보다 작을경우 22:00 ~ 07:00
				start_time = to_time - req_time;
			}
			while( current_time < start_time){
				current_time += cycle;
			}
			return current_time - start_time;
		}
	}
	
	private void calcPlanStartTime(List<SignalTod> tod) {		
		LocalDate reqDate = LocalDate.parse(tod.get(0).getReq_date("-"));
		int reqTime = LocalTime.parse(tod.get(0).getReq_from_time(":")).toSecondOfDay();
		LocalDate LastDate = null;
		LocalTime LastTime = null;
		
		//Default Plan(0)을 제외한 시작시간 계산.
		int tick_count = 0;
		for(int i = 1; i < tod.size(); i++){
			SignalTod t = tod.get(i);
			LocalDate todDate = LocalDate.parse(t.getReq_date("-"));
			LocalTime startTime = LocalTime.parse(t.getFrom_time());
						
			if( LastTime == null) {
				LastTime = LocalTime.parse(t.getFrom_time());
			}
			
			if( todDate.isEqual(reqDate) ) {
				//요청일자(시작일자)와 동일하다면 
				if( i == 1) {
					//최초 시작시간에서 요청시간을 빼준다.
					tick_count += (startTime.toSecondOfDay() - reqTime);
				}else {
					//이전 시간을 빼준다
					tick_count += (startTime.toSecondOfDay() - LastTime.toSecondOfDay());
				}
				//시작시간 누적 처리용
				LastTime = LocalTime.parse(t.getFrom_time());
			}else {
				//요청일자(시작일자)와 틀리다면  날짜 + 시간  의 간격을 구한다.
				if(LastDate == null || !LastDate.isEqual(todDate)) {
					//마지막 일자가 NULL이면서  요청일자와 TOD일자가 틀릴경우는 TOD에 Plan이 1개일 경우
					//TOD의 날짜가 익일로 넘어가는 시점이라면 
					//전일의 마지막  시작시간 ~ 0시까지의 Tick을 구하여 더해준다.
					tick_count += (86400 - LastTime.toSecondOfDay());
					//전일 마지막 시간을 더했음으로, 0시부터 시작 
					tick_count += startTime.toSecondOfDay();
					LastDate = todDate;
				}else{					
					tick_count += (startTime.toSecondOfDay() - LastTime.toSecondOfDay());
				}
				//시작시간 누적 처리용
				LastTime = LocalTime.parse(t.getFrom_time());
			}
			
			this.TODPlan.setPlan(new TrafficSignalTodDefaultPlan(t.getPattern_id(), tick_count, t.getOffset()) );
		}	
	}
			
	/**
 	 @param tod the tod to set
	*/
	private void setSignalTod(List<SignalTod> tod){
		// TODO : Signal객체를 통해 TrafficSignal객체로 전환시 신호 XML 생성에 필요한 TOD Plan 처리, Offset 계산
		//        offset : 요청시간과 시작시간이 같을경우 TOD의 기본 offset, 그렇지 않을 경우 계산.    tick_count : 요청시간을 기준으로 0부터 시작
		if( tod == null || tod.isEmpty() )
			return;
		
		//데이터 조회시 첫번째 TOD가 기본으로 되게끔 조회 처리
		this.TODPlan.setDefaultPlan(tod.get(0).getPattern_id());
		/**
		   Start Time : Plan별 누적(초)
		   Offset : 요청시간을 기준으로 Offset을 구한다.  Peristalsis(연동) 반영
		      요청 시작시간과 패턴 시작시간이 동일하지 않아 Default Plan에 대해서 만 별도로 Offset을 연산
		      나머지 Plan에 대해서는 Peristalsis(연동) 사용
		  */
		//defaultPlan 의 Offset를 구한다.
		this.TODPlan.setOffset(calcDefaultPlanOffset(tod.get(0)));
		
		//TOD 목록을 XML용 TOD로 변환
		for(int i = 0; i < tod.size(); i++){
			SignalTod t = tod.get(i);
			this.tod.add( new TrafficSignalTod(t.getPlan_id(), t.getSeq(), 
					t.getPattern_id(), t.getFrom_time(), t.getTo_time())  );	
		}
				
		//TOD별 시작시간(Tick Count)을 구한다.
		calcPlanStartTime(tod);
	}
	
	/**
 	 @param schedule the schedule to set
	*/
	private void setSignalSchedule(List<SignalScenario> scenario){
		// TODO : Signal객체를 통해 TrafficSignal객체로 전환시  신호 XML 생성에 필요한 스케줄 처리
		if( scenario == null || scenario.isEmpty() )
			return;
		
		Iterator<SignalScenario> it = scenario.iterator();
		while(it.hasNext()){
			SignalScenario s = it.next();
			try {
				this.schedule.add( new TrafficSignalScenario(s.getPattern_id(),
					s.getPeristalsis(), s.getPhase())  );
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
