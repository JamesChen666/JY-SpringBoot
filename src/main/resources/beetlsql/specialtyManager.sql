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

/*查询出某专业的管理员*/
querySpecialtyManager
===
SELECT ssm.*,st.RealName,sf.FacultyName from School_Specialty_Manager  ssm
LEFT JOIN School_Teacher st ON st.JobNumber = ssm.JobNumber
LEFT JOIN School_Faculty sf ON st.FacultyCode = sf.FacultyCode
where ssm.SpecialtyCode = #{specialtyCode}
