list
===
SELECT
  st.*,sf.FacultyName AS Faculty
FROM school_teacher st
LEFT JOIN school_faculty sf ON st.FacultyCode = sf.FacultyCode

findOne
===
SELECT * FROM school_teacher WHERE id = #{id}

findByJobNumber
===
SELECT * FROM school_teacher WHERE JobNumber = #{JobNumber}

combotree
===
SELECT DISTINCT
sc.CampusCode AS Id,sc.CampusName as Name,0 as ParentId
 FROM school_teacher st
LEFT JOIN school_faculty sf ON st.FacultyCode = sf.FacultyCode
LEFT JOIN school_campus sc ON sf.CampusCode = sc.CampusCode
UNION ALL
SELECT DISTINCT
sf.FacultyCode,sf.FacultyName,sf.CampusCode as ParentId
FROM school_teacher st
LEFT JOIN school_faculty sf ON st.FacultyCode = sf.FacultyCode
UNION ALL
SELECT DISTINCT
st.JobNumber,st.RealName,sf.FacultyCode as ParentId
FROM school_teacher st
LEFT JOIN school_faculty sf ON st.FacultyCode = sf.FacultyCode
teachingTeacher
===
SELECT DISTINCT
sc.CampusCode AS Id,sc.CampusName as Name,0 as ParentId
FROM consult_teaching_teacher ctt
LEFT JOIN school_teacher st ON ctt.JobNumber = st.JobNumber
LEFT JOIN school_faculty sf ON st.FacultyCode = sf.FacultyCode
LEFT JOIN school_campus sc ON sf.CampusCode = sc.CampusCode
UNION ALL
SELECT DISTINCT
sf.FacultyCode,sf.FacultyName,sf.CampusCode as ParentId
FROM consult_teaching_teacher ctt
LEFT JOIN school_teacher st ON ctt.JobNumber = st.JobNumber
LEFT JOIN school_faculty sf ON st.FacultyCode = sf.FacultyCode
UNION ALL
SELECT DISTINCT st.JobNumber,st.RealName,sf.FacultyCode as ParentId
FROM consult_teaching_teacher ctt
LEFT JOIN school_teacher st ON ctt.JobNumber = st.JobNumber
LEFT JOIN school_faculty sf ON st.FacultyCode = sf.FacultyCode
