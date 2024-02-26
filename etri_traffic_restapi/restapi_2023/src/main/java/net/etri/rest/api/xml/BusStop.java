package net.etri.rest.api.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
  <p>클래스 설명 : BusStop정보 XML처리를 위한 클래스</p>
  <p>BusStop</p>
  <pre>
   net.etri.rest.api.xml
          └ BusStop.java
  </pre>
  @author 장길수 
  @since  2021. 11. 15.
  @version 0.1
**/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"id", "edge", "position", "buses"})
@JsonInclude(Include.NON_NULL)
public class BusStop {
	@XmlAttribute(name = "id", required = true)
	protected String id = null;			//id (string)
	@XmlAttribute(name = "edge")
	private String edge = null;			//edge id 
	@XmlAttribute(name = "position")
	private String position = null;		//geometry info
	@XmlAttribute(name = "buses")
	private String buses = null;		//buses id	
	//@XmlAttribute(name = "dong_cd")
	//protected String dong_cd = null;	
	//@XmlAttribute(name = "partitionOwner")
	//protected String partitionOwner = null;	
		

	public BusStop() {
		
	}

	/**
	 * @param id
	 * @param edge
	 * @param position
	 */
	public BusStop(String id, String edge) {
		this.id = id;
		this.edge = edge;		
	}

	public BusStop(String id, String edge, String position, String buses) {
		this.id = id;
		this.edge = edge;
		this.position = position;
		this.buses = buses;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the edge
	 */
	public String getEdge() {
		return edge;
	}

	/**
	 * @param edge the edge to set
	 */
	public void setEdge(String edge) {
		this.edge = edge;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}
	
	/**
	 * @return the position
	 */
	public String getBuses() {
		return buses;
	}

	/**
	 * @param position the position to set
	 */
	public void setBuses(String buses) {
		this.buses = buses;
	}
	
//	/**
//	 * @return the dong_cd
//	 */
//	public String getDong_cd() {
//		return dong_cd;
//	}
//
//	/**
//	 * @param dong_cd the dong_cd to set
//	 */
//	public void setDong_cd(String dong_cd) {
//		this.dong_cd = dong_cd;
//	}
//
//	/**
//	 * @return the partitionOwner
//	 */
//	public String getPartitionOwner() {
//		return partitionOwner;
//	}
//
//	/**
//	 * @param partitionOwner the partitionOwner to set
//	 */
//	public void setPartitionOwner(String partitionOwner) {
//		this.partitionOwner = partitionOwner;
//	}
}
