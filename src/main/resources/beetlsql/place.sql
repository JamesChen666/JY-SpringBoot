list
===
SELECT * FROM Recruit_Place

findOne
===
SELECT * FROM Recruit_Place WHERE id = #{id}

updatePlace
===
UPDATE Recruit_Place SET IsEnabled = 0 WHERE Id = #{id}
