package net.etri.rest.api.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
  <p>클래스 설명 : PartitionScenarios정보 XML처리를 위한 클래스</p>
  <p>PartitionScenarios</p>
  <pre>
   net.etri.rest.api.xml
          └ PartitionScenarios.java
  </pre>
**/
@XmlRootElement(name="sceneario")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"borderSet", "partition", "partitionNo"})
public class PartitionScenarios {

	@XmlAttribute(name = "partitionNo")
	private String partitionNo = null;
	@XmlElement(name="partition")
	private List<Partition> partition = null;
	@XmlElement(name="borderSet")
	private BorderSet borderSet = null;
	
	/**
     @return the partitionNo
	*/
	public String getPartitionNo() {
		return partitionNo;
	}
	/**
  	 @param partitionNo the partitionNo to set
	*/
	public void setPartitionNo(String partitionNo) {
		this.partitionNo = partitionNo;
	}
	/**
     @return the partition
	*/
	public List<Partition> getPartition() {
		return partition;
	}
	/**
  	 @param partition the partition to set
	*/
	public void setPartition(List<Partition> partition) {
		this.partition = partition;
		if(this.partition == null || this.partition.isEmpty()){
			this.partition = null;
		}
	}
	/**
 	 @param partition the partition to set
	*/
	public void setPartition(Partition partition) {
		if(this.partition == null){
			this.partition = new ArrayList<Partition>();
		}
		this.partition.add(partition);
	}	
	/**
     @return the borderSet
	*/
	public BorderSet getBorderSet() {
		return borderSet;
	}
	/**
  	 @param borderSet the borderSet to set
	*/
	public void setBorderSet(BorderSet borderSet) {
		this.borderSet = borderSet;
	}
}
