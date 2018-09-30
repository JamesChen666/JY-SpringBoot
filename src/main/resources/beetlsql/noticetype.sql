list
===
SELECT * FROM portal_noticetype

findOne
===
SELECT * FROM portal_noticetype where id = #{id}

queryCode
===
select * from portal_noticetype where TypeCode = #{typeCode}

queryName
===
select * from portal_noticetype where TypeName = #{typeName}
