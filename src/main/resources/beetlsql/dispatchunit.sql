list
===
SELECT bd.Id,
bd.Address,bd.IsEnabled,
bd.IsNormal,bd.FileReceivAddress,
bd.ProviderName,bd.FileReceivUnit,
(CASE WHEN bd.IsNormal = 1 THEN '是' ELSE '否' END)  as IsNormalName,
(CASE WHEN bd.IsEnabled = 1 THEN '是' ELSE '否' END)  as IsEnabledName
FROM base_dispatchunit bd

findOne
===
SELECT bd.*,
(CASE
WHEN ba3.AreaName IS NULL AND ba3.AreaName IS NULL AND ba2.AreaName IS NULL
THEN CONCAT(ba1.AreaName)
WHEN ba4.AreaName IS NULL AND ba3.AreaName IS NULL
THEN CONCAT(ba2.AreaName,ba1.AreaName)
WHEN ba4.AreaName IS NULL
THEN CONCAT(ba3.AreaName,ba2.AreaName,ba1.AreaName)
ELSE CONCAT(ba4.AreaName,ba3.AreaName,ba2.AreaName,ba1.AreaName)
END
) AS AreaAdress
FROM base_dispatchunit bd
LEFT JOIN base_area ba1 ON bd.AreaCode = ba1.AreaCode
LEFT JOIN base_area ba2 ON ba1.ParentId = ba2.Id
LEFT JOIN base_area ba3 ON ba2.ParentId = ba3.Id
LEFT JOIN base_area ba4 ON ba3.ParentId = ba4.Id
 WHERE bd.Id = #{Id}
