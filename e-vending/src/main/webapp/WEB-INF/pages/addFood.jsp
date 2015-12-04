<%-- 
    Document   : add_module
    Created on : 31.10.2015, 21:47:26
    Author     : mishka
--%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
	isELIgnored="false"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="../resources/css/private_room.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Меню на неделю</title>
    </head>
    <body>
        <div id="left_menu">
            <img id="home-icon" title="Домой" alt="Home" src="../resources/images/room/home.png">
            <img id="chart-icon" title="Графики" alt="Chart" src="../resources/images/room/chart.png">
            <a href="module"> <img id="modul-icon" title="Модули" alt="Modul" src="../resources/images/room/modul.png"> </a>
            <img id="users-icon" title="Пользователи" alt="Users" src="../resources/images/room/users.png">
            <img id="role-icon" title="Права доступа" alt="Role" src="../resources/images/room/role.png">
            <img id="settings-icon" title="Настройки" alt="settings" src="../resources/images/room/settings.png">               
        </div>
        
        	<form:form  method="POST" modelAttribute="food">
			<h1>
				<c:if test="${empty food.id}">Добавление нового блюда</c:if>
				<c:if test="${not empty food.id}">Редактирование блюда</c:if>
			</h1>
			
			<fieldset>
				<form:input path="name" type="text" placeholder="Наименование" name="name" />
				<form:input path="description" type="text" placeholder="Описание" name="description" />
           		<form:input path="price" type="text" placeholder="Стоимость, грн." name="price" />		
           		<input class="button" type="submit" value="Ок" name="Okfood">         
			</fieldset>
			
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		</form:form>
    </body>
</html>
