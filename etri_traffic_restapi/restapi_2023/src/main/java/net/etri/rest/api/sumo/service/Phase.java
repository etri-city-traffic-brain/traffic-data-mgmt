package net.etri.rest.api.sumo.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
<p>클래스 설명 : Phase정보 XML처리를 위한 클래스</p>
<p>Phase</p>
<pre>
 net.etri.rest.api.sumo.service
        └ Phase.java
</pre>
**/
@XmlAccessorType(XmlAccessType.NONE)
@JsonInclude(Include.NON_NULL)
public class Phase {	
	private int seq = 0;
	@XmlAttribute(name = "duration")
	private int duration;
	@XmlAttribute(name = "state")
	private String state = null;
	
	private int yellowDuration = 0;
	private int redDuration = 0;
	
	public Phase() {
		
	}
	
	public Phase(int seq, int duration, String state) {
		this.seq = seq;
		this.duration = duration;
		this.state = state;
	}
	
	public Phase(int seq, int duration, int yellowDuration, int redDuration, String state) {
		this.seq = seq;
		this.duration = duration;
		this.yellowDuration = yellowDuration;
		this.redDuration = redDuration;
		this.state = state;
	}

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}
	/**
	 * @param duration the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
		if(this.state.isEmpty()){
			this.state = null;
		}
	}
	
	/**
	 * @return the seq
	 */
	public int getSeq() {
		return seq;
	}
	/**
	 * @param seq the seq to set
	 */
	public void setSeq(int seq) {
		this.seq = seq;
	}
	/**
	 * @return the yellowDuration
	 */
	public int getYellowDuration() {
		return yellowDuration;
	}
	/**
	 * @param yellowDuration the yellowDuration to set
	 */
	public void setYellowDuration(int yellowDuration) {
		this.yellowDuration = yellowDuration;
	}
	/**
	 * @return the redDuration
	 */
	public int getRedDuration() {
		return redDuration;
	}
	/**
	 * @param redDuration the redDuration to set
	 */
	public void setRedDuration(int redDuration) {
		this.redDuration = redDuration;
	}
	
}
