list
===
SELECT * FROM base_area_dispatchunit

findOne
===
SELECT * FROM base_area_dispatchunit where id = #{id}

deleteRelation
===
DELETE FROM base_area_dispatchunit WHERE areaCode = #{areaCode};
