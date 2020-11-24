<% 
/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project bis.web
 * @file main.jsp
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *	수정일				수정자			수정내용
 * -------------	------		-------------------------------
 * 2018. 4. 18.		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
 
<!DOCTYPE html>   
<html>
	<head>
		<meta charset="UTF-8" /> 
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"> 
		<meta name="author" content="<spring:message code="title.context"/>" />
		<meta name="copyright" content="Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. " />
		
		<title><spring:message code="title.context"/></title>
		<link rel="shortcut icon" type="image/x-icon" href="<c:url value='/img/favicon.ico'/>" />
		
		<link rel='stylesheet' href="<c:url value='/css/bootstrap/bootstrap.css'/>" type="text/css" />
		<link rel='stylesheet' href="<c:url value='/css/bootstrap/bootstrap-theme.css'/>" type="text/css" />
		<link rel='stylesheet' href="<c:url value='/css/bootstrap/bootstrap-datetimepicker.css'/>" type="text/css" />
		<link rel='stylesheet' href="<c:url value='/css/openlayers/ol.css'/>" type="text/css" />
		<link rel='stylesheet' href="<c:url value='/css/layout.css'/>" type="text/css" />
		<link rel='stylesheet' href="<c:url value='/css/style.css'/>" type="text/css" />
		<link rel='stylesheet' href="<c:url value='/css/main.css'/>" type="text/css" />
		
		<script type="text/javascript" src="<c:url value='/js/jquery-3.3.1.min.js'/>"></script>	    
		<script type="text/javascript" src="<c:url value='/js/boostrap/bootstrap.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/js/boostrap/moment-with-locales.min.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/js/boostrap/bootstrap-datetimepicker.min.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/js/openlayers/ol.js'/>"></script>	
		<script type="text/javascript" src="<c:url value='/js/openlayers/Vworld.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/js/main.js'/>"></script>
	     
	</head>
<body>
	<jsp:include page="./common/header.jsp" />
 
	<div id="map">
	
	</div>
	
	<div id="menu" class="leftmenu search-form">
		<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
			<div class="panel panel-primary">
				<div class="panel-heading" role="tab" id="headingOne">
			      <h4 class="panel-title">
			        <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" onclick="javascript:selectAccordion(0)" aria-expanded="true" aria-controls="collapseOne">
			         	 시나리오 (영역)
			        </a>
			      </h4>
			    </div>
			    <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
			      <div class="panel-body">			      	
			      	<form id="ScenarioByCoordinate_form" class="form-inline" action="ScenarioByCoordinate" method="get" enctype="application/">
			      		<input type="hidden" id="include" name="include" value="0">
			      		<div class="panel panel-default">
							<div class="panel-heading">좌표</div>
							<div class="panel-body">
								<div class="form-group">
									<label for="fromDate" class="control-label">시작날짜</label>
									<div class='input-group date' role="date">							
					                    <input type='text' class="form-control input-sm" id="fromDate" name="fromDate"/>
					                    <span class="input-group-addon">
					                        <span class="glyphicon glyphicon-calendar"></span>
					                    </span>		                    
				                	</div> 
								
					                <label for="fromTime" class="control-label">시작시간</label>
					                <div class='input-group date' role="time">
					                    <input type='text' class="form-control input-sm" id="fromTime" name="fromTime" />
					                    <span class="input-group-addon">
					                        <span class="glyphicon glyphicon-time"></span>
					                    </span>
					                </div>
					                
					                <label for="toDate" class="control-label">종료날짜</label>
									<div class='input-group date' role="date">							
					                    <input type='text' class="form-control input-sm" id="toDate" name="toDate" />
					                    <span class="input-group-addon">
					                        <span class="glyphicon glyphicon-calendar"></span>
					                    </span>		                    
				                	</div> 
								
					                <label for="toTime" class="control-label">종료시간</label>
					                <div class='input-group date' role="time">
					                    <input type='text' class="form-control input-sm" id="toTime" name="toTime" />
					                    <span class="input-group-addon">
					                        <span class="glyphicon glyphicon-time"></span>
					                    </span>
					                </div>
					                
					                <div class="form-group">
						                <label for="min_x" class="control-label" style="margin-right: 3px;">MinX</label>
									    <input type="number" class="form-control input-sm" style="width: 90px;" id="minX" name="minX" placeholder="" maxlength="11">
									
									    <label for="min_y" class="control-label" style="margin-right: 3px;">MinY</label>
										<input type="number" class="form-control input-sm" style="width: 90px;" id="minY" name="minY" placeholder="" maxlength="11">
									</div>
									
									<div class="form-group">
										<label for="max_x" class="control-label" >MaxX</label>
										<input type="number" class="form-control input-sm" style="width: 90px;" id="maxX" name="maxX" placeholder="" maxlength="11">
								    		
										<label for="max_y" class="control-label">MaxY</label>
										<input type="number" class="form-control input-sm" style="width: 90px;" id="maxY" name="maxY" placeholder="" maxlength="11">
										<label style="color: red;">영역선택 : Ctrl + 좌측상단 클릭, 우측하단 클릭</label>
									</div>
									
									<div class="form-group">
										<span class="button-checkbox">
									        <button type="button" class="btn input-sm" style="width: 90px;" data-color="primary">신호포함</button>
									        <input type="checkbox" class="hidden" id="signal" name="signal" checked="checked" />
									    </span>		
										
										<input class="btn input-sm btn-primary" type="button" value="요청" onclick="javascript:RequestScenarioByCoordinate();" >
									</div>
						
				                </div>
				        	</div>
		            	</div>
				             
			      	</form>   
			      </div>
				</div>
			</div>
			
			<!-- 2020-11-11	 -->
			<div class="panel panel-primary">
				<div class="panel-heading" role="tab" id="headingFour">
			      <h4 class="panel-title">
			        <a data-toggle="collapse" data-parent="#accordion" href="#collapseFour" onclick="javascript:selectAccordion(4)" aria-expanded="false" aria-controls="collapseFour">
			         	 시나리오 (지역)
			        </a>
			      </h4>
			    </div>
			    <div id="collapseFour" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFour">
			      <div class="panel-body">
			      
			      <form id="ScenarioByRegion_form" name="ScenarioByRegion_form" class="form-inline" action="ScenarioByRegion" method="get" enctype="application/">
			      		<input type="hidden" id="include" name="include" value="0">
			      		<div class="panel panel-default">
							<div class="panel-heading">대 전</div>
							<div class="panel-body">
								<div class="form-group">
									<label for="fromDate" class="control-label">시작날짜</label>
									<div class='input-group date' role="date">							
					                    <input type='text' class="form-control input-sm" id="fromDate" name="fromDate"/>
					                    <span class="input-group-addon">
					                        <span class="glyphicon glyphicon-calendar"></span>
					                    </span>		                    
				                	</div> 
								
					                <label for="fromTime" class="control-label">시작시간</label>
					                <div class='input-group date' role="time">
					                    <input type='text' class="form-control input-sm" id="fromTime" name="fromTime"/>
					                    <span class="input-group-addon">
					                        <span class="glyphicon glyphicon-time"></span>
					                    </span>
					                </div>
					                
					                <label for="toDate" class="control-label">종료날짜</label>
									<div class='input-group date' role="date">							
					                    <input type='text' class="form-control input-sm" id="toDate" name="toDate"/>
					                    <span class="input-group-addon">
					                        <span class="glyphicon glyphicon-calendar"></span>
					                    </span>		                    
				                	</div> 
								
					                <label for="toTime" class="control-label">종료시간</label>
					                <div class='input-group date' role="time">
					                    <input type='text' class="form-control input-sm" id="toTime" name="toTime" />
					                    <span class="input-group-addon">
					                        <span class="glyphicon glyphicon-time"></span>
					                    </span>
					                </div>
					                
					                <label for="region" class="control-label">구명</label>
									<select id="region" name="region" role="region-select" class="form-control input-sm" style="width: 80px;" data-target="region" 
												data-sub="scenario-subregion-select" data-part="scenario-partitions">
										<option value="250" selected="selected" data-tokens="대전광역시" >대전광역시</option> 
										<c:forEach items="${sigu}" step="1" var="item" varStatus="status" >
											<c:if test="${item.sigu_cd == '25010' or item.sigu_cd == '25020' or item.sigu_cd == '25030' or item.sigu_cd == '25040' or item.sigu_cd == '25050'}">									
											<option value="${item.sigu_cd}" data-tokens="${item.sigu_nm}" >${item.sigu_nm}</option>
											</c:if> 
										</c:forEach>						
									</select> 
									
									<label for="subregion" class="control-label">동명</label>
									<select id="subregion" name="subregion" role="scenario-subregion-select"  class="form-control input-sm" style="width: 80px;" data-target="subregion">
										<option value="0" selected="selected" data-tokens="전체" >전체</option> 
										<c:forEach items="${dong}" step="1" var="item" varStatus="status" >	
											<option value="${item.dong_cd}" data-tokens="${item.dong_nm}" >${item.dong_nm}</option>
										</c:forEach>	
									</select> 
									
									<div class="form-group">
										<label for="partitions" class="control-label">파티션 개수</label>		
										<select id="partitions" name="partitions" role="scenario-partitions" class="form-control input-sm" style="width: 60px;" data-region="">
											<c:forEach items="${partition_cnt_list}" step="1" var="item" varStatus="status" >									
												<option value="${item}" <c:if test="${item == '1'}">selected </c:if> >${item}</option> 
											</c:forEach>
										</select>	
								
										<span class="button-checkbox">
									        <button type="button" class="btn input-sm" style="width: 90px;" data-color="primary">신호포함</button>
									        <input type="checkbox" class="hidden" id="signal" name="signal" checked="checked" />
									    </span>		
										
										<input class="btn input-sm btn-primary" type="button" value="요청" onclick="javascript:RequestScenarioByRegion();" >
									</div>
						
				                </div>
				        	</div>
		            	</div>
				             
			      	</form> 
			      
			      	<form id="ScenarioByRegion2_form" name="ScenarioByRegion2_form" class="form-inline" action="ScenarioByRegion2" method="get" enctype="application/">
			      		<input type="hidden" id="include" name="include" value="0">
			      		<div class="panel panel-default">
							<div class="panel-heading">세 종</div>
							<div class="panel-body">
								<div class="form-group">
									<label for="fromDate" class="control-label">시작날짜</label>
									<div class='input-group date' role="date">							
					                    <input type='text' class="form-control input-sm" id="fromDate" name="fromDate"/>
					                    <span class="input-group-addon">
					                        <span class="glyphicon glyphicon-calendar"></span>
					                    </span>		                    
				                	</div> 
								
					                <label for="fromTime" class="control-label">시작시간</label>
					                <div class='input-group date' role="time">
					                    <input type='text' class="form-control input-sm" id="fromTime" name="fromTime"/>
					                    <span class="input-group-addon">
					                        <span class="glyphicon glyphicon-time"></span>
					                    </span>
					                </div>
					                
					                <label for="toDate" class="control-label">종료날짜</label>
									<div class='input-group date' role="date">							
					                    <input type='text' class="form-control input-sm" id="toDate" name="toDate"/>
					                    <span class="input-group-addon">
					                        <span class="glyphicon glyphicon-calendar"></span>
					                    </span>		                    
				                	</div> 
								
					                <label for="toTime" class="control-label">종료시간</label>
					                <div class='input-group date' role="time">
					                    <input type='text' class="form-control input-sm" id="toTime" name="toTime" />
					                    <span class="input-group-addon">
					                        <span class="glyphicon glyphicon-time"></span>
					                    </span>
					                </div>
					                
					                <label for="region" class="control-label">구명</label>
									<select id="region" name="region" role="region-select" class="form-control input-sm" style="width: 80px;" data-target="region" 
												data-sub="scenario-subregion-select" data-part="scenario-partitions">
										<option value="290" selected="selected" data-tokens="세종특별자치시" >세종특별자치시</option> 
										<c:forEach items="${sigu}" step="1" var="item" varStatus="status" >
											<c:if test="${item.sigu_cd == '29010'}">
											<option value="${item.sigu_cd}" data-tokens="${item.sigu_nm}" >${item.sigu_nm}</option> 
											</c:if>
										</c:forEach>						
									</select> 
									
									<label for="subregion" class="control-label">동명</label>
									<select id="subregion" name="subregion" role="scenario-subregion-select"  class="form-control input-sm" style="width: 80px;" data-target="subregion">
										<option value="0" selected="selected" data-tokens="전체" >전체</option> 
										<c:forEach items="${dong}" step="1" var="item" varStatus="status" >	
											<option value="${item.dong_cd}" data-tokens="${item.dong_nm}" >${item.dong_nm}</option>
										</c:forEach>	
									</select> 
									
									<div class="form-group">
										<label for="partitions" class="control-label">파티션 개수</label>		
										<select id="partitions" name="partitions" role="scenario-partitions" class="form-control input-sm" style="width: 60px;" data-region="">
											<c:forEach items="${partition_cnt_list}" step="1" var="item" varStatus="status" >									
												<option value="${item}" <c:if test="${item == '1'}">selected </c:if> >${item}</option> 
											</c:forEach>
										</select>	
								
										<span class="button-checkbox">
									        <button type="button" class="btn input-sm" style="width: 90px;" data-color="primary">신호포함</button>
									        <input type="checkbox" class="hidden" id="signal" name="signal" checked="checked" />
									    </span>		
										
										<input class="btn input-sm btn-primary" type="button" value="요청" onclick="javascript:RequestScenarioByRegion2();" >
									</div>
						
				                </div>
				        	</div>
		            	</div>				             
			      	</form> 
			      </div>
				</div>
			</div>		
			
			
			<!-- 2020-11-20	 -->
			<div class="panel panel-primary">
		    <div class="panel-heading" role="tab" id="headingTwo">
		      <h4 class="panel-title">
		        <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" onclick="javascript:selectAccordion(1)" aria-expanded="false" aria-controls="collapseTwo">
		          		지도 (영역)
		        </a>
		      </h4>
		    </div>
		    <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
		      <div class="panel-body">
			      	<form id="MapByCoordinate_form" class="form-inline" action="MapByCoordinate" method="get" enctype="application/">
			      		<input type="hidden" id="include" name="include" value="1">
			      		<div class="panel panel-default">
							<div class="panel-heading">좌표</div>
							<div class="panel-body">
								<div class="form-group">
					                <label for="min_x" class="control-label" style="margin-right: 3px;">MinX</label>
								    <input type="number" class="form-control input-sm" style="width: 90px;" id="minX" name="minX" placeholder="" maxlength="11">
								
								    <label for="min_y" class="control-label" style="margin-right: 3px;">MinY</label>
									<input type="number" class="form-control input-sm" style="width: 90px;" id="minY" name="minY" placeholder="" maxlength="11">
								</div>
								
								<div class="form-group">
									<label for="max_x" class="control-label" >MaxX</label>
									<input type="number" class="form-control input-sm" style="width: 90px;" id="maxX" name="maxX" placeholder="" maxlength="11">
							    		
									<label for="max_y" class="control-label">MaxY</label>
									<input type="number" class="form-control input-sm" style="width: 90px;" id="maxY" name="maxY" placeholder="" maxlength="11">
									<label style="color: red;">영역선택 : Ctrl + 좌측상단 클릭, 우측하단 클릭</label>
								</div>
									
								<div class="form-group">									
									<input class="btn input-sm btn-primary" type="button" value="요청" onclick="javascript:RequestMapByCoordinate();" >
				                </div>
				        	</div>
		            	</div>
				             
			      	</form>     
		      </div>
		    </div>
		  </div>
		
		  <div class="panel panel-primary">
		    <div class="panel-heading" role="tab" id="headingFive">
		      <h4 class="panel-title">
		        <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseFive" onclick="javascript:selectAccordion(2)" aria-expanded="false" aria-controls="collapseFive">
		          		지도 (지역)
		        </a>
		      </h4>
		    </div>
		    <div id="collapseFive" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFive">
		      <div class="panel-body">
		      	<form id="MapByRegion_form" name="MapByRegion_form" class="form-inline" action="MapByRegion" method="get" enctype="application/">
		      		<input type="hidden" id="include" name="include" value="1">
			      		<div class="panel panel-default">
							<div class="panel-heading">대 전</div>
							<div class="panel-body">
								<div class="form-group">									
					                <label for="region" class="control-label">구명</label>
									<select id="region" name="region" role="region-select" class="form-control input-sm" style="width: 80px;" data-target="region" 
												data-sub="map-subregion-select" data-part="map-partitions">
										<option value="250" selected="selected" data-tokens="대전광역시" >대전광역시</option> 
										<c:forEach items="${sigu}" step="1" var="item" varStatus="status" >				
											<c:if test="${item.sigu_cd == '25010' or item.sigu_cd == '25020' or item.sigu_cd == '25030' or item.sigu_cd == '25040' or item.sigu_cd == '25050'}">					
											<option value="${item.sigu_cd}" data-tokens="${item.sigu_nm}" >${item.sigu_nm}</option>
											</c:if> 
										</c:forEach>						
									</select> 
									
									<label for="subregion" class="control-label">동명</label>
									<select id="subregion" name="subregion" role="map-subregion-select"  class="form-control input-sm" style="width: 80px;" data-target="subregion">
										<option value="0" selected="selected" data-tokens="전체" >전체</option> 
										<c:forEach items="${dong}" step="1" var="item" varStatus="status" >	
											<option value="${item.dong_cd}" data-tokens="${item.dong_nm}" >${item.dong_nm}</option>
										</c:forEach>	
									</select> 
									
									<div class="form-group">
										<label for="partitions" class="control-label">파티션 개수</label>		
										<select id="partitions" name="partitions" role="map-partitions"  class="form-control input-sm" style="width: 60px;" data-region="">
											<c:forEach items="${partition_cnt_list}" step="1" var="item" varStatus="status" >									
												<option value="${item}" <c:if test="${item == '1'}">selected </c:if> >${item}</option> 
											</c:forEach>
										</select>	
								
										<input class="btn input-sm btn-primary" type="button" value="요청" onclick="javascript:RequestMapByRegion();" >
									</div>
						
				                </div>
				        	</div>
		            	</div>
				             
			      	</form> 
			      	<form id="MapByRegion2_form" name="MapByRegion2_form" class="form-inline" action="MapByRegion2" method="get" enctype="application/">
		      		<input type="hidden" id="include" name="include" value="1">
			      		<div class="panel panel-default">
							<div class="panel-heading">세 종</div>
							<div class="panel-body">
								<div class="form-group">									
					                <label for="region" class="control-label">구명</label>
									<select id="region" name="region" role="region-select" class="form-control input-sm" style="width: 80px;" data-target="region" 
												data-sub="map-subregion-select" data-part="map-partitions">
										<option value="290" selected="selected" data-tokens="세종특별자치시" >세종특별자치시</option> 
										<c:forEach items="${sigu}" step="1" var="item" varStatus="status" >			
											<c:if test="${item.sigu_cd == '29010'}">						
											<option value="${item.sigu_cd}" data-tokens="${item.sigu_nm}" >${item.sigu_nm}</option>
											</c:if> 
										</c:forEach>						
									</select> 
									
									<label for="subregion" class="control-label">동명</label>
									<select id="subregion" name="subregion" role="map-subregion-select"  class="form-control input-sm" style="width: 80px;" data-target="subregion">
										<option value="0" selected="selected" data-tokens="전체" >전체</option> 
										<c:forEach items="${dong}" step="1" var="item" varStatus="status" >	
											<option value="${item.dong_cd}" data-tokens="${item.dong_nm}" >${item.dong_nm}</option>
										</c:forEach>	
									</select> 
									
									<div class="form-group">
										<label for="partitions" class="control-label">파티션 개수</label>		
										<select id="partitions" name="partitions" role="map-partitions"  class="form-control input-sm" style="width: 60px;" data-region="">
											<c:forEach items="${partition_cnt_list}" step="1" var="item" varStatus="status" >									
												<option value="${item}" <c:if test="${item == '1'}">selected </c:if> >${item}</option> 
											</c:forEach>
										</select>	
								
										<input class="btn input-sm btn-primary" type="button" value="요청" onclick="javascript:RequestMapByRegion();" >
									</div>
						
				                </div>
				        	</div>
		            	</div>
			      	</form> 
		      </div>
		    </div>
		  </div>
		  
		  <div class="panel panel-primary">
		    <div class="panel-heading" role="tab" id="headingThree">
		      <h4 class="panel-title">
		        <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" onclick="javascript:selectAccordion(3)" aria-expanded="false" aria-controls="collapseThree">
		          		신호 (영역)
		        </a>
		      </h4>
		    </div>
		    <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
		      <div class="panel-body">			      	
			     			      	
			      	<form id="SignalByCoordinate_form" class="form-inline" action="SignalByCoordinate" method="get" enctype="application/">
			      		<input type="hidden" id="include" name="include" value="2">
			      		<div class="panel panel-default">
							<div class="panel-heading">좌표</div>
							<div class="panel-body">
								<div class="form-group">
									<label for="fromDate" class="control-label">시작날짜</label>
									<div class='input-group date' role="date">							
					                    <input type='text' class="form-control input-sm" id="fromDate" name="fromDate"/>
					                    <span class="input-group-addon">
					                        <span class="glyphicon glyphicon-calendar"></span>
					                    </span>		                    
				                	</div> 
								
					                <label for="fromTime" class="control-label">시작시간</label>
					                <div class='input-group date' role="time">
					                    <input type='text' class="form-control input-sm" id="fromTime" name="fromTime" />
					                    <span class="input-group-addon">
					                        <span class="glyphicon glyphicon-time"></span>
					                    </span>
					                </div>
					                
					                <label for="toDate" class="control-label">종료날짜</label>
									<div class='input-group date' role="date">							
					                    <input type='text' class="form-control input-sm" id="toDate" name="toDate" />
					                    <span class="input-group-addon">
					                        <span class="glyphicon glyphicon-calendar"></span>
					                    </span>		                    
				                	</div> 
								
					                <label for="toTime" class="control-label">종료시간</label>
					                <div class='input-group date' role="time">
					                    <input type='text' class="form-control input-sm" id="toTime" name="toTime" />
					                    <span class="input-group-addon">
					                        <span class="glyphicon glyphicon-time"></span>
					                    </span>
					                </div>
					                
					                <div class="form-group">
						                <label for="min_x" class="control-label" style="margin-right: 3px;">MinX</label>
									    <input type="number" class="form-control input-sm" style="width: 90px;" id="minX" name="minX" placeholder="" maxlength="11">
									
									    <label for="min_y" class="control-label" style="margin-right: 3px;">MinY</label>
										<input type="number" class="form-control input-sm" style="width: 90px;" id="minY" name="minY" placeholder="" maxlength="11">
									</div>
									
									<div class="form-group">
										<label for="max_x" class="control-label" >MaxX</label>
										<input type="number" class="form-control input-sm" style="width: 90px;" id="maxX" name="maxX" placeholder="" maxlength="11">
								    		
										<label for="max_y" class="control-label">MaxY</label>
										<input type="number" class="form-control input-sm" style="width: 90px;" id="maxY" name="maxY" placeholder="" maxlength="11">
										<label style="color: red;">영역선택 : Ctrl + 좌측상단 클릭, 우측하단 클릭</label>
									</div>
									
									<div class="form-group">										
										<input class="btn input-sm btn-primary" type="button" value="요청" onclick="javascript:RequestSignalByCoordinate();" >
									</div>
						
				                </div>
				        	</div>
		            	</div>
				             
			      	</form>     
		      </div>
		    </div>
		  </div>
			
		  <div class="panel panel-primary">
		    <div class="panel-heading" role="tab" id="headingSix">
		      <h4 class="panel-title">
		        <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseSix" onclick="javascript:selectAccordion(5)" aria-expanded="false" aria-controls="collapseSix">
		          		신호 (지역)
		        </a>
		      </h4>
		    </div>
		    <div id="collapseSix" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingSix">
		      <div class="panel-body">
		      	<form id="SignalByRegion_form" class="form-inline" action="SignalByRegion" method="get" enctype="application/">
		      		<input type="hidden" id="include" name="include" value="2">
			      		<div class="panel panel-default">
							<div class="panel-heading">대 전</div>
							<div class="panel-body">
								<div class="form-group">
									<label for="fromDate" class="control-label">시작날짜</label>
									<div class='input-group date' role="date">							
					                    <input type='text' class="form-control input-sm" id="fromDate" name="fromDate"/>
					                    <span class="input-group-addon">
					                        <span class="glyphicon glyphicon-calendar"></span>
					                    </span>		                    
				                	</div> 
								
					                <label for="fromTime" class="control-label">시작시간</label>
					                <div class='input-group date' role="time">
					                    <input type='text' class="form-control input-sm" id="fromTime" name="fromTime" />
					                    <span class="input-group-addon">
					                        <span class="glyphicon glyphicon-time"></span>
					                    </span>
					                </div>
					                
					                <label for="toDate" class="control-label">종료날짜</label>
									<div class='input-group date' role="date">							
					                    <input type='text' class="form-control input-sm" id="toDate" name="toDate" />
					                    <span class="input-group-addon">
					                        <span class="glyphicon glyphicon-calendar"></span>
					                    </span>		                    
				                	</div> 
								
					                <label for="toTime" class="control-label">종료시간</label>
					                <div class='input-group date' role="time">
					                    <input type='text' class="form-control input-sm" id="toTime" name="toTime" />
					                    <span class="input-group-addon">
					                        <span class="glyphicon glyphicon-time"></span>
					                    </span>
					                </div>
					                
									<div class="form-group">
										<label for="region" class="control-label">구명</label>
									<select id="region" name="region" role="region-select" class="form-control input-sm" style="width: 80px;" data-target="region">
										<option value="250" selected="selected" data-tokens="대전광역시" >대전광역시</option> 
										<c:forEach items="${sigu}" step="1" var="item" varStatus="status" >					
											<c:if test="${item.sigu_cd == '25010' or item.sigu_cd == '25020' or item.sigu_cd == '25030' or item.sigu_cd == '25040' or item.sigu_cd == '25050'}">				
											<option value="${item.sigu_cd}" data-tokens="${item.sigu_nm}" >${item.sigu_nm}</option>
											</c:if> 
										</c:forEach>						
									</select> 
										
										<input class="btn input-sm btn-primary" type="button" value="요청" onclick="javascript:RequestSignalByRegion();" >
									</div>
						
				                </div>
				        	</div>
		            	</div>
				             
			      	</form> 
			      	
			      	<form id="SignalByRegion2_form" class="form-inline" action="SignalByRegion2" method="get" enctype="application/">
		      		<input type="hidden" id="include" name="include" value="2">
			      		<div class="panel panel-default">
							<div class="panel-heading">세 종</div>
							<div class="panel-body">
								<div class="form-group">
									<label for="fromDate" class="control-label">시작날짜</label>
									<div class='input-group date' role="date">							
					                    <input type='text' class="form-control input-sm" id="fromDate" name="fromDate"/>
					                    <span class="input-group-addon">
					                        <span class="glyphicon glyphicon-calendar"></span>
					                    </span>		                    
				                	</div> 
								
					                <label for="fromTime" class="control-label">시작시간</label>
					                <div class='input-group date' role="time">
					                    <input type='text' class="form-control input-sm" id="fromTime" name="fromTime" />
					                    <span class="input-group-addon">
					                        <span class="glyphicon glyphicon-time"></span>
					                    </span>
					                </div>
					                
					                <label for="toDate" class="control-label">종료날짜</label>
									<div class='input-group date' role="date">							
					                    <input type='text' class="form-control input-sm" id="toDate" name="toDate" />
					                    <span class="input-group-addon">
					                        <span class="glyphicon glyphicon-calendar"></span>
					                    </span>		                    
				                	</div> 
								
					                <label for="toTime" class="control-label">종료시간</label>
					                <div class='input-group date' role="time">
					                    <input type='text' class="form-control input-sm" id="toTime" name="toTime" />
					                    <span class="input-group-addon">
					                        <span class="glyphicon glyphicon-time"></span>
					                    </span>
					                </div>
					                
									<div class="form-group">
										<label for="region" class="control-label">구명</label>
									<select id="region" name="region" role="region-select" class="form-control input-sm" style="width: 80px;" data-target="region">
										<option value="290" selected="selected" data-tokens="세종특별자치시" >세종특별자치시</option> 
										<c:forEach items="${sigu}" step="1" var="item" varStatus="status" >	
											<c:if test="${item.sigu_cd == '29010'}">								
											<option value="${item.sigu_cd}" data-tokens="${item.sigu_nm}" >${item.sigu_nm}</option>
											</c:if> 
										</c:forEach>						
									</select> 
										
										<input class="btn input-sm btn-primary" type="button" value="요청" onclick="javascript:RequestSignalByRegion2();" >
									</div>
						
				                </div>
				        	</div>
		            	</div>
				             
			      	</form> 
			      	 
		      </div>
		    </div>
		  </div>	
		
			
		</div>	
	</div>
	
	
	<!-- The Modal -->
    <div id="downloadModal" class="modal">
 
      <!-- Modal content -->
      <div class="modal-content"> 
      	<p style="text-align: center;">
      		<span style="font-size: 14pt;"><b><span style="font-size: 24pt;">XML Download</span></b></span>
      	</p>
        <p>
        	<a href="downNodeXml" class="btn btn-primary btn-rounded btn-sm my-0" target="_blank">DownLoad Node XML</a>	
			<a href="downEdgeXml" class="btn btn-primary btn-rounded btn-sm my-0" target="_blank">DownLoad Edge XML</a>        			
        	<a href="downConXml" class="btn btn-primary btn-rounded btn-sm my-0" target="_blank">DownLoad Connection XML</a>  
        	<a href="downTlXml" class="btn btn-primary btn-rounded btn-sm my-0" target="_blank">DownLoad TlLogic XML</a>  		
        				
         <div style="cursor:pointer;background-color:#DDDDDD;text-align: center;padding-bottom: 10px;padding-top: 10px;" onClick="close_pop();">
             <span class="pop_bt" style="font-size: 13pt;" > 닫기 </span>
         </div>
      </div>
 
    </div>
	
	<!-- The Modal -->
    <div id="uploadModal" class="modal">
 
      <!-- Modal content -->
      <div class="modal-content"> 
      	<p style="text-align: center;">
      		<span style="font-size: 14pt;"><b><span style="font-size: 24pt;">XML Upload</span></b></span>
      	</p>
        <p>
        	<div class="row" style="margin-bottom: 12px;">
        		<label for="nodeFile" class="col-sm-3 control-label">Node</label>
  				<div class="col-sm-7" style="margin: 0; padding: 0;">
		        	<div class="input-group" style="width: 100%;">
						<input id="nodeFile" name="nodeFile" type="file" role="upload-file" data-target="nodeFilename" data-class-button="btn btn-default" data-class-input="form-control" class="form-control" tabindex="-1" style="position: absolute; clip: rect(0px 0px 0px 0px);">
						<div class="bootstrap-filestyle input-group">
							<input type="text" id="nodeFilename" name="nodeFilename" class="form-control" disabled placeholder="Choose Node Xml file">
							<span class="group-span-filestyle input-group-btn" >
								<label for="nodeFile" class="btn btn-default ">
									<span class="glyphicon glyphicon-paperclip"></span>
								</label>
							</span>						
						</div> 						
					</div>
				</div>
				<input class="btn btn-primary" type="button" value="Send" id="nodeUpload" role="upload" data-target="nodeFile" data-url="upNodeXml" onclick="UploadXml(this);">
			</div>
				
			<div class="row" style="margin-bottom: 12px;">
        		<label for="nodeFile" class="col-sm-3 control-label">Edge</label>
  				<div class="col-sm-7" style="margin: 0; padding: 0;">
		        	<div class="input-group" style="width: 100%;">
						<input id="edgeFile" name="edgeFile" type="file" role="upload-file" data-target="edgeFilename" data-class-button="btn btn-default" data-class-input="form-control" class="form-control" tabindex="-1" style="position: absolute; clip: rect(0px 0px 0px 0px);">
						<div class="bootstrap-filestyle input-group">
							<input type="text" id="edgeFilename" name="edgeFilename" class="form-control" disabled placeholder="Choose Edge Xml file">
							<span class="group-span-filestyle input-group-btn" >
								<label for="edgeFile" class="btn btn-default ">
									<span class="glyphicon glyphicon-paperclip"></span>
								</label>
							</span>						
						</div> 						
					</div>
				</div>
				<input class="btn btn-primary" type="button" value="Send" id="edgeUpload" role="upload" data-target="edgeFile" data-url="upEdgeXml" onclick="UploadXml(this);">
			</div>
				
			<div class="row" style="margin-bottom: 12px;">
        		<label for="connectionFile" class="col-sm-3 control-label">Connection</label>
  				<div class="col-sm-7" style="margin: 0; padding: 0;">
		        	<div class="input-group" style="width: 100%;">
						<input id="connectionFile" name="connectionFile" role="upload-file" data-target="connectionFilename" type="file" data-class-button="btn btn-default" data-class-input="form-control" class="form-control" tabindex="-1" style="position: absolute; clip: rect(0px 0px 0px 0px);">
						<div class="bootstrap-filestyle input-group">
							<input type="text" id="connectionFilename" name="connectionFilename" class="form-control" disabled placeholder="Choose Connection Xml file">
							<span class="group-span-filestyle input-group-btn" >
								<label for="connectionFile" class="btn btn-default ">
									<span class="glyphicon glyphicon-paperclip"></span>
								</label>
							</span>						
						</div> 						
					</div>
				</div>
				<input class="btn btn-primary" type="button" value="Send" id="connectionUpload" role="upload" data-target="connectionFile" data-url="upConXml" onclick="UploadXml(this);">
			</div>
					
			<div class="row" style="margin-bottom: 12px;">
        		<label for="tllogicFile" class="col-sm-3 control-label">TlLogic</label>
  				<div class="col-sm-7" style="margin: 0; padding: 0;">
		        	<div class="input-group" style="width: 100%;">
						<input id="tllogicFile" name="tllogicFile" type="file" role="upload-file" data-target="tllogicFilename" data-target="" data-class-button="btn btn-default" data-class-input="form-control" class="form-control" tabindex="-1" style="position: absolute; clip: rect(0px 0px 0px 0px);">
						<div class="bootstrap-filestyle input-group">
							<input type="text" id="tllogicFilename" name="tllogicFilename" class="form-control" disabled placeholder="Choose TlLogic Xml file">
							<span class="group-span-filestyle input-group-btn" >
								<label for="tllogicFile" class="btn btn-default ">
									<span class="glyphicon glyphicon-paperclip"></span>
								</label>
							</span>						
						</div> 						
					</div>
				</div>
				<input class="btn btn-primary" type="button" value="Send" id="tllogicFileUpload" role="upload" data-target="tllogicFile" data-url="upTlXml" onclick="UploadXml(this);">
			</div>
			
			<div class="row" style="margin-bottom: 12px;">
        		<label for="tllogicFile" class="col-sm-3 control-label">Signal Excel</label>
  				<div class="col-sm-7" style="margin: 0; padding: 0;">
		        	<div class="input-group" style="width: 100%;">
						<input id="signalFile" name="signalFile" type="file" role="upload-file" data-target="signalFilename" data-target="" data-class-button="btn btn-default" data-class-input="form-control" class="form-control" tabindex="-1" style="position: absolute; clip: rect(0px 0px 0px 0px);">
						<div class="bootstrap-filestyle input-group">
							<input type="text" id="signalFilename" name="signalFilename" class="form-control" disabled placeholder="Choose Signal Excel file">
							<span class="group-span-filestyle input-group-btn" >
								<label for="signalFile" class="btn btn-default ">
									<span class="glyphicon glyphicon-paperclip"></span>
								</label>
							</span>						
						</div> 						
					</div>
				</div>
				<input class="btn btn-primary" type="button" value="Send" id="signalFileUpload" role="upload" data-target="signalFile" data-url="upLoadSignalExcel" onclick="UploadXml(this);">
			</div>
			
			<div class="row" style="margin-bottom: 12px;">
        		<label for="tllogicFile2" class="col-sm-3 control-label">TlLogic</label>
  				<div class="col-sm-7" style="margin: 0; padding: 0;">
		        	<div class="input-group" style="width: 100%;">
						<input id="tllogicFile2" name="tllogicFile2" type="file" role="upload-file" data-target="tllogicFilename2" data-target="" data-class-button="btn btn-default" data-class-input="form-control" class="form-control" tabindex="-1" style="position: absolute; clip: rect(0px 0px 0px 0px);">
						<div class="bootstrap-filestyle input-group">
							<input type="text" id="tllogicFilename2" name="tllogicFilename2" class="form-control" disabled placeholder="Choose TlLogic Xml file">
							<span class="group-span-filestyle input-group-btn" >
								<label for="tllogicFile2" class="btn btn-default ">
									<span class="glyphicon glyphicon-paperclip"></span>
								</label>
							</span>						
						</div> 						
					</div>
				</div>
				<input class="btn btn-primary" type="button" value="Send" id="tllogicFileUpload2" role="upload" data-target="tllogicFile2" data-url="filterTlXml" onclick="UploadXml2(this);">
			</div>
			
         <div style="cursor:pointer;background-color:#DDDDDD;text-align: center;padding-bottom: 10px;padding-top: 10px;" onClick="close_pop();">
             <span class="pop_bt" style="font-size: 13pt;" > 닫기 </span>
         </div>
      </div>
 
    </div>
	
	<!-- The Modal -->
    <div id="uploadJsonModal" class="modal">
 
      <!-- Modal content -->
      <div class="modal-content"> 
      	<p style="text-align: center;">
      		<span style="font-size: 14pt;"><b><span style="font-size: 24pt;">JSON Upload</span></b></span>
      	</p>
        <p>
        	<div class="row" style="margin-bottom: 12px;">
        		<label for="tllogicFile" class="col-sm-3 control-label">Signal JSON</label>
  				<div class="col-sm-7" style="margin: 0; padding: 0;">
		        	<div class="input-group" style="width: 100%;">
						<input id="signalJsonFile" name="signalJsonFile" type="file" role="upload-file" data-target="signalJsonFilename" data-target="" data-class-button="btn btn-default" data-class-input="form-control" class="form-control" tabindex="-1" style="position: absolute; clip: rect(0px 0px 0px 0px);">
						<div class="bootstrap-filestyle input-group">
							<input type="text" id="signalJsonFilename" name="signalJsonFilename" class="form-control" disabled placeholder="Choose Signal Json file">
							<span class="group-span-filestyle input-group-btn" >
								<label for="signalJsonFile" class="btn btn-default ">
									<span class="glyphicon glyphicon-paperclip"></span>
								</label>
							</span>						
						</div> 						
					</div>
				</div>
				<input class="btn btn-primary" type="button" value="Send" id="signalJsonFileUpload" role="upload" data-target="signalJsonFile" data-url="../updateSignal" onclick="UploadJson(this);">
			</div>
        				
         <div style="cursor:pointer;background-color:#DDDDDD;text-align: center;padding-bottom: 10px;padding-top: 10px;" onClick="close_pop();">
             <span class="pop_bt" style="font-size: 13pt;" > 닫기 </span>
         </div>
      </div>
 
    </div>
    
</body>
</html>