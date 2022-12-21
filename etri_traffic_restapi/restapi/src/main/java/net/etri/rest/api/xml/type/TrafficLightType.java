package net.etri.rest.api.xml.type;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
  <p>클래스 설명 : </p>
  <p>TrafficLightType</p>
  <pre>
   net.etri.rest.api.xml.type
          └ TrafficLightType.java
  </pre>
**/
@XmlType(name = "tlType")
@XmlEnum
public enum TrafficLightType {
	@XmlEnumValue("actuated")
    ACTUATED("actuated"),
    @XmlEnumValue("delay_based")
    DELAY_BASED("delay_based"),
    @XmlEnumValue("static")
    STATIC("static");
	
    private final String value;

    TrafficLightType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TrafficLightType get(String v) {
    	if( v == null || v.isEmpty() ){
    		return null;
    	}
    	
        for (TrafficLightType c: TrafficLightType.values()) {
            if (c.value.toUpperCase().equals(v.toUpperCase())) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
