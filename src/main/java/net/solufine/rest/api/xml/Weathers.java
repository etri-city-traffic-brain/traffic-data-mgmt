/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.xml
 * @file Weathers.java
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
  <p>클래스 설명 : Weather정보목록 XML처리를 위한 클래스</p>
  <p>Weathers</p>
  <pre>
   net.solufine.rest.api.xml
          └ Weathers.java
  </pre>
  @author  redkaras 
  @since  2018. 7. 25.
  @version 1.0
**/
@XmlRootElement(name="weathers")
@XmlAccessorType(XmlAccessType.FIELD)
public class Weathers {

	@XmlElement(name="weather")
	private List<Weather> weather = null;

	/**
     @return the weather
	*/
	public List<Weather> getWeather() {
		return weather;
	}

	/**
  	 @param weather the weather to set
	*/
	public void setWeather(List<Weather> weather) {
		this.weather = weather;
		if(this.weather != null){
			if(this.weather.isEmpty()){
				this.weather = null;
			}else{
				ArrayList<Weather> deduplication = new  ArrayList<Weather>();
				Iterator<Weather> it = this.weather.iterator();
				while(it.hasNext()){
					Weather data = it.next();
					if(!deduplication.contains(data)){
						deduplication.add(data);
					}
				}
				this.weather.clear();
				this.weather.addAll(deduplication);
			}
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("----------").append("WEATHER START").append("------------").append("\n");
		Iterator<Weather> it = weather.iterator();
		while(it.hasNext()){
			Weather n = it.next();
			sb.append(n.toString()).append("\n");
		}
		sb.append("----------").append("WEATHER END").append("------------").append("\n");
		return sb.toString();
	}
	
	
}
