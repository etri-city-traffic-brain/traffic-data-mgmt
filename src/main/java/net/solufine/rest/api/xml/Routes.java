/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 * 
 * @project rest.api
 * @package net.solufine.rest.api.xml
 * @file Routes.java
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 * 	수정일			버전			수정자		수정내용
 * -------------	--------	------		-------------------------------
 * 2018. 9. 28.		0.0.1		redkaras	최초작성
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

import net.solufine.rest.api.xml.service.VehicleRoute;

/**
  <p>클래스 설명 : </p>
  <p>Routes</p>
  <pre>
   net.solufine.rest.api.xml
          └ Routes.java
  </pre>
  @author  redkaras 
  @since  2018. 9. 28.
  @version 1.0
**/
@XmlRootElement(name="routes")
@XmlAccessorType(XmlAccessType.FIELD)
public class Routes {

	@XmlElement(name="vehicle")
	private List<Vehicle> vehicle = null;

	/**
	 @return the vehicle
	*/
	public List<Vehicle> getVehicle() {
		return vehicle;
	}

	/**
	 @param vehicle the vehicle to set
	*/
	public void setVehicle(List<Vehicle> vehicle) {
		this.vehicle = vehicle;
	}
	
	public void setRoute(List<VehicleRoute> route) {
		if(route == null || route.isEmpty())
			return;
		
		if(this.vehicle == null)
			this.vehicle = new ArrayList<Vehicle>();
		
		Iterator<VehicleRoute> it = route.iterator();
		while(it.hasNext()){
			VehicleRoute r = it.next();
			Vehicle v = null;
			Iterator<Vehicle> it2 = vehicle.iterator();
			while(it2.hasNext()){
				Vehicle n = it2.next();
				if(n.getId().equals(r.getId()) || n.getId() == r.getId()){
					v = n;
				}
			}
			
			if(v != null){
				v.setRoute(new Route(r.getLinks(), r.getStartingOffset()));
			}
		}
		
		
	}
	
	/* (non-Javadoc)
	 @see java.lang.Object#toString()
	*/
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("----------").append("ROUTR START").append("------------").append("\n");
		Iterator<Vehicle> it = vehicle.iterator();
		while(it.hasNext()){
			Vehicle n = it.next();
			sb.append(n.toString()).append("\n");
		}
		sb.append("----------").append("ROUTR END").append("------------").append("\n");
		return sb.toString();
	} 
}
