package net.etri.rest.api.xml.service;

/**
  <p>클래스 설명 : 신호현시정보 저장을 위한 클래스</p>
  <p>SignalPhase</p>
  <pre>
   net.etri.rest.api.xml.service
          └ SignalPhase.java
  </pre>
**/
public class SignalPhase {

	private String signal_id = null;
	private String version = null;
	private int pattern_id = 0;
	private int phase = 0;
	private String state = null;
	private int duration = 0;
	private int yellow_duration = 0;
	private int red_duration = 0;
	
	/**
	 Constructor of SignalPhase.java class
	*/
	public SignalPhase(){
		
	}
	/**
	 Constructor of SignalPhase.java class
	 @param id 신호ID
	 @param ver 버전
	 @param p 패턴ID
	 @param a 현시번호
	 @param s 현시상태
	 @param d 현시유지시간
	 @param y 황색신호 유지시간
	 @param r 전적색신호 유지시간
	*/
	public SignalPhase(String id, String ver, int p, int a, String s, int d, int y, int r){
		this.signal_id = id;
		this.version = ver;
		this.pattern_id = p;
		this.phase = a;
		this.state = s;
		this.duration = d;
		this.yellow_duration = y;
		this.red_duration = r;
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
	
	/* (non-Javadoc)
	 @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("signal_id : ").append(signal_id);
		sb.append(", version : ").append(version);
		sb.append(", pattern_id : ").append(pattern_id);
		sb.append(", state : ").append(state);
		sb.append(", duration : ").append(duration);
		sb.append(", yellow_duration : ").append(yellow_duration);
		sb.append(", red_duration : ").append(red_duration);
		return sb.toString();
	}	
}