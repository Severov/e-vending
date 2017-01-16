SELECT
		MAX(IFNULL(cash, 0)) AS `max_cash`
	FROM
		cashModule
	INNER JOIN (
		SELECT
			MAX(timeStamp) AS `t1`, 
			modul_id
		FROM
			collectionModule
		GROUP BY
			modul_id
	) AS `tmp` ON cashModule.modul_id = tmp.modul_id AND tmp.t1 <= timeStamp
    WHERE 
		cashModule.modul_id = :modul_id
	GROUP BY
		cashModule.modul_id
	LIMIT 1