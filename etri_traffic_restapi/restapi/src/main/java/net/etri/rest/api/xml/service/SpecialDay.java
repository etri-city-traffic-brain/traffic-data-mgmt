package net.etri.rest.api.xml.service;

/**
  <p>클래스 설명 : 신호별특수일정보 저장을 위한 클래스</p>
  <p>SpecialDay</p>
  <pre>
   net.etri.rest.api.xml.service
          └ SpecialDay.java
  </pre>
**/
public class SpecialDay {

	private String signal_id = null;
	private String version = null;
	private String special_date = null;
	private String special_nm = null;
	private int lunar_yn = 0;
	private String plan_id = "4";
	
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
     @return the special_date
	*/
	public String getSpecial_date() {
		return special_date;
	}
	/**
  	 @param special_date the special_date to set
	*/
	public void setSpecial_date(String special_date) {
		this.special_date = special_date;
	}
	/**
     @return the special_nm
	*/
	public String getSpecial_nm() {
		return special_nm;
	}
	/**
  	 @param special_nm the special_nm to set
	*/
	public void setSpecial_nm(String special_nm) {
		this.special_nm = special_nm.trim();
		
		if( this.special_nm == "신정" || this.special_nm.equals("신정")) return;
		if( this.special_nm == "설날전" || this.special_nm.equals("설날전")) return;
		if( this.special_nm == "설날" || this.special_nm.equals("설날")) return;
		if( this.special_nm == "설날후" || this.special_nm.equals("설날후")) return;
		if( this.special_nm == "삼일절" || this.special_nm.equals("삼일절")) return;
		if( this.special_nm == "어린이날" || this.special_nm.equals("어린이날")) return;
		if( this.special_nm == "석탄일" || this.special_nm.equals("석탄일")) return;
		if( this.special_nm == "현충일" || this.special_nm.equals("현충일")) return;
		if( this.special_nm == "광복절" || this.special_nm.equals("광복절")) return;
		if( this.special_nm == "추석전" || this.special_nm.equals("추석전")) return;
		if( this.special_nm == "추석" || this.special_nm.equals("추석")) return;
		if( this.special_nm == "추석후" || this.special_nm.equals("추석후")) return;
		if( this.special_nm == "개천절" || this.special_nm.equals("개천절")) return;
		if( this.special_nm == "한글날" || this.special_nm.equals("한글날")) return;
		if( this.special_nm == "성탄일" || this.special_nm.equals("성탄일")) return;  
		if( this.special_nm == "국군의날" || this.special_nm.equals("국군의날")) return;
		
		System.out.println(this.toString());
		
	}
	/**
     @return the lunar_yn
	*/
	public int getLunar_yn() {
		return lunar_yn;
	}
	/**
  	 @param lunar_yn the lunar_yn to set
	*/
	public void setLunar_yn(int lunar_yn) {
		this.lunar_yn = lunar_yn;
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
	
	/* (non-Javadoc)
	 @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("signal_id : ").append(signal_id);
		sb.append(", version : ").append(version);
		sb.append(", special_date : ").append(special_date);
		sb.append(", special_nm : ").append(special_nm);
		sb.append(", lunar_yn : ").append(lunar_yn);
		return sb.toString();
	}	
}