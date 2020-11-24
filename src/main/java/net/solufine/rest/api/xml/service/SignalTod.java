/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.xml.service
 * @file SignalTod.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일			버전			수정자		수정내용
 * -------------	--------	------		-------------------------------
 * 2018. 9. 21.		0.0.1		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.xml.service;

/**
  <p>클래스 설명 : TOD플랜정보 저장을 위한 클래스</p>
  <p>SignalTod</p>
  <pre>
   net.solufine.rest.api.xml.service
          └ SignalTod.java
  </pre>
  @author  redkaras 
  @since  2018. 9. 21.
  @version 1.0
**/
public class SignalTod {

	private String signal_id = null;
	private String version = null;	
	private String plan_id = null;
	private int seq = 0;	
	private int pattern_id = 0;
	private String from_time = null;
	private String to_time = null;	
	private int start_time;
	
	private int cycle = 0;
	private int offset = 0;	
	private String req_date = null;
	private String req_from_time = null;
	private String req_time = null;
	/**
	 Constructor of SignalScenario.java class 
	*/
	public SignalTod(){
		
	}
	/**
	 Constructor of SignalScenario.java class 
	 @param id 신호ID
	 @param ver	버전
	 @param plan 플랜ID
	 @param  s 순번
	 @param  p 패턴ID
	 @param  f 시작시간
	 @param  t 종료시간
	 @param  start_time 누적 시작시간
	 @param  c 주기
	 @param  o 연동
	 @param  req_d 요청일자
	 @param  req_f 요청시작시간
	 @param  req_t 요청종료시간
	*/
	public SignalTod(String id, String ver, String plan, int s, int p, String f, String t, 
			int start_time, int c, int o, String req_d, String req_f, String req_t){
		this.signal_id = id;
		this.version = ver;	
		this.plan_id = plan;
		this.seq = s;	
		this.pattern_id = p;
		this.from_time = f;
		this.to_time = t;
		this.start_time = start_time;
		this.cycle = c;
		this.offset = o;
		this.req_date = req_d;
		this.req_from_time = req_f;
		this.req_time = req_t;
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
     @return the req_date
	*/
	public String getReq_date() {
		return req_date;
	}
	
	public String getReq_date(String Separator) {
		if(req_date == null || req_date.isEmpty() || req_date == "" || req_date.length() < 8)
			return req_date;
		return req_date.substring(0, 4) + Separator + req_date.substring(4, 6) + Separator + req_date.substring(6, 8);
	}
	/**
     @param req_date the req_date to set
	*/
	public void setReq_date(String req_date) {
		this.req_date = req_date;
	}
	/**
     @return the req_from_time
	*/
	public String getReq_from_time() {
		return req_from_time;
	}

	public String getReq_from_time(String Separator) {
		if(req_from_time == null || req_from_time.isEmpty() || req_from_time == "" || req_from_time.length() < 6)
			return req_from_time;
		return req_from_time.substring(0, 2) + Separator + req_from_time.substring(2, 4) + Separator + req_from_time.substring(4, 6);
	}
	/**
  	 @param req_from_time the req_from_time to set
	*/
	public void setReq_from_time(String req_from_time) {
		this.req_from_time = req_from_time;
	}
	/**
     @return the req_time
	*/
	public String getReq_time() {
		return req_time;
	}
	/**
  	 @param req_time the req_time to set
	*/
	public void setReq_time(String req_time) {
		this.req_time = req_time;
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
		sb.append(", pattern_id : ").append(pattern_id);
		sb.append(", from_time : ").append(from_time);
		sb.append(", to_time : ").append(to_time);
		sb.append(", start_time : ").append(start_time);
		sb.append(", cycle : ").append(cycle);
		sb.append(", offset : ").append(offset);
		sb.append(", req_date : ").append(req_date);
		sb.append(", req_from_time : ").append(req_from_time);
		sb.append(", req_time : ").append(req_time);
		return sb.toString();
	}	
}
