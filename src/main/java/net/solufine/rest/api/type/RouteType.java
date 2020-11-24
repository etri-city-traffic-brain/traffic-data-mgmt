/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.type
 * @file RouteType.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일			버전			수정자		수정내용
 * -------------	--------	------		-------------------------------
 * 2018. 7. 12.		0.0.1		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.type;

/**
  <p>클래스 설명 : 궤적요청에 대한 열거형</p>
  <p>RouteType</p>
  <pre>
   net.solufine.rest.api.type
          └ RouteType.java
  </pre>
  @author  redkaras 
  @since  2018. 7. 12.
  @version 1.0
**/
public enum RouteType {

	NOT_INCLUDE(0), 
	SKT_ROUTE(1);
	
	private int value;

	private RouteType(int value) {
		this.value = value;
    }
	
	public int getValue(){
		return this.value;
	}
	
	public static RouteType get(int v){
		RouteType[] values = RouteType.values();
		for(RouteType rct : values ){
			if(rct.getValue() == v){
				return rct;
			}
		}
		return null;
	}
}
