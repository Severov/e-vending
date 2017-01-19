<%-- 
    Document   : googleMap
    Created on : 29.10.2015, 13:07:29
    Author     : mishka
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="context" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
  <head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
 
	<script src='http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js'></script>
	
	<title>Карта модулей</title>
	<style>
      html, body {
        height: 100%;
        margin: 0;
        padding: 0;
        
      }
      #map {
        height: 100%;
        position: fixed;
  		top: 0;
  		left: 46px;
 		width: calc(100% - 46px);
  		height: 100%;
      }
    </style>
  </head>
  <body>
	<jsp:include page="left-menu.jsp" />
	
    <div id="map"></div>


	<script type="text/javascript" src="${context}/resources/js/googleMap.js"></script>
    </script>
			<script src="https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/markerclusterer.js">
    </script>
    <script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDrhRs_bVpCm4CQaJB29gDRfvSl7MWJ0a0&signed_in=true&callback=initMap"></script>

  </body>
</html>