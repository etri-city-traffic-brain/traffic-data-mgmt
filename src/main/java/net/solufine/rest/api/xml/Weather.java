/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.xml
 * @file Weather.java
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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
  <p>클래스 설명 : Weather정보 XML처리를 위한 클래스</p>
  <p>Weather</p>
  <pre>
   net.solufine.rest.api.xml
          └ Weather.java
  </pre>
  @author  redkaras 
  @since  2018. 7. 25.
  @version 1.0
**/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"wea_date", "rainly_status", "humidity", "rainfall", "sky_status", "temperature", "thunderstorm", "wind", "wind_velocity"})
@JsonInclude(Include.NON_NULL)
public class Weather {
	@XmlAttribute(name = "wea_date", required = true)
	private String  wea_date = null; //기상실황일시
	@XmlAttribute(name = "rainly_status")
	private int rainly_status = 0; //강수형태
	@XmlAttribute(name = "humidity")
	private int humidity; //습도
	@XmlAttribute(name = "rainfall")
	private int rainfall; //강수
	@XmlAttribute(name = "sky_status")
	private int sky_status; //하늘상태
	@XmlAttribute(name = "temperature")
	private float temperature; //온도	
	@XmlAttribute(name = "thunderstorm")
	private int thunderstorm; //뇌전
	@XmlAttribute(name = "wind")
	private int wind; //풍향
	@XmlAttribute(name = "wind_velocity")
	private float wind_velocity; //풍속	

	/**
	 Constructor of Weather.java class
	**/
	public Weather(){
		
	}
	
	/**
	 Constructor of Weather.java class
  	 @param wea_date
	**/
	public Weather(String wea_date){
		this.wea_date = wea_date;
		if(this.wea_date.isEmpty()){
			this.wea_date = null;
		}
	}
	/**
     @return the wea_date
	*/
	public String getWea_date() {
		return wea_date;
	}
	/**
  	 @param wea_date the wea_date to set
	*/
	public void setWea_date(String wea_date) {
		this.wea_date = wea_date;
		if(this.wea_date.isEmpty()){
			this.wea_date = null;
		}
	}
	/**
     @return the rainly_status
	*/
	public int getRainly_status() {
		return rainly_status;
	}
	/**
  	 @param rainly_status the rainly_status to set
	*/
	public void setRainly_status(int rainly_status) {
		this.rainly_status = rainly_status;
	}
	/**
     @return the humidity
	*/
	public int getHumidity() {
		return humidity;
	}
	/**
  	 @param humidity the humidity to set
	*/
	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}
	/**
     @return the rainfall
	*/
	public int getRainfall() {
		return rainfall;
	}
	/**
  	 @param rainfall the rainfall to set
	*/
	public void setRainfall(int rainfall) {
		this.rainfall = rainfall;
	}
	/**
     @return the sky_status
	*/
	public int getSky_status() {
		return sky_status;
	}
	/**
  	 @param sky_status the sky_status to set
	*/
	public void setSky_status(int sky_status) {
		this.sky_status = sky_status;
	}
	/**
     @return the temperature
	*/
	public float getTemperature() {
		return temperature;
	}
	/**
  	 @param temperature the temperature to set
	*/
	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}
	/**
     @return the thunderstorm
	*/
	public int getThunderstorm() {
		return thunderstorm;
	}
	/**
  	 @param thunderstorm the thunderstorm to set
	*/
	public void setThunderstorm(int thunderstorm) {
		this.thunderstorm = thunderstorm;
	}
	/**
     @return the wind
	*/
	public int getWind() {
		return wind;
	}
	/**
  	 @param wind the wind to set
	*/
	public void setWind(int wind) {
		this.wind = wind;
	}
	/**
     @return the wind_velocity
	*/
	public float getWind_velocity() {
		return wind_velocity;
	}
	/**
  	 @param wind_velocity the wind_velocity to set
	*/
	public void setWind_velocity(float wind_velocity) {
		this.wind_velocity = wind_velocity;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("wea_date : ").append(wea_date);
		sb.append(", rainly_status : ").append(rainly_status);
		sb.append(", humidity : ").append(humidity);
		sb.append(", rainfall : ").append(rainfall);
		sb.append(", sky_status : ").append(sky_status);
		sb.append(", temperature : ").append(temperature);
		sb.append(", thunderstorm : ").append(thunderstorm);
		sb.append(", wind : ").append(wind);
		sb.append(", wind_velocity : ").append(wind_velocity);
		
		return sb.toString();
	}

}
