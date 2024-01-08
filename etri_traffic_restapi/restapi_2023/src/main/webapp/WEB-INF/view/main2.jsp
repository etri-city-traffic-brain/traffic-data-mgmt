<% 
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
		<meta name="copyright" content="Copyright 2018. etri. all rights reserved. " />
		
		<title><spring:message code="title.context"/></title>
		<link rel="shortcut icon" type="image/x-icon" href="<c:url value='/img/favicon.ico'/>" />
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
 
	<div id="map"></div>
	
	<div id="menu" class="leftmenu search-form">
		<form id="search_form" class="form-inline" action="" method="get" enctype="application/">
			<input type="hidden" id="include" name="include" value="0">
			<input type="hidden" id="reqDate" name="reqDate" />
			<input type="hidden" id="fromTime" name="fromTime" />
			<input type="hidden" id="toTime" name="toTime" />
			<input type="hidden" id="signal" name="signal"/>
			<input type="hidden" id="event" name="event"/>
			<input type="hidden" id="weather" name="weather"/>
			<input type="hidden" id="route" name="route" value="0" />
			<input type="hidden" id="region" name="region" value="${sigu_cd}" />		
			<input type="hidden" id="subregion" name="subregion" value="${dong_cd}" />	
										
				<div class="panel panel-primary">
					<div class="panel-heading">시나리오</div>
					<div class="panel-body">
						<div class="form-group">
							<label for="reqDate" class="control-label">조회일자</label>
							<div class='input-group date' role="date" data-target="reqDate">							
			                    <input type='text' class="form-control input-sm" />
			                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>		                    
		                	</div> 
						</div>
						
						<div class="form-group">
			                <label for="fromTime" class="control-label">시작시간</label>
			                <div class='input-group date' role="time" data-target="fromTime">
			                    <input type='text' class="form-control input-sm"  />
			                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-time"></span>
			                    </span>
			                </div>
		                </div>
						
						<div class="form-group">
							<label for="toTime" class="control-label">종료시간</label>
			                <div class='input-group date' role="time" data-target="toTime">
			                    <input type='text' class="form-control input-sm"  />
			                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-time"></span>
			                    </span>
			                </div>
						</div>
						
						<div class="form-group pull-right">
							<span class="button-checkbox">
						        <button type="button" class="btn input-sm" style="width: 90px;" data-color="primary">신호포함</button>
						        <input type="checkbox" class="hidden" id="signal" name="signal" checked="checked" />
						    </span>		
						</div>
						<!-- 
						<div class="form-group">
							<span class="button-checkbox">
						        <button type="button" class="btn input-sm" style="width: 90px;" data-color="primary">Event</button>
						        <input type="checkbox" class="hidden" id="event" name="event"/>
						    </span>	
						</div>
						
						<div class="form-group">
							<span class="button-checkbox">
						        <button type="button" class="btn input-sm" style="width: 90px;" data-color="primary">Weather</button>
						        <input type="checkbox" class="hidden" id="weather" name="weather"/>
						    </span>		
						</div>
							                	
	                	<div class="form-group">
							<label for="route" class="control-label">Route</label>
							<select role="route-select" class="form-control input-sm" style="width: 110px;" data-target="route"> 								
								<option value="0" selected="selected">Not included</option> 
								<option value="1">SKT route</option> 
							</select> 
						</div>
						 -->    
					</div>
				</div>
				
				<div class="panel panel-primary">
					<div class="panel-heading">지도(행정구역)</div>
					<div class="panel-body">
						<div class="form-group">
							<label for="region" class="control-label">구명</label>
							<select role="region-select" class="form-control input-sm" style="width: 80px;" data-target="region" data-sub="scBysubregion">
								<option value="112" data-tokens="강남4구" >강남4구</option> 
								<c:forEach items="${sigu}" step="1" var="item" varStatus="status" >									
									<option value="${item.sigu_cd}" <c:if test="${item.sigu_cd == sigu_cd}">selected </c:if> data-tokens="${item.sigu_nm}" >${item.sigu_nm}</option> 
								</c:forEach>						
							</select> 
						</div>
						
						<div class="form-group">
							<label for="scBysubregion" class="control-label">동명</label>
							<select role="region-select" id="scBysubregion" name="scBysubregion"  class="form-control input-sm" style="width: 80px;" data-target="subregion">
								<option value="" data-tokens="전체" >전체</option> 
								<c:forEach items="${dong}" step="1" var="item" varStatus="status" >	
									<option value="${item.dong_cd}" <c:if test="${item.dong_cd == dong_cd}">selected </c:if> data-tokens="${item.dong_nm}" >${item.dong_nm}</option>
								</c:forEach>	
							</select> 
						</div>
												
						<input class="btn input-sm btn-primary pull-right" type="button" value="요청" role="request" data-url="requestScenearioByRegion" id="RequestScenearioByRegion" data-include="0" onclick="RequestXml(this)">
				    </div>				  	
				</div>
				
				<div class="panel panel-primary">
					<div class="panel-heading">지도(범위)</div>
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
						</div>
						<label style="color: red;">영역선택 : Ctrl + 좌측상단 클릭, 우측하단 클릭</label>						 
						<input class="btn input-sm btn-primary pull-right" type="button" value="요청" role="request" data-url="requestScenearioByCoordinate" id="RequestScenearioByCoordinate" data-include="0" onclick="RequestXml(this)">
					</div>
				</div>
				 
				<div class="panel panel-primary">
					<div class="panel-heading">파티션</div>
					<div class="panel-body">	
						<div class="form-group">
							<label for="region" class="control-label">구명</label>
							<select role="partition-region-select" class="form-control input-sm" style="width: 90px;" data-target="region" data-sub="partitions"> 
								<option value="112" data-tokens="강남4구" >강남4구</option> 
								<c:forEach items="${sigu}" step="1" var="item" varStatus="status" >									
									<option value="${item.sigu_cd}" <c:if test="${item.sigu_cd == sigu_cd}">selected </c:if> data-tokens="${item.sigu_nm}" >${item.sigu_nm}</option> 
								</c:forEach>						
							</select> 
						</div>
						
						<div class="form-group"> 
							<!-- 
							<label for="partitionNo" class="control-label" style="margin-left: 10px;">Partition No</label> -->								
			                <input type="hidden" class="form-control input-sm" style="width: 60px;" id="partitionNo" name="partitionNo" placeholder="" value="0">
			                 
							<label for="partitions" class="control-label">파티션 개수</label>		
							<select id="partitions" name="partitions" role="partitions-select"  class="form-control input-sm" style="width: 60px;">
								<c:forEach items="${partition_cnt_list}" step="1" var="item" varStatus="status" >									
									<option value="${item}" <c:if test="${item == '1'}">selected </c:if> >${item}</option> 
								</c:forEach>
							</select>													
			                <!-- <input type="number" class="form-control input-sm" style="width: 60px;" id="partitions" name="partitions" placeholder="" maxlength="4">  -->
						</div>
						
                		<!-- <input class="btn input-sm btn-primary pull-left" type="button" value="Master Request" role="request" data-url="requestPartitionedScenarioByMaster" id="RequestPartitionedScenarioByMaster" onclick="RequestXml(this)">  -->
                		<input class="btn input-sm btn-primary pull-right" type="button" value="요청" role="request" data-url="requestPartitionedScenarioByWorker" id="RequestPartitionedScenarioByWorker" data-include="8" onclick="RequestXml(this)">                		
                				                
				    </div>				  	
				</div>		
				
				<!-- 
				<div class="panel panel-primary">
					<div class="panel-heading">Standalone</div>
					<div class="panel-body">	
						<div class="form-group">
							<label for="region" class="control-label">구명</label>
							<select role="partition-region-select" class="form-control input-sm" style="width: 90px;" data-target="region" data-sub="partition_cnt"> 
								<c:forEach items="${sigu}" step="1" var="item" varStatus="status" >									
									<option value="${item.sigu_cd}" <c:if test="${item.sigu_cd == sigu_cd}">selected </c:if> data-tokens="${item.sigu_nm}" >${item.sigu_nm}</option> 
								</c:forEach>						
							</select> 
						</div>

						<div class="form-group">
							<label for="partition_cnt" class="control-label">Partition Count</label>
							<select id="partition_cnt" name="partition_cnt" role="partition-cnt-select"  class="form-control input-sm" style="width: 60px;" data-target="partition_cnt">
								<c:forEach items="${partition_cnt_list}" step="1" var="item" varStatus="status" >									
									<option value="${item}" <c:if test="${item == '1'}">selected </c:if> >${item}</option> 
								</c:forEach>
							</select>
						</div>
                		
                		<input class="btn input-sm btn-primary pull-right" type="button" value="Standalone Request" role="request" data-url="getScenarioByRegion" id="GetScenarioByRegion" onclick="RequestXml(this)">                		
                				                
				    </div>				  	
				</div>	
				 -->
				 
				 <div class="panel panel-primary">
					<div class="panel-heading">신호</div>
					<div class="panel-body">	
						<div class="form-group">
							<label for="version" class="control-label">버전</label>
							<select id="version" name="version" role="version-select"  class="form-control input-sm" style="width: 60px;">
								<c:forEach items="${version_list}" step="1" var="item" varStatus="status" >									
									<option value="${item}" <c:if test="${item == '1'}">selected </c:if> >${item}</option> 
								</c:forEach>
							</select>
						</div>
                		
                		<input class="btn input-sm btn-primary pull-right" type="button" value="요청" role="request" data-url="getSignal" id="GetSignal" data-include="9" onclick="RequestXml(this)">                		
                				                
				    </div>				  	
				</div>	
				
				<div class="panel panel-primary">
					<div class="panel-heading">궤적</div>
					<div class="panel-body">
                		
                		<input class="btn input-sm btn-primary pull-right" type="button" value="요청" role="request" data-url="getRoute" id="GetRoute" data-include="7" onclick="RequestXml(this)">                		
                				                
				    </div>				  	
				</div>				 
		</form>
		
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
			
         <div style="cursor:pointer;background-color:#DDDDDD;text-align: center;padding-bottom: 10px;padding-top: 10px;" onClick="close_pop();">
             <span class="pop_bt" style="font-size: 13pt;" > 닫기 </span>
         </div>
      </div>
 
    </div>

</body>
</html>