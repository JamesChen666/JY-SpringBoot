list
===
SELECT
sr.Id,sr.AllotDate,
sr.Number,ss.RealName,
ss.StudentNumber,sl.RealName AS Operator
FROM student_registercardnumber sr
LEFT JOIN school_student ss  ON sr.StudentId = ss.Id
LEFT JOIN sys_loginaccount sl ON sr.UserId = sl.Id

findOne
===
SELECT * FROM student_registercardnumber WHERE id = #{id}
