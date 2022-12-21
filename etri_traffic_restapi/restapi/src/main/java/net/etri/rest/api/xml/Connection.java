package net.etri.rest.api.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
  <p>클래스 설명 : Connection정보 XML처리를 위한 클래스</p>
  <p>Connection</p>
  <pre>
   net.etri.rest.api.xml
          └ Connection.java
  </pre>
**/
@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(propOrder={"linkIndex", "tl", "toLane", "fromLane", "to_edge", "from_edge"})
@XmlType(propOrder={"from_edge", "to_edge", "fromLane", "toLane", "tl", "linkIndex"})
@JsonInclude(Include.NON_NULL)
public class Connection {
	@XmlAttribute(name = "from", required = true)
	private String from_edge = null;	//referenced edge id	The name of the edge the vehicles leave	
	@XmlAttribute(name = "to", required = true)
	private String to_edge = null;		//referenced edge id	The name of the edge the vehicles may reach when leaving "from"
	@XmlAttribute(name = "fromLane")
	private int fromLane;		//the lane index of the incoming lane (numbers starting with 0)
	@XmlAttribute(name = "toLane", required = true)
	private int toLane;			//the lane index of the outgoing lane (numbers starting with 0)
	@XmlAttribute(name = "tl")
	private String tl = null;		
	@XmlAttribute(name = "linkIndex")//id of the traffic light which controls this connection
	private String linkIndex;	// <phase>-elements which controls this connection
	
	/**
     @return the from_edge
	*/
	public String getFrom_edge() {
		return from_edge;
	}
	/**
  	 @param from_edge the from_edge to set
	*/
	public void setFrom_edge(String from_edge) {
		this.from_edge = from_edge;
		if(this.from_edge.isEmpty()){
			this.from_edge = null;
		}
	}
	/**
     @return the to_edge
	*/
	public String getTo_edge() {
		return to_edge;
	}
	
	/**
     @return the fromLane
	*/
	public int getFromLane() {
		return fromLane;
	}
	
	/**
  	 @param to_edge the to_edge to set
	*/
	public void setTo_edge(String to_edge) {
		this.to_edge = to_edge;
		if(this.to_edge.isEmpty()){
			this.to_edge = null;
		}
	}
	/**
     @param fromLane the fromLane to set
	*/
	public void setFromLane(int fromLane) {
		this.fromLane = fromLane;
	}
	/**
     @return the toLane
	*/
	public int getToLane() {
		return toLane;
	}
	/**
  	 @param toLane the toLane to set
	*/
	public void setToLane(int toLane) {
		this.toLane = toLane;
	}
	/**
     @return the tl
	*/
	public String getTl() {
		return tl;
	}
	/**
  	 @param tl the tl to set
	*/
	public void setTl(String tl) {
		this.tl = tl;
		if(this.tl.isEmpty()){
			this.tl = null;
		}
	}
	/**
     @return the linkIndex
	*/
	public String getLinkIndex() {
		return linkIndex;
	}
	/**
  	 @param linkIndex the linkIndex to set
	*/
	public void setLinkIndex(String linkIndex) {
		this.linkIndex = linkIndex;
	}
	
	
}
