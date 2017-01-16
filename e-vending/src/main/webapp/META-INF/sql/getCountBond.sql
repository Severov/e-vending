SELECT
		bs as `bs`
	FROM
		cashModule
	WHERE
        modul_id = :modul_id
		AND bs IS NOT NULL
	ORDER BY 
		timeStamp DESC
	LIMIT 
		1