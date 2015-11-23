<%-- 
    Document   : private_room
    Created on : 29.10.2015, 13:07:29
    Author     : mishka
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css"
              href="<c:url value='resources/css/private_room.css'/>" />



        <!-- The jQuery library is a prerequisite for all jqSuite products -->
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>

        <!-- We support more than 40 localizations -->
        <script type="text/ecmascript" src="resources/js/jq/trirand/i18n/grid.locale-ru.js"></script>
        <!-- This is the Javascript file of jqGrid -->   
        <script type="text/ecmascript" src="resources/js/jq/trirand/jquery.jqGrid.min.js"></script>
        <!-- This is the localization file of the grid controlling messages, labels, etc.
        <!-- A link to a jQuery UI ThemeRoller theme, more than 22 built-in and many more custom -->
        <link rel="stylesheet" type="text/css" media="screen" href="resources/css/jq/jquery-ui.css" />
        <!-- The link to the CSS that the grid needs -->
        <link rel="stylesheet" type="text/css" media="screen" href="resources/css/jq/trirand/ui.jqgrid.css" />   



        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Личный кабинет</title>
    </head>
    <body>            
        <div id="left_menu">
            <img id="home-icon" title="Домой" alt="Home" src="resources/images/room/home.png">
            <img id="chart-icon" title="Графики" alt="Chart" src="resources/images/room/chart.png">
            <a href="room/module"> <img id="modul-icon" title="Модули" alt="Modul" src="resources/images/room/modul.png"> </a>
            <img id="users-icon" title="Пользователи" alt="Users" src="resources/images/room/users.png">
            <a href="food"> <img id="role-icon" title="Права доступа" alt="Role" src="resources/images/room/role.png"> </a>
            <img id="settings-icon" title="Настройки" alt="settings" src="resources/images/room/settings.png">               
        </div>
        
        
        <table id="jqGrid"></table>
        <div id="jqGridPager"></div>
       

        <script type="text/javascript">

            function send() {
                $.ajax({
                    type: 'POST',
                    url: 'private_room/rest',
                    dataType: 'json',
                    data: '${_csrf.parameterName}=${_csrf.token}',
                                async: true,
                                success: function (result) {
                                   // document.getElementById("test").innerHTML = result.username;
                                    //alert(result.username);
                                },
                                error: function (result) {
                                    //document.getElementById("test").innerHTML = result.responseText;
                                    alert('Error');
                                }
                            });
                        }

                        $(document).ready(function () {
                            $("#jqGrid").jqGrid({
                                url: 'http://trirand.com/blog/phpjqgrid/examples/jsonp/getjsonp.php?callback=?&qwery=longorders',
                                mtype: "GET",
                                regional: "ru",
                                datatype: "jsonp",
                                colModel: [
                                    {label: 'id', name: 'OrderID', key: true, width: 75},
                                    {label: 'Customer ID', name: 'CustomerID', width: 150},
                                    {label: 'Order Date', name: 'OrderDate', width: 150},
                                    {label: 'Freight', name: 'Freight', width: 150},
                                    {label: 'Ship Name', name: 'ShipName', width: 150}
                                ],
                                width: 800,
                                viewrecords: true,
                                rowNum: 50,
                                pager: "#jqGridPager"
                            });
                        });

        </script>

    </body>
</html>
