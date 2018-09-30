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
SELECT * FROM Corp_Postion WHERE id = #{id}
