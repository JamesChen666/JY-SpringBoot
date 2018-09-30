list
===
SELECT sf.*,
sc.CampusName,
(CASE
 WHEN sf.TypeID = 1 THEN '学院'
 ELSE '系部'
 END
) as TypeName,
GROUP_CONCAT(st.RealName) AS ManagerName
FROM school_faculty sf
LEFT JOIN school_campus sc ON sf.CampusCode = sc.CampusCode
LEFT JOIN school_faculty_manager sfm ON sf.FacultyCode = sfm.FacultyCode
LEFT JOIN school_teacher st ON sfm.JobNumber = st.JobNumber
GROUP BY sf.Id

findOne
===
SELECT * FROM school_faculty where id = #{id}
