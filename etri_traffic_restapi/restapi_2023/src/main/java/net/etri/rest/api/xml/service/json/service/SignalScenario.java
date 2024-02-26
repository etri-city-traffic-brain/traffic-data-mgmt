package net.etri.rest.api.xml.service.json.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
<p>클래스 설명 : </p>
<p>SignalScenario</p>
<pre>
 net.etri.rest.api.json.service
        └ SignalScenario.java
</pre>
**/
@JsonInclude(Include.ALWAYS)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignalScenario {

	@JsonProperty("id")
	private String pattern_id = null;
	@JsonProperty("scenarioNo")
	private String scenario_no = null;
	@JsonProperty("offset")
	private String peristalsis = null;
	@JsonProperty("duration")
	private String cycle = null;
	@JsonProperty("phase")
	private String phase = null;
	/**
	 * @return the pattern_id
	 */
	public String getPattern_id() {
		return pattern_id;
	}
	/**
	 * @param pattern_id the pattern_id to set
	 */
	public void setPattern_id(String pattern_id) {
		this.pattern_id = pattern_id;
	}
	/**
	 * @return the scenario_no
	 */
	public String getScenario_no() {
		return scenario_no;
	}
	/**
	 * @param scenario_no the scenario_no to set
	 */
	public void setScenario_no(String scenario_no) {
		this.scenario_no = scenario_no;
	}
	/**
	 * @return the peristalsis
	 */
	public String getPeristalsis() {
		return peristalsis;
	}
	/**
	 * @param peristalsis the peristalsis to set
	 */
	public void setPeristalsis(String peristalsis) {
		this.peristalsis = peristalsis;
	}
	/**
	 * @return the cycle
	 */
	public String getCycle() {
		return cycle;
	}
	/**
	 * @param cycle the cycle to set
	 */
	public void setCycle(String cycle) {
		this.cycle = cycle;
	}
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
	
	
}
