list
===
SELECT ec.*,sl.RealName,sc.CorpName,se.Title as ElectionName,GROUP_CONCAT(cp.Title) as postionName,GROUP_CONCAT(eb.BoothNumber) as BoothNumber FROM Election_CorpPart ec
left join sys_loginaccount sl on ec.ExamineUserId = sl.Id
left join school_corp sc on ec.CorpId = sc.Id
left join school_election se on ec.ElectionId = se.Id
LEFT JOIN election_corp_position ecp ON ec.Id = ecp.ElectionCorpId
LEFT JOIN corp_postion cp ON cp.id = ecp.PositionId
LEFT JOIN election_corp_booth ecb ON ecb.ElectionCorpId = ec.Id
LEFT JOIN election_booth eb ON ecb.BoothId = eb.Id
GROUP BY ec.Id


findOne
===
SELECT * FROM Election_CorpPart WHERE id = #{id}

lastOne
===
select * from Election_CorpPart order by Id desc limit 0,1

queryBoothIdandPositionId
===
select ec.*,GROUP_CONCAT(ecb.BoothId) as boothId,GROUP_CONCAT(ecp.PositionId) as positionId,
GROUP_CONCAT(ecb.Id) as ecbId,GROUP_CONCAT(ecp.Id) as ecpId from election_corppart ec
left join Election_Corp_Booth ecb on ec.Id = ecb.ElectionCorpId
left join Election_Corp_Position ecp on ec.Id = ecp.ElectionCorpId
where ec.Id = #{id}
GROUP BY ec.Id

queryBoothId
===
select ec.*,GROUP_CONCAT(ecb.BoothId) as boothId, GROUP_CONCAT(ecb.Id) as ecbId from election_corppart ec
left join Election_Corp_Booth ecb on ec.Id = ecb.ElectionCorpId

isExist
===
SELECT * from election_corppart ec WHERE ec.CorpId=#{corpId} AND ec.ElectionId = #{electionId}


