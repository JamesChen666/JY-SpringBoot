list
===
SELECT * FROM sys_config

findOne
===
SELECT * FROM sys_config WHERE id = #{id}

findKey
===
SELECT * from sys_config sc WHERE sc.ParameterKey LIKE "%"#{key}"%" LIMIT 0,1;