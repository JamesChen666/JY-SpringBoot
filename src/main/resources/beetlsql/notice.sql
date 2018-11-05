list
===
SELECT * FROM portal_notice

findOne
===
SELECT * FROM portal_notice where id = #{id}

typeCodeList
===
SELECT * FROM portal_notice where TypeCode = #{typeCode} ORDER BY CreateDate DESC
