list
===
SELECT pn.*,pav.Title ParentTitle FROM Portal_Nav pn left join Portal_Nav pav ON pav.Id = pn.ParentId

findOne
===
SELECT * FROM Portal_Nav WHERE id = #{id}

navList
===
SELECT * FROM Portal_Nav where ParentId = 0

nodeList
===
SELECT * FROM Portal_Nav where ParentId = #{Id}