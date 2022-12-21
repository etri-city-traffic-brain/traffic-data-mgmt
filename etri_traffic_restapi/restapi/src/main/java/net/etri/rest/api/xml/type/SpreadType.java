package net.etri.rest.api.xml.type;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
  <p>클래스 설명 : </p>
  <p>SpreadType</p>
  <pre>
   net.etri.rest.api.xml.type
          └ SpreadType.java
  </pre>
**/
@XmlType(name = "spreadType")
@XmlEnum
public enum SpreadType {
	@XmlEnumValue("center")
	CENTER("center"), 
	@XmlEnumValue("right")
	RIGHT("right");
	
	private final String value;

	SpreadType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SpreadType get(String v) {
    	if( v == null || v.isEmpty() ){
    		return null;
    	}
    	
        for (SpreadType c: SpreadType.values()) {
            if (c.value.toUpperCase().equals(v.toUpperCase())) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
