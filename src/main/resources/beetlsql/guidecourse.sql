list
===
SELECT sg.*,st.RealName FROM school_guidecourse sg
LEFT JOIN school_teacher st ON sg.JobNumber = st.JobNumber

findOne
===
SELECT * FROM school_guidecourse WHERE id = #{id}
