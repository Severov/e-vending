<%-- 
    Document   : add_module
    Created on : 31.10.2015, 21:47:26
    Author     : mishka
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
           <form:form method="POST"  modelAttribute="user">
				<form:input path="id"/>

				<c:forEach items="${user.order}" var="cell" varStatus="vs">
					<form:input path="order[${vs.index}].count"/>
            	</c:forEach>

					<input class="button" type="submit" value="Ок" name="Okfood">

					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
			</form:form>
        </div>
    </body>
</html>
