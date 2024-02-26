package net.etri.rest.api.xml.service.json.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
<p>클래스 설명 : </p>
<p>SignalPhase</p>
<pre>
 net.etri.rest.api.json.service
        └ SignalPhase.java
</pre>
**/
@JsonInclude(Include.ALWAYS)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignalPhase {

	@JsonProperty("index")
	private String phase = null;
	@JsonProperty("state")
	private String state = null;
	
	/**
	 * @return the phase
	 */
	public String getPhase() {
		return phase;
	}
	/**
	 * @param phase the phase to set
	 */
	public void setPhase(String phase) {
		this.phase = phase;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	
}
