list
===
SELECT sm.*,sm2.MenuName AS parentName FROM sys_menu sm
LEFT JOIN sys_menu sm2 ON sm.ParentId=sm2.Id

findOne
===
SELECT * FROM sys_menu WHERE id = #{id}
