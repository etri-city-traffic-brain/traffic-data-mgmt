/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.xml
 * @file TrafficSignals.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일			버전			수정자		수정내용
 * -------------	--------	------		-------------------------------
 * 2018. 9. 21.		0.0.1		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
  <p>클래스 설명 : TrafficSignal정보목록 XML처리를 위한 클래스</p>
  <p>TrafficSignals</p>
  <pre>
   net.solufine.rest.api.xml
          └ TrafficSignals.java
  </pre>
  @author  redkaras 
  @since  2018. 9. 21.
  @version 1.0
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
