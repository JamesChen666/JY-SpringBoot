list
===
SELECT sr.*,ss.RealName,ss.StudentNumber FROM student_resume sr
LEFT JOIN school_student ss ON sr.StudentId = ss.Id

findOne
===
SELECT * FROM student_resume WHERE id = #{id}
