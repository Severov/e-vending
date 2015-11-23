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
        
        	<form:form  method="POST" modelAttribute="Menu">
			<h1>Меню на неделю</h1>
			<fieldset>

				<form:input path="week" type="text" placeholder="Номер недели" name="week" />
           		
           		<form:checkboxes delimiter="" element="li" path="food" items="${Menu.food}" itemValue="id" itemLabel="name" />
           		
           		<input class="button" type="submit" value="Ок" name="Okfood">
            
			</fieldset>
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form:form>
    </body>
</html>
