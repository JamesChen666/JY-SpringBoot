list
===
SELECT sp.*,sp.id as spId,cp.Title as PositionName,sc.CorpName FROM Recruit_Position sp
left join corp_postion cp on sp.PositionId = cp.Id
left join Corp_Recruit ss on sp.SpecialId = ss.Id
left join school_corp sc on sc.Id = ss.CorpId

findOne
===
SELECT * FROM Recruit_Position WHERE id = #{id}

lastOne
===
SELECT * from Corp_Recruit  ORDER BY Id DESC LIMIT 0,1

findCorpName
===
SELECT ss.Id,sc.CorpName,sc.Id as CorpId from Corp_Recruit ss
LEFT JOIN school_corp sc ON sc.Id = ss.CorpId
WHERE ss.Id = #{id}