findRoleByUserName
===
SELECT sr.* FROM sys_role sr
LEFT JOIN sys_loginaccount_role slr ON sr.Id = slr.RoleId
LEFT JOIN sys_loginaccount sl ON sl.Id = slr.UserId
WHERE sl.UserName = #{UserName}
findUserByUserName
===
SELECT * FROM sys_loginaccount WHERE UserName=#{UserName}

findUserMenu
===
SELECT sm.* FROM sys_menu sm
LEFT JOIN sys_role_menu srm ON sm.Id = srm.MenuId
LEFT JOIN sys_loginaccount_role slr ON slr.RoleId = srm.RoleId
LEFT JOIN sys_loginaccount sl ON slr.UserId = sl.Id
WHERE sm.IsEnabled = 1 AND sl.UserName = #{UserName}
ORDER BY Sort



