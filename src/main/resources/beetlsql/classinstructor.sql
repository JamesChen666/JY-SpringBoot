list
===
SELECT * FROM school_class_instructor

findOne
===
SELECT * FROM school_class_instructor where id = #{id}
deleteByClassNo
===
DELETE FROM school_class_instructor where ClassNo = #{ClassNo}
findByClassNo
===
SELECT * FROM school_class_instructor where ClassNo = #{ClassNo}
