list
===
SELECT sf.*,ss.RealName,ss.StudentNumber FROM student_feedback sf
LEFT JOIN school_student ss ON sf.StudentId = ss.Id
myList
===
SELECT sf.*,ss.RealName,ss.StudentNumber FROM student_feedback sf
LEFT JOIN school_student ss ON sf.StudentId = ss.Id
WHERE StudentNumber = #{StudentNumber} ORDER BY CreateDate DESC
searchList
===
SELECT sf.*,ss.RealName,ss.StudentNumber FROM student_feedback sf
LEFT JOIN school_student ss ON sf.StudentId = ss.Id
WHERE StudentNumber = #{StudentNumber}
AND sf.Title LIKE  #{'%'+search+'%'} OR sf.Content LIKE #{'%'+search+'%'}
OR sf.ReplyContent LIKE #{'%'+search+'%'}
ORDER BY CreateDate DESC

findOne
===
SELECT * FROM student_feedback WHERE id = #{id}
