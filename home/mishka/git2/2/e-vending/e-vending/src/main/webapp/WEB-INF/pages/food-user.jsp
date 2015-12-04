<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Меню на неделю</title>
</head>
<body>
	<%@include file="/WEB-INF/pages/left-menu.jsp"%>
	<div id="block-module">
		<div id="food-list">


			<form:form method="POST" action="food-list" modelAttribute="orderFood">

				<h2>Меню на ${description}</h2>

				<form:input path="id" type="hidden"/>
				<form:input path="date" type="hidden" />
				<form:input path="user" type="hidden" />
				
				<!-- <form:checkboxes path="order" delimiter="" element="li"
						items="${listFood}" itemValue="id" itemLabel="name" /> -->
						
				<c:forEach items="${orderFood.order}" var="cell" varStatus="vs">

				<li>
					<form:checkbox path="order[${vs.index}].food" value='1'/>
					${cell.food.name}
					<form:input path="order[${vs.index}].count" />							
				</li>
				</c:forEach>

				<input class="button" type="submit" value="Ок" name="Okfood">

				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</form:form>

		</div>
	</div>
</body>
</html>