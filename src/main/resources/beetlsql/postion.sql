list
===
SELECT cp.*,sl.RealName,sc.CorpName,bd1.DisplayText as IndustryCodeName,
bd2.DisplayText as TypeCodeName,bd3.DisplayText as LevelCodeName,
bd4.DisplayText as PositionTypeCodeName
FROM Corp_Postion cp
left join sys_loginaccount sl on cp.ExamineUserId = sl.Id
left join school_corp sc on sc.Id = cp.CorpId
left join (select * from base_dictionary where TypeCode = 'HYFLDM') bd1 on bd1.MemberValue = cp.IndustryCode
left join (select * from base_dictionary where TypeCode = 'ZWLBDM') bd2 on bd2.MemberValue = cp.typeCode
left join (select * from base_dictionary where TypeCode = 'JYXLDM') bd3 on bd3.MemberValue = cp.levelCode
left join (select * from base_dictionary where TypeCode = 'GWLXDM') bd4 on bd4.MemberValue = cp.positionTypeCode


findOne
===
SELECT cp.*,group_concat(cps.SpecialtyCode) as SpecialtyCode FROM Corp_Postion cp
LEFT JOIN Corp_PostionSpecialty cps ON cp.Id = cps.PositionId
WHERE cp.Id = #{id}
GROUP BY cp.Id

find
===
SELECT cp.*,sc.CorpName,sc.Description,bd.DisplayText as hy,bd1.DisplayText as gm,bd2.DisplayText as xl,ba.AreaName from Corp_Postion cp
LEFT JOIN school_corp sc ON sc.Id = cp.CorpId
LEFT JOIN Base_Area ba ON sc.OrginCode = ba.AreaCode
LEFT JOIN (SELECT * from Base_Dictionary WHERE TypeCode = 'HYFLDM') bd ON bd.MemberValue = sc.IndustryCode
LEFT JOIN (SELECT * from Base_Dictionary WHERE TypeCode = 'QYGMDM') bd1 ON bd1.MemberValue = sc.ScaleCode
LEFT JOIN (SELECT * from Base_Dictionary WHERE TypeCode = 'XLYQDM') bd2 ON bd2.MemberValue = cp.LevelCode
WHERE cp.Id = #{id}

/*查询自行联系单位列表信息*/
queryCorpList
===
SELECT cp.Title,cp.ExamineDate,sc.Id,sc.CorpName from (SELECT * from Corp_Postion WHERE Status=1)  cp
LEFT JOIN school_corp sc   ON sc.Id = cp.CorpId
LEFT JOIN Corp_Recruit cr ON cr.CorpId = sc.Id
WHERE cr.TypeId =1 AND sc.`Status` = 1 AND cr.`Status`=1
GROUP BY sc.Id

/*查询出自行联系职位列表*/
queryPostionList
===
SELECT cp.*,bd2.DisplayText,GROUP_CONCAT(ss.SpecialtyName) as SpecialtyName from Corp_Postion cp
LEFT JOIN (SELECT * from Base_Dictionary WHERE TypeCode = 'XLYQDM') bd2 ON bd2.MemberValue = cp.LevelCode
LEFT JOIN Corp_PostionSpecialty cps ON cps.PositionId = cp.Id
LEFT JOIN School_Specialty ss ON cps.SpecialtyCode = ss.SpecialtyCode
WHERE cp.`Status`=1 AND cp.CorpId = #{corpId}
GROUP BY cp.Id

query
===
SELECT sc.CorpName,cp.*,bd2.DisplayText FROM Corp_Postion cp
LEFT JOIN school_corp sc ON cp.CorpId = sc.Id
LEFT JOIN (SELECT * from Base_Dictionary WHERE TypeCode = 'XLYQDM') bd2 ON bd2.MemberValue = cp.LevelCode
LEFT JOIN Corp_Recruit cr ON cr.CorpId = sc.Id
WHERE cr.TypeId = 1 AND cp.`Status` = 1 AND sc.`Status`=1 AND cr.`Status`=1

lastOne
===
SELECT * from Corp_Postion  ORDER BY Id DESC LIMIT 0,1

/*查询出某单位的职位列表*/
findCorpPostionList
===
SELECT cp.*,lx.DisplayText from Corp_Postion cp
LEFT JOIN (SELECT * from Base_Dictionary WHERE TypeCode ='ZWLBDM') lx ON cp.TypeCode = lx.MemberValue

findCorpPostionName
===
SELECT cp.*,lx.DisplayText from Corp_Postion cp
LEFT JOIN (SELECT * from Base_Dictionary WHERE TypeCode ='ZWLBDM') lx ON cp.TypeCode = lx.MemberValue
WHERE cp.CorpId = #{corpId} and  cp.Title like CONCAT('%',#{title},'%')

postionCount
===
SELECT COUNT(id) FROM Corp_Postion cp WHERE cp.`Status` = 1 and cp.CorpId = #{corpId}