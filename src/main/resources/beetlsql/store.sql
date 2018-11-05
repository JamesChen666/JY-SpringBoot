list
===
SELECT * FROM Student_Store

findOne
===
SELECT * FROM Student_Store WHERE id = #{id}

isStore
===
select * from Student_Store where StudentId = #{studentId} and PositionId = #{postionId}

storeCount
===
SELECT COUNT(id) from Student_Store ss WHERE ss.CorpId = #{corpId}
