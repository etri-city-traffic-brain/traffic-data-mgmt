package net.etri.rest.api.xml.service.json.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
<p>클래스 설명 : </p>
<p>Signal</p>
<pre>
 net.etri.rest.api.json.service
        └ Signal.java
</pre>
**/
@JsonInclude(Include.ALWAYS)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Signal {

	@JsonProperty("id")
	private String signal_id = null;
	@JsonProperty("date")
	private String eff_date = null;
	@JsonProperty("police_station")
	private String police_station = null;
	@JsonProperty("version")
	private String version = null;
	@JsonProperty("cross_no")
	private String cross_no = null;
	@JsonProperty("crossName")
	private String cross_nm = null;	
	@JsonProperty("signalGroup")
	private String signal_group = null;
	@JsonProperty("editDate")
	private String edit_date = null;
	@JsonProperty("description")
	private String description = null;
	
	/**
	 * @return the signal_id
	 */
	public String getSignal_id() {
		return signal_id;
	}
	/**
	 * @param signal_id the signal_id to set
	 */
	public void setSignal_id(String signal_id) {
		this.signal_id = signal_id;
	}
	
	/**
	 * @return the eff_date
	 */
	public String getEff_date() {
		return eff_date;
	}
	/**
	 * @param eff_date the eff_date to set
	 */
	public void setEff_date(String eff_date) {
		this.eff_date = eff_date;
	}
	
	/**
	 * @return the police_station
	 */
	public String getPolice_station() {
		return police_station;
	}
	/**
	 * @param police_station the police_station to set
	 */
	public void setPolice_station(String police_station) {
		this.police_station = police_station;
	}
	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * @return the cross_no
	 */
	public String getCross_no() {
		return cross_no;
	}
	/**
	 * @param cross_no the cross_no to set
	 */
	public void setCross_no(String cross_no) {
		this.cross_no = cross_no;
	}
	/**
	 * @return the cross_nm
	 */
	public String getCross_nm() {
		return cross_nm;
	}
	/**
	 * @param cross_nm the cross_nm to set
	 */
	public void setCross_nm(String cross_nm) {
		this.cross_nm = cross_nm;
	}
	/**
	 * @return the signal_group
	 */
	public String getSignal_group() {
		return signal_group;
	}
	/**
	 * @param signal_group the signal_group to set
	 */
	public void setSignal_group(String signal_group) {
		this.signal_group = signal_group;
	}
	
	/**
	 * @return the edit_date
	 */
	public String getEdit_date() {
		return edit_date;
	}
	/**
	 * @param edit_date the edit_date to set
	 */
	public void setEdit_date(String edit_date) {
		this.edit_date = edit_date;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
