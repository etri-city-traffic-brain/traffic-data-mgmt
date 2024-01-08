package net.etri.rest.api.xml.type;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
  <p>클래스 설명 : TLogic유형 열거형</p>
  <p>TlLogicType</p>
  <pre>
   net.etri.rest.api.xml.type
          └ TlLogicType.java
  </pre>
**/
@XmlType(namespace="tlLogic", name = "type")
@XmlEnum
public enum TlLogicType {
	@XmlEnumValue("actuated")
	ACTUATED("actuated"), 
	@XmlEnumValue("delay_based")
	DELAY_BASED("delay_based"),
	@XmlEnumValue("static")
	STATIC("static");
	
	private final String value;

	TlLogicType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TlLogicType get(String v) {
    	if( v == null || v.isEmpty() ){
    		return null;
    	}
    	
        for (TlLogicType c: TlLogicType.values()) {
            if (c.value.toUpperCase().equals(v.toUpperCase())) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
