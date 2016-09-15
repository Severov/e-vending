SELECT
	modul.modul_id as `nodul_id`,
    modul.uin as `uin`,
    modul.place,
	modul.version,
	modul.telephon,
	modul.trademark,
    tbl_cash.max_cash,
    tbl_cash.max_sell
FROM
	company, modul
-- выборка суммы и продаж с последней реализации
LEFT JOIN(
		SELECT
			MAX(IFNULL(cash, 0)) AS `max_cash`,
			MAX(IFNULL(sell, 0)) AS `max_sell`,
			cashModule.modul_id  AS `modul_id`
		FROM
			cashModule
		INNER JOIN (
			SELECT
				MAX(timeStamp) AS `t1`, 
				collectionModule.modul_id as `modul_id`
			FROM
				collectionModule
			GROUP BY
				collectionModule.modul_id
		) AS `tmp` ON cashModule.modul_id = tmp.modul_id AND tmp.t1 <= cashModule.timeStamp
GROUP BY
	cashModule.modul_id
) AS `tbl_cash` ON modul.modul_id = tbl_cash.modul_id
-- время off модуля
LEFT JOIN (
	SELECT
		MAX(dataModule.timeStamp) AS `time_off`,
		dataModule.modul_id AS `modul_id`
	FROM
		dataModule
	GROUP BY
		dataModule.modul_id
) AS `time_off` ON time_off.modul_id = modul.modul_id
-- КОЛИЧЕСТВО БАНКНОТ ПОСЛЕ ИНКАСАЦИИ
LEFT JOIN (
	SELECT
		bs AS `count_bond`,
    	cashModule.modul_id as `modul_id`
	FROM
		cashModule
	INNER JOIN (
		SELECT
			MAX(cashModule.cashModule_id) as `tm`,
			cashModule.modul_id AS `modul_id`
		FROM
			cashModule
		GROUP BY
			cashModule.modul_id
	) AS `tmp` ON tmp.modul_id = cashModule.modul_id AND tmp.tm = cashModule.cashModule_id
) AS `bs` ON modul.modul_id = bs.modul_id
-- КОЛИЧЕСТВО ПРОДАЖ ПОЛУЧЕННЫХ ЗА ПОСЛЕДНИЕ СУТКИ
LEFT JOIN (
	SELECT
		COUNT(sell) AS `count_bond_day`,
		cashModule.modul_id AS `modul_id`
	FROM
		cashModule
	WHERE
		cashModule.timeStamp BETWEEN '2016-08-09 00:00:00' AND '2016-08-09 23:00:00' 
		AND bond = 0
		AND cash = 0
		AND sell IS NOT NULL
		AND sell <> 0
	GROUP BY
		cashModule.modul_id
) AS `bs_day` ON modul.modul_id = bs_day.modul_id
-- Температурные показатели модулей
LEFT JOIN (
	SELECT
		dataModule.modul_id AS `modul_id`,
		dataModule.temp / 10 AS `temp`,
		dataModule.temp2 / 10 AS `temp2`,
		dataModule.u / 10 AS `V`,
		FLOOR(dataModule.l / 5.4) AS `level`,
		dataModule.l AS `level2`
	FROM
		dataModule
		 	INNER JOIN (
				SELECT
					MAX(dataModule.dataModul_id) as `tm`,
					dataModule.modul_id AS `modul_id`
				FROM
					dataModule
				WHERE
					dataModule.timeStamp BETWEEN ADDDATE('2016-08-09 00:00:00', INTERVAL -310 SECOND) AND '2016-08-09 00:00:00'
				GROUP BY
					dataModule.modul_id
			) AS `tmp` ON tmp.modul_id = dataModule.modul_id AND tmp.tm = dataModule.dataModul_id	
) AS `temp` ON modul.modul_id = temp.modul_id
-- Положение дверей модуля
LEFT JOIN (
	SELECT
		dataDoor.k AS `k`,
		dataDoor.modul_id
	FROM
		dataDoor
	WHERE
		dataDoor.timeStamp BETWEEN ADDDATE('2016-08-09 00:00:00', INTERVAL -30 SECOND) AND '2016-08-09 00:00:00'
	ORDER BY
		dataDoor.dataDoor_id DESC
	LIMIT 1
) AS `door` ON door.modul_id = modul.modul_id
-- обязательное условие выборки модулей
WHERE
	company.company_id = 1 
    AND company.company_id = modul.company_id	
    
    
    	
