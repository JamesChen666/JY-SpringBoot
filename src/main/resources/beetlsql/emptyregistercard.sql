list
===
SELECT
se.*,ss.RealName,ss.StudentNumber,
 sl.RealName AS Operator,
 (
  CASE WHEN se.TypeId = 1
  THEN '改签'
  ELSE '补办'
  END
 ) AS TypeId
FROM student_emptyregistercard se
LEFT JOIN school_student ss  ON  se.StudentId = ss.Id
LEFT JOIN sys_loginaccount sl ON se.UserId = sl.Id

findOne
===
SELECT * FROM student_emptyregistercard WHERE id = #{id}
