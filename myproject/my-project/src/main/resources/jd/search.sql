SELECT
  COLUMN_NAME 字段,
  COLUMN_TYPE 数据类型,
  CHARACTER_MAXIMUM_LENGTH 长度,
	(
		CASE
		WHEN column_key = 'PRI' THEN
			'√'
		ELSE
			''
		END
	) 主键,
	COLUMN_KEY,
	(
		CASE
		WHEN column_key = 'PRI' THEN
			'主键索引'
		WHEN column_key = 'UNI' THEN
			'唯一索引'
		WHEN column_key = 'MUL' THEN
			'可重复索引'
		ELSE
			''
		END
	) 索引,
  COLUMN_COMMENT 含义
  FROM
  INFORMATION_SCHEMA.COLUMNS
  WHERE
  table_schema = 'jd'  -- xxxxx 为数据库名称
  AND
  -- 如果不写的话，默认会查询出所有表中的数据，这样可能就分不清到底哪些字段是哪张表中的了，所以还是建议写上要导出的表名称
  table_name = 'user' -- xxxx 为表名
