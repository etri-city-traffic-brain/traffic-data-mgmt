package net.etri.rest.api.type;

/**
  <p>클래스 설명 : XML데이터 유형에 대한 열거형</p>
  <p>XmlType</p>
  <pre>
   net.etri.rest.api.type
          └ XmlType.java
  </pre>
**/
public enum XmlType {
	NODE(0), 
	EDGE(1), 
	CONNECTION(2), 
	TLLOGIC(3),
	WEATHER(4),
	NET(5),
	SCENARIO(6),
	BUSSTOP(7);
	
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
