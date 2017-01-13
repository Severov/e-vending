<%-- 
    Document   : main
    Created on : 18-08-2016
    Author     : mishka
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="context" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="${context}/resources/css/main.css" />

        <!-- The jQuery library is a prerequisite for all jqSuite products -->
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
        <script type="text/javascript" src="${context}/resources/easyui/jquery.easyui.min.js"></script>
        
        <link rel="stylesheet" type="text/css" href="${context}/resources/easyui/themes/icon.css">
    	<link rel="stylesheet" type="text/css" href="${context}/resources/easyui/demo/demo.css">
		<link rel="stylesheet" type="text/css" href="${context}/resources/easyui/themes/default/easyui.css">
		
   		<script type="text/javascript" src="${context}/resources/js/privateRoomTable.js"></script>
   		
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Личный кабинет</title>
    </head>
    <body>  
    	
    	<jsp:include page="left-menu.jsp" />
        <div id="worcspace">
        	<table id="tt" class="easyui-datagrid" style="width:100%; height:100%; font-size:10px;"
            url="${pageContext.request.contextPath}/private/ws/table"
            title="Все модули пользователя"
			iconCls="icon-modules"
            rownumbers="true"
			singleSelect="true" 
			method="get"
			collapsible="false"
			striped="true"
			view="scrollview"
			toolbar="#tb"
			pageSize="30"
			showFooter="true"
			>
		<thead frozen="true">
        	<tr>
            	<th field="ck" checkbox="true"></th>
                <th field="uin" width="80" formatter="formatUin">ID модуля</th>
                <th field="place" auto="true">Расположение</th>
        	</tr>
    	</thead>
        <thead>
            <tr>
				<th field="temp2" auto="true" align="center" sortable="true" rowspan="2" >Датчик,<br>°C</th>
				<th field="time_off" align="left" rowspan="2" formatter="formatTimeOff" styler="stylerTimeOff" auto="true">Time-off</th>
				<th field="time_sell" auto="true" align="left" sortable="true" rowspan="2" formatter="formatTime_sell" styler="stylerTime_sell" >Простой</th>
				<th field="status"  align="center" rowspan="2" auto="true" formatter="formatStatus">Статус</th>
				<th colspan="2">Сумма, грн</th>
				<th colspan="2">Продажи, шт</th>
				<th field="count_bond" auto="true" align="center" rowspan="2" sortable="true">Банкнот,<br>шт.</th>
				<th field="progress" width="120" align="center" rowspan="2" formatter="progressFormatter" >Принятo, %</th>
				<th field="door" width="50" align="center" rowspan="2" formatter="formatDoor" styler="stylerDoor">Дверь</th>
				<th field="Level" auto="true" align="center" rowspan="2" styler="formatLevel">Сигнал</th>
				<th field="Volt" auto="true" rowspan="2" align="center">MDB, V</th>
				<th field="temp" auto="true" rowspan="2" align="center" sortable="true">Т-ра,<br>°C</th>
				<th field="version" width="120" rowspan="2" align="center" >Версия</th>
				<th field="telephon" width="120" rowspan="2" align="center" >№ Телефона</th>
			</tr>
			<tr>
				<th field="day_summ" auto="true" align="center" sortable="true">За сутки</th>
				<th field="max_cash" auto="true" align="center" sortable="true">Всего</th>
				<th field="count_bond_day" auto="true" align="center" sortable="true">За сутки</th>
				<th field="max_sell" auto="true" align="center" sortable="true">Всего</th>
            </tr>
        </thead>
    </table>
        </div>
        
        <!-- Панель управления табличкой -->
        	<div id="tb">
	<a href="#" title="Управление выбранным модулем" class="easyui-tooltip easyui-linkbutton" iconCls="icon-control" plain="true" onclick="$('#panel-graph').panel('collapse'); $('#panel-settings-module-main').dialog('open'); $('#panel-settings-module-main').dialog('center'); return false;">Управление</a>

	<a href="#" title="Настройки выбранного модуля" class="easyui-tooltip easyui-linkbutton" iconCls="icon-settings" plain="true" onclick="query_settings_module(); return false;">Настройки</a>

	<a href="#" title="Икасация выбранного модуля" class="easyui-tooltip easyui-linkbutton" iconCls="icon-inkasso" plain="true" onclick="open_Collect(); return false;">Инкассация</a>

    <a href="#" title="Зарегистрировать в системе новый модуль" class="easyui-tooltip easyui-linkbutton" iconCls="icon-add" plain="true" onclick="getFormRegModule(); return false">Добавить модуль</a>

	<a href="#" title="Редактировать выбранный модуль" class="easyui-tooltip easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="show_info_module(); return false">Редактировать</a>

	<a href="#" title="Проверить состояние мобильного счета" class="easyui-tooltip easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="sendComand_Balance(); return false">Состояние счета</a>

	<div style="float:right;">
		<a href="#" title="Удалить выбранный модуль" class="easyui-tooltip easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="confirmDeleteModule(); return false">Удалить выбранный модуль</a>
	</div>

	</div>
	
	<!--ПАНЕЛЬ РЕГИСТРАЦИИ МОДУЛЯ-->
<div id="panel-registration-module-main" class="easyui-dialog" style="padding:5px;width:auto;height:auto;line-height: 1.4;"
	title="  Регистрация нового модуля"
	iconCls="icon-add"
	closed="true"
	modal="true"
    buttons="#panel-registration-module">

	<span class="sp">
		Отправьте на регистрируемый</br> модуль SMS с текстом:
	</span></br>
	<input class="input" id='UID' name="keyModule" type="text" readonly style="text-align:center;font-weight:bold;" /></br>
	<span class="rr">
		(!) Модуль должен быть включен</br> и подготовлен к работе
	</span>
</div>

<div id="panel-registration-module">
    <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:$('#panel-registration-module-main').dialog('close'); return false;">Ок</a>
</div>

<!--КОНЕЦ ПАНЕЛИ РЕГИСТРАЦИИ МОДУЛЯ-->

<!--ПАНЕЛЬ ИНФОРМАЦИИ О МОДУЛЕ-->
<div id="panel-edit-module-main" class="easyui-dialog" style="padding:5px;width:auto;height:auto;line-height: 1.4;"
	title="Информация о модуле"
	iconCls="icon-edit"
	closed="true"
	modal="true"
    buttons="#panel-edit-module">

	<table>
        <tr>
            <td>Марка:</td>
            <td><input class="input" name="username" id="trademark" placeholder="Марка, модель" value="" type="text"></input></td>
        </tr>
        <tr>
            <td>Расположение:</td>
            <td><input class="input" name="fullname" id="place" placeholder="Адрес или описание" value="" type="text"></input></td>
        </tr>
	</table>
</div>

<div id="panel-edit-module">
    <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="save_info_module(); $('#panel-edit-module-main').dialog('close'); return false;">Ок</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#panel-edit-module-main').dialog('close'); return false;">Отмена</a>
</div>
<!--КОНЕЦ ПАНЕЛИ ИНФОРМАЦИИ О МОДУЛЕ-->

<!--Панель инкасации-->
	<div id="panel-collect" class="easyui-dialog" style="padding:5px;width:auto;height:auto;"
        title="Инкасcация"
		iconCls="icon-inkasso"
		closed="true"
		modal="true"
        buttons="#panel-collect-buttons">
    	<div id="panel-collect-info" style="line-height: 1.4"></div>
    <table>
	<tr>
       <td></td>
    </tr>
        <tr>
            <td>План:</td>
            <td><input id="panel-collect-plan" class="easyui-numberbox" data-options="precision:2,groupSeparator:' ',decimalSeparator:'.', disabled:true"></input></td>
			<td> грн.</td>
        </tr>
        <tr>
            <td>Факт:</td>
            <td><input id="panel-collect-fakt" class="easyui-numberbox" data-options="precision:2,groupSeparator:' ',decimalSeparator:'.'"></input></td>
			<td> грн.</td>
        </tr>
	</table>
	</div>


<div id="panel-collect-buttons">
    <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="Send_Comand_Collect_New(); return false;">Ок</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#panel-collect').dialog('close'); return false;">Отмена</a>
</div>

<!--КОНЕЦ ПАНЕЛИ ИНКАСАЦИИ-->
 
    </body>
</html>
