list
===
SELECT ba.*,ba2.AreaName AS ParentName,bd.ProviderName as NormalCorpName,bd.Id DispatchUnitId,bd1.ProviderName as NonNormalCorpName,bd1.Id DispatchUnitId2 FROM base_area ba
LEFT JOIN base_area ba2 ON ba.ParentId = ba2.Id
LEFT JOIN base_area_dispatchunit bad ON bad.AreaCode = ba.AreaCode AND bad.IsNormal = 1
LEFT JOIN (SELECT * from Base_DispatchUnit WHERE IsNormal =1) bd ON bad.DispatchUnitId = bd.Id
LEFT JOIN base_area_dispatchunit bad2 ON bad2.AreaCode = ba.AreaCode AND bad2.IsNormal = 0
LEFT JOIN (SELECT * from Base_DispatchUnit WHERE IsNormal =0) bd1 ON bad2.DispatchUnitId = bd1.Id
/*SELECT ba.*,ba2.AreaName AS ParentName FROM base_area ba
LEFT JOIN base_area ba2 ON ba.ParentId = ba2.Id*/

areaList
===
SELECT
(CASE
WHEN ba3.AreaName IS NULL AND ba3.AreaName IS NULL AND ba2.AreaName IS NULL
THEN CONCAT(ba1.AreaName)
WHEN ba4.AreaName IS NULL AND ba3.AreaName IS NULL
THEN CONCAT(ba2.AreaName,ba1.AreaName)
WHEN ba4.AreaName IS NULL
THEN CONCAT(ba3.AreaName,ba2.AreaName,ba1.AreaName)
ELSE CONCAT(ba4.AreaName,ba3.AreaName,ba2.AreaName,ba1.AreaName)
END
)  as area FROM base_area ba1
LEFT JOIN base_area ba2 ON ba1.ParentId = ba2.Id
LEFT JOIN base_area ba3 ON ba2.ParentId = ba3.Id
LEFT JOIN base_area ba4 ON ba3.ParentId = ba4.Id
WHERE ba1.AreaCode = #{AreaCode}

findOne
===
SELECT * FROM base_area WHERE id = #{id}

find
===
SELECT ba.Id,ba.AreaName,ba.AreaCode,bd.Id as DispatchUnitId,bd.ProviderName,bd.IsNormal from Base_Area ba
LEFT JOIN Base_Area_DispatchUnit bad ON ba.AreaCode = bad.AreaCode
LEFT JOIN Base_DispatchUnit bd ON bd.Id = bad.DispatchUnitId
WHERE ba.Id = #{id}

queryProvince
===
SELECT * FROM Base_Area ba WHERE ba.ParentId =0

queryCity
===
SELECT * FROM Base_Area ba WHERE ba.ParentId =#{ParentId}

queryCounty
===
SELECT * FROM Base_Area ba WHERE ba.ParentId =#{ParentId}

queryCode
===
SELECT GROUP_CONCAT(ba2.AreaCode,',',ba1.AreaCode,',',ba.AreaCode) from base_area ba
LEFT JOIN base_area ba1 ON ba.ParentId = ba1.Id
LEFT JOIN base_area ba2 ON ba1.ParentId = ba2.Id
WHERE ba.AreaCode = #{AreaCode}