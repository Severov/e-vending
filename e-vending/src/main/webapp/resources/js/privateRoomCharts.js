/**
 * Содержит описание класа для работы с модулями
 * на страничке charts
 */

function Moduls(conteinerName){
	// имя класса для оформления блока модуля
	var modulClassName = 'Block-Data';
	// цвет выделеного модуля
	var normalStateColor = 'rgba(0, 0, 0, 0.08)';
	// цвет обчного состояния модуля
	var selectedStateColor = 'rgba(140,160,255, 0.5)';
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
			
			modul.innerHTML = '<table style="text-align: left; line-height: normal; width: 180px; height:  80px;" border="0" cellpadding="0" 		cellspacing="2">   <tbody> <tr>  <td style="vertical-align: top; width: 150px;">Модуль: ' +moduls[i].uin+ '<br><div id="' +moduls[i].uin +'-info-trademark" style="font-size: 12px;">Марка: ' +moduls[i].trademark+ '</div><div id="'+moduls[i].uin+'-info-place" style="font-size: 12px;"> Положение:' +moduls[i].place+ '</div><div id="'+moduls[i].uin+'-time-off" style="font-size: 12px;"><span style="font-size:12px;font-weight:norm; color: #804747">Time-off: ' +moduls[i].time_off+'</span></div><div id="'+moduls[i].uin+'-max-cash" style="font-size: 12px;"><span style="font-size:14px;font-weight:bold; color: #229022"> Сумма: ' +moduls[i].max_cash+ ' грн.' + '</span></div></td>  <td style="vertical-align: middle; text-align: left; width: 18px;"><img id="' +moduls[i].uin+ '-img"' + ' style="width: 15px; eight: 44px;" alt="" src="http://e-vending.biz/picvis/0line.png"></td>   </tr>  </tbody> </table>';

			if (nameSelectedModule == null){
				nameSelectedModule = moduls[i].uin;
				document.getElementById(nameSelectedModule).onclick();
			}

		}
	}
		
}


// *************************************************************************
function updateCharts(){
	$.ajax({
  		url: '../private/ws/table',
  		type: 'GET',
  		dataType: 'json',
  		success: function(param){ 	
  			var m = new Moduls('Panel-left');
  			m.update(param.rows);
   		},
		error: function() {}
	})
}


