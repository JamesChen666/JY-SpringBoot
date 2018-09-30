list
===
SELECT ss.*,sc.CorpName,sp.Title as placeName,sl.RealName,spn.PositionId,cp.Title as PositionName FROM School_Specia ss
left join school_corp sc on ss.CorpId = sc.Id
left join specia_place sp on ss.PlaceId = sp.Id
left join sys_loginaccount sl on ss.ExamineUserId = sl.Id
left join specia_position spn on spn.SpecialId = ss.Id
left join corp_postion cp on cp.Id = spn.PositionId


findOne
===
SELECT ss.*,sp.PositionId FROM School_Specia ss
left join specia_position sp on ss.Id = sp.SpecialId
 WHERE ss.id = #{id}
