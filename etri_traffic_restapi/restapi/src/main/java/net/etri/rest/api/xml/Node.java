package net.etri.rest.api.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import net.etri.rest.api.xml.type.NodeType;
import net.etri.rest.api.xml.type.TrafficLightType;

/**
  <p>클래스 설명 : Node정보 XML처리를 위한 클래스</p>
  <p>Node</p>
  <pre>
   net.etri.rest.api.xml
          └ Node.java
  </pre>
**/
@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(propOrder={"partitionOwner", "dong_cd", "keepClear", "shape", "radius", "tl", "tlType", "type", "z", "y", "x", "id"})
@XmlType(propOrder={"id", "x", "y", "z", "type", "tlType", "tl", "radius", "shape", "keepClear", "dong_cd", "partitionOwner"})
@JsonInclude(Include.NON_NULL)
public class Node {
		
	@XmlAttribute(name = "id", required = true)
	protected String id = null;		//id (string)	The name of the node
	@XmlAttribute(name = "x")
	private float x;		//float	The x-position of the node on the plane in meters
	@XmlAttribute(name = "y")
	private float y= 0f;		//float	The y-position of the node on the plane in meters
	@XmlAttribute(name = "z")
	private float z;		//float	The z-position of the node on the plane in meters
	@XmlAttribute(name = "type")
	private NodeType type;		//enum ( "priority", "traffic_light", "right_before_left", "unregulated", "priority_stop", "traffic_light_unregulated", "allway_stop", "rail_signal", "zipper", "traffic_light_right_on_red", "rail_crossing")	An optional type for the node
	@XmlAttribute(name = "tlType")
	private TrafficLightType tlType;	//e	enum ( "static", "actuated")	An optional type for the traffic light algorithm
	@XmlAttribute(name = "tl")
	private String tl = null;			//id (string)	An optional id for the traffic light program. Nodes with the same tl-value will be joined into a single program
	@XmlAttribute(name = "radius")
	private float radius = 1.5f;	//positive float;	optional turning radius (for all corners) for that node in meters (default 1.5)
	@XmlAttribute(name = "shape")
	private String shape = null;		//List of positions; each position is encoded in x,y or x,y,z in meters (do not separate the numbers with a space!).	A custom shape for that node. If less than two positions are given, netconvert will reset that node to use a computed shape.
	@XmlAttribute(name = "keepClear")
	private boolean keepClear = true;	//	bool	Whether the junction-blocking-heuristic should be activated at this node (default true)
	//controlledInner	list of edge ids	Edges which shall be controlled by a joined TLS despite being incoming as well as outgoing to the jointly controlled nodes			
	@XmlAttribute(name = "dong_cd")
	protected String dong_cd = null;	
	@XmlAttribute(name = "partitionOwner")
	protected String partitionOwner = null;	
	
	/**
	  Constructor of Node.java class
	**/
	public Node(){
		
	}
	/**
	 Constructor of Node.java class
  	 @param id
  	 @param x
  	 @param y
	**/
	public Node(String id, float x, float y){
		this.id = id;
		this.x = x;
		this.y = y;
	}
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
		if(this.id.isEmpty()){
			this.id = null;
		}
	}
	/**
     @return the x
	*/	
	public float getX() {
		return x;
	}
	/**
  	 @param x the x to set
	*/
	public void setX(float x) {
		this.x = x;
	}
	/**
     @return the y
	*/	
	public float getY() {
		return y;
	}
	/**
  	 @param y the y to set
	*/
	public void setY(float y) {
		this.y = y;
	}
	/**
     @return the z
	*/
	public float getZ() {
		return z;
	}
	/**
  	 @param z the z to set
	*/
	public void setZ(float z) {
		this.z = z;
	}
	/**
     @return the type
	*/
	public NodeType getType() {
		return type;
	}
	/**
    @return the Nodetype
	*/
	public NodeType getNodeType(){
		return type;
	}
	/**
     @param type the type to set
	*/
	public void setType(NodeType type) {
		this.type = type;
	}
	/**
    @param type the type to set
	*/
	public void setNodeType(NodeType type){
		this.type = type;
	}
	/**
     @return the tlType
	*/
	public TrafficLightType getTlType() {
		return tlType;
	}
	/**
  	 @param tlType the tlType to set
	*/
	public void setTlType(TrafficLightType tlType) {
		this.tlType = tlType;
	}
	/**
     @return the tl
	*/
	public String getTl() {
		return tl;
	}
	/**
  	 @param tl the tl to set
	*/
	public void setTl(String tl) {
		this.tl = tl;
		if(this.tl.isEmpty()){
			this.tl = null;
		}
	}
	/**
     @return the radius
    */
	public float getRadius() {
		return radius;
	}
	/**
  	 @param radius the radius to set
	*/
	public void setRadius(float radius) {
		this.radius = radius;
	}
	/**
     @return the keepClear
	*/
	public boolean isKeepClear() {
		return keepClear;
	}
	/**
  	 @param keepClear the keepClear to set
	*/
	public void setKeepClear(boolean keepClear) {
		this.keepClear = keepClear;
	}
	/**
     @return the dong_cd
	*/
	public String getDong_cd() {
		return dong_cd;
	}
	/**
  	 @param dong_cd the dong_cd to set
	*/
	public void setDong_cd(String dong_cd) {
		this.dong_cd = dong_cd;
	}
	/**
	 @return the partitionOwner
	*/
	public String getPartitionOwner() {
		return partitionOwner;
	}
	/**
	 @param partitionOwner the partitionOwner to set
	*/
	public void setPartitionOwner(String partitionOwner) {
		this.partitionOwner = partitionOwner;
	}
	/**
     @return the shape
	*/
	public String getShape() {
		return shape;
	}
	/**
  	 @param shape the shape to set
	*/
	public void setShape(String shape) {
		this.shape = shape;
		if(this.shape.isEmpty()){
			this.shape = null;
		}
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("id : ").append(id);
		sb.append(", x : ").append(x);
		sb.append(", y : ").append(y);
		sb.append(", z : ").append(z);
		sb.append(", tlType : ").append(tlType);
		sb.append(", tl : ").append(tl);
		sb.append(", radius : ").append(radius);
		sb.append(", keepClear : ").append(keepClear);
		sb.append(", dong_cd : ").append(dong_cd);
		sb.append(", shape : ").append(shape);
		return sb.toString();
	}	
}
