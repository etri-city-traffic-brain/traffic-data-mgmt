<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%
	response.setStatus(200);
%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title></title>

    <link href="css/sb-admin/bootstrap.min.css" rel="stylesheet">
    
<style type="text/css">
.error-template{
	padding: 40px 15px;
	text-align:center;
}

</style>
</head>

<body>

<div class="wrapper">
	<div class="row">
        <div class="col-md-12">
            <div class="error-template">
                <h1>
                     Exception Error</h1>
                <div class="error-details">
                    Failed URL: ${url} <br>
                    Exception: ${exception.message} <br>
                    <c:forEach items="${exception.stackTrace}" var="ste"> ${ste}<br> 
                    </c:forEach>
				</div>
            </div>
        </div>
    </div>
</div>


</body>

</html>
