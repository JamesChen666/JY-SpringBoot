list
===
SELECT cp.Title,ss.SpecialtyName FROM corp_postionSpecialty cps
LEFT JOIN Corp_Postion cp ON cps.PositionId = cp.Id
LEFT JOIN School_Specialty ss ON ss.SpecialtyCode = cps.SpecialtyCode

findOne
===
SELECT * FROM corp_postionSpecialty WHERE id = #{id}

deleteSpecialty
===
DELETE FROM Corp_PostionSpecialty  WHERE PositionId = #{PositionId}