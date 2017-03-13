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
		
		<script type="text/javascript" src="${context}/resources/js/general.js"></script>
   		<script type="text/javascript" src="${context}/resources/js/privateRoomTable.js"></script>
   		<script type="text/javascript" src="${context}/resources/js/preloader.js"></script>
   		
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
	<a href="#" title="Управление выбранным модулем" class="easyui-tooltip easyui-linkbutton" iconCls="icon-control" plain="true" onclick="$('#panel-settings-module-main').dialog('open'); $('#panel-settings-module-main').dialog('center'); return false;">Управление</a>
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

<!--ПАНЕЛЬ УПРАВЛЕНИЯ МОДУЛЕМ-->
<div id="panel-settings-module-main" class="easyui-dialog" style="padding:5px;width:auto;height:auto;line-height: 1.4;"
	title="Управление модулем"
	iconCls="icon-control"
	closed="true"
	modal="true"
    buttons="#panel-settings-module">

	<table>
        <tr>
            <td>
				<span class="sp">
				<!--<input tabindex="1" id="c01" name="comm" value="collect"    type="radio"> Инкассация <Br> -->
				<input tabindex="2" id="c02" name="comm" value="formatcard" type="radio"> Форматировать карту памяти <Br>
 				<input tabindex="3" id="c03" name="comm" value="dlfirmware" type="radio"> Обновить прошивку<Br>
  				<input tabindex="4" id="c04" name="comm" value="default"    type="radio"> Сброс всех настроек<Br>
  				<input tabindex="5" id="c05" name="comm" value="reset"      type="radio"> Перезагрузка<Br>
				</span>
			</td>
        </tr>
        <c:if test="${root_user == 1}">
			<tr>
            	<td>
            		<input title="Произвольная команда" id="randComand" class="easyui-tooltip" type="edit" placeholder="команда">
				</td>
            	<td>
            		<input title="Отправить команду" class="easyui-tooltip" value=">>" type="button" onclick="send_random_comand(); return false;">
				</td>
        	</tr>
		</c:if>
	</table>
</div>

<div id="panel-settings-module">
    <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="send_Comand(); return false;">Ок</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#panel-settings-module-main').dialog('close'); return false;">Отмена</a>
</div>
<!--КОНЕЦ ПАНЕЛИ УПРАВЛЕНИЯ МОДУЛЕМ-->

<!-- ЗАПРОС БАЛАНСА -->
<div class="loginForm-balance" id="balanceForm" style='display: none; position: fixed; width: 70px; height: 70px; top: 50%; left: 50%; margin-top: -135px; margin-left: 0px; z-index: 1010;'>
	<div id="loaderImage"></div>
</div>
<!-- КОНЕЦ ЗАПРОС БАЛАНСА -->

<!--ПАНЕЛЬ НАСТРОЙКИ МОДУЛЯ-->
<div id="panel-setings-module-main" class="easyui-dialog" style="padding:5px;width:auto;height:auto;line-height: 1.4;"
	title="Настройка модуля"
	iconCls="icon-settings"
	closed="true"
	modal="true"
    buttons="#panel-setings-module">

	<select id="sthours" name="sthours" tabindex="1">
		<option value=0>00</option>
		<option value=1>01</option>
		<option value=2>02</option>
		<option value=3>03</option>
		<option value=4>04</option>
		<option value=5>05</option>
		<option value=6>06</option>
		<option value=7>07</option>
		<option value=8>08</option>
		<option value=9>09</option>
		<option value=10>10</option>
      	<option value=11>11</option>
		<option value=12>12</option>
		<option value=13>13</option>
		<option value=14>14</option>
		<option value=15>15</option>
		<option value=16>16</option>
		<option value=17>17</option>
		<option value=18>18</option>
		<option value=19>19</option>
		<option value=20>20</option>
     	<option value=21>21</option>
		<option value=22>22</option>
		<option value=23>23</option>
     </select>ч

	<select id="stminutes" name="stminutes" tabindex="2">
		<option value=0>00</option>
		<option value=1>01</option>
		<option value=2>02</option>
		<option value=3>03</option>
		<option value=4>04</option>
		<option value=5>05</option>
		<option value=6>06</option>
		<option value=7>07</option>
		<option value=8>08</option>
		<option value=9>09</option>
		<option value=10>10</option>
        <option value=11>11</option>
		<option value=12>12</option>
		<option value=13>13</option>
		<option value=14>14</option>
		<option value=15>15</option>
		<option value=16>16</option>
		<option value=17>17</option>
		<option value=18>18</option>
		<option value=19>19</option>
		<option value=20>20</option>
		<option value=21>21</option>
		<option value=22>22</option>
		<option value=23>23</option>
		<option value=24>24</option>
		<option value=25>25</option>
		<option value=26>26</option>
		<option value=27>27</option>
		<option value=28>28</option>
		<option value=29>29</option>
		<option value=30>30</option>
        <option value=31>31</option>
		<option value=32>32</option>
		<option value=33>33</option>
		<option value=34>34</option>
		<option value=35>35</option>
		<option value=36>36</option>
		<option value=37>37</option>
		<option value=38>38</option>
		<option value=39>39</option>
		<option value=40>40</option>
        <option value=41>41</option>
		<option value=42>42</option>
		<option value=43>43</option>
		<option value=44>44</option>
		<option value=45>45</option>
		<option value=46>46</option>
		<option value=47>47</option>
		<option value=48>48</option>
		<option value=49>49</option>
		<option value=50>50</option>
        <option value=51>51</option>
		<option value=52>52</option>
		<option value=53>53</option>
		<option value=54>54</option>
		<option value=55>55</option>
		<option value=56>56</option>
		<option value=57>57</option>
		<option value=58>58</option>
		<option value=59>59</option>
	</select>м Время RTC

	<p>
		<label for="c01">
			<select id="stprofile" name="stprofile" tabindex="3">
				<option value="1">ПР1</option>
				<option value="2">ПР2</option>
				<option value="3">ПР3</option>
				</select> Профиль пользователя
		</label>
	</p>

	<p>
		<label for="c06">
			<select id="stbalance" name="stbalance" tabindex="4">
				<option value="2">*101#</option>
				<option value="1">*111#</option>
				<option value="3">*112#</option>
				<option value="4">*119#</option>
				<option value="5">*121#</option>
			</select> Баланс для sms-отчета
		</label>
	</p>

	<p><label for="c02"><input name="strequest" id="strequest" data-options="onText:'Вкл.',offText:'Выкл.'" tabindex="5" class="easyui-switchbutton"> Режим запроса</label></p>
	<p><label for="c03"><input name="stsilent" id="stsilent" data-options="onText:'Вкл.',offText:'Выкл.'" tabindex="6" class="easyui-switchbutton"> Бесшумный режим</label></p>
	<p><label for="c04"><input id="stvoice" data-options="onText:'Вкл.',offText:'Выкл.'" name="stvoice"  tabindex="7" class="easyui-switchbutton"> Голосовой режим</label></p>
	<p><label for="c05"><input id="stigprs" data-options="onText:'Вкл.',offText:'Выкл.'" name="stigprs"  tabindex="8" class="easyui-switchbutton"> GPRS соединение</label></p>
</div>

<div id="panel-setings-module">
    <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="stopAnimation(); save_current_settings_module(); $('#panel-setings-module-main').dialog('close'); return false;">Ок</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="stopAnimation(); $('#panel-setings-module-main').dialog('close'); return false;">Отмена</a>
</div>
<!--КОНЕЦ ПАНЕЛИ НАСТРОЙКИ МОДУЛЯ-->
 
<div id='podlogka' style='position: fixed; display: none; top: 0; left: 0; width: 100%; height: 100%; background: #000; opacity: 0.6; z-index: 1000;'></div>
    </body>
</html>
