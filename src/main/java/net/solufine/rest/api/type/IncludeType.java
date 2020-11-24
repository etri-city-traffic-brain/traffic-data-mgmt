/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.type
 * @file IncludeType.java
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
  <p>클래스 설명 : 데이터 출력을 위한 포함유형 열거형</p>
  <p>IncludeType</p>
  <pre>
   net.solufine.rest.api.type
          └ IncludeType.java
  </pre>
  @author  redkaras 
  @since  2018. 7. 12.
  @version 1.0
**/
public enum IncludeType implements IncludeTypeInterface {
	//scenario.zip : node.xml, edge.xml, connection.xml, tss.xml
	SCENARIO(0){
		@Override
		public String toString() {
			return "SCENARIO";
		}
		@Override
		public String fileName() {
			return "scenario.zip";
		}
	},	
	//map.zip : node.xml, edge.xml, connection.xml
	MAP(1){
		@Override
		public String toString() {
			return "MAP";
		}
		@Override
		public String fileName() {
			return "map.zip";
		}
	},
	//tss.xml
	TSS(2){
		@Override
		public String toString() {
			return "TSS";
		}
		@Override
		public String fileName() {
			return "tss.xml";
		}
	},	
	NODE(3){
		@Override
		public String toString() {
			return "NODE";
		}
		@Override
		public String fileName() {
			return "node.xml";
		}
	},
	EDGE(4){
		@Override
		public String toString() {
			return "EDGE";
		}
		@Override
		public String fileName() {
			return "edge.xml";
		}
	},
	CONNECTION(5){
		@Override
		public String toString() {
			return "CONNECTION";
		}
		@Override
		public String fileName() {
			return "connection.xml";
		}
	},
	TLLGIC(6){
		@Override
		public String toString() {
			return "TLLGIC";
		}
		@Override
		public String fileName() {
			return "tll.xml";
		}
	},
	UNKONWN(9){
		@Override
		public String toString() {		
			return "UNKONWN";
		}
		@Override
		public String fileName() {
			return null;
		}
	};
	
	private int value;

	private IncludeType(int value) {
		this.value = value;
    }
	
	public int getValue(){
		return this.value;
	}
	
	public static IncludeType get(int v){
		IncludeType[] values = IncludeType.values();
		for(IncludeType rct : values ){
			if(rct.getValue() == v){
				return rct;
			}
		}
		return null;
	}
}
