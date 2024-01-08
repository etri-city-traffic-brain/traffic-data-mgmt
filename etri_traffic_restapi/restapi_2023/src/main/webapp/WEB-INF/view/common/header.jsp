<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">

</script>
<nav class="navbar navbar-default">
  <div class="container-fluid">
  	<div class="navbar-header">
      <a class="navbar-brand" href="<c:url value='main'/>">
        <img alt="Brand" src="img/ci.png" width="196" height="54" style="position: absolute; top: -2px;">
      </a>
    </div>
    
    <ul class="nav navbar-nav navbar-right" >
    	<c:if test="${fn:contains(pageContext.request.requestURI, 'main') }">
<%--		<li>--%>
<%--    		<button type="button" class="btn btn-default btn-md" onclick="location.href='requesthistory?pageIndex=1'">--%>
<%--    			<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span> --%>
<%--    			요청 이력	--%>
<%--    		</button>--%>
<%--    	</li>    	--%>
	    	<li>
	    		<button type="button" class="btn btn-default btn-md"  data-toggle="modal" data-target="#downloadModal">
	    			<span class="glyphicon glyphicon-download" aria-hidden="true"></span> 
	    			XML 파일다운로드
	    		</button>
	    	</li>
<%--	    	<li>--%>
<%--	    		<button type="button" class="btn btn-default btn-md" data-toggle="modal" data-target="#uploadModal">--%>
<%--	    			<span class="glyphicon glyphicon-upload" aria-hidden="true"></span> --%>
<%--	    			XML 파일업로드--%>
<%--	    		</button>--%>
<%--	    	</li>--%>
	    	<li>
	    		<button type="button" class="btn btn-default btn-md" data-toggle="modal" data-target="#uploadJsonModal">
	    			<span class="glyphicon glyphicon-upload" aria-hidden="true"></span> 
	    			JSON 파일업로드
	    		</button>
	    	</li>
    	</c:if>
    </ul>
    
  </div>
</nav>