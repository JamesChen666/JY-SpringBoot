list
===
SELECT sc.Id,sc.CorporationName,
sc.Reason,sc.ArchiveCorp,
sc.CreateDate,sc.Status,
ss.RealName,ss.StudentNumber,
bd.DisplayText AS TypeCode
FROM student_changesign sc
LEFT JOIN school_student ss ON sc.StudentId = ss.Id
LEFT JOIN (SELECT MemberValue,DisplayText FROM base_dictionary WHERE TypeCode = 'GQBBYYM') bd ON  sc.TypeCode= bd.MemberValue

findOne
===
SELECT
bd.DisplayText AS TypeCode,
bd1.DisplayText AS GraduationWhereAboutCode,
bd2.DisplayText AS HighGradeTypeCode,
bd3.DisplayText AS CorpNatureCdoe,
bd4.DisplayText AS CorpIndustryCode,
bd5.DisplayText AS UnderDepartmentCode,
bd6.DisplayText AS JobTypeCode,
sc.Reason,sc.CorporationName,sc.CorpOrganizationCode,
sc.CorpContactor,sc.ContactorPostion,sc.CorpContactPhone,
sc.CorpContactEmail,sc.CorpContactFax,sc.CorpAddress,
sc.CorpPostCode,sc.ArchiveCorp,sc.ArchiveContactor,
sc.ArchiveContactPhone,sc.ForwardCorpName,
sc.ForwardCorpPostCode,sc.Remark,sc.StudentId,
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
FROM student_changesign sc
LEFT JOIN (SELECT MemberValue,DisplayText FROM base_dictionary WHERE TypeCode = 'GQBBYYM') bd ON  sc.TypeCode= bd.MemberValue
LEFT JOIN (SELECT MemberValue,DisplayText FROM base_dictionary WHERE TypeCode = 'JYBYQXDM') bd1 ON  sc.GraduationWhereAboutCode= bd1.MemberValue
LEFT JOIN (SELECT MemberValue,DisplayText FROM base_dictionary WHERE TypeCode = 'SXLXDM') bd2 ON  sc.HighGradeTypeCode= bd2.MemberValue
LEFT JOIN (SELECT MemberValue,DisplayText FROM base_dictionary WHERE TypeCode = 'DWXZDM') bd3 ON  sc.CorpNatureCdoe= bd3.MemberValue
LEFT JOIN (SELECT MemberValue,DisplayText FROM base_dictionary WHERE TypeCode = 'HYFLDM') bd4 ON  sc.CorpIndustryCode= bd4.MemberValue
LEFT JOIN (SELECT MemberValue,DisplayText FROM base_dictionary WHERE TypeCode = 'DWLSBMDM') bd5 ON  sc.UnderDepartmentCode= bd5.MemberValue
LEFT JOIN (SELECT MemberValue,DisplayText FROM base_dictionary WHERE TypeCode = 'ZWLBDM') bd6 ON  sc.JobTypeCode= bd6.MemberValue
LEFT JOIN base_area ba1 ON sc.CorpOrginCode = ba1.AreaCode
LEFT JOIN base_area ba2 ON ba1.ParentId = ba2.Id
LEFT JOIN base_area ba3 ON ba2.ParentId = ba3.Id
LEFT JOIN base_area ba4 ON ba3.ParentId = ba4.Id
WHERE sc.Id = #{Id}
