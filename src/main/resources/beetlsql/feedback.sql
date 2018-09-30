list
===
SELECT sf.*,ss.RealName,ss.StudentNumber FROM student_feedback sf
LEFT JOIN school_student ss ON sf.StudentId = ss.Id

findOne
===
SELECT * FROM student_feedback WHERE id = #{id}
