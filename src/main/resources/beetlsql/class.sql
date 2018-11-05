list
===
SELECT sc.*,ss.SpecialtyName,sf.FacultyName,
      sc1.CampusName,GROUP_CONCAT(st.RealName) AS ManagerName
FROM school_class sc
LEFT JOIN school_specialty ss ON sc.SpecialtyCode = ss.SpecialtyCode
LEFT JOIN school_faculty sf ON ss.FacultyCode = sf.FacultyCode
LEFT JOIN school_campus sc1 ON sf.CampusCode = sc1.CampusCode
LEFT JOIN school_class_instructor sci ON sc.ClassNo = sci.ClassNo
LEFT JOIN school_teacher st ON sci.JobNumber = st.JobNumber
GROUP BY sc.Id

findOne
===
SELECT * FROM school_class WHERE id = #{id}
combotreeList
===
SELECT sc.ClassNo AS Id,sc.ClassName AS Name,sc.SpecialtyCode AS ParentId
FROM school_class sc
UNION ALL
SELECT DISTINCT ss.SpecialtyCode,ss.SpecialtyName,ss.FacultyCode
FROM school_class sc
LEFT JOIN school_specialty ss ON sc.SpecialtyCode = ss.SpecialtyCode
UNION ALL
SELECT DISTINCT sf.FacultyCode,sf.FacultyName,sf.CampusCode
FROM school_class sc
LEFT JOIN school_specialty ss ON sc.SpecialtyCode = ss.SpecialtyCode
LEFT JOIN school_faculty sf ON ss.FacultyCode = sf.FacultyCode
UNION ALL
SELECT DISTINCT sc1.CampusCode,sc1.CampusName,0
FROM school_class sc
LEFT JOIN school_specialty ss ON sc.SpecialtyCode = ss.SpecialtyCode
LEFT JOIN school_faculty sf ON ss.FacultyCode = sf.FacultyCode
LEFT JOIN school_campus sc1 ON sf.CampusCode = sc1.CampusCode

combotreeLists
===
SELECT * from (
SELECT sc.ClassNo AS Id,sc.ClassName AS Name,sc.SpecialtyCode AS ParentId,sc.GraduationYear
FROM school_class sc
UNION ALL
SELECT DISTINCT ss.SpecialtyCode,ss.SpecialtyName,ss.FacultyCode,sc.GraduationYear
FROM school_class sc
LEFT JOIN school_specialty ss ON sc.SpecialtyCode = ss.SpecialtyCode
UNION ALL
SELECT DISTINCT sf.FacultyCode,sf.FacultyName,sf.CampusCode,sc.GraduationYear
FROM school_class sc
LEFT JOIN school_specialty ss ON sc.SpecialtyCode = ss.SpecialtyCode
LEFT JOIN school_faculty sf ON ss.FacultyCode = sf.FacultyCode
UNION ALL
SELECT DISTINCT sc1.CampusCode,sc1.CampusName,0,sc.GraduationYear
FROM school_class sc
LEFT JOIN school_specialty ss ON sc.SpecialtyCode = ss.SpecialtyCode
LEFT JOIN school_faculty sf ON ss.FacultyCode = sf.FacultyCode
LEFT JOIN school_campus sc1 ON sf.CampusCode = sc1.CampusCode
) as class where GraduationYear=#{year}

findClass
===
SELECT * from School_Class sc WHERE sc.SpecialtyCode = #{SpecialtyCode}
