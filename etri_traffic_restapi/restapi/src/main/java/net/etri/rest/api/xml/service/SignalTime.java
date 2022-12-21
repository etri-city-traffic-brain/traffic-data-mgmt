package net.etri.rest.api.xml.service;

/**
  <p>클래스 설명 : 신호별시간정보 저장을 위한 클래스</p>
  <p>SignalTime</p>
  <pre>
   net.etri.rest.api.xml.service
          └ SignalTime.java
  </pre>
**/
public class SignalTime {

	private String signal_id = null;
	private String version = null;
	private String signal_type;
	private short tm1 = 0;
	private short tm2 = 0;
	private short tm3 = 0;
	private short tm4 = 0;
	private short tm5 = 0;
	private short tm6 = 0;

	
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
     @return the signal_type
	*/
	public String getSignal_type() {
		return signal_type;
	}
	/**
  	 @param signal_type the signal_type to set
	*/
	public void setSignal_type(String signal_type) {
		this.signal_type = signal_type;
	}
	/**
     @return the tm1
	*/
	public short getTm1() {
		return tm1;
	}
	/**
  	 @param tm1 the tm1 to set
	*/
	public void setTm1(short tm1) {
		this.tm1 = tm1;
	}
	/**
     @return the tm2
	*/
	public short getTm2() {
		return tm2;
	}
	/**
  	 @param tm2 the tm2 to set
	*/
	public void setTm2(short tm2) {
		this.tm2 = tm2;
	}
	/**
     @return the tm3
	*/
	public short getTm3() {
		return tm3;
	}
	/**
  	 @param tm3 the tm3 to set
	*/
	public void setTm3(short tm3) {
		this.tm3 = tm3;
	}
	/**
     @return the tm4
	*/
	public short getTm4() {
		return tm4;
	}
	/**
  	 @param tm4 the tm4 to set
	*/
	public void setTm4(short tm4) {
		this.tm4 = tm4;
	}
	
	/**
	 * @return the tm5
	 */
	public short getTm5() {
		return tm5;
	}
	/**
	 * @param tm5 the tm5 to set
	 */
	public void setTm5(short tm5) {
		this.tm5 = tm5;
	}
	/**
	 * @return the tm6
	 */
	public short getTm6() {
		return tm6;
	}
	/**
	 * @param tm6 the tm6 to set
	 */
	public void setTm6(short tm6) {
		this.tm6 = tm6;
	}
	/* (non-Javadoc)
	 @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("signal_id : ").append(signal_id);
		sb.append(", version : ").append(version);
		sb.append(", signal_type : ").append(signal_type);
		sb.append(", tm1 : ").append(tm1);
		sb.append(", tm2 : ").append(tm2);
		sb.append(", tm3 : ").append(tm3);
		sb.append(", tm4 : ").append(tm4);
		sb.append(", tm5 : ").append(tm5);
		sb.append(", tm6 : ").append(tm6);
		return sb.toString();
	}	
}