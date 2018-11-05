list
===
SELECT sm.Id,sm.ParentId,sm.MenuCode,sm.MenuName,sm2.MenuName AS parentName FROM sys_menu sm
LEFT JOIN sys_menu sm2 ON sm.ParentId=sm2.Id

treeList
===
SELECT sm.Id,sm.ParentId as pid,sm.MenuCode,sm.MenuName as text,sm2.MenuName AS parentName FROM sys_menu sm
LEFT JOIN sys_menu sm2 ON sm.ParentId=sm2.Id
UNION
SELECT CONCAT('but_',smb.Id) as Id,sm.Id as pid,smb.ButtonCode,smb.ButtonName as text,sm.MenuName AS parentName FROM Sys_Menu_Button smb
LEFT JOIN sys_menu sm  ON smb.MenuId = sm.Id

/*SELECT sm.Id,sm.ParentId as pid,sm.MenuCode,sm.MenuName as text,sm2.MenuName AS parentName FROM sys_menu sm
LEFT JOIN sys_menu sm2 ON sm.ParentId=sm2.Id*/

findOne
===
SELECT * FROM sys_menu WHERE id = #{id}

findTest
===
SELECT sy.Id FROM sys_menu sy WHERE sy.ParentId != 0
