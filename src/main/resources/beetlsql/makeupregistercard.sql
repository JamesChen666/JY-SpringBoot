list
===
SELECT sm.*,
ss.RealName,ss.StudentNumber,
sl.RealName AS Applyer,
sl1.RealName AS Approvaler,
bd.DisplayText AS TypeCode
 FROM student_makeupregistercard sm
LEFT JOIN school_student ss ON sm.StudentId = ss.Id
LEFT JOIN sys_loginaccount sl  ON sm.UserId = sl.Id
LEFT JOIN sys_loginaccount sl1  ON sm.ExamineUserId = sl1.Id
LEFT JOIN (SELECT MemberValue,DisplayText FROM base_dictionary WHERE TypeCode = 'GQBBYYM') bd ON  sm.TypeCode= bd.MemberValue
findOne
===
SELECT * FROM student_makeupregistercard WHERE id = #{id}
auditMakeupRegisterCard
===
UPDATE student_makeupregistercard
 SET Status = #{Status},ExamineUserId = #{ExamineUserId},ExamineDate = #{ExamineDate}
 WHERE FIND_IN_SET(Id,#{Ids})
