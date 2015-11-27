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

			<c:if test="${not empty admin and admin}">
				<form:form method="POST" action="week-list" modelAttribute="menu">

					<h2>Меню на ${menu.description}</h2>
					<form:input path="id" type="hidden" />
					<form:input path="description" type="hidden" />
					<form:input path="week" type="hidden" />
					<form:checkboxes path="food" delimiter="" element="li"
						items="${listFood}" itemValue="id" itemLabel="name" />
					<input class="button" type="submit" value="Ок" name="Okfood">

					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</form:form>
			</c:if>

			<c:if test="${empty admin or not admin}">
				<form:form method="POST" action="order-list" modelAttribute="menu">

					<h2>Записаться на ${menu.description}</h2>
					<form:input path="id" type="hidden" />
					<form:input path="description" type="hidden" />
					<form:input path="week" type="hidden" />
					<form:checkboxes path="food" delimiter="" element="li"
						items="${listFood}" itemValue="id" itemLabel="name" />
					<input class="button" type="submit" value="Ок" name="Okfood">

					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</form:form>
			</c:if>

		</div>
	</div>
</body>
</html>