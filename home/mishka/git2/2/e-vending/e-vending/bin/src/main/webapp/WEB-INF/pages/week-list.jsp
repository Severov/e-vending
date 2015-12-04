<%-- 
    Document   : add_module
    Created on : 31.10.2015, 21:47:26
    Author     : mishka
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
    	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>День недели</title>
    </head>
    <body>
        <%@include file="/WEB-INF/pages/left-menu.jsp" %>
        
		<div id="block-module">
        <h1>Выберите день недели</h1>
        <c:forEach items="${weekList}" var="cell">
        	<div id="food-list">
				<a href="${href}?week=${cell.id}">${cell.description}</a>
			</div>
        </c:forEach>
		</div>
    </body>
</html>
