package net.etri.rest.api.xml.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.etri.rest.api.xml.type.TlLogicType;

/**
  <p>클래스 설명 : 신호정보 저장을 위한 클래스</p>
  <p>Signal</p>
  <pre>
   net.etri.rest.api.xml.service
          └ Signal.java
  </pre>
**/
public class Signal {

	private String signal_id = null;
	private String version = null;
	private String cross_no = null;
	private String cross_nm = null;
	private String eff_date = null;
	private String signal_group = null;
	private List<SignalTod> tod = new ArrayList<SignalTod>();
	private List<SignalScenario> schedule = new ArrayList<SignalScenario>();
	///////////////////////////////////////////////////////////
	private List<SignalTime> signalTime = new ArrayList<SignalTime>();
	///////////////////////////////////////////////////////////	
	private TlLogicType tllogictype;
	private String std_id = null;		
	private String police_station = null;
	
	/**
     @return the signal_id
	*/
	public String getSignal_id() {
		return signal_id;
	}
	/**
  	 @param signal_id the signal_id to set
	*/
	public void setSignal_id(String signal_id) {
		this.signal_id = signal_id;
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
     @return the cross_no
	*/
	public String getCross_no() {
		return cross_no;
	}
	/**
  	 @param cross_no the cross_no to set
	*/
	public void setCross_no(String cross_no) {
		this.cross_no = cross_no;
	}
	/**
     @return the cross_nm
	*/
	public String getCross_nm() {
		return cross_nm;
	}
	/**
  	 @param cross_nm the cross_nm to set
	*/
	public void setCross_nm(String cross_nm) {
		this.cross_nm = cross_nm;
	}
	/**
     @return the eff_date
	*/
	public String getEff_date() {
		return eff_date;
	}
	/**
  	 @param eff_date the eff_date to set
	*/
	public void setEff_date(String eff_date) {
		this.eff_date = eff_date;
	}
	/**
     @return the signal_group
	*/
	public String getSignal_group() {
		// SALT NetEdit 에서 로딩 오류 발생을 방지하기 위해 예외처리
		if( signal_group.equals(""))
			return signal_group = " ";
		else
			return signal_group;
	}
	/**
  	 @param signal_group the signal_group to set
	*/
	public void setSignal_group(String signal_group) {
		this.signal_group = signal_group;
	}
	/**
     @return the tod
	*/
	public List<SignalTod> getTod() {
		return tod;
	}
	
	/**
     @return the tod
	*/
	public SignalTod getTod(String signal_id, String version, String plan_id, int seq, int pattern_id, String reqDate) {
		Iterator<SignalTod> it = this.getTod().iterator();
		while(it.hasNext()){
			SignalTod t = it.next();
			if( t.getSignal_id() == signal_id || t.getSignal_id().equals(signal_id))
				if( t.getVersion() == version || t.getVersion().equals(version))
					if( t.getPlan_id() == plan_id || t.getPlan_id().equals(plan_id))
						if( t.getSeq() == seq)
							if( t.getPattern_id() == pattern_id)
								if( t.getReq_date() == reqDate || t.getReq_date().equals(reqDate))
									return t;
		}
		return null;
	}
	/**
  	 @param tod the tod to set
	*/
	public void setTod(List<SignalTod> tod) {
		this.tod = tod;
	}
	/**
     @return the schedule
	*/
	public List<SignalScenario> getSchedule() {
		return schedule;
	}
	
	public SignalScenario getSchedule(String signal_id, String version, int pattern_id, int scenario_no) {
		Iterator<SignalScenario> it = this.getSchedule().iterator();
		while(it.hasNext()){
			SignalScenario s = it.next();
			if( s.getSignal_id() == signal_id || s.getSignal_id().equals(signal_id))
				if( s.getVersion() == version || s.getVersion().equals(version))
						if( s.getPattern_id() == pattern_id)
							if( s.getScenario_no() == scenario_no)
								return s;
		}
		return null;
	}
	
	/**
  	 @param schedule the schedule to set
	*/
	public void setSchedule(List<SignalScenario> schedule) {
		this.schedule = schedule;
	}
	/**
     @return the tllogictype
	*/
	public TlLogicType getTllogictype() {
		return tllogictype;
	}
	/**
  	 @param tllogictype the tllogictype to set
	*/
	public void setTllogictype(TlLogicType tllogictype) {
		this.tllogictype = tllogictype;
	}
	/**
     @return the std_id
	*/
	public String getStd_id() {
		return std_id;
	}
	/**
  	 @param std_id the std_id to set
	*/
	public void setStd_id(String std_id) {
		this.std_id = std_id;
	}
	/**
     @return the police_station
	*/
	public String getPolice_station() {
		return police_station;
	}
	/**
  	 @param police_station the police_station to set
	*/
	public void setPolice_station(String police_station) {
		this.police_station = police_station;
	}
		
	/* (non-Javadoc)
	 @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("signal_id : ").append(signal_id);
		sb.append(", version : ").append(version);
		sb.append(", cross_no : ").append(cross_no);
		sb.append(", cross_nm : ").append(cross_nm);
		sb.append(", eff_date : ").append(eff_date);
		sb.append(", signal_group : ").append(signal_group);
		sb.append(", std_id : ").append(std_id);
		sb.append(", police_station : ").append(police_station);
		return sb.toString();
	}		
	
	
	/////////////////////////////////////////////////////////////////////
	// 2차 추가 작업용
	

	public List<SignalTime> getSignalTime() {
		return signalTime;
	}

	
	public void setSignalTime(List<SignalTime> signalTime) {
		this.signalTime = signalTime;
	}
	
	
}
