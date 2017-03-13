/**
 * Содержит описание класа для работы с модулями
 * на страничке charts
 */

var modulObject = null;

function Moduls(conteinerName){
	// имя класса для оформления блока модуля
	var modulClassName = 'Block-Data';
	// цвет выделеного модуля
	var normalStateColor = 'rgba(0, 0, 0, 0.08)';
	// цвет обчного состояния модуля
	var selectedStateColor = 'rgba(140,160,255, 0.5)';
	// цвет модуля с ошибкой
	var errorStateColor = 'rgba(255, 0, 0, 0.35)';
	// выделенный в текущий момент модуль
	var nameSelectedModule = null;
	// содержит все модули добавленные на страничку
	this.moduls = {};
	// div в котором будут создаваться модули
	var container = document.getElementById(conteinerName); 
	
	// добавим новый модуль и вернем его div
	function addModul(uin) {
		new_div = document.createElement('div');
		new_div.className = modulClassName;
		new_div.id = uin;

		new_div.onclick = function() {

			if (nameSelectedModule != null) {
				document.getElementById(nameSelectedModule).style.cssText = '';
			}

			this.style.backgroundColor = selectedStateColor;

			nameSelectedModule = this.id;
			//ToDotoEverything();

		};
		
		container.appendChild(new_div);
		
		return new_div;
	}
	
	// добавляет/обновляет модуль на форму
	this.update = function(moduls){
		for(var i = 0; i< moduls.length; i++){
			var modul = document.getElementById(moduls[i].uin);		
			if(modul == null){
				modul = addModul(moduls[i].uin);
			}
			
			var imageName = 'Ok';
			var timeOFF = getHumenTime(moduls[i].time_off, moduls[i].nowUnixTime);
			if (timeOFF != '0 м' && timeOFF != ''){
				imageName = '0';
			}
			
			modul.innerHTML = '<table style="text-align: left; line-height: normal; width: 180px; height:  80px;" border="0" cellpadding="0" 		cellspacing="2">   <tbody> <tr>  <td style="vertical-align: top; width: 150px;">Модуль: ' +moduls[i].uin+ '<br><div id="' +moduls[i].uin +'-info-trademark" style="font-size: 12px;">Марка: ' +moduls[i].trademark+ '</div><div id="'+moduls[i].uin+'-info-place" style="font-size: 12px;"> Положение:' +moduls[i].place+ '</div><div id="'+moduls[i].uin+'-time-off" style="font-size: 12px;"><span style="font-size:12px;font-weight:norm; color: #804747">Time-off: ' + timeOFF +'</span></div><div id="'+moduls[i].uin+'-max-cash" style="font-size: 12px;"><span style="font-size:14px;font-weight:bold; color: #229022"> Сумма: ' +moduls[i].max_cash+ ' грн.' + '</span></div></td>  <td style="vertical-align: middle; text-align: left; width: 18px;"><img id="' +moduls[i].uin+ '-img"' + ' style="width: 15px; eight: 44px;" alt="" src="../resources/images/room/' + imageName + 'line.png"></td>   </tr>  </tbody> </table>';
		
			if (nameSelectedModule == moduls[i].uin){
				document.getElementById(nameSelectedModule).onclick();
			}
			
			if (nameSelectedModule == null){
				nameSelectedModule = moduls[i].uin;
				document.getElementById(nameSelectedModule).onclick();
			}
		}
	}		
	
	this.getNameSelectedModule = function(){
		return nameSelectedModule;
	}
}



function startr(port){
    port.on(
        'data',
        gotData
    );

    port.send('howdy doody doo!')
}

function gotData(data){
    console.log(data);
} 


function nnn(){
	var serialjs=require('serialport-js');
	serialjs.open(
	    '/dev/ttyUSB1',
	    start,
	    '\n'
	);
	
	startr(serialjs);
}



// *************************************************************************
$(document).ready(function () {
	modulObject = new Moduls('Panel-left');
});

function createChartBond(){
	$.ajax({
  		url: '../private/ws/' + modulObject.getNameSelectedModule() + '/getChartBond',
  		type: 'GET',
  		dataType: 'json',
  		success: function(param){ 	 			
  			var a0 = [];
  			var a1 = [];
  			var a2 = [];
  			var a3 = [];
  			var a4 = [];
  			var a5 = [];
  			var a6 = [];
  			
  			for (var i = 0; i < param.length; i++){
  			    switch (param[i].bond){
  				 	case 100: a0.push( [param[i].time_stamp, 100 ]);                   
  			        break;
  			        case 1: a1.push( [param[i].time_stamp, 1 ]);                   
  			        break;
  			        case 2: a2.push( [param[i].time_stamp, 2 ]); 
  			        break;
  			        case 5: a3.push( [param[i].time_stamp, 5 ]);               
  			        break;
  			        case 10: a4.push( [param[i].time_stamp, 10 ]);                  
  			        break;  
  			        case 20: a5.push( [param[i].time_stamp, 20 ]);                   
  			        break;
  			        case 50: a6.push( [param[i].time_stamp, 50 ]);                  
  			        break;     
  			    }       
  			}
  			
  			var chart_bond = new Highcharts.Chart({
  			     chart: {
  			        renderTo: 'chart_bond',
  			        type: 'scatter',
  			        height: 250,
  			        backgroundColor: 'rgba(255, 255, 255, 0)',
  			        zoomType: 'x'
  			     },
  			    title: {
  			       text: 'Поступление купюр'
  			        },
  			     legend: {
  			             itemDistance: 75
  			        },
  			      xAxis: {
  			              type: 'datetime',
  			              //minRange: 86400000,
  			               // tickPixelInterval: 70
  			        },
  			      yAxis: {
  			              title: {
  			              text: 'грн.'
  			        },
  						  min : 1,	
  			              max : 50,
  						  type:"logarithmic",
  						  tickPositions: [
  			              0,
  			              0.301029995663981,
  			              0.698970004336018,
  			              1,
  			              1.301029995663981,
  			              1.698970004336018,
  			              2
  			              ],
  			              plotLines: [{
  			                value: 0,
  			                width: 10,
  			                color: '#808080'
  			            }]
  			        },
  			        tooltip: {
  			            shared: false,
  			            headerFormat: '<b>{series.name}</b><br>',
  			            pointFormat: '',
  			            valueSuffix: '',
  			            formatter: function() {
  			                return this.series.name + ', ' + Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x);
  			              }
  			        },
  			        exporting: {
  			                enabled: false
  			        },
  			        series: [{
  			            	marker: {
  			                symbol: 'circle',
  			                radius: 4
  			            },
  			            color: '#2EB82E',
  			            name: '01 грн',
  			                data: a1 
  			        },{
  			                marker: {
  			                symbol: 'circle',
  			                radius: 4
  			            },
  			          color: '#FF9900',
  			            name: '02 грн',
  			                data: a2   
  			        },{
  			                marker: {
  			                symbol: 'circle',
  			                radius: 4
  			            },
  			          color: '#3895DB',
  			            name: '05 грн',
  			                data: a3
  			        },{
  			                marker: {
  			                symbol: 'circle',
  			                radius: 4
  			            },
  			          color: '#FF0066',
  			            name: '10 грн',
  			                data: a4   
  			        },{
  			                marker: {
  			                symbol: 'circle',
  			                radius: 4
  			            },
  			          color: '#99850F',
  			            name: '20 грн',
  			                data: a5
  			        },{
  			                marker: {
  			                symbol: 'circle',
  			                radius: 4
  			            },
  			          color: '#9900CC',
  			            name: '50 грн',
  			                data: a6  
  			        },{
  			                marker: {
  			                symbol: 'circle',
  			                radius: 4
  			            },
  			          color: '#1E1812',
  			            name: 'Отказ от купюры',
  			                data: a0  
  			        }]
  			    });
  			
   		},
		error: function() {}
	})
}

function createCollectionChart(){
	$.ajax({
  		url: '../private/ws/' + modulObject.getNameSelectedModule() + '/getCollectionChart',
  		type: 'GET',
  		dataType: 'json',
  		success: function(param){ 	 			
  			plan  = [];
  			fakt  = [];
  			horizont = [];

  			for (var i = 0; i < param.length; i++){
  				plan.push(param[i].plan);
  				fakt.push(param[i].fakt);
  				horizont.push(new Date(param[i].time_stamp));

  			}

  			 var chart_coin = new Highcharts.Chart({
  			 	chart: {
  						renderTo: 'chart_cash_collection',
  			        	height: 250,
  						type: 'column',
  			        	backgroundColor: 'rgba(255, 255, 255, 0)',
  			        	zoomType: 'x'
  			        },
  			         title: {
  			            text: 'Инкасации'

  			        },
  					exporting: {
  			                enabled: false
  			        },
  					xAxis: {
  			              type: 'datetime',
  						  crosshair: true,
  						  categories: horizont,
  						  labels: {
  						  	formatter: function() {
  			              		return Highcharts.dateFormat('%d. %b', this.value);
  			              	}
  						  }
  					},
  			        tooltip: {
  			            shared: true,
  			            formatter: function () {
  			                var s = '<b>' + Highcharts.dateFormat('%A, %b %d, %H:%M:%S', this.x) + '</b>';

  			                $.each(this.points, function () {
  			                    s += '<br/><span style="color:'+this.series.color +'">● </span> '+ this.series.name +'<span style="color:black; font-weight: bold;"> :  '+ this.y + ' грн. </span>';
  			                });

  							return s;
  			    		}
  			        },
  					plotOptions: {
  			            areaspline: {
  			                fillOpacity: 0.3
  			            }
  			        },
  			        series: [{
  						marker: {
  			                symbol: 'circle',
  			                radius: 4
  			            },
  			            name: 'План',
  			            data: plan
  			        },{
  						marker: {
  			                symbol: 'circle',
  			                radius: 4
  			            },
  			            name: 'Факт',
  			            data: fakt
  			        }]
  			    });
  			
   		},
		error: function() {}
	})
}


function updateCharts(){
	$.ajax({
  		url: '../private/ws/table',
  		type: 'GET',
  		dataType: 'json',
  		success: function(param){ 	 			
  			modulObject.update(param.rows);
  			createChartBond();
  			createCollectionChart();
   		},
		error: function() {}
	})
}


