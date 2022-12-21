package net.etri.rest.api.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import net.etri.rest.api.xml.type.TlLogicType;

/**
  <p>클래스 설명 : TlLogic정보 XML처리를 위한 클래스</p>
  <p>TlLogic</p>
  <pre>
   net.etri.rest.api.xml
          └ TlLogic.java
  </pre>
**/
@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(propOrder={"phases", "offset", "programID", "tlLogicType", "id" })
@XmlType(propOrder={"id", "tlLogicType", "programID", "offset", "phases", "std_id", "cross_no", "cross_nm" })
@JsonInclude(Include.NON_NULL)
public class TlLogic {
	@XmlAttribute(name = "id", required = true)
	private String id = null;
	@XmlAttribute(name = "type")
	private TlLogicType tlLogicType;
	@XmlAttribute(name = "programID")
	private String programID = null;
	@XmlAttribute(name = "offset")
	private float offset;
	@XmlElement(name="phase")
	private List<Phase> phases = null;
	@XmlAttribute(name = "std_id")
	private String std_id = null;
	@XmlAttribute(name = "cross_no")
	private String cross_no = null;
	@XmlAttribute(name = "cross_nm")
	private String cross_nm = null;
	
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
     @return the tlLogicType
	*/
	public TlLogicType getTlLogicType() {
		return tlLogicType;
	}
	/**
  	 @param tlLogicType the tlLogicType to set
	*/
	public void setTlLogicType(TlLogicType tlLogicType) {
		this.tlLogicType = tlLogicType;
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
	/**
     @return the offset
	*/
	public float getOffset() {
		return offset;
	}
	/**
  	 @param offset the offset to set
	*/
	public void setOffset(float offset) {
		this.offset = offset;
	}
	/**
     @return the phases
	*/
	public List<Phase> getPhases() {
		return phases;
	}
	/**
  	 @param phases the phases to set
	*/
	public void setPhases(List<Phase> phases) {
		this.phases = phases;
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
	
	
	
}
