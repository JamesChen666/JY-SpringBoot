
findByQuId
===
SELECT
        id as id,
        check_type as checkType,
        is_note as isNote,
        is_required_fill as isRequiredFill,
        option_name as optionName,
        option_title as optionTitle,
        order_by_id as orderById,
        qu_id as quId,
        visibility as visibility
 FROM sv_qu_radio where qu_id=#{quId}

findGroupStats
===
select
      qu_item_id as quItemId,count(qu_item_id) as count
  from sv_an_radio where visibility=1 and  qu_id=#{quId}
  GROUP BY qu_item_id

findStatsDataChart
===
select
      qu_item_id as quItemId,count(qu_item_id) as count
  from sv_an_radio where visibility=1 and  qu_id=#{quId}
  GROUP BY qu_item_id

findAnswer
===
select
    id as id,
        belong_answer_id as belongAnswerId,
        belong_id as belongId,
        other_text as otherText,
        qu_id as quId,
        qu_item_id as quItemId,
        visibility as visibility
from sv_an_radio
where belong_answer_id = #{belongAnswerId} and qu_id = #{quId}