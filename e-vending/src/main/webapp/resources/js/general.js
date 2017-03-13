/**
 * Процедуры и функции общего назначения
 */

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