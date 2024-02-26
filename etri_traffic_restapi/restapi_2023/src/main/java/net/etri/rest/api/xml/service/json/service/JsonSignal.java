package net.etri.rest.api.xml.service.json.service;

import java.util.List;

/**
<p>클래스 설명 : </p>
<p>JsonSignal</p>
<pre>
 net.etri.rest.api.json.service
        └ JsonSignal.java
</pre>
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
