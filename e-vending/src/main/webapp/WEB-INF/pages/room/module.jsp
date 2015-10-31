<%-- 
    Document   : add_module
    Created on : 31.10.2015, 21:47:26
    Author     : mishka
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="../resources/css/private_room.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Мои модули</title>
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
        
        <div id="block-module">
            <c:forEach items="${module_list}" var="cell">
                <div id="mmm">${cell.imai} <br> ${cell.uin} <br> ${cell.version} </div>
            </c:forEach>
        </div>
    </body>
</html>
