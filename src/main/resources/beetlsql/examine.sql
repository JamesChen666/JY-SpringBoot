list
===
SELECT se.*,ss.RealName,ss.StudentNumber,
sf.CnTitle,sl.RealName AS operater,
sl1.RealName AS ApprovalUserId
FROM student_examine se
LEFT JOIN school_student ss ON se.StudentId = ss.Id
LEFT JOIN student_field sf ON se.FieldId = sf.Id
LEFT JOIN sys_loginaccount sl ON se.UserId = sl.Id
LEFT JOIN sys_loginaccount sl1 ON se.ApprovalUserId = sl1.Id
studentList
===
SELECT se.*,ss.RealName,ss.StudentNumber,
sf.CnTitle,sl.RealName AS operater,
sl1.RealName AS ApprovalUserId
FROM student_examine se
LEFT JOIN school_student ss ON se.StudentId = ss.Id
LEFT JOIN student_field sf ON se.FieldId = sf.Id
LEFT JOIN sys_loginaccount sl ON se.UserId = sl.Id
LEFT JOIN sys_loginaccount sl1 ON se.ApprovalUserId = sl1.Id
WHERE StudentNumber = #{StudentNumber}

findOne
===
SELECT se.*,ss.RealName,ss.StudentNumber,
       sf.CnTitle,sl.RealName AS operater,
       sl1.RealName AS ApprovalUserId
FROM student_examine se
       LEFT JOIN school_student ss ON se.StudentId = ss.Id
       LEFT JOIN student_field sf ON se.FieldId = sf.Id
       LEFT JOIN sys_loginaccount sl ON se.UserId = sl.Id
       LEFT JOIN sys_loginaccount sl1 ON se.ApprovalUserId = sl1.Id
WHERE se.Id = #{Id}


