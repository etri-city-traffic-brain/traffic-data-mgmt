package net.etri.rest.api.repository;

import net.etri.rest.api.service.DefaultVO;
import net.etri.rest.api.type.XmlType;

/**
  <p>클래스 설명 : 파일VO 클래스</p>
  <p>FileVO</p>
  <pre>
   net.etri.rest.api.repository
          └ FileVO.java
  </pre>
**/
@SuppressWarnings("serial")
public class FileVO extends DefaultVO {

	private String reg_date;
	private XmlType xml_type;
	private String file_name;
	private long file_size;
	
	/**
     @return the reg_date
	*/
	public String getReg_date() {
		return reg_date;
	}
	/**
  	 @param reg_date the reg_date to set
	*/
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	/**
     @return the xml_type
	*/
	public XmlType getXml_type() {
		return xml_type;
	}
	/**
  	 @param xml_type the xml_type to set
	*/
	public void setXml_type(XmlType xml_type) {
		this.xml_type = xml_type;
	}
	/**
     @return the file_name
	*/
	public String getFile_name() {
		return file_name;
	}
	/**
  	 @param file_name the file_name to set
	*/
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	/**
     @return the file_size
	*/
	public long getFile_size() {
		return file_size;
	}
	/**
  	 @param file_size the file_size to set
	*/
	public void setFile_size(long file_size) {
		this.file_size = file_size;
	}
	/* (non-Javadoc)
	 @see java.lang.Object#toString()
	*/
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("reg_date :").append(reg_date).append(", ");
		sb.append("xml_type :").append(xml_type).append(", ");
		sb.append("file_name :").append(file_name).append(", ");
		sb.append("file_size :").append(file_size);
		return sb.toString();
	}
}