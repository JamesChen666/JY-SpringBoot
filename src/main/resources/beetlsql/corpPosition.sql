list
===
SELECT ecp.*,cp.Title postionName FROM Election_Corp_Position ecp left join corp_postion cp on ecp.PositionId = cp.Id

findOne
===
SELECT * FROM Election_Corp_Position WHERE id = #{id}
