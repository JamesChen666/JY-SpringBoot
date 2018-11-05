list
===
SELECT * from (
SELECT CONCAT(sc.Id,'-','School_Corp') as Id,sc.ExamineUserId,sc.ExamineDate,sc.ExamineRemark,sc.`Status`,sl.RealName,"School_Corp" as tableName,sc.CorpName as `name` from School_Corp sc
LEFT JOIN Sys_LoginAccount sl ON sc.ExamineUserId = sl.Id
UNION ALL
SELECT CONCAT(cp.Id,'-','Corp_Postion') as Id,cp.ExamineUserId,cp.ExamineDate,cp.ExamineRemark,cp.`Status`,sl.RealName,"Corp_Postion" as tableName,cp.Title as `name` from Corp_Postion cp
LEFT JOIN Sys_LoginAccount sl ON cp.ExamineUserId = sl.Id
UNION ALL
SELECT CONCAT(ss.Id,'-','School_Specia') as Id,ss.ExamineUserId,ss.ExamineDate,ss.ExamineRemark,ss.`Status`,sl.RealName,"Corp_Recruit" as tableName,CONCAT(sc.CorpName,'_',sp.Title) as `name` from Corp_Recruit ss
LEFT JOIN Sys_LoginAccount sl ON ss.ExamineUserId = sl.Id
LEFT JOIN school_corp sc ON sc.Id = ss.CorpId
LEFT JOIN Recruit_place sp ON sp.Id = ss.PlaceId
UNION ALL
SELECT CONCAT(ec.Id,'-','Election_CorpPart') as Id,ec.ExamineUserId,ec.ExamineDate,ec.ExamineRemark,ec.`Status`,sl.RealName,"Election_CorpPart" as tableName,CONCAT(sc.CorpName,'_',se.Title) as `name` from Election_CorpPart ec
LEFT JOIN Sys_LoginAccount sl ON ec.ExamineUserId = sl.Id
LEFT JOIN school_corp sc ON sc.Id = ec.CorpId
LEFT JOIN school_election se ON se.Id = ec.ElectionId
) as examine


newList
===
SELECT sc.*,a.zs as zs1,b.zs as zs2,c.zs as zs3
from school_corp sc
LEFT JOIN (SELECT *,COUNT(`Status`) as zs from(
SELECT sc.CorpName,sc.EstablishmentDate,sc.`Status` from School_Corp sc
UNION ALL
SELECT sc.CorpName,sc.EstablishmentDate,cp.`Status` from  Corp_Postion cp
LEFT JOIN School_Corp sc ON cp.CorpId = sc.Id
UNION ALL
SELECT sc.CorpName,sc.EstablishmentDate,ss.`Status` from Corp_Recruit ss
LEFT JOIN  School_Corp sc ON ss.CorpId = sc.Id
UNION ALL
SELECT sc.CorpName,sc.EstablishmentDate,ec.`Status` from  Election_CorpPart ec
LEFT JOIN  School_Corp sc ON ec.CorpId = sc.Id
) as a WHERE `Status`=1 GROUP BY CorpName) as a ON sc.CorpName = a.CorpName

LEFT JOIN (SELECT *,COUNT(`Status`) as zs from(
SELECT sc.CorpName,sc.EstablishmentDate,sc.`Status` from School_Corp sc
UNION ALL
SELECT sc.CorpName,sc.EstablishmentDate,cp.`Status` from  Corp_Postion cp
LEFT JOIN School_Corp sc ON cp.CorpId = sc.Id
UNION ALL
SELECT sc.CorpName,sc.EstablishmentDate,ss.`Status` from Corp_Recruit ss
LEFT JOIN  School_Corp sc ON ss.CorpId = sc.Id
UNION ALL
SELECT sc.CorpName,sc.EstablishmentDate,ec.`Status` from  Election_CorpPart ec
LEFT JOIN  School_Corp sc ON ec.CorpId = sc.Id
) as a WHERE `Status`=2 GROUP BY CorpName) as b ON sc.CorpName = b.CorpName

LEFT JOIN (SELECT *,COUNT(`Status`) as zs from(
SELECT sc.CorpName,sc.EstablishmentDate,sc.`Status` from School_Corp sc
UNION ALL
SELECT sc.CorpName,sc.EstablishmentDate,cp.`Status` from  Corp_Postion cp
LEFT JOIN School_Corp sc ON cp.CorpId = sc.Id
UNION ALL
SELECT sc.CorpName,sc.EstablishmentDate,ss.`Status` from Corp_Recruit ss
LEFT JOIN  School_Corp sc ON ss.CorpId = sc.Id
UNION ALL
SELECT sc.CorpName,sc.EstablishmentDate,ec.`Status` from  Election_CorpPart ec
LEFT JOIN  School_Corp sc ON ec.CorpId = sc.Id
) as a WHERE `Status`=3 GROUP BY CorpName) as c ON sc.CorpName = c.CorpName
