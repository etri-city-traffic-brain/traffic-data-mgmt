package net.etri.rest.api.type;

/**
  <p>클래스 설명 : 궤적요청에 대한 열거형</p>
  <p>RouteType</p>
  <pre>
   net.etri.rest.api.type
          └ RouteType.java
  </pre>
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
