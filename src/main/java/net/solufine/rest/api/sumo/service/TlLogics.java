/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.sumo.service
 * @file TlLogics.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일			버전			수정자		수정내용
 * -------------	--------	------		-------------------------------
 * 2018. 6. 28.		0.0.1		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.sumo.service;

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
 net.solufine.rest.api.sumo.service
        └ TlLogics.java
</pre>
@author  redkaras 
@since  2018. 6. 28.
@version 1.0
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
