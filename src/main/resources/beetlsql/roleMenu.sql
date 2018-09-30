list
===
SELECT * FROM sys_role_menu

findOne
===
SELECT * FROM sys_role_menu where id = #{id}
deleteByMenuId
===
DELETE FROM sys_role_menu WHERE MenuId=#{MenuId}
deleteByRoleId
===
DELETE FROM sys_role_menu WHERE RoleId=#{RoleId}
findByRoleId
===
SELECT * FROM sys_role_menu WHERE RoleId = #{RoleId}

