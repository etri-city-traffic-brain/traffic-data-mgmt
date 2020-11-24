/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.json.service
 * @file Tods.java
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
<p>Tods</p>
<pre>
 net.solufine.rest.api.json.service
        └ Tods.java
</pre>
@author  redkaras 
@since  2019. 6. 21.
@version 1.0
**/
@JsonInclude(Include.ALWAYS)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tods {

	@JsonProperty("todSeq")
	private int seq = 0;
	@JsonProperty("patternId")
	private int pattern_id = 0;
	@JsonProperty("fromTime")
	private String from_time = null;
	@JsonProperty("toTime")
	private String to_time = null;
	
	public Tods() {
		
	}
			
	public Tods(int seq, int pattern_id, String from_time, String to_time) {
		this.seq = seq;
		this.pattern_id = pattern_id;
		this.from_time = from_time;
		this.to_time = to_time;
	}
	
	/**
	 * @return the seq
	 */
	public int getSeq() {
		return seq;
	}
	/**
	 * @param seq the seq to set
	 */
	public void setSeq(int seq) {
		this.seq = seq;
	}
	/**
	 * @return the pattern_id
	 */
	public int getPattern_id() {
		return pattern_id;
	}
	/**
	 * @param pattern_id the pattern_id to set
	 */
	public void setPattern_id(int pattern_id) {
		this.pattern_id = pattern_id;
	}
	/**
	 * @return the from_time
	 */
	public String getFrom_time() {
		return from_time;
	}
	/**
	 * @param from_time the from_time to set
	 */
	public void setFrom_time(String from_time) {
		this.from_time = from_time;
	}
	/**
	 * @return the to_time
	 */
	public String getTo_time() {
		return to_time;
	}
	/**
	 * @param to_time the to_time to set
	 */
	public void setTo_time(String to_time) {
		this.to_time = to_time;
	}
	
	
}
