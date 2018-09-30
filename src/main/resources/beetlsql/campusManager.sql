list
===
SELECT * FROM school_campus_manager

deleteByCampusCode
===
DELETE FROM school_campus_manager where CampusCode = #{CampusCode}
findByCampusCode
===
SELECT * FROM school_campus_manager where CampusCode = #{CampusCode}

deleteByCampus
===
DELETE FROM school_campus_manager
WHERE CampusCode = (SELECT CampusCode FROM school_campus WHERE Id = #{Id})
