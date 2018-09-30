list
===
SELECT sr.*,
(CASE
 WHEN sr.TypeCode = 1 THEN '升学'
 ELSE '复学'
 END
) AS TypeName,
ss.RealName,
ss.StudentNumber,
sl.RealName AS operater
FROM student_roll sr
LEFT JOIN school_student ss ON sr.StudentId = ss.Id
LEFT JOIN sys_loginaccount sl ON sr.UserId = sl.Id

findOne
===
SELECT * FROM student_roll WHERE id = #{id}
