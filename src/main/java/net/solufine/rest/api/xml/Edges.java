/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.xml
 * @file Edges.java
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
  <p>클래스 설명 : Edge정보목록 XML처리를 위한 클래스</p>
  <p>Edges</p>
  <pre>
   net.solufine.rest.api.xml
          └ Edges.java
  </pre>
  @author  redkaras 
  @since  2018. 6. 25.
  @version 1.0
**/
@XmlRootElement(name="edges")
@XmlAccessorType(XmlAccessType.FIELD)
public class Edges {

	@XmlElement(name="edge")
	private List<Edge> edge = null;

	/**
     @return the edge
	*/
	public List<Edge> getEdge() {
		return edge;
	}

	/**
  	 @param edge the edge to set
	*/
	public void setEdge(List<Edge> edge) {
		this.edge = edge;
		if(this.edge != null){
			if(this.edge.isEmpty()){
				this.edge = null;
			}else{
				ArrayList<Edge> deduplication = new  ArrayList<Edge>();
				Iterator<Edge> it = this.edge.iterator();
				while(it.hasNext()){
					Edge data = it.next();
					if(!deduplication.contains(data)){
						deduplication.add(data);
					}
				}
				this.edge.clear();
				this.edge.addAll(deduplication);
			}
		}
	}
	/**
 	 @param nodes
 	 @return List&lt;Edge&gt;
	*/
	public List<String> notInNode(List<Node> nodes){
		if( nodes == null)
			return null;
		
		if(this.edge == null)
			return null;
		
		List<String> notIn = new ArrayList<String>();		
		Iterator<Edge> it = this.edge.iterator();
		while(it.hasNext()){
			boolean infromId = false;
			boolean inToId = false;
			Edge data = it.next();
			Iterator<Node> it2 = nodes.iterator();
			while(it2.hasNext()){
				Node node = it2.next();
				if(!infromId){
					if(data.getFrom_node().equals(node.getId()) || data.getFrom_node() == node.getId()){
						infromId = true;
					}
				}
				if(!inToId){
					if(data.getTo_node().equals(node.getId()) || data.getTo_node() == node.getId()){
						inToId = true;					
					}
				}
				if(infromId && inToId)
					break;
			}
			
			if(!infromId)				
				notIn.add(data.getFrom_node());
			if(!inToId)				
				notIn.add(data.getTo_node());
		}
		return notIn;
	}
	
	/**
	 @param node
	 @return boolean
	*/
	public boolean existToNode (Node node){
		Iterator<Edge> it = this.edge.iterator();
		while(it.hasNext()){
			Edge data = it.next();
			if(data.getTo_node().equals(node.getId()) || data.getTo_node() == node.getId()){
				return true;					
			}
		}
		return false;
	}
	
	/* (non-Javadoc)
	 @see java.lang.Object#toString()
	*/
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("----------").append("EDGES START").append("------------").append("\n");
		Iterator<Edge> it = edge.iterator();
		while(it.hasNext()){
			Edge n = it.next();
			sb.append(n.toString()).append("\n");
		}
		sb.append("----------").append("EDGES END").append("------------").append("\n");
		return sb.toString();
	} 
}
