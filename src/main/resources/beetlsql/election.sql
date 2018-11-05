list
===
SELECT se.*,sl.RealName FROM School_Election se left join sys_loginaccount sl on se.UserId = sl.Id

findOne
===
SELECT se.*,GROUP_CONCAT(ei.IndustryCode) as IndustryCode,GROUP_CONCAT(er.PlaceId) as PlaceId,
GROUP_CONCAT(ei.Id) as eiId,GROUP_CONCAT(er.Id) as erId FROM School_Election se
left join election_industry ei on se.Id = ei.ElectionId
left join election_runplace er on se.Id = er.ElectionId
WHERE se.id = #{id}
GROUP BY se.Id


lastOne
===
select * from school_election order by Id desc limit 0,1
stuList
===
SELECT Title,Address,RunDate,SignEndDate,Description FROM school_election ORDER BY RunDate asc
findList
===
SELECT se.*,ec.CorpId,ec.ApplyDate,ec.ElectionId,ec.`Status`,ec.RecuitFiles FROM School_Election se
left join election_corppart ec ON ec.ElectionId = se.Id
