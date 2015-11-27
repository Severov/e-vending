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
       <%@include file="/WEB-INF/pages/left-menu.jsp" %>
        
        
        

    </body>
</html>
