list
===
SELECT ctt.*,st.RealName FROM consult_teaching_teacher ctt
LEFT JOIN school_teacher st ON ctt.JobNumber = st.JobNumber

findOne
===
SELECT * FROM consult_teaching_teacher WHERE id = #{id}
