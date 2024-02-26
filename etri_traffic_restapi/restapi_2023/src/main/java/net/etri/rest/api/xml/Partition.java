package net.etri.rest.api.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
  <p>클래스 설명 : Partition정보 XML처리를 위한 클래스</p>
  <p>Partition</p>
  <pre>
   net.etri.rest.api.xml
          └ Partition.java
  </pre>
**/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"id", "nodes", "edges", "connections", "trafficSignalSystem", "route", "borderSet", "busstops"})
@JsonInclude(Include.NON_NULL)
public class Partition {

	@XmlAttribute(name = "id")
	private String id = null;
	@XmlElement(name="nodes")
	private Nodes nodes = null;
	@XmlElement(name="edges")
	private Edges edges = null;
	@XmlElement(name="connections")
	private Connections connections = null;
	@XmlElement(name="trafficSignalSystem")
	private TrafficSignals trafficSignalSystem = null;
	@XmlElement(name="busstops")
	private BusStops busstops = null;

	private List<Node> notInNodes = null;
	
	
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
	}
	/**
	 * @return the notInNodes
	 */
	public List<Node> getNotInNodes() {
		return notInNodes;
	}
	/**
	 * @param notInNodes the notInNodes to set
	 */
	public void setNotInNodes(List<Node> notInNodes) {
		this.notInNodes = notInNodes;
	}
	
	/**
	 * 20211118 장길수 추가 : 버스 정류소 반환
	 * @return the busstops
	 */
	public BusStops getBusstops() {
		return busstops;
	}
	/**
	 * 20211118 장길수 추가 : 버스 정류소 할당
	 * @param busstops the busstops to set
	 */
	public void BusStops(BusStops busstops) {
		this.busstops = busstops;
	}
	
}
