list
===
SELECT ba.*,ba2.AreaName AS ParentName FROM base_area ba
LEFT JOIN base_area ba2 ON ba.ParentId = ba2.Id

list1
===
SELECT * FROM base_area

findOne
===
SELECT * FROM base_area WHERE id = #{id}
