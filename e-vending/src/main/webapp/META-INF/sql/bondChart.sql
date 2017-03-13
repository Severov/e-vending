SELECT 
			cast(bond as UNSIGNED) as `bond`, 
			UNIX_TIMESTAMP(time_stamp) * 1000 as `time_stamp`
		FROM(
				(
					SELECT 
						cashModule.bond as`bond`,
						cashModule.timeStamp as `time_stamp`,
						cashModule.modul_id as `uin`
					FROM 
						cashModule
					WHERE
						timeStamp BETWEEN ADDDATE(now(), INTERVAL -3 DAY) AND now()
						AND modul_id = :modul_id
						AND bond <> 0
				)
				UNION ALL
				(
					SELECT
						'100' as `bond`,
						cashNotReception.timeStamp, 
						cashNotReception.modul_id
					FROM 
						cashNotReception
					WHERE
						timeStamp BETWEEN ADDDATE(now(), INTERVAL -3 DAY) AND now()
						AND modul_id = :modul_id
				)
			) as `main_tmp`