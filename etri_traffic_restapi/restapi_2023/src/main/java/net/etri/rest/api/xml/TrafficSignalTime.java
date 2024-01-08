
package net.etri.rest.api.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import net.etri.rest.api.xml.service.SignalTime;

/**
  <p>클래스 설명 : TrafficSignal정보 XML처리를 위한 클래스 </p>
  <p>TrafficSignalTime</p>

**/

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"time"})
@JsonInclude(Include.NON_NULL)
public class TrafficSignalTime {
	
	@XmlElement(name="time")
	private List<TrafficTimePlan> time = new ArrayList<TrafficTimePlan>(); 

	/**
	 Constructor of TrafficSignalTime.java class
	 */
	public TrafficSignalTime(){
		
	}
	
	public TrafficSignalTime(List<SignalTime> signaltime) {
	
		
		Iterator<SignalTime> it = signaltime.iterator();
		while(it.hasNext()){
			SignalTime s = it.next();
			try {
				this.time.add( new TrafficTimePlan( 0, s.getSignal_type(),
						s.getTm1(), s.getTm2(), s.getTm3(), s.getTm4(), s.getTm5(), s.getTm6()) );
			}catch(Exception e) {
				e.printStackTrace();
			}
		}		
		
//		TrafficTimePlan tp = new TrafficTimePlan(timePlan, signalType, tm1, tm2, tm3, tm4, tm5, tm6);
//		
//		if( tp != null ){
//			this.time = new ArrayList<TrafficTimePlan>(); 
//			time.add(tp);
//		}
	}

	
	public List<TrafficTimePlan> getTrafficTime() {
		return time;
	}
	/**
  	 @param phase the phase to set
	*/
	public void setTrafficTime(List<TrafficTimePlan> phase) {
		this.time = time;
	}
	
}
