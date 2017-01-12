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
	
	if (delta >= 14400){
		return 'background-color: #ffb098;';
	}
	
	if(delta >= 50400){
		return 'background-color: #ff75a2;';
	}
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
  			if($('#tt').datagrid('getRows').length == 0){
  				$('#tt').datagrid('reload');
  				return;
  			}
  				
  			$('#tt').datagrid('loadData', param);    // reload the data
  		},
		error: function() {}
	})
}

// *** END MAIN FUNCTION ***


$(document).ready(function () {
	
	setInterval(reloadTable, 60000);

	$('#tt').datagrid({
		rowStyler:function(index,row){
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
			document.getElementById("tt").style.height = ($('#tt').datagrid('getRows').length +1) * 25 + 110;
				$('#tt').datagrid('resize');
        }
	});


	// Добавим дополнительные кнопки управления
	var pager = $('#tt').datagrid('getPager');
	pager.pagination({
		buttons:$('#pg')
	});
	
});
