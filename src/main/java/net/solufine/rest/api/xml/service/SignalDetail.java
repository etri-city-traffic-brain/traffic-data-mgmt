/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.xml.service
 * @file SignalDetail.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일			버전			수정자		수정내용
 * -------------	--------	------		-------------------------------
 * 2018. 9. 20.		0.0.1		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.xml.service;

/**
  <p>클래스 설명 : 신호상세정보 저장을 위한 클래스</p>
  <p>SignalDetail</p>
  <pre>
   net.solufine.rest.api.xml.service
          └ SignalDetail.java
  </pre>
  @author  redkaras 
  @since  2018. 9. 20.
  @version 1.0
**/
public class SignalDetail {

	private String req_date = null;
	private String signal_id = null; 
	private String version = null; 
	private String plan_id = null; 
	private int seq; 
	private String from_time = null; 
	private String to_time = null; 
	private int pattern_id;
	private int scenario_no; 
	private int cycle; 
	private int peristalsis;
	private int phase; 
	private String state = null;
	private int duration; 
	private int yellow_duration;
	private int red_duration;
	private int start_time;
	
	/**
	 * @return the req_date
	 */
	public String getReq_date() {
		return req_date;
	}
	/**
	 * @param req_date the req_date to set
	 */
	public void setReq_date(String req_date) {
		this.req_date = req_date;
	}
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
     @return the plan_id
	*/
	public String getPlan_id() {
		return plan_id;
	}
	/**
  	 @param plan_id the plan_id to set
	*/
	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}
	/**
     @return the seq
	*/
	public int getSeq() {
		return seq;
	}
	/**
  	 @param seq the seq to set
	*/
	public void setSeq(int seq) {
		this.seq = seq;
	}
	/**
     @return the from_time
	*/
	public String getFrom_time() {
		return from_time;
	}
	/**
  	 @param from_time the from_time to set
	*/
	public void setFrom_time(String from_time) {
		this.from_time = from_time;
	}
	/**
     @return the to_time
	*/
	public String getTo_time() {
		return to_time;
	}
	/**
  	 @param to_time the to_time to set
	*/
	public void setTo_time(String to_time) {
		this.to_time = to_time;
	}
	/**
     @return the pattern_id
	*/
	public int getPattern_id() {
		return pattern_id;
	}
	/**
  	 @param pattern_id the pattern_id to set
	*/
	public void setPattern_id(int pattern_id) {
		this.pattern_id = pattern_id;
	}
	/**
     @return the scenario_no
	*/
	public int getScenario_no() {
		return scenario_no;
	}
	/**
  	 @param scenario_no the scenario_no to set
	*/
	public void setScenario_no(int scenario_no) {
		this.scenario_no = scenario_no;
	}
	/**
     @return the cycle
	*/
	public int getCycle() {
		return cycle;
	}
	/**
  	 @param cycle the cycle to set
	*/
	public void setCycle(int cycle) {
		this.cycle = cycle;
	}
	/**
     @return the peristalsis
	*/
	public int getPeristalsis() {
		return peristalsis;
	}
	/**
  	 @param peristalsis the peristalsis to set
	*/
	public void setPeristalsis(int peristalsis) {
		this.peristalsis = peristalsis;
	}
	/**
     @return the phase
	*/
	public int getPhase() {
		return phase;
	}
	/**
  	 @param phase the phase to set
	*/
	public void setPhase(int phase) {
		this.phase = phase;
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
     @return the yellow_duration
	*/
	public int getYellow_duration() {
		return yellow_duration;
	}
	/**
  	 @param yellow_duration the yellow_duration to set
	*/
	public void setYellow_duration(int yellow_duration) {
		this.yellow_duration = yellow_duration;
	}
	
	/**
	 @return the red_duration
	*/
	public int getRed_duration() {
		return red_duration;
	}
	/**
	 @param red_duration the red_duration to set
	*/
	public void setRed_duration(int red_duration) {
		this.red_duration = red_duration;
	}
	/**
     @return the start_time
	*/
	public int getStart_time() {
		return start_time;
	}
	/**
  	 @param start_time the start_time to set
	*/
	public void setStart_time(int start_time) {
		this.start_time = start_time;
	}
	
	/* (non-Javadoc)
	 @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("signal_id : ").append(signal_id);
		sb.append(", version : ").append(version);
		sb.append(", plan_id : ").append(plan_id);
		sb.append(", seq : ").append(seq);
		sb.append(", from_time : ").append(from_time);
		sb.append(", to_time : ").append(to_time);
		sb.append(", pattern_id : ").append(pattern_id);
		sb.append(", scenario_no : ").append(scenario_no);
		sb.append(", cycle : ").append(cycle);
		sb.append(", peristalsis : ").append(peristalsis);
		sb.append(", phase : ").append(phase);
		sb.append(", state : ").append(state);
		sb.append(", duration : ").append(duration);
		sb.append(", yellow_duration : ").append(yellow_duration);
		sb.append(", red_duration : ").append(red_duration);
		sb.append(", start_time : ").append(start_time);
		return sb.toString();
	}	
	
}
