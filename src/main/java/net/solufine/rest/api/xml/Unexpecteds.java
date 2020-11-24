/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.xml
 * @file Unexpecteds.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일			버전			수정자		수정내용
 * -------------	--------	------		-------------------------------
 * 2018. 7. 25.		0.0.1		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.xml;

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
   net.solufine.rest.api.xml
          └ Unexpecteds.java
  </pre>
  @author  redkaras 
  @since  2018. 7. 25.
  @version 1.0
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
