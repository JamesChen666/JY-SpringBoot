list
===
SELECT * FROM sys_role_button

findOne
===
SELECT * FROM sys_role_button where id = #{id}

deleteByRoleId
===
DELETE FROM sys_role_button WHERE RoleId=#{RoleId}

findByRoleId
===
SELECT * FROM sys_role_button WHERE RoleId = #{RoleId}
