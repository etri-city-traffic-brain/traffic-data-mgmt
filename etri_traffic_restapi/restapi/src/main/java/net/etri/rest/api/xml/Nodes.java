package net.etri.rest.api.xml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
  <p>클래스 설명 : Node정보목록 XML처리를 위한 클래스</p>
  <p>Nodes</p>
  <p>
	&lt;?xml version="1.0" encoding="UTF-8"?&gt;
	&lt;nodes xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="http://sumo.dlr.de/xsd/nodes_file.xsd"&gt;
	    &lt;node id="1" x="-250.0" y="0.0" &#47;&gt;
	    &lt;node id="2" x="+250.0" y="0.0" &#47;&gt;
	    &lt;node id="3" x="+251.0" y="0.0" &#47;&gt;
	&lt;&#47;nodes&gt;
   </p>
  <pre>
   net.etri.rest.api.xml
          └ Nodes.java
  </pre>
**/
@XmlRootElement(name="nodes")
@XmlAccessorType(XmlAccessType.FIELD)
public class Nodes {
    @XmlElement(name="node")
	private List<Node> node = null;

	/**
     @return the node
	*/
	public List<Node> getNode() {
		return node;
	}

	/**
  	 @param node the node to set
	*/
	public void setNode(List<Node> node) {
		this.node = node;
		if(this.node != null){
			if(this.node.isEmpty()){
				this.node = null;
			}else{
				ArrayList<Node> deduplication = new  ArrayList<Node>();
				Iterator<Node> it = this.node.iterator();
				while(it.hasNext()){
					Node data = it.next();
					if(!deduplication.contains(data)){
						deduplication.add(data);
					}
				}
				this.node.clear();
				this.node.addAll(deduplication);
			}
		}
	}

	/**
	 @param node the node to set
	*/
	public void appendNode(List<Node> node, String partitionId) {
		if(node == null)
			return;
		
		if(this.node == null){
			this.node = node;
			return;
		}
	
		Iterator<Node> it = node.iterator();
		while(it.hasNext()){
			boolean exist = false;
			Node data = it.next();
			Iterator<Node> it2 = this.node.iterator();
			while(it2.hasNext()){
				Node data2 = it2.next();
				if(partitionId != ""){
					if(data2.getPartitionOwner() != null){
						if(data2.getPartitionOwner().equals(partitionId) || data2.getPartitionOwner() == partitionId){
							data2.setPartitionOwner(null);
						}
					}
				}
				if(data.getId().equals(data2.getId()) || data.getId() == data2.getId()){					
					exist = true;
					break;
				}
			}
			if(!exist){
				if(data.getPartitionOwner() != null){
					if(data.getPartitionOwner().equals(partitionId) || data.getPartitionOwner() == partitionId){
						data.setPartitionOwner(null);
					}
				}
				this.node.add(data);
			}
		}
		
		
	}
	
	public boolean existNode(String id) {
		Iterator<Node> it = this.node.iterator();
		while(it.hasNext()){
			Node data = it.next();
			if(data.getId().equals(id) || data.getId() == id){					
				return true;
			}
		}
		return false;
	}
	
	/**
	 @param node the node to set
	*/
	public void appendNode(Node node, String partitionId) {
		Iterator<Node> it = this.node.iterator();
		boolean exist = false;
		while(it.hasNext()){
			Node data = it.next();
			if(partitionId != ""){
				if(data.getPartitionOwner() != null){
					if(data.getPartitionOwner().equals(partitionId) || data.getPartitionOwner() == partitionId){
						data.setPartitionOwner(null);
					}
				}
			}
			if(node.getId().equals(data.getId()) || node.getId() == data.getId()){					
				exist = true;
				break;
			}
		}
		
		if(!exist){
			if(node.getPartitionOwner() != null){
				if(node.getPartitionOwner().equals(partitionId) || node.getPartitionOwner() == partitionId){
					node.setPartitionOwner(null);
				}
			}
			this.node.add(node);
		}
	}
	/* (non-Javadoc)
	 @see java.lang.Object#toString()
	*/
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("----------").append("NODES START").append("------------").append("\n");
		Iterator<Node> it = node.iterator();
		while(it.hasNext()){
			Node n = it.next();
			sb.append(n.toString()).append("\n");
		}
		sb.append("----------").append("NODES END").append("------------").append("\n");
		return sb.toString();
	} 
	
    
    
}
