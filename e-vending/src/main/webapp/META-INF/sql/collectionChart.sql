SELECT 
		UNIX_TIMESTAMP(timeStamp) * 1000 as `time_stamp`,
		CAST(plan as UNSIGNED) as `plan`,
		CAST(fakt as UNSIGNED) as `fakt`
	FROM
		collectionModule 
	WHERE
		timeStamp BETWEEN ADDDATE(now(), INTERVAL -45 DAY) AND now()
		AND modul_id = :modul_id
		AND ( plan > 0 OR fakt > 0 )
	ORDER BY 
		collection_id