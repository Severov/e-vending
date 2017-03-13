<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="context" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
		<link rel="stylesheet" type="text/css" href="${context}/resources/css/main.css"/>
		<link rel="stylesheet" type="text/css" href="${context}/resources/css/adaptive.css"/>
		
		<link rel="stylesheet" type="text/css" href="${context}/resources/css/leftMenu.css"/>
		
		<link rel="stylesheet" type="text/css" href="${context}/resources/easyui/themes/icon.css">
		<link rel="stylesheet" type="text/css" href="${context}/resources/easyui/themes/default/easyui.css">

        <!-- The jQuery library is a prerequisite for all jqSuite products -->
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
        <script type="text/javascript" src="${context}/resources/easyui/jquery.easyui.min.js"></script>
        
        <script type="text/javascript" src="${context}/resources/js/general.js"></script>
		<script type="text/javascript" src="${context}/resources/js/privateRoomCharts.js"></script>
		
		<!-- HIGHCHARTS -->
		<script type="text/javascript" src="${context}/resources/highcharts507/code/highcharts.js"></script>
		<script type="text/javascript" src="${context}/resources/highcharts507/code/highcharts-more.js"></script>
		
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="left-menu.jsp" />
	<div id="Panel-left"></div>
	<input type="button" style="margin-left: 200px;" value="Проверить" onclick="updateCharts()">
	
<div class="masonry">
	<div class="dx">
	  <div class="item" id="chart_2"><input type="button" style="margin-left: 200px;" value="Проверить" onclick="nnn()"></div>
	  <div class="item" id="chart_sale"><br><br><br><br><br><br><br><br><br><br><br></div>   
	  <div class="item" id="chart_error"><br><br><br><br><br></div>
	  <div class="item" id="chart_bond"><br><br><br<br><br><br><br><br><br>><br><br><br><br><br><br><br></div>   
	  <div class="item" id="chart_cash_collection"><br><br><br><br><br><br><br><br><br><br></div>
	  <div class="item" id="chartEstimationCost"><br><br><br><br><br></div>
	  <div class="item" id="chart_coin" ><br><br><br><br></div>
	  <div class="item" id="chartDayReceipt"><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br></div>
	</div>	  
</div>
</body>
</html>