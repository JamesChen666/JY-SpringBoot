list
===
SELECT * FROM student_complaint

myList
===
SELECT sc.*,
 sc2.CorpName,cp.Title AS jobName,
 sl.RealName AS auditUserName,
 bd.DisplayText AS typeName,
 (CASE
    WHEN sc.Status=0
    THEN '待审核'
    WHEN sc.Status=1
    THEN '审核通过'
    ELSE '审核拒绝'
    END
 ) AS statusName
 FROM student_complaint sc
LEFT JOIN school_corp sc2 ON sc.CompanyId = sc2.Id
LEFT JOIN corp_postion cp ON sc.JobId = cp.Id
LEFT JOIN sys_loginaccount sl ON sc.AuditUserId = sl.Id
LEFT JOIN (SELECT MemberValue,DisplayText FROM base_dictionary WHERE TypeCode = 'DWXZDM') bd ON  sc.TypeCode= bd.MemberValue
searchList
===
SELECT sc.*,
 sc2.CorpName,cp.Title AS jobName,
 sl.RealName AS auditUserName,
 bd.DisplayText AS typeName,
 (CASE
    WHEN sc.Status=0
    THEN '待审核'
    WHEN sc.Status=1
    THEN '审核通过'
    ELSE '审核拒绝'
    END
 ) AS statusName
 FROM student_complaint sc
LEFT JOIN school_corp sc2 ON sc.CompanyId = sc2.Id
LEFT JOIN corp_postion cp ON sc.JobId = cp.Id
LEFT JOIN sys_loginaccount sl ON sc.AuditUserId = sl.Id
LEFT JOIN (SELECT MemberValue,DisplayText FROM base_dictionary WHERE TypeCode = 'DWXZDM') bd ON  sc.TypeCode= bd.MemberValue
WHERE CorpName LIKE #{'%'+search+'%'} OR cp.Title  LIKE #{'%'+search+'%'}
OR bd.DisplayText LIKE  #{'%'+search+'%'} OR sc.Reason  LIKE #{'%'+search+'%'} OR sc.AuditReamrks  LIKE #{'%'+search+'%'}

findOne
===
SELECT * FROM student_complaint WHERE id = #{id}
