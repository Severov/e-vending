<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="<c:url value='resources/css/main.css'/>" />
<meta charset="utf-8">
<title>e-vending</title>
</head>
<body>
	<div class="loginForm">
		<h1>Авторизация на сайте</h1>
		<div class="loginLogin">

			<form name='loginForm' action="<c:url value='${loginUrl}' />"
				method='POST'>
				<fieldset class="loginLoginFieldset">
					<c:if test="${not empty error}">
						<div class="error">${error}</div>
					</c:if>
					<c:if test="${not empty msg}">
						<div class="msg">${msg}</div>
					</c:if>
					<input class="loginUsername" type="text" name="username"
						placeholder="E-mail" required autofocus> <input
						class="loginPassword" type="password" name="password"
						placeholder="Пароль" required> <span
						class="loginLoginButton"><input type="submit" name="Login"
						value="Ok" /></span>
					<footer class="clearfix">
						<p>
							<span class="info">?</span><a href="[[~8]]">Забыли пароль?</a>
						</p>
						<p>
							<span class="info">!</span><a href="registration">Регистрация</a>
						</p>
					</footer>
				</fieldset>
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</form>
		</div>
	</div>
</body>
</html>
