list
===
SELECT * FROM school_specialty_manager

findOne
===
SELECT * FROM school_specialty_manager where id = #{id}
deleteBySpecialtyCode
===
DELETE FROM school_specialty_manager where SpecialtyCode = #{SpecialtyCode}
findBySpecialtyCode
===
SELECT * FROM school_specialty_manager where SpecialtyCode = #{SpecialtyCode}
