/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.type
 * @file XmlType.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일			버전			수정자		수정내용
 * -------------	--------	------		-------------------------------
 * 2018. 6. 29.		0.0.1		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.type;

/**
  <p>클래스 설명 : XML데이터 유형에 대한 열거형</p>
  <p>XmlType</p>
  <pre>
   net.solufine.rest.api.type
          └ XmlType.java
  </pre>
  @author  redkaras 
  @since  2018. 6. 29.
  @version 1.0
**/
public enum XmlType {
	NODE(0), 
	EDGE(1), 
	CONNECTION(2), 
	TLLOGIC(3),
	WEATHER(4),
	NET(5),
	SCENARIO(6);
	
	private int value;

	private XmlType(int value) {
		this.value = value;
    }
	
	public int getValue(){
		return this.value;
	}
	
	public static XmlType get(int v){
		XmlType[] values = XmlType.values();
		for(XmlType rct : values ){
			if(rct.getValue() == v){
				return rct;
			}
		}
		return null;
	}
}
