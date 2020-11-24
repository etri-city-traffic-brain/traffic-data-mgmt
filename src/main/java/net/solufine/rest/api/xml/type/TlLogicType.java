/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.xml.type
 * @file TlLogicType.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일			버전			수정자		수정내용
 * -------------	--------	------		-------------------------------
 * 2018. 6. 28.		0.0.1		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.xml.type;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
  <p>클래스 설명 : TLogic유형 열거형</p>
  <p>TlLogicType</p>
  <pre>
   net.solufine.rest.api.xml.type
          └ TlLogicType.java
  </pre>
  @author  redkaras 
  @since  2018. 6. 28.
  @version 1.0
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
