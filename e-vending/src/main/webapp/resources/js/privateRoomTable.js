/**
 * 
 */

// Хранить имя текущего выделеного модуля
var nameSelectedModule = null;

// *** START FORMATER/STYLER TABLE ***
function stylerTimeOff(value, rowData, rowIndex){
	
	value = getHumenTime(value, rowData.nowUnixTime);
	rowData.time_off = value;
	
    if (value != '0 м' && value != ''){
    	rowData.status = 'OFF';
        return 'background-color:#AC0000; color:white; font-weight:bold;';
    } 
}

function formatTimeOff(value, rowData, rowIndex){
	return rowData.time_off; 
}

function stylerTime_sell(value,rowData,rowIndex){
	
	rowData.time_sell = getHumenTime(value, rowData.nowUnixTime);
	delta = rowData.nowUnixTime - value;
	
	if (delta == 0){
		return;
	}
	
	var style = '';
	
	if (delta >= 14400){
		style = 'background-color: #ffb098;';
	}
	
	if(delta >= 50400){
		style = 'background-color: #ff75a2;';
	}
	
	return style;
}

function formatTime_sell(value,rowData,rowIndex){
	return rowData.time_sell;
}

function progressFormatter(value,rowData,rowIndex){
	if (value){
		var s = '<div style="width:100%;border:1px solid #ccc">' + '<div style="width:' + value + '%;background:'+((value >= 90) ? '#7BFF94' : '#FC0000')+';">' + value + '%' + '</div></div>';
		return s;
	} else {
		return '';
	}
}

function formatLevel(val, row){
    if (val <= 1){
        return 'background-color: #ffb098;';
    } 
}

function stylerDoor(value, rowData, rowIndex){	
	var style;
	
	switch(value){
		case -100: 
			rowData.door = 'N/A';
			style =  'background-color: #ffb098;';
			break;
		case 1:
			rowData.door = 'Open';
			style = 'background-color: #FC6441;';
			break;
		case undefined:
			style = '';
			break;
		default:
			rowData.door = 'Close';
			style = 'background-color: #7BFF94;';	
	}

	return style;
}

function formatDoor(value, rowData, rowIndex){	
	if(value == undefined){
		return '';
	}
	
	return rowData.door;
}

function formatUin(value, rowData, rowIndex){
	if (value && rowData.id_comand > 0){
		return '<a class="easyui-tooltip-error" href="javascript:void(0)" style="color:red;">' + value + '</a>';
	}else{
		return value;
	}
}

function formatStatus(value, rowData, rowIndex){
	if (value > 0){
		rowData.status = 'Error (' + rowData.description_error + ') ' + rowData.time_error;
		return rowData.status;
	}else{
		return value;
	}
}

// *** END FORMATER/STYLER TABLE ***


// *** START MAIN FUNCTION ***

// возвращает время в виде хд. хч. хм.
function getHumenTime(paramTime, curent_time) {
	
	if(paramTime == undefined){
		return '';
	}
	
	if (paramTime == 0){
		return 'Более 1 н';
	}
	
	if (curent_time - paramTime < 300) {
		return '0 м';
	}
	
	var returnValue = '0 м';
	
	var rez = curent_time - paramTime;
	var day = Math.floor(rez / 86400); // дней
	day = day > 0 ? day : 0;

	var hour = Math.floor((rez - day * 86400) / 3600); // часов
	hour = hour > 0 ? hour : 0;

	var minute = Math.floor((rez - day * 86400 - hour * 3600) / 60); // Минут
	minute = minute > 0 ? minute : 0;

	if (day != 0 || hour != 0 || minute != 0){
		returnValue =  day + ' д ' + hour + ' ч ' + minute + ' м';
	}
	
	return returnValue;
}

function open_Collect(){
	var row = $('#tt').datagrid('getRows');
	
	var nn = $('#tt').datagrid('options').sortOrder;
}

// Обновление таблички
function reloadTable(){
	var sort = $('#tt').datagrid('options').sortName;
	var order = $('#tt').datagrid('options').sortOrder;
	var sortString = '';
	
	if(sort != undefined && order != undefined){
		sortString = '?sort=' + sort + '&order=' + order;
	}
	
	$.ajax({
  		url: '../private/ws/table' + sortString,
  		type: 'GET',
  		dataType: 'json',
  		success: function(param){ 	
  			
  			var currentLength = $('#tt').datagrid('getRows').length;
  			if(currentLength == 0){
  				$('#tt').datagrid('reload');
  				return;
  			}
  				
  			$('#tt').datagrid('loadData', param);    // reload the data
  			
  			if(currentLength < param.rows.length) {
  				throw_message('Добавлен новый модуль');
  			}
  		},
		error: function() {}
	})
}

// Регистрация нового модуля
function getFormRegModule(){
	$.ajax({
  		url: '../private/ws/registerModul',
  		type: 'GET',
  		dataType: "json",
  		success: function(code){
								document.getElementById('UID').value = 'REG'+code;
								$('#panel-registration-module-main').dialog('open');
								$('#panel-registration-module-main').dialog('center');
								},
		error: function() {throw_message('Не удалось получить код.<br>Проверьте подключение к интернету!');}
	})
}

//Отобразить форму информации о модуле
function show_info_module(){
	var row = $('#tt').datagrid('getSelected');
	if (row){
		document.getElementById('place').value = row.place;
		document.getElementById('trademark').value = row.trademark;
		
		$('#panel-edit-module-main').dialog('open');
		$('#panel-edit-module-main').dialog('center');
	}
}

//Сохраним информацию о модуле
function save_info_module(){
	var row = $('#tt').datagrid('getSelected');
	if (row){
			$.ajax({
				url: '../private/ws/' + row.uin + '/saveInfoModul?trademark=' + document.getElementById('trademark').value + '&place=' + document.getElementById('place').value,
				type: 'GET',
				dataType: 'json',
				success: function(){
							$("#info-module-form").fadeOut(500);
							reloadTable();
							throw_message('Данные успешно сохранены!');
							  },
				error: function() {throw_message('Не удалось отправить команду!');}
			});
	}
}

//Отправка команды модулю
function send_Comand(){	
	var row = $('#tt').datagrid('getSelected');
	if (!row){
		throw_message('Модуль не выбран');
		return;
	}

	$.ajax({
  		url: '../private/ws/' + row.uin + '/sendCommand?command=' +  $('input[name=comm]:checked').val(),
  		type: 'GET',
		dataType: 'json',
  		success: function(mes){  								
  								if (!mes){
  									throw_message('Произошел сбой при отправке команды. Повторите попытку позже!');
  								}else{
  									throw_message('Команда отправлена успешно');
  								}
  								
							  },
		error: function() {throw_message('Не удалось отправить команду!');}
});
	
	$('#panel-settings-module-main').dialog('close');
}

//Отправка запроса настроек модуля
function query_settings_module(){
	var row = $('#tt').datagrid('getSelected');
	if (!row){
		throw_message('Модуль не выбран');
		return;
	}
	
	$.ajax({
  		url: '../private/ws/' + row.uin + '/sendCommand?command=isett',
  		type: 'GET',
  		data: "comand=sendComand&uin=" + nameSelectedModule + "&comm=isett",
		dataType: 'json',
  		success: function(mes){
								$('#podlogka').fadeIn(500);
								$('#balanceForm').fadeIn(500);
								startAnimation();
								setTimeout(get_current_settings_module, 20000);
							  },
		error: function() {
			$('#podlogka').fadeOut(500);
			$('#balanceForm').fadeOut(500);
			stopAnimation();
			throw_message('Не удалось отправить команду!');
		}
});
}

//Установим настройки модуля
function get_current_settings_module(){
	var row = $('#tt').datagrid('getSelected');
	if (!row){
		throw_message('Модуль не выбран');
		return;
	}
	
	$.ajax({
		url: '../private/ws/' + row.uin + '/getCurrentSettings',
  		type: 'GET',
  		dataType: 'json',
  		success: function(param){

			$('#panel-graph').panel('collapse'); // свернем панель с графиками

			$("#podlogka").fadeOut(500);
			$("#balanceForm").fadeOut(500);
			stopAnimation();

			$('#strequest').switchbutton(param.request == '1' ? 'check' : 'uncheck');
			$('#stsilent').switchbutton(param.silent == '1' ? 'check' : 'uncheck'); 
			$('#stvoice').switchbutton(param.voice == '1' ? 'check' : 'uncheck');
			$('#stigprs').switchbutton('check');

			document.getElementById('sthours').options[parseInt(param.hours, 10)].selected     = true;
			document.getElementById('stminutes').options[parseInt(param.minutes, 10)].selected = true;
			document.getElementById('stprofile').options[parseInt(param.profile, 10)-1].selected = true;
			document.getElementById('stbalance').options[parseInt(param.balance, 10)].selected = true;

			$('#panel-setings-module-main').dialog('open');
			$('#panel-setings-module-main').dialog('center');

		},
		error: function() {
			$("#podlogka").fadeOut(500);
			$("#balanceForm").fadeOut(500);
			$('#popup-setings-module').fadeOut(500);
			stopAnimation();
			throw_message('Не удалось получить настройки модуля!');}
})
}

//Всплывающие уведомления
function throw_message(str, fadein, latency, fadeof) {
		// Установим значение по умолчанию
		if (typeof(fadein)==='undefined')   fadein  = 500;
  		if (typeof(latency)==='undefined')  latency = 3000;
		if (typeof(fadeof)==='undefined')   fadeof  = 500;

        $.messager.show({
                title:'Уведомление',
                msg:str,
                timeout:latency,
                showType:'slide',
				style:{
                    left:'',
                    right:0,
                    top:document.body.scrollTop+document.documentElement.scrollTop,
                    bottom:''
                }
            });
}

// подтверждение на удаление модуля из учетной записи
function confirmDeleteModule(){
    $.messager.confirm('Удаление модуля', 'Вы действительно хотите удалить выбранный модуль ?', function(r){
        if (r){
            delete_module();
        }
    });
}

//удалим модуль по просьбе пользователя
function delete_module(){
	var row = $('#tt').datagrid('getSelected');
	if (!row){
		return;
	}

	$.ajax({
  		url: '../private/ws/deleteModul?uin=' + row.uin,
  		type: 'GET',
		dataType: 'json',
  		success: function(mes){
								if(!mes){
									throw_message('Не удалось удалить модуль! <br> Попробуйте еще раз!');
									return;
								}
								
								reloadTable();
								throw_message('Модуль успешно удален!');

							  },
		error: function() {throw_message('Не удалось отправить команду!');}
	});
}

// открывает панель инкассации
function open_Collect(){

	var row = $('#tt').datagrid('getSelected');
	if (!row){
		throw_message("Выберите модуль!");
		return;
	}
	
	if (row.time_off != '0 м' && row.time_off != ''){ // модуль выкл
		$.messager.alert('Предупреждение', 'Модуль не в сети. Проведение операций с модулем невозможно!','error');
		return;
	}

	//$('#panel-collect-fakt').focus();
	$('#panel-collect').dialog('open');
	$('#panel-collect').dialog('center');

	$("#panel-collect-fakt").numberbox('setValue', row.max_cash);
	$("#panel-collect-plan").numberbox('setValue', row.max_cash);
	document.getElementById('panel-collect-info').innerHTML = 'Вы действительно хотите произвести инкассацию модуля ' + row.uin + ' (' + row.place + ') ?';

}

// собственно - сама инкассация
function Send_Comand_Collect_New(){
	var row = $('#tt').datagrid('getSelected');
	if (!row){
		return;
	}
	
	 $.ajax({
 				url: '../private/ws/' + row.uin + '/setCollection?plan=' + document.getElementById('panel-collect-plan').value + '&fakt=' +  document.getElementById('panel-collect-fakt').value,
 				type: 'GET',
				dataType: "json",
 				success: function(mes){
 								if (mes){
 									throw_message('Инкассация прошла успешно!');
 								}else{
 									throw_message('Возникла ошибка. Повторите попоже!');
 								}
							  },
				error: function() {throw_message('Не удалось отправить команду!');}
	});

	$('#panel-collect').dialog('close');
}


// *** END MAIN FUNCTION ***



$(document).ready(function () {
	
	setInterval(reloadTable, 60000);

	$('#tt').datagrid({
		rowStyler:function(index, row){
			if ((row.status + '').charAt(0) == 'E' ){
				return 'background-color:#FC6441;color:blue;font-weight:bold;';
			}

		},
		onSelect: function(rowIndex, rowData){
			if (rowData){
				if (rowData.uin != nameSelectedModule){
					nameSelectedModule = rowData.uin;
				}
			}
		},
		onLoadSuccess: function (data) { // Если этот модуль был выран пользователем
			if (!nameSelectedModule){
				$(this).datagrid('checkRow', 0);
				return;
			}
			
			//data.rows.length = data.rows.length;
			for (i = 0; i < data.rows.length; ++i) {
				if(data.rows[i]['uin'] == nameSelectedModule)
					$(this).datagrid('checkRow', i);
					// break;
			}
			document.getElementById('tt').style.height = ($('#tt').datagrid('getRows').length +1) * 25 + 110;
				$('#tt').datagrid('resize');
        }
	});

	// Добавим дополнительные кнопки управления
	var pager = $('#tt').datagrid('getPager');
	pager.pagination({
		buttons:$('#pg')
	});
	
});
