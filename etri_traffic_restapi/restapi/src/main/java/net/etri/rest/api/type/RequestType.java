package net.etri.rest.api.type;

/**
  <p>클래스 설명 : 요청유형에 대한 열거형</p>
  <p>RequestType</p>
  <pre>
   net.etri.rest.api.type
          └ RequestType.java
  </pre>
**/
public enum RequestType implements RequestTypeInterface {

	SCENARIO_REGION(0){
		@Override
		public String toString() {		
			return "SCENARIO REGION";
		}
		@Override
		public String URL() {
			return "ScenarioByRegion";
		}
	}, 
	SCENARIO_COORDINATE(1){
		@Override
		public String toString() {		
			return "SCENARIO COORDNATE";
		}
		@Override
		public String URL() {
			return "ScenarioByCoordinate";
		}
	},
	MAP_REGION(2){
		@Override
		public String toString() {		
			return "MAP REGION";
		}
		@Override
		public String URL() {
			return "MapByRegion";
		}
	}, 
	MAP_COORDINATE(3){
		@Override
		public String toString() {		
			return "MAP COORDNATE";
		}
		@Override
		public String URL() {
			return "MapByCoordinate";
		}
	},
	SIGNAL_REGION(4){
		@Override
		public String toString() {		
			return "SIGNAL REGION";
		}
		@Override
		public String URL() {
			return "SignalByRegion";
		}
	}, 
	SIGNAL_COORDINATE(5){
		@Override
		public String toString() {		
			return "SIGNAL COORDNATE";
		}
		@Override
		public String URL() {
			return "SignalByCoordinate";
		}
	},
	SCENARIO_REGION2(6){
		@Override
		public String toString() {		
			return "SCENARIO REGION2";
		}
		@Override
		public String URL() {
			return "ScenarioByRegion2";
		}
	}, 
	MAP_REGION2(7){
		@Override
		public String toString() {		
			return "MAP REGION2";
		}
		@Override
		public String URL() {
			return "MapByRegion2";
		}
	}, 
	SIGNAL_REGION2(8){
		@Override
		public String toString() {		
			return "SIGNAL REGION2";
		}
		@Override
		public String URL() {
			return "SignalByRegion2";
		}
	}, 
	UNKONWN(9){
		@Override
		public String toString() {		
			return "UNKONWN";
		}
		@Override
		public String URL() {
			return null;
		}
	};
	
	private int value;

	private RequestType(int value) {
		this.value = value;
    }
	
	public int getValue(){
		return this.value;
	}
	
	public static RequestType get(int v){
		RequestType[] values = RequestType.values();
		for(RequestType rct : values ){
			if(rct.getValue() == v){
				return rct;
			}
		}
		return null;
	}
}
