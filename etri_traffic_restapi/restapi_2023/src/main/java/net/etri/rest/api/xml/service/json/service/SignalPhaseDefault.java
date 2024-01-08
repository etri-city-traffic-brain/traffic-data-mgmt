package net.etri.rest.api.xml.service.json.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
<p>클래스 설명 : </p>
<p>TrafficSignal</p>
<pre>
 net.etri.rest.api.json.service
        └ SignalPhaseDefault.java
</pre>
**/
@JsonInclude(Include.ALWAYS)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignalPhaseDefault {

	@JsonProperty("type")
	private String signal_type = null;
	@JsonProperty("tm")
	private String tm = null;
	
	/**
	 * @return the signal_type
	 */
	public String getSignal_type() {
		return signal_type;
	}
	/**
	 * @param signal_type the signal_type to set
	 */
	public void setSignal_type(String signal_type) {
		this.signal_type = signal_type;
	}
	/**
	 * @return the tm
	 */
	public String getTm() {
		return tm;
	}
	/**
	 * @param tm the tm to set
	 */
	public void setTm(String tm) {
		this.tm = tm;
	}
	
	
}
