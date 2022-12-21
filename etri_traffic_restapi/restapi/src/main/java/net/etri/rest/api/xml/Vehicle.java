package net.etri.rest.api.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
  <p>클래스 설명 : </p>
  <p>Vehicle</p>
  <pre>
   net.etri.rest.api.xml
          └ Vehicle.java
  </pre>
**/
public class Vehicle {
	
	@XmlAttribute(name = "id")
	private String id = null; 
	@XmlAttribute(name = "type")
	private String type = null;
	@XmlAttribute(name = "depart")
	private String depart = null;
	@XmlAttribute(name = "departLane")
    private String departLane = null;			
	@XmlElement(name="route")
	private List<Route> route = null;
	/**
	 @return the id
	*/
	public String getId() {
		return id;
	}
	/**
	 @param id the id to set
	*/
	public void setId(String id) {
		this.id = id;
	}
	/**
	 @return the type
	*/
	public String getType() {
		return type;
	}
	/**
	 @param type the type to set
	*/
	public void setType(String type) {
		this.type = type;
	}
	/**
	 @return the depart
	*/
	public String getDepart() {
		return depart;
	}
	/**
	 @param depart the depart to set
	*/
	public void setDepart(String depart) {
		this.depart = depart;
	}
	/**
	 @return the departLane
	*/
	public String getDepartLane() {
		return departLane;
	}
	/**
	 @param departLane the departLane to set
	*/
	public void setDepartLane(String departLane) {
		this.departLane = departLane;
	}
	/**
	 @return the route
	*/
	public List<Route> getRoute() {
		return route;
	}
	/**
	 @param route the route to set
	*/
	public void setRoute(List<Route> route) {
		this.route = route;
	}
	
	/**
	 @param route the route to set
	*/
	public void setRoute(Route route) {
		if(route == null)
			return;
		
		if(this.route == null)
			this.route = new ArrayList<Route>();
		
		this.route.add(route);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("id : ").append(id);
		sb.append(", type : ").append(type);
		sb.append(", depart : ").append(depart);
		sb.append(", departLane : ").append(departLane);
		sb.append(", route : ").append(route.toString());
		return sb.toString();
	}
		
}
