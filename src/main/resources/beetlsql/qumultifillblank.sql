
findByQuId
===
SELECT id as id,
        check_type as checkType,
        option_name as optionName,
        option_title as optionTitle,
        order_by_id as orderById,
        qu_id as quId,
        visibility as visibility
  FROM sv_qu_multi_fillblank where qu_id=#{quId}

findGroupStats
===
select qu_item_id as quItemId,count(1) as count
from sv_an_dfillblank
where  visibility=1 and  qu_id=#{quId} group by qu_item_id

findAnswer
===
select
id as id,
        answer as answer,
        belong_answer_id as belongAnswerId,
        belong_id as belongId,
        qu_id as quId,
        qu_item_id as quItemId,
        visibility as visibility
from sv_an_dfillblank
where belong_answer_id = #{belongAnswerId} and qu_id = #{quId}
