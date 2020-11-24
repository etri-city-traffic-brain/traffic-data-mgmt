/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project rest.api
 * @package net.solufine.rest.api.repository
 * @file RequestVO.java
 *
 * <pre>
 * 개정이력(Modification Information)
 *	수정일			버전			수정자		수정내용
 * -------------	--------	------		-------------------------------
 * 2018. 6. 29.		0.0.1		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
package net.solufine.rest.api.repository;

import java.util.ArrayList;
import java.util.List;

import net.solufine.rest.api.service.DefaultVO;
import net.solufine.rest.api.type.IncludeType;
import net.solufine.rest.api.type.RequestType;
import net.solufine.rest.api.type.RouteType;

/**
  <p>클래스 설명 : 요청파라이터VO 클래스</p>
  <p>RequestVO</p>
  <pre>
   net.solufine.rest.api.repository
          └ RequestVO.java
  </pre>
  @author  redkaras 
  @since  2018. 10. 4.
  @version 1.0
**/
@SuppressWarnings("serial")
public class RequestVO extends DefaultVO {
	
	private IncludeType include = IncludeType.UNKONWN;
	private String reg_date = null;
	private String req_date = null;
	private String from_date = null;
	private String to_date = null;
	private String fromTime = null;
	private String toTime = null;
	
	private int dong_cd;
	private String region = null;
	private String subregion = null;
	
	private float minX;
	private float minY;
	private float maxX;
	private float maxY;
	 
	private int partition_cnt;
	private int partitionNo;
	private int partition_id;
	
	private boolean signal_yn;
	private boolean event_yn;
	private boolean weather_yn;
	private RouteType route;
	private RequestType requestType;
	private String req_div = "COORDS";
	
	private String sigu_nm = null;
	private String dong_nm = null;
	private String download = null;
	
	private String signal_id = null; 
	private String version = null; 
	
	private List<PeriodDay> periodDate = new ArrayList<PeriodDay>();
	
	/**	  
	 Constructor of RequestVO.java class	  
	*/
	public RequestVO(){
		
	}	
	/**	  
	 Constructor of RequestVO.java class
	 @param include 결과 포함 내용
	 @param req_date 시작일자
	 @param toDate 종료일자
	 @param fromTime 시작시간
	 @param toTime 종료시간
	 @param region 시구코드
	 @param subregion 동코드
	 @param partition_cnt 파티션 개수
	 @param signal_yn 신호포함여부
	 @param route 궤적유형
	 @param event_yn 돌발포함여부
	 @param weather_yn 날씨포함여부	 
	 @param requestType 요청유형 
	*/
	public RequestVO(IncludeType include, String req_date, String toDate, String fromTime, String toTime, 
			 			String region, String subregion, int partition_cnt, boolean signal_yn, RouteType route, 
			 			boolean event_yn, boolean weather_yn, RequestType requestType){
		this.include = include;
		this.req_date = req_date;
		this.from_date = req_date;
		this.to_date = toDate;
		this.fromTime = fromTime;
		this.toTime = toTime;
		SetDongCode(region, subregion);		
		this.partition_cnt = partition_cnt;
		this.signal_yn = signal_yn;
		setRequestType(requestType);
	}
	/**	  
	 Constructor of RequestVO.java class
	 @param include 결과 포함 내용
	 @param req_date 시작일자
	 @param toDate 종료일자
	 @param fromTime 시작시간
	 @param toTime 종료시간
	 @param minX 최소X좌표
	 @param minY 최소Y좌표
	 @param maxX 최대X좌표
	 @param maxY 최대Y좌표
	 @param partition_cnt 파티션 개수
	 @param signal_yn 신호포함여부
	 @param route 궤적유형
	 @param event_yn 돌발포함여부
	 @param weather_yn 날씨포함여부	 
	 @param requestType 요청유형 
	*/
	public RequestVO(IncludeType include, String req_date, String toDate, String fromTime, String toTime,
						float minX, float minY, float maxX, float maxY, int partition_cnt, boolean signal_yn, 
						RouteType route, boolean event_yn, boolean weather_yn, RequestType requestType){
		this.include = include;
		this.req_date = req_date;
		this.from_date = req_date;
		this.to_date = toDate;
		this.fromTime = fromTime;
		this.toTime = toTime;
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
		this.partition_cnt = partition_cnt;
		this.signal_yn = signal_yn;
		setRequestType(requestType);
	}
	/**	  
	 Constructor of RequestVO.java class
	 @param include 결과 포함 내용
	 @param region 시구코드
	 @param subregion 동코드
	 @param partition_cnt 파티션 개수
	 @param requestType 요청유형 
	*/
	public RequestVO(IncludeType include, String region, String subregion, int partition_cnt, RequestType requestType){
		this.include = include;
		SetDongCode(region, subregion);	
		this.partition_cnt = partition_cnt;
		setRequestType(requestType);
	}
	/**	  
	 Constructor of RequestVO.java class
	 @param include 결과 포함 내용
	 @param minX 최소X좌표
	 @param minY 최소Y좌표
	 @param maxX 최대X좌표
	 @param maxY 최대Y좌표
	 @param partition_cnt 파티션 개수
	 @param requestType 요청유형 
	*/
	public RequestVO(IncludeType include, float minX, float minY, float maxX, float maxY, int partition_cnt, RequestType requestType){
		this.include = include;
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
		this.partition_cnt = partition_cnt;
		setRequestType(requestType);
	}
	/**	  
	 Constructor of RequestVO.java class
	 @param include 결과 포함 내용
	 @param req_date 시작일자
	 @param toDate 종료일자
	 @param fromTime 시작시간
	 @param toTime 종료시간
	 @param region 시구코드
	 @param subregion 동코드
	 @param partition_cnt 파티션 개수	 
	 @param requestType 요청유형 
	*/
	public RequestVO(IncludeType include, String req_date, String toDate,  String fromTime, String toTime,
			String region, String subregion, int partition_cnt, RequestType requestType) {
		this.include = include;
		this.req_date = req_date;
		this.from_date = req_date;
		this.to_date = toDate;
		this.fromTime = fromTime;
		this.toTime = toTime;
		SetDongCode(region, subregion);		
		this.partition_cnt = partition_cnt;
		setRequestType(requestType);
	}
	/**	  
	 Constructor of RequestVO.java class
	 @param include 결과 포함 내용
	 @param req_date 시작일자
	 @param toDate 종료일자
	 @param fromTime 시작시간
	 @param toTime 종료시간
	 @param minX 최소X좌표
	 @param minY 최소Y좌표
	 @param maxX 최대X좌표
	 @param maxY 최대Y좌표
	 @param partition_cnt 파티션 개수
	 @param requestType 요청유형 
	*/
	public RequestVO(IncludeType include, String req_date, String toDate, String fromTime, String toTime,
			float minX, float minY, float maxX, float maxY, int partition_cnt, RequestType requestType) {
		this.include = include;
		this.req_date = req_date;
		this.from_date = req_date;
		this.to_date = toDate;
		this.fromTime = fromTime;
		this.toTime = toTime;
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
		this.partition_cnt = partition_cnt;
		setRequestType(requestType);
	}
	
	/**	  
	 Constructor of RequestVO.java class
	 @param include 결과 포함 내용
	 @param fromDate 시작일자
	 @param toDate 종료일자
	 @param fromTime 시작시간
	 @param toTime 종료시간
	 @param signal_yn 신호포함여부
	 @param version 버전	 
	 @param requestType 요청유형 
	*/
	public RequestVO(IncludeType include, String req_date, String toDate, String fromTime, String toTime, boolean signal_yn, String version, RequestType requestType){
		this.req_date = req_date;
		this.from_date = req_date;
		this.to_date = toDate;
		this.fromTime = fromTime;
		this.toTime = toTime;		
		this.signal_yn = signal_yn;
		this.version = version;
		setRequestType(requestType);
	}
	/**	  
	 Constructor of RequestVO.java class
	 @param include 결과 포함 내용
	 @param req_date 시작일자
	 @param toDate 종료일자
	 @param fromTime 시작시간
	 @param toTime 종료시간
	 @param region 시구코드
	 @param subregion 동코드
	 @param signal_yn 신호포함여부
	 @param event_yn 돌발포함여부
	 @param weather_yn 날씨포함여부
	 @param route 궤적
	 @param requestType 요청유형
	*/
	public RequestVO(IncludeType include, String req_date, String toDate, String fromTime, String toTime, String region, String subregion, 
			boolean signal_yn, boolean event_yn, boolean weather_yn, RouteType route, RequestType requestType){
		this.req_date = req_date;
		this.from_date = req_date;
		this.to_date = toDate;
		this.fromTime = fromTime;
		this.toTime = toTime;
		SetDongCode(region, subregion);	
		this.signal_yn = signal_yn;
		this.event_yn = event_yn;
		this.weather_yn = weather_yn;
		this.route = route;
		setRequestType(requestType);
	}
	/**	  
	 Constructor of RequestVO.java class
	 @param include 결과 포함 내용
	 @param req_date 시작일자
	 @param toDate 종료일자
	 @param fromTime 시작시간
	 @param toTime 종료시간
	 @param minX 최소X좌표
	 @param minY 최소Y좌표
	 @param maxX 최대X좌표
	 @param maxY 최대Y좌표
	 @param signal_yn 신호포함여부
	 @param event_yn 돌발포함여부
	 @param weather_yn 날씨포함여부
	 @param route 궤적
	 @param requestType 요청유형
	*/
	public RequestVO(IncludeType include, String req_date, String toDate, String fromTime, String toTime, float minX, float minY, float maxX, float maxY,
			boolean signal_yn, boolean event_yn, boolean weather_yn, RouteType route, RequestType requestType){
		this.req_date = req_date;
		this.from_date = req_date;
		this.to_date = toDate;
		this.fromTime = fromTime;
		this.toTime = toTime;
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
		this.signal_yn = signal_yn;
		this.event_yn = event_yn;
		this.weather_yn = weather_yn;
		this.route = route;
		setRequestType(requestType);
	}
	/**	  
	 Constructor of RequestVO.java class
	 @param include 결과 포함 내용
	 @param req_date 시작일자
	 @param toDate 종료일자
	 @param fromTime 시작시간
	 @param toTime 종료시간
	 @param region 시구코드
	 @param partition_cnt 파티션 개수
	 @param signal_yn 신호포함여부
	 @param event_yn 돌발포함여부
	 @param weather_yn 날씨포함여부
	 @param route 궤적
	 @param requestType 요청유형
	*/
	public RequestVO(IncludeType include, String req_date, String toDate, String fromTime, String toTime, String region, int partition_cnt, 
			boolean signal_yn, boolean event_yn, boolean weather_yn, RouteType route, RequestType requestType){
		this.req_date = req_date;
		this.from_date = req_date;
		this.to_date = toDate;
		this.fromTime = fromTime;
		this.toTime = toTime;
		SetDongCode(region, null);		
		this.partition_cnt = partition_cnt;
		this.signal_yn = signal_yn;
		this.event_yn = event_yn;
		this.weather_yn = weather_yn;
		this.route = route;
		setRequestType(requestType);
	}
	/**	  
	 Constructor of RequestVO.java class
	 @param include 결과 포함 내용
	 @param req_date 시작일자
	 @param toDate 종료일자
	 @param fromTime 시작시간
	 @param toTime 종료시간
	 @param region 시구코드
	 @param partition_cnt 파티션 개수
	 @param partitionNo 파티션번호
	 @param signal_yn 신호포함여부
	 @param event_yn 돌발포함여부
	 @param weather_yn 날씨포함여부
	 @param route 궤적
	 @param requestType 요청유형
	*/
	public RequestVO(IncludeType include, String req_date, String toDate, String fromTime, String toTime, String region, int partition_cnt, int partitionNo,
			boolean signal_yn, boolean event_yn, boolean weather_yn, RouteType route, RequestType requestType){
		this.req_date = req_date;
		this.from_date = req_date;
		this.to_date = toDate;
		this.fromTime = fromTime;
		this.toTime = toTime;
		SetDongCode(region, null);	
		this.partition_cnt = partition_cnt;
		this.partitionNo = partitionNo;
		this.signal_yn = signal_yn;
		this.event_yn = event_yn;
		this.weather_yn = weather_yn;
		this.route = route;
		setRequestType(requestType);
	}
	/**	  
	 Constructor of RequestVO.java class
	 @param include 결과 포함 내용
	 @param fromDate 시작일자
	 @param toDate 종료일자
	 @param fromTime 시작시간
	 @param toTime 종료시간	  
	 @param signal_yn 신호포함여부
	 @param requestType 요청유형
	*/
	public RequestVO(IncludeType include, String req_date, String toDate, String fromTime, String toTime, Boolean signal_yn, RequestType requestType) {
		this.req_date = req_date;
		this.from_date = req_date;
		this.to_date = toDate;
		this.fromTime = fromTime;
		this.toTime = toTime;		
		this.signal_yn = signal_yn;		
		setRequestType(requestType);
	}
	
	/**	  
	 시구코드, 동코드 설정 
	 @param region 시구코드
	 @param subregion 동코드
	*/
	private void SetDongCode(String region, String subregion){
		if(region != null && !region.isEmpty()){
			this.region = region;
			if(subregion != null && !subregion.isEmpty()){
				this.subregion = subregion;
				this.dong_cd = Integer.valueOf( this.region + this.subregion );
			}	
		}
	}
	
	/**
	 @return the include
	*/
	public IncludeType getInclude() {
		return include;
	}
	/**
	 @param include the include to set
	*/
	public void setInclude(IncludeType include) {
		this.include = include;
	}
	
	/**
	 @return the req_date
	*/
	public String getReq_date() {
		return req_date;
	}
	/**
	 @param req_date the req_date to set
	*/
	public void setReq_date(String req_date) {
		this.req_date = req_date;
	}
	/**
     @return the reg_date
	*/
	public String getReg_date() {
		return reg_date;
	}
	/**
  	 @param reg_date the reg_date to set
	*/
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

	/**
     @return the fromTime
	*/
	public String getFromTime() {
		return fromTime;
	}
	/**
  	 @param fromTime the fromTime to set
	*/
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}
	/**
     @return the toTime
	*/
	public String getToTime() {
		return toTime;
	}
	/**
  	 @param toTime the toTime to set
	*/
	public void setToTime(String toTime) {
		this.toTime = toTime;
	}
		
	/**
     @return the route
	*/
	public RouteType getRoute() {
		return route;
	}
	/**
     @param route the route to set
	*/
	public void setRoute(RouteType route) {
		this.route = route;
	}
	/**
     @return the requestType
	*/
	public RequestType getRequestType() {
		return requestType;
	}
	/**
  	 @param requestType the requestType to set
	*/
	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
		
		switch(this.requestType) {
			case SCENARIO_COORDINATE :	
			case MAP_COORDINATE :
			case SIGNAL_COORDINATE : 
				req_div = "COORDS";
				break;
			case SCENARIO_REGION : 
			case MAP_REGION : 
			case SIGNAL_REGION :
				req_div = "REGION";
				break;
			case UNKONWN :
				req_div = "";
				break;
		}
		
		
		
	}
	
	/**
     @return the dong_cd
	*/
	public int getDong_cd() {
		return dong_cd;
	}

	/**
  	 @param dong_cd the dong_cd to set
	*/
	public void setDong_cd(int dong_cd) {
		this.dong_cd = dong_cd;
	}

	/**
     @return the region
	*/
	public String getRegion() {
		return region;
	}

	/**
  	 @param region the region to set
	*/
	public void setRegion(String region) {
		this.region = region;
		if(this.region != null){
			this.region = this.region.trim();
			if(!this.region.isEmpty()){
				if(this.subregion != null && !this.subregion.isEmpty() && this.subregion != "0" && !this.subregion.equals("0")){			
					this.dong_cd = Integer.valueOf( this.region + this.subregion );
				}
			}
		}
	}

	/**
     @return the subregion
	*/
	public String getSubregion() {
		return subregion;
	}

	/**
  	 @param subregion the subregion to set
	*/
	public void setSubregion(String subregion) {
		this.subregion = subregion;
		if(this.subregion != null){
			if(!this.subregion.isEmpty()){
				this.subregion = this.subregion.trim();
				if(this.subregion != "0"){
					if(!this.subregion.equals("0")){						
						if(this.region != null && !this.region.isEmpty()){
							this.dong_cd = Integer.valueOf( this.region + this.subregion );
						}
					}
				}
			}
		}
	}

	/**
     @return the minX
	*/
	public float getMinX() {
		return minX;
	}
	/**
  	 @param minX the minX to set
	*/
	public void setMinX(float minX) {
		this.minX = minX;
	}
	/**
     @return the minY
	*/
	public float getMinY() {
		return minY;
	}
	/**
  	 @param minY the minY to set
	*/
	public void setMinY(float minY) {
		this.minY = minY;
	}
	/**
     @return the maxX
	*/
	public float getMaxX() {
		return maxX;
	}
	/**
  	 @param maxX the maxX to set
	*/
	public void setMaxX(float maxX) {
		this.maxX = maxX;
	}
	/**
     @return the maxY
	*/
	public float getMaxY() {
		return maxY;
	}
	/**
  	 @param maxY the maxY to set
	*/
	public void setMaxY(float maxY) {
		this.maxY = maxY;
	}
	
	/**
     @return the partition_cnt
	*/
	public int getPartition_cnt() {
		return partition_cnt;
	}

	/**
  	 @param partition_cnt the partition_cnt to set
	*/
	public void setPartition_cnt(int partition_cnt) {
		this.partition_cnt = partition_cnt;
	}

	/**
     @return the partitionNo
	*/
	public int getPartitionNo() {
		return partitionNo;
	}
	/**
  	 @param partitionNo the partitionNo to set
	*/
	public void setPartitionNo(int partitionNo) {
		this.partitionNo = partitionNo;
	}
	/**
     @return the signal_yn
	*/
	public boolean isSignal_yn() {
		return signal_yn;
	}
	/**
  	 @param signal_yn the signal_yn to set
	*/
	public void setSignal_yn(boolean signal_yn) {
		this.signal_yn = signal_yn;
	}
	/**
     @return the event_yn
	*/
	public boolean isEvent_yn() {
		return event_yn;
	}
	/**
  	 @param event_yn the event_yn to set
	*/
	public void setEvent_yn(boolean event_yn) {
		this.event_yn = event_yn;
	}
	/**
     @return the weather_yn
	*/
	public boolean isWeather_yn() {
		return weather_yn;
	}
	/**
  	 @param weather_yn the weather_yn to set
	*/
	public void setWeather_yn(boolean weather_yn) {
		this.weather_yn = weather_yn;
	}

	/**
     @return the sigu_nm
	*/
	public String getSigu_nm() {
		return sigu_nm;
	}

	/**
  	 @param sigu_nm the sigu_nm to set
	*/
	public void setSigu_nm(String sigu_nm) {
		this.sigu_nm = sigu_nm;
	}

	/**
     @return the dong_nm
	*/
	public String getDong_nm() {
		return dong_nm;
	}

	/**
  	 @param dong_nm the dong_nm to set
	*/
	public void setDong_nm(String dong_nm) {
		this.dong_nm = dong_nm;
	}

	/**
     @return the signal_id
	*/
	public String getSignal_id() {
		return signal_id;
	}

	/**
  	 @param signal_id the signal_id to set
	*/
	public void setSignal_id(String signal_id) {
		this.signal_id = signal_id;
	}

	/**
     @return the version
	*/
	public String getVersion() {
		return version;
	}

	/**
  	 @param version the version to set
	*/
	public void setVersion(String version) {
		this.version = version;
	}
	
	/**
     @return the download
	*/
	public String getDownload() {
		if(download == null || download.isEmpty()){
			StringBuffer sb = new StringBuffer();
			
			switch(requestType){
				case SCENARIO_REGION : 
					sb.append(RequestType.SCENARIO_REGION.URL())
					.append("?reqDate=").append(req_date).append("&")
					.append("fromTime=").append(fromTime).append("&")
					.append("toTime=").append(toTime).append("&")
					.append("region=").append(region).append("&")
					.append("subregion=").append(subregion).append("&")	
					.append("partitions=").append(partition_cnt).append("&")
					.append("signal=").append(signal_yn);				
					break;
				case SCENARIO_REGION2 : 
					sb.append(RequestType.SCENARIO_REGION.URL())
					.append("?reqDate=").append(req_date).append("&")
					.append("fromTime=").append(fromTime).append("&")
					.append("toTime=").append(toTime).append("&")
					.append("region=").append(region).append("&")
					.append("subregion=").append(subregion).append("&")	
					.append("partitions=").append(partition_cnt).append("&")
					.append("signal=").append(signal_yn);				
					break; 
				case SCENARIO_COORDINATE : 
					sb.append(RequestType.SCENARIO_COORDINATE.URL())
					.append("?reqDate=").append(req_date).append("&")
					.append("fromTime=").append(fromTime).append("&")
					.append("toTime=").append(toTime).append("&")
					.append("minX=").append(minX).append("&")
					.append("minY=").append(minY).append("&")
					.append("maxX=").append(maxX).append("&")
					.append("maxY=").append(maxY).append("&")
					.append("partitions=").append(partition_cnt).append("&")
					.append("signal=").append(signal_yn);										
					break; 
				case MAP_REGION :
					sb.append(RequestType.MAP_REGION.URL())
					.append("?region=").append(region).append("&")
					.append("subregion=").append(subregion).append("&")	
					.append("partitions=").append(partition_cnt);
					break;					
				case MAP_COORDINATE : 
					sb.append(RequestType.MAP_COORDINATE.URL())
					.append("?minX=").append(minX).append("&")
					.append("minY=").append(minY).append("&")
					.append("maxX=").append(maxX).append("&")
					.append("maxY=").append(maxY).append("&")
					.append("partitions=").append(partition_cnt);
					break;
				case SIGNAL_REGION :
					sb.append(RequestType.SIGNAL_REGION.URL())
					.append("?reqDate=").append(req_date).append("&")
					.append("fromTime=").append(fromTime).append("&")
					.append("toTime=").append(toTime).append("&")
					.append("region=").append(region).append("&")
					.append("subregion=").append(subregion).append("&")	
					.append("partitions=").append(partition_cnt);			
					break;
				case SIGNAL_COORDINATE :
					sb.append(RequestType.SIGNAL_COORDINATE.URL())
					.append("?reqDate=").append(req_date).append("&")
					.append("fromTime=").append(fromTime).append("&")
					.append("toTime=").append(toTime).append("&")
					.append("minX=").append(minX).append("&")
					.append("minY=").append(minY).append("&")
					.append("maxX=").append(maxX).append("&")
					.append("maxY=").append(maxY).append("&")
					.append("partitions=").append(partition_cnt);			
					break;
				/*
				case MASTER :
					sb.append("requestPartitionedScenarioByMaster?").append("fromDate=").append(req_date).append("&");
					sb.append("toDate=").append(to_date).append("&");
					sb.append("fromTime=").append(fromTime).append("&").append("toTime=").append(toTime).append("&");
					sb.append("route=").append(route.getValue()).append("&").append("signal=").append(signal_yn).append("&");
					sb.append("event=").append(event_yn).append("&").append("weather=").append(weather_yn).append("&");						
					sb.append("region=").append(region).append("&").append("partitions=").append(partition_cnt);					
					break; 
				case WORKER : 
					sb.append("requestPartitionedScenarioByWorker?").append("fromDate=").append(req_date).append("&");
					sb.append("toDate=").append(to_date).append("&");
					sb.append("fromTime=").append(fromTime).append("&").append("toTime=").append(toTime).append("&");
					sb.append("route=").append(route.getValue()).append("&").append("signal=").append(signal_yn).append("&");
					sb.append("event=").append(event_yn).append("&").append("weather=").append(weather_yn).append("&");						
					sb.append("region=").append(region).append("&").append("partitions=").append(partition_cnt).append("&");
					sb.append("partitionNo=").append(partitionNo);			
					break; 
				case STANDALONE:
					sb.append("getScenarioByRegion?").append("fromDate=").append(req_date).append("&");
					sb.append("toDate=").append(to_date).append("&");
					sb.append("fromTime=").append(fromTime).append("&").append("toTime=").append(toTime).append("&");
					sb.append("route=").append(route.getValue()).append("&").append("signal=").append(signal_yn).append("&");
					sb.append("event=").append(event_yn).append("&").append("weather=").append(weather_yn).append("&");						
					sb.append("region=").append(region).append("&").append("partitions=").append(partition_cnt).append("&");
					sb.append("partitionNo=").append(partitionNo);
					break;
				case SIGNAL_VER:
					sb.append("getSignal?").append("fromDate=").append(req_date).append("&");
					sb.append("toDate=").append(to_date).append("&");
					sb.append("fromTime=").append(fromTime).append("&").append("toTime=").append(toTime).append("&");					
					sb.append("version=").append(version);
					break;
				case ROUTE:
					sb.append("getRoute?").append("fromDate=").append(req_date).append("&");
					sb.append("toDate=").append(to_date).append("&");
					sb.append("fromTime=").append(fromTime).append("&").append("toTime=").append(toTime).append("&");					
					sb.append("signal_yn=").append(signal_yn);
					break;
				*/
				case UNKONWN : break;
			default:
				break;
			}
			download = sb.toString();
		}
		return download;
	}

	/**
  	 @param download the download to set
	*/
	public void setDownload(String download) {
		this.download = download;
	}
	/**
	 @return String
	*/
	public String getLeftTop(){
		return String.valueOf(minX) + " " + String.valueOf(minY);
	}
	/**
	 @return String
	*/
	public String getRightTop(){
		return String.valueOf(maxX) + " " + String.valueOf(minY);
	}
	/**
	 @return String
	*/
	public String getRightBottom(){
		return String.valueOf(maxX) + " " + String.valueOf(maxY);
	}
	/**
	 @return String
	*/
	public String getLeftBottom(){
		return String.valueOf(minX) + " " + String.valueOf(maxY);
	}

	/**
	 @return the partition_id
	*/
	public int getPartition_id() {
		return partition_id;
	}

	/**
	 @param partition_id the partition_id to set
	*/
	public void setPartition_id(int partition_id) {
		this.partition_id = partition_id;
	}
	
	/**
	 @return the from_date
	*/
	public String getFrom_date() {
		return from_date;
	}
	/**
	 @param from_date the from_date to set
	*/
	public void setFrom_date(String from_date) {
		this.from_date = from_date;
	}
	/**
	 @return the to_date
	*/
	public String getTo_date() {
		return to_date;
	}
	/**
	 @param to_date the to_date to set
	*/
	public void setTo_date(String to_date) {
		this.to_date = to_date;
	}
	
	public void addPeriodDay(String pd, String ft, String tt) {
		periodDate.add( new PeriodDay(pd, ft, tt) );
	}
	
	/**
	 * @return the periodDate
	 */
	public List<PeriodDay> getPeriodDate() {
		return periodDate;
	}
	/**
	 * @param periodDate the periodDate to set
	 */
	public void setPeriodDate(List<PeriodDay> periodDate) {
		this.periodDate = periodDate;
	}
	
	/**
	 * @return the req_div
	 */
	public String getReq_div() {
		return req_div;
	}
	/**
	 * @param req_div the req_div to set
	 */
	public void setReq_div(String req_div) {
		this.req_div = req_div;
	}
	/* (non-Javadoc)
	 @see net.solufine.rest.api.service.DefaultVO#toString()
	*/
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("reg_date :").append(reg_date).append(", ");
		sb.append("req_date :").append(req_date).append(", ");
		sb.append("from_date :").append(from_date).append(", ");
		sb.append("to_date :").append(to_date).append(", ");
		sb.append("fromTime :").append(fromTime).append(", ");
		sb.append("toTime :").append(toTime).append(", ");
		sb.append("dong_cd :").append(dong_cd).append(", ");
		sb.append("region :").append(region).append(", ");
		sb.append("subregion :").append(subregion).append(", ");
		sb.append("minX :").append(minX).append(", ");
		sb.append("minY :").append(minY).append(", ");
		sb.append("maxX :").append(maxX).append(", ");
		sb.append("maxY :").append(maxY).append(", ");
		sb.append("partition_cnt :").append(partition_cnt).append(", ");
		sb.append("partitionNo :").append(partitionNo).append(", ");
		sb.append("partition_id :").append(partition_id).append(", ");
		sb.append("signal_yn :").append(signal_yn).append(", ");
		sb.append("event_yn :").append(event_yn).append(", ");
		sb.append("weather_yn :").append(weather_yn).append(", ");
		sb.append("route :").append(route).append(", ");
		sb.append("requestType :").append(requestType).append(", ");
		sb.append("sigu_nm :").append(sigu_nm).append(", ");
		sb.append("dong_nm :").append(dong_nm).append(", ");
		sb.append("download :").append(download).append(", ");
		sb.append("signal_id :").append(signal_id).append(", ");
		sb.append("version :").append(version);
		return sb.toString();
	}
}

class PeriodDay{
	private String periodDate = null;
	private String fromTime = null;
	private String toTime = null;
	
	public PeriodDay(String pd, String ft, String tt) {
		this.periodDate = pd;
		this.fromTime = ft;
		this.toTime = tt;
	}
	
	/**
	 * @return the periodDate
	 */
	public String getPeriodDate() {
		return periodDate;
	}

	/**
	 * @param periodDate the periodDate to set
	 */
	public void setPeriodDate(String periodDate) {
		this.periodDate = periodDate;
	}

	/**
	 * @return the fromTime
	 */
	public String getFromTime() {
		return fromTime;
	}

	/**
	 * @param fromTime the fromTime to set
	 */
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	/**
	 * @return the toTime
	 */
	public String getToTime() {
		return toTime;
	}

	/**
	 * @param toTime the toTime to set
	 */
	public void setToTime(String toTime) {
		this.toTime = toTime;
	}
	
	/* (non-Javadoc)
	 @see net.solufine.rest.api.service.DefaultVO#toString()
	*/
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("periodDate :").append(periodDate).append(", ");
		sb.append("fromTime :").append(fromTime).append(", ");
		sb.append("toTime :").append(toTime);
		return sb.toString();
	}
}
