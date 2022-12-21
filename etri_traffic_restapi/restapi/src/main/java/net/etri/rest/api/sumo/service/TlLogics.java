package net.etri.rest.api.sumo.service;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
<p>클래스 설명 : TlLogic정보목록 XML처리를 위한 클래스</p>
<p>TlLogics</p>
<pre>
 net.etri.rest.api.sumo.service
        └ TlLogics.java
</pre>
**/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="tlLogics")
public class TlLogics {
	@XmlAttribute(name = "version" )
	private String version = "1.3"; 
			
	@XmlElement(name="tlLogic")
	private List<TlLogic> tlLogics = null;

	@XmlElement(name="connection")
	private List<Connection> con = null;

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the tlLogics
	 */
	public List<TlLogic> getTlLogics() {
		return tlLogics;
	}

	/**
	 * @param tlLogics the tlLogics to set
	 */
	public void setTlLogics(List<TlLogic> tlLogics) {
		this.tlLogics = tlLogics;
	}

	/**
	 * @return the con
	 */
	public List<Connection> getCon() {
		return con;
	}

	/**
	 * @param con the con to set
	 */
	public void setCon(List<Connection> con) {
		this.con = con;
	}
}
