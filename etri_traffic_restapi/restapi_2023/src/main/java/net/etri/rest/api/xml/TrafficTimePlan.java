
package net.etri.rest.api.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
  <p>클래스 설명 : TrafficSignal정보 XML처리를 위한 클래스 </p>
  <p>TrafficSignalTime</p>

**/

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"timePlan", "signalType", "tm1", "tm2", "tm3", "tm4", "tm5", "tm6"})
@JsonInclude(Include.NON_NULL)
public class TrafficTimePlan {
	
	@XmlAttribute(name = "timePlan")
	private String timePlan = null;
	@XmlAttribute(name = "signalType")
	private String signalType = null;
	@XmlAttribute(name = "tm1")
	private String tm1 = "";
	@XmlAttribute(name = "tm2")
	private String tm2 = "";
	@XmlAttribute(name = "tm3")
	private String tm3 = "";
	@XmlAttribute(name = "tm4")
	private String tm4 = "";
	@XmlAttribute(name = "tm5")
	private String tm5 = "";
	@XmlAttribute(name = "tm6")
	private String tm6 = "";
	

	
	/////////////////////////////////////////////////////////
	//  2차 작업용

	public TrafficTimePlan(int timePlan, String signalType, 
			short tm1, short tm2, short tm3, short tm4, short tm5, short tm6) {
		this.timePlan = String.valueOf(timePlan);
		this.signalType = signalType;
		this.tm1 = String.valueOf(tm1);
		this.tm2 = String.valueOf(tm2);
		this.tm3 = String.valueOf(tm3);
		this.tm4 = String.valueOf(tm4);
		this.tm5 = String.valueOf(tm5);
		this.tm6 = String.valueOf(tm6);
	}
	/////////////////////////////////////////////////////////
	
	/**
	 Constructor of TrafficSignalTime.java class
	 */
	public TrafficTimePlan(){
		
	}


	public String getTimePlan() {
		return timePlan;
	}

	public void setTimePlan(String timePlan) {
		this.timePlan = timePlan;
	}
	

	public String getSignalType() {
		return signalType;
	}

	public void setSignalType(String signalType) {
		this.signalType = signalType;
	}
	
	
	public String getTm1() {
		return tm1;
	}

	public void setTm1(String tm1) {
		this.tm1 = tm1;
	}

	public String getTm2() {
		return tm2;
	}

	public void setTm2(String tm2) {
		this.tm2 = tm2;
	}
	
	
	public String getTm3() {
		return tm3;
	}

	public void setTm3(String tm3) {
		this.tm3 = tm3;
	}
	
	
	public String getTm4() {
		return tm4;
	}

	public void setTm4(String tm4) {
		this.tm4 = tm4;
	}
	
	
	public String getTm5() {
		return tm5;
	}

	public void setTm5(String tm5) {
		this.tm5 = tm5;
	}

	public String getTm6() {
		return tm6;
	}

	public void setTm6(String tm6) {
		this.tm6 = tm6;
	}
	
}
