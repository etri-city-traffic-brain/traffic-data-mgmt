package net.etri.rest.api.xml.type;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
  <p>클래스 설명 : Node유형 열거형</p>
  <p>NodeType</p>
  <pre>
   net.etri.rest.api.xml.type
          └ NodeType.java
  </pre>
**/
@XmlType(namespace="node", name="type")
@XmlEnum
public enum NodeType {
	@XmlEnumValue("priority")
	PRIORITY("priority"), 
	@XmlEnumValue("traffic_light")
	TRAFFIC_LIGHT("traffic_light"), 
	@XmlEnumValue("right_before_left")
	RIGHT_BEFORE_LEFT("right_before_left"), 
	@XmlEnumValue("unregulated")
	UNREGULATED("unregulated"), 
	@XmlEnumValue("priority_stop")
	PRIORITY_STOP("priority_stop"), 
	@XmlEnumValue("traffic_light_unregulated")
	TRAFFIC_LIGHT_UNREGULATED("traffic_light_unregulated"), 
	@XmlEnumValue("allway_stop")
	ALLWAY_STOP("allway_stop"), 
	@XmlEnumValue("rail_signal")
	RAIL_SIGNAL("rail_signal"), 
	@XmlEnumValue("zipper")
	ZIPPER("zipper"), 
	@XmlEnumValue("traffic_light_right_on_red")
	TRAFFIC_LIGHT_RIGHT_ON_RED("traffic_light_right_on_red"), 
	@XmlEnumValue("rail_crossing")
	RAIL_CROSSING("rail_crossing"),
	@XmlEnumValue("dead_end")
	DEAD_END("dead_end")
	;

    private final String value;

    NodeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static NodeType get(String v) {
    	if( v == null || v.isEmpty() ){
    		return null;
    	}
    	
        for (NodeType c: NodeType.values()) {
            if (c.value.toUpperCase().equals(v.toUpperCase())) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
