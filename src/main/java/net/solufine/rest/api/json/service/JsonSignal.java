/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.json.service
 * @file JsonSignal.java
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

import java.util.List;

/**
<p>클래스 설명 : </p>
<p>JsonSignal</p>
<pre>
 net.solufine.rest.api.json.service
        └ JsonSignal.java
</pre>
@author  redkaras 
@since  2019. 6. 21.
@version 1.0
**/
public class JsonSignal {

	private List<Signal> signal = null;

	/**
	 * @return the signal
	 */
	public List<Signal> getSignal() {
		return signal;
	}

	/**
	 * @param signal the signal to set
	 */
	public void setSignal(List<Signal> signal) {
		this.signal = signal;
	}
	
	
}
