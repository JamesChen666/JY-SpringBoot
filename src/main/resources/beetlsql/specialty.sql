list
===
SELECT ss.*,
sf.FacultyName,
sc.CampusName,bd.DisplayText AS LevelCode,
GROUP_CONCAT(st.RealName) AS ManagerName
FROM school_specialty ss
LEFT JOIN school_faculty sf ON ss.FacultyCode = sf.FacultyCode
LEFT JOIN school_campus sc ON sf.CampusCode = sc.CampusCode
LEFT JOIN school_specialty_manager ssm ON ss.SpecialtyCode = ssm.SpecialtyCode
LEFT JOIN school_teacher st ON ssm.JobNumber = st.JobNumber
LEFT JOIN (SELECT MemberValue,DisplayText FROM base_dictionary WHERE TypeCode='PYCCDM') bd ON bd.MemberValue=ss.LevelCode
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

findSpecialty
===
SELECT ss.*,CONCAT(ss.SpecialtyName,"(",ss.SpecialtyDirection,")") as `name` from School_Specialty ss WHERE ss.FacultyCode = #{FacultyCode}

queryCurrentYearSpecialty
===
SELECT sp.SpecialtyName,sp.SpecialtyCode,sc.ClassName FROM School_Specialty sp
LEFT JOIN School_Class sc ON sp.SpecialtyCode = sc.SpecialtyCode
LEFT JOIN (SELECT ss.ClassNumber,COUNT(id) as counts FROM School_Student ss GROUP BY ss.ClassNumber) as student ON student.ClassNumber = sc.ClassNo
WHERE student.counts > 0 AND sc.GraduationYear = #{GraduationYear}