<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
	isELIgnored="false"%>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="<c:url value='resources/css/main.css'/>" />
<title>e-vending</title>
</head>
<body>
	<div id="register-form" class="register">
		<form:form method="POST" modelAttribute="user">
			<h1>
				<c:if test="${empty user.id}">Регистрация на сайте</c:if>
				<c:if test="${not empty user.id}">Редактирования профиля</c:if>
			</h1>
			<fieldset>
				<c:if test="${not empty error}">
					<div class="error">${error}</div>
				</c:if>

				<form:input path="username" type="text" placeholder="Имя"
					name="username" />
				<form:input path="fullname" type="text" placeholder="Фамилия"
					name="fullname" />
				<form:input path="email" type="email" placeholder="E-mail"
					name="email" />
				<form:input path="password" type="password" placeholder="Пароль"
					name="password" />
				<input type="password" value="${password_confirm}"
					placeholder="Повторите пароль" name="password_confirm" />
				<footer class="clearfix">
					<input class="button" type="submit" value="Ок" name="registerbtn">
					<p>
						<span class="info">?</span> <a href="login">Войти</a>
					</p>
				</footer>
			</fieldset>
		</form:form>
	</div>
</body>
</html>