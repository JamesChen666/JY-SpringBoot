list
===
SELECT ec.*,sl.RealName,sc.CorpName,se.Title as ElectionName  FROM Election_CorpPart ec
left join sys_loginaccount sl on ec.ExamineUserId = sl.Id
left join school_corp sc on ec.CorpId = sc.Id
left join school_election se on ec.ElectionId = se.Id

findOne
===
SELECT * FROM Election_CorpPart WHERE id = #{id}
