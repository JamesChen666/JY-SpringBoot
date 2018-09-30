list
===
SELECT
sst.Id,ss.RealName,
ss.StudentNumber,
sst.CorporationName,
sst.CreateDate,sl.RealName AS UserId,
bd.DisplayText AS GraduationWhereAboutCode
FROM student_sign_log sst
LEFT JOIN school_student ss ON sst.StudentId = ss.Id
LEFT JOIN sys_loginaccount sl  ON sst.UserId = sl.Id
LEFT JOIN (SELECT MemberValue,DisplayText FROM base_dictionary WHERE TypeCode = 'JYBYQXDM') bd ON  sst.GraduationWhereAboutCode= bd.MemberValue

findOne
===
SELECT
bd1.DisplayText AS GraduationWhereAboutCode,
bd2.DisplayText AS HighGradeTypeCode,
bd3.DisplayText AS CorpNatureCdoe,
bd4.DisplayText AS CorpIndustryCode,
bd5.DisplayText AS UnderDepartmentCode,
bd6.DisplayText AS JobTypeCode,
ss.CorporationName,ss.CorpOrganizationCode,
ss.CorpContactor,ss.ContactorPostion,ss.CorpContactPhone,
ss.CorpContactEmail,ss.CorpContactFax,ss.CorpAddress,
ss.CorpPostCode,ss.ArchiveCorp,ss.ArchiveContactor,
ss.ArchiveContactPhone,ss.ForwardCorpName,
ss.ForwardCorpPostCode,ss.Remark,ss.CreateDate,
sl.RealName AS UserId,
(CASE
WHEN ba3.AreaName IS NULL AND ba3.AreaName IS NULL AND ba2.AreaName IS NULL
THEN CONCAT(ba1.AreaName)
WHEN ba4.AreaName IS NULL AND ba3.AreaName IS NULL
THEN CONCAT(ba2.AreaName,ba1.AreaName)
WHEN ba4.AreaName IS NULL
THEN CONCAT(ba3.AreaName,ba2.AreaName,ba1.AreaName)
ELSE CONCAT(ba4.AreaName,ba3.AreaName,ba2.AreaName,ba1.AreaName)
END
) AS CorpOrginCode
FROM student_sign_log ss
LEFT JOIN sys_loginaccount sl  ON ss.UserId = sl.Id
LEFT JOIN (SELECT MemberValue,DisplayText FROM base_dictionary WHERE TypeCode = 'JYBYQXDM') bd1 ON  ss.GraduationWhereAboutCode= bd1.MemberValue
LEFT JOIN (SELECT MemberValue,DisplayText FROM base_dictionary WHERE TypeCode = 'SXLXDM') bd2 ON  ss.HighGradeTypeCode= bd2.MemberValue
LEFT JOIN (SELECT MemberValue,DisplayText FROM base_dictionary WHERE TypeCode = 'DWXZDM') bd3 ON  ss.CorpNatureCdoe= bd3.MemberValue
LEFT JOIN (SELECT MemberValue,DisplayText FROM base_dictionary WHERE TypeCode = 'HYFLDM') bd4 ON  ss.CorpIndustryCode= bd4.MemberValue
LEFT JOIN (SELECT MemberValue,DisplayText FROM base_dictionary WHERE TypeCode = 'DWLSBMDM') bd5 ON  ss.UnderDepartmentCode= bd5.MemberValue
LEFT JOIN (SELECT MemberValue,DisplayText FROM base_dictionary WHERE TypeCode = 'ZWLBDM') bd6 ON  ss.JobTypeCode= bd6.MemberValue
LEFT JOIN base_area ba1 ON ss.CorpOrginCode = ba1.AreaCode
LEFT JOIN base_area ba2 ON ba1.ParentId = ba2.Id
LEFT JOIN base_area ba3 ON ba2.ParentId = ba3.Id
LEFT JOIN base_area ba4 ON ba3.ParentId = ba4.Id
WHERE ss.Id = #{Id}

