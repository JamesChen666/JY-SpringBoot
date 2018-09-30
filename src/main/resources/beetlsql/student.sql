list
===
SELECT ss.*,
na.DisplayText as NationCode,
po.DisplayText as PoliticalCode,
ed.DisplayText as LevelCode,
cu.DisplayText as CultureCode,
nor.DisplayText as NormalCode,
di.DisplayText as DifficultCode,
ur.DisplayText as UrbanOrRuralCode,
su.DisplayText as SubsistenceCode,
ma.DisplayText as MajorLanguageCode,
sc.ClassName AS ClassNumber,
ssp.SpecialtyName AS SpecialtyCode,
(CASE
WHEN ba3.AreaName IS NULL AND ba3.AreaName IS NULL AND ba2.AreaName IS NULL
THEN CONCAT(ba1.AreaName)
WHEN ba4.AreaName IS NULL AND ba3.AreaName IS NULL
THEN CONCAT(ba2.AreaName,ba1.AreaName)
WHEN ba4.AreaName IS NULL
THEN CONCAT(ba3.AreaName,ba2.AreaName,ba1.AreaName)
ELSE CONCAT(ba4.AreaName,ba3.AreaName,ba2.AreaName,ba1.AreaName)
END
)  as OriginCode
 FROM school_student ss
LEFT JOIN (SELECT MemberValue,DisplayText FROM base_dictionary WHERE TypeCode = 'MZDM') na ON ss.NationCode = na.MemberValue
LEFT JOIN (SELECT MemberValue,DisplayText FROM base_dictionary WHERE TypeCode = 'ZZMMDM') po ON ss.PoliticalCode = po.MemberValue
LEFT JOIN (SELECT MemberValue,DisplayText FROM base_dictionary WHERE TypeCode = 'XLDM') ed ON ss.LevelCode = ed.MemberValue
LEFT JOIN (SELECT MemberValue,DisplayText FROM base_dictionary WHERE TypeCode = 'JYPYFSDM') cu ON ss.CultureCode = cu.MemberValue
LEFT JOIN (SELECT MemberValue,DisplayText FROM base_dictionary WHERE TypeCode = 'ZXWYYZDM') ma ON ss.MajorLanguageCode = ma.MemberValue
LEFT JOIN (SELECT MemberValue,DisplayText FROM base_dictionary WHERE TypeCode = 'JYSFSLBDM') nor ON ss.NormalCode = nor.MemberValue
LEFT JOIN (SELECT MemberValue,DisplayText FROM base_dictionary WHERE TypeCode = 'JYKNLXDM') di ON ss.DifficultCode = di.MemberValue
LEFT JOIN (SELECT MemberValue,DisplayText FROM base_dictionary WHERE TypeCode = 'CXJMDBJTLXDM') su ON ss.SubsistenceCode = su.MemberValue
LEFT JOIN (SELECT MemberValue,DisplayText FROM base_dictionary WHERE TypeCode = 'CXSYDM') ur ON ss.UrbanOrRuralCode = ur.MemberValue
LEFT JOIN school_class sc ON ss.ClassNumber = sc.ClassNo
LEFT JOIN school_specialty ssp ON ss.SpecialtyCode = ssp.SpecialtyCode
LEFT JOIN base_area ba1 ON ss.OriginCode = ba1.AreaCode
LEFT JOIN base_area ba2 ON ba1.ParentId = ba2.Id
LEFT JOIN base_area ba3 ON ba2.ParentId = ba3.Id
LEFT JOIN base_area ba4 ON ba3.ParentId = ba4.Id

findOne
===
SELECT * FROM school_student WHERE id = #{id}

gridList
===
SELECT ss.Id,ss.RealName,ss.StudentNumber,
sc.ClassName AS ClassName
FROM school_student ss
LEFT JOIN school_class sc ON ss.ClassNumber = sc.ClassNo

findSimpleStudentMessage
===
SELECT
sst.Id,sst.RealName,sst.StudentNumber,
sst.IdCard,sst.ExamineNumber,sc.ClassName AS bj,
po.DisplayText AS zzmm,ed.DisplayText AS xl,
cu.DisplayText AS pyfs,nor.DisplayText AS sfslb,
(CASE
WHEN  ba13.AreaName IS NULL AND ba12.AreaName IS NULL
THEN CONCAT(ba11.AreaName)
WHEN ba14.AreaName IS NULL AND ba13.AreaName IS NULL
THEN CONCAT(ba12.AreaName,ba11.AreaName)
WHEN ba14.AreaName IS NULL
THEN CONCAT(ba13.AreaName,ba12.AreaName,ba11.AreaName)
ELSE CONCAT(ba14.AreaName,ba13.AreaName,ba12.AreaName,ba11.AreaName)
END
)  as OriginCode
FROM school_student sst
LEFT JOIN (SELECT MemberValue,DisplayText FROM base_dictionary WHERE TypeCode = 'ZZMMDM') po ON sst.PoliticalCode = po.MemberValue
LEFT JOIN (SELECT MemberValue,DisplayText FROM base_dictionary WHERE TypeCode = 'XLDM') ed ON sst.LevelCode = ed.MemberValue
LEFT JOIN (SELECT MemberValue,DisplayText FROM base_dictionary WHERE TypeCode = 'JYPYFSDM') cu ON sst.CultureCode = cu.MemberValue
LEFT JOIN (SELECT MemberValue,DisplayText FROM base_dictionary WHERE TypeCode = 'JYSFSLBDM') nor ON sst.NormalCode = nor.MemberValue
LEFT JOIN school_class sc ON sst.ClassNumber = sc.ClassNo
LEFT JOIN base_area ba11 ON sst.OriginCode = ba11.AreaCode
LEFT JOIN base_area ba12 ON ba11.ParentId = ba12.Id
LEFT JOIN base_area ba13 ON ba12.ParentId = ba13.Id
LEFT JOIN base_area ba14 ON ba13.ParentId = ba14.Id
WHERE sst.Id = #{Id}

classToCFS
===
SELECT
sc1.CampusCode,sc1.CampusName,
sf.FacultyCode,sf.FacultyName,
ss.SpecialtyCode,ss.SpecialtyName,
sc.ClassNo,sc.ClassName
FROM school_class sc
LEFT JOIN school_specialty ss ON sc.SpecialtyCode = ss.SpecialtyCode
LEFT JOIN school_faculty sf ON ss.FacultyCode = sf.FacultyCode
LEFT JOIN school_campus sc1 ON sf.CampusCode = sc1.CampusCode
WHERE sc.ClassNo = #{ClassNo}
findSignView
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
ss.ForwardCorpPostCode,ss.Remark,
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
FROM student_sign ss
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
WHERE ss.StudentId = #{StudentId}
findWork
===
SELECT
ss.ArchiveContactor,ss.ArchiveContactPhone,ss.ArchiveCorp,
ss.ContactorPostion,ss.CorpAddress,ss.CorpContactEmail,
ss.CorpContactFax,ss.CorpContactor,ss.CorpContactPhone,
ss.CorpIndustryCode,ss.CorporationName,ss.CorpOrganizationCode,
ss.CorpOrginCode,ss.CorpPostCode,ss.ForwardCorpName,ss.CorpNatureCdoe,
ss.ForwardCorpPostCode,ss.GraduationWhereAboutCode,ss.HighGradeTypeCode,
ss.IsLock,ss.JobTypeCode,ss.Remark,ss.UnderDepartmentCode,
ss.CorpOrginCode
FROM school_student sst
LEFT JOIN student_sign ss ON sst.Id = ss.StudentId
WHERE ss.GraduationWhereAboutCode!=10 AND sst.Id = #{Id}

findSign
===
SELECT
ss.ArchiveContactor,ss.ArchiveContactPhone,ss.ArchiveCorp,
ss.ContactorPostion,ss.CorpAddress,ss.CorpContactEmail,
ss.CorpContactFax,ss.CorpContactor,ss.CorpContactPhone,
ss.CorpIndustryCode,ss.CorporationName,ss.CorpOrganizationCode,
ss.CorpOrginCode,ss.CorpPostCode,ss.ForwardCorpName,ss.CorpNatureCdoe,
ss.ForwardCorpPostCode,ss.GraduationWhereAboutCode,ss.HighGradeTypeCode,
ss.IsLock,ss.JobTypeCode,ss.Remark,ss.UnderDepartmentCode,
ss.CorpOrginCode
FROM school_student sst
LEFT JOIN student_sign ss ON sst.Id = ss.StudentId
WHERE ss.GraduationWhereAboutCode=10 AND sst.Id = #{Id}
