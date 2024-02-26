package net.etri.rest.api.type;

/**
  <p>클래스 설명 : 데이터 출력을 위한 포함유형 열거형</p>
  <p>IncludeType</p>
  <pre>
   net.etri.rest.api.type
          └ IncludeType.java
  </pre>
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
	BUSSTOP(7){
		@Override
		public String toString() {
			return "BUSSTOP";
		}
		@Override
		public String fileName() {
			return "busstops.xml";
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
