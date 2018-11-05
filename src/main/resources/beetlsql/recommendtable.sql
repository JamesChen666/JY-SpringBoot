list
===
SELECT ss.Id,ss.RealName,ss.StudentNumber,
sr.HopeLocation,sr.FacultyRecommendation,
sr.CreateDate,sr.RecommendDate,sr.RecommendUserId,
sl.RealName AS Operator,
(CASE
 WHEN sr.Id IS NOT NULL
 THEN '已推荐'
 ELSE '未推荐'
 END
) AS recommend
FROM school_student ss
LEFT JOIN student_recommendtable sr ON ss.Id = sr.StudentId
LEFT JOIN sys_loginaccount sl ON sr.UserId = sl.Id
findOne
===
SELECT sr.HopeIndustryCode,sr.HopeNationCode,
sr.FacultyRecommendation,sr.HopeLocation,sr.SelfRecommendation,
sr.HopeSalary,sr.RecommendDate,sr.RecommendUserId,
sr.PositionRemark,sr.PracticeRemark,sr.RewardRemark
FROM student_recommendtable sr
LEFT JOIN school_student ss ON sr.StudentId = ss.Id
LEFT JOIN sys_loginaccount sl ON sr.UserId = sl.Id
WHERE ss.Id = #{Id}
