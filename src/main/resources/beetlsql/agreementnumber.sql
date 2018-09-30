list
===
SELECT sa.*,
ss.RealName,ss.StudentNumber,
sl.RealName AS Operator
FROM student_agreementnumber sa
LEFT JOIN school_student ss ON sa.StudentId = ss.Id
LEFT JOIN sys_loginaccount sl  ON sa.UserId = sl.Id

findOne
===
SELECT * FROM student_agreementnumber WHERE id = #{id}
