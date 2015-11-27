<%-- 
    Document   : add_module
    Created on : 31.10.2015, 21:47:26
    Author     : mishka
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
    	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css"
              href="<c:url value='resources/css/private_room.css'/>" />
        <title>Меню на неделю</title>
    </head>
    <body>
        <%@include file="/WEB-INF/pages/left-menu.jsp" %>
        
        <div id="block-module">
            <c:forEach items="${menu}" var="cell">
                <div id="food-list"><h2><a href="week-list?week=${cell.id}">${cell.description}</a></h2>
                	<ul>
                		<c:forEach items="${cell.food}" var="cellFood">	           
                 			 <li>${cellFood.name}</li>  
                 		</c:forEach>
                 	</ul>
                 </div>
            </c:forEach>
        </div>
    </body>
</html>
