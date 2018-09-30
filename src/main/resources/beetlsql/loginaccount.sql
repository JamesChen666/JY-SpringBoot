list
===
SELECT sl.*,sr.RoleName,
 (CASE
  WHEN sl.UserTypeId=1
  THEN '管理员'
  WHEN sl.UserTypeId=2
  THEN '教师'
  WHEN sl.UserTypeId=3
  THEN '学生'
  ELSE '单位'
  END
 ) AS UserTypeName
 FROM sys_loginaccount sl
LEFT JOIN sys_loginaccount_role slr ON sl.Id = slr.UserId
LEFT JOIN sys_role sr ON sr.Id = slr.RoleId

findOne
===
SELECT sl.*,sr.Id AS RoleId FROM sys_loginaccount sl
LEFT JOIN sys_loginaccount_role slr ON sl.Id = slr.UserId
LEFT JOIN sys_role sr ON sr.Id = slr.RoleId
WHERE sl.id = #{id}

findByUserName
===
SELECT * FROM sys_loginaccount WHERE UserName=#{UserName}
