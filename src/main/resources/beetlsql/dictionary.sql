list
===
SELECT * FROM base_dictionary

findOne
===
SELECT * FROM base_dictionary WHERE id = #{id}
findDictionaryByTypeCode
===
SELECT * FROM base_dictionary WHERE TypeCode = #{TypeCode}
