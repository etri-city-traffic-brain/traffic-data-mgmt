/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.xml
 * @file Edge.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일			버전			수정자		수정내용
 * -------------	--------	------		-------------------------------
 * 2018. 6. 25.		0.0.1		redkaras	최초작성
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

import net.solufine.rest.api.xml.type.SpreadType;

/**
  <p>클래스 설명 : Edge정보 XML처리를 위한 클래스</p>
  <p>Edge</p>
  <pre>
   net.solufine.rest.api.xml
          └ Edge.java
  </pre>
  @author  redkaras 
  @since  2018. 6. 25.
  @version 1.0
**/
@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(propOrder={"dong_cd", "spreadType", "shape", "speed", "numLanes", "priority", "to_node", "from_node", "id" })
@XmlType(propOrder={"id", "from_node", "to_node", "priority", "numLanes", "speed", "shape", "spreadType", "dong_cd", "edge_len", "left_pocket", "right_pocket" })
@JsonInclude(Include.NON_NULL)
public class Edge {
	@XmlAttribute(name = "id", required = true)
	private String id = null;		//id (string)	The id of the edge (must be unique)
	@XmlAttribute(name = "from")
	private String from_node = null;	//referenced node id	The name of a node within the nodes-file the edge shall start at
	@XmlAttribute(name = "to")
	private String to_node = null;		//referenced node id	The name of a node within the nodes-file the edge shall end at
	@XmlAttribute(name = "priority")
	private int priority;	//The priority of the edge
	@XmlAttribute(name = "numLanes")
	private int numLanes;	//The number of lanes of the edge; must be an integer value
	@XmlAttribute(name = "speed")
	private float speed;	//The maximum speed allowed on the edge in m/s; must be a floating point number (see also "Using Edges' maximum Speed Definitions in km/h")
	@XmlAttribute(name = "shape")
	private String shape = null;	//List of positions; each position is encoded in x,y or x,y,z in meters (do not separate the numbers with a space!).	If the shape is given it should start and end with the positions of the from-node and to-node. Alternatively it can also start and end with the position where the edge leaves or enters the junction shape. This gives some control over the final junction shape. When using the option  --plain.extend-edge-shape it is sufficient to supply inner geometry points and extend the shape with the starting and ending node positions automatically
	@XmlAttribute(name = "spreadType")
	private SpreadType spreadType;	//The description of how to spread the lanes; "center" spreads lanes to both directions of the shape, any other value will be interpreted as "right"
	@XmlAttribute(name = "dong_cd")
	private String dong_cd = null;
	@XmlAttribute(name = "edge_len")
	private int edge_len;	
	/*
	포켓정보 없을 경우 -1
	Lane 이 0 부터 시작이라  포켓 1씩 빼줘야 함. 
	*/
	@XmlAttribute(name = "left_pocket")  // Lane 이 "0"부터 시작하여 -1 사용
	private int left_pocket = -1;
	@XmlAttribute(name = "right_pocket")
	private int right_pocket = -1;
	
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
     @return the from_node
	*/	
	public String getFrom_node() {
		return from_node;
	}
	/**
  	 @param from_node the from_node to set
	*/
	public void setFrom_node(String from_node) {
		this.from_node = from_node;
		if(this.from_node.isEmpty()){
			this.from_node = null;
		}
	}
	/**
     @return the to_node
	*/	
	public String getTo_node() {
		return to_node;
	}
	/**
  	 @param to_node the to_node to set
	*/
	public void setTo_node(String to_node) {
		this.to_node = to_node;
		if(this.to_node.isEmpty()){
			this.to_node = null;
		}
	}
	
	/**
     @return the priority
	*/	
	public int getPriority() {
		return priority;
	}
	/**
     @param priority the priority to set
	*/
	public void setPriority(int priority) {
		this.priority = priority;
	}
	/**
     @return the numLanes
	*/	
	public int getNumLanes() {
		return numLanes;
	}
	/**
  	 @param numLanes the numLanes to set
	*/
	public void setNumLanes(int numLanes) {
		this.numLanes = numLanes;
	}
	/**
     @return the speed
	*/	
	public float getSpeed() {
		return speed;
	}
	/**
  	 @param speed the speed to set
	*/
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	/**
     @return the shape
	*/	
	public String getShape() {
		return shape;
	}
	
	/**
	 @return the edge_len
	*/
	public int getEdge_len() {
		return edge_len;
	}
	/**
	 @param edge_len the edge_len to set
	*/
	public void setEdge_len(int edge_len) {
		this.edge_len = edge_len;
	}
	/**
  	 @param shape the shape to set
	*/
	public void setShape(String shape) {
		this.shape = shape;
		if(this.shape == null || this.shape.isEmpty()){
			this.shape = null;
			return;
		}
		//127.154327 37.538906,127.154839 37.538834
		StringBuffer sb = new StringBuffer();
		String[] strPoints = shape.split(" ");
		for(int i = 0; i < strPoints.length; i++){
			String[] xy = strPoints[i].split(",");
			sb.append(String.format("%s %s", xy[0], xy[1])).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		this.shape = sb.toString();
	}
	
	/**
  	 @param shape the shape to set
	*/
	public void setShapeText(String shape) {
		this.shape = shape;
		if(this.shape == null || this.shape.isEmpty()){
			this.shape = null;
			return;
		}
		
		StringBuffer sb = new StringBuffer();
		String[] strPoints = shape.split(",");
		for(int i = 0; i < strPoints.length; i++){
			String[] xy = strPoints[i].split(" ");
			sb.append(String.format("%s,%s", xy[0], xy[1])).append(" ");
		}
		sb.deleteCharAt(sb.length() - 1);
		this.shape = sb.toString();
	}
	
	/**
     @return the spreadType
	*/	
	public SpreadType getSpreadType() {
		return spreadType;
	}
	/**
  	 @param spreadType the spreadType to set
	*/
	public void setSpreadType(SpreadType spreadType) {
		this.spreadType = spreadType;
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
			
}
