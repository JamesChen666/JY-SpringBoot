list
===
SELECT * FROM Student_Resume_Post

findOne
===
SELECT * FROM Student_Resume_Post WHERE id = #{id}

isDelivery
===
select * from Student_Resume_Post where StudentId = #{studentId} and PostionId = #{postionId}

deliveryList
===
SELECT cp.Title,sl.RealName,(CASE
 WHEN ss.Sex = 1 THEN '男'
 WHEN ss.Sex = 0 THEN '女'
 END) as sex,sc.ClassName,ssy.SpecialtyName,sf.FacultyName,srp.CreateDate,sr.FileUrl,sr.SelfVideoUrl
from Student_Resume_Post srp
LEFT JOIN Corp_Postion cp ON srp.PostionId = cp.Id
LEFT JOIN School_Student ss ON srp.StudentId = ss.Id
LEFT JOIN Sys_LoginAccount sl ON sl.UserName = ss.StudentNumber
LEFT JOIN School_Class sc ON sc.ClassNo = ss.ClassNumber
LEFT JOIN School_Specialty ssy ON ssy.SpecialtyCode = sc.SpecialtyCode
LEFT JOIN School_Faculty sf ON ssy.FacultyCode = sf.FacultyCode
LEFT JOIN Student_Resume sr ON sr.StudentId = ss.Id

resumeCount
===
SELECT COUNT(*) from Student_Resume_Post srp
LEFT JOIN Corp_Postion cp ON srp.PostionId = cp.Id
WHERE cp.CorpId = #{corpId}
ytjl
===
SELECT
  srp.StudentId,sc.CorpName,
  cp.Title,cp.Functions,
  cp.WorkAddress,cp.PeopleCount,
  cp.Salary,cp.Contactor,cp.ContactPhone
FROM student_resume_post srp
LEFT JOIN corp_postion cp ON srp.PostionId = cp.Id
LEFT JOIN school_corp sc ON cp.CorpId =sc.Id
WHERE cp.Status=1 AND srp.StudentId = #{StudentId}
