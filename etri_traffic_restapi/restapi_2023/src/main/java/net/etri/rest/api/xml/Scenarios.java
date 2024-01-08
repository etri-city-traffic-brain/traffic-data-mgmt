package net.etri.rest.api.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
  <p>클래스 설명 : Scenario정보 XML처리를 위한 클래스</p>
  <p>Scenarios</p>
  <pre>
   net.etri.rest.api.xml
          └ Scenarios.java
  </pre>
**/
@XmlRootElement(name="sceneario")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"nodes", "edges", "connections", "trafficSignalSystem", "weathers", "events", "busstops"})
public class Scenarios {
	
	@XmlElement(name="nodes")
	private Nodes nodes = null;
	@XmlElement(name="edges")
	private Edges edges = null;
	@XmlElement(name="connections")
	private Connections connections = null;
	@XmlElement(name="trafficSignalSystem")
	private TrafficSignals trafficSignalSystem = null;
	@XmlElement(name="weathers")
	private Weathers weathers = null;
	@XmlElement(name="events")
	private Unexpecteds events = null;
	@XmlElement(name="busstops")
	private BusStops busstops = null;
	

	/**
     @return the nodes
	*/
	public Nodes getNodes() {
		return nodes;
	}
	/**
  	 @param nodes the nodes to set
	*/
	public void setNodes(Nodes nodes) {
		this.nodes = nodes;
		if(this.nodes != null){
			if(this.nodes.getNode() == null || this.nodes.getNode().isEmpty()){
				this.nodes = null;
			}
		}
	}
	/**
     @return the edges
	*/
	public Edges getEdges() {
		return edges;
	}
	/**
  	 @param edges the edges to set
	*/
	public void setEdges(Edges edges) {
		this.edges = edges;
		if(this.edges != null){
			if(this.edges.getEdge() == null || this.edges.getEdge().isEmpty()){
				this.edges = null;
			}
		}
	}	
	/**
     @return the trafficSignalSystem
	*/
	public TrafficSignals getTrafficSignalSystem() {
		return trafficSignalSystem;
	}
	/**
  	 @param trafficSignalSystem the trafficSignalSystem to set
	*/
	public void setTrafficSignalSystem(TrafficSignals trafficSignalSystem) {
		this.trafficSignalSystem = trafficSignalSystem;
		if( this.trafficSignalSystem != null ){
			if( this.trafficSignalSystem.getTrafficSignal() == null || this.trafficSignalSystem.getTrafficSignal().isEmpty()){
				this.trafficSignalSystem = null;
			}
		}
	}
	/**
     @return the connections
	*/
	public Connections getConnections() {
		return connections;
	}
	/**
  	 @param connections the connections to set
	*/
	public void setConnections(Connections connections) {
		this.connections = connections;
		if(this.connections != null){
			if(this.connections.getCon() == null || this.connections.getCon().isEmpty()){
				this.connections = null;
			}
		}
	}
	/**
     @return the weathers
	*/
	public Weathers getWeathers() {
		return weathers;
	}
	/**
  	 @param weathers the weathers to set
	*/
	public void setWeathers(Weathers weathers) {
		this.weathers = weathers;
		if(this.weathers != null){
			if(this.weathers.getWeather() == null || this.weathers.getWeather().isEmpty()){
				this.weathers = null;
			}
		}
	}
	/**
     @return the events
	*/
	public Unexpecteds getEvents() {
		return events;
	}
	/**
  	 @param events the events to set
	*/
	public void setEvents(Unexpecteds events) {
		this.events = events;
		if(this.events != null){
			if(this.events.getUnexpected() == null || this.events.getUnexpected().isEmpty()){
				this.events = null;
			}
		}
	}
	
	/**
	 * 20211118 장길수 추가 : 버스 정류소 목록 조회
	 * @param busstops the busstops to set
	 */
	public void setBusStops(BusStops busstops) {
		this.busstops = busstops;
		if(this.busstops != null){
			if(this.busstops.getBusstop() == null || this.busstops.getBusstop().isEmpty()){
				this.busstops = null;
			}
		}		
	}
	
	/**
	 * @return the busstops
	 */
	public BusStops getBusstops() {
		return busstops;
	}
}