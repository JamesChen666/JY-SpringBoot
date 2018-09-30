list
===
SELECT ss.*,sf.FacultyName,sc.CampusName,GROUP_CONCAT(st.RealName) AS ManagerName FROM school_specialty ss
LEFT JOIN school_faculty sf ON ss.FacultyCode = sf.FacultyCode
LEFT JOIN school_campus sc ON sf.CampusCode = sc.CampusCode
LEFT JOIN school_specialty_manager ssm ON ss.SpecialtyCode = ssm.SpecialtyCode
LEFT JOIN school_teacher st ON ssm.JobNumber = st.JobNumber
GROUP BY ss.Id

findOne
===
SELECT * FROM school_specialty WHERE id = #{id}
combotreeList
===
SELECT ss.SpecialtyCode AS Id,ss.SpecialtyName AS Name,ss.FacultyCode AS ParentId
FROM school_specialty ss
UNION ALL
SELECT sf.FacultyCode,sf.FacultyName,sf.CampusCode
FROM school_specialty ss
LEFT JOIN school_faculty sf ON ss.FacultyCode = sf.FacultyCode
UNION ALL
SELECT sc1.CampusCode,sc1.CampusName,0
FROM school_specialty ss
LEFT JOIN school_faculty sf ON ss.FacultyCode = sf.FacultyCode
LEFT JOIN school_campus sc1 ON sf.CampusCode = sc1.CampusCode
