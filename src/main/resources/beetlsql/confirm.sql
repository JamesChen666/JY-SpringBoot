list
===
SELECT sc.*,ss.RealName,ss.StudentNumber,sl.RealName AS confirmUser FROM student_confirm sc
LEFT JOIN school_student ss ON sc.StudentId = ss.Id
LEFT JOIN sys_loginaccount sl ON sc.UserId = sl.Id

findOne
===
SELECT * FROM student_confirm WHERE id = #{id}
