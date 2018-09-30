list
===
SELECT * FROM sys_loginaccount_role

findByUserId
===
SELECT * FROM sys_loginaccount_role where UserId = #{UserId}

updateByUserId
===
UPDATE sys_loginaccount_role SET RoleId = #{RoleId} where UserId = #{UserId}

deleteByUserId
===
DELETE FROM sys_loginaccount_role WHERE UserId = #{UserId}
