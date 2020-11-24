/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.xml
 * @file TrafficSignalScenario.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일			버전			수정자		수정내용
 * -------------	--------	------		-------------------------------
 * 2018. 9. 21.		0.0.1		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import net.solufine.rest.api.xml.service.SignalPhase;

/**
  <p>클래스 설명 : TrafficSignalScenario정보 XML처리를 위한 클래스</p>
  <p>TrafficSignalScenario</p>
  <pre>
   net.solufine.rest.api.xml
          └ TrafficSignalScenario.java
  </pre>
  @author  redkaras 
  @since  2018. 9. 21.
  @version 1.0
**/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"phase", "offset", "id"})
@JsonInclude(Include.NON_NULL)
public class TrafficSignalScenario {

	@XmlAttribute(name = "id", required = true)
	private int id = 0;
	@XmlAttribute(name = "offset")
	private int offset = 0;
	@XmlElement(name="phase")
	private List<TrafficSignalPhase> phase = null;
	/**
	 Constructor of TrafficSignalScenario.java class
	*/
	public TrafficSignalScenario(){
		
	}
	/**
	 Constructor of TrafficSignalScenario.java class
	 @param pattern_id
	 @param o
	*/
	public TrafficSignalScenario(int pattern_id, int o){
		this.id = pattern_id;
		this.offset = o;
	}
	/**
	 Constructor of TrafficSignalScenario.java class
	 @param pattern_id
	 @param o
	 @param phase
	*/
	public TrafficSignalScenario(int pattern_id, int o, List<SignalPhase> phase){
		this.id = pattern_id;
		this.offset = o;
		if(phase != null && !phase.isEmpty()){
			this.phase = new ArrayList<TrafficSignalPhase>();
			setSignalPhase(phase);
		}
	}
	/**
     @return the id
	*/
	public int getId() {
		return id;
	}
	/**
  	 @param id the id to set
	*/
	public void setId(int id) {
		this.id = id;
	}
	/**
     @return the offset
	*/
	public int getOffset() {
		return offset;
	}
	/**
  	 @param offset the offset to set
	*/
	public void setOffset(int offset) {
		this.offset = offset;
	}
	/**
     @return the phase
	*/
	public List<TrafficSignalPhase> getPhase() {
		return phase;
	}
	/**
  	 @param phase the phase to set
	*/
	public void setPhase(List<TrafficSignalPhase> phase) {
		this.phase = phase;
	}
	/**
 	 @param phase the phase to set
	*/
	private void setSignalPhase(List<SignalPhase> phase){		
		// TODO : 신호 XML 생성에 필요한 현시 처리 ( 황색 및 전적색 신호 포함 )
		// 전적색 신호는 교차로내의 차량을 소거시키기 위해 교차로에서 모든 방향에 적색신호를 등화하는 방법
		// 황색시간은 최대 5초로 하며, 이를 넘는 나머지 시간은 1～2초의 전적색시간으로
		if(phase.size() == 1){
			this.phase.add( new TrafficSignalPhase(phase.get(0).getDuration(), phase.get(0).getState())  );
			return;
		}
		
		String preState = phase.get(0).getState();
		String nextState = "";	
		for(int i = 1; i < phase.size(); i++){		
			boolean bYellow = false;			
			nextState = phase.get(i).getState(); 
			String state = "";
			String red_state = "";
			for(int c = 0; c < preState.length(); c++ ){
				String pre = String.valueOf(preState.toLowerCase().charAt(c));
				String next = String.valueOf(nextState.toLowerCase().charAt(c));
				if( (pre == "g" || pre.equals("g")) && (next == "r" || next.equals("r")) ){
					state = state + "y";
					red_state = red_state + "r";
					bYellow = true;						
				}else{
					state = state + pre;
					red_state = red_state + pre;
				}
			}
			
			if(bYellow && phase.get(i - 1).getYellow_duration() > 0){
				int duration = phase.get(i - 1).getDuration();
				int yellowDuration = phase.get(i - 1).getYellow_duration();
				int redDuration = phase.get(i - 1).getRed_duration();
				
				phase.get(i - 1).setDuration(duration - yellowDuration - redDuration);
				this.phase.add( new TrafficSignalPhase(phase.get(i - 1).getDuration(), phase.get(i - 1).getState())  );
				this.phase.add( new TrafficSignalPhase(yellowDuration, state)  );
				if( redDuration > 0){
					this.phase.add( new TrafficSignalPhase(redDuration, red_state)  );
				}
			}else{
				this.phase.add( new TrafficSignalPhase(phase.get(i - 1).getDuration(), phase.get(i - 1).getState())  );
			}
			preState = nextState;
		}
		
		preState = phase.get(phase.size() - 1).getState();
		nextState = phase.get(0).getState();
		boolean bYellow = false;	
		String state = "";
		String red_state = "";
		
		// 2020-11-11
		//System.out.println("nextState : " + nextState + ", nextStateLength : " + nextState.length());
		//System.out.println("preState : " + preState + ", preState : " + preState.length());
		
		if (nextState.length() < preState.length())
		{
			System.out.println("nextState : " + nextState + ", nextStateLength : " + nextState.length());
			System.out.println("preState : " + preState + ", preStateLength : " + preState.length());
		}
		 
		for(int c = 0; c < preState.length(); c++ ){
			String pre = String.valueOf(preState.toLowerCase().charAt(c));
			
			//String next = null;
			//if(nextState.length() >= preState.length())
			//	next = String.valueOf(nextState.toLowerCase().charAt(c));
			String next = String.valueOf(nextState.toLowerCase().charAt(c));
			
			if( (pre == "g" || pre.equals("g")) && (next == "r" || next.equals("r")) ){
				state = state + "y";
				red_state = red_state + "r";
				bYellow = true;						
			}else{
				state = state + pre;
				red_state = red_state + pre;
			}
		}
		
		if(bYellow && phase.get(phase.size() - 1).getYellow_duration() > 0){
			int duration = phase.get(phase.size() - 1).getDuration();
			int yellowDuration = phase.get(phase.size() - 1).getYellow_duration();
			int redDuration = phase.get(phase.size() - 1).getRed_duration();
			
			phase.get(phase.size() - 1).setDuration(duration - yellowDuration - redDuration);
			this.phase.add( new TrafficSignalPhase(phase.get(phase.size() - 1).getDuration(), phase.get(phase.size() - 1).getState())  );
			this.phase.add( new TrafficSignalPhase(yellowDuration, state)  );
			if( redDuration > 0){
				this.phase.add( new TrafficSignalPhase(redDuration, red_state)  );
			}
		}else{
			this.phase.add( new TrafficSignalPhase(phase.get(phase.size() - 1).getDuration(), phase.get(phase.size() - 1).getState())  );
		}
		
	}
}
