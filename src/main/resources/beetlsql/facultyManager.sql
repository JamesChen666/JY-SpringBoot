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
