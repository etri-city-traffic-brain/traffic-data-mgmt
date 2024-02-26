package net.etri.rest.api.sumo.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
<p>클래스 설명 : Connection정보목록 XML처리를 위한 클래스</p>
<p>Connections</p>
<pre>
 net.etri.rest.api.sumo
        └ Connections.java
</pre>
**/
@XmlRootElement(name="connections")
@XmlAccessorType(XmlAccessType.FIELD)
public class Connections {

	@XmlElement(name="connection")
	private List<Connection> con = null;

	/**
     @return the con
	*/
	public List<Connection> getCon() {
		return con;
	}

	/**
  	 @param con the con to set
	*/
	public void setCon(List<Connection> con) {
		this.con = con;
		if(this.con != null){
			if(this.con.isEmpty()){
				this.con = null;
			}else{
				ArrayList<Connection> deduplication = new  ArrayList<Connection>();
				Iterator<Connection> it = this.con.iterator();
				while(it.hasNext()){
					Connection data = it.next();
					if(!deduplication.contains(data)){
						deduplication.add(data);
					}
				}
				this.con.clear();
				this.con.addAll(deduplication);
			}
		}
	}
	
	public List<String> getTlIds(){
		if(this.con == null || this.con.isEmpty())
			return null;
		
		List<String> tls = new ArrayList<String>();
		Iterator<Connection> it = this.con.iterator();
		while(it.hasNext()){
			Connection data = it.next();
			if(data.getTl() != null && !data.getTl().isEmpty()){
				tls.add(data.getTl());
			}
		}
		return tls;
	}
}
