/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.json.service
 * @file TodPlan.java
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

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
<p>클래스 설명 : </p>
<p>TodPlan</p>
<pre>
 net.solufine.rest.api.json.service
        └ TodPlan.java
</pre>
@author  redkaras 
@since  2019. 6. 21.
@version 1.0
**/
@JsonInclude(Include.ALWAYS)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TodPlan {
	
	@JsonProperty("id")
	private String plan_id = null;
	@JsonProperty("tods")
	private List<Tods>  tods = null;
	
	public TodPlan() {

	}
	
	public TodPlan(String id) {
		this.plan_id = id;
	}
	
	/**
	 * @return the plan_id
	 */
	public String getPlan_id() {
		return plan_id;
	}
	/**
	 * @param plan_id the plan_id to set
	 */
	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}
	/**
	 * @return the tods
	 */
	public List<Tods> getTods() {
		return tods;
	}
	/**
	 * @param tods the tods to set
	 */
	public void setTods(List<Tods> tods) {
		this.tods = tods;
	}
	
	public void addTod(int seq, int pattern_id, String from_time, String to_time) {
		if(tods == null) {
			tods = new LinkedList<Tods>();			
		}
		
		for(int i = 0; i < tods.size(); i++) {
			if( tods.get(i).getSeq() == seq ) {
				return;
			}
		}
		
		tods.add( new Tods(seq, pattern_id, from_time, to_time) );
	}
}
