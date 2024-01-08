package net.etri.rest.api.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
  <p>클래스 설명 : Border정보 XML처리를 위한 클래스</p>
  <p>Border</p>
  <pre>
   net.etri.rest.api.xml
          └ Border.java
  </pre>
**/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"toOwner", "fromOwner", "to", "from", "edge", "id"})
@JsonInclude(Include.NON_NULL)
public class Border {

	@XmlAttribute(name = "id")
	private String id = null; 
	@XmlAttribute(name = "edge")
	private String edge = null; 
	@XmlAttribute(name = "from")
	private String from = null; 
	@XmlAttribute(name = "to")
	private String to = null;
	@XmlAttribute(name = "fromOwner")
	private String fromOwner = null; 
	@XmlAttribute(name = "toOwner")
	private String toOwner = null;
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
	}
	/**
     @return the edge
	*/
	public String getEdge() {
		return edge;
	}
	/**
  	 @param edge the edge to set
	*/
	public void setEdge(String edge) {
		this.edge = edge;
	}
	/**
     @return the from
	*/
	public String getFrom() {
		return from;
	}
	/**
  	 @param from the from to set
	*/
	public void setFrom(String from) {
		this.from = from;
	}
	/**
     @return the to
	*/
	public String getTo() {
		return to;
	}
	/**
  	 @param to the to to set
	*/
	public void setTo(String to) {
		this.to = to;
	}
	/**
     @return the fromOwner
	*/
	public String getFromOwner() {
		return fromOwner;
	}
	/**
  	 @param fromOwner the fromOwner to set
	*/
	public void setFromOwner(String fromOwner) {
		this.fromOwner = fromOwner;
	}
	/**
     @return the toOwner
	*/
	public String getToOwner() {
		return toOwner;
	}
	/**
  	 @param toOwner the toOwner to set
	*/
	public void setToOwner(String toOwner) {
		this.toOwner = toOwner;
	}
	
	
}