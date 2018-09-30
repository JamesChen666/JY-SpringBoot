list
===
SELECT sd.*,
ss.RealName,ss.StudentNumber,sl.RealName AS Operator,
bd.DisplayText AS IssueTypeCode,
(CASE
WHEN ba3.AreaName IS NULL AND ba3.AreaName IS NULL AND ba2.AreaName IS NULL
THEN CONCAT(ba1.AreaName)
WHEN ba4.AreaName IS NULL AND ba3.AreaName IS NULL
THEN CONCAT(ba2.AreaName,ba1.AreaName)
WHEN ba4.AreaName IS NULL
THEN CONCAT(ba3.AreaName,ba2.AreaName,ba1.AreaName)
ELSE CONCAT(ba4.AreaName,ba3.AreaName,ba2.AreaName,ba1.AreaName)
END
) AS OriginCode
FROM student_dispatch_log sd
LEFT JOIN school_student ss ON sd.StudentId = ss.Id
LEFT JOIN sys_loginaccount sl ON sd.UserId = sl.Id
LEFT JOIN (SELECT MemberValue,DisplayText FROM base_dictionary WHERE TypeCode = 'JYBDZQFLBDM') bd ON  sd.IssueTypeCode= bd.MemberValue
LEFT JOIN base_area ba1 ON sd.OriginCode = ba1.AreaCode
LEFT JOIN base_area ba2 ON ba1.ParentId = ba2.Id
LEFT JOIN base_area ba3 ON ba2.ParentId = ba3.Id
LEFT JOIN base_area ba4 ON ba3.ParentId = ba4.Id

findOne
===
SELECT * FROM student_dispatch_log WHERE id = #{id}
