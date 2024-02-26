package net.etri.rest.api.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
  <p>클래스 설명 : Unexpected정보 XML처리를 위한 클래스</p>
  <p>Unexpected</p>
  <pre>
   net.etri.rest.api.xml
          └ Unexpected.java
  </pre>
**/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"sudn_st_id", "sudn_st_large_cd", "lane_no", "long_cd", "sudn_st_st_sdttm", "sudn_st_ed_sdttm", "control_time", "occur_link_id", "sudn_st_cont"})
@JsonInclude(Include.NON_NULL)
public class Unexpected {
	@XmlAttribute(name = "sudn_st_id", required = true)
	private String sudn_st_id = null; //돌발상황id
	@XmlAttribute(name = "sudn_st_large_cd")
	private String sudn_st_large_cd = null; //
	@XmlAttribute(name = "lane_no")
	private String lane_no = null; //통제차로수
	@XmlAttribute(name = "long_cd")
	private String long_cd = null; //지속돌발
	@XmlAttribute(name = "sudn_st_st_sdttm")
	private String sudn_st_st_sdttm = null; //돌발정보 유효시작시각
	@XmlAttribute(name = "sudn_st_ed_sdttm")
	private String sudn_st_ed_sdttm = null; //돌발정보 유효종료시각
	@XmlAttribute(name = "control_time")
	private String control_time = null; //통제시간
	@XmlAttribute(name = "occur_link_id")
	private String occur_link_id = null; //매핑 tsdlink_id
	
	@XmlAttribute(name = "sudn_st_cont")
	private String sudn_st_cont = null; //돌발정보 설명 텍스트
	  
	/**
	  Constructor of Unexpected.java class
	*/
	public Unexpected() {
		
	}
	/**
     @return the sudn_st_id
	*/
	public String getSudn_st_id() {
		return sudn_st_id;
	}
	/**
  	 @param sudn_st_id the sudn_st_id to set
	*/
	public void setSudn_st_id(String sudn_st_id) {
		this.sudn_st_id = sudn_st_id;
		if(this.sudn_st_id.isEmpty()){
			this.sudn_st_id = null;
		}
	}
	/**
     @return the sudn_st_large_cd
	*/
	public String getSudn_st_large_cd() {
		return sudn_st_large_cd;
	}
	/**
  	 @param sudn_st_large_cd the sudn_st_large_cd to set
	*/
	public void setSudn_st_large_cd(String sudn_st_large_cd) {
		this.sudn_st_large_cd = sudn_st_large_cd;
		if(this.sudn_st_large_cd.isEmpty()){
			this.sudn_st_large_cd = null;
		}
	}
	/**
     @return the lane_no
	*/
	public String getLane_no() {
		return lane_no;
	}
	/**
  	 @param lane_no the lane_no to set
	*/
	public void setLane_no(String lane_no) {
		this.lane_no = lane_no;
		if(this.lane_no.isEmpty()){
			this.lane_no = null;
		}
	}
	/**
     @return the long_cd
	*/
	public String getLong_cd() {
		return long_cd;
	}
	/**
  	 @param long_cd the long_cd to set
	*/
	public void setLong_cd(String long_cd) {
		this.long_cd = long_cd;
		if(this.long_cd.isEmpty()){
			this.long_cd = null;
		}
	}
	/**
     @return the sudn_st_st_sdttm
	*/
	public String getSudn_st_st_sdttm() {
		return sudn_st_st_sdttm;
	}
	/**
  	 @param sudn_st_st_sdttm the sudn_st_st_sdttm to set
	*/
	public void setSudn_st_st_sdttm(String sudn_st_st_sdttm) {
		this.sudn_st_st_sdttm = sudn_st_st_sdttm;
		if(this.sudn_st_st_sdttm.isEmpty()){
			this.sudn_st_st_sdttm = null;
		}
	}
	/**
     @return the sudn_st_ed_sdttm
	*/
	public String getSudn_st_ed_sdttm() {
		return sudn_st_ed_sdttm;
	}
	/**
  	 @param sudn_st_ed_sdttm the sudn_st_ed_sdttm to set
	*/
	public void setSudn_st_ed_sdttm(String sudn_st_ed_sdttm) {
		this.sudn_st_ed_sdttm = sudn_st_ed_sdttm;
		if(this.sudn_st_ed_sdttm.isEmpty()){
			this.sudn_st_ed_sdttm = null;
		}
	}
	/**
     @return the control_time
	*/
	public String getControl_time() {
		return control_time;
	}
	/**
  	 @param control_time the control_time to set
	*/
	public void setControl_time(String control_time) {
		this.control_time = control_time;
		if(this.control_time.isEmpty()){
			this.control_time = null;
		}
	}
	/**
     @return the occur_link_id
	*/
	public String getOccur_link_id() {
		return occur_link_id;
	}
	/**
  	 @param occur_link_id the occur_link_id to set
	*/
	public void setOccur_link_id(String occur_link_id) {
		this.occur_link_id = occur_link_id;
		if(this.occur_link_id.isEmpty()){
			this.occur_link_id = null;
		}
	}
	/**
     @return the sudn_st_cont
	*/
	public String getSudn_st_cont() {
		return sudn_st_cont;
	}
	/**
  	 @param sudn_st_cont the sudn_st_cont to set
	*/
	public void setSudn_st_cont(String sudn_st_cont) {
		this.sudn_st_cont = sudn_st_cont;
		if(this.sudn_st_cont.isEmpty()){
			this.sudn_st_cont = null;
		}
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("sudn_st_id : ").append(sudn_st_id);
		sb.append(", sudn_st_large_cd : ").append(sudn_st_large_cd);
		sb.append(", lane_no : ").append(lane_no);
		sb.append(", long_cd : ").append(long_cd);
		sb.append(", sudn_st_st_sdttm : ").append(sudn_st_st_sdttm);
		sb.append(", sudn_st_ed_sdttm : ").append(sudn_st_ed_sdttm);
		sb.append(", control_time : ").append(control_time);
		sb.append(", occur_link_id : ").append(occur_link_id);
		sb.append(", sudn_st_cont : ").append(sudn_st_cont);
		
		return sb.toString();
	}
	
}
