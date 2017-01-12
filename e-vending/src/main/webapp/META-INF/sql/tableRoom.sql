SELECT
    modul.modul_id as `modul_id`,
    modul.uin as `uin`,
    modul.place,
    UNIX_TIMESTAMP(now()) as `nowUnixTime`,
    modul.lat as `lat`,
    modul.lng as `lng`,
    modul.version,
    modul.telephon,
    modul.trademark,
    CASE WHEN IFNULL(FORMAT(temp.temp2, 2), '') = -100 THEN "" ELSE IFNULL(FORMAT(temp.temp2, 2), '') END AS `temp2`,
    CASE WHEN IFNULL(FORMAT(temp.temp, 2), '') = -100 THEN "" ELSE  IFNULL(FORMAT(temp.temp, 2), '') END AS `temp`,
    IFNULL(temp.level, 0) AS `Level`,
    IFNULL(temp.level2, 0) AS `Level2`,
    FORMAT(IFNULL(temp.V, ' '), 2) AS `Volt`,
    IFNULL(tmp_error.error, 'Ok') AS `status`,
    IFNULL(tmp_error.time_error, '') AS `time_error`,
    IFNULL(tmp_error.description, '') AS `description_error`,
    IFNULL(UNIX_TIMESTAMP(last_sell.time_off_sell), 0) as `time_sell`,
    IFNULL(UNIX_TIMESTAMP(time_off.time_off), 0) AS `time_off`,
    IFNULL(bs.count_bond, 0) as `count_bond`,
    FLOOR(IFNULL(prinjatoCash.prinjato, 0)) as `progress`,
    IFNULL(door.k, -100) as `door`,
    IFNULL(comand_to_module.id_comand, 0) as `id_comand`,
    IFNULL(day_rec.day_bond, 0) as `day_summ`,
    IFNULL(bs_day.count_bond_day, 0) as `count_bond_day`,
    IFNULL(tbl_cash.max_cash, 0) as `max_cash`,
    IFNULL(tbl_cash.max_sell, 0) as `max_sell`
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
        WHERE
			bs IS NOT NULL
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
        cashModule.timeStamp BETWEEN :startDay AND :nowTime 
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
                    dataModule.timeStamp BETWEEN ADDDATE( :nowTime, INTERVAL -310 SECOND) AND :nowTime
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
        dataDoor.timeStamp BETWEEN ADDDATE( :nowTime , INTERVAL -30 SECOND) AND :nowTime
    ORDER BY
        dataDoor.dataDoor_id DESC
    LIMIT 1
) AS `door` ON door.modul_id = modul.modul_id
-- СУММА ПРОДАЖ ЗА ДЕНЬ
LEFT JOIN (
    SELECT 
        SUM(bond) as `day_bond`,
        modul_id
    FROM
        cashModule 
    WHERE 
        (timeStamp BETWEEN :startDay AND :endDay ) 
        AND bond <> 0 
    GROUP BY
        DATE(timeStamp),
        modul_id
) as `day_rec` ON day_rec.modul_id = modul.modul_id
-- ПРОЦЕНТ ПРИЁМА КУПЮР = [ПРИНЯТЫЕ] * 100 / [ПРИНЯТЫЕ] + [ОТКАЗЫ]
LEFT JOIN (
    SELECT 
        SUM(FORMAT(IFNULL(mdc.in, 0) / (IFNULL(mdc.in, 0) + IFNULL(nt.not_in, 0)) * 100, 2)) as `prinjato`,
        mdc.modul_id
    FROM    
        (
            SELECT
                COUNT(cashModule_id) as `in`,
                modul_id 
            FROM
                cashModule
            WHERE 
                timeStamp BETWEEN ADDDATE( :nowTime , INTERVAL -7 DAY) AND  :nowTime
                AND bond > 0 
            GROUP BY
                modul_id
        ) as `mdc`
    LEFT JOIN 
        (
            SELECT 
                COUNT(cashNotReception.cashNotReception_id) as `not_in`,
                MAX(cashNotReception.timeStamp) as `time_not`,
                cashNotReception.modul_id
            FROM 
                cashNotReception
            LEFT JOIN 
                (   
                    SELECT 
                        MAX(timeStamp) as `time_reset`,
                        modul_id 
                    FROM 
                        resetKup 
                    WHERE 
                        timeStamp BETWEEN ADDDATE( :nowTime , INTERVAL -7 DAY) AND  :nowTime
                    GROUP BY 
                        modul_id
                ) as `reset` ON reset.modul_id = cashNotReception.modul_id
            WHERE
                timeStamp BETWEEN IF (reset.time_reset IS NULL, ADDDATE( :nowTime , INTERVAL -7 DAY), reset.time_reset) AND  :nowTime
            GROUP BY
                cashNotReception.modul_id

        ) as `nt` ON nt.modul_id = mdc.modul_id
    GROUP BY
            mdc.modul_id
) as `prinjatoCash` ON prinjatoCash.modul_id = modul.modul_id
-- ВРЕМЯ, КОТОРОЕ ПРОШЛО С ПОСЛЕДНЕЙ ПОКУПКИ ( 27.01.2016 - С ПОСЛЕДНЕ ПОСТУПИВШЕЙ КУПЮРЫ )
LEFT JOIN (
    SELECT 
        MAX(timeStamp) as `time_off_sell`,
        modul_id
    FROM 
        cashModule
    WHERE
        NOT bs IS NULL
    GROUP BY
        modul_id             
) as `last_sell` ON last_sell.modul_id = modul.modul_id
-- ПРОСИГНАЛИЗИРУЕМ ЧТО У МОДУЛЯ ЕСТЬ НАКОПИВШИЕСЯ КОМАНДЫ
LEFT JOIN (
    SELECT
        MAX(command_id) as `id_comand`,
        modul_id
    FROM 
        commandToModule
    GROUP BY
        modul_id
) as `comand_to_module` ON comand_to_module.modul_id = modul.modul_id
-- ОШИБКИ МОДУЛЯ
LEFT JOIN (
    SELECT
        errorModule.error,
        errorDescription.description,
        errorModule.timeStamp as `time_error`,
        errorModule.modul_id
    FROM
        errorModule
    INNER JOIN 
        errorDescription ON errorDescription.error = errorModule.error 
    -- Нам необходимо только крайняя ошибка - находим макс. время
    INNER JOIN (
        SELECT 
            MAX(timeStamp) as `max_time`,
            modul_id
        FROM
            errorModule
        GROUP BY
            modul_id
        ) as `tbl_max_time` ON tbl_max_time.max_time = errorModule.timeStamp AND tbl_max_time.modul_id = errorModule.modul_id      
    LEFT JOIN (
        SELECT
            modul_id AS `modul_id`,
            MAX(timeStamp) AS `time_stm`
        FROM
            cashModule
        WHERE
            bond > 0
        GROUP BY
            modul_id
    ) AS `ttm` ON ttm.modul_id = errorModule.modul_id
            AND errorModule.timeStamp < ttm.time_stm
    WHERE
        ttm.modul_id IS NULL
        AND errorModule.error <> 0
) AS `tmp_error` ON tmp_error.modul_id = modul.modul_id

-- обязательное условие выборки модулей
WHERE
    company.company_id = 1 
    AND company.company_id = modul.company_id   
 
    
    
    
        
