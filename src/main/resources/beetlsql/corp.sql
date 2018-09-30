list
===
SELECT sc.*,sl.RealName FROM School_Corp sc left join sys_loginaccount sl on sc.ExamineUserId = sl.Id

findOne
===
SELECT sc.*,sl.RealName,sl.PassWord,sl.UserName FROM School_Corp sc left join sys_loginaccount sl on sc.UserId = sl.Id
  WHERE sc.id = #{id}
