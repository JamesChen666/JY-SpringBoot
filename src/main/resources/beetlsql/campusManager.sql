list
===
SELECT * FROM school_campus_manager

deleteByCampusCode
===
DELETE FROM school_campus_manager where CampusCode = #{CampusCode}
findByCampusCode
===
SELECT * FROM school_campus_manager where CampusCode = #{CampusCode}

deleteByCampus
===
DELETE FROM school_campus_manager
WHERE CampusCode = (SELECT CampusCode FROM school_campus WHERE Id = #{Id})

/**
 * 查询出某校区的管理员
 */
queryCampusManager
===
SELECT scm.*,sc.CampusName,sc.CampusCode,st.RealName,sf.FacultyName from school_campus_manager scm
LEFT JOIN School_Campus sc ON scm.CampusCode = sc.CampusCode
LEFT JOIN School_Teacher st ON st.JobNumber = scm.JobNumber
LEFT JOIN School_Faculty sf ON st.FacultyCode = sf.FacultyCode
WHERE scm.CampusCode = #{campusCode}