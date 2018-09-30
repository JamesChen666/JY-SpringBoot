list
===
SELECT smb.*,sm.MenuName FROM sys_menu_button smb
LEFT JOIN sys_menu sm ON smb.MenuId = sm.Id

findOne
===
SELECT * FROM sys_menu_button WHERE id = #{id}
