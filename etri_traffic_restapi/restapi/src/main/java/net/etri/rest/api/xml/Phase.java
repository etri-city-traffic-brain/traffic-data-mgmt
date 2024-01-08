package net.etri.rest.api.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
  <p>클래스 설명 : Phase정보 XML처리를 위한 클래스</p>
  <p>Phase</p>
  <pre>
   net.etri.rest.api.xml
          └ Phase.java
  </pre>
**/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"state", "duration", "seq", "programID", "id"})
@JsonInclude(Include.NON_NULL)
public class Phase {
	
	private String id = null;
	private String programID = null;
	private String seq = null;
	@XmlAttribute(name = "duration", required = true)
	private int duration;
	@XmlAttribute(name = "state")
	private String state = null;
	
	/**
     @return the id
	*/
	public String getId() {
		return id;
	}
	/**
  	 @param id the id to set
	*/
	public void setId(String id) {
		this.id = id;
		if(this.id.isEmpty()){
			this.id = null;
		}
	}
	/**
     @return the seq
	*/
	public String getSeq() {
		return seq;
	}
	/**
  	 @param seq the seq to set
	*/
	public void setSeq(String seq) {
		this.seq = seq;
		if(this.seq.isEmpty()){
			this.seq = null;
		}
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
		if(this.state.isEmpty()){
			this.state = null;
		}
	}
	/**
     @return the programID
	*/
	public String getProgramID() {
		return programID;
	}
	/**
  	 @param programID the programID to set
	*/
	public void setProgramID(String programID) {
		this.programID = programID;
		if(this.programID.isEmpty()){
			this.programID = null;
		}
	}
	
}