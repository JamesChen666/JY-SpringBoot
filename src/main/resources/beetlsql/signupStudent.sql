list
===
SELECT css.*,
 ss.RealName AS Student,ss.StudentNumber,
 st.RealName AS Teacher,st.JobNumber
 FROM consult_signup_student css
LEFT JOIN school_student ss ON css.StudentId = ss.Id
LEFT JOIN school_teacher st ON css.JobNumber = st.JobNumber

findOne
===
SELECT * FROM consult_signup_student WHERE id = #{id}
