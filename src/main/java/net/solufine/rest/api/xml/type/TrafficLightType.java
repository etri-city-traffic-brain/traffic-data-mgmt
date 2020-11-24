/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.xml.type
 * @file TrafficLightType.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일			버전			수정자		수정내용
 * -------------	--------	------		-------------------------------
 * 2018. 5. 23.		0.0.1		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.xml.type;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
  <p>클래스 설명 : </p>
  <p>TrafficLightType</p>
  <pre>
   net.solufine.rest.api.xml.type
          └ TrafficLightType.java
  </pre>
  @author  redkaras 
  @since  2018. 5. 23.
  @version 1.0
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
