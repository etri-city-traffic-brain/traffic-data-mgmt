/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.json.service
 * @file SignalPhaseDefault.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일			버전			수정자		수정내용
 * -------------	--------	------		-------------------------------
 * 2019. 6. 21.		0.0.1		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.json.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
<p>클래스 설명 : </p>
<p>TrafficSignal</p>
<pre>
 net.solufine.rest.api.json.service
        └ SignalPhaseDefault.java
</pre>
@author  redkaras 
@since  2019. 6. 21.
@version 1.0
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
