list
===
SELECT * FROM school_class_instructor

findOne
===
SELECT * FROM school_class_instructor where id = #{id}
deleteByClassNo
===
DELETE FROM school_class_instructor where ClassNo = #{ClassNo}
findByClassNo
===
SELECT * FROM school_class_instructor where ClassNo = #{ClassNo}

queryClassInstructor
===
SELECT sci.*,st.RealName,sf.FacultyName from School_Class_Instructor sci
LEFT JOIN School_Teacher st ON st.JobNumber = sci.JobNumber
LEFT JOIN School_Faculty sf ON st.FacultyCode = sf.FacultyCode
where sci.ClassNo = #{classNo}
