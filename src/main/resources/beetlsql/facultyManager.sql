list
===
SELECT * FROM school_faculty_manager

findOne
===
SELECT * FROM school_faculty_manager where id = #{id}

deleteByFacultyCode
===
DELETE FROM school_faculty_manager where FacultyCode = #{FacultyCode}
findByFacultyCode
===
SELECT * FROM school_faculty_manager where FacultyCode = #{FacultyCode}

/*查询出某院系的管理员*/
queryFacultyManager
===
SELECT sfm.*,st.RealName,sf.FacultyName from School_Faculty_Manager sfm
LEFT JOIN School_Teacher st ON st.JobNumber = sfm.JobNumber
LEFT JOIN School_Faculty sf ON st.FacultyCode = sf.FacultyCode
where sfm.FacultyCode = #{facultyCode}
