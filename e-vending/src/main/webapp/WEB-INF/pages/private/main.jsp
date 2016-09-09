<%-- 
    Document   : main
    Created on : 18-08-2016
    Author     : mishka
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css"
              href="<c:url value='../resources/css/main.css'/>" />

        <!-- The jQuery library is a prerequisite for all jqSuite products -->
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
        <script type="text/javascript" src="../resources/easyui/jquery.easyui.min.js"></script>
        
        <link rel="stylesheet" type="text/css" href="../resources/easyui/themes/icon.css">
    	<link rel="stylesheet" type="text/css" href="../resources/easyui/demo/demo.css">
		<link rel="stylesheet" type="text/css" href="../resources/easyui/themes/default/easyui.css">
		
   
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Личный кабинет</title>
    </head>
    <body>  
    	
    	<jsp:include page="left-menu.jsp" />
        <div id="worcspace">
        	<table id="tt" class="easyui-datagrid" style="width:100%; height:100%; font-size:10px;"
            url="${pageContext.request.contextPath}/ws/table"
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
                <th field="uin" width="80">ID модуля</th>
                <th field="place" auto="true">Расположение</th>
        	</tr>
    	</thead>
        <thead>
            <tr>
				<th field="temp2" auto="true" align="center" sortable="true" rowspan="2" >Датчик,<br>°C</th>
				<th field="time_off" align="left" rowspan="2" auto="true">Time-off</th>
				<th field="time_sell" auto="true" align="left" sortable="true" rowspan="2">Простой</th>
				<th field="status"  align="center" sortable="true" rowspan="2" auto="true">Статус</th>
				<th colspan="2">Сумма, грн</th>
				<th colspan="2">Продажи, шт</th>
				<th field="count_bond" auto="true" align="center" rowspan="2" sortable="true">Банкнот,<br>шт.</th>
				<th field="progress" width="120" align="center" rowspan="2" >Принятo, %</th>
				<th field="door" width="50" align="center" rowspan="2" >Дверь</th>
				<th field="Level" auto="true" align="center" rowspan="2" >Сигнал</th>
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

    <a href="#" title="Зарегистрировать в системе новый модуль" class="easyui-tooltip easyui-linkbutton" iconCls="icon-add" plain="true" onclick="getFormRegModule(); $('#panel-registration-module-main').dialog('open'); $('#panel-registration-module-main').dialog('center'); return false">Добавить модуль</a>

	<a href="#" title="Редактировать выбранный модуль" class="easyui-tooltip easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="$('#panel-edit-module-main').dialog('open'); $('#panel-edit-module-main').dialog('center'); show_info_module(); return false">Редактировать</a>

	<a href="#" title="Проверить состояние мобильного счета" class="easyui-tooltip easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="sendComand_Balance(); return false">Состояние счета</a>

	<div style="float:right;">
		<a href="#" title="Удалить выбранный модуль" class="easyui-tooltip easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="confirmDeleteModule(); return false">Удалить выбранный модуль</a>
	</div>

	</div>
 
    </body>
</html>
