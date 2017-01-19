/**
 * 
 */

var currentInfoWindow = null;

function initMap() {
  var kiev = {lat: 50.448853, lng: 30.513346};
  var map = new google.maps.Map(document.getElementById('map'), {
    zoom: 16,
    center: kiev,
    mapTypeId: google.maps.MapTypeId.ROADMAP
  });

	google.maps.event.addListener(map, 'click', function(event){
  if(currentInfoWindow != null){
    currentInfoWindow.close();
  }
});

	get_current_settings_module(map);
 }


function get_current_settings_module(map){
$.ajax({
  		url: '../private/ws/table',
  		type: 'GET',
  		dataType: 'json',
  		success: function(modul){


	var markers      = [];
	var latlngbounds = new google.maps.LatLngBounds();
	var infowindow   = new google.maps.InfoWindow();

  for (i = 0; i < modul.rows.length; i++) {
   	var beach = modul.rows[i];

	// Если пустые строки
  	if(!beach['lat'] || !beach['lng']){
		continue;
	}

	// Если просто нули
	if(beach['lat'] == 0 || beach['lng'] == 0){
		continue;
	}

	var markerPosition = new google.maps.LatLng(beach['lat'], beach['lng']);

	var contentString = '<div id="content">'+
      '<b>UIN:</b> '+ beach['uin'] +'<br>'+
	  '<b>Состояние:</b> '+ beach['status'] +'<br>'+
	  '<b>Сумма:</b> '+ beach['max_cash'] +'<br>'+
	  '<b>Датчик, °С:</b> '+ beach['temp'] +'<br>'+
      '</div>';


    marker = new google.maps.Marker({
      position: markerPosition,
	  animation: google.maps.Animation.DROP,
      title: beach['place'] + ' (' + beach['uin'] + ')',
	  contentString: contentString
    });


	marker.addListener('click', function() {
		infowindow.setContent(this.contentString);
   		infowindow.open(map, this);
		currentInfoWindow = infowindow;
  	});

  	markers.push(marker);

	// + Для центрирования и масштабирования карты
	latlngbounds.extend(markerPosition);
  }

  var markerCluster = new MarkerClusterer(map, markers,
            {imagePath: 	'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});


	map.setCenter( latlngbounds.getCenter(), map.fitBounds(latlngbounds));


		},
		error: function() {}
})
}