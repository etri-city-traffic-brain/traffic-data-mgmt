package net.etri.rest.api.sumo.service;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import net.etri.rest.api.xml.type.TlLogicType;

/**
<p>클래스 설명 : TlLogic정보 XML처리를 위한 클래스</p>
<p>TlLogic</p>
<pre>
 net.etri.rest.api.sumo.service
        └ TlLogic.java
</pre>
**/
@XmlAccessorType(XmlAccessType.NONE)
@JsonInclude(Include.NON_NULL)
public class TlLogic {

	@XmlAttribute(name = "id")
	private String id = null;
	@XmlAttribute(name = "type")
	private TlLogicType tlLogicType = TlLogicType.STATIC;
	@XmlAttribute(name = "programID")
	private int programID = 0;
	@XmlAttribute(name = "offset")
	private int offset;
	@XmlElement(name="phase")
	private List<Phase> phases = null;
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
		if(this.id.isEmpty()){
			this.id = null;
		}
	}
	/**
	 * @return the tlLogicType
	 */
	public TlLogicType getTlLogicType() {
		return tlLogicType;
	}
	/**
	 * @param tlLogicType the tlLogicType to set
	 */
	public void setTlLogicType(TlLogicType tlLogicType) {
		this.tlLogicType = tlLogicType;
	}
	/**
	 * @return the programID
	 */
	public int getProgramID() {
		return programID;
	}
	/**
	 * @param programID the programID to set
	 */
	public void setProgramID(int programID) {
		this.programID = programID;
	}
	/**
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}
	/**
	 * @param offset the offset to set
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}
	/**
	 * @return the phases
	 */
	public List<Phase> getPhases() {
		return phases;
	}
	/**
	 * @param phases the phases to set
	 */
	public void setPhases(List<Phase> phases) {
		this.phases = phases;
	}
	
	public void addPhases(int seq, int duration, int yellowDuration, int redDuration, String state) {
		if( this.phases == null) {
			this.phases = new LinkedList<Phase>();
		}	
		this.phases.add( new Phase(seq, duration, yellowDuration, redDuration, state) );
	}
	
	public void calcPhases() {
		if( this.phases == null || this.phases.isEmpty() || this.phases.size() == 1)
			return;
		
		LinkedList<Phase> calPhase = new LinkedList<Phase>();
		
		String preState = this.phases.get(0).getState();
		String nextState = "";	
		for(int i = 1; i < this.phases.size(); i++){		
			boolean bYellow = false;			
			nextState = this.phases.get(i).getState(); 
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
			
			if(bYellow && this.phases.get(i - 1).getYellowDuration() > 0){
				int duration = this.phases.get(i - 1).getDuration();
				int yellowDuration = this.phases.get(i - 1).getYellowDuration();
				int redDuration = this.phases.get(i - 1).getRedDuration();
				
				this.phases.get(i - 1).setDuration(duration - yellowDuration - redDuration);
				
				calPhase.add( new Phase(this.phases.get(i - 1).getSeq(), this.phases.get(i - 1).getDuration(), this.phases.get(i - 1).getState())  );
				calPhase.add( new Phase(this.phases.get(i - 1).getSeq(), yellowDuration, state)  );
				if( redDuration > 0){
					calPhase.add( new Phase(this.phases.get(i - 1).getSeq(), redDuration, red_state)  );
				}
			}else{
				calPhase.add( new Phase(this.phases.get(i - 1).getSeq(), this.phases.get(i - 1).getDuration(), this.phases.get(i - 1).getState())  );
			}
			preState = nextState;
		}
		
		preState = this.phases.get(this.phases.size() - 1).getState();
		nextState = this.phases.get(0).getState();
		boolean bYellow = false;	
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
		
		if(bYellow && this.phases.get(this.phases.size() - 1).getYellowDuration() > 0){
			int duration = this.phases.get(this.phases.size() - 1).getDuration();
			int yellowDuration = this.phases.get(this.phases.size() - 1).getYellowDuration();
			int redDuration = this.phases.get(this.phases.size() - 1).getRedDuration();
			
			this.phases.get(this.phases.size() - 1).setDuration(duration - yellowDuration - redDuration);
	
			calPhase.add( new Phase(this.phases.get(this.phases.size() - 1).getSeq(), this.phases.get(this.phases.size() - 1).getDuration(), this.phases.get(this.phases.size() - 1).getState())  );
			calPhase.add( new Phase(this.phases.get(this.phases.size() - 1).getSeq(), yellowDuration, state)  );
			if( redDuration > 0){
				calPhase.add( new Phase(this.phases.get(this.phases.size() - 1).getSeq(), redDuration, red_state)  );
			}
		}else{
			calPhase.add( new Phase(this.phases.get(this.phases.size() - 1).getSeq(), this.phases.get(this.phases.size() - 1).getDuration(), this.phases.get(this.phases.size() - 1).getState())  );
		}
		
		this.phases.clear();
		this.phases.addAll(calPhase);
		
	}
}
