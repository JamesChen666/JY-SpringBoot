list
===
SELECT cl.*,sc.CorpName,sc.Contactor from corp_leave cl
LEFT JOIN school_corp sc ON cl.CorpId = sc.Id

findOne
===
SELECT * FROM Corp_Leave WHERE id = #{id}
