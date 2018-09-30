list
===
SELECT se.*,sl.RealName FROM School_Election se left join sys_loginaccount sl on se.UserId = sl.Id

findOne
===
SELECT se.*,ei.IndustryCode,er.PlaceId,ecb.BoothId,ecp.PositionId FROM School_Election se
left join election_industry ei on se.Id = ei.ElectionId
left join election_runplace er on se.Id = er.ElectionId
left join election_corp_booth ecb on se.Id = ecb.ElectionCorpId
left join election_corp_position ecp on se.Id = ecp.ElectionCorpId
WHERE se.id = #{id}

lastOne
===
select * from school_election order by Id desc limit 0,1
