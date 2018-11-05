list
===
SELECT * FROM sys_role
findOne
===
SELECT * FROM sys_role where id = #{id}
findPrentId
===
SELECT DISTINCT ParentId FROM sys_menu WHERE FIND_IN_SET(Id,#{Ids})
findPermissionDataIds
===
SELECT GROUP_CONCAT(Id) FROM sys_menu WHERE FIND_IN_SET(Id,#{Ids}) AND ParentId!=0

findButtonDataIds
===
SELECT GROUP_CONCAT(CONCAT('but_',Id)) FROM Sys_Menu_Button WHERE FIND_IN_SET(Id,#{Ids})

findRole
===
SELECT srm.* from Sys_Role_Menu srm
LEFT JOIN Sys_Role sr ON sr.Id = srm.RoleId
UNION
SELECT srb.RoleId,CONCAT('but_',srb.ButtonId)  from Sys_Role_Button srb
LEFT JOIN Sys_Role sr  ON sr.Id = srb.RoleId
