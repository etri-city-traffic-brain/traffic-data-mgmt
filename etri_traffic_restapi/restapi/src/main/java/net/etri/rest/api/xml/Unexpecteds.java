package net.etri.rest.api.xml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
  <p>클래스 설명 : Unexpected정보목록 XML처리를 위한 클래스</p>
  <p>Unexpecteds</p>
  <pre>
   net.etri.rest.api.xml
          └ Unexpecteds.java
  </pre>
**/
@XmlRootElement(name="events")
@XmlAccessorType(XmlAccessType.FIELD)
public class Unexpecteds {

	@XmlElement(name="event")
	private List<Unexpected> unexpected = null;

	/**
     @return the unexpected
	*/
	public List<Unexpected> getUnexpected() {
		return unexpected;
	}

	/**
  	 @param unexpected the unexpected to set
	*/
	public void setUnexpected(List<Unexpected> unexpected) {
		this.unexpected = unexpected;
		if(this.unexpected != null){
			if(this.unexpected.isEmpty()){
				this.unexpected = null;
			}else{
				ArrayList<Unexpected> deduplication = new  ArrayList<Unexpected>();
				Iterator<Unexpected> it = this.unexpected.iterator();
				while(it.hasNext()){
					Unexpected data = it.next();
					if(!deduplication.contains(data)){
						deduplication.add(data);
					}
				}
				this.unexpected.clear();
				this.unexpected.addAll(deduplication);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("----------").append("EVENT START").append("------------").append("\n");
		Iterator<Unexpected> it = unexpected.iterator();
		while(it.hasNext()){
			Unexpected n = it.next();
			sb.append(n.toString()).append("\n");
		}
		sb.append("----------").append("EVENT END").append("------------").append("\n");
		return sb.toString();
	}
}
