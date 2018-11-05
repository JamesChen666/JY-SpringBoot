list
===
SELECT sc.*,sl.RealName FROM School_Corp sc left join sys_loginaccount sl on sc.ExamineUserId = sl.Id

findOne
===
SELECT sc.*,sl.RealName,sl.PassWord,sl.UserName,sl.Id as loginId FROM School_Corp sc left join sys_loginaccount sl on sc.UserId = sl.Id
  WHERE sc.id = #{id}

findPostion
===
SELECT * from  corp_postion WHERE CorpId = #{corpId}

findCorp
===
select
sc.Id,sc.CorpName,sc.OrganizetionCode,
sc.Description,sc.Logo,
sc.Website,sc.Address,
bd.DisplayText as EconomicTypeCode,
bd1.DisplayText as NatureCdoe,
bd2.DisplayText as IndustryCode,
bd3.DisplayText as ScaleCode,
bd4.DisplayText as CapitalCode,
(CASE
 WHEN ba3.AreaName IS NULL AND ba3.AreaName IS NULL AND ba2.AreaName IS NULL
   THEN CONCAT(ba1.AreaName)
 WHEN ba4.AreaName IS NULL AND ba3.AreaName IS NULL
   THEN CONCAT(ba2.AreaName,ba1.AreaName)
 WHEN ba4.AreaName IS NULL
   THEN CONCAT(ba3.AreaName,ba2.AreaName,ba1.AreaName)
 ELSE CONCAT(ba4.AreaName,ba3.AreaName,ba2.AreaName,ba1.AreaName)
 END
 ) as OrginCode
FROM School_Corp sc
LEFT JOIN base_area ba1 ON sc.OrginCode = ba1.AreaCode
LEFT JOIN base_area ba2 ON ba1.ParentId = ba2.Id
LEFT JOIN base_area ba3 ON ba2.ParentId = ba3.Id
LEFT JOIN base_area ba4 ON ba3.ParentId = ba4.Id
LEFT JOIN (SELECT * from Base_Dictionary WHERE TypeCode = 'DWJJLXDM') bd ON bd.MemberValue = sc.EconomicTypeCode
LEFT JOIN (SELECT * from Base_Dictionary WHERE TypeCode = 'DWXZDM') bd1 ON bd1.MemberValue = sc.NatureCdoe
LEFT JOIN (SELECT * from Base_Dictionary WHERE TypeCode = 'HYFLDM') bd2 ON bd2.MemberValue = sc.IndustryCode
LEFT JOIN (SELECT * from Base_Dictionary WHERE TypeCode = 'QYGMDM') bd3 ON bd3.MemberValue = sc.ScaleCode
LEFT JOIN (SELECT * from Base_Dictionary WHERE TypeCode = 'ZCZJDM') bd4 ON bd4.MemberValue = sc.CapitalCode
WHERE sc.Id = #{id}

findCorpName
===
select * from School_Corp where CorpName = #{corpName}
findZxlx
===
SELECT
cr.Id,sc.CorpName,sc.Email,
sc.Contactor,sc.ContactPhone,
sc.OrganizetionCode,
sc.Description,sc.Logo,
sc.Website,sc.Address,
bd.DisplayText as EconomicTypeCode,
bd1.DisplayText as NatureCdoe,
bd2.DisplayText as IndustryCode,
bd3.DisplayText as ScaleCode,
bd4.DisplayText as CapitalCode,
ba.AreaName as OrginCode
FROM corp_recruit cr
     LEFT JOIN School_Corp sc ON cr.CorpId = sc.Id
     LEFT JOIN base_area ba ON sc.OrginCode = ba.AreaCode
     LEFT JOIN (SELECT * from Base_Dictionary WHERE TypeCode = 'DWJJLXDM') bd ON bd.MemberValue = sc.EconomicTypeCode
     LEFT JOIN (SELECT * from Base_Dictionary WHERE TypeCode = 'DWXZDM') bd1 ON bd1.MemberValue = sc.NatureCdoe
     LEFT JOIN (SELECT * from Base_Dictionary WHERE TypeCode = 'HYFLDM') bd2 ON bd2.MemberValue = sc.IndustryCode
     LEFT JOIN (SELECT * from Base_Dictionary WHERE TypeCode = 'QYGMDM') bd3 ON bd3.MemberValue = sc.ScaleCode
     LEFT JOIN (SELECT * from Base_Dictionary WHERE TypeCode = 'ZCZJDM') bd4 ON bd4.MemberValue = sc.CapitalCode
WHERE cr.TypeId = 1

