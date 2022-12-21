package net.etri.rest.api.xml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="busstops")
@XmlAccessorType(XmlAccessType.FIELD)
public class BusStops {

	@XmlElement(name="busstop")
	private List<BusStop> busstop = null;

	/**
	 * @return the BusStop
	 */
	public List<BusStop> getBusstop() {
		return busstop;
	}

	/**
	 * @param BusStop the BusStop to set
	 */
	public void setBusstop(List<BusStop> busstop) {
		this.busstop = busstop;
		
		if(this.busstop != null) {
			if(this.busstop.isEmpty()) {
				this.busstop = null;
			} else {
				ArrayList<BusStop> deduplication = new  ArrayList<BusStop>();
				Iterator<BusStop> it = this.busstop.iterator();
				
				while(it.hasNext()) {
					BusStop data = it.next();
					
					if(!deduplication.contains(data)) {
						deduplication.add(data);
					}
				}
				this.busstop.clear();
				this.busstop.addAll(deduplication);
			}
		}
	}
}
