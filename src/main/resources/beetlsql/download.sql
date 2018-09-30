list
===
SELECT pd.*,sl.RealName FROM portal_download pd left join sys_loginaccount sl ON pd.UserId = sl.Id

findOne
===
SELECT * FROM portal_download WHERE id = #{id}

typeCodeList
===
SELECT * FROM portal_download where TypeCode = #{typeCode}
