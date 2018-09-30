list
===
SELECT sc.*,GROUP_CONCAT(st.RealName) AS ManagerName FROM school_campus sc
LEFT JOIN school_campus_manager scm ON sc.CampusCode = scm.CampusCode
LEFT JOIN school_teacher st ON scm.JobNumber = st.JobNumber
GROUP BY sc.Id

findOne
===
SELECT * FROM school_campus where id = #{id}
