
findByQuId
===
SELECT id as id,
        cg_qu_item_id as cgQuItemId,
        ck_qu_id as ckQuId,
        create_date as createDate,
        ge_le as geLe,
        logic_type as logicType,
        score_num as scoreNum,
        sk_qu_id as skQuId,
        visibility as visibility
FROM sv_question_logic where ck_qu_id=#{ckQuId} and visibility=#{ckQuId}

