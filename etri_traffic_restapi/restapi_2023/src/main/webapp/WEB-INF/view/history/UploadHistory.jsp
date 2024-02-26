<% 
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html> 
<html>
<head>
	<head>
		<meta charset="UTF-8" /> 
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, initial-scale=1"> 
		<meta name="author" content="<spring:message code="title.context"/>" />
		<meta name="copyright" content="Copyright 2018. etri. all rights reserved. " />
		
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
		
	</head>
<body>
	<jsp:include page="../common/header.jsp" />
</body>
</html>