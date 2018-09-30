list
===
SELECT ctt.*,st.RealName FROM consult_teacher_time ctt
LEFT JOIN school_teacher st ON ctt.JobNumber = st.JobNumber

findOne
===
SELECT * FROM consult_teacher_time WHERE id = #{id}
