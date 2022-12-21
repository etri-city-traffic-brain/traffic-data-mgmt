package net.etri.rest.api.xml.service.json.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
<p>클래스 설명 : </p>
<p>Connection</p>
<pre>
 net.etri.rest.api.json.service
        └ Connection.java
</pre>
**/
@JsonInclude(Include.ALWAYS)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Connection {
	
	@JsonProperty("fromLinkId")
	private String from_edge = null;
	@JsonProperty("fromLaneNum")
	private String fromlane = null;
	@JsonProperty("toLinkId")
	private String to_edge = null;
	@JsonProperty("toLaneNum")
	private String tolane = null;
	@JsonProperty("linkIndex")
	private String linkIndex = null;
	
	/**
	 * @return the from_edge
	 */
	public String getFrom_edge() {
		return from_edge;
	}
	/**
	 * @param from_edge the from_edge to set
	 */
	public void setFrom_edge(String from_edge) {
		this.from_edge = from_edge;
	}
	/**
	 * @return the fromlane
	 */
	public String getFromlane() {
		return fromlane;
	}
	/**
	 * @param fromlane the fromlane to set
	 */
	public void setFromlane(String fromlane) {
		this.fromlane = fromlane;
	}
	/**
	 * @return the to_edge
	 */
	public String getTo_edge() {
		return to_edge;
	}
	/**
	 * @param to_edge the to_edge to set
	 */
	public void setTo_edge(String to_edge) {
		this.to_edge = to_edge;
	}
	/**
	 * @return the tolane
	 */
	public String getTolane() {
		return tolane;
	}
	/**
	 * @param tolane the tolane to set
	 */
	public void setTolane(String tolane) {
		this.tolane = tolane;
	}
	/**
	 * @return the linkIndex
	 */
	public String getLinkIndex() {
		return linkIndex;
	}
	/**
	 * @param linkIndex the linkIndex to set
	 */
	public void setLinkIndex(String linkIndex) {
		this.linkIndex = linkIndex;
	}
	
	
}
