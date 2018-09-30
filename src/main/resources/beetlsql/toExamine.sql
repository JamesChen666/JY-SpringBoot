list
===
SELECT * from (
SELECT CONCAT(sc.Id,'-','School_Corp') as Id,sc.ExamineUserId,sc.ExamineDate,sc.ExamineRemark,sc.`Status`,sl.RealName,"School_Corp" as tableName from School_Corp sc
LEFT JOIN Sys_LoginAccount sl ON sc.ExamineUserId = sl.Id
UNION ALL
SELECT CONCAT(cp.Id,'-','Corp_Postion') as Id,cp.ExamineUserId,cp.ExamineDate,cp.ExamineRemark,cp.`Status`,sl.RealName,"Corp_Postion" as tableName from Corp_Postion cp
LEFT JOIN Sys_LoginAccount sl ON cp.ExamineUserId = sl.Id
UNION ALL
SELECT CONCAT(ss.Id,'-','School_Specia') as Id,ss.ExamineUserId,ss.ExamineDate,ss.ExamineRemark,ss.`Status`,sl.RealName,"School_Specia" as tableName from School_Specia ss
LEFT JOIN Sys_LoginAccount sl ON ss.ExamineUserId = sl.Id
UNION ALL
SELECT CONCAT(ec.Id,'-','Election_CorpPart') as Id,ec.ExamineUserId,ec.ExamineDate,ec.ExamineRemark,ec.`Status`,sl.RealName,"Election_CorpPart" as tableName from Election_CorpPart ec
LEFT JOIN Sys_LoginAccount sl ON ec.ExamineUserId = sl.Id
) as examine