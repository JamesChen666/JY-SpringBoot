list
===
SELECT sp.*,cp.Title as PositionName,sc.CorpName FROM Specia_Position sp
left join corp_postion cp on sp.PositionId = cp.Id
left join school_specia ss on sp.SpecialId = ss.Id
left join school_corp sc on sc.Id = ss.CorpId

findOne
===
SELECT * FROM Specia_Position WHERE id = #{id}

lastOne
===
SELECT * from School_Specia  ORDER BY Id DESC LIMIT 0,1