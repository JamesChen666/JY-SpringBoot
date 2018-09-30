list
===
SELECT cb.*,sl.RealName,sc.CorpName FROM Corp_BlackList cb
left join sys_loginaccount sl on cb.UserId = sl.Id
left join school_corp sc on cb.CorpId = sc.Id

findOne
===
SELECT * FROM Corp_BlackList WHERE id = #{id}
