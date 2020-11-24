/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.json.service
 * @file TrafficSignal.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일			버전			수정자		수정내용
 * -------------	--------	------		-------------------------------
 * 2019. 6. 21.		0.0.1		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.json.service;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
<p>클래스 설명 : TrafficSignal정보 JSON처리를 위한 클래스</p>
<p>TrafficSignal</p>
<pre>
 net.solufine.rest.api.json.service
        └ TrafficSignal.java
</pre>
@author  redkaras 
@since  2019. 6. 21.
@version 1.0
**/
@JsonInclude(Include.ALWAYS)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrafficSignal {

	@JsonProperty("id")
	private String signal_id = null;
	@JsonProperty("version")
	private String version = null;
	@JsonProperty("crossNo")
	private String cross_no = null;
	@JsonProperty("crossName")
	private String cross_nm = null;
	@JsonProperty("date")
	private String eff_date = null;
	@JsonProperty("editDate")
	private String edit_date = null;
	@JsonProperty("signalGroup")
	private String signal_group = null;
	@JsonProperty("policeStation")
	private String police_station = null;
	@JsonProperty("description")
	private String description = null;
	@JsonProperty("signalPhaseDefault")
	private List<SignalPhaseDefault> signalPhaseDefault = null;
	@JsonProperty("signalPhase")
	private List<SignalPhase> signalPhase = null;
	@JsonProperty("todPlan")
	private List<TodPlan> todPlan = null;
	@JsonProperty("signalScenario")
	private List<SignalScenario> signalScenario = null;
	
	/**
	 * @return the signal_id
	 */
	public String getSignal_id() {
		return signal_id;
	}
	/**
	 * @param signal_id the signal_id to set
	 */
	public void setSignal_id(String signal_id) {
		this.signal_id = signal_id;
	}
	/**
	 * @return the eff_date
	 */
	public String getEff_date() {
		return eff_date;
	}
	/**
	 * @param eff_date the eff_date to set
	 */
	public void setEff_date(String eff_date) {
		this.eff_date = eff_date;
	}
	/**
	 * @return the signal_group
	 */
	public String getSignal_group() {
		return signal_group;
	}
	/**
	 * @param signal_group the signal_group to set
	 */
	public void setSignal_group(String signal_group) {
		this.signal_group = signal_group;
	}
	/**
	 * @return the police_station
	 */
	public String getPolice_station() {
		return police_station;
	}
	/**
	 * @param police_station the police_station to set
	 */
	public void setPolice_station(String police_station) {
		this.police_station = police_station;
	}
	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	
	/**
	 * @return the cross_no
	 */
	public String getCross_no() {
		return cross_no;
	}
	/**
	 * @param cross_no the cross_no to set
	 */
	public void setCross_no(String cross_no) {
		this.cross_no = cross_no;
	}
	/**
	 * @return the cross_nm
	 */
	public String getCross_nm() {
		return cross_nm;
	}
	/**
	 * @param cross_nm the cross_nm to set
	 */
	public void setCross_nm(String cross_nm) {
		this.cross_nm = cross_nm;
	}
	
	/**
	 * @return the edit_date
	 */
	public String getEdit_date() {
		return edit_date;
	}
	/**
	 * @param edit_date the edit_date to set
	 */
	public void setEdit_date(String edit_date) {
		this.edit_date = edit_date;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the signalPhaseDefault
	 */
	public List<SignalPhaseDefault> getSignalPhaseDefault() {
		return signalPhaseDefault;
	}
	/**
	 * @param signalPhaseDefault the signalPhaseDefault to set
	 */
	public void setSignalPhaseDefault(List<SignalPhaseDefault> signalPhaseDefault) {
		this.signalPhaseDefault = signalPhaseDefault;
	}
	/**
	 * @return the signalPhase
	 */
	public List<SignalPhase> getSignalPhase() {
		return signalPhase;
	}
	/**
	 * @param signalPhase the signalPhase to set
	 */
	public void setSignalPhase(List<SignalPhase> signalPhase) {
		this.signalPhase = signalPhase;
	}
	/**
	 * @return the todPlan
	 */
	public List<TodPlan> getTodPlan() {
		return todPlan;
	}
	/**
	 * @param todPlan the todPlan to set
	 */
	public void setTodPlan(List<TodPlan> todPlan) {
		this.todPlan = todPlan;
	}
	/**
	 * @return the signalScenario
	 */
	public List<SignalScenario> getSignalScenario() {
		return signalScenario;
	}
	/**
	 * @param signalScenario the signalScenario to set
	 */
	public void setSignalScenario(List<SignalScenario> signalScenario) {
		this.signalScenario = signalScenario;
	}
	
	public void addTodPlan(String plan_id, int seq, int pattern_id, String from_time, String to_time) {
		if(todPlan == null) {
			todPlan = new LinkedList<TodPlan>();			
		}
		
		for(int i = 0; i < todPlan.size(); i++) {
			if( todPlan.get(i).getPlan_id() == plan_id || todPlan.get(i).getPlan_id().equals(plan_id) ) {
				todPlan.get(i).addTod(seq, pattern_id, from_time, to_time);
				return;
			}
		}
		
		TodPlan t = new TodPlan(plan_id);
		t.addTod(seq, pattern_id, from_time, to_time);
		todPlan.add( t );
	}
}
