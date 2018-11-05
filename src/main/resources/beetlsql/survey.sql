findOne
===
SELECT
    id as id,
      an_item_least_num as anItemLeastNum,
      answer_num as answerNum,
      create_date as createDate,
      dir_type as dirType,
      excerpt_num as excerptNum,
      html_path as htmlPath,
      is_share as isShare,
      parent_id as parentId,
      sid as sid,
      survey_detail_id as surveyDetailId,
      survey_model as surveyModel,
      survey_name as surveyName,
      survey_qu_num as surveyQuNum,
      survey_state as surveyState,
      survey_tag as surveyTag,
      user_id as userId,
      view_answer as viewAnswer,
      visibility as visibility
 FROM sv_survey_directory WHERE id = #{id}

list
===
SELECT
      d1.id as id,
			d1.survey_name as surveyName,
      d1.create_date as createDate,
			d1.survey_state as surveyState,
			d1.sid as sid,
			d2.beg_time as begTime,
			d2.end_time as endTime,
			d2.to_group as toGroup,
			d2.is_force as isForce
from sv_survey_directory d1
left join sv_survey_detail d2 on d1.id=d2.dir_id


findBySid
===
SELECT
    id as id,
      an_item_least_num as anItemLeastNum,
      answer_num as answerNum,
      create_date as createDate,
      dir_type as dirType,
      excerpt_num as excerptNum,
      html_path as htmlPath,
      is_share as isShare,
      parent_id as parentId,
      sid as sid,
      survey_detail_id as surveyDetailId,
      survey_model as surveyModel,
      survey_name as surveyName,
      survey_qu_num as surveyQuNum,
      survey_state as surveyState,
      survey_tag as surveyTag,
      user_id as userId,
      view_answer as viewAnswer,
      visibility as visibility
 FROM sv_survey_directory WHERE sid = #{sid}

questionList
===
SELECT * FROM sv_survey_directory WHERE survey_state=1

searchList
===
SELECT * FROM sv_survey_directory
WHERE survey_name LIKE #{'%'+search+'%'}
findQuestion
===
SELECT sq.*,ssd.survey_name FROM sv_question sq
LEFT JOIN sv_survey_directory ssd ON sq.belong_id = ssd.id
cancelQuestion
===
UPDATE sv_survey_directory SET survey_state=0,sid = NULL  WHERE id=#{id}

deleteDirectory
===
DELETE FROM sv_survey_directory WHERE id=#{id};
DELETE FROM sv_question WHERE belong_id = #{id};
DELETE FROM sv_survey_answer WHERE survey_id = #{id};
DELETE FROM sv_survey_detail WHERE dir_id = #{id};
DELETE FROM sv_survey_stats WHERE survey_id = #{id};
DELETE FROM sv_survey_style WHERE survey_id = #{id};
deleteQuestion
===
DELETE FROM sv_qu_checkbox WHERE qu_id = #{qid};
DELETE FROM sv_qu_multi_fillblank WHERE qu_id = #{qid};
DELETE FROM sv_qu_radio WHERE qu_id = #{qid};
DELETE FROM sv_an_checkbox WHERE qu_id = #{qid};
DELETE FROM sv_an_multi_fillblank WHERE qu_id = #{qid};
DELETE FROM sv_an_radio WHERE qu_id = #{qid};
