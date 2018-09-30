list
===
SELECT sl.*,sl2.RealName AS UserName FROM sys_log sl
LEFT JOIN sys_loginaccount sl2 ON sl.UserId = sl2.Id

findOne
===
SELECT * FROM sys_log WHERE id = #{id}
