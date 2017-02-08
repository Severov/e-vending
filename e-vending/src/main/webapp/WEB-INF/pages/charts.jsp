<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="context" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
		<link rel="stylesheet" type="text/css" href="${context}/resources/css/main.css" />
		<link rel="stylesheet" type="text/css" href="${context}/resources/css/leftMenu.css" />
		
		<link rel="stylesheet" type="text/css" href="${context}/resources/easyui/themes/icon.css">
		<link rel="stylesheet" type="text/css" href="${context}/resources/easyui/themes/default/easyui.css">

        <!-- The jQuery library is a prerequisite for all jqSuite products -->
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
        <script type="text/javascript" src="${context}/resources/easyui/jquery.easyui.min.js"></script>
        
		<script type="text/javascript" src="${context}/resources/js/privateRoomCharts.js"></script>
		
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="left-menu.jsp" />
	
	<div id="Panel-left"></div>
	
<div id="worcspace">
<input type="button" style="margin-left: 200px;" value="Проверить" onclick="updateCharts()">

<table id="table-data" style="text-align: left; width: 100%; height: 100%;" border="0" cellpadding="2" cellspacing="2">

  <tbody>
    <tr>
      <td style="vertical-align: middle; width: 400px; height: 280px; text-align: center;">
	  <div id="chartHourReceipt" style="min-width: 475px; height: 250px; margin: 0 auto"></div>
      <br></td>
      <td style="vertical-align: middle; width: 400px; text-align: center; height: 280px;">
	  <div id="chart_2" style="min-width: 475px; height: 250px; margin: 0 auto"></div>
      <br></td>
    </tr>
    <tr>
      <td style="vertical-align: middle; height: 280px; width: 400px; text-align: center;">
	  <div id="chart_sale" style="min-width: 475px; height: 250px; margin: 0 auto"></div><br>
      <br>
</td>
      <td style="vertical-align: middle; height: 280px; width: 400px; text-align: center;">
	   <div id="chart_error" style="min-width: 475px; height: 250px; margin: 0 auto"></div><br>
      <br>
</td>
    </tr>

    <tr>
      <td style="vertical-align: middle; height: 280px; width: 400px; text-align: center;">
	  <div id="chart_bond" style="min-width: 475px; height: 250px; margin: 0 auto"></div><br>
      <br>
</td>
      <td style="vertical-align: middle; height: 280px; width: 400px; text-align: center;">
	   <div id="chart_cash_collection" style="min-width: 475px; height: 250px; margin: 0 auto"></div><br>
      <br>
</td>
    </tr>

	<tr>
      <td style="vertical-align: middle; height: 280px; width: 400px; text-align: center;">
	  <div id="chartEstimationCost" style="min-width: 475px; height: 250px; margin: 0 auto"></div><br>
      <br>
</td>
      <td style="vertical-align: middle; height: 280px; width: 400px; text-align: center;">
	   <div id="chart_coin" style="min-width: 475px; height: 250px; margin: 0 auto"></div><br>
      <br>
</td>
    </tr>
<tr>
      <td style="vertical-align: middle; height: 280px; width: 400px; text-align: center;">
	  <div id="chartDayReceipt" style="min-width: 475px; height: 250px; margin: 0 auto"></div><br>
      <br>
</td>
      <td style="vertical-align: middle; height: 280px; width: 400px; text-align: center;">
	   <div id="EMPTYCHART" style="min-width: 475px; height: 250px; margin: 0 auto"></div><br>
      <br>
</td>
    </tr>

  </tbody>
</table>

</div>
</body>
</html>