list
===
SELECT pa.*,sl.RealName FROM Portal_Advert pa left join sys_loginaccount sl ON pa.UserId = sl.Id

findOne
===
SELECT * FROM Portal_Advert WHERE id = #{id}

queryPositionIdList
===
SELECT * FROM Portal_Advert where IsEnabled = 1 and PositionId = #{Id}
