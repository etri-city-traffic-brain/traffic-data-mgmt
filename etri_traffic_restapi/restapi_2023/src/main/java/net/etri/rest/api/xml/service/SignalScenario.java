package net.etri.rest.api.xml.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
  <p>클래스 설명 : 신호시나리오정보 저장을 위한 클래스</p>
  <p>SignalScenario</p>
  <pre>
   net.etri.rest.api.xml.service
          └ SignalScenario.java
  </pre>
**/
public class SignalScenario {

	private String signal_id = null;
	private String version = null;	
	private int pattern_id = 0;
	private int scenario_no = 0;
	private int cycle = 0;	
	private int peristalsis = 0;	
	private List<SignalPhase> phase = new ArrayList<SignalPhase>();
	/**
	 Constructor of SignalScenario.java class
	*/
	public SignalScenario(){
		
	}
	/**
	 Constructor of SignalScenario.java class
	 @param id 신호ID
	 @param ver 버전
	 @param p 패턴ID
	 @param s 시나리오번호
	 @param c 주기
	 @param o 연동
	*/
	public SignalScenario(String id, String ver, int p, int s, int c, int o){
		this.signal_id = id;
		this.version = ver;	
		this.pattern_id = p;
		this.scenario_no = s;
		this.cycle = c;	
		this.peristalsis = o;
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
	public List<SignalPhase> getPhase() {
		return phase;
	}
	/**
  	 @param phase the phase to set
	*/
	public void setPhase(List<SignalPhase> phase) {
		this.phase = phase;
	}
	/**
     @return the phase
	*/
	public SignalPhase getPhase(String signal_id, String version, int pattern_id, int phase) {
		Iterator<SignalPhase> it = this.getPhase().iterator();
		while(it.hasNext()){
			SignalPhase p = it.next();
			if( p.getSignal_id() == signal_id || p.getSignal_id().equals(signal_id))
				if( p.getVersion() == version || p.getVersion().equals(version))
					if( p.getPhase() == phase)
						return p;
		}		
		return null;
	}
	
	/* (non-Javadoc)
	 @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("signal_id : ").append(signal_id);
		sb.append(", version : ").append(version);
		sb.append(", pattern_id : ").append(pattern_id);
		sb.append(", scenario_no : ").append(scenario_no);
		sb.append(", cycle : ").append(cycle);
		sb.append(", peristalsis : ").append(peristalsis);
		return sb.toString();
	}	
}
	
