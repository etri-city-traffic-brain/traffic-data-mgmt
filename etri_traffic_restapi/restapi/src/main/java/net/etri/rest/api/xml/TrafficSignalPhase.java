package net.etri.rest.api.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
  <p>클래스 설명 : TrafficSignalPhase정보 XML처리를 위한 클래스</p>
  <p>TrafficSignalPhase</p>
  <pre>
   net.etri.rest.api.xml
          └ TrafficSignalPhase.java
  </pre>
**/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"state", "duration", "minDur", "maxDur"})
@JsonInclude(Include.NON_NULL)
public class TrafficSignalPhase {

	@XmlAttribute(name = "duration")
	private int duration = 0;	
	@XmlAttribute(name = "state")
	private String state = null;
	//////////////////////////////////////////////////////
	@XmlAttribute(name = "minDur")
	private String minDur = null;
	@XmlAttribute(name = "maxDur")
	private String maxDur = null;
	//////////////////////////////////////////////////////
	/**
	 Constructor of TrafficSignalPhase.java class
	*/
	public TrafficSignalPhase(){
		
	}
	/**
	 Constructor of TrafficSignalPhase.java class
	 @param d
	 @param s	 
	*/
	public TrafficSignalPhase(int d, String s){
		this.duration = d;
		this.state = s;
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
	
	
	
	public TrafficSignalPhase(int d, String s, String minDur, String maxDur){
		this.duration = d;
		this.state = s;
		this.minDur = minDur;
		this.maxDur = maxDur;
	}	

	
	public String getMinDur() {
		return minDur;
	}
	public void setMinDur(String minDur) {
		this.minDur = minDur;
	}
	public String getMaxDur() {
		return maxDur;
	}
	public void setMaxDur(String maxDur) {
		this.maxDur = maxDur;
	}
	
}
