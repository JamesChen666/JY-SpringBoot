list
===
SELECT ss.*,sc.CorpName,sp.Title as placeName,sl.RealName FROM Corp_Recruit ss
left join school_corp sc on ss.CorpId = sc.Id
left join Recruit_Place sp on ss.PlaceId = sp.Id
left join sys_loginaccount sl on ss.ExamineUserId = sl.Id
/*SELECT ss.*,sc.CorpName,sp.Title as placeName,sl.RealName,spn.PositionId,cp.Title as PositionName FROM School_Specia ss
left join school_corp sc on ss.CorpId = sc.Id
left join specia_place sp on ss.PlaceId = sp.Id
left join sys_loginaccount sl on ss.ExamineUserId = sl.Id
left join specia_position spn on spn.SpecialId = ss.Id
left join corp_postion cp on cp.Id = spn.PositionId*/


findOne
===
SELECT ss.*,GROUP_CONCAT(sp.PositionId) as PositionId,GROUP_CONCAT(sp.id) as spId FROM Corp_Recruit ss
left join Recruit_Position sp on ss.Id = sp.SpecialId
 WHERE ss.id = #{id}
GROUP BY ss.id

queryZczpList
===
SELECT ss.*,sp.Title,sc.CorpName from Corp_Recruit ss
LEFT JOIN Recruit_Place sp ON ss.PlaceId = sp.Id
LEFT JOIN School_Corp sc ON ss.CorpId = sc.Id

query
===
SELECT ss.*,sp.Title,sc.CorpName,cp.IsPortal from Corp_Recruit ss
LEFT JOIN Recruit_Place sp ON ss.PlaceId = sp.Id
LEFT JOIN School_Corp sc ON ss.CorpId = sc.Id
LEFT JOIN Recruit_Position spn ON spn.SpecialId = ss.Id
LEFT JOIN Corp_Postion cp ON spn.PositionId = cp.Id
WHERE cp.IsPortal = 1


queryCorp
===
SELECT ss.*,sc.CorpName,sc.OrganizetionCode,ba.AreaName,sc.Address,
bd1.DisplayText as ScaleCodeName,bd.DisplayText as IndustryCodeName
 from Corp_Recruit ss
LEFT JOIN School_Corp sc ON sc.Id = ss.CorpId
LEFT JOIN Base_Area ba ON sc.OrginCode = ba.AreaCode
LEFT JOIN (SELECT * from Base_Dictionary WHERE TypeCode = 'HYFLDM') bd ON bd.MemberValue = sc.IndustryCode
LEFT JOIN (SELECT * from Base_Dictionary WHERE TypeCode = 'QYGMDM') bd1 ON bd1.MemberValue = sc.ScaleCode
/*LEFT JOIN Specia_Position sp ON ss.Id = sp.SpecialId
LEFT JOIN Corp_Postion cp ON sp.PositionId = cp.Id*/
where ss.Id = #{id}

queryDetails
===
SELECT cp.*,sp.SpecialId FROM Corp_Recruit ss
LEFT JOIN Recruit_Position sp ON ss.Id = sp.SpecialId
LEFT JOIN Corp_Postion cp ON sp.PositionId = cp.Id
WHERE ss.Id = #{id}

queryPlace
===
SELECT sp.Id,sp.Title,sp.TypeId  from  Recruit_Place sp
LEFT JOIN Corp_Recruit ss ON ss.PlaceId = sp.Id
WHERE ss.Id = #{id}

stuList
===
SELECT
ss.CorpId,ss.PlaceId,ss.UseDate,
ss.StartHour,ss.EndHour,
sc.CorpName,bd1.DisplayText AS dwhy,
bd2.DisplayText AS dwxz,bd3.DisplayText AS dwgm,
sp.Title as placeName,sp.Address,
ba.AreaName AS dwdz
FROM Corp_Recruit ss
LEFT JOIN school_corp sc on ss.CorpId = sc.Id
LEFT JOIN Recruit_Place sp on ss.PlaceId = sp.Id
LEFT JOIN (SELECT MemberValue,DisplayText FROM base_dictionary WHERE TypeCode = 'HYFLDM') bd1 ON  sc.IndustryCode= bd1.MemberValue
LEFT JOIN (SELECT MemberValue,DisplayText FROM base_dictionary WHERE TypeCode = 'DWXZDM') bd2 ON  sc.NatureCdoe= bd2.MemberValue
LEFT JOIN (SELECT MemberValue,DisplayText FROM base_dictionary WHERE TypeCode = 'QYGMDM') bd3 ON  sc.ScaleCode= bd3.MemberValue
LEFT JOIN base_area ba ON sc.OrginCode = ba.AreaCode
WHERE ss.UseDate >= now() ORDER BY ss.UseDate,ss.StartHour

lastOne
===
SELECT * from Corp_Recruit  ORDER BY Id DESC LIMIT 0,1

toSchoolCount
===
select count(Id) from corp_recruit where CorpId=#{corpId}

contactCount
===
SELECT COUNT(rp.PositionId) FROM Corp_Recruit cr
LEFT JOIN Recruit_Position rp ON cr.Id = rp.SpecialId
WHERE  cr.TypeId=1 AND cr.CorpId=#{corpId}

