package net.etri.rest.api.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
  <p>클래스 설명 : TrafficSignal정보목록 XML처리를 위한 클래스</p>
  <p>TrafficSignals</p>
  <pre>
   net.etri.rest.api.xml
          └ TrafficSignals.java
  </pre>
**/
@XmlRootElement(name="trafficSignalSystem")
@XmlAccessorType(XmlAccessType.FIELD)
public class TrafficSignals {

	@XmlElement(name="trafficSignal")
	private List<TrafficSignal> trafficSignal = null;

	/**
     @return the trafficSignal
	*/
	public List<TrafficSignal> getTrafficSignal() {
		return trafficSignal;
	}

	/**
  	 @param trafficSignal the trafficSignal to set
	*/
	public void setTrafficSignal(List<TrafficSignal> trafficSignal) {
		this.trafficSignal = trafficSignal;
		if(this.trafficSignal == null || this.trafficSignal.isEmpty()){
			this.trafficSignal = null;
		}
	}
}
