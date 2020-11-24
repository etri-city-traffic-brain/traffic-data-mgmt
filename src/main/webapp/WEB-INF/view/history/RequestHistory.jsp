<% 
/********************************************************************
 * 이프로그램은 (주)솔루파인 소유의 소프트웨어입니다. 
 * 
 * Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. 
 *
 * @project bis.web
 * @file RequestHistory.jsp
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *	수정일				수정자			수정내용
 * -------------	------		-------------------------------
 * 2018. 6. 18.		redkaras	최초작성
 * 
 * </pre>
 ********************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html> 
<html>
<head>
	<head>
		<meta charset="UTF-8" /> 
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, initial-scale=1"> 
		<meta name="author" content="<spring:message code="title.context"/>" />
		<meta name="copyright" content="Copyright 2018. (주)솔루파인 Co., Ltd. all rights reserved. " />
		
		<title><spring:message code="title.context"/></title>
		<link rel="shortcut icon" type="image/x-icon" href="<c:url value='/img/favicon.ico'/>" />
		
		<link rel='stylesheet' href="<c:url value='/css/bootstrap/bootstrap.css'/>" type="text/css" />
		<link rel='stylesheet' href="<c:url value='/css/bootstrap/bootstrap-theme.css'/>" type="text/css" />
		<link rel='stylesheet' href="<c:url value='/css/bootstrap/bootstrap-datetimepicker.css'/>" type="text/css" />
		<link rel='stylesheet' href="<c:url value='/css/layout.css'/>" type="text/css" />
		<link rel='stylesheet' href="<c:url value='/css/style.css'/>" type="text/css" />
		<link rel='stylesheet' href="<c:url value='/css/history.css'/>" type="text/css" />
		
		<script type="text/javascript" src="<c:url value='/js/jquery-3.3.1.min.js'/>"></script>	    
		<script type="text/javascript" src="<c:url value='/js/boostrap/bootstrap.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/js/boostrap/moment-with-locales.min.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/js/boostrap/bootstrap-datetimepicker.min.js'/>"></script>
	     
<script type="text/javascript">

function onDownload(btn){
	var href = $(btn).attr("href");
	if( href.search("include=") == -1 ){
		$(btn).attr("href", href + "&" + "include=" + $("#include").val());	
	}else{
		var pos = href.indexOf("include=");
		var sStr = href.substr(pos, 9); 
		$(btn).attr("href", href.replace(sStr, "include=" + $("#include").val()));
	}	
}

</script>	     
	</head>
<body>
	<jsp:include page="../common/header.jsp" />
	
	<div class="container">	
		<!-- 
		<form id="search_form" class="form-inline" action="" method="post">	
			<div class="form-group">
			    <label for="exampleInputName2">Name</label>
			    <input type="text" class="form-control" id="exampleInputName2" placeholder="Jane Doe">
		  	</div>
		  	
		  	<div class="form-group">
			    <label for="exampleInputEmail2">Email</label>
			    <input type="email" class="form-control" id="exampleInputEmail2" placeholder="jane.doe@example.com">
		  	</div>
		  	<button type="submit" class="btn btn-default">Send invitation</button>
		  	
		  	<div class="input-group">
		      <span class="input-group-addon">
		        <input type="checkbox" aria-label="선택여부">
		      </span>
		      <input type="text" class="form-control" aria-label="...">
		    </div>
    
		</form>
		 -->
		<div class="table-responsive">
			<table class="table table-fixed">
				<caption>XML 요청목록</caption>
				<thead>
					<tr>
						<th scope="col" width="120px">항목</th>
						<th scope="col" width="76px">DownLoad</th>
						<th scope="col" width="46px">번호</th>
						<th scope="col" width="94px">요청유형</th>
						<th scope="col" width="146px">요청일시</th>
						<th scope="col" width="80px">조회일자</th>				
						<th scope="col" width="70px">시작시간</th>
						<th scope="col" width="70px">종료시간</th>
						<th scope="col" width="56px">신호</th>
						<th scope="col" width="56px">돌발</th>
						<th scope="col" width="56px">날씨</th>
						<th scope="col" width="65px">구명</th>
						<th scope="col" width="75px">동명</th>
						<th scope="col" width="80px">Min X</th>
						<th scope="col" width="80px">Min Y</th>
						<th scope="col" width="80px">Max X</th>
						<th scope="col" width="80px">Max Y</th>
						<th scope="col" width="80px">파티션번호</th>
						<th scope="col" width="80px">파티션개수</th>						
						<th scope="col" width="120px">Route</th>
					</tr>
				</thead>	
				<tbody>
					<c:if test="${list.size() == 0}">
						<tr style="height: 100px; background: white; text-align: center;">
							<td colspan="18" style="vertical-align: middle;">데이터가 없습니다.</td>
						</tr>						
					</c:if>
					
					<c:forEach items="${list}" step="1" var="item" varStatus="status" >	
						<fmt:parseDate value="${item.req_date}" var="req_dateFmt" pattern="yyyyMMdd" scope="page"/>
						<fmt:parseDate value="${item.fromTime}" var="fromTimeFmt" pattern="HHmmss" scope="page"/>
						<fmt:parseDate value="${item.toTime}" var="toTimeFmt" pattern="HHmmss" scope="page"/>
						<tr>
							<td>  
								<select id="include" name="include" class="form-control" style="width: 120px;" >
								<c:forEach items="${codes}" step="1" var="code" varStatus="status" >	
									<option value="${code.cd_id}">${code.cd_nm}</option>
								</c:forEach>
								</select>									
							</td>
							<td>
								<a href="${item.download}" class="btn btn-primary btn-rounded btn-sm my-0" onclick="onDownload(this);">DownLoad</a>								
							</td>
							<th scope="row">${item.row_num}</th>
							<td>${item.requestType}</td>
							<td>${item.reg_date}</td>
							<td><fmt:formatDate value="${req_dateFmt}" type="date" pattern="yyyy-MM-dd" /></td>
							<td><fmt:formatDate value="${fromTimeFmt}" type="time" pattern="HH:mm:ss" /></td>
							<td><fmt:formatDate value="${toTimeFmt}" type="time" pattern="HH:mm:ss" /></td>
							<td>${item.signal_yn}</td>
							<td>${item.event_yn}</td>
							<td>${item.weather_yn}</td>
							<td>${item.sigu_nm}</td>
							<td>${item.dong_nm}</td>
							<td>${item.minX}</td>
							<td>${item.minY}</td>
							<td>${item.maxX}</td>
							<td>${item.maxY}</td>
							<td>${item.partitionNo}</td>
							<td>${item.partition_cnt}</td>							
							<td>${item.route}</td>							
						</tr>
					</c:forEach>					
				</tbody>
			</table>
		</div>
		
		<a class="btn btn-default pull-right" style="margin-top: 20px;" href="<c:url value='main'/>">메인으로</a>
		
		<div class="clearfix"></div>
		
		<div class="text-center">
			<nav>
			  <ul class="pagination">
			    <li>
			      <a href="#" aria-label="Previous">
			        <span aria-hidden="true">&laquo;</span>
			      </a>
			    </li>
			    
			    <c:forEach begin="1" end="${req.pageUnit}" step="1" varStatus="status">
					<li>						
						<a href="requesthistory?pageIndex=${status.count}" <c:if test="${req.pageIndex == status.count }">style="font-weight: bold;"</c:if>>${status.count}</a>
					</li>
				</c:forEach>
			    <li>
			      <a href="#" aria-label="Next">
			        <span aria-hidden="true">&raquo;</span>
			      </a>
			    </li>
			  </ul>
			</nav>
		</div>
		
	</div>
</body>
</html>