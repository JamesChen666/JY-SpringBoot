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
